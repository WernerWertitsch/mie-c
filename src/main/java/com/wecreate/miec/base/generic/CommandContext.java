package com.wecreate.miec.base.generic;

import com.wecreate.miec.base.generic.util.MessageReceiver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class CommandContext {
    //we will have multiple contexts here
    GenericContext genericContext;

    @Autowired
    public CommandContext(MessageReceiver messageReceiver) {
        this.genericContext = new GenericContext(messageReceiver);
    }

    //ok also theoretisch ist die math etc function usage schon im domain context, dann hier convenience zeug machen evtl oder doch nicht?



    public boolean setValue(String variableName, String identifier) {
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



}
