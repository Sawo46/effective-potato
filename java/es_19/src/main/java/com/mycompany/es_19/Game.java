package com.mycompany.es_19;

import java.io.*;
import java.util.*;
import java.time.*;
import java.time.format.DateTimeParseException;

/**
 * Rappresenta un videogioco con attributi quali nome, piattaforma, genere e data di rilascio.
 * Implementa l'interfaccia Serializable per consentire la serializzazione e deserializzazione degli oggetti.
 */
public class Game implements Serializable {
    private static final long serialVersionUID = 1L;
    private final String name;
    private final String platform;
    private final String genre;
    private final LocalDate releaseDate;

    /**
     * Costruisce un nuovo oggetto Game con i dettagli forniti.
     * 
     * @param name         il nome del gioco
     * @param platform     la piattaforma su cui è disponibile il gioco
     * @param genre        il genere del gioco
     * @param releaseDate  la data di rilascio del gioco
     */
    public Game(String name, String platform, String genre, LocalDate releaseDate) {
        this.name = name;
        this.platform = platform;
        this.genre = genre;
        this.releaseDate = releaseDate;
    }

    /**
     * Costruisce un nuovo oggetto Game copiando i dettagli di un altro gioco.
     * 
     * @param g1 il gioco da cui copiare i dettagli
     */
    public Game(Game g1) {
        this.name = g1.getName();
        this.platform = g1.getPlatform();
        this.genre = g1.getGenre();
        this.releaseDate = g1.getReleaseDate();
    }

    /**
     * Restituisce il nome del gioco.
     * 
     * @return il nome del gioco
     */
    public String getName() {
        return name;
    }

    /**
     * Restituisce la piattaforma del gioco.
     * 
     * @return la piattaforma del gioco
     */
    public String getPlatform() {
        return platform;
    }

    /**
     * Restituisce il genere del gioco.
     * 
     * @return il genere del gioco
     */
    public String getGenre() {
        return genre;
    }

    /**
     * Restituisce la data di rilascio del gioco.
     * 
     * @return la data di rilascio del gioco
     */
    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    /**
     * Verifica se questo gioco è uguale a un altro oggetto.
     * 
     * @param obj l'oggetto da confrontare
     * @return true se gli oggetti sono uguali, altrimenti false
     */
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

    /**
     * Calcola il codice hash del gioco.
     * 
     * @return il codice hash del gioco
     */
    @Override
    public int hashCode() {
        return Objects.hash(name, platform, genre, releaseDate);
    }

    /**
     * Restituisce una rappresentazione testuale del gioco.
     * 
     * @return una stringa che rappresenta il gioco
     */
    @Override
    public String toString() {
        return "Game [name=" + name + ", platform=" + platform + ", genre=" + genre + ", releaseDate=" + releaseDate + "]";
    }

    /**
     * Verifica se il gioco è stato rilasciato prima di una certa data.
     * 
     * @param date la data da confrontare
     * @return true se il gioco è stato rilasciato prima della data specificata, altrimenti false
     */
    public boolean releasedBefore(LocalDate date) {
        return releaseDate.isBefore(date);
    }

    /**
     * Calcola da quanti giorni è stato rilasciato il gioco.
     * 
     * @return il numero di giorni trascorsi dalla data di rilascio del gioco
     */
    public long calculateReleaseAgeInDays() {
        return LocalDate.now().toEpochDay() - releaseDate.toEpochDay();
    }

    /**
     * Restituisce una rappresentazione del gioco per l'esportazione o il salvataggio.
     * 
     * @return una stringa formattata per l'esportazione del gioco
     */
    public String toExportFormat() {
        return name + ";" + platform + ";" + genre + ";" + releaseDate;
    }

    /**
     * Crea un oggetto Game da una stringa formattata per l'importazione.
     * 
     * @param data la stringa formattata contenente i dettagli del gioco
     * @return un nuovo oggetto Game creato dalla stringa di dati
     * @throws IllegalArgumentException se il formato della stringa di dati non è valido
     */
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

    /**
     * Serializza questo oggetto Game e lo salva in un file.
     * 
     * @param fileName il nome del file in cui salvare l'oggetto serializzato
     */
    public void serializeGame(String fileName) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(this);
            System.out.println("Gioco serializzato correttamente nel file: " + fileName);
        } catch (IOException e) {
            System.out.println("Errore durante la serializzazione del gioco: " + e.getMessage());
        }
    }

    /**
     * Deserializza un oggetto Game da un file.
     * 
     * @param fileName il nome del file da cui leggere l'oggetto serializzato
     * @return l'oggetto Game deserializzato
     */
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
