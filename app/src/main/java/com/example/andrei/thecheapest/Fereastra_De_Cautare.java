package com.example.andrei.thecheapest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class Fereastra_De_Cautare extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fereastra__de__cautare);

    }

    public void cauta2(View v)
    {
        EditText et=(EditText) findViewById(R.id.edtName);
        TextView tv3=(TextView) findViewById(R.id.textView3);
        TextView tv4=(TextView) findViewById(R.id.textView4);
        TextView tv5=(TextView) findViewById(R.id.textView5);
        TextView tv6=(TextView) findViewById(R.id.textView6);
        ImageView iv=(ImageView) findViewById(R.id.imageView);

        String manc="pui dezosat";

        if (manc.toLowerCase().contains(et.getText().toString().toLowerCase())) {
            tv3.setVisibility(View.VISIBLE);
            tv4.setVisibility(View.VISIBLE);
            tv5.setVisibility(View.VISIBLE);
            tv6.setVisibility(View.VISIBLE);
            iv.setVisibility(View.VISIBLE);
        }
    }


}
