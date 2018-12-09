package com.fairinna;
import  javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.*;


/**
 * Created by Inna on 11/18/2018.
 */
public class ClientChatGUI {
   // final  Color COLOR_LIGH_GREEN = new Color(49, 222, 155);

    //GLOBALS
    private static ClientChat clientChat;
    public static String userName = "Anonymous";

    //GUI Globals - main window;

    public static JFrame mainWindow = new JFrame();
    private static JButton b_About = new JButton();
    private static JButton b_Connect = new JButton();
    private static JButton b_Disconnect = new JButton();
    private static JButton b_Help = new JButton();
    private static JButton b_Send = new JButton();
    private static JLabel l_Message = new JLabel("Message: ");
    public static JTextField tf_Message = new JTextField(20);
    private static JLabel l_Conversation = new JLabel();
    public  static JTextArea ta_conversation = new JTextArea();
    private static  JScrollPane sp_conversation = new JScrollPane();
    private  static JLabel l_online = new JLabel();
    public static JList jl_online = new JList();
    private static JScrollPane sp_online = new JScrollPane();
    private static JLabel l_loggedInAs = new JLabel();
    private static JLabel l_loggedInAsBox = new JLabel();

    //GUI globals - Login window
    public static JFrame logInWindow = new JFrame();
    public static JTextField tf_userNameBox = new JTextField(20);
    private static JButton b_Enter = new JButton("ENTER");
    private static JLabel l_EnterUserName = new JLabel("Enter username: ");
    private static JPanel p_LogIn = new JPanel();

