package com.wecreate.miec.example;

import com.wecreate.miec.base.generic.GenericContext;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class ExampleContext {
    MainModelAndData.Customer curentCustomer;
    LocalDate fromDate = LocalDate.now().minus(30, ChronoUnit.DAYS);
    LocalDate toDate = LocalDate.now();
    Double energyPrice = 2.1;


//    @Autowired
    MainModelAndData mainModelAndData = new MainModelAndData();

    public void populateContext(GenericContext context) {
        this.curentCustomer = mainModelAndData.getCustomer(1).get();
        context.putVariableValue("from", fromDate);
        context.putVariableValue("to", toDate);
        context.putVariableValue("customerId", curentCustomer.getId());
        context.putVariableValue("energyPrice", energyPrice);

        context.addFunction("energyUsageInKw", c -> mainModelAndData.getEnergyUsageInKws());
        context.addFunction("rents(customerId,from,to)", c -> mainModelAndData.findRentedDaysForCustomer(
                (Integer) c.<Integer>get("customerId").apply(this),
                (LocalDate) c.get("from").apply(this),
                (LocalDate) c.get("to").apply(this)));
        context.addFunction("calculateRents(prices,rents)", c -> mainModelAndData.enrichRentDataWithPrice(
                (List<MainModelAndData.ConfirmedRentedDay>) c.get("prices").apply(this),
                (List<MainModelAndData.Price>) c.get("rents").apply(this)
        ));
        context.addFunction("energyKw(rents,energyInKw)", c -> mainModelAndData.allEnergyForRents(
                (List<MainModelAndData.ConfirmedRentedDay>) c.get("rents").apply(this),
                (List<MainModelAndData.EnergyInKw>) c.get("energyUsageInKw").apply(this),
                (Double)c.get("energyPrice").apply(this)
        ));
    }



}
