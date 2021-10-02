package com.company;
import java.net.*;
import java.io.*;
import java.lang.*;

 class Server {
    ServerSocket server;
    Socket socket;
    BufferedReader br;
    PrintWriter out;
    public Server(){
        try {
            server = new ServerSocket(3500);
            System.out.println("server is redy to accept connection");
            System.out.println("Waiting.....");
            socket=server.accept();
            br=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out=new PrintWriter(socket.getOutputStream());

            startReading();
            startWriteing();

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void startReading() {
        //thereding-read-karke denga
        Runnable r1 = () ->{

            System.out.println("reader started");
            try {
            while (true && !socket.isClosed()) {

                    String msg = br.readLine();
                    if (msg.equals("exit")) {
                        System.out.println("client terminated the chatt");
                        socket.close();
                        break;
                    }

                    System.out.println("Client :" + msg);
                }
            }catch (Exception e)
            {
                e.printStackTrace();
            }

        };
        new Thread(r1).start();
    }


    public void startWriteing(){
        Runnable r2=()->{
            System.out.println("writer start");
            try {
            while (true){

                    BufferedReader br1=new BufferedReader(new InputStreamReader(System.in));
                    String content =br1.readLine();
                    out.println(content);
                    out.flush();
                    if(content.equals("exit")){
                        socket.close();
                        break;
                    }

                }
                }catch (Exception e){

                e.printStackTrace();
            }
        };

        new Thread(r2).start();


    }


    public static void main(String[] args) {
        System.out.println("this is server..going  to start server ");
        new Server();


    }
}
