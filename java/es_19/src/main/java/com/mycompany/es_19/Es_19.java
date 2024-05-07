package com.mycompany.es_19;

import java.time.LocalDate;

public class Es_19 {

    public static void main(String[] args) throws InventoryException, ClassNotFoundException {
        // Creazione di alcuni giochi
        Game game1 = new Game("The Witcher 3", "PC", "RPG", LocalDate.of(2015, 5, 19));
        Game game2 = new Game("Beat Saber", "VR", "Rhythm", LocalDate.of(2018, 5, 1));

        // Creazione dell'inventario
        Inventory inventory = new Inventory();

        // Aggiunta dei giochi all'inventario
        inventory.addGame(game1);
        inventory.addGame(game2);

        // Chiamata al menu
        Menu.getInstance().menu();
    }
}
