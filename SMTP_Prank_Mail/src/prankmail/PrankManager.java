package prankmail;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Adrian on 08.04.2017.
 */
public class PrankManager {
    private ArrayList<String> recipients;
    private ArrayList<String> messages;
    private ArrayList<String> subjects;
    private ArrayList<String> fakesenders;

    public PrankManager(String pathRcpt, String pathMes){
        recipients = new ArrayList<String>();
        messages = new ArrayList<String>();
        subjects = new ArrayList<String>();
        fakesenders = new ArrayList<String>();

        File rcpt = new File(pathRcpt);
        File mes = new File(pathMes);

        try{
            setRecipients(rcpt);
        }catch (IOException e){
            System.out.println("SMTP_Prank_Mail says: File " + rcpt.getAbsolutePath() + " not found.");
            System.exit(1);
        }

        try{
            setMessages(mes);
        }catch (IOException e){
            System.out.println("SMTP_Prank_Mail says: File " + mes.getAbsolutePath() + " not found.");
            System.exit(1);
        }
    }

    public void setRecipients(File rcpt) throws IOException{
        Scanner sc = new Scanner(new FileInputStream(rcpt));

        while(sc.hasNextLine()){
            recipients.add(sc.nextLine());
        }

        sc.close();
    }

    public void setMessages(File mes) throws IOException{
        Scanner sc = new Scanner(new FileInputStream(mes));

        while(sc.hasNextLine()){
            fakesenders.add(sc.nextLine());
            subjects.add(sc.nextLine());

            String message = "";
            String nextLine = "";
            while(!nextLine.contains("ENDOFMSAGE")){
                message = message.concat(nextLine);
                message += System.lineSeparator();
                nextLine = sc.nextLine();
            }
            messages.add(message);
        }

        sc.close();
    }
    public String msg_at(int index){
        if(index < messages.size())
            return messages.get(index);
        return null;
    }
    public String rcpt_at(int index){
        if(index < recipients.size())
            return recipients.get(index);
        return null;
    }
    public String subj_at(int index){
        if(index < subjects.size())
            return subjects.get(index);
        return null;
    }
    public String fksn_at(int index){
        if(index < fakesenders.size())
            return fakesenders.get(index);
        return null;
    }
    public int msg_size(){
        return messages.size();
    }
    public int rcpt_size(){
        return recipients.size();
    }
    public int subj_size(){
        return subjects.size();
    }
    public int fksn_size(){
        return fakesenders.size();
    }


}
