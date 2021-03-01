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

public final class ParserDabrabyt {

    public static Map<String, String> getValuesAndFilials(String city,
                                                          String typeMoney,
                                                          String currencySelect,
                                                          String url) {
        Map<String, String> filialsAndValues = new HashMap<>();
        XMLEventReader reader = ParserXml.getReader(url);
        String getCity = "";
        String getValue;
        String filialId = "";
        if (city.equalsIgnoreCase("могилев")) {
            city = "могилёв";
        }
        try {
            while (reader.hasNext()) {
                XMLEvent event = reader.nextEvent();
                if (event.isStartElement()) {
                    StartElement startElement = event.asStartElement();
                    if (startElement.getName().getLocalPart().equalsIgnoreCase("city")) {
                        event = reader.nextEvent();
                        if (event.isCharacters()) {
                            getCity = event.asCharacters().getData();
                        }
                    }
                    if (startElement.getName().getLocalPart().equalsIgnoreCase("id")) {
                        event = reader.nextEvent();
                        if (event.isCharacters()) {
                            filialId = event.asCharacters().getData();
                        }
                    }
                    if (currencySelect.equalsIgnoreCase("продажа") && getCity.equalsIgnoreCase(city)) {
                        if (startElement.getName().getLocalPart().equalsIgnoreCase("value")) {
                            Attribute attribute = startElement.getAttributeByName(new QName("iso"));
                            if (attribute.getValue().equalsIgnoreCase(typeMoney)) {
                                attribute = startElement.getAttributeByName(new QName("sale"));
                                getValue = attribute.getValue();
                                filialsAndValues.put(filialId, getValue);

                            }
                        }
                    }
                    if (currencySelect.equalsIgnoreCase("покупка") && getCity.equalsIgnoreCase(city)) {
                        if (startElement.getName().getLocalPart().equalsIgnoreCase("value")) {
                            Attribute attribute = startElement.getAttributeByName(new QName("iso"));
                            if (attribute.getValue().equalsIgnoreCase(typeMoney)) {
                                attribute = startElement.getAttributeByName(new QName("buy"));
                                getValue = attribute.getValue();
                                filialsAndValues.put(filialId, getValue);
                            }
                        }
                    }
                }
            }
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }
        return filialsAndValues;
    }

    public static String getBestValue(String currencySelect, Map<String, String> filialsAndValue) {
        String value;
        List<String> values = new ArrayList<>(filialsAndValue.values());
        value = Parser.getBestValue(values, currencySelect);
        return value;
    }

    public static String getFilialId(Map<String, String> filialsAndValue, String bestValue) {
        String filialId = "";
        for (Map.Entry<String, String> filial : filialsAndValue.entrySet()) {
            if (filial.getValue().contains(bestValue)) {
                filialId = filial.getKey();
                break;
            }
        }
        return filialId;
    }

    public static BankBranch getFilial(Bank bank, City city, String url, String filialId) {
        BankBranch filial = new BankBranch();
        XMLEventReader reader = ParserXml.getReader(url);
        try {
            String nameFilial = "";
            String address = "";
            while (reader.hasNext()) {
                XMLEvent event = reader.nextEvent();
                if (event.isStartElement()) {
                    StartElement startElement = event.asStartElement();
                    if (startElement.getName().getLocalPart().equalsIgnoreCase("filial")) {
                        Attribute attribute = startElement.getAttributeByName(new QName("name"));
                        nameFilial = attribute.getValue();
                    }
                    if (startElement.getName().getLocalPart().equalsIgnoreCase("address")) {
                        event = reader.nextEvent();
                        if (event.isCharacters()) {
                            address = event.asCharacters().getData();
                        }
                    }
                    if (startElement.getName().getLocalPart().equalsIgnoreCase("id")) {
                        event = reader.nextEvent();
                        if (event.isCharacters() && event.asCharacters().getData().equals(filialId)) {
                            break;
                        }
                    }
                }
            }
            filial.setBank(bank);
            filial.setCity(city);
            filial.setFilialId(Integer.parseInt(filialId));

            if (!nameFilial.isEmpty()) {
                filial.setName(nameFilial);
            } else {
                filial.setName("Нет информации от банка");
            }
            if (!address.isEmpty()) {
                filial.setAddress(address);
            } else {
                filial.setAddress("Нет информации от банка");
            }
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }
        return filial;
    }
}