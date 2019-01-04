package example.com.famss10;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.Blob;
import java.sql.Time;
import java.util.ArrayList;
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


    //EXAMPLES
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

    public Cursor getUser(){
        Cursor c = db.rawQuery("select * from User", null);
        return c;
    }


    /////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////USER/////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////
    public void addUser (String name, String mdp, String sexe, java.sql.Date birthday, String Mail){
        db.execSQL("insert into User (Name, Psw, Gender, Birthday,Email) VALUES ('"+name+"','"+mdp+"','"+sexe+"','"+birthday+"','"+Mail+"')",new String[]{});
    }

    public String getStringAttributWhere(String select, String from,String where, String element){
        c=db.rawQuery("select "+select+" from "+from+" where "+where+" = '"+element+"'", new String[]{});
        StringBuffer buffer= new StringBuffer();
        while(c.moveToNext()){
            String nom = c.getString(0);
            buffer.append(""+nom);

        }
        return buffer.toString();
    }

    public String getStringAttributWhereInt(String select, String from,String where, int element){
        c=db.rawQuery("select "+select+" from "+from+" where "+where+" = '"+element+"'", new String[]{});
        StringBuffer buffer= new StringBuffer();
        while(c.moveToNext()){
            String nom = c.getString(0);
            buffer.append(""+nom);

        }
        return buffer.toString();
    }

    public String getStringAttributWhere2 (String select, String from, String where, String element, String where2, String element2){
        c=db.rawQuery("select "+select+" from "+from+" where "+where+" = '"+element+"' and "+where2+" = '"+element2+"'", new String[]{});
        StringBuffer buffer= new StringBuffer();
        while(c.moveToNext()){
            String nom = c.getString(0);
            buffer.append(""+nom);

        }
        return buffer.toString();
    }

    public int getIntAttributWhereInt(String select, String from, String where,int element ){
        c=db.rawQuery("select "+select+" from "+from+" where "+where+"= '"+element+"'", new String[]{});
        c.moveToFirst();
        int entier = c.getInt(0);
        return entier;
    }

/**
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
    public String UserExist(String name){
        c=db.rawQuery("select Name from User where Name = '"+name+"'", new String[]{});
        StringBuffer buffer= new StringBuffer();
        while(c.moveToNext()){
            String prenom = c.getString(0);
            buffer.append(""+prenom);

        }
        return buffer.toString();
    }
*/





    //////////////////////////////////////////////////
    /////////////////Select, delete, update //////////
    //////////////////////////////////////////////////
    public int getcounter (String select, String from, String where, String element){
        c=db.rawQuery("select "+select+" from "+from+" where "+where+"= '"+element+"'", new String[]{});
        return c.getCount();
    }

    public String getStringAttribut(String select, String from, String where,String element, int i ){
        c=db.rawQuery("select "+select+" from "+from+" where "+where+"= '"+element+"'", new String[]{});
        StringBuffer buffer= new StringBuffer();
        c.moveToPosition(i);
        String nom = c.getString(0);
        buffer.append(""+nom);
        return buffer.toString();
    }

    public int getintAttribut(String select, String from, String where,String element, int i ){
        c=db.rawQuery("select "+select+" from "+from+" where "+where+"= '"+element+"'", new String[]{});
        c.moveToPosition(i);
        int entier = c.getInt(0);
        return entier;
    }

    public String getLastStringAttribut(String select, String from, String where, String element){
        c=db.rawQuery("select "+select+" from "+from+" where "+where+"= '"+element+"'", new String[]{});
        StringBuffer buffer= new StringBuffer();
        c.moveToLast();
        String nom = c.getString(0);
        buffer.append(""+nom);
        return buffer.toString();
    }


    public void delete1 (String from, String where,String Element){
        db.execSQL("delete from "+from+" where "+where+" = '"+Element+"' ",new String []{});
    }


    public void delete2 (String from, String where,String Element,String where2, String Element2){
        db.execSQL("delete from "+from+" where "+where+" = '"+Element+"' and "+where2+" = '"+Element2+"'",new String []{});
    }

    public void updateStringById (String upDate, String set, String element, String where, int element2){
        db.execSQL("update "+upDate+" set "+set+" = '"+element+"' where "+where+" = '"+element2+"' ",new  String []{});

    }

    public void updateIntById (String upDate, String set, int element, String where, int element2){
        db.execSQL("update "+upDate+" set "+set+" = '"+element+"' where "+where+" = '"+element2+"' ",new  String []{});

    }





    /////////////////////////////////////////////
    ////////////////////COUNT////////////////////
    /////////////////////////////////////////////
    public void addCount (String namecount, String currency, String Email, float balance) {
        db.execSQL("insert into Count (NameCount, Currency, Email, Balance) VALUES ('"+namecount+"','"+currency+"','"+Email+"','"+balance+"')");
    }

    public float getBalance(Integer idCount){
        c=db.rawQuery("select Balance from Count where idCount= '"+idCount+"'", new String[]{});
        return c.getFloat(0);
    }

    public String getCountName(String name,String email){
        c=db.rawQuery("select NameCount from Count where NameCount= '"+name+"'", new String[]{});
        c.moveToLast();
        return c.getString(0);
    }

    public String getCountNameEmail(String name,String email){

        Cursor c = db.rawQuery("select Count.NameCount,User.Email from " +
                "User Inner join Count on (User.Email = Count.Email) where " +
                "User.Email = '"+email+"'and Count.NameCount='"+name+"'", new String[]{});
        return c.toString();
    }

    public int getCountIDNameEmail(String name,String email){

        Cursor c = db.rawQuery("select User.idUser from " +
                "User Inner join Count on (User.Email = Count.Email) where " +
                "User.Email = '"+email+"'and Count.NameCount='"+name+"'", new String[]{});
        return c.getInt(0);
    }

    public int getBalanceCountNameEmail(String name,String email){
        c = db.rawQuery("select Count.Balance from " +
                "User Inner join Count on (User.Email = Count.Email) where " +
                "User.Email = '"+email+"'and Count.NameCount='"+name+"'", new String[]{});

        return c.getInt(0);
    }

    public void setBalanceCountNameEmail(String name,String email,Float montant){
        db.execSQL("Update Count set Balance=montant where Email='"+email+"' and NameCount='"+name+"'");
        //return c.getFloat(0);
    }

