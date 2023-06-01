import java.io.File;
import java.text.DecimalFormat;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Main {
    static double  val = 80;
    public static void main(String[] args) {
        File file = new File("file.txt");
        Scanner scan = new Scanner(System.in);
        while (true){
            String curCom = scan.nextLine();
            System.out.println(curCom);
            System.out.println(calculate(curCom));
        }
    }

    public static String toDollars(String string){
        if (Pattern.matches("[0-9]+.[0-9]{2}[p]",string) == true){
            Double number = Double.parseDouble(string.substring(0, string.indexOf('p')));
            if(number != 0){
                number /= val;
                String result = new DecimalFormat("#0.00").format(number);
                return "$" + result;
            }
            else {
                return "Convert error";
            }
        }
        else {
            return "Convert error";
        }
    }

    public static String toRubles(String string){
        if (Pattern.matches("[$][0-9]+.[0-9]{2}", string) == true) {
            Double number = Double.parseDouble(string.substring(1));
            number *= val;
            String result = new DecimalFormat("#0.00").format(number);
            return number + "p";
        }
        else {
            return "Convert error";
        }
    }

    public static String calculate(String string){
        if (!(Pattern.matches("(toDollars|toRubles)[(][(toDollars|toRubles)+.0-9$p ]+[)]", string)) == true ){
            return "String error";
        }
        String command = string.substring(0,string.indexOf('('));
        String arg = string.substring(string.indexOf('(')+1, string.lastIndexOf(')'));
        String[] divArg = arg.split(" ");
        String result = "";
        switch (command){
            case "toDollars":
                if (divArg.length == 1){
                    return toDollars(divArg[0]);
                }
                if (divArg.length %2 == 0){
                    return "Calculate error";
                }
                else {

                    for (int i = 0; i < divArg.length; i++){
                        if (Pattern.matches("(toDollars|toRubles)[(][(toDollars|toRubles)+.0-9$p ]+[)]", divArg[i]) == true ){
                            divArg[i] = calculate(divArg[i]);
                        }
                    }
                    result += divArg[0];
                    for (int i = 2; i < divArg.length; i += 2){
                        if (divArg[i-1].equals("+") == true){
                            result = sum(result, divArg[i]);
                        }
                        else {
                            result = diff(result, divArg[i]);
                        }
                    }
                    return toDollars(result);
                }
            case  "toRubles":
                if (divArg.length == 1){
                    return toRubles(divArg[0]);
                }
                if (divArg.length %2 == 0){
                    return "Calculate error";
                }
                else {

                    for (int i = 0; i < divArg.length; i++){
                        if (Pattern.matches("(toDollars|toRubles)[(][(toDollars|toRubles)+.0-9$p ]+[)]", divArg[i]) == true ){
                            divArg[i] = calculate(divArg[i]);
                        }
                    }
                    result += divArg[0];
                    for (int i = 2; i < divArg.length; i += 2){
                        if (divArg[i-1].equals("+") == true){
                            result = sum(result, divArg[i]);
                        }
                        else {
                            result = diff(result, divArg[i]);
                        }
                    }
                    return toRubles(result);
                }

            default:
                return "Calculate error";


        }
    }

    public static String sum(String str1, String str2){
        if (str1.charAt(0) == '$' && str2.charAt(0) == '$' ){
            Double d1 = Double.parseDouble(str1.substring(1));
            Double d2 = Double.parseDouble(str2.substring(1));
            Double result = d1+d2;
            String strResult = new DecimalFormat("#0.00").format(result);
            return "$"+strResult.replace(',','.');
        }
        if (str1.charAt(str1.length()-1) == 'p' && str2.charAt(str2.length()-1) == 'p'){
            Double d1 = Double.parseDouble((str1.substring(0,str1.indexOf("p"))));
            Double d2 = Double.parseDouble((str2.substring(0,str2.indexOf("p"))));
            Double result = d1 + d2;
            String strResult = new DecimalFormat("#0.00").format(result);
            return strResult.replace(',','.')+"p";
        }
        return "Sum error";
    }

    public static String diff(String str1, String str2){
        if (str1.charAt(0) == '$' && str2.charAt(0) == '$' ){
            Double d1 = Double.parseDouble(str1.substring(1));
            Double d2 = Double.parseDouble(str2.substring(1));
            Double result = d1-d2;
            String strResult = new DecimalFormat("#0.00").format(result);
            return "$"+strResult.replace(',','.');
        }
        if (str1.charAt(str1.length()-1) == 'p' && str2.charAt(str2.length()-1) == 'p'){
            Double d1 = Double.parseDouble((str1.substring(0,str1.indexOf("p"))));
            Double d2 = Double.parseDouble((str2.substring(0,str2.indexOf("p"))));
            Double result = d1 - d2;
            String strResult = new DecimalFormat("#0.00").format(result);
            return strResult.replace(',','.')+"p";
        }
        return "Diff error";
    }
}
