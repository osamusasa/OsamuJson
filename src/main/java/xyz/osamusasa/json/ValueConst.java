package xyz.osamusasa.json;

public class ValueConst implements JsonToken {
    public static final JsonToken NULL_TOKEN = new ValueConst(2);
    final String[] value = {"true","false","null"};
    private int pointer;

    ValueConst(int pointer){
        if(pointer<0||2<pointer)this.pointer=2;
        else this.pointer = pointer;
    }

    @Override
    public int size(){
        return 1;
    }
    @Override
    public JsonStream<ValueConst> stream(){
        return new JsonStream<>(this);
    }
    @Override public String toString(){
        return value[pointer];
    }
    @Override public String print(){
        return value[pointer];
    }
}
