package prankmail;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Created by Adrian on 08.04.2017.
 */
public class ClientSMTP {
    private final int serverPort;
    private final String server;
    private Socket socket;
    private BufferedReader br;
    private PrintWriter pw;
    private ArrayList<String>[] groups;
    private ArrayList<String> senders;
    private ArrayList<String> messages;
    private ArrayList<String> subjects;


    public ClientSMTP(String server, int serverPort){
        this.server = server;
        this.serverPort = serverPort;
    }

    public void connect() throws IOException{
        socket = new Socket(server, serverPort);
        br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        pw = new PrintWriter(socket.getOutputStream(), true);

        System.out.println("server says: " + br.readLine());
    }

    public void initiateComWithSmtpServer() throws IOException{
        pw.println(ClientCommands.EHLO);
        boolean notLastLine = true;
        String response = new String();
        while(notLastLine){
            response = br.readLine();
            System.out.println("server says: " + response);

            if(response.contains(ServerResponse.CAN_START))
                notLastLine = false;
        }
        System.out.println("SMTP_Prank_Mail says: Command EHLO OK, can start communication");
    }

    public void setupPranks(String pathRcpt, String pathMes){
        PrankManager pm = new PrankManager(pathRcpt, pathMes);
        int nGroups = 0;
        String senderMail;
        System.out.println("SMTP_Prank_Mail says: Starting configurations...");
        System.out.println("How many groups of victims do yo want to set?");
        Scanner in = new Scanner(System.in);
        try{
            nGroups = in.nextInt();
        }catch(InputMismatchException e){
            System.out.println("Please only choose integers input.");
            System.out.println("Groups set to min (1)");
            nGroups = 1;
            in.nextLine();
        }
        if(nGroups < 1){
            System.out.println("Groups set to min (1)");
            nGroups = 1;
        }
        groups = new ArrayList[nGroups];
        for (int i = 0; i < nGroups; i++) {
            groups[i] = new ArrayList<String>();
            System.out.println("Choose in the following list the members of group " + (i+1) +" type 0 when it's done");
            for (int j = 0; j < pm.rcpt_size(); j++) {
                System.out.println((j+1) + ": " + pm.rcpt_at(j));
            }
            int choice = -1;
            int choosen = 0;
            while(true){
                try{
                    choice = in.nextInt();
                }catch(InputMismatchException e){
                    System.out.println("Please only choose integers input.");
                    in.nextLine();
                    choice = -1;
                }
                if(choice > pm.rcpt_size() || choice < 0)
                    System.out.println("Not a good input");
                else if(choice == 0 && choosen < 2)
                    System.out.println("You have to choose 2 recipients min per group");
                else if(choice == 0 && choosen >= 2)
                    break;
                else{
                    groups[i].add(pm.rcpt_at(choice - 1));
                    System.out.println(pm.rcpt_at(choice - 1) + " added");
                    choosen++;
                }
            }
        }
        senders = new ArrayList<String>();
        in.nextLine();
        for (int i = 0; i < nGroups ; i++) {
            System.out.println("Type the e-mail address of the sender for the group " + (i+1));
            senders.add(in.nextLine());
        }
        messages = new ArrayList<String>();
        subjects = new ArrayList<String>();
        System.out.println("Choose in the following list the message that will be send...");
        for (int j = 0; j < pm.msg_size(); j++) {
            System.out.println("MESSAGE NO " + (j+1));
            System.out.println("Subject: " +pm.subj_at(j));
            System.out.println(pm.msg_at(j));
        }
        for (int i = 0; i < nGroups; i++) {
            System.out.println("which one to group " + (i+1));
            int msgChoice = -1;
            while(true){
                msgChoice = in.nextInt();
                if(msgChoice < 1 || msgChoice > pm.msg_size())
                    System.out.println("Not a good input");
                else{
                    messages.add(pm.msg_at(msgChoice - 1));
                    subjects.add(pm.subj_at(msgChoice -1));
                    break;
                }
            }
        }
    }

    public void sendingPranks() throws IOException{
        for (int i = 0; i < groups.length ; i++) {
            System.out.println("Sending from: " + senders.get(i));
            pw.println(ClientCommands.MF + senders.get(i));
            System.out.println("server says: " + br.readLine());
            String to = "";
            for (int j = 0; j < groups[i].size(); j++) {
                System.out.println("Sending rcpt to: " + groups[i].get(j));
                pw.println(ClientCommands.RT + groups[i].get(j));
                System.out.println("server says: " + br.readLine());
                to = to.concat(groups[i].get(j));
                if((j+1) != groups[i].size())
                    to = to.concat(", ");
            }
            System.out.println("Sending data");
            pw.println(ClientCommands.DATA);
            System.out.println("server says: " + br.readLine());
            System.out.println("Sending mail body");
            pw.println(ClientCommands.FROM + senders.get(i));
            pw.println(ClientCommands.TO + to);
            pw.println(ClientCommands.SUB + subjects.get(i));
            pw.println(messages.get(i));
            pw.println(".");
            System.out.println("server says: " + br.readLine());
        }
    }

    public void disconnect() throws IOException{
        pw.println(StringSMTPFormatter.format(ClientCommands.QUIT));
        System.out.println("server says: " + br.readLine());
        br.close();
        pw.close();
        socket.close();
        System.out.println("SMTP_Prank_Mail says: Connection to server " + server +" port " +serverPort + " closed.");
    }
}
