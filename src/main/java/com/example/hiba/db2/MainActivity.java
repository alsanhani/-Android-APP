package com.example.hiba.db2;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText e1=null;
    EditText e2=null;
    EditText e3=null;
    ListView l;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        e1=(EditText)findViewById(R.id.editText);
        e2=(EditText)findViewById(R.id.editText2);
        e3=(EditText)findViewById(R.id.editText3);
        l=(ListView)findViewById(R.id.listView);


    }

    public void add(View view) {
        DBAdapter da=new DBAdapter(this);
        long id;
        da.OpenMyDb();
        id=da.insertcontact( e2.getText().toString(), e3.getText().toString());
        //id=da.insertcontact("hiba", "hibayahoo.com");
        Toast.makeText(this,id+"compelete",Toast.LENGTH_LONG).show();
        da.close();
    }

    public void Get(View view) {
        ArrayList<String> names=new ArrayList<String>();
        DBAdapter da=new DBAdapter(this);
        da.OpenMyDb();
       Cursor c=da.getcontact(Integer.parseInt(e1.getText().toString()));
       if(c.moveToFirst()) {
           for (int i = 0; i < 3; i++)
               names.add(c.getString(i));
           ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,names);
           l.setAdapter(adapter);
       }

        else
           Toast.makeText (this,"NotFound",Toast. LENGTH_LONG ).show();
      da.close();
    }
    private void display(Cursor c) {



        Toast.makeText(this, "id is"+c.getString(0)+"\n"+"name is"+c.getString(1)+"\n"+"email is"+"\n"+c.getString(2), Toast.LENGTH_LONG).show();
    }

    public void del(View view) {
        DBAdapter da=new DBAdapter(this);
        da.OpenMyDb();
       if(da.deletecontact(Integer.parseInt(e1.getText().toString())))
        Toast.makeText (this,"delete succeful",Toast. LENGTH_LONG ).show();
        else
        Toast.makeText (this,"delete faild",Toast. LENGTH_LONG ).show();
        da.close();

    }

    public void Getall(View view) {
        ArrayList<String> names=new ArrayList<String>();
        DBAdapter da=new DBAdapter(this);
        da.OpenMyDb();
        Cursor c=da.getallcontac();;

        if(c.moveToFirst())
        {
            do
            {
                for (int i = 0; i < 3; i++)
                    names.add(c.getString(i));
                ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,names);
                l.setAdapter(adapter);

               // display(c);
            }while(c.moveToNext());
        }

        else
            Toast.makeText (this,"NotFound",Toast. LENGTH_LONG ).show();
        da.close();


    }

    public void Update(View view) {
        DBAdapter da=new DBAdapter(this);
       da.OpenMyDb();
        if(da.update(Integer.parseInt(e1.getText().toString()), e2.getText().toString(), e3.getText().toString()))
        Toast.makeText (this,"update succeful",Toast. LENGTH_LONG ).show();
        else
        Toast.makeText (this,"update faild",Toast. LENGTH_LONG ).show();
        da.close();


    }
}
