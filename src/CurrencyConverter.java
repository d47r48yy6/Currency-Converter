import netscape.javascript.JSObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.HashMap;
import org.json.JSONObject;

import java.util.Scanner;

public class CurrencyConverter {
    public static void main(String[] args) throws IOException
    {
        boolean running =true;
        do
        {
            HashMap<Integer ,String > currencyCodes = new HashMap<Integer ,String >();

            // Add a currency Codes
            currencyCodes.put(1, "USD");
            currencyCodes.put(2, "CAD");
            currencyCodes.put(3, "HKD");
            currencyCodes.put(4, "INR");

            int to , from;

            String fromCode , toCode ;
            double amount ;

            Scanner sc= new Scanner(System.in);

            System.out.println("***** Welcome to Currency Converter *****");
            System.out.println("Currency Converting From ? ");
            System.out.println("1: USD (US Dollar) \t 2:CAD (Canadian Dollar) \t 3:EUR (Euro )\t 4:HKD (HongKong dollar) \t5:INR (Indian Rupees)");
            from = sc.nextInt();
            while (from < 1 || from > 5)
            {
                System.out.println("Please Enter The Valid Currency (1-5) ");
                System.out.println("1: USD (US Dollar) \t 2:CAD (Canadian Dollar) \t 3:EUR (Euro )\t 4:HKD (HongKong dollar) \t5:INR (Indian Rupees)");
                from =sc.nextInt();
            }
            fromCode =currencyCodes.get(from);

            System.out.println("Currency Converting To ?");
            System.out.println("1: USD (US Dollar) \t 2:CAD (Canadian Dollar) \t 3:EUR (Euro )\t 4:HKD (HongKong dollar) \t5:INR (Indian Rupees)");

            to = sc.nextInt();
            while (to < 1 || to > 5)
            {
                System.out.println("Please Enter The Valid Currency (1-5) ");
                System.out.println("1: USD (US Dollar) \t 2:CAD (Canadian Dollar) \t 3:EUR (Euro )\t 4:HKD (HongKong dollar) \t5:INR (Indian Rupees)");
                to =sc.nextInt();
            }
            toCode =currencyCodes.get(to);
            System.out.println("Amount You Wish to Convert ");
            amount=sc.nextFloat();

            sendHttpGETRequest(fromCode ,toCode ,amount);

            System.out.println("Would you like to make Another Conversion ?");


            System.out.println("Press 1: yes \t Press :Any Interger for 'NO'");
            if(sc.nextInt()!=1)
            {
                running =false;
            }

        }while (running);
        System.out.println("Thank you for Using Currency Converter");

    }

    public static void sendHttpGETRequest (String fromCode ,String toCode , double amount ) throws IOException {
        DecimalFormat f= new DecimalFormat("00.00");
        String GET_url="https://api.exchangerate.host/latest?base="+ toCode + "&symbols=" + fromCode ;
        URL url=new URL(GET_url);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod("GET");
        int responsecode =httpURLConnection.getResponseCode();

        if(responsecode==HttpURLConnection.HTTP_OK)
        {
            BufferedReader in= new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            String inputLine ;
            StringBuffer response =new StringBuffer();

            while ((inputLine =in.readLine()) != null)
            {
                response.append(inputLine);

            }in.close();

            JSONObject obj = new JSONObject(response.toString());
            Double exchangeRate = obj.getJSONObject("rates").getDouble(fromCode);
            System.out.println(obj.getJSONObject("rates"));
            System.out.println(exchangeRate); // KEEP FOR DEBUGGING
            System.out.println();
            System.out.println(f.format(amount) + fromCode + " = " + f.format(amount/exchangeRate) + toCode);


        }
        else {
            System.out.println("GET Request failed  !!");
        }

    }

}
