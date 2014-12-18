package com.ohanhi.into_orbit;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Base64Coder;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter.OutputType;
import com.badlogic.gdx.utils.ObjectMap;

/**
 * Created by ohan on 14.11.2014.
 *
 * Based on the article at
 * http://www.toxsickproductions.com/libgdx/libgdx-intermediate-saving-and-loading-data-from-files/
 */
public class SaveManager {

    private boolean encoded;
    private FileHandle file;
    private Save save;
    private String localPath = Gdx.files.getLocalStoragePath();

    public SaveManager(boolean encoded){
        this.encoded = encoded;
        file = Const.SAVE_FILE;
        save = getSave();
        System.out.println(localPath);
    }
    public static class Save{
        public ObjectMap<String, Object> data = new ObjectMap<String, Object>();

    }

    private Save getSave() {
        Save save = new Save();

        if(file.exists()){
            Json json = new Json();
            if (encoded) save = json.fromJson(Save.class, Base64Coder.decodeString(file.readString()));
            else save = json.fromJson(Save.class,file.readString());
        }
        return save;
    }

    public void saveToJson() {
        Json json = new Json();
        json.setOutputType(OutputType.json);
        if (encoded) file.writeString(Base64Coder.encodeString(json.prettyPrint(save)), false);
        else file.writeString(json.prettyPrint(save), false);
    }

    @SuppressWarnings("unchecked")
    public <T> T loadDataValue(String key, Class type) {
        if (save.data.containsKey(key)) return (T)save.data.get(key);
        else return null;   // this if() avoids an exception, but check for null on load.
    }
    public void saveDataValue(String key, Object object) {
        save.data.put(key, object);
        saveToJson(); //Saves current save immediately.
    }
    public ObjectMap<String, Object> getAllData(){
        return save.data;
    }
}