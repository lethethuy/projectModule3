package ra.config;

public class Message {
    public static String getStatusByCode(byte code){
        switch (code){
            case 1 :
                return "Confirming";
            case 2:
                return "Accepted";
            case 3:
                return "Canceled";
            default:
                return  "Invalid";
        }
    }
}
