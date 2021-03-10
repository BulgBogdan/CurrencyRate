package currencyRate.tasks.banks;

import currencyRate.entity.Bank;
import currencyRate.entity.BankBranch;
import currencyRate.entity.City;
import currencyRate.tasks.parser.ParserXml;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

public final class ParserVTB {

    public static String getValue(String typeMoney, String currencySelect, String url) {
        String value = "";
        XMLEventReader reader = ParserXml.getReader(url);
        boolean foundTypeMoney = false;
        try {
            while (reader.hasNext()) {
                XMLEvent event = reader.nextEvent();
                if (event.isStartElement()) {
                    StartElement startElement = event.asStartElement();
                    if (startElement.getName().getLocalPart().equalsIgnoreCase("code")) {
                        event = reader.nextEvent();
                        if (event.isCharacters() && typeMoney.equalsIgnoreCase(event.asCharacters().getData())) {
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

    public static BankBranch getBranch(City city, Bank bank) {
        BankBranch branch = new BankBranch();
        branch.setCity(city);
        branch.setBank(bank);
        branch.setName("Нет информации");
        branch.setId(0);
        branch.setAddress("Нет информации");
        return branch;
    }
}
