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
import java.util.*;

public final class ParserBelagroprombank {

    public static String getCityId(String city, XMLEventReader eventReader) {
        String cityId = "";
        XMLEventReader reader = eventReader;
        try {
            while (reader.hasNext()) {
                XMLEvent event = reader.nextEvent();
                if (event.isStartElement()) {
                    StartElement startElement = event.asStartElement();
                    if (startElement.getName().getLocalPart().equalsIgnoreCase("CityId")) {
                        event = reader.nextEvent();
                        if (event.isCharacters()) {
                            cityId = event.asCharacters().getData();
                        }
                    }
                    if (startElement.getName().getLocalPart().equalsIgnoreCase("CityTitleRu")) {
                        event = reader.nextEvent();
                        if (event.isCharacters() && event.asCharacters().getData().equalsIgnoreCase(city)) {
                            break;
                        }
                    }
                }
            }
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }
        return cityId;
    }

    public static BankBranch getFilial(Bank bank, City city, Set<String> filialIds, String urlBank) {
        BankBranch filial = new BankBranch();
        XMLEventReader reader = ParserXml.getReader(urlBank);
        try {
            String nameFilial = "";
            String address = "";
            String idBranch = "";
            boolean foundBank = false;
            while (reader.hasNext()) {
                XMLEvent event = reader.nextEvent();
                if (event.isStartElement()) {
                    StartElement startElement = event.asStartElement();
                    if (startElement.getName().getLocalPart().equals("Bank")) {
                        Attribute attribute = startElement.getAttributeByName(new QName("Id"));
                        for (String filialId : filialIds) {
                            if (filialId.equalsIgnoreCase(attribute.getValue())) {
                                foundBank = true;
                                idBranch = filialId;
                            }
                        }
                    }
                    if (startElement.getName().getLocalPart().equalsIgnoreCase("BankTitleRu")) {
                        event = reader.nextEvent();
                        if (event.isCharacters()) {
                            nameFilial = event.asCharacters().getData();
                        }
                    }
                    if (startElement.getName().getLocalPart().equalsIgnoreCase("BankAddressRu")) {
                        event = reader.nextEvent();
                        if (event.isCharacters() && foundBank) {
                            address = event.asCharacters().getData();
                            break;
                        }
                    }
                }
            }
            filial.setBank(bank);
            filial.setCity(city);
            if (idBranch.isEmpty()) {
                for (String filialId : filialIds) {
                    idBranch = filialId;
                    break;
                }
                filial.setFilialId(Integer.parseInt(idBranch));
            } else {
                filial.setFilialId(Integer.parseInt(idBranch));
            }
            if (foundBank) {
                filial.setName(nameFilial);
                filial.setAddress(address);
            } else {
                filial.setName("Нет информации от банка");
                filial.setAddress("Нет информации от банка");
            }
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }
        return filial;
    }

    public static Set<String> getFilialId(String bestValue, Map<String, String> banksValue) {
        Set<String> filialsId = new HashSet<>();
        for (Map.Entry<String, String> entry : banksValue.entrySet()) {
            if (entry.getValue().contains(bestValue)) {
                filialsId.add(entry.getKey());
            }
        }
        return filialsId;
    }

    public static String getUsefulValue(String currencySelect, Map<String, String> banksValue) {
        String value;
        List<String> values = new ArrayList<>(banksValue.values());
        value = Parser.getBestValue(values, currencySelect);
        return value;
    }

    public static Map<String, String> getBanksAndValues(String cityId,
                                                        String typeMoney,
                                                        String selectMoney,
                                                        String urlCurrency) {
        Map<String, String> banksValue = new HashMap<>();
        try {
            XMLEventReader reader = ParserXml.getReader(urlCurrency);
            String getValue = "";
            String getTypeMoney = "";
            while (reader.hasNext()) {
                XMLEvent event = reader.nextEvent();
                if (event.isStartElement()) {
                    StartElement startElement = event.asStartElement();
                    if (startElement.getName().getLocalPart().equalsIgnoreCase("CharCode")) {
                        event = reader.nextEvent();
                        if (event.isCharacters()) {
                            getTypeMoney = event.asCharacters().getData();
                        }
                    }
                    if (selectMoney.equalsIgnoreCase("продажа")
                            && getTypeMoney.equalsIgnoreCase(typeMoney)) {
                        if (startElement.getName().getLocalPart().equalsIgnoreCase("RateSell")) {
                            event = reader.nextEvent();
                            if (event.isCharacters()) {
                                getValue = event.asCharacters().getData();
                            }
                        }
                    }
                    if (selectMoney.equalsIgnoreCase("покупка")
                            && getTypeMoney.equalsIgnoreCase(typeMoney)) {
                        if (startElement.getName().getLocalPart().equalsIgnoreCase("RateBuy")) {
                            event = reader.nextEvent();
                            if (event.isCharacters()) {
                                getValue = event.asCharacters().getData();
                            }
                        }
                    }
                    if (startElement.getName().getLocalPart().equalsIgnoreCase("CityId")) {
                        event = reader.nextEvent();
                        if (event.isCharacters()
                                && event.asCharacters().getData().equalsIgnoreCase(String.valueOf(cityId))) {
                            if (getTypeMoney.equalsIgnoreCase(typeMoney)) {
                                for (int i = 0; i < 4; i++) {
                                    event = reader.nextEvent();
                                }
                                if (event.isCharacters()) {
                                    String idBank = event.asCharacters().getData();
                                    banksValue.put(idBank, getValue);
                                }
                            }
                        }
                    }
                }
            }
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }
        return banksValue;
    }
}