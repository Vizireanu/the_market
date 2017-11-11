package com.example.andrei.thecheapest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import static com.example.andrei.thecheapest.R.id.spinner;

public class MainActivity extends AppCompatActivity  {

    private String[] arraySpinner;
    private String[] arraySpinner2;

    Button cauta;
    TextView Loc;
    Spinner setLoc;
    Spinner setJud;

    int i,u;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cauta=(Button) findViewById(R.id.btnChoose);
        Loc=(TextView) findViewById(R.id.textView7);
        setJud=(Spinner) findViewById(R.id.spinner);
        setLoc=(Spinner) findViewById(R.id.spinner2);


        setJud.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> arg0, View arg1, int pos, long id)
                    {
                        if(pos==1)
                        {

                            setLoc.setVisibility(View.VISIBLE);
                            Loc.setVisibility(View.VISIBLE);
                            System.out.println(setJud.getSelectedItem().toString());
                        }
                        if(pos==0)
                        {
                            setLoc.setVisibility(View.INVISIBLE);
                            Loc.setVisibility(View.INVISIBLE);
                            cauta.setVisibility(View.INVISIBLE);
                            u=1;
                        }

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {

                    }
                });
        setLoc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int pos, long id)
            {
                if(pos==1)
                {

                    cauta.setVisibility(View.VISIBLE);
                    i=1;
                }
                if(pos==0 && i==1)
                {
                    cauta.setVisibility(View.INVISIBLE);
                }
                System.out.println(u);
                if(u==1)
                {
                    pos=0;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });

        this.arraySpinner = new String[] {
                "", "Constanta"
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_item, arraySpinner);
        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_item,list);
        setJud.setAdapter(adapter);

        this.arraySpinner2 = new String[] {
                "", "Constanta"
        };
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, R.layout.spinner_item, arraySpinner2);
        setLoc.setAdapter(adapter2);
    }


    public void cauta (View v)
    {
        Intent i = new Intent(getBaseContext(), Fereastra_De_Cautare.class);
        startActivity(i);
    }
    public void login1 (View v)
    {
        Intent i = new Intent(getBaseContext(), Login.class);
        startActivity(i);
    }
}
