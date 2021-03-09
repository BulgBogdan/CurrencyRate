package currencyRate.tasks.banks;

import currencyRate.entity.Bank;
import currencyRate.entity.BankBranch;
import currencyRate.entity.City;
import currencyRate.tasks.parser.ParserXml;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
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

    public static String getFilialName(String cityName, String url) {
        String filialName = "";
        XMLEventReader reader = ParserXml.getReader(url);
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
                                filialName = attribute.getValue();
                                break;
                            }
                        }
                        if (cityName.equalsIgnoreCase("минск")
                                && foundCity("Головное отделение", attribute.getValue())) {
                            event = reader.nextEvent();
                            if (event.isStartElement()) {
                                filialName = attribute.getValue();
                                break;
                            }
                        }
                    }
                }
            }
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }
        return filialName;
    }

    public static String getValue(String typeMoney, String currencySelect, String filialName, String url) {
        String value = "";
        XMLEventReader reader = ParserXml.getReader(url);
        boolean getFilial = !filialName.isEmpty();
        boolean foundTypeMoney = false;
        try {
            while (reader.hasNext()) {
                XMLEvent event = reader.nextEvent();
                if (event.isStartElement()) {
                    StartElement startElement = event.asStartElement();
                    if (startElement.getName().getLocalPart().equalsIgnoreCase("rate")) {
                        Attribute attribute = startElement.getAttributeByName(new QName("currency"));
                        if (attribute.getValue().equalsIgnoreCase(typeMoney) && getFilial) {
                            foundTypeMoney = true;
                        }
                    }
                    if (foundTypeMoney && currencySelect.equalsIgnoreCase("продажа")) {
                        if (startElement.getName().getLocalPart().equalsIgnoreCase("sell")) {
                            event = reader.nextEvent();
                            if (event.isCharacters()) {
                                value = event.asCharacters().getData();
                                break;
                            }
                        }
                    }
                    if (foundTypeMoney && currencySelect.equalsIgnoreCase("покупка")) {
                        if (startElement.getName().getLocalPart().equalsIgnoreCase("buy")) {
                            event = reader.nextEvent();
                            if (event.isCharacters()) {
                                value = event.asCharacters().getData();
                                break;
                            }
                        }
                    }
                }
            }
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }
        return value;
    }

    public static BankBranch getBranch(City city, Bank bank, String filialName) {
        BankBranch branch = new BankBranch();
        branch.setCity(city);
        branch.setBank(bank);
        if (filialName.isEmpty()) {
            branch.setName("Нет информации");
            branch.setId(0);
        } else {
            branch.setName(filialName);
            branch.setFilialId(0);
        }
        branch.setAddress("Нет информации");
        return branch;
    }
}