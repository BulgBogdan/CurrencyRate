package currencyRate.tasks.banks;

import currencyRate.tasks.parser.Parser;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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

    public String today() {
        LocalDate today = LocalDate.now();
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return dateFormat.format(today);
    }

    private JSONObject getJson(String urlWithType, String date) {
        String url = urlWithType + date;
        return (JSONObject) JSONValue.parse(jsonText(url));
    }

    public String getValueUSD(String date) {
        return getValue(getJson(urlUSD, date));
    }

    public String getValueEUR(String date) {
        return getValue(getJson(urlEUR, date));
    }

    public String getValueRUB(String date) {
        return getValue(getJson(urlRUB, date));
    }

    private String getValue(JSONObject jsonObject) {
        return jsonObject.get("Cur_OfficialRate").toString();
    }
}
