package currencyRate.tasks.banks;

import currencyRate.entity.Bank;
import currencyRate.entity.BankBranch;
import currencyRate.entity.City;
import currencyRate.tasks.parser.ParserXml;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

public final class ParserMTBank {

    public static String getValue(String city, String typeMoney, String currencySelect, String url) {
        String value = "";
        XMLEventReader reader = ParserXml.getReader(url);
        boolean foundTypeMoney = false;
        boolean foundRow = false;
        try {
            while (reader.hasNext()) {
                XMLEvent event = reader.nextEvent();
                if (event.isStartElement()) {
                    StartElement startElement = event.asStartElement();
                    if (startElement.getName().getLocalPart().equalsIgnoreCase("code")) {
                        event = reader.nextEvent();
                        if (event.asCharacters().getData().equalsIgnoreCase("BYN")) {
                            foundRow = true;
                        } else {
                            foundRow = false;
                        }
                    }
                    if (startElement.getName().getLocalPart().equalsIgnoreCase("codeTo")) {
                        event = reader.nextEvent();
                        if (foundRow && event.asCharacters().getData().equalsIgnoreCase(typeMoney)) {
                            foundTypeMoney = true;
                        }
                    }
                    if (foundTypeMoney && currencySelect.equalsIgnoreCase("продажа")) {
                        if (startElement.getName().getLocalPart().equalsIgnoreCase("purchase")) {
                            event = reader.nextEvent();
                            if (event.isCharacters()) {
                                value = event.asCharacters().getData();
                                break;
                            }
                        }
                    }
                    if (foundTypeMoney && currencySelect.equalsIgnoreCase("покупка")) {
                        if (startElement.getName().getLocalPart().equalsIgnoreCase("sale")) {
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


    public static BankBranch getFilial(City city, Bank bank) {
        BankBranch branch = new BankBranch();


        branch.setName("Филиал города " + city.getName() + ".");
        branch.setAddress("Нет информации от банка");
        branch.setFilialId(0);
        branch.setCity(city);
        branch.setBank(bank);
        return branch;
    }
}