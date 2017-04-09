package prankmail;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import java.io.IOException;

public class Main {
    public static final String MSG_PATH = "src/sources/messages.txt";
    public static final String DST_PATH = "src/sources/destinataires.txt";
    public static final String SERVER = "localhost";
    public static final int PORT = 25;

    public static void main(String[] args)  throws IOException {
	    ClientSMTP client = new ClientSMTP(SERVER, PORT);
	    client.setupPranks(DST_PATH, MSG_PATH);
	    client.connect();
	    client.initiateComWithSmtpServer();
	    client.sendingPranks();
	    client.disconnect();
    }
}
