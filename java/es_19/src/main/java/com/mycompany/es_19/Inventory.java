package com.mycompany.es_19;

import java.io.*;
import java.time.*;
import java.util.*;

public class Inventory implements Serializable{
    private static final long serialVersionUID = 1L;
    private final List<Game> games;

    public Inventory() {
        this.games = new ArrayList<>();
    }

    public void addGame(Game game) {
        games.add(new Game(game));
        // Se games è null, potremmo voler lanciare un'eccezione o gestire questa situazione in modo appropriato
        // Qui eseguiamo solo una stampa di debug per identificare il problema
        System.out.println("L'arraylist 'games' non è stato correttamente inizializzato.");
}

    public void removeGame(Game game) throws InventoryException {
        if (!games.contains(game)) {
            throw new InventoryException("Il gioco specificato non è presente nell'inventario.");
        }
        games.remove(game);
    }

    public List<Game> getGames() {
        return new ArrayList<>(games);
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
    
    public void printInventoryToFile(String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (Game game : games) {
                writer.write(game.getName() + "," + game.getPlatform() + "," + game.getGenre() + "," + game.getReleaseDate() + "\n");
            }
            System.out.println("Inventario stampato su file: " + fileName);
        } catch (IOException e) {
            System.out.println("Errore durante la scrittura del file: " + e.getMessage());
        }
    }

    // Metodo per leggere l'inventario da file
    public static Inventory readInventoryFromFile(String filePath) {
        Inventory inventory = new Inventory();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length != 4) {
                    System.out.println("Errore: Formato di riga non valido: " + line);
                    continue; // Ignora questa riga e passa alla successiva
                }
                try {
                    String name = parts[0].trim();
                    System.out.println(name);
                    String platform = parts[1].trim();
                    System.out.println(platform);
                    String genre = parts[2].trim();
                    System.out.println(genre);
                    LocalDate releaseDate = LocalDate.parse(parts[3].trim());
                    System.out.println(releaseDate);
                    inventory.addGame(new Game(name, platform, genre, releaseDate));
                } catch (Exception e) {
                    System.out.println("Errore durante la lettura della riga: " + line);
                }
            }
            System.out.println("Inventario letto dal file: " + filePath);
        } catch (IOException e) {
            System.out.println("Errore durante la lettura del file: " + e.getMessage());
        }
        return inventory;
    }
    // Metodo per eseguire la serializzazione dell'oggetto Inventory in un file
    public void serializeInventory(String fileName) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(this);
            System.out.println("Inventario serializzato correttamente nel file: " + fileName);
        } catch (IOException e) {
            System.out.println("Errore durante la serializzazione dell'inventario: " + e.getMessage());
        }
    }

    // Metodo per eseguire la deserializzazione di un oggetto Inventory da un file
    public static Inventory deserializeInventory(String fileName) {
        Inventory inventory = null;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            inventory = (Inventory) ois.readObject();
            System.out.println("Inventario deserializzato correttamente dal file: " + fileName);
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Errore durante la deserializzazione dell'inventario: " + e.getMessage());
        }
        return inventory;
    }

}
