package com.wecreate.miec.example;

import com.wecreate.miec.base.generic.GenericContext;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class ExampleContext extends GenericContext {
    MainModelAndData.Customer curentCustomer;
    LocalDate fromDate = LocalDate.now().minus(30, ChronoUnit.DAYS);
    LocalDate toDate = LocalDate.now();
    Double energyPrice = 2.1;


//    @Autowired
    MainModelAndData mainModelAndData = new MainModelAndData();

    public ExampleContext() {
        this.curentCustomer = mainModelAndData.getCustomer(1).get();
        putVariableValue("from", fromDate);
        putVariableValue("to", toDate);
        putVariableValue("customerId", curentCustomer.getId());
        putVariableValue("energyPrice", energyPrice);

        addFunction("energyUsageInKw", context -> mainModelAndData.getEnergyUsageInKws());
        addFunction("rents(customerId,from,to)", context -> mainModelAndData.findRentedDaysForCustomer(
                (Integer) context.get("customerId").apply(this),
                (LocalDate) context.get("from").apply(this),
                (LocalDate) context.get("to").apply(this)));
        addFunction("calculateRents(prices,rents)", context -> mainModelAndData.enrichRentDataWithPrice(
                (List<MainModelAndData.ConfirmedRentedDay>) context.get("prices").apply(this),
                (List<MainModelAndData.Price>) context.get("rents").apply(this)
        ));
        addFunction("energyKw(rents,energyInKw)", context -> mainModelAndData.allEnergyForRents(
                (List<MainModelAndData.ConfirmedRentedDay>) context.get("rents").apply(this),
                (List<MainModelAndData.EnergyInKw>) context.get("energyUsageInKw").apply(this),
                (Double)context.get("energyPrice").apply(this)
        ));
    }



}
