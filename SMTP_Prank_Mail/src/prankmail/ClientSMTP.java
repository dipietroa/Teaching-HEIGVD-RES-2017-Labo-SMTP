package prankmail;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by Adrian on 08.04.2017.
 */
public class ClientSMTP {
    private final int serverPort;
    private final String server;
    private Socket socket;
    private BufferedReader br;
    private PrintWriter pw;

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

    public void disconnect() throws IOException{
        pw.println(StringSMTPFormatter.format(ClientCommands.QUIT));
        System.out.println("server says: " + br.readLine());
        br.close();
        pw.close();
        socket.close();
        System.out.println("SMTP_Prank_Mail says: Connection to server " + server +" port " +serverPort + " closed.");
    }
}
