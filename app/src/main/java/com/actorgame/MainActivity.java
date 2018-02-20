package com.actorgame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void findConnection(View view) {
        EditText editTextA = (EditText) findViewById(R.id.startActor);
        EditText editTextB = (EditText) findViewById(R.id.endActor);

        String actorA = editTextA.getText().toString();
        String actorB = editTextB.getText().toString();

        Intent intent = new Intent(this, ConnectionActivity.class);
        intent.putExtra("start", actorA);
        intent.putExtra("end", actorB);
        startActivity(intent);
    }
}
