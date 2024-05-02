package Test;

import com.mycompany.es_19.Game;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;

public class GameTest {

    @Test
    public void testEquals() {
        Game game1 = new Game("The Witcher 3", "PC", "RPG", LocalDate.of(2015, 5, 19));
        Game game2 = new Game("The Witcher 3", "PC", "RPG", LocalDate.of(2015, 5, 19));
        Game game3 = new Game("Beat Saber", "VR", "Rhythm", LocalDate.of(2018, 5, 1));

        assertTrue(game1.equals(game2));
        assertFalse(game1.equals(game3));
    }

    @Test
    public void testHashCode() {
        Game game1 = new Game("The Witcher 3", "PC", "RPG", LocalDate.of(2015, 5, 19));
        Game game2 = new Game("The Witcher 3", "PC", "RPG", LocalDate.of(2015, 5, 19));
        Game game3 = new Game("Beat Saber", "VR", "Rhythm", LocalDate.of(2018, 5, 1));

        assertEquals(game1.hashCode(), game2.hashCode());
        assertFalse(game1.hashCode() == game3.hashCode());
    }

    // Aggiungere altri test per i metodi della classe Game secondo necessità
}
