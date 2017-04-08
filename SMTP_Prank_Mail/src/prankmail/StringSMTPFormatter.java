package prankmail;

/**
 * Created by Adrian on 08.04.2017.
 */
public abstract class StringSMTPFormatter {
    public static String format(String s){
        return s.concat("\r\n");
    }
    public static String empty(){
        return "\r\n";
    }
}
