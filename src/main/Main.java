package main;

import service.interactive.MenuService;

public class Main {
    public static void main(String[] args) {
        MenuService menuService = new MenuService();
        System.out.println("Welcome to the system!");
        menuService.begin();
        System.out.println("Have a good day!");
    }
}
