package currencyRate.tasks.scheduleds;

import currencyRate.tasks.component.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTimes {
    private Belarusbank belarusbank;
    private Belagrpombank belagrpombank;
    private Belgazprombank belgazprombank;
    private BSB bsb;
    private BankDabrabyt dabrabyt;
    private Absolutebank absolutebank;
    private MTBank mtb;
    private VTB vtb;

    @Autowired
    public ScheduledTimes(Belarusbank belarusbank, Belagrpombank belagrpombank, Belgazprombank belgazprombank,
                          BSB bsb, BankDabrabyt dabrabyt, Absolutebank absolutebank, MTBank mtb, VTB vtb) {
        this.belarusbank = belarusbank;
        this.belagrpombank = belagrpombank;
        this.belgazprombank = belgazprombank;
        this.bsb = bsb;
        this.dabrabyt = dabrabyt;
        this.absolutebank = absolutebank;
        this.mtb = mtb;
        this.vtb = vtb;
    }

    @Scheduled(cron = "0 */15 * ? * *") //every 15 minutes
    public void editFilialsEveryQuarter() {
        belarusbank.createAndUpdateValues();
        belagrpombank.createAndUpdateValues();
    }

    @Scheduled(cron = "0 0 * ? * *") //every hour
    public void editFilialsEveryHour() {
        belgazprombank.createAndUpdateValues();
        bsb.createAndUpdateValues();
        dabrabyt.createAndUpdateValues();
        absolutebank.createAndUpdateValues();
        vtb.createAndUpdateValues();
        mtb.createAndUpdateValues();
    }


}