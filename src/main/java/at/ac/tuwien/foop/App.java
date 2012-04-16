package at.ac.tuwien.foop;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import at.ac.tuwien.foop.basic.Tournament;
import at.ac.tuwien.foop.game.gameobjects.Player;
import at.ac.tuwien.foop.game.gameobjects.PlayerColor;
import at.ac.tuwien.foop.game.levels.InvalidLevelConfigurationException;
import at.ac.tuwien.foop.game.levels.Level;
import at.ac.tuwien.foop.game.levels.Level01;
import at.ac.tuwien.foop.game.levels.Level02;
import at.ac.tuwien.foop.game.levels.Level03;
import at.ac.tuwien.foop.game.levels.Level2Players;
import at.ac.tuwien.foop.game.levels.LevelsNotCompatibleException;
import at.ac.tuwien.foop.game.levels.TestLevel;

public class App {
    private List<Level> levels = new ArrayList<Level>();
    private Set<Player> players = new HashSet<Player>();

    public static void main(String[] args) throws LevelsNotCompatibleException, InvalidLevelConfigurationException {
        new App();
    }

    private App() throws LevelsNotCompatibleException, InvalidLevelConfigurationException {
        // loadLevels();
        // createPlayers();

        loadLevels();
        createPlayers();

        Tournament tournament = new Tournament(levels, players);
        tournament.startTournament();

        // Client client = new Client();
        // client.run();
    }

    private void createPlayers() {
        players.add(new Player(PlayerColor.BLUE));
        players.add(new Player(PlayerColor.GREEN));
        players.add(new Player(PlayerColor.RED));
    }

    private void loadLevels() throws InvalidLevelConfigurationException {
        levels.add(new Level01());
        levels.add(new Level02());
        levels.add(new Level03());
    }

    private void createPlayers2() {
        players.add(new Player(PlayerColor.BLUE));
    }

    private void loadLevels2() throws InvalidLevelConfigurationException {
        levels.add(new TestLevel());
        levels.add(new TestLevel());
    }

    private void createPlayers3() {
        players.add(new Player(PlayerColor.BLUE));
        players.add(new Player(PlayerColor.RED));
    }

    private void loadLevels3() throws InvalidLevelConfigurationException {
        levels.add(new Level2Players());
    }
}
