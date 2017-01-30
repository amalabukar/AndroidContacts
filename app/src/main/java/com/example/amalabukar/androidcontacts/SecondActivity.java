package com.example.amalabukar.androidcontacts;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SecondActivity extends AppCompatActivity  implements View.OnClickListener {

    EditText newName;
    EditText newPhone;

    int position = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);


        newName = (EditText) findViewById(R.id.editText2);
        newPhone = (EditText) findViewById(R.id.editText);

        String newString=getIntent().getExtras().getString("contacts");
        String linepart[]=newString.split(" : ");
        newName.setText(linepart[0]);
        newPhone.setText(linepart[1]);
        position=getIntent().getExtras().getInt("position",0);

        Button button=(Button) findViewById(R.id.button);
        button.setOnClickListener(this);


    }
    @Override
    public void onClick(View v) {
        String cname = newName.getText().toString();
        String pname=newPhone.getText().toString();
        String contacts= (cname + " : " + pname);
        Intent returnIntent= new Intent();
        returnIntent.putExtra("contacts",contacts);
        returnIntent.putExtra("position", position);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();

    }
}
