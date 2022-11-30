package org.example;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        try {

            while(true){
                Socket socket=new Socket("3.88.145.60",8081);
                System.out.println("Connected to local client");
            }
        }
        catch(Exception e)
        {
            System.out.println(e);
        }


    }
}