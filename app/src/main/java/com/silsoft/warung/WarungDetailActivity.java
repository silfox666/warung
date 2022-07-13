package com.silsoft.warung;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

public class WarungDetailActivity extends AppCompatActivity {

    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.warung_detail);
        getSupportActionBar().setTitle("DETAIL WARUNG");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        id = getIntent().getExtras().getString("id");
        ImageView edtKoordinat = (ImageView) findViewById(R.id.imgGPS);
        edtKoordinat.setImageDrawable(null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.action_edit:
                Intent i = new Intent(WarungDetailActivity.this, AddEditActivity.class);
                i.putExtra("action", "edit");
                i.putExtra("id", id);
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}