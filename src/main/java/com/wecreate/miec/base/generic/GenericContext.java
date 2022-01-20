package com.wecreate.miec.base.generic;

import com.wecreate.miec.base.generic.util.MessageReceiver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

public class GenericContext {
    Map<String, Function<GenericContext, Object>> functionMap = new HashMap<>();
    Map<String, List<String>> functionParamMap = new HashMap<>();
    Map<String, Function<Object, Boolean>> filterMap = new HashMap<>();
    Map<String, Supplier<Object>> variables = new HashMap<>();

    public static final String functionPrefix = "#";
    public static final String filterPrefix = "$";
    public static final String constantPrefix= "+";
    public static final String variablePrefix= "!";


    MessageReceiver messageReceiver;

    public GenericContext(MessageReceiver messageReceiver) {
        this.messageReceiver = messageReceiver;
    }

    public StringBuilder printAllDefinitions() {
        StringBuilder ret = new StringBuilder("\nVariables:\n");
        variables.forEach((k,v) -> ret.append(String.format("\t%s\n", k)));
        ret.append(("Functions:\n"));
        functionMap.forEach((k,v) -> {
            ret.append(String.format("\t%s", k));
            List<String> params = functionParamMap.get(k);
            if(params!=null) {
                ret.append("(");
                for(int i=0; i<params.size(); i++) {
                    if(!isVariableSet(params.get(i))) {
                        ret.append("*");
                    }
                    ret.append(params.get(i));
                    ret.append(i>= (params.size()-1) ? "":",");
                }
                ret.append(")");
            }
            ret.append("\n");
        });
        ret.append("Filters:\n");
        filterMap.forEach((k,v) -> ret.append(String.format("\t%s\n", k)));
        return ret;
    }

    public Object getValue(String identifier) {
        String prefix = identifier.substring(0,1);
        String pureKey = identifier.substring(1);
        switch (prefix) {
            case functionPrefix: return getFunction(pureKey);
            case filterPrefix: return getFilter(pureKey);
            case variablePrefix: return getVariableValue(pureKey);
            case constantPrefix: return getVariableValue(pureKey);
        }
        return null;
    }

    public void addFunction(String fullIdentifier, Function<GenericContext, Object> value) {
        String pureKey = fullIdentifier;
        if(fullIdentifier.contains("(")) {
            String[] parts = fullIdentifier.split("\\(");
            pureKey = parts[0];
            String params[] = parts[1].split(",");
            List<String> paramsAsList=new ArrayList<>();
            for(int i=1; i< params.length; i++) {
                String param = params[i];
                paramsAsList.add((param.endsWith(")") ? param.substring(0, param.length()-1) : param).trim());
            }
            this.functionParamMap.put(pureKey, paramsAsList);
        }
        this.functionMap.put(pureKey, value);
    }

    public boolean isVariableSet(String identifier) {
        return getVariableValue(identifier)!=null;
    }

    public <T>Function<GenericContext, T> getFunction(String key) {
        return (Function<GenericContext, T>) this.functionMap.get(key);
    }

    public void addFilter(String key, Function<Object, Boolean> value) {
        this.filterMap.put(key, value);
    }

    public Function<Object, Boolean> getFilter(String key) {
        return this.filterMap.get(key);
    }

    public void putVariableValue(String key, Object value) {
        this.variables.put(key, ()->value);
    }

    public <T>Supplier<T> getVariableValue(String key) {
        return (Supplier<T>) this.variables.get(key);
    }

}
