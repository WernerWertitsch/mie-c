package com.wecreate.miec.base.generic;

import java.util.function.Function;

public class CommandContext extends GenericContext {

    //ok also theoretisch ist die math etc function usage schon im domain context, dann hier convenience zeug machen evtl oder doch nicht?

    public boolean setVariable(String variableName, String functionName) {
        //ok MITTRWOCH das hier muss halt auch mit math functions listen und so gehen, evtl welche die schon im context sind

        Function<GenericContext, Object> function = getFunction(functionName);
        this.putVariableValue(variableName, function.apply(this));

        return false;
    }
}
