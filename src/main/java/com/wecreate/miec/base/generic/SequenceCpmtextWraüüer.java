package com.wecreate.miec.base.generic;

import com.wecreate.miec.base.generic.util.ContextPopulator;
import com.wecreate.miec.base.generic.util.MessageReceiver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SequenceCpmtextWraüüer {
    //we will have multiple contexts here
    GenericContext genericContext;
    MessageReceiver messageReceiver;

    @Autowired
    public SequenceCpmtextWraüüer(MessageReceiver messageReceiver) {
        this.messageReceiver = messageReceiver;
        this.genericContext = new GenericContext(messageReceiver);
    }

    public void populate(ContextPopulator populator) {
        populator.populateContext(genericContext);
    }

    //TODO NEXT: bei print schauen ob alle notwendigen fucntion variablen gesetzt sind und anezeigen
    //hier entweder bei jeddem set die sequence, oder das separat...im letzteren fall muss ein "compute/run" geben, aber wenn dann scahuen ob da eh geht
    //glaub letztendlich muss alles auf variablen level existrieren, udh im idealfall bei print zurückverfolgen

    //ok also theoretisch ist die math etc function usage schon im domain context, dann hier convenience zeug machen evtl oder doch nicht?

    protected boolean setValue(String variableName, String identifier) {
        //ok MITTRWOCH das hier muss halt auch mit math functions listen und so gehen, evtl welche die schon im context sind
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

    public void print() {
        log.info("============ CONTEXT CONTENT ==========");
        log.info(this.genericContext.printAllDefinitions().toString());
    }

    //todo move this out
    public void parseCommand(String command) {

    }



}
