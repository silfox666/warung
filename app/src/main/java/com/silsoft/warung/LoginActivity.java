package com.silsoft.warung;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    EditText edtPass;
    ImageView imgShow;
    boolean show = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        getSupportActionBar().setTitle("LOGIN PAGE");
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_launcher);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        edtPass = (EditText) findViewById(R.id.edtPass);
        imgShow = (ImageView) findViewById(R.id.imgShow);

        imgShow.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if (show){
                    show=false;
                    edtPass.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    edtPass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    imgShow.setImageResource(R.drawable.ic_hide);
                } else {
                    show=true;
                    edtPass.setInputType(InputType.TYPE_CLASS_TEXT);
                    edtPass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    imgShow.setImageResource(R.drawable.ic_show);
                }
            }
        });

        Button btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent i = new Intent(LoginActivity.this, DaftarWarungActivity.class);
                startActivity(i);
                finish();
            }
        });

        TextView txtRegister = (TextView) findViewById(R.id.txtRegister);
        txtRegister.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(i);
            }
        });
    }
}