package com.wecreate.miec.base.generic;

import com.wecreate.miec.base.generic.functions.FilterFunctions;
import com.wecreate.miec.base.generic.functions.MathFunctions;
import com.wecreate.miec.base.generic.util.ContextPopulator;
import com.wecreate.miec.base.generic.util.MessageReceiver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
@Slf4j
public class SequenceCpmtextWraüüer {
    //we could have multiple contexts here (a map)
    GenericContext genericContext;
    MessageReceiver messageReceiver;

    FilterFunctions filterFunctions;
    MathFunctions mathFunctions;
    //TODO NEXT:
    //syntax für filter und math functions definnieren (<variable.$xxyz), (y=variable.ssum(fieldName))
    //

    @Autowired
    public SequenceCpmtextWraüüer(MessageReceiver messageReceiver, FilterFunctions filterFunctions, MathFunctions mathFunctions) {
        this.messageReceiver = messageReceiver;
        this.genericContext = new GenericContext(messageReceiver);
        this.filterFunctions = filterFunctions;
        this.mathFunctions = mathFunctions;
    }

    public void populate(ContextPopulator populator) {
        populator.populateContext(genericContext);
    }




    public boolean setValue(String variableName, String identifier) {
        String initialSetStatement=identifier;
        String[] pipeParts=null;
        if(identifier.contains(".")) {
            pipeParts = identifier.split(".");
            initialSetStatement = pipeParts[0];
            pipeParts = Arrays.copyOfRange(pipeParts,1, pipeParts.length);
        }

        Object value=this.genericContext.getValue(identifier);

        // TODO NEXT, HIER DIE PIPE DURCHGEHEN, MIT IRGENDINEM FORMAT

        Object finalValue;
        if(value instanceof List) {
            finalValue = pipeProcessList(value, pipeParts);
        } else {

        }


        return false;
    }

    public void printVVariable(String identifier) {
        Object value = this.genericContext.getVariableValue(identifier).get();
        if(value instanceof List) {
            List list=(List)value;
            list.forEach(v -> messageReceiver.message(v.toString()));
        } else {
            messageReceiver.message(value.toString());
        }
    }

    public void print() {
        log.info("\n============ CONTEXT CONTENT ==========\n\n");
        log.info(this.genericContext.printAllDefinitions().toString());
    }

    //todo move this out
    public void parseCommand(String command) {

    }



}
