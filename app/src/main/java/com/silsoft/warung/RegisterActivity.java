package com.silsoft.warung;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class RegisterActivity extends AppCompatActivity {

    EditText edtPass, edtPass2;
    ImageView imgShow, imgShow2;
    boolean show = false, show2=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        getSupportActionBar().setTitle("REGISTER PAGE");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        edtPass = (EditText) findViewById(R.id.edtPass);
        edtPass2 = (EditText) findViewById(R.id.edtPass2);
        imgShow = (ImageView) findViewById(R.id.imgShow);
        imgShow2 = (ImageView) findViewById(R.id.imgShow2);

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

        imgShow2.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if (show2){
                    show2=false;
                    edtPass2.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    edtPass2.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    imgShow2.setImageResource(R.drawable.ic_hide);
                } else {
                    show2=true;
                    edtPass2.setInputType(InputType.TYPE_CLASS_TEXT);
                    edtPass2.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    imgShow2.setImageResource(R.drawable.ic_show);
                }
            }
        });

        Button btnRegister = (Button) findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if (edtPass.getText().toString().equals(edtPass2.getText().toString())){
                    finish();
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}