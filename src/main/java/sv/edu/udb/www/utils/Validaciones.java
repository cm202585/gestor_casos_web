package sv.edu.udb.www.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validaciones {

    private static int entero;
    private static double decimal;
    private static String cadena;

    public static boolean isEmpty(String mensaje) {
        return mensaje.trim().equals("");
    }

    public static boolean esEnteroPositivo(String cadena) {
        try {
            entero = Integer.parseInt(cadena.trim());
            if (entero <= 0) {
                return false;
            }
            return true;
        } catch (Exception a) {
            return false;
        }
    }

    public static boolean esCodigoUsuario(String codigoUsuario){
        Pattern pattern = Pattern.compile("^[a-z]{2}\\d{6}$");
        Matcher matcher = pattern.matcher(codigoUsuario);
        return matcher.matches();
    }

    public static boolean esTelefono(String cadena) {
        Pattern pattern = Pattern.compile("[0-9]{4}-[0-9]{4}");
        Matcher matcher = pattern.matcher(cadena);
        return matcher.matches();
    }

    public static boolean esCorreo(String correo){
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
        Matcher matcher = pattern.matcher(correo);
        return matcher.matches();
    }

    public static boolean esDui(String dui){
        Pattern pattern = Pattern.compile("^\\d{8}-\\d{1}$");
        Matcher matcher = pattern.matcher(dui);
        return matcher.matches();
    }
}