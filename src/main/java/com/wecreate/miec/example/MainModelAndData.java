package com.wecreate.miec.example;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Data
@Component
public class MainModelAndData {

    List<Customer> customers;
    List<Space> spaces;
    List<EnergyInKw> energyUsageInKws;
    List<ConfirmedRentedDay> confirmedRentedDays;
    List<Price> prices;
    List<Rebate> rebates;


    public MainModelAndData() {
        this.customers = Customer.create(5);
        this.spaces = Space.create(10);
        this.energyUsageInKws = EnergyInKw.create(10, spaces);
        this.confirmedRentedDays = ConfirmedRentedDay.create(30, customers, spaces);
        this.rebates = Rebate.create(10, customers, spaces);
    }


    public Optional<Customer> getCustomer(int id) {
        return this.customers.stream().filter(c -> c.id == id).findFirst();
    }

    public Optional<Space> getSpace(int id) {
        return this.spaces.stream().filter(c -> c.id == id).findFirst();
    }

    public Optional<EnergyInKw> getEnergyUsageInKws(int id) {
        return this.energyUsageInKws.stream().filter(c -> c.id == id).findFirst();
    }

    public Optional<ConfirmedRentedDay> getConfirmedRentalDay(int id) {
        return this.confirmedRentedDays.stream().filter(c -> c.id == id).findFirst();
    }
    public Optional<Price> getPrice(int id) {
        return this.prices.stream().filter(c -> c.id == id).findFirst();
    }
    public Optional<Rebate> getRebate(int id) {
        return this.rebates.stream().filter(c -> c.id == id).findFirst();
    }

    public List<ConfirmedRentedDay> findRentedDaysForCustomer(Integer customerId, LocalDate from, LocalDate to) {
        return this.getConfirmedRentedDays().stream().filter(d -> from.isBefore(d.date) && to.isAfter(d.date)).collect(Collectors.toList());
    }

    public List<ConfirmedRentedDay> enrichRentDataWithPrice(List<ConfirmedRentedDay> rentedDays, List<Price> prices) {
        rentedDays.forEach(d -> {
            Optional<Price> relevantPrice = prices.stream().filter(p -> p.from.isBefore(d.date) && p.to.isAfter(d.date)).findFirst();
            if(relevantPrice.isPresent()) {
                d.setCalculatedPrice(d.getSpace().getSize() * relevantPrice.get().pricePerM2);
            }
        });
        return rentedDays;
    }

    public Double allEnergyForRents(List<ConfirmedRentedDay> rentedDays, List<EnergyInKw> energyInKw, Double kwPrice) {
        return rentedDays.stream().collect(Collectors.summingDouble(d -> getUsageForDay(d.date, d.space, energyInKw)*kwPrice));
    }

    private int getUsageForDay(LocalDate day, Space space, List<EnergyInKw> energyUsageKw) {
        Optional<EnergyInKw> usageForSpace = energyUsageKw.stream().filter(u -> u.day.equals(day) && u.space==space).findFirst();
        if(!usageForSpace.isPresent()) {
            return 0;
        }
        return usageForSpace.get().amount;
    }



    @Data
    @AllArgsConstructor
    public static class Customer {
        int id;
        String name;

        public static List<Customer> create(int count) {
            return IntStream.range(1, count+1)
                    .mapToObj(i -> new Customer(i, String.format("Customer No.%d", i)))
                    .collect(Collectors.toList());
        }
    }

    @Data
    @AllArgsConstructor
    public static class Space {
        int id;
        String name;
        int size;

        public static List<Space> create(int count) {
            return IntStream.range(1, count+1)
                    .mapToObj(i -> new Space(i, String.format("Space No.%d", i), 3*i))
                    .collect(Collectors.toList());
        }
    }

    @Data
    @AllArgsConstructor
    public static class EnergyInKw {
        int id;
        LocalDate day;
        Space space;
        int amount;

        public static List<EnergyInKw> create(int count, List<Space> spaces) {
            return IntStream.range(1, count+1)
                    .mapToObj(i -> new EnergyInKw(i,
                            LocalDate.now().minus(30, ChronoUnit.DAYS).plus(i, ChronoUnit.DAYS),
                            spaces.get(new Random().nextInt(spaces.size()-1)), new Random().nextInt(200)))
                    .collect(Collectors.toList());
        }
    }

    @Data
    @AllArgsConstructor
    public static class ConfirmedRentedDay {
        int id;
        Customer czstomer;
        Space space;
        LocalDate date;
        Double calculatedPrice;

        public static List<ConfirmedRentedDay> create(int count,  List<Customer> customers, List<Space> spaces) {
            return IntStream.range(1, count+1)
                    .mapToObj(i -> new ConfirmedRentedDay(i,
                            customers.get(new Random().nextInt(customers.size()-1)),
                            spaces.get(new Random().nextInt(customers.size()-1)),
                            LocalDate.now().minus(30, ChronoUnit.DAYS).plus(i, ChronoUnit.DAYS),
                            null
                            ))
                    .collect(Collectors.toList());
        }
    }

    @Data
    @AllArgsConstructor
    public static class Price {
        int id;
        LocalDate from;
        LocalDate to;
        double pricePerM2;

        //currently only 1
        public static List<Price> create(int count) {
            return List.of(new Price(1,
                    LocalDate.now().minus(1,
                    ChronoUnit.MONTHS),
                    LocalDate.now().plus(1, ChronoUnit.DAYS),
                    22.0));
        }

    }

    @Data
    @AllArgsConstructor
    public static class Rebate {
        int id;
        LocalDate from;
        LocalDate to;
        double percent;
        Space space;
        public static List<Rebate> create(int count, List<Customer> customers, List<Space> spaces) {
            return IntStream.range(1, count+1)
                    .mapToObj(i -> new Rebate(
                            i,
                            LocalDate.now().minus(30, ChronoUnit.DAYS).plus(i*2, ChronoUnit.DAYS),
                            LocalDate.now().minus(30, ChronoUnit.DAYS).plus(i*2+2, ChronoUnit.DAYS),
                            i/100,
                            spaces.get(new Random().nextInt(spaces.size()-1))))
                    .collect(Collectors.toList());
        }
    }



}
