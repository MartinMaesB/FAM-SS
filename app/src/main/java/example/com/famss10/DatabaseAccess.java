package example.com.famss10;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.Blob;
import java.util.ArrayList;
import java.util.Date;

public class DatabaseAccess {
    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase db;
    private static  DatabaseAccess instance;
    Cursor c = null , c1 = null;

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




//--------------------------------------------------------REQUÊTES---------------------------------------------------------





    //------------------------------------------------GLOBAL--------------------------------------------------------

    // --------------------------------------------Get-------------------------------------------------


    public String getStringAttribut(String select, String from, String where,String element, int i ){

        c=db.rawQuery("select "+select+" from "+from+" where "+where+"= '"+element+"'", new String[]{});

        StringBuffer buffer= new StringBuffer();
        c.moveToPosition(i);
        String nom = c.getString(0);
        buffer.append(""+nom);
        return buffer.toString();
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


    public String getStringAttributWhere2 (String select, String from, String where, String element, String where2, String element2){

        c=db.rawQuery("select "+select+" from "+from+" " +
                "where "+where+" = '"+element+"' and "+where2+" = '"+element2+"'", new String[]{});

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


    public int getIntAttributWhereInt(String select, String from, String where,int element ){

        c=db.rawQuery("select "+select+" from "+from+" where "+where+"= '"+element+"'", new String[]{});

        c.moveToFirst();
        int entier = c.getInt(0);
        return entier;
    }


    public int getIntAttributWhereDate(String select, String from, String where, java.sql.Date element ){

        c=db.rawQuery("select "+select+" from "+from+" where "+where+"= '"+element+"'", new String[]{});

        c.moveToFirst();
        int entier = c.getInt(0);
        return entier;
    }

    public int getIntAttributWhereDate2(String select, String from, String where, String where2, String where3 , int répétition,java.sql.Date element, java.sql.Date element2){

        c=db.rawQuery("select '"+select+"' from '"+from+"' where '"+where+"' = '"+répétition+"' and '"+where2+"' = '"+element+"' and  '"+where3+"' = '"+element2+"'",null);
        return c.getInt(0);
    }


    public int getcounter (String select, String from, String where, String element){

        c=db.rawQuery("select "+select+" from "+from+" where "+where+"= '"+element+"'", new String[]{});
        return c.getCount();
    }



    // --------------------------------------------Update----------------------------------------------


    public void updateStringById (String upDate, String set, String element, String where, int element2){

        db.execSQL("update "+upDate+" set "+set+" = '"+element+"' where "+where+" = '"+element2+"' ",new  String []{});

    }


    public void updateIntById (String upDate, String set, int element, String where, int element2){

        db.execSQL("update "+upDate+" set "+set+" = '"+element+"' where "+where+" = '"+element2+"' ",new  String []{});

    }


    // ------------------------------------------Delete------------------------------------------------


    public void delete1 (String from, String where,String Element){

        db.execSQL("delete from "+from+" where "+where+" = '"+Element+"' ",new String []{});
    }


    public void delete2 (String from, String where,String Element,String where2, String Element2){

        db.execSQL("delete from "+from+" where "+where+" = '"+Element+"' and "+where2+" = '"+Element2+"'",new String []{});
    }








    //------------------------------------------------USER--------------------------------------------------------


    public Cursor getUser(){

        c = db.rawQuery("select * from User", null);
        return c;
    }


    public void addUser (String name, String mdp, String sexe, java.sql.Date birthday, String Mail){

        db.execSQL("insert into User (Name, Psw, Gender, Birthday,Email) " +
                "VALUES ('"+name+"','"+mdp+"','"+sexe+"','"+birthday+"','"+Mail+"')",new String[]{});
    }











    //------------------------------------------------COUNT--------------------------------------------------------


    public void addCount (String namecount, String currency, String Email, float balance) {

        db.execSQL("insert into Count (NameCount, Currency, Email, Balance) " +
                "VALUES ('"+namecount+"','"+currency+"','"+Email+"','"+balance+"')");
    }


    public Cursor getCount(String Email){

        c = db.rawQuery(" select Count.idCount, Count.NameCount, Count.Currency, Count.Balance " +
                "from Count where Email = '"+Email+"' ",null);
        return c;
    }


    public Cursor getCountEnfant(String EmailSupervisor){

        c = db.rawQuery(" select Control.EmailUser, Control.Relation, Count.idCount, Count.NameCount, Count.Currency,Count.Balance  " +
                "from Control inner join Count on (Control.EmailUser = Count.Email) " +
                "where  Control.EmailSupervisor = '"+EmailSupervisor+"' ",null);
        return c;
    }


    public String getCountNameEmail(String name,String email){

        c = db.rawQuery("select Count.NameCount,User.Email from " +
                "User Inner join Count on (User.Email = Count.Email) where " +
                "User.Email = '"+email+"'and Count.NameCount='"+name+"'", new String[]{});
        return c.toString();
    }


    public int getCountIDNameEmail(String name,String email){

        c = db.rawQuery("select User.idUser from " +
                "User Inner join Count on (User.Email = Count.Email) where " +
                "User.Email = '"+email+"'and Count.NameCount='"+name+"'", new String[]{});
        return c.getInt(0);
    }










//------------------------------------------------CATEGORY--------------------------------------------------------


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







    //------------------------------------------------CONTROL--------------------------------------------------------

    public void addControl ( String Email, String EmailSupervisor, String relation){

        db.execSQL("insert into Control (EmailUser, EmailSupervisor,Relation) VALUES ('"+Email+"','"+EmailSupervisor+"','"+relation+"')", new String[]{});
    }









    //------------------------------------------------DIARY--------------------------------------------------------

    public void addDiary (java.sql.Date date){

        db.execSQL("insert into Diary (Date) VALUES ('"+date+"')", new String[]{});
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






    //------------------------------------------------FREQUENCY--------------------------------------------------------

    public void addFrequency (Integer nombre, java.sql.Date startdate, java.sql.Date enddate){

        db.execSQL("insert into Frequency (StartDate, EndDate, DescriptionF) " +
                "VALUES ('"+startdate+"','"+enddate+"','"+nombre+"')", new String[]{});
    }


    public boolean getFrequencyID(Integer nombre,java.sql.Date startdate, java.sql.Date enddate){
        boolean a=false;
        c=db.rawQuery("select idFrequency from Frequency where StartDate= '"+startdate+"' AND EndDate='"+enddate+"' AND DescriptionF='"+nombre+"'", new String[]{});

        if(c.moveToNext()){
            a=true;
        }
        return a;
    }










    //------------------------------------------------SUPERVISOR--------------------------------------------------------


    public void addSupervisor( String EmailSupervisor){
        db.execSQL("insert into Supervisor (EmailSupervisor) VALUES ('"+EmailSupervisor+"')", new String[]{});
    }





    //------------------------------------------------TRANSACTIONS-------------------------------------------------------


    public void addTransaction(String name,String notes,Integer mountant, String operation, String idCategory, int frequency,int idCount,int idDiary){

        db.execSQL("insert into Transactions (Name,Notes,Mountant,Operation,idCategory,Frequency,idCount,idDiary) " +
                "VALUES ('"+name+"','"+notes+"','"+mountant+"','"+operation+"','"+idCategory+"','"+frequency+"','"+idCount+"','"+idDiary+"')", new String[]{});
    }

    public void addTransactionTransfert(String name,String notes,Integer mountant, String operation, String idCategory, int frequency,int beneficiarycount,int idCount,int idDiary){

        db.execSQL("insert into Transactions (Name,Notes,Mountant,Operation,idCategory,Frequency,idCount,idDiary,idBeneficiaryCount) " +
                "VALUES ('"+name+"','"+notes+"','"+mountant+"','"+operation+"','"+idCategory+"','"+frequency+"','"+idCount+"','"+idDiary+"','"+beneficiarycount+"')", new String[]{});
    }



    public Cursor getTransactions(int count){

        c = db.rawQuery("SELECT Transactions.Name , Transactions.Notes , Transactions.Mountant , Transactions.Operation , Transactions.idCategory , Diary.Date " +
                "FROM Transactions INNER JOIN Diary ON (Transactions.idDiary = Diary.idDiary) " +
                "WHERE Transactions.idCount = '"+count+"'" +
                "ORDER BY Diary.Date DESC",null);
        return c;
    }



    public ArrayList<Float> getDepenses( String start, String end , int count){

        ArrayList<Float> liste=new ArrayList<>();

            c = db.rawQuery("select Transactions.idTransaction , Transactions.Mountant " +
                    "from Transactions Inner join Diary on (Transactions.idDiary = Diary.idDiary) " +
                    "where Transactions.idCount = '"+count+"' and Date between '"+start+"' and '"+end+"' and Transactions.Operation != 'Type : Revenu'", new String[]{});

        StringBuffer buffer= new StringBuffer();
        while(c.moveToNext()){
            Float mountant= c.getFloat(1);
            buffer.append(""+mountant);
            liste.add(mountant);
            System.out.println("dep" +c.getInt(0));
        }
        return liste ;
    }


    public ArrayList<Float> getRevenus( String start, String end , int count){

        ArrayList<Float> liste=new ArrayList<>();
        Float mountant;

        c = db.rawQuery("select Transactions.idTransaction , Transactions.Mountant " +
                "from Transactions Inner join Diary on (Transactions.idDiary = Diary.idDiary) " +
                "where Transactions.idCount = '"+count+"' and (Date between '"+start+"' and '"+end+"') and Transactions.Operation = 'Type : Revenu' ", new String[]{});

        StringBuffer buffer= new StringBuffer();
        while(c.moveToNext()){
            mountant= c.getFloat(1);
            buffer.append(""+mountant);
            liste.add(mountant);
            //System.out.println("rev" +c.getInt(0));
        }

        c1 = db.rawQuery("select Transactions.idTransaction , Transactions.Mountant from " +
                "Transactions Inner join Diary on (Transactions.idDiary = Diary.idDiary) where " +
                "Transactions.idBeneficiaryCount = '"+count+"' and (Date between '"+start+"' and '"+end+"') and Transactions.Operation = 'Type : Transfert'", new String[]{});

        while(c1.moveToNext()) {
            mountant = c1.getFloat(1);
            buffer.append("" + mountant);
            liste.add(mountant);
            //System.out.println("rev trans" +c1.getInt(0));
        }
        return liste ;
    }



    public ArrayList<Float> getDepenseByCategoryFloat(String start, String end , int count){
        ArrayList<Float> liste=new ArrayList<>();
        Float mountant;

        c = db.rawQuery("select Transactions.idCategory, SUM(Transactions.Mountant) " +
                "from Transactions Inner join Diary on (Transactions.idDiary = Diary.idDiary) " +
                "where Transactions.idCount = '"+count+"' and Date between '"+start+"' and '"+end+"' and Transactions.Operation != 'Type : Revenu'" +
                "GROUP BY Transactions.idCategory", new String[]{});

        StringBuffer buffer= new StringBuffer();
        while(c.moveToNext()){
            mountant= c.getFloat(1);
            buffer.append(""+mountant);
            liste.add(mountant);
        }
        return liste;
    }

    public ArrayList<String> getDepenseByCategoryNames(String start, String end , int count){
        ArrayList<String> liste=new ArrayList<>();
        String name;

        c = db.rawQuery("select Transactions.idCategory, SUM(Transactions.Mountant) " +
                "from Transactions Inner join Diary on (Transactions.idDiary = Diary.idDiary) " +
                "where Transactions.idCount = '"+count+"' and Date between '"+start+"' and '"+end+"' and Transactions.Operation != 'Type : Revenu'" +
                "GROUP BY Transactions.idCategory", new String[]{});

        StringBuffer buffer= new StringBuffer();
        while(c.moveToNext()){
            name= c.getString(0);
            buffer.append(""+name);
            liste.add(name);
        }
        return liste;
    }


    public ArrayList<Float> getRevenusByCategiryFloat(String start, String end , int count){
        ArrayList<Float> liste=new ArrayList<>();
        Float mountant;

        c = db.rawQuery("select Transactions.idCategory, SUM(Transactions.Mountant) " +
                "from Transactions Inner join Diary on (Transactions.idDiary = Diary.idDiary) " +
                "where Transactions.idCount = '"+count+"' and Date between '"+start+"' and '"+end+"' and Transactions.Operation = 'Type : Revenu'" +
                "GROUP BY Transactions.idCategory", new String[]{});

        StringBuffer buffer= new StringBuffer();
        while(c.moveToNext()){
            mountant= c.getFloat(1);
            buffer.append(""+mountant);
            liste.add(mountant);
        }
        return liste;
    }


    public ArrayList<String> getRevenusByCategiryNames(String start, String end , int count){
        ArrayList<String> liste=new ArrayList<>();
        String name;

        c = db.rawQuery("select Transactions.idCategory, SUM(Transactions.Mountant) " +
                "from Transactions Inner join Diary on (Transactions.idDiary = Diary.idDiary) " +
                "where Transactions.idCount = '"+count+"' and Date between '"+start+"' and '"+end+"' and Transactions.Operation = 'Type : Revenu'" +
                "GROUP BY Transactions.idCategory", new String[]{});

        StringBuffer buffer= new StringBuffer();
        while(c.moveToNext()){
            name= c.getString(0);
            buffer.append(""+name);
            liste.add(name);
        }
        return liste;
    }

}
