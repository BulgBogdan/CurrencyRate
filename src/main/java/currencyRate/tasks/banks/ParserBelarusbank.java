package currencyRate.tasks.banks;

import currencyRate.entity.Bank;
import currencyRate.entity.BankBranch;
import currencyRate.entity.City;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.time.LocalDate;
import java.time.LocalTime;


public final class ParserBelarusbank {

    public static String getTypeMoney(String currencySelect, String typeMoney) {
        StringBuilder type = new StringBuilder(typeMoney);
        if (currencySelect.equalsIgnoreCase("продажа")) {
            return String.valueOf(type.append("_out"));
        } else {
            return String.valueOf(type.append("_in"));
        }
    }

    public static String getValue(String currencySelect, String typeSelectMoney, JSONArray array) {
        String value = "";
        if (currencySelect.equalsIgnoreCase("продажа")) {
            for (Object object : array) {
                JSONObject jsonObject = (JSONObject) object;
                value = (String) jsonObject.get(typeSelectMoney);
            }
        } else if (currencySelect.equalsIgnoreCase("покупка")) {
            for (Object object : array) {
                JSONObject jsonObject = (JSONObject) object;
                value = (String) jsonObject.get(typeSelectMoney);
            }
        }
        return value;
    }

    public static String getUsefulValue(String currencySelect, String typeSelectMoney, JSONArray array) {
        String value = "";
        JSONObject firstValue = (JSONObject) array.get(0);

        if (currencySelect.equalsIgnoreCase("продажа")) {
            String minOutValue = (String) firstValue.get(typeSelectMoney);

            for (Object object : array) {
                JSONObject jsonObject = (JSONObject) object;
                if (workTimeFilial((String) jsonObject.get("info_worktime"))
                        &&
                        (Double.parseDouble((String) jsonObject.get(typeSelectMoney))
                                < Double.parseDouble(minOutValue))) {
                    minOutValue = (String) jsonObject.get(typeSelectMoney);
                }
            }
            value = minOutValue;
        } else if (currencySelect.equalsIgnoreCase("покупка")) {
            String maxInValue = (String) firstValue.get(typeSelectMoney);

            for (Object object : array) {
                JSONObject jsonObject = (JSONObject) object;
                if (workTimeFilial((String) jsonObject.get("info_worktime"))
                        &&
                        (Double.parseDouble((String) jsonObject.get(typeSelectMoney))
                                > Double.parseDouble(maxInValue))) {
                    maxInValue = (String) jsonObject.get(typeSelectMoney);
                }
            }
            value = maxInValue;
        }
        return value;
    }

    public static boolean workTimeFilial(String workTime) {

        String[] daysWorkTime = workTime.split("\\|");
        int dayOfWeek = LocalDate.now().getDayOfWeek().getValue();
        LocalTime timeNow = LocalTime.now();

        boolean closeOrOpen = false;

        for (int i = 0; i < daysWorkTime.length; i++) {
            if (dayOfWeek == i + 1) {
                String workTimeToday = daysWorkTime[i].replaceAll("([А-Яа-я]*)", "").trim();
                if (workTimeToday.length() > 0) {
                    String[] arrWorkTime = workTimeToday.split(" ");
                    if ((arrWorkTime[2] + arrWorkTime[3]).equals("2400")) {
                        arrWorkTime[2] = "23";
                        arrWorkTime[3] = "59";
                    }
                    LocalTime workStart = LocalTime
                            .of(Integer.valueOf(arrWorkTime[0]), Integer.valueOf(arrWorkTime[1]));
                    LocalTime workEnd = LocalTime
                            .of(Integer.valueOf(arrWorkTime[2]), Integer.valueOf(arrWorkTime[3]));
                    if (arrWorkTime.length == 4) {
                        closeOrOpen = timeNow.isAfter(workStart) && timeNow.isBefore(workEnd);
                    } else {
                        LocalTime breakStart = LocalTime
                                .of(Integer.valueOf(arrWorkTime[4]), Integer.valueOf(arrWorkTime[5]));
                        LocalTime breakEnd = LocalTime
                                .of(Integer.valueOf(arrWorkTime[6]), Integer.valueOf(arrWorkTime[7]));
                        closeOrOpen = (timeNow.isAfter(workStart) && timeNow.isBefore(breakStart))
                                || (timeNow.isAfter(breakEnd) && timeNow.isBefore(workEnd));
                    }
                } else {
                    closeOrOpen = false;
                }
            }
        }
        return closeOrOpen;
    }

    public static BankBranch getFilial(Bank bank,
                                       City city,
                                       String typeSelectMoney,
                                       JSONArray arrayJson,
                                       String value) {
        BankBranch bankBranch = new BankBranch();
        for (Object object : arrayJson) {
            JSONObject jsonObject = (JSONObject) object;
            if ((jsonObject.get(typeSelectMoney)).equals(value)) {
                bankBranch.setBank(bank);
                bankBranch.setCity(city);
                String address = (String) jsonObject.get("street_type")
                        + (String) jsonObject.get("street")
                        + " " + (String) jsonObject.get("home_number");
                bankBranch.setAddress(address);
                bankBranch.setName((String) jsonObject.get("filials_text"));
                bankBranch.setFilialId(Integer.parseInt((String) jsonObject.get("filial_id")));
            }
        }
        return bankBranch;
    }
}