package currencyRate.tasks.banks;

import currencyRate.entity.Bank;
import currencyRate.entity.BankBranch;
import currencyRate.entity.City;
import currencyRate.tasks.parser.Parser;
import currencyRate.tasks.parser.ParserXml;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class ParserAbsolutebank {

    private static boolean foundCity(String cityName, String obtainText) {
        boolean foundCityName = false;
        Pattern pattern = Pattern.compile("\\b" + cityName + "\\b*");
        Matcher matcher = pattern.matcher(obtainText);
        while (matcher.find()) {
            if (!matcher.group().isEmpty())
                foundCityName = true;
        }
        return foundCityName;
    }

//    public static String getFilialName(String )

    public static Map<String, String> getFilialAndValue(String cityName, String typeMoney,
                                                        String currencySelect, String url) {
        Map<String, String> filialAndValue = new HashMap<>();
        XMLEventReader reader = ParserXml.getReader(url);
        String getFilial = "";
        boolean foundTypeMoney = false;
        try {
            while (reader.hasNext()) {
                XMLEvent event = reader.nextEvent();
                if (event.isStartElement()) {
                    StartElement startElement = event.asStartElement();
                    if (startElement.getName().getLocalPart().equalsIgnoreCase("branch")) {
                        Attribute attribute = startElement.getAttributeByName(new QName("name"));
                        if (foundCity(cityName, attribute.getValue())) {
                            event = reader.nextEvent();
                            if (event.isStartElement()) {
                                startElement = event.asStartElement();
                                getFilial = attribute.getValue();
                            }
                        }
                        if (cityName.equalsIgnoreCase("минск")
                                && !foundCity(cityName, attribute.getValue())) {
                            event = reader.nextEvent();
                            if (event.isStartElement()) {
                                startElement = event.asStartElement();
                                getFilial = attribute.getValue();
                            }
                        }
                    }
                    if (startElement.getName().getLocalPart().equalsIgnoreCase("rate")) {
                        Attribute attribute = startElement.getAttributeByName(new QName("currency"));
                        if ((!getFilial.isEmpty()) && attribute.getValue().equalsIgnoreCase(typeMoney)) {
                            foundTypeMoney = true;
                        }
                    }
                    if (foundTypeMoney && currencySelect.equalsIgnoreCase("продажа")) {
                        if (startElement.getName().getLocalPart().equalsIgnoreCase("sell")) {
                            event = reader.nextEvent();
                            if (event.isCharacters()) {
                                filialAndValue.put(getFilial, event.asCharacters().getData());
                            }
                        }
                    }
                    if (foundTypeMoney && currencySelect.equalsIgnoreCase("покупка")) {
                        if (startElement.getName().getLocalPart().equalsIgnoreCase("buy")) {
                            event = reader.nextEvent();
                            if (event.isCharacters()) {
                                filialAndValue.put(getFilial, event.asCharacters().getData());
                            }
                        }
                    }
                }
            }

        } catch (XMLStreamException e) {
            e.printStackTrace();
        }
        return filialAndValue;
    }

    public static String getBestValue(Map<String, String> filialsValues, String currencySelect) {
        String value;
        List<String> values = new ArrayList<>(filialsValues.values());
        value = Parser.getBestValue(values, currencySelect);
        return value;
    }

    public static BankBranch getBranch(City city, Bank bank, Map<String, String> filialsValues,
                                       String bestValue, String url) {
        BankBranch branch = new BankBranch();
        String filialName = ParserAbsolutebank.getFilialName(bestValue, filialsValues);
        branch.setCity(city);
        branch.setBank(bank);
        if (filialName.isEmpty()) {
            branch.setName("Нет информации от банка");
            branch.setId(0);
        } else {
            branch.setName(filialName);
            branch.setFilialId(Integer.parseInt(ParserAbsolutebank.getFilialId(filialName, url)));
        }
        branch.setAddress("Нет информации от банка");
        return branch;
    }

    private static String getFilialId(String filialName, String url) {
        String filialId = "";
        XMLEventReader reader = ParserXml.getReader(url);
        try {
            while (reader.hasNext()) {
                XMLEvent event = reader.nextEvent();
                if (event.isStartElement()) {
                    StartElement startElement = event.asStartElement();
                    if (startElement.getName().getLocalPart().equalsIgnoreCase("branch")) {
                        Attribute attribute = startElement.getAttributeByName(new QName("id"));
                        filialId = attribute.getValue();
                    }
                    if (startElement.getName().getLocalPart().equalsIgnoreCase("branch")) {
                        Attribute attribute = startElement.getAttributeByName(new QName("name"));
                        if (attribute.getValue().equalsIgnoreCase(filialName)) {
                            break;
                        }
                    }
                }
            }
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }
        return filialId;
    }

    private static String getFilialName(String bestValue, Map<String, String> banksValue) {
        String filialName = "";
        for (Map.Entry<String, String> entry : banksValue.entrySet()) {
            if (entry.getValue().contains(bestValue)) {
                filialName = entry.getKey();
                break;
            }
        }
        return filialName;
    }
}