    public static void main(String[] args) {
buildMainWindow();
initialize();

    }
    //--------------------------------------CONNECT()___________________________________
public static  void connect(){
        try{
            final int PORT = 7777;
            final String HOST = "127.0.0.1"; //blumix;
            Socket socket = new Socket(HOST, PORT);
            System.out.println("You connection to HOST: " + HOST);

            clientChat = new ClientChat(socket);

            //Send name to add to "online" list;
            PrintWriter out = new PrintWriter(socket.getOutputStream());
            out.println(userName);
            out.flush();
            Thread x = new Thread(clientChat);
            x.start();

        }  catch (Exception X){
            System.out.print(X);
            JOptionPane.showMessageDialog(null,"Server not responding...");
            System.exit(0);
        }
}
public static void initialize(){
    b_Send.setEnabled(false);
    b_Connect.setEnabled(true);
    b_Disconnect.setEnabled(false);
    b_Help.setEnabled(true);


}
public  static void buildLoginWindow(){
    logInWindow.setTitle(" * What's your name? * ");
    logInWindow.setSize(400,100);
    logInWindow.setLocation(250,200);
    logInWindow.setResizable(false);
    p_LogIn = new JPanel();
    p_LogIn.add(l_EnterUserName);
    p_LogIn.add(tf_userNameBox);
    p_LogIn.add(b_Enter);
    logInWindow.add(p_LogIn);

     loginAction();
    logInWindow.setVisible(true);
}

public static void buildMainWindow(){
    mainWindow.setTitle(userName.trim()+"'s Chat Box");
    mainWindow.setSize(450,500);
    mainWindow.setLocation(220,180);
    mainWindow.setResizable(false);
    configureMainWindow();
    mainWindow_Action();
    mainWindow.setVisible(true);

}

public static void configureMainWindow(){
    mainWindow.setBackground(Color.LIGHT_GRAY);
    mainWindow.setSize(500,320);
    mainWindow.getContentPane().setLayout(null);

//button SEND
    b_Send.setBackground(new Color(49, 222, 155));
    b_Send.setForeground(new Color(89,89,89));
    b_Send.setText("SEND");
    mainWindow.getContentPane().add(b_Send);
    b_Send.setBounds(250,40,81,25);

// button DISCONNECT

    b_Disconnect.setBackground(new Color(255,222,155));
    b_Disconnect.setForeground(new Color(199,89,89));
    b_Disconnect.setText("DISCONNECT");
    mainWindow.getContentPane().add(b_Disconnect);
    b_Disconnect.setBounds(10,40,110,25);

    //button CONNECT

    b_Connect.setBackground(new Color(199,89,89));
    b_Connect.setForeground(new Color(255,255,155));
    b_Connect.setText("CONNECT");
    b_Connect.setToolTipText("");
    mainWindow.getContentPane().add(b_Connect);
    b_Connect.setBounds(130, 40,110,25);

// button HELP

    b_Help.setBackground(new Color(123,123,153));
    b_Help.setForeground(new Color(250,250,250));
    b_Help.setText("HELP");
   mainWindow.getContentPane().add(b_Help);
    b_Help.setBounds(420,40,70,25);

    // button About
    b_About.setBackground(new Color(123,123,153));
    b_About.setForeground(new Color(250,250,250));
    b_About.setText("ABOUT");
    mainWindow.getContentPane().add(b_About);
    b_About.setBounds(340,40,75,25);

    // GUI Messanger

    l_Message.setText("Message: ");
    mainWindow.getContentPane().add(l_Message);
    l_Message.setBounds(10,10,60,20);

    tf_Message.setForeground(new Color(0,0,255));
    tf_Message.requestFocus();
    mainWindow.getContentPane().add(tf_Message);
    tf_Message.setBounds(70,4,260,30);


    l_Conversation.setHorizontalAlignment(SwingConstants.CENTER);
    l_Conversation.setText("Conversation");
    mainWindow.getContentPane().add(l_Conversation);
    l_Conversation.setBounds(100,70,140,16);

    ta_conversation.setColumns(20);
    ta_conversation.setFont(new Font("Verdana",0,12));
    ta_conversation.setForeground(new Color(89,89,89));
    ta_conversation.setLineWrap(true);
    ta_conversation.setRows(5);
    ta_conversation.setEditable(false);

    sp_conversation.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
    sp_conversation.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
    sp_conversation.setViewportView(ta_conversation);
    mainWindow.getContentPane().add(sp_conversation);
    sp_conversation.setBounds(10,90,330,180);

    l_online.setHorizontalAlignment(SwingConstants.CENTER);
    l_online.setText("Currently Online");
    l_online.setToolTipText("");
    mainWindow.getContentPane().add(l_online);
    l_online.setBounds(350,70,130,16);


   // String[] testName = {"Inna", "Elad","Somebody"};
    jl_online.setForeground(new Color(69,69,69));
 //   jl_online.setListData(testName);

    sp_online.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
    sp_online.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
    sp_online.setViewportView(jl_online);
    mainWindow.getContentPane().add(sp_online);
    sp_online.setBounds(350,90,130,180);

    l_loggedInAs.setFont(new Font("Verdana", 0,12));
    l_loggedInAs.setText("Currently Logged in Us");
    mainWindow.getContentPane().add(l_loggedInAs);
    l_loggedInAs.setBounds(348,0,140,15);

    l_loggedInAsBox.setHorizontalAlignment(SwingConstants.CENTER);
    l_loggedInAsBox.setFont(new Font("Verdana", 0,12));
    l_loggedInAsBox.setForeground(new Color(68,68,68));
    l_loggedInAsBox.setBorder(BorderFactory.createLineBorder(new Color(49,222,155)));
    mainWindow.getContentPane().add(l_loggedInAsBox);
    l_loggedInAsBox.setBounds(340,17,150,20);


}

//--------------------------------LOGINACTION()___________________________________________


public  static void loginAction(){
    b_Enter.addActionListener(e -> action_b_Enter());
}
// ----------------------------ACTION_B_ENTER()________________________________


public static void action_b_Enter(){
    if(!tf_userNameBox.getText().equals("")){
        userName = tf_userNameBox.getText().trim();
        l_loggedInAsBox.setText(userName);
        ServerChatMain.currentUser.add(userName);
        mainWindow.setTitle(userName+"'s Chat Box");
        logInWindow.setVisible(false);
        b_Send.setEnabled(true);
        b_Connect.setEnabled(false);
        b_Disconnect.setEnabled(true);
        connect();
    }else {
        JOptionPane.showMessageDialog(null,"Please, enter a name!");
    }
}
//-------------------MAINWINDOW_ACTION()_____________________________________


public static void mainWindow_Action(){
    b_Send.addActionListener(e -> action_b_Send());
    b_Disconnect.addActionListener(e -> action_b_Disconnect());
    b_Connect.addActionListener(e -> buildLoginWindow());

    b_About.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            // --> action_b_About();
        }
    });
    b_Help.addActionListener(e -> action_b_Help());

}


//----------------------------------ACTION_B_SEND()_________________________________

public static void action_b_Send(){
    if(!tf_Message.getText().equals("")){
      clientChat.send(tf_Message.getText());
        tf_Message.requestFocus();
    }
}

//---------------------------------ACTION_B_DISCONECT()_______________________

public static void action_b_Disconnect(){
    try {
        clientChat.disconnect();
    } catch (IOException e) {
        e.printStackTrace();
    }catch (Exception y){
        y.printStackTrace();
    }
}
//--------------------------------ACTION_B_HELP()_______________
    public static void action_b_Help(){
    JOptionPane.showMessageDialog(null, "CHAT \" Fairinna \" \n Israel 2018 ");
    }
}
