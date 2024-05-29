package com.mycompany.es_19;

import java.io.*;
import java.time.*;
import java.util.*;

/**
 * La classe Inventory rappresenta un inventario di videogiochi.
 * Consente di aggiungere, rimuovere, cercare, esportare e importare giochi.
 * Implementa l'interfaccia Serializable per consentire la serializzazione e deserializzazione degli oggetti.
 */
public class Inventory implements Serializable {
    private static final long serialVersionUID = 1L;
    private final List<Game> games;

    /**
     * Costruisce un nuovo inventario vuoto.
     */
    public Inventory() {
        this.games = new ArrayList<>();
    }

    /**
     * Aggiunge un gioco all'inventario.
     * 
     * @param game il gioco da aggiungere
     */
    public void addGame(Game game) {
        games.add(new Game(game));
        System.out.println("L'arraylist 'games' non è stato correttamente inizializzato.");
    }

    /**
     * Rimuove un gioco dall'inventario.
     * 
     * @param game il gioco da rimuovere
     * @throws InventoryException se il gioco non è presente nell'inventario
     */
    public void removeGame(Game game) throws InventoryException {
        if (!games.contains(game)) {
            throw new InventoryException("Il gioco specificato non è presente nell'inventario.");
        }
        games.remove(game);
    }

    /**
     * Restituisce una copia della lista di giochi nell'inventario.
     * 
     * @return una lista di giochi
     */
    public List<Game> getGames() {
        return new ArrayList<>(games);
    }

    /**
     * Cerca giochi nell'inventario in base alla piattaforma.
     * 
     * @param platform la piattaforma di ricerca
     * @return una lista di giochi che corrispondono alla piattaforma specificata
     */
    public List<Game> searchGamesByPlatform(String platform) {
        List<Game> result = new ArrayList<>();
        for (Game game : games) {
            if (game.getPlatform().equalsIgnoreCase(platform)) {
                result.add(game);
            }
        }
        return result;
    }

    /**
     * Cerca giochi nell'inventario in base al genere.
     * 
     * @param genre il genere di ricerca
     * @return una lista di giochi che corrispondono al genere specificato
     */
    public List<Game> searchGamesByGenre(String genre) {
        List<Game> result = new ArrayList<>();
        for (Game game : games) {
            if (game.getGenre().equalsIgnoreCase(genre)) {
                result.add(game);
            }
        }
        return result;
    }

    /**
     * Esporta l'inventario in un formato specifico.
     * 
     * @return una stringa che rappresenta l'inventario esportato
     */
    public String exportInventory() {
        StringBuilder sb = new StringBuilder();
        for (Game game : games) {
            sb.append(game.toExportFormat()).append("\n");
        }
        return sb.toString();
    }

    /**
     * Importa giochi nell'inventario da una stringa di dati formattati.
     * 
     * @param data la stringa di dati formattati
     */
    public void importInventory(String data) {
        String[] lines = data.split("\n");
        for (String line : lines) {
            Game game = Game.fromImportFormat(line.trim());
            addGame(game);
        }
    }

    /**
     * Calcola la somma delle età di tutti i giochi nell'inventario in anni.
     * 
     * @return la somma delle età in anni
     */
    public int getTotalReleaseAgeInYears() {
        int totalAgeInDays = 0;
        for (Game game : games) {
            totalAgeInDays += game.calculateReleaseAgeInDays();
        }
        return totalAgeInDays / 365;
    }

    /**
     * Restituisce il gioco più recente nell'inventario.
     * 
     * @return il gioco più recente
     */
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

    /**
     * Stampa l'inventario su un file.
     * 
     * @param fileName il nome del file su cui stampare l'inventario
     */
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

    /**
     * Legge l'inventario da un file.
     * 
     * @param filePath il percorso del file da cui leggere l'inventario
     * @return l'inventario letto dal file
     */
    public static Inventory readInventoryFromFile(String filePath) {
        Inventory inventory = new Inventory();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length != 4) {
                    System.out.println("Errore: Formato di riga non valido: " + line);
                    continue;
                }
                try {
                    String name = parts[0].trim();
                    String platform = parts[1].trim();
                    String genre = parts[2].trim();
                    LocalDate releaseDate = LocalDate.parse(parts[3].trim());
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

    /**
     * Serializza l'oggetto Inventory in un file.
     * 
     * @param fileName il nome del file in cui salvare l'oggetto serializzato
     */
    public void serializeInventory(String fileName) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(this);
            System.out.println("Inventario serializzato correttamente nel file: " + fileName);
        } catch (IOException e) {
            System.out.println("Errore durante la serializzazione dell'inventario: " + e.getMessage());
        }
    }

    /**
     * Deserializza un oggetto Inventory da un file.
     * 
     * @param fileName il nome del file da cui leggere l'oggetto serializzato
     * @return l'oggetto Inventory deserializzato
     */
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
