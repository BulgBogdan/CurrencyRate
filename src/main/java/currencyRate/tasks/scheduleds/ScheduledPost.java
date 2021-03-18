package currencyRate.tasks.scheduleds;

import currencyRate.tasks.component.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ScheduledPost {

    private Belarusbank belarusbank;
    private Belagrpombank belagrpombank;
    private Belgazprombank belgazprombank;
    private BSB bsb;
    private BankDabrabyt dabrabyt;
    private Absolutebank absolutebank;
    private MTBank mtb;
    private VTB vtb;

    @Autowired
    public ScheduledPost(Belarusbank belarusbank, Belagrpombank belagrpombank, Belgazprombank belgazprombank,
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

//    @PostConstruct
//    public void createFilialsWithValues() {
//        belarusbank.createAndUpdateValues();
//        belagrpombank.createAndUpdateValues();
//        belgazprombank.createAndUpdateValues();
//        bsb.createAndUpdateValues();
//        dabrabyt.createAndUpdateValues();
//        absolutebank.createAndUpdateValues();
//        mtb.createAndUpdateValues();
//        vtb.createAndUpdateValues();
//    }
}