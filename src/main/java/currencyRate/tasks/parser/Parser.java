package currencyRate.tasks.parser;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public final class Parser {

    public static String getTodayDateYMD() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date todayDate = new java.util.Date();
        return dateFormat.format(todayDate);
    }

    public static String getTodayDateMDY() {
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        java.util.Date todayDate = new java.util.Date();
        return dateFormat.format(todayDate);
    }

    public static String getChangedDate(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(date);
    }

    public static String getInputText(String urlFromBank) {
        String inputLine = "";
        try {
            URL url = new URL(urlFromBank);
            URLConnection connection = url.openConnection();
            BufferedReader input = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            inputLine = input.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return inputLine;
    }

    public static double getValueFromNBRB(String date, String typeCurrency) {
        double valueCurrency = 0;
        try {

            String urlFromBank = "https://www.nbrb.by/api/exrates/rates/" + typeCurrency + "?parammode=2&ondate=" + date;
            URL url = new URL(urlFromBank);
            URLConnection connection = url.openConnection();
            BufferedReader input = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine = input.readLine();

            JSONObject obj = (JSONObject) JSONValue.parse(inputLine);
            valueCurrency = (Double) obj.get("Cur_OfficialRate");

        } catch (IOException e) {
            e.printStackTrace();
        }
        return valueCurrency;
    }

    public static String getBestValue(List<String> values, String currencySelect) {
        String value = "";
        double bestValue = 0;
        for (String val : values) {
            if (currencySelect.equalsIgnoreCase("продажа")
                    && (Double.parseDouble(val) < bestValue)) {
                value = val;
            } else if (currencySelect.equalsIgnoreCase("покупка")
                    && (Double.parseDouble(val) > bestValue)) {
                value = val;
            } else if (currencySelect.equalsIgnoreCase("продажа")
                    && (bestValue == 0)) {
                value = val;
                bestValue = Double.parseDouble(value);
            }
        }
        return value;
    }
}