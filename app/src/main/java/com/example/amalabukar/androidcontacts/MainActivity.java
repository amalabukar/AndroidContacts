package com.example.amalabukar.androidcontacts;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;


public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemLongClickListener, AdapterView.OnItemClickListener {
    ArrayAdapter<String> contacts;

    public static final int CONTACTS_REQUEST = 1;
    final String FILENAME = "contact.csv";

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
        loadcontact();
        list.setAdapter(contacts);

        button.setOnClickListener(this);
        list.setOnItemLongClickListener(this);
        list.setOnItemClickListener(this);
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
    public boolean onItemLongClick(AdapterView<?> adapterView, View view,  int position, long l) {
        final String contact = contacts.getItem(position);
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("contacts");
        builder.setMessage("Remove?");
        builder.setPositiveButton(android.R.string.ok, new AlertDialog.OnClickListener() {


            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                contacts.remove(contact);
                savecontact();
            }
        });

        builder.setNegativeButton(android.R.string.cancel, new AlertDialog.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
        return true;
    }
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        Intent intent = new Intent(this, SecondActivity.class);
        intent.putExtra("contacts",contacts.getItem(position));
        intent.putExtra("position", position);
        startActivityForResult(intent, CONTACTS_REQUEST);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CONTACTS_REQUEST) {
            if (resultCode == RESULT_OK) {
                int position = data.getIntExtra("position", 0);
                String contact = data.getStringExtra("contacts");
                contacts.remove(contacts.getItem(position));
                contacts.notifyDataSetChanged();
                contacts.add(contact);
                savecontact();
            }
        }
    }

    private void savecontact() {
        try {
            FileOutputStream fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);

            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < contacts.getCount(); i++) {
                String contact = contacts.getItem(i);
                sb.append(contact + "\n");
            }
            fos.write(sb.toString().getBytes());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
        private void loadcontact() {
            try {
                FileInputStream fis = openFileInput(FILENAME);
                InputStreamReader isr = new InputStreamReader(fis);
                BufferedReader scanner = new BufferedReader(isr);
                while (scanner.ready()) {
                    String contact = scanner.readLine();
                    contacts.add(contact);
                }
            } catch (IOException e) {
                e.printStackTrace();

    }
    }

}
