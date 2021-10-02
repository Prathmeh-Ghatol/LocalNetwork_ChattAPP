package com.company;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.*;
import java.net.*;
class Client extends JFrame {
    Socket socket;
    BufferedReader br;
    PrintWriter out;
    //compont decleartiomn;
    private JLabel heading =new JLabel("Client area");
    private  JTextArea message_Area=new JTextArea();
    private JTextField message_Input=new JTextField();
    private Font font=new Font("Roboto",Font.PLAIN,20);



    public Client() {
        try {

            System.out.println("Sending request to server.......");
            socket = new Socket("127.0.0.1", 3500);
            System.out.println("Connection Establish.");
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream());
                createGUI();
                EventHandel();

            startReading();
//            startWriteing();

        } catch (Exception e) {

        }
    }

    private void EventHandel() {
        message_Input.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {
                System.out.println("KeyRelsed"+e.getKeyCode());
                if(e.getKeyCode()==10){
                    //System.out.println("you pressed enter");
                    String contentToSend=message_Input.getText();
                    System.out.println("\n");
                    message_Area.append("\n"+ "Me :  " +contentToSend);
                    out.println(contentToSend);
                    out.flush();
                    message_Input.setText("");
                    message_Input.requestFocus();

                }

            }
        });


    }

    private void createGUI(){
        this.setTitle("Client  Messenger[END]");
        this.setSize(600,700);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        // coding for compont
        heading.setFont(font);
        message_Area.setFont(font);
        message_Input.setFont(font);
      // heading.setIcon(new ImageIcon("clone.png"));
        heading.setHorizontalTextPosition(SwingConstants.CENTER);
         heading.setVerticalTextPosition(SwingConstants.BOTTOM);

         heading.setHorizontalAlignment(SwingConstants.CENTER);

         heading.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        message_Area.setEnabled(false);
        message_Input.setHorizontalAlignment(SwingConstants.CENTER);
        this.setLayout(new BorderLayout());
        // creation set of feild in frame;
        this.add(heading,BorderLayout.NORTH);
        JScrollPane jScrollPane=new JScrollPane(message_Area);
        this.add(jScrollPane,BorderLayout.CENTER);
        this.add(message_Input,BorderLayout.SOUTH);




    }

    public void startReading() {
        //thereding
        Runnable r1 = () -> {

            System.out.println("reader started");
            try {
            while (true) {



                    String msg = br.readLine();
                    if (msg.equals("exit")) {
                        System.out.println("server terminated the chatt");
                        JOptionPane.showMessageDialog(this,"server terminated chatt");
                        message_Input.setEnabled(false);

                        socket.close();


                        break;
                    }

                   message_Area.append("Server : " + msg + " \n ") ;
                }

            }catch (Exception e)
            {
                System.out.println("Connection is closed");
            }

        };
        new Thread(r1).start();
    }


    public void startWriteing() {
        Runnable r2 = () -> {
            System.out.println("writer start");
            try {
            while (true && !socket.isClosed()) {

                    BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));
                    String content = br1.readLine();
                    out.println(content);
                    out.flush();
                if(content.equals("exit")){
                    socket.close();
                    break;
                }
                }

                System.out.println("Connection is closed");
            }catch (Exception e) {
                e.printStackTrace();
            }
        };

        new Thread(r2).start();

    }







    public static void main(String[] args) {
        System.out.println("This is client....");
        new Client();

    }
}
