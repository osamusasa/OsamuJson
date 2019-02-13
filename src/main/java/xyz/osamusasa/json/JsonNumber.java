package xyz.osamusasa.json;

public class JsonNumber implements JsonToken {
    String num;

    private JsonNumber(String s){
        num=s;
    }

    @Override public String toString(){
        return "Number";
    }
    static JsonNumber create(){
        return new JsonNumber("0");
    }
    public static JsonNumber create(int n){return new JsonNumber(Integer.toString(n));}
    static JsonNumber create(JsonTokener tokener){
        JsonNumber jnum = JsonNumber.create();
        StringBuilder str = new StringBuilder();
        while(!tokener.isNumberEnd()){
            str.append(tokener.next());
        }
        jnum.num = str.toString();
        return jnum;
    }
    @Override public String print(){
        return num;
    }
    @Override
    public int size(){
        return 1;
    }
    @Override
    public JsonStream<JsonNumber> stream(){
        return new JsonStream<>(this);
    }
    public int getValue(){
        return Integer.parseInt(this.num);
    }
    public JsonString getString(){
        return JsonString.create(num);
    }
}
