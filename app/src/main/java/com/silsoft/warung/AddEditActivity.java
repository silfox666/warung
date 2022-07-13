package com.silsoft.warung;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.util.Locale;

public class AddEditActivity extends AppCompatActivity {

    Button btnSimpan;
    ImageView imgPhoto, imgGPS;
    EditText edtKoordinat;
    String id=null;
    String folder = Environment.getExternalStorageDirectory().getPath();
    Uri photoURI;
    Bitmap bitmap;
    boolean gps=false;

    private final static int PERMISSION_REQUEST = 1;
    private LocationManager locManager;
    private Location lastLocation;

    private final LocationListener locListener = new LocationListener() {
        public void onLocationChanged(Location loc) {
            updateLocation(loc);
        }

        public void onProviderEnabled(String provider) {
            updateLocation();
        }

        public void onProviderDisabled(String provider) {
            updateLocation();
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.warung_detail);
        String title;

        String action = getIntent().getExtras().getString("action");
        if (action.equals("add")){
            title = "Tambah Warung";
        } else {
            title = "Edit Warung";
            id = getIntent().getExtras().getString("id");
        }
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnSimpan = (Button) findViewById(R.id.btnSimpan);
        btnSimpan.setVisibility(View.VISIBLE);

        imgPhoto = (ImageView) findViewById(R.id.imgPhoto);
        imgPhoto.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if (!getPermission()){
                    requestPermission();
                } else {
                    getCamera();
                }
            }
        });

        imgGPS = (ImageView) findViewById(R.id.imgGPS);
        imgGPS.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                getGPS();
            }
        });

        edtKoordinat = (EditText) findViewById(R.id.edtKoordinat);
        locManager = (LocationManager) getSystemService(LOCATION_SERVICE);
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
    
    private void getCamera(){
        try{
            Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            String filename = folder + "/test.jpg";
            if (!new File(filename).exists()) new File(filename).createNewFile();
            photoURI = Uri.fromFile(new File(filename));
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
            startActivityForResult(cameraIntent, 1);
        }catch(Exception e){
            Toast.makeText(AddEditActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            bitmap = BitmapFactory.decodeFile(photoURI.getPath());
            new File(photoURI.getPath()).delete();
            final int maxSize = 1024;
            int outWidth, outHeight;
            int inWidth = bitmap.getWidth();
            int inHeight = bitmap.getHeight();
            if(inWidth > inHeight){
                outWidth = maxSize;
                outHeight = (inHeight * maxSize) / inWidth;
            } else {
                outHeight = maxSize;
                outWidth = (inWidth * maxSize) / inHeight;
            }
            bitmap = Bitmap.createScaledBitmap(bitmap, outWidth, outHeight, false);
            ImageView imgPhoto=(ImageView) findViewById(R.id.imgPhoto);
            imgPhoto.setImageBitmap(bitmap);
        }
    }

    private boolean getPermission(){
        int r1 = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        int r2 = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        int r3 = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (r1 == PackageManager.PERMISSION_GRANTED
                && r2 == PackageManager.PERMISSION_GRANTED
                && r3 == PackageManager.PERMISSION_GRANTED
        ) {
            return true;
        } else {
            return false;
        }

    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(AddEditActivity.this, new String[]{
                Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
        }, 102);
    }

    private void getGPS(){
        gps=true;
        startRequestingLocation();
        updateLocation();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (gps) getGPS();
    }

    @Override
    protected void onStop() {
        super.onStop();
        try {
            locManager.removeUpdates(locListener);
        } catch (SecurityException e) {
            Log.e("", "Failed to stop listening for location updates", e);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST &&
                grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            startRequestingLocation();
        }
    }

    private void updateLocation() {
        // Trigger a UI update without changing the location
        updateLocation(lastLocation);
    }

    private void updateLocation(Location location) {
        boolean locationEnabled = locManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean waitingForLocation = locationEnabled && !validLocation(location);
        boolean haveLocation = locationEnabled && !waitingForLocation;

        if (haveLocation) {
            edtKoordinat.setText(formatLocation(location));
            lastLocation = location;
        }
    }

    public void openLocationSettings() {
        if (!locManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
        }
    }

    private void startRequestingLocation() {
        if (!locManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            openLocationSettings();
            return;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_REQUEST);
            return;
        }

        // GPS enabled and have permission - start requesting location updates
        locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locListener);
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    private boolean validLocation(Location location) {
        if (location == null) {
            return false;
        }

        // Location must be from less than 30 seconds ago to be considered valid
        if (Build.VERSION.SDK_INT < 17) {
            return System.currentTimeMillis() - location.getTime() < 30e3;
        } else {
            return SystemClock.elapsedRealtimeNanos() - location.getElapsedRealtimeNanos() < 30e9;
        }
    }

    private String getLatitude(Location location) {
        return String.format(Locale.US, "%2.8f", location.getLatitude());
    }

    private String getLongitude(Location location) {
        return String.format(Locale.US, "%3.8f", location.getLongitude());
    }

    private String formatLocation(Location location) {
        return getLatitude(location) + ", " + getLongitude(location);
    }
}