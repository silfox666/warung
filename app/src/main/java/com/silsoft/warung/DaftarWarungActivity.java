package com.silsoft.warung;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DaftarWarungActivity extends AppCompatActivity {

    LinearLayout lin;
    CardView card, removeID=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.daftar_warung);

        getSupportActionBar().setTitle("DAFTAR WARUNG");
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_launcher);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        lin = (LinearLayout) findViewById(R.id.lin);
        newCardView("1", "Warung Masa Kini", "Jalan Soedirman No 22", "78.7828390, 34.78293", "https://silsoft.id/favicon.png");
    }

    private void newCardView(final String id, String nama, String alamat, String koordinat, String photo){
        card = new CardView(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );

        params.setMargins(30,20,30,20);
        card.setLayoutParams(params);

        card.setRadius(20);
        card.setContentPadding(50, 50, 50, 50);
        card.setCardBackgroundColor(Color.parseColor("#ffffff"));
        card.setMaxCardElevation(25);
        card.setCardElevation(20);

        View view = getLayoutInflater().inflate(R.layout.card_view_warung, null);

        ImageView img = (ImageView) view.findViewById(R.id.imgPhoto);
        img.setImageResource(R.drawable.defphoto);

        TextView txtNama = (TextView) view.findViewById(R.id.txtNama);
        txtNama.setText(nama);

        TextView txtAlamat = (TextView) view.findViewById(R.id.txtAlamat);
        txtAlamat.setText(alamat);

        TextView txtKoordinat = (TextView) view.findViewById(R.id.txtKoordinat);
        txtKoordinat.setText(koordinat);

        card.setTag(card);
        card.setOnClickListener(new View.OnClickListener(){
            public void onClick(final View v){
                removeID = (CardView) ((CardView) v).getTag();
                Intent i = new Intent(DaftarWarungActivity.this, WarungDetailActivity.class);
                i.putExtra("id", id);
                startActivity(i);
            }
        });

        card.addView(view);
        lin.addView(card);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                Intent i = new Intent(DaftarWarungActivity.this, AddEditActivity.class);
                i.putExtra("action", "add");
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}