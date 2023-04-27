package com.example.hiba.db2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
public class DBAdapter {
    static final String KEY_ROWID="_id";
    static final String KEY_NAME="name";
    static final String KEY_EMAIL="email";
    static final String  DATABASE_NAME="mydb1";
    static final String TABLE_NAME="contacts";
    static final int DATABASE_VERSION=1;
    static final String TAG="DBAdapter";
    private static final String DATABASE_CREATE =
            "create table contacts (_id integer primary key autoincrement, "
                    + "name text not null, email text not null);" ;
    DatabaseHelper databasehelper;
    final Context context;
    SQLiteDatabase db;
    public DBAdapter(Context ctx)
    {
        this.context=ctx;
        databasehelper=new DatabaseHelper( context);
    }
    private static class DatabaseHelper extends SQLiteOpenHelper
    {

        DatabaseHelper(Context context)
        {
            super(context, DATABASE_NAME,null,DATABASE_VERSION);
            // TODO Auto-generated constructor stub
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            // TODO Auto-generated method stub
            try
            {
                db.execSQL(DATABASE_CREATE);

            }catch(SQLiteException e){

                e.printStackTrace();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // TODO Auto-generated method stub

            Log.e(TAG,"upgrade database from"+oldVersion+"to new version"+newVersion);
            db.execSQL("DROP TABLE IF EXISTS contac");
            onCreate(db);
        }


    }//close helperclass which contain oncreate and upgrad now we return to adapter class to deal with database open,close,....
    public DBAdapter OpenMyDb()throws SQLException
    {
        Log.e("open database", "error");
        db=databasehelper.getWritableDatabase();
        return this;

    }
    public void close()

    {
        databasehelper.close();
    }
    public long insertcontact(String name,String email)
    {
        Log.e("insert database", "error");
        ContentValues content=new ContentValues();
        content.put(KEY_NAME, name);
        content.put(KEY_EMAIL, email);
        return db.insert(TABLE_NAME, null, content);

    }
    public boolean deletecontact(long rowid)
    {
        return db.delete(TABLE_NAME, KEY_ROWID+"="+rowid, null)>0;

    }
    public Cursor getallcontac()
    {
        return  db.query(TABLE_NAME, new String[]{KEY_ROWID,KEY_NAME,KEY_EMAIL},null,null,null,null,null);

    }
    public Cursor getcontact(long rowid)throws SQLException
    {
        Cursor mycursor;
        mycursor= db.query(TABLE_NAME, new String[]{KEY_ROWID,KEY_NAME,KEY_EMAIL}, KEY_ROWID+"="+rowid, null, null, null,null);
        if(mycursor!=null)
            mycursor.moveToFirst();
        return mycursor;


    }

    public boolean update(int rowid,String name,String email)
    {
        ContentValues values=new ContentValues();
        values.put(KEY_NAME, name);
        values.put(KEY_EMAIL, email);
        return db.update(TABLE_NAME, values, KEY_ROWID+"="+rowid, null)>0;


    }
}
