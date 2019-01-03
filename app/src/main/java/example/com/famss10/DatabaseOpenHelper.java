package example.com.famss10;

import android.content.Context;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class DatabaseOpenHelper extends SQLiteAssetHelper {
    //private static final String DATABASE_NAME="MyExternalDataBase.db";
    private static final String DATABASE_NAME="BddDeTest.db";
    private static final int DATABASE_VERSION=3;

    //Constructor

    public DatabaseOpenHelper(Context context){
        super(context, DATABASE_NAME, null,DATABASE_VERSION);
        setForcedUpgrade();
    }
}
