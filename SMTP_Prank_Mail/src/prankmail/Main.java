package prankmail;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import java.io.IOException;

public class Main {
    public static String MSG_PATH = "src/sources/messages.txt";
    public static String DST_PATH = "src/sources/destinataires.txt";
    public static void main(String[] args)  throws IOException {
	    ClientSMTP client = new ClientSMTP("localhost", 25);
	    /*try{
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
	    client.setupPranks(DST_PATH, MSG_PATH);
	    client.connect();
	    client.initiateComWithSmtpServer();
	    client.sendingPranks();
	    client.disconnect();
    }
}
