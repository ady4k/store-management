package main;

import service.interactive.MenuService;

public class Main {
    public static void main(String[] args) {
        // due to encapsulation, all it's required to start the program is the function that shows the main menu
        MenuService menuService = new MenuService();

        System.out.println("Welcome to the system!");
        menuService.begin();
        System.out.println("Have a good day!");
    }
}
