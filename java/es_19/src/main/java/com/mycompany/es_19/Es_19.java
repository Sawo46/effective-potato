package com.mycompany.es_19;

import java.time.LocalDate;

public class Es_19 {

    public static void main(String[] args) {
        // Creazione di alcuni giochi
        Game game1 = new Game("The Witcher 3", "PC", "RPG", LocalDate.of(2015, 5, 19));
        Game game2 = new Game("Beat Saber", "VR", "Rhythm", LocalDate.of(2018, 5, 1));

        // Creazione dell'inventario
        Inventory inventory = new Inventory();

        // Aggiunta dei giochi all'inventario
        inventory.addGame(game1);
        inventory.addGame(game2);

        // Stampa dell'inventario
        System.out.println("Inventario dei giochi:");
        for (Game game : inventory.getGames()) {
            System.out.println("Nome: " + game.getName() + ", Piattaforma: " + game.getPlatform() + ", Genere: " + game.getGenre() + ", Data di rilascio: " + game.getReleaseDate());
        }

        // Esempio di ricerca giochi per piattaforma
        String platformToSearch = "PC";
        System.out.println("\nGiochi per piattaforma " + platformToSearch + ":");
        for (Game game : inventory.searchGamesByPlatform(platformToSearch)) {
            System.out.println("Nome: " + game.getName() + ", Piattaforma: " + game.getPlatform() + ", Genere: " + game.getGenre() + ", Data di rilascio: " + game.getReleaseDate());
        }

        // Esempio di ricerca giochi per genere
        String genreToSearch = "RPG";
        System.out.println("\nGiochi di genere " + genreToSearch + ":");
        for (Game game : inventory.searchGamesByGenre(genreToSearch)) {
            System.out.println("Nome: " + game.getName() + ", Piattaforma: " + game.getPlatform() + ", Genere: " + game.getGenre() + ", Data di rilascio: " + game.getReleaseDate());
        }

        // Esempio di calcolo dell'età totale dei giochi nell'inventario
        int totalAgeInYears = inventory.getTotalReleaseAgeInYears();
        System.out.println("\nEtà totale dei giochi nell'inventario: " + totalAgeInYears + " anni");

        // Esempio di ottenimento del gioco più recente nell'inventario
        Game latestGame = inventory.getLatestGame();
        System.out.println("\nGioco più recente nell'inventario:");
        if (latestGame != null) {
            System.out.println("Nome: " + latestGame.getName() + ", Piattaforma: " + latestGame.getPlatform() + ", Genere: " + latestGame.getGenre() + ", Data di rilascio: " + latestGame.getReleaseDate());
        } else {
            System.out.println("Nessun gioco presente nell'inventario.");
        }
    }
}
