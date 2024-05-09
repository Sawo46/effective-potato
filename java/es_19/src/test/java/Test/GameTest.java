package Test;

import com.mycompany.es_19.Game;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class GameTest {

    private Game game;

    @BeforeEach
    void setUp() {
        LocalDate releaseDate = LocalDate.of(2022, 4, 20);
        game = new Game("Test Game", "Platform", "Genre", releaseDate);
    }

    @Test
    void testGetName() {
        assertEquals("Test Game", game.getName());
    }

    @Test
    void testGetPlatform() {
        assertEquals("Platform", game.getPlatform());
    }

    @Test
    void testGetGenre() {
        assertEquals("Genre", game.getGenre());
    }

    @Test
    void testGetReleaseDate() {
        assertEquals(LocalDate.of(2022, 4, 20), game.getReleaseDate());
    }

    @Test
    void testEquals() {
        LocalDate releaseDate = LocalDate.of(2022, 4, 20);
        Game sameGame = new Game("Test Game", "Platform", "Genre", releaseDate);
        assertTrue(game.equals(sameGame));
    }

    @Test
    void testNotEquals() {
        LocalDate releaseDate = LocalDate.of(2022, 4, 20);
        Game differentGame = new Game("Different Game", "Platform", "Genre", releaseDate);
        assertFalse(game.equals(differentGame));
    }

    @Test
    void testReleasedBefore() {
        LocalDate dateBefore = LocalDate.of(2022, 4, 19);
        assertTrue(game.releasedBefore(dateBefore));
        LocalDate dateAfter = LocalDate.of(2022, 4, 21);
        assertFalse(game.releasedBefore(dateAfter));
    }

    @Test
    void testCalculateReleaseAgeInDays() {
        long expectedDays = LocalDate.now().toEpochDay() - LocalDate.of(2022, 4, 20).toEpochDay();
        assertEquals(expectedDays, game.calculateReleaseAgeInDays());
    }

    @Test
    void testToExportFormat() {
        assertEquals("Test Game;Platform;Genre;2022-04-20", game.toExportFormat());
    }

    @Test
    void testFromImportFormat() {
        String importData = "Test Game;Platform;Genre;2022-04-20";
        Game importedGame = Game.fromImportFormat(importData);
        assertEquals("Test Game", importedGame.getName());
        assertEquals("Platform", importedGame.getPlatform());
        assertEquals("Genre", importedGame.getGenre());
        assertEquals(LocalDate.of(2022, 4, 20), importedGame.getReleaseDate());
    }
}
