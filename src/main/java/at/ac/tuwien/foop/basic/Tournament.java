package at.ac.tuwien.foop.basic;

import java.util.List;
import java.util.Set;

import at.ac.tuwien.foop.game.gameobjects.Player;
import at.ac.tuwien.foop.game.levels.Level;
import at.ac.tuwien.foop.game.levels.LevelsNotCompatibleException;

public class Tournament {
    private final Set<Player> players;
    private final List<Level> levels;
    private int nextLevelIndex = 0;

    public Tournament(List<Level> levels, Set<Player> players) throws LevelsNotCompatibleException {
        final int cntTournamentPlayers = players.size();
        for (Level l : levels) {
            if (l.numberOfPlayers() != cntTournamentPlayers) {
                throw new LevelsNotCompatibleException(
                        "Number of players provided doesn't match with number of defined player positions in level.");
            }
        }
        this.players = players;
        this.levels = levels;
    }

    public void startTournament() {
        System.out.println("Tournament started!");
        if (gameLeft()) {
            startNextGame();
        } else {
            tournamentFinished();
        }
    }

    public void startNextGame() {
        Level nextLevel = levels.get(nextLevelIndex);
        System.out.print("Round " + nextLevelIndex + " (" + nextLevel.getName() + ")... ");

        Set<GameObject> allGameObjects = nextLevel.getAllGameObjects();
        allGameObjects.addAll(this.players);
        assignPlayersToPositions(nextLevel);
        Game game = new Game(this, allGameObjects, players);

        game.startGame(); // Starts a new thread.

        ++this.nextLevelIndex;
    }

    private void assignPlayersToPositions(Level nextLevel) {
        preparePlayers();
        Position[] positions = nextLevel.getPlayerPositions().toArray(new Position[] {});
        Player[] players = this.players.toArray(new Player[] {});
        for (int i = 0; i < positions.length; i++) {
            players[i].setPosition(positions[i]);
        }
    }

    private void preparePlayers() {
        for (Player p : players) {
            p.preparePlayer();
        }
    }

    /**
     * Called by game thread when game is finished.
     */
    void gameFinished() {
        if (gameLeft()) {
            System.out.println("finished.");
            startNextGame();
        } else {
            tournamentFinished();
        }
    }

    private void tournamentFinished() {
        System.out.println("Tournament finished.\nResults:");
        for (Player p : players) {
            System.out.println("Player " + p.getColor() + " points: " + p.getPoints());
        }
    }

    public boolean gameLeft() {
        return this.nextLevelIndex < levels.size();
    }
   
}
