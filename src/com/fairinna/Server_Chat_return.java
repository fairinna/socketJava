package com.fairinna;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by Inna on 11/17/2018.
 */
public class Server_Chat_return implements Runnable {
    //Globals
    Socket SOCKET = null;
    private Scanner INPUT;
    private PrintWriter OUTPUT;
    String MESSAGE ="";


    public Server_Chat_return(Socket X){
        this.SOCKET = X;
    }

    public void checkConnection () throws IOException
    {
        if(!SOCKET.isConnected())
        {
            for (int i = 1; i <=ServerChatMain.connectionsArray.size() ; i++)
            {
                if(ServerChatMain.connectionsArray.get(i-1) == SOCKET)
                {
                    ServerChatMain.connectionsArray.remove(i-1);
                }

            }
            for (int i = 1; i <=ServerChatMain.connectionsArray.size() ; i++) {

                Socket temp_soket = (Socket) ServerChatMain.connectionsArray.get(i-1);
                PrintWriter temp_out = new PrintWriter(temp_soket.getOutputStream());
                temp_out.println(temp_soket.getLocalAddress().getHostName() + " ! disconnected... ***Bye***");
                temp_out.flush();

                //Show disconnection at Server
                System.out.println(temp_soket.getLocalAddress().getHostName()+" disconnected ");

            }
        }

    }


    @Override
    public void run() {
        try {
            try {
                INPUT = new Scanner(SOCKET.getInputStream());
                OUTPUT = new PrintWriter(SOCKET.getOutputStream());
                while (true){
                    checkConnection();
                    if(!INPUT.hasNext()){
                        return;
                    }
                    MESSAGE = INPUT.nextLine();
                    //show message at Server
                    System.out.println("client said: " + MESSAGE);

                    //show message at clients

                    for (int i = 1; i <=ServerChatMain.connectionsArray.size() ; i++) {
                        Socket temp_soket = ServerChatMain.connectionsArray.get(i-1);
                        PrintWriter temp_out = new PrintWriter(temp_soket.getOutputStream());
                        temp_out.println(MESSAGE);
                        temp_out.flush();

                        System.out.println("Send to "+ temp_soket.getLocalAddress().getHostName());

                    }//close for loop

                }// close while loop
            } finally
            {
                if (SOCKET != null) {
                    SOCKET.close();
                }

            }

        }catch (Exception X){ System.out.print(X);}
    }
}
