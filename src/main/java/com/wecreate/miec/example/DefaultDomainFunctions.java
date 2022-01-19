package com.wecreate.miec.example;

import com.wecreate.miec.base.generic.GenericContext;
import com.wecreate.miec.base.generic.util.ContextPopulator;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class DefaultDomainFunctions implements ContextPopulator {
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
                (Integer) c.getVariableValue("customerId").get(),
                (LocalDate) c.getVariableValue("from").get(),
                (LocalDate) c.getVariableValue("to").get()));
        context.addFunction("calculateRents(prices,rents)", c -> mainModelAndData.enrichRentDataWithPrice(
                (List<MainModelAndData.ConfirmedRentedDay>) c.getFunction("prices").apply(c),
                (List<MainModelAndData.Price>) c.getFunction("rents").apply(c)
        ));
        context.addFunction("energyKw(rents,energyInKw)", c -> mainModelAndData.allEnergyForRents(
                (List<MainModelAndData.ConfirmedRentedDay>) c.getFunction("rents").apply(c),
                (List<MainModelAndData.EnergyInKw>) c.getFunction("energyUsageInKw").apply(c),
                (Double)c.getVariableValue("energyPrice").get()
        ));
    }



}
