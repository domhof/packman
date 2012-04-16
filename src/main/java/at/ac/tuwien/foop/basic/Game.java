package at.ac.tuwien.foop.basic;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import at.ac.tuwien.foop.game.Direction;
import at.ac.tuwien.foop.game.gameobjects.Player;
import at.ac.tuwien.foop.network.ClientHandle;
import at.ac.tuwien.foop.network.ConcreteNetworkAdapter;
import at.ac.tuwien.foop.network.GameStateDTO;
import at.ac.tuwien.foop.network.NetworkAdapter;

/**
 * Represents one round. Also defines when round finishes.
 * 
 * @author dominik
 * 
 */
public class Game {
    private static final long MILLISECONDS = 100;
    private static final Integer TICKCOUNTER = 20;

    private final GameState gameState = new GameState();
    private final Set<Player> players;
    private final Tournament tournament;

    private final NetworkAdapter networkAdapter;
    private final Hashtable<ClientHandle, Player> clientPlayerMapping;

    public Game(Tournament tournament, Set<GameObject> gameObjects, Set<Player> players) {
        gameState.initGameObjects(gameObjects);
        this.tournament = tournament;
        this.players = players;

        // Setup network
        networkAdapter = new ConcreteNetworkAdapter();
        clientPlayerMapping = new Hashtable<ClientHandle, Player>();
    }

    public void startGame() {
        // TODO Dominik check design
        List<ClientHandle> clients = networkAdapter.awaitConnections(players.size());

        // Assign Client Handles to players
        Iterator<Player> playerIt = players.iterator();
        for (ClientHandle c : clients) {
            Player player = playerIt.next();
            clientPlayerMapping.put(c, player);
        }

        // Register game inputs
        // Note [hs]: Currently there is no locking between logics and
        // observation of these
        // events. Correctness is in this particular case given by the fact
        // the complete network transfer is synchronized with MainLoop already.
        for (final ClientHandle c : clients) {
            c.getInputEvents().addObserver(new Observer() {
                @Override
                public void update(Observable arg0, Object arg1) {
                    final Player player = clientPlayerMapping.get(c); // get
                                                                      // player

                    Direction acutalDirection = c.getInputEvents().getDirection();

                    player.setDirection(acutalDirection);
                }
            });
        }

        // TODO Game loop which calls gameStates.tick() in every cycle.

        final Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            Integer tickCounter = TICKCOUNTER;

            @Override
            public void run() {
                networkAdapter.tick();

                // Transfer only gameStateDelta and call draw() on these objects
                // on client side to update game view.
                Set<GameObject> gameStateDelta = gameState.tick();
                transerGameStateToClients(gameStateDelta);
                sendStatus(gameEnds(), tournament.gameLeft());

                if (gameEnds()) {
                    System.out.println("Game ended. cancelling");
                    timer.cancel();
                    networkAdapter.dispose();
                    tournament.gameFinished();
                } else {
                    rotatePlayerColors();
                }
            }

            private void rotatePlayerColors() {
                tickCounter--;
                if (tickCounter == 0) {
                    for (Player p : players) {
                        p.setColor(p.getColor().nextColor());
                        tickCounter = TICKCOUNTER;
                    }
                }
            }
        }, 40, MILLISECONDS);
    }

    private boolean gameEnds() {
        int alivePlayers = players.size();
        for (Player p : players) {
            if (!p.isAlive()) {
                --alivePlayers;
            }
        }
        // return false;
        return alivePlayers <= 1;
    }

    /**
     * Prints out the current game state for testing purposes.
     * 
     * @param list All game objects in the game state.
     */
    public static void printGameState(List<GameObject> list) {
        int levelWidth = 0;
        int levelHeight = 0;

        for (GameObject gameObject : list) {
            // allGameObjects.add(gameObject);

            int x = gameObject.getPosition().getX();
            if (levelWidth < x) {
                levelWidth = x;
            }
            int y = gameObject.getPosition().getY();
            if (levelHeight < y) {
                levelHeight = y;
            }
        }

        System.out.println();
        for (int y = 0; y <= levelHeight; y++) {
            for (int x = 0; x <= levelWidth; x++) {
                boolean found = false;
                for (GameObject go : list) {
                    if (go.getPosition().equals(new Position(x, y))) {
                        go.draw();
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    System.out.print("F");
                }
            }
            System.out.println();
        }
    }

    private void transerGameStateToClients(Set<GameObject> allGameObjects) {

        ArrayList<GameObject> gameObjects = new ArrayList<GameObject>();

        for (GameObject o : allGameObjects) {
            gameObjects.add(o);
        }

        GameStateDTO dto = new GameStateDTO(gameObjects);

        for (ClientHandle h : clientPlayerMapping.keySet()) {
            h.transferGameState(dto);
        }
    }

    private void sendStatus(boolean gameEnds, boolean anotherLevelLeft) {

        for (ClientHandle h : clientPlayerMapping.keySet()) {

            Player p = clientPlayerMapping.get(h);

            String message = "Erreichte Punkte: " + p.getPoints();

            h.transferStatus(gameEnds, message, anotherLevelLeft);
        }
    }
}
