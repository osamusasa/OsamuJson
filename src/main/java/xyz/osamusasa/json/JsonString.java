package xyz.osamusasa.json;

public class JsonString implements JsonToken {
    private String str;

    private JsonString(String str){
        this.str = str;
    }

    @Override public String toString(){
        return "String";
    }
    @Override public int hashCode(){return str.hashCode();}
    public static JsonString create(){
        return new JsonString("");
    }
    public static JsonString create(String s){
        return new JsonString(s);
    }
    public static JsonString create(int n){ return new JsonString(Integer.toString(n));}
    static JsonString create(JsonTokener tokener){
        if(!tokener.isStringBegin())throw new JsonException("Syntax error:not exist double quotation at line " + tokener.line);
        JsonString jstr = JsonString.create();
        StringBuilder str = new StringBuilder();
        while(!tokener.isStringEnd()){
            str.append(tokener.next());
        }
        jstr.str = str.toString();
        return jstr;
    }
    @Override public String print(){
        return "\""+str+"\"";
    }
    @Override public boolean equals(Object obj){
        if(!(obj instanceof JsonString)){
            return false;
        }
        JsonString js = (JsonString)obj;
        return this.str.equals(js.str);
    }

    public String getValue(){
        return this.str;
    }
}
