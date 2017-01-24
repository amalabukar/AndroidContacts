package com.example.amalabukar.androidcontacts;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;



public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemLongClickListener {
    ArrayAdapter<String> contacts;

    EditText name;
    EditText phone;
    ListView list;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        list = (ListView) findViewById(R.id.list);

        button= (Button) findViewById(R.id.button);

        name = (EditText) findViewById(R.id.name);
       phone= (EditText) findViewById(R.id.phone);


        contacts = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        list.setAdapter(contacts);

        button.setOnClickListener(this);
        list.setOnItemLongClickListener(this);
    }

    @Override
    public void onClick(View view) {
        String n = name.getText().toString();
        String p= phone.getText().toString ();
        contacts.add(n + " " + p);
        name.setText(" ");
        phone.setText(" ");
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
        String contact = contacts.getItem(position);
        contacts.remove(contact);
        return true;
    }
}