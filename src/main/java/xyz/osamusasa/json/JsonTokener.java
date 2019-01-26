package xyz.osamusasa.json;

import java.io.*;

public class JsonTokener {
    private boolean eof;
    private Reader reader;
    private int previous;
    private boolean usePrevious;		//true:not use, false:used
    long line;
    long point;
    private static final boolean NOT_USED = true;
    private static final boolean USED = false;

    public JsonTokener(Reader reader){
        this.reader = reader;
        this.eof = false;
        this.previous = 0;
        this.usePrevious = false;
        this.line = 1;
        this.point = 1;
    }
    public JsonTokener(String string){
        this(new StringReader(string));
    }

    public JsonObject getObject(){
        return JsonObject.create(this);
    }
    public static JsonObject getObject(File f){
        try {
            return new JsonTokener(new FileReader(f)).getObject();
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }
        return JsonObject.create();
    }

    char next(){
        int c;
        if(this.usePrevious){
            this.usePrevious = false;
            return (char)this.previous;
        }
        try{
            c = reader.read();
        }catch(IOException e){
            System.out.println(e);
            c = -1;
        }
        if(c<0){
            this.eof = true;
            c = 0;
        }
        this.point++;
        if(c==10){
            this.line++;
            this.point=1;
        }
        this.previous = c;
        return (char)c;
    }
    private boolean next(char query){
        return this.next(query,NOT_USED);
    }
    private boolean next(char query,boolean use){
        char c = this.next();
        this.usePrevious = use;
        return c == query;
    }
    String nextString(char spliter){
        StringBuilder str = new StringBuilder();
        this.skipVoid();
        while(!this.next(spliter)){
            str.append(this.next());
        }
        this.usePrevious = USED;
        return str.toString();
    }
    private void skipVoid(){
        int c;
        while(true){
            c = next();
            if(!(c == 9||c == 10||c == 13||c == 32))break;
        }
        this.previous = c;
        this.usePrevious = NOT_USED;
    }
    private boolean check(char c){
        return check(c, USED);
    }
    private boolean check(char c, boolean used){
        this.skipVoid();
        if(this.next(c)){
            this.usePrevious=used;
            return true;
        }else{
            return false;
        }
    }
    boolean check(String c, boolean used, boolean and_or){
        //and_or ? and : or
        for(int i=0;i<c.length();i++){
            if(check(c.charAt(i),used) ^ and_or){
                return true;
            }
        }
        return false;
    }
    boolean isObjectBegin(){
        return this.check('{');
    }
    boolean isObjectSpliter(){
        return this.check(':');
    }
    boolean isObjectPairSpliter(){
        return this.check(',');
    }
    boolean isObjectEnd(){
        return this.check('}');
    }
    boolean isStringBegin(){
        return this.check('"');
    }
    boolean isStringEnd(){
        return this.check('"');
    }
    boolean isArrayBegin(){
        return this.check('[');
    }
    boolean isArraySpliter(){
        return this.check(',');
    }
    boolean isArrayEnd(){
        return this.check(']');
    }
    boolean isNumberBegin(){
        return check("-0123456789",NOT_USED,false);
    }
    boolean isNumberEnd(){
        return !check("-0123456789.eE+-",NOT_USED,false);
    }
    boolean isTrue(){
        return check("true",NOT_USED,true);
    }
    boolean isFalse(){
        return check("false", NOT_USED, true);
    }
    boolean isNull(){
        return check("null",NOT_USED,true);
    }
    int whichBegin(){			//0:string 1:number 2:object 3:array 4:true 5:false 6:null -1:except
        this.skipVoid();
        if(this.isStringBegin()){this.usePrevious=NOT_USED;return 0;}
        else if(this.isNumberBegin()){this.usePrevious=NOT_USED;return 1;}
        else if(this.isObjectBegin()){this.usePrevious=NOT_USED;return 2;}
        else if(this.isArrayBegin()){this.usePrevious=NOT_USED;return 3;}
        else if(this.isTrue()){this.usePrevious=NOT_USED;return 4;}
        else if(this.isFalse()){this.usePrevious=NOT_USED;return 5;}
        else if(this.isNull()){this.usePrevious=NOT_USED;return 6;}
        else return -1;
    }
}
