package com.wecreate.miec.base.generic;

import com.wecreate.miec.base.generic.util.ContextPopulator;
import com.wecreate.miec.base.generic.util.MessageReceiver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class SequenceCpmtextWraüüer {
    //we will have multiple contexts here
    GenericContext genericContext;
    MessageReceiver messageReceiver;

    //TODO NEXT:
    //syntax für filter und math functions definnieren (<variable.$xxyz), (y=variable.ssum(fieldName))
    //

    @Autowired
    public SequenceCpmtextWraüüer(MessageReceiver messageReceiver) {
        this.messageReceiver = messageReceiver;
        this.genericContext = new GenericContext(messageReceiver);
    }

    public void populate(ContextPopulator populator) {
        populator.populateContext(genericContext);
    }

    public boolean setValue(String variableName, String identifier) {
        if(GenericContext.isFunctionIdentifier.apply(identifier)) {
            String pureKey =identifier.substring(1);
            genericContext.putVariableValue(variableName, genericContext.getFunction(pureKey).apply(genericContext));
            return true;
        }
        if(GenericContext.isFulterIdentifier.apply(identifier)) {
            String pureKey = identifier.substring(1);
            genericContext.putVariableValue(variableName, genericContext.getFilter(pureKey).apply(genericContext));
            return true;
        }
        if(GenericContext.isConstant.apply(identifier)) {
            String pureKey = identifier.substring(1);
            genericContext.putVariableValue(variableName, identifier);
            return true;
        }

        if(genericContext.getVariableValue(identifier)!=null) {
            genericContext.putVariableValue(variableName, genericContext.getVariableValue(identifier).get());
            return true;
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
