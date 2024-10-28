package com.example;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws UnknownHostException, IOException {
        Socket socket = new Socket("localhost", 3000);
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            String nota;
            String fraseServer;
            boolean presente = false;
            Scanner sc = new Scanner(System.in);

            do {
                System.out.println("inserire il proprio username:");
                String username = sc.nextLine();
                out.writeBytes(username + "\n");
                String apprUsername = in.readLine();

                if(apprUsername.equals("n")){
                    System.out.println("username non disponibile");
                    presente = true;
                }
                else{
                    System.out.println("username disponibile");
                    presente = false;
                }
            } while (presente);
            
            do {
                
                if(!presente){
                    System.out.println("Scrivere la nota (scrivere exit per uscire, visualizza per vedere tutte le note,cancella per eliminare una nota e condivisibile per aggiungere una nota per tutti)");

                    nota = sc.nextLine();

                    if(nota.equals("exit")){
                        out.writeBytes("!" + "\n");
                    }
                    else if(nota.equals("visualizza")){
                        out.writeBytes("?" + "\n");

                        do {
                            fraseServer =  in.readLine();
                            System.out.println(fraseServer);
                            
                        } while (!fraseServer.equals("@"));
                    }
                    else if(nota.equals("cancella")){
                        out.writeBytes("$" + "\n");
                        System.out.println("Quale nota vuoi cancellare fra queste? (inserire la stringa da cancellare)");
                        do {
                            fraseServer =  in.readLine();
                            System.out.println(fraseServer);
                            
                        } while (!fraseServer.equals("@"));

                        String strCancella = sc.nextLine();
                        out.writeBytes(strCancella + "\n");
                        String trovatoCanc = in.readLine();
                        if(trovatoCanc.equals("n")){
                            System.out.println("stringa non trovata");
                        }
                        else{
                            System.out.println("stringa eliminata");
                        }
                    }
                    else{
                        out.writeBytes(nota + "\n");

                        fraseServer =  in.readLine();
                        System.out.println(fraseServer);
                    }
                }
                else{
                    nota = "exit";
                    out.writeBytes("!" + "\n");
                }
            } while (!nota.equals("exit"));
            



        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}