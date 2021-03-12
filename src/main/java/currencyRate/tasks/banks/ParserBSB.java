package currencyRate.tasks.banks;

import currencyRate.entity.Bank;
import currencyRate.entity.BankBranch;
import currencyRate.entity.City;
import currencyRate.tasks.parser.ParserXml;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

public final class ParserBSB {

    public static String getValue(String typeMoney, String currencySelect, String url) {
        String value = "";
        XMLEventReader reader = ParserXml.getReader(url);
        String belarusMoney = "BYN";
        boolean foundTypeMoney = false;
        try {
            while (reader.hasNext()) {
                XMLEvent event = reader.nextEvent();
                if (event.isStartElement()) {
                    StartElement startElement = event.asStartElement();
                    if (currencySelect.equalsIgnoreCase("продажа")) {
                        if (startElement.getName().getLocalPart().equalsIgnoreCase("sell_amount")) {
                            event = reader.nextEvent();
                            if (event.isCharacters()) {
                                value = event.asCharacters().getData();
                            }
                        }
                    }
                    if (currencySelect.equalsIgnoreCase("покупка")) {
                        if (startElement.getName().getLocalPart().equalsIgnoreCase("buy_amount")) {
                            event = reader.nextEvent();
                            if (event.isCharacters()) {
                                value = event.asCharacters().getData();
                            }
                        }
                    }
                    if (startElement.getName().getLocalPart().equalsIgnoreCase("buy_good_name")) {
                        event = reader.nextEvent();
                        if (event.isCharacters() && typeMoney.equalsIgnoreCase(event.asCharacters().getData())) {
                            foundTypeMoney = true;
                        }
                    }
                    if (foundTypeMoney && startElement.getName().getLocalPart().equalsIgnoreCase("sell_good_name")) {
                        event = reader.nextEvent();
                        if (event.isCharacters() && belarusMoney.equalsIgnoreCase(event.asCharacters().getData())) {
                            break;
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