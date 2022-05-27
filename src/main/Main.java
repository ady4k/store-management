package main;

import input.FileRead;
import output.FileWrite;
import service.interactive.MenuService;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {

        MenuService menuService = new MenuService();

        FileRead.readFromFiles();
        System.out.println("Welcome to the system!");
        menuService.begin();
        System.out.println("Have a good day!");
        FileWrite.writeToFiles();
    }
}
