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

public final class ParserBelgazprombank {

    public static String getValue(String city, String typeMoney, String currencySelect, String url) {
        String value = "";
        XMLEventReader reader = ParserXml.getReader(url);
        boolean foundCity = false;
        boolean foundTypeMoney = false;
        try {
            while (reader.hasNext()) {
                XMLEvent event = reader.nextEvent();
                if (event.isStartElement()) {
                    StartElement startElement = event.asStartElement();
                    if (startElement.getName().getLocalPart().equalsIgnoreCase("branch")) {
                        Attribute attribute = startElement.getAttributeByName(new QName("name"));
                        if (attribute.getValue().substring(0, 5).equalsIgnoreCase(city.substring(0, 5))) {
                            foundCity = true;
                        }
                    }
                    if (startElement.getName().getLocalPart().equalsIgnoreCase("rate")) {
                        Attribute attribute = startElement.getAttributeByName(new QName("currency"));
                        if (foundCity && attribute.getValue().equalsIgnoreCase(typeMoney)) {
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

    public static BankBranch getFilial(City city, Bank bank, String url) {
        BankBranch branch = new BankBranch();

        XMLEventReader reader = ParserXml.getReader(url);
        String foundFilial = "";
        try {
            while (reader.hasNext()) {
                XMLEvent event = reader.nextEvent();
                if (event.isStartElement()) {
                    StartElement startElement = event.asStartElement();
                    if (startElement.getName().getLocalPart().equalsIgnoreCase("branch")) {
                        Attribute attribute = startElement.getAttributeByName(new QName("name"));
                        if (attribute.getValue().substring(0, 5).equalsIgnoreCase(city.getName().substring(0, 5))) {
                            foundFilial = attribute.getValue();
                            break;
                        }
                    }
                }
            }
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }
        if (foundFilial.isEmpty()) {
            branch.setName("Нет информации от банка");
        } else {
            branch.setName(foundFilial);
        }
        branch.setAddress("Нет информации от банка");
        branch.setFilialId(0);
        branch.setCity(city);
        branch.setBank(bank);
        return branch;
    }
}
