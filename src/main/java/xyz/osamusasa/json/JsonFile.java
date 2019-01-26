package xyz.osamusasa.json;

import jdk.internal.jline.internal.Nullable;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.function.Consumer;

public class JsonFile {
    protected final File JSON_FILE;
    protected JsonObject jsonObj;

    /**
     * Create a new JsonFile instance using {@param file} path.
     *
     * same to use {@code JsonFile(file, null);}.
     *
     * @param file  Access to the Json file pointed to by the file path.
     */
    protected JsonFile(File file){
        this(file, null);
    }

    /**
     * Create a new JsonFile instance using preprocessed {@param file} path on {@param c}.
     *
     * @param file  Access to the Json file pointed to by the file path.
     *
     * @param c     If {@param file} need to be preprocessing, set this param.
     */
    protected JsonFile(File file, @Nullable Consumer<File> c){
        if(c != null){
            c.accept(file);
        }
        JSON_FILE = file;
        jsonObj = JsonTokener.getObject(JSON_FILE);
    }

    /**
     * Update the the entity of Json File.
     *
     * if change {@code jsonObj}, must be called.
     */
    public void save(){
        try{
            FileWriter w = new FileWriter(JSON_FILE, false);
            w.write(jsonObj.print());
            w.flush();
            w.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
