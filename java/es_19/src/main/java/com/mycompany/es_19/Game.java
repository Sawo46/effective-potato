package com.mycompany.es_19;

import java.io.*;
import java.util.*;
import java.time.*;
import java.time.format.DateTimeParseException;

public class Game implements Serializable {
    private static final long serialVersionUID = 1L;
    private final String name;
    private final String platform;
    private final String genre;
    private final LocalDate releaseDate;

    public Game(String name, String platform, String genre, LocalDate releaseDate) {
        this.name = name;
        this.platform = platform;
        this.genre = genre;
        this.releaseDate = releaseDate;
    }
    
    public Game(Game g1){
        this.name = g1.getName();
        this.platform = g1.getPlatform();
        this.genre = g1.getGenre();
        this.releaseDate = g1.getReleaseDate();
    }

    // Metodi getter per le informazioni del gioco
    public String getName() {
        return name;
    }

    public String getPlatform() {
        return platform;
    }

    public String getGenre() {
        return genre;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    // Metodi per confrontare giochi
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Game)) {
            return false;
        }
        Game other = (Game) obj;
        return name.equals(other.name) && platform.equals(other.platform) && genre.equals(other.genre) && releaseDate.equals(other.releaseDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, platform, genre, releaseDate);
    }

    // Metodo per ottenere una rappresentazione testuale del gioco
    @Override
    public String toString() {
        return "Game [name=" + name + ", platform=" + platform + ", genre=" + genre + ", releaseDate=" + releaseDate + "]";
    }

    // Metodo per controllare se un gioco è stato rilasciato prima di una certa data
    public boolean releasedBefore(LocalDate date) {
        return releaseDate.isBefore(date);
    }

    // Metodo per calcolare da quanto tempo è stato rilasciato un gioco
    public long calculateReleaseAgeInDays() {
        return LocalDate.now().toEpochDay() - releaseDate.toEpochDay();
    }

    // Metodo per ottenere una rappresentazione del gioco per esportazione o salvataggio
    public String toExportFormat() {
        // Qui si potrebbe definire un formato specifico per l'esportazione dei dati del gioco
        return name + ";" + platform + ";" + genre + ";" + releaseDate;
    }

    // Metodo per creare un gioco da una stringa formattata per l'importazione
    public static Game fromImportFormat(String data) {
        try {
            String[] parts = data.split(";");
            if (parts.length != 4) {
                throw new IllegalArgumentException("Il formato di importazione non è valido: " + data);
            }
            String name = parts[0];
            String platform = parts[1];
            String genre = parts[2];
            LocalDate releaseDate = LocalDate.parse(parts[3]);
            return new Game(name, platform, genre, releaseDate);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Formato data non valido per il gioco: " + e.getMessage());
        }
    }
    public void serializeGame(String fileName) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(this);
            System.out.println("Gioco serializzato correttamente nel file: " + fileName);
        } catch (IOException e) {
            System.out.println("Errore durante la serializzazione del gioco: " + e.getMessage());
        }
    }
    public static Game deserializeGame(String fileName) {
        Game game = null;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            game = (Game) ois.readObject();
            System.out.println("Gioco deserializzato correttamente dal file: " + fileName);
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Errore durante la deserializzazione del gioco: " + e.getMessage());
        }
        return game;
    }
}
