package com.mycompany.es_19;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Inventory {
    private final List<Game> games;

    public Inventory() {
        this.games = new ArrayList<>();
    }

    public void addGame(Game game) {
        games.add(game);
    }

    public void removeGame(Game game) throws InventoryException {
        if (!games.contains(game)) {
            throw new InventoryException("Il gioco specificato non è presente nell'inventario.");
        }
        games.remove(game);
    }

    public List<Game> getGames() {
        return Collections.unmodifiableList(games);
    }

    public List<Game> searchGamesByPlatform(String platform) {
        List<Game> result = new ArrayList<>();
        for (Game game : games) {
            if (game.getPlatform().equalsIgnoreCase(platform)) {
                result.add(game);
            }
        }
        return result;
    }

    public List<Game> searchGamesByGenre(String genre) {
        List<Game> result = new ArrayList<>();
        for (Game game : games) {
            if (game.getGenre().equalsIgnoreCase(genre)) {
                result.add(game);
            }
        }
        return result;
    }

    // Metodo per esportare l'inventario in un formato specifico
    public String exportInventory() {
        StringBuilder sb = new StringBuilder();
        for (Game game : games) {
            sb.append(game.toExportFormat()).append("\n");
        }
        return sb.toString();
    }

    // Metodo per importare l'inventario da un formato specifico
    public void importInventory(String data) {
        String[] lines = data.split("\n");
        for (String line : lines) {
            Game game = Game.fromImportFormat(line.trim());
            addGame(game);
        }
    }

    // Metodo per ottenere la somma delle età di tutti i giochi nell'inventario
    public int getTotalReleaseAgeInYears() {
        int totalAgeInDays = 0;
        for (Game game : games) {
            totalAgeInDays += game.calculateReleaseAgeInDays();
        }
        return totalAgeInDays / 365; // Converti da giorni a anni
    }

    // Metodo per ottenere il gioco più recente nell'inventario
    public Game getLatestGame() {
        Game latestGame = null;
        LocalDate latestReleaseDate = LocalDate.MIN;
        for (Game game : games) {
            if (game.getReleaseDate().isAfter(latestReleaseDate)) {
                latestReleaseDate = game.getReleaseDate();
                latestGame = game;
            }
        }
        return latestGame;
    }

    // Altri metodi per l'esportazione, l'importazione, ecc. possono essere aggiunti qui
}