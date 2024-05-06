package com.mycompany.es_19;

import java.time.LocalDate;
import java.util.Scanner;
import java.util.List;

public class Menu {

    private static final Scanner scanner = new Scanner(System.in);
    private static final Inventory inventory = new Inventory();
    private static Menu instance;

    private Menu() {
    }

    public static Menu getInstance() {
        if (instance == null) {
            instance = new Menu();
        }
        return instance;
    }

    public void menu() throws InventoryException {
        boolean exit = false;
        while (!exit) {
            int choice = displayMenuAndGetChoice();
            switch (choice) {
                case 1 -> addGame();
                case 2 -> removeGame();
                case 3 -> displayInventory();
                case 4 -> searchGamesByPlatform();
                case 5 -> searchGamesByGenre();
                case 6 -> exit = true;
                default -> System.out.println("Scelta non valida. Riprova.");
            }
        }
    }

    private static int displayMenuAndGetChoice() {
        System.out.println("\nMenu:");
        System.out.println("1. Aggiungi gioco");
        System.out.println("2. Rimuovi gioco");
        System.out.println("3. Visualizza inventario");
        System.out.println("4. Ricerca giochi per piattaforma");
        System.out.println("5. Ricerca giochi per genere");
        System.out.println("6. Esci");

        System.out.print("Scegli un'opzione: ");
        return Integer.parseInt(scanner.nextLine());
    }

    private void addGame() {
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

    private static void displayInventory() {
        System.out.println("Inventario dei giochi:");
        for (Game game : inventory.getGames()) {
            System.out.println("Nome: " + game.getName() + ", Piattaforma: " + game.getPlatform() + ", Genere: " + game.getGenre() + ", Data di rilascio: " + game.getReleaseDate());
        }
    }

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
}