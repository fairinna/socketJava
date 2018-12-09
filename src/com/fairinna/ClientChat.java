package com.fairinna;

import javax.swing.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by Inna on 11/18/2018.
 */
public class ClientChat implements Runnable{
    Socket socket;
    Scanner input;
   // Scanner send = new Scanner(System.in);
    PrintWriter out;

    //------------------------------------Constructor_______________

    public ClientChat(Socket socket){  this.socket = socket;   }
    //------------------------------------------------------------------------
    @Override
    public void run() {
        try {
            try {
                input = new Scanner(socket.getInputStream());
                out = new PrintWriter(socket.getOutputStream());
                out.flush();
                 checkStream();
            } finally {
                        socket.close();
            }
        }catch (Exception X){ System.out.print(X);  }
    }
    //------------------------------------DISCONNECT()________________________________________

    public void disconnect() throws IOException{
        out.println(ClientChatGUI.userName+ " has disconnected.");
        out.flush();
        JOptionPane.showMessageDialog(null, " You disconnected! ");
        System.exit(0);
    }

    //----------------------------CHECKSTREAM()__________________
    public void checkStream(){
        while (true){
            receive();
        }
    }
    //------------------------RESEIVE()___________________________________
    public void receive(){
        if(input.hasNext()){
            String message = input.nextLine();
            if(message.contains("#?!")){
                String temp1 = message.substring(3);
                temp1 = temp1.replace("[","");
                temp1 = temp1.replace("]", "");

                String[] currentUsers = temp1.split(", ");
                ClientChatGUI.jl_online.setListData(currentUsers);

            }else {
                ClientChatGUI.ta_conversation.append(message +"\n");
            }
        }
    }
    //-------------------------------------SEND()_____________________________

    public void send(String str){
        out.println(ClientChatGUI.userName+": "+ str);
        out.flush();
        ClientChatGUI.tf_Message.setText("");

    }
}
