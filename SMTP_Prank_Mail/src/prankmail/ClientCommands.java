package prankmail;

/**
 * Created by Adrian on 08.04.2017.
 */
public abstract class ClientCommands {
    public final static String QUIT = "quit";
    public final static String EHLO = "EHLO smtppm.com";
    public final static String MF = "MAIL FROM: ";
    public final static String RT = "RCPT TO: ";
    public final static String FROM = "From: ";
    public final static String TO = "To: ";
    public final static String SUB = "Subject: ";
    public final static String DATA = "DATA";
}
