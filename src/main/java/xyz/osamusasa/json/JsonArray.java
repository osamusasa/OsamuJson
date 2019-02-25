package xyz.osamusasa.json;

import java.util.ArrayList;

public class JsonArray implements JsonToken {
    private ArrayList<JsonValue> list;

    private JsonArray(){
        list = new ArrayList<JsonValue>();
    }

    @Override public String toString(){
        return "Array";
    }
    public void add(JsonValue val){
        list.add(val);
    }
    public static JsonArray create(){
        return new JsonArray();
    }
    static JsonArray create(JsonTokener tokener){
        if(!tokener.isArrayBegin())throw new JsonException("Syntax error:not exist Box bracket at line " + tokener.line);
        JsonArray arr = JsonArray.create();
        while(!tokener.isArrayEnd()){
            arr.add(JsonValue.create(tokener));
            tokener.isArraySpliter();
        }
        return arr;
    }
    @Override public String print(){
        StringBuilder str = new StringBuilder();
        str.append('[');
        for(JsonValue val:list){
            str.append(val.print()+",");
        }
        str = str.deleteCharAt(str.length()-1);
        str.append(']');
        return str.toString();
    }
    @Override
    public int size(){
        return list.size();
    }
}
