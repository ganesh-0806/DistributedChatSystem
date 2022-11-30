package org.example;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        try {
            ServerSocket server = new ServerSocket(8081);
            while(true)
            {
                System.out.println("Starting up");
                server.accept();
                System.out.println("Accepted");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}