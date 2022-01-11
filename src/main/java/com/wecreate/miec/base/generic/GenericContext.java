package com.wecreate.miec.base.generic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class GenericContext {
    Map<String, Function<GenericContext, Object>> functionMap = new HashMap<>();
    Map<String, List<String>> functionParamMap = new HashMap<>();
    Map<String, Function<Object, Boolean>> filterMap = new HashMap<>();
    Map<String, Object> variables = new HashMap<>();

    public  Function<? ,? extends Object> get(String fullIdentifier) {
        String pureKey = fullIdentifier;
        if(fullIdentifier.startsWith("#")) {
            pureKey = pureKey.substring(1);
            return getFunction(pureKey);
        }
        if(fullIdentifier.startsWith("$")) {
            pureKey = pureKey.substring(1);
            return getFilter(pureKey);
        }
        final Object var = getVariableValue(fullIdentifier);
        if(var!=null) return (ctx) -> var;

        // in case people forgot the filter/function identifier we will assume a mistake and return the respective function/filter
        Function<GenericContext, Object> fun = getFunction(fullIdentifier);
        if(fun!=null) return fun;
        return getFilter(fullIdentifier);
    }

    public StringBuilder printAllDefinitions() {
        StringBuilder ret = new StringBuilder("Variables:\n");
        variables.forEach((k,v) -> ret.append(String.format("\t%s\n", k)));
        ret.append(("Functions:\n"));
        functionMap.forEach((k,v) -> {
            ret.append(String.format("\t%s", k));
            List<String> params = functionParamMap.get(k);
            if(params!=null) {
                ret.append("(");
                for(int i=0; i<params.size(); i++) {
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

    protected Function<GenericContext, Object> getFunction(String key) {
        return this.functionMap.get(key);
    }

    public void addFilter(String key, Function<Object, Boolean> value) {
        this.filterMap.put(key, value);
    }

    protected Function<Object, Boolean> getFilter(String key) {
        return this.filterMap.get(key);
    }

    public void putVariableValue(String key, Object value) {
        this.variables.put(key, value);
    }

    protected Object getVariableValue(String key) {
        return this.variables.get(key);
    }



}
