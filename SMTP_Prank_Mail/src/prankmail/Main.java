package prankmail;

import java.io.IOException;

public class Main {
    public static String MSG_PATH = "src/sources/messages.txt";
    public static String DST_PATH = "src/sources/destinataires.txt";
    public static void main(String[] args)  {
	    /*ClientSMTP client = new ClientSMTP("localhost", 25);
	    try{
	        client.connect();
	    }catch(IOException e){
	        System.out.println("La connexion au serveur a échoué.");
	        System.exit(1);
        }

        try{
	    	client.initiateComWithSmtpServer();
	    	client.disconnect();
		}catch(IOException e){
			System.out.println("Problème lors de la communication avec le serveur");
			System.exit(1);
		}*/
	    PrankManager pm = new PrankManager(DST_PATH, MSG_PATH);
	    System.out.println(pm.fksn_at(0));
        System.out.println(pm.rcpt_at(2));
        System.out.println(pm.msg_at(1));
        System.out.println(pm.msg_at(0));
    }
}
