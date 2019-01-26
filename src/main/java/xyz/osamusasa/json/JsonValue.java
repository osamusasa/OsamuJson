package xyz.osamusasa.json;

public class JsonValue {
    private JsonToken value;
    private String value_name;

    private JsonValue(JsonToken token){
        this.value = token;
        this.setName();
    }

    @Override public String toString(){
        return "Value";
    }
    private void setName(){
        this.value_name=value.toString();
    }
    public static JsonValue create(JsonToken t){return new JsonValue(t);}
    public static JsonValue create(int n){return JsonValue.create(JsonNumber.create(n));}
    public static JsonValue create(String s){return JsonValue.create(JsonString.create(s));}
    public static JsonValue createObj(JsonObject o){
        return new JsonValue(o);
    }
    static JsonValue create(JsonTokener tokener){
        //JsonValue val;
        switch(tokener.whichBegin()){
            case 0:{
                return new JsonValue(JsonString.create(tokener));
            }
            case 1:{
                return new JsonValue(JsonNumber.create(tokener));
            }
            case 2:{
                return new JsonValue(JsonObject.create(tokener));
            }
            case 3:{
                return new JsonValue(JsonArray.create(tokener));
            }
            case 4:{
                return new JsonValue(new ValueConst(0));
            }
            case 5:{
                return new JsonValue(new ValueConst(1));
            }
            case 6:{
                return new JsonValue(new ValueConst(2));
            }
            default:{
                throw new JsonException("Syntax error:not exist value content at line " + tokener.line);
            }
        }
    }
    public String print(){
        return this.value.print();
    }
    public JsonToken getValue(){
        return this.value;
    }
}
