package com.fairinna;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.JOptionPane;

/**
 * Created by Inna on 11/17/2018.
 */
public class ServerChatMain {
    public static ArrayList<Socket> connectionsArray = new ArrayList<>();
    public  static  ArrayList<String> currentUser = new ArrayList<>();



    public static void main(String[] args) throws IOException {
        ServerSocket serverChat = null;
        Server_Chat_return chat =null;
        try {
            final int PORT = 7777;

             serverChat = new ServerSocket(PORT);

            System.out.println("Waiting new users to chat... ");
            while (true){
                Socket soket = serverChat.accept();
                connectionsArray.add(soket);

                System.out.println("Client connection from "+ soket.getLocalAddress().getHostName());

                AddUserName(soket);
                 chat = new Server_Chat_return(soket);

                Thread X = new Thread(chat);
                X.start();
            }

        } catch (Exception X) {
            System.out.println(X);
            X.printStackTrace();
        }finally {
            if (serverChat != null) {
                serverChat.close();

            }
        }

    }
    public static void AddUserName (Socket X) {
        Scanner in = null;
        String userName = "";
        PrintWriter out = null;

        try {

            in = new Scanner(X.getInputStream());
            if(in.hasNext()){
            userName = in.nextLine();
            currentUser.add(userName);

            testArrayConnectionSocket();

            for (int i = 1; i <= ServerChatMain.connectionsArray.size(); i++) {

                Socket temp_sock = ServerChatMain.connectionsArray.get(i - 1);


                System.out.println();
                out = new PrintWriter(temp_sock.getOutputStream());
                out.println("#?!" + currentUser);
                out.flush();
            }
                testArrayConnectionSocket();
            }
        }catch (IOException e){
            e.getCause();
            e.printStackTrace();

        }
        catch (Exception Xe){
            Xe.printStackTrace();
        }



    }
    private static void testArrayConnectionSocket(){
        for (Socket element:ServerChatMain.connectionsArray) {
            System.out.println(element);
        }
    }
}

