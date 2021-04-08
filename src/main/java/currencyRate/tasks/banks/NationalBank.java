package currencyRate.tasks.banks;

import currencyRate.tasks.parser.Parser;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Component
@PropertySource("classpath:banks.properties")
public class NationalBank {

    @Value("${url.nbrb.usd}")
    private String urlUSD;

    @Value("${url.nbrb.eur}")
    private String urlEUR;

    @Value("${url.nbrb.rub}")
    private String urlRUB;

    private String jsonText(String url) {
        return Parser.getInputText(url);
    }

    private String today() {
        LocalDate today = LocalDate.now();
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return dateFormat.format(today);
    }

    private String selectDate(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(date);
    }

    private JSONObject getJson(String urlWithType, String date) {
        String url = urlWithType + date;
        return (JSONObject) JSONValue.parse(jsonText(url));
    }

    public String getValueUSD() {
        return getValue(getJson(urlUSD, today()));
    }

    public String getValueEUR() {
        return getValue(getJson(urlEUR, today()));
    }

    public String getValueRUB() {
        return getValue(getJson(urlRUB, today()));
    }

    private String getValue(JSONObject jsonObject) {
        return jsonObject.get("Cur_OfficialRate").toString();
    }
}
