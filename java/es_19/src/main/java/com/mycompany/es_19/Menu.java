package com.mycompany.es_19;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

/**
 * La classe Menu gestisce l'interazione con l'utente tramite un menu a console.
 * Consente di aggiungere, rimuovere, visualizzare e cercare giochi nell'inventario,
 * oltre a esportare e importare l'inventario da file.
 */
public class Menu {

    private static final Scanner scanner = new Scanner(System.in);
    private static final Inventory inventory = new Inventory();
    private static Menu instance;

    private Menu() {
    }

    /**
     * Restituisce l'istanza singleton del menu.
     * 
     * @return l'istanza del menu
     */
    public static Menu getInstance() {
        if (instance == null) {
            instance = new Menu();
        }
        return instance;
    }

    /**
     * Avvia il ciclo del menu principale per l'interazione con l'utente.
     * 
     * @throws ClassNotFoundException se si verifica un problema durante la lettura di file di inventario
     */
    public void menu() throws ClassNotFoundException {
        boolean exit = false;
        while (!exit) {
            try {
                int choice = displayMenuAndGetChoice();
                switch (choice) {
                    case 1 -> addGame();
                    case 2 -> removeGame();
                    case 3 -> displayInventory();
                    case 4 -> searchGamesByPlatform();
                    case 5 -> searchGamesByGenre();
                    case 6 -> printInventoryToFile();
                    case 7 -> readInventoryFromFile();
                    case 8 -> exit = true;
                    default -> System.out.println("Scelta non valida. Riprova.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Input non valido. Inserisci un numero.");
            } catch (InventoryException e) {
                System.out.println("Errore nell'operazione: " + e.getMessage());
            } catch (IOException e) {
                System.out.println("Errore di I/O: " + e.getMessage());
            }
        }
    }

    /**
     * Visualizza il menu principale e ottiene la scelta dell'utente.
     * 
     * @return la scelta dell'utente come intero
     */
    private static int displayMenuAndGetChoice() {
        System.out.println("\nMenu:");
        System.out.println("1. Aggiungi gioco");
        System.out.println("2. Rimuovi gioco");
        System.out.println("3. Visualizza inventario");
        System.out.println("4. Ricerca giochi per piattaforma");
        System.out.println("5. Ricerca giochi per genere");
        System.out.println("6. Stampare inventario su file");
        System.out.println("7. Leggere inventario da file");
        System.out.println("8. Esci");

        System.out.print("Scegli un'opzione: ");
        return Integer.parseInt(scanner.nextLine());
    }

    /**
     * Aggiunge un nuovo gioco all'inventario.
     * 
     * @throws InventoryException se si verifica un problema durante l'aggiunta del gioco
     */
    private void addGame() throws InventoryException {
        System.out.println("Inserisci le informazioni del gioco:");

        System.out.print("Nome: ");
        String name = scanner.nextLine();

        System.out.print("Piattaforma: ");
        String platform = scanner.nextLine();

        System.out.print("Genere: ");
        String genre = scanner.nextLine();

        LocalDate releaseDate = null;
        boolean validInput = false;
        while (!validInput) {
            System.out.print("Data di rilascio (AAAA-MM-GG): ");
            try {
                releaseDate = LocalDate.parse(scanner.nextLine());
                validInput = true;
            } catch (Exception e) {
                System.out.println("Formato data non valido. Riprova.");
            }
        }

        inventory.addGame(new Game(name, platform, genre, releaseDate));
    }

    /**
     * Rimuove un gioco dall'inventario in base al nome.
     * 
     * @throws InventoryException se si verifica un problema durante la rimozione del gioco
     */
    private static void removeGame() throws InventoryException {
        System.out.println("Inserisci il nome del gioco da rimuovere:");
        String gameName = scanner.nextLine();

        List<Game> gamesToRemove = inventory.getGames().stream()
                .filter(game -> game.getName().equalsIgnoreCase(gameName))
                .toList();

        if (gamesToRemove.isEmpty()) {
            System.out.println("Nessun gioco trovato con il nome specificato.");
            return;
        }

        if (gamesToRemove.size() > 1) {
            System.out.println("Sono stati trovati più giochi con lo stesso nome:");
            for (int i = 0; i < gamesToRemove.size(); i++) {
                System.out.println((i + 1) + ". " + gamesToRemove.get(i).toString());
            }
            System.out.print("Scegli l'indice del gioco da rimuovere: ");
            int index = Integer.parseInt(scanner.nextLine());
            if (index < 1 || index > gamesToRemove.size()) {
                System.out.println("Indice non valido.");
                return;
            }
            inventory.removeGame(gamesToRemove.get(index - 1));
            System.out.println("Gioco rimosso con successo dall'inventario.");
        } else {
            inventory.removeGame(gamesToRemove.get(0));
            System.out.println("Gioco rimosso con successo dall'inventario.");
        }
    }

    /**
     * Visualizza tutti i giochi presenti nell'inventario.
     */
    private static void displayInventory() {
        System.out.println("Inventario dei giochi:");
        for (Game game : inventory.getGames()) {
            System.out.println("Nome: " + game.getName() + ", Piattaforma: " + game.getPlatform() + ", Genere: " + game.getGenre() + ", Data di rilascio: " + game.getReleaseDate());
        }
    }

    /**
     * Cerca giochi nell'inventario in base alla piattaforma.
     */
    private static void searchGamesByPlatform() {
        System.out.print("Inserisci la piattaforma da cercare: ");
        String platform = scanner.nextLine();

        List<Game> gamesFound = inventory.searchGamesByPlatform(platform);

        if (gamesFound.isEmpty()) {
            System.out.println("Nessun gioco trovato per la piattaforma specificata.");
            return;
        }

        System.out.println("Giochi trovati per la piattaforma " + platform + ":");
        for (Game game : gamesFound) {
            System.out.println("Nome: " + game.getName() + ", Piattaforma: " + game.getPlatform() + ", Genere: " + game.getGenre() + ", Data di rilascio: " + game.getReleaseDate());
        }
    }

    /**
     * Cerca giochi nell'inventario in base al genere.
     */
    private static void searchGamesByGenre() {
        System.out.print("Inserisci il genere da cercare: ");
        String genre = scanner.nextLine();

        List<Game> gamesFound = inventory.searchGamesByGenre(genre);

        if (gamesFound.isEmpty()) {
            System.out.println("Nessun gioco trovato per il genere specificato.");
            return;
        }

        System.out.println("Giochi trovati per il genere " + genre + ":");
        for (Game game : gamesFound) {
            System.out.println("Nome: " + game.getName() + ", Piattaforma: " + game.getPlatform() + ", Genere: " + game.getGenre() + ", Data di rilascio: " + game.getReleaseDate());
        }
    }

    /**
     * Stampa l'inventario su un file.
     * 
     * @throws IOException se si verifica un problema durante la scrittura del file
     */
    private static void printInventoryToFile() throws IOException {
        System.out.print("Inserisci il nome del file in cui stampare l'inventario: ");
        String fileName = scanner.nextLine();
        inventory.printInventoryToFile(fileName);
    }

    /**
     * Legge l'inventario da un file.
     * 
     * @throws ClassNotFoundException se si verifica un problema durante la lettura del file
     */
    private static void readInventoryFromFile() throws ClassNotFoundException {
        System.out.print("Inserisci il nome del file da cui leggere l'inventario: ");
        String fileName = scanner.nextLine();

        Inventory newInventory = Inventory.readInventoryFromFile(fileName);
        inventory.getGames().clear();
        inventory.getGames().addAll(newInventory.getGames());
        System.out.println("Inventario letto correttamente dal file: " + fileName);
    }
}
