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

    public static final Function<String, Boolean> isFunctionIdentifier = (s -> s.startsWith("#"));
    public static final Function<String, Boolean> isFulterIdentifier = (s -> s.startsWith("$"));
    public static final Function<String, Boolean> isConstant = (s -> s.startsWith("+"));

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

    public boolean isFunctionSet(String identifier) {
        if(identifier.startsWith("#")) {
            identifier = identifier.substring(1);
        }
        return getFunction(identifier)!=null;
    }

    public boolean isFilterSet(String identifier) {
        if(identifier.startsWith("$")) {
            identifier = identifier.substring(1);
        }
        return getFilter(identifier)!=null;
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
