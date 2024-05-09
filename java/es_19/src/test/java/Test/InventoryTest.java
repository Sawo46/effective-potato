package Test;

import com.mycompany.es_19.Game;
import com.mycompany.es_19.Inventory;
import com.mycompany.es_19.InventoryException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class InventoryTest {

    private Inventory inventory;
    private Game game;

    @BeforeEach
    void setUp() {
        inventory = new Inventory();
        LocalDate releaseDate = LocalDate.of(2022, 4, 20);
        game = new Game("Test Game", "Platform", "Genre", releaseDate);
    }

    @Test
    void testAddGame() {
        inventory.addGame(game);
        List<Game> games = inventory.getGames();
        assertEquals(1, games.size());
        assertEquals(game, games.get(0));
    }

    @Test
    void testRemoveGame() throws InventoryException {
        inventory.addGame(game);
        inventory.removeGame(game);
        List<Game> games = inventory.getGames();
        assertTrue(games.isEmpty());
    }

    @Test
    void testSearchGamesByPlatform() {
        inventory.addGame(game);
        List<Game> gamesFound = inventory.searchGamesByPlatform("Platform");
        assertEquals(1, gamesFound.size());
        assertEquals(game, gamesFound.get(0));
    }

    @Test
    void testSearchGamesByGenre() {
        inventory.addGame(game);
        List<Game> gamesFound = inventory.searchGamesByGenre("Genre");
        assertEquals(1, gamesFound.size());
        assertEquals(game, gamesFound.get(0));
    }

    @Test
    void testExportInventory() {
        inventory.addGame(game);
        String exportedInventory = inventory.exportInventory();
        assertTrue(exportedInventory.contains("Test Game;Platform;Genre;2022-04-20"));
    }

    @Test
    void testImportInventory() {
        String importedData = "Test Game;Platform;Genre;2022-04-20\n";
        inventory.importInventory(importedData);
        List<Game> games = inventory.getGames();
        assertEquals(1, games.size());
        Game importedGame = games.get(0);
        assertEquals("Test Game", importedGame.getName());
        assertEquals("Platform", importedGame.getPlatform());
        assertEquals("Genre", importedGame.getGenre());
        assertEquals(LocalDate.of(2022, 4, 20), importedGame.getReleaseDate());
    }
}
