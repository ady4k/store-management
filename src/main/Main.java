package main;

import input.FileRead;
import output.FileWrite;
import persistence.SQLDatabase;
import service.interactive.MenuService;

import java.io.IOException;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws IOException {

        MenuService menuService = new MenuService();
        try {
            SQLDatabase sqlDatabase = new SQLDatabase();
        } catch (SQLException exc) {
            throw new RuntimeException(exc);
        }


        FileRead.readFromFiles();
        System.out.println("Welcome to the system!");
        menuService.begin();
        System.out.println("Have a good day!");
        FileWrite.writeToFiles();
    }
}
