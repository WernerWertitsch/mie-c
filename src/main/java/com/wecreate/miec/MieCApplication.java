package com.wecreate.miec;

import com.wecreate.miec.base.generic.SequenceCpmtextWraüüer;
import com.wecreate.miec.example.DefaultDomainFunctions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MieCApplication {

    public static void main(String[] args) {
        SpringApplication.run(MieCApplication.class, args);
    }

    SequenceCpmtextWraüüer sequenceCpmtextWraüüer;

    @Autowired
    public MieCApplication(SequenceCpmtextWraüüer sequenceCpmtextWraüüer) {
        this.sequenceCpmtextWraüüer = sequenceCpmtextWraüüer;
        this.sequenceCpmtextWraüüer.populate(new DefaultDomainFunctions());
        this.sequenceCpmtextWraüüer.print();;
        this.sequenceCpmtextWraüüer.setValue("test", "#rents");
        this.sequenceCpmtextWraüüer.print();;
        this.sequenceCpmtextWraüüer.printVVariable("test");
    }




}
