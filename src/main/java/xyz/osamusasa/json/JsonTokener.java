package xyz.osamusasa.json;

import java.io.*;

public class JsonTokener {
    private boolean eof;
    private Reader reader;

    /**
     * The buffer for read-ahead.
     *
     * only use if {@param usePrevious} is true.
     */
    private int previous;

    /**
     * If only read-ahead usePrevious is true(not used) and not is false(used).
     *
     * if usePrevious is true, {@code next()} return {@param previous} and set false.
     */
    private boolean usePrevious;
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

    /**
     * Return the next char.
     *
     * This method consumption read-ahead buffer.
     * If {@code IOException} is thrown when read new char, return 0.
     *
     * @return the next char.
     */
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

    /**
     * Check next char is {@param query}.
     *
     * This method don't consumption read-ahead buffer.
     *
     * @param query is char that we expect next char.
     * @return true if next char equal to query, otherwise false.
     */
    private boolean next(char query){
        return this.next(query,NOT_USED);
    }

    /**
     * Check next char is {@param query} and set {@param usePrevious} {@param use}.
     *
     * This method consumption read-ahead buffer if {@param use} is true.
     *
     * @param query is char that we expect next char.
     * @param use decide to consumption read-ahead buffer or not.
     * @return true if next char equal to query, otherwise false.
     */
    private boolean next(char query,boolean use){
        char c = this.next();
        this.usePrevious = use;
        return c == query;
    }

    /**
     * Cut String before {@param spliter}.
     *
     * {@param spliter} have been consumption.
     *
     * @param spliter is delimiter.
     * @return the String that is cut before {@param spliter}.
     */
    String nextString(char spliter){
        StringBuilder str = new StringBuilder();
        this.skipVoid();
        while(!this.next(spliter)){
            str.append(this.next());
            if(eof){
                str.deleteCharAt(str.length()-1);
                break;
            }
        }
        this.usePrevious = USED;
        return str.toString();
    }

    /**
     * Skip continuous void character.
     *
     * The void character that skip in this method is Horizontal tabulation,
     * newline, carriage return and space.
     */
    private void skipVoid(){
        int c;
        while(true){
            c = next();
            if(!(c == 9||c == 10||c == 13||c == 32))break;
        }
        this.previous = c;
        this.usePrevious = NOT_USED;
    }

    /**
     * Check if a next effective character is {@param c}.
     *
     * Check next char is {@param c} after call {@code skipVoid()}.
     * This method consumption read-ahead buffer.
     *
     * @param c is char that we expect next effective char.
     * @return true if next effective char equal to {@param c}, otherwise false.
     */
    private boolean check(char c){
        return check(c, USED);
    }

    /**
     * Check if a next effective character is {@param c}.
     *
     * Check next char is {@param c} after call {@code skipVoid()}.
     * This method consumption read-ahead buffer if {@param used} is true.
     *
     * @param c is char that we expect next effective char.
     * @param used decide to consumption read-ahead buffer or not.
     * @return true if next effective char equal to {@param c}, otherwise false.
     */
    private boolean check(char c, boolean used){
        this.skipVoid();
        if(this.next(c)){
            this.usePrevious=used;
            return true;
        }else{
            return false;
        }
    }

    /**
     * Compare a next character and {@param c}.
     *
     * If {@param and_or} is true, return the if {@param c} is equal to next
     * character sequences. If {@param and_or} is false, check if a next
     * effective character is any one of {@param c}.
     * This method consumption read-ahead buffer if {@param used} is true.
     *
     * @param c is character set that we want to compare the next sequence.
     * @param used decide to consumption read-ahead buffer or not.
     * @param and_or is condition of each compare with one of character from
     *        {@param c} and next sequence.
     * @return the compare each character of {@param c} and combine with {@param and_or}.
     */
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
        return check("true",USED,true);
    }
    boolean isFalse(){
        return check("false", USED, true);
    }
    boolean isNull(){
        return check("null",USED,true);
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