/**
    public String getStringAttribut(String select, String from, int i ){
        c=db.rawQuery("select "+select+" from "+from+"", new String[]{});
        StringBuffer buffer= new StringBuffer();
        c.moveToPosition(i);
        String nom = c.getString(0);
        buffer.append(""+nom);
        return buffer.toString();
    }

    public String getLastStringAttribut(String select, String from){
        c=db.rawQuery("select "+select+" from "+from+"", new String[]{});
        StringBuffer buffer= new StringBuffer();
        c.moveToLast();
        String nom = c.getString(0);
        buffer.append(""+nom);
        return buffer.toString();
    }
    public int getcount (String select, String from){
        c=db.rawQuery("select "+select+" from "+from+"", new String[]{});
        return c.getCount();
    }
*/





    ////////////////////////////////////////////////
    ////////////////////Category////////////////////
    ////////////////////////////////////////////////
    public void addCategoryByName (String nom) {
        db.execSQL("insert into Category(idCategory) VALUES ('"+nom+"')", new String[]{});
    }

    public void addCategory (String nom,String color) {
        db.execSQL("insert into Category (idCategory,Color)VALUES ('"+nom+"'+'"+color+"')", new String[]{});
    }

    public String getCategory(String nom){
        c=db.rawQuery("select idCategory from Category", new String[]{});
        StringBuffer buffer= new StringBuffer();
        while(c.moveToNext()){

            String sexe= c.getString(0);
            if(sexe.equals(nom))
            buffer.append(""+sexe);
        }
        return buffer.toString() ;
    }

    public ArrayList<String> getToutNomCategory(){
        ArrayList<String> liste=new ArrayList<>();
        c=db.rawQuery("select idCategory from Category", new String[]{});
        StringBuffer buffer= new StringBuffer();
        while(c.moveToNext()){
            String sexe= c.getString(0);
            buffer.append(""+sexe);
            liste.add(c.getString(0));
        }
        return liste ;
    }





    ///////////////////////////////////////////////
    /////////////////Control///////////////////////
    //////////////////////////////////////////////
    public void addControl ( String Email, String EmailSupervisor, String relation){
        db.execSQL("insert into Control (EmailUser, EmailSupervisor,Relation) VALUES ('"+Email+"','"+EmailSupervisor+"','"+relation+"')", new String[]{});
    }

    public String UserControl (String where, String Element){
        c= db.rawQuery("select User.Email, User.Name, User.psw, Control.EmailUser,Control.EmailSupervisor from User left join Control ON User.Email = Control.EmailUser where "+where+" = '"+Element+"'",new String []{});
        return c.getString(0);
    }





    ///////////////////////////////////////////////
    /////////////////Creation//////////////////////
    ///////////////////////////////////////////////
    public void addCreation (int Quantity, int idDiary, int idSummary){
        db.execSQL("insert into Control (Quantity, idDiary, idSummary) VALUES ('"+Quantity+"','"+idDiary+"','"+idSummary+"')", new String[]{});
    }





    //////////////////////////////////////////////////
    ////////////////Diary/////////////////////////////
    //////////////////////////////////////////////////
    public void addDiary (java.sql.Date date){
        db.execSQL("insert into Diary (Date) VALUES ('"+date+"')", new String[]{});
    }

    public String diaryExist(java.sql.Date date){
        c=db.rawQuery("select Date from Diary where Date= '"+date+"'", new String[]{});
        return"ok";
    }

    public String getStringDate(String select, String from, String where, java.sql.Date element){
        c=db.rawQuery("select "+select+" from "+from+" where "+where+" = '"+element+"'", new String[]{});
        StringBuffer buffer= new StringBuffer();
        while(c.moveToNext()){
            String nom = c.getString(0);
            buffer.append(""+nom);

        }
        return buffer.toString();
    }

    public int getDiaryid(java.sql.Date date){
        c=db.rawQuery("select idDiary from Diary where Date= '"+date+"'", new String[]{});
        return c.getInt(0);
    }






    ///////////////////////////////////////////////////
    ///////////////Frequency////////////////////////////
    ///////////////////////////////////////////////////
    public void addFrequency (Integer nombre, java.sql.Date startdate, java.sql.Date enddate){
        db.execSQL("insert into Frequency (StartDate, EndDate, NbrRépétitions) VALUES ('"+startdate+"','"+enddate+"','"+nombre+"')", new String[]{});
    }

    public void addFrequencynbr (Integer nombre){
        db.execSQL("insert into Frequency (NbrRépétitions) VALUES ('"+nombre+"')", new String[]{});
    }

    public Integer getFrequencyID(Integer nombre,java.sql.Date startdate, java.sql.Date enddate){
        c=db.rawQuery("select idFrequency from Frequency where StartDate= '"+startdate+"'AND EndDate='"+enddate+"'AND NbrRépétitons='"+nombre+"'", new String[]{});
        return c.getInt(0);
    }

    public Integer getFrequencyRepetitions(Integer id){
        c=db.rawQuery("select NbrRépétitions from Frequency where idFrequency='"+id+"'", new String[]{});
        return c.getInt(0);
    }

    public ArrayList<String> getToutFrequency(){
        ArrayList<String> liste=new ArrayList<>();
        c=db.rawQuery("select NbrRépétitions from Frequency", new String[]{});
        StringBuffer buffer= new StringBuffer();
        while(c.moveToNext()){
            String sexe= c.getString(0);
            buffer.append(""+sexe);
            liste.add(sexe);
        }
        return liste ;
    }





    //////////////////////////////////////////////////
    ///////////////Summary////////////////////////////
    //////////////////////////////////////////////////
    public void addSummary (Blob graphic, java.sql.Date startdate, Date enddate){
        db.execSQL("insert into Summary (Graphic, StartDate, EndDate) VALUES ('"+graphic+"','"+startdate+"','"+enddate+"')", new String[]{});
    }





    ////////////////////////////////////////////////
    /////////////Supervisor/////////////////////////
    ///////////////////////////////////////////////

    public void addSupervisor( String EmailSupervisor){
        db.execSQL("insert into Supervisor (EmailSupervisor) VALUES ('"+EmailSupervisor+"')", new String[]{});
    }





    ///////////////////////////////////////////////////////
    ///////////////Transaction/////////////////////
    ///////////////////////////////////////////////////////

    public void addTransaction(String name,String notes,Integer mountant, String operation, String idCategory, Integer frequency,int idCount,Integer idDiary){
        db.execSQL("insert into Transactions (Name,Notes,Mountant,Operation,idCategory,Frequency,idCount,idDiary) VALUES ('"+name+"','"+notes+"','"+mountant+"','"+operation+"','"+idCategory+"','"+frequency+"','"+idCount+"','"+idDiary+"')", new String[]{});
    }

    public ArrayList<Float> getDepenses( String start, String end , int count){

        ArrayList<Float> liste=new ArrayList<>();

            Cursor c = db.rawQuery("select Transactions.Mountant from " +
                    "Transactions Inner join Diary on (Transactions.idDiary = Diary.idDiary) where " +
                    "Transactions.idCount = '"+count+"' and Date between '"+start+"' and '"+end+"' and Transactions.Operation = 'Type : Dépense' or Transactions.Operation = 'Type : Transfert'", new String[]{});

        StringBuffer buffer= new StringBuffer();
        while(c.moveToNext()){
            Float mountant= c.getFloat(0);
            buffer.append(""+mountant);
            liste.add(mountant);
            //System.out.println(liste);
        }
        return liste ;
    }

    public ArrayList<Float> getRevenus( String start, String end , int count){

        ArrayList<Float> liste=new ArrayList<>();
        Float mountant;

        Cursor c = db.rawQuery("select Transactions.Mountant from " +
                "Transactions Inner join Diary on (Transactions.idDiary = Diary.idDiary) where " +
                "Transactions.idCount = '"+count+"' and (Date between '"+start+"' and '"+end+"') and Transactions.Operation = 'Type : Revenu' ", new String[]{});

        StringBuffer buffer= new StringBuffer();
        while(c.moveToNext()){

            mountant= c.getFloat(0);
            buffer.append(""+mountant);
            liste.add(mountant);

        }
        Cursor c1 = db.rawQuery("select Transactions.Mountant from " +
                "Transactions Inner join Diary on (Transactions.idDiary = Diary.idDiary) where " +
                "Transactions.idBeneficiaryCount = '"+count+"' and (Date between '"+start+"' and '"+end+"') and Transactions.Operation = 'Type : Transfert'", new String[]{});


        while(c1.moveToNext()) {

            mountant = c1.getFloat(0);
            buffer.append("" + mountant);
            liste.add(mountant);
            //System.out.println(liste);
        }

        return liste ;
    }

    public Cursor getTransactions(int count){
        Cursor c = db.rawQuery("SELECT Transactions.Name , Transactions.Notes , Transactions.Mountant , Transactions.Operation , Transactions.idCategory , Diary.Date FROM " +
                "Transactions INNER JOIN Diary ON (Transactions.idDiary = Diary.idDiary) WHERE " +
                "Transactions.idCount = '"+count+"'" +
                "ORDER BY Diary.Date",null);
        return c;
    }




    /////////////////////////////////////////////
    //////////////Type///////////////////////////
    /////////////////////////////////////////////
    public void addType( String idOperation){
        db.execSQL("insert into Type (idOperation) VALUES ('"+idOperation+"')", new String[]{});
    }


}
