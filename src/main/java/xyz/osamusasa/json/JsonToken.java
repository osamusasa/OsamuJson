package xyz.osamusasa.json;

public interface JsonToken {
    /**
     * Return the number of contains values.
     *
     * @return the number of contains values.
     */
    int size();

    String print();

    default JsonToken createValue(JsonTokener tokener){
        switch(tokener.whichBegin()){
            case 0:{
                return JsonString.create(tokener);
            }
            case 1:{
                return JsonNumber.create(tokener);
            }
            case 2:{
                return JsonObject.create(tokener);
            }
            case 3:{
                return JsonArray.create(tokener);
            }
            case 4:{
                return new ValueConst(0);
            }
            case 5:{
                return new ValueConst(1);
            }
            case 6:{
                return new ValueConst(2);
            }
            default:{
                throw new JsonException("Syntax error:not exist value content at line " + tokener.line);
            }
        }
    }
}
