package example.com.famss10;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Date;

public class DatabaseAccess {
    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase db;
    private static  DatabaseAccess instance;
    Cursor c = null;

    //private contructor so that object creation from outside the class is avoided
    private DatabaseAccess(Context context){
        this.openHelper=new DatabaseOpenHelper(context);
    }


    //to return the single instance of database
    public static DatabaseAccess getInstance (Context context){
        if (instance==null){
            instance=new DatabaseAccess(context);
        }
        return instance;
    }

    //to open database

    public void open(){
        this.db=openHelper.getWritableDatabase();

    }

    //closing the database connection

    public void close(){
        if(db!=null){
            this.db.close();
        }
    }

    //create a method to query and return the result from database
    //query for address by passing name
    public String getAddress(String name){
        c=db.rawQuery("select Address from Table1 where Name = '"+name+"'", new String[]{});
        StringBuffer buffer= new StringBuffer();
        while(c.moveToNext()){
            String Address = c.getString(0);
            buffer.append(""+Address);

        }
        return buffer.toString();
    }

    public void addCount(String name, String currency){
        db.execSQL("insert into Table1 (Name, Address) VALUES ('"+name+"','"+currency+"')", new String[]{});
    }



    public Cursor getUser(){
        Cursor c = db.rawQuery("select * from User", null);
        return c;
    }



    ////////////////////////////////////////USER/////////////////////////////////

    public void addUser (String name, String mdp, String sexe, String birthday,String Mail){
        db.execSQL("insert into User (Name, Psw, Gender, Birthday,Email) VALUES ('"+name+"','"+mdp+"','"+sexe+"','"+birthday+"','"+Mail+"')",new String[]{});
    }

    public String getAttribut(String Attribut, String name){
        c=db.rawQuery("select "+Attribut+" from User where Name = '"+name+"'", new String[]{});
        StringBuffer buffer= new StringBuffer();
        while(c.moveToNext()){
            String nom = c.getString(0);
            buffer.append(""+nom);

        }
        return buffer.toString();
    }



/*
    public String getGender(int id){
        c=db.rawQuery("select Gender from User where idUser = '"+id+"'", new String[]{});
        StringBuffer buffer= new StringBuffer();
        while(c.moveToNext()){
            String sexe= c.getString(0);
            buffer.append(""+sexe);
        }
        return buffer.toString();
    }

    public String getPassword(String nomUser){
        c=db.rawQuery("select Psw from User where Name = '"+nomUser+"'", new String[]{});
        StringBuffer buffer= new StringBuffer();
        while(c.moveToNext()){
            String password = c.getString(0);
            buffer.append(""+password);

        }
        return buffer.toString();
    }
    */


    public String UserExist(String name){
        c=db.rawQuery("select Name from User where Name = '"+name+"'", new String[]{});
        StringBuffer buffer= new StringBuffer();
        while(c.moveToNext()){
            String prenom = c.getString(0);
            buffer.append(""+prenom);

        }
        return buffer.toString();
    }




}
