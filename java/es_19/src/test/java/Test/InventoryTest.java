package Test;

import com.mycompany.es_19.Game;
import com.mycompany.es_19.Inventory;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import java.time.LocalDate;
import com.mycompany.es_19.InventoryException;

public class InventoryTest {

    private Inventory inventory;
    private Game game1;
    private Game game2;

    @BeforeAll
    public void setUp() {
        inventory = new Inventory();
        game1 = new Game("The Witcher 3", "PC", "RPG", LocalDate.of(2015, 5, 19));
        game2 = new Game("Beat Saber", "VR", "Rhythm", LocalDate.of(2018, 5, 1));
    }

    @Test
    public void testAddGame() {
        inventory.addGame(game1);
        assertTrue(inventory.getGames().contains(game1));
    }

    @Test
    public void testRemoveGame() throws InventoryException {
        inventory.addGame(game1);
        inventory.addGame(game2);
        inventory.removeGame(game1);
        assertFalse(inventory.getGames().contains(game1));
        assertTrue(inventory.getGames().contains(game2));
    }

    @Test
    public void testRemoveNonExistentGame() throws InventoryException {
        inventory.removeGame(game1);
    }

    // Aggiungere altri test per i metodi della classe Inventory secondo necessità
}

