package xyz.osamusasa.json;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class JsonObject implements JsonToken,Iterator<JsonString>,Iterable<JsonString>{
    private HashMap<JsonString, Integer> map;
    private ArrayList<JsonString> key;
    private ArrayList<JsonValue> value;

    private int pos;

    private JsonObject(){
        map = new HashMap<>();
        key = new ArrayList<>();
        value = new ArrayList<>();
        pos = 0;
    }

    @Override
    public boolean hasNext(){
        return pos < key.size();
    }

    @Override
    public JsonString next(){
        return key.get(pos++);
    }

    @Override
    public Iterator<JsonString> iterator(){
        pos = 0;
        return this;
    }

    @Override public String toString(){
        return "Object";
    }
    public boolean add(JsonString key, JsonValue value){
        Integer var = map.get(key);
        if(var != null){
            if(this.key.get(var).equals(key)){
                this.value.set(var, value);
                return false;
            }else{
                int p = this.key.indexOf(key);
                if(p!=-1){
                    this.value.set(var, value);
                    return false;
                }
            }
        }
        int len = this.key.size();
        map.put(key, len);
        this.key.add(len, key);
        this.value.add(len, value);
        return true;
    }
    public JsonValue get(JsonString key){
        Integer var = map.get(key);
        if(var==null)return null;
        return value.get(var);
    }

    public static JsonObject create(){
        return new JsonObject();
    }
    static JsonObject create(JsonTokener tokener){
        if(!tokener.isObjectBegin())throw new JsonException("Syntax error:not exist Curly bracket at line " + tokener.line);
        JsonObject obj = create();
        JsonString str;
        JsonValue val;
        while(!tokener.isObjectEnd()){
            str = JsonString.create(tokener);
            if(!tokener.isObjectSpliter())throw new JsonException("Syntax error:not exist colon at line " + tokener.line);
            val = JsonValue.create(tokener);
            obj.add(str,val);
            tokener.isObjectPairSpliter();
        }
        return obj;
    }
    @Override public String print(){
        StringBuilder str = new StringBuilder();
        boolean flag = false;
        str.append('{');
        for(JsonString key:this){
            str.append(key.print());
            str.append(':');
            str.append(get(key).print());
            str.append(',');
            flag = true;
        }
        if(flag)str = str.deleteCharAt(str.length()-1);
        str.append('}');
        return str.toString();
    }

    public String println(){
        StringBuffer str = new StringBuffer();
        boolean flag = false;
        str.append('{');
        for(JsonString key:this){
            str.append(key.print());
            str.append(':');
            str.append(get(key).print());
            str.append(',');
            str.append('\n');
            flag = true;
        }
        if(flag){
            int i = str.length();
            str = str.delete(i-2,i);
        }
        str.append('}');
        return str.toString();
    }
}
