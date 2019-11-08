package com.example.presensimitratel;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.example.presensimitratel.Model.DataRequestGet;
import com.example.presensimitratel.Model.GetAbsenRequest;
import com.example.presensimitratel.Model.GetAbsenStatus;
import com.example.presensimitratel.Model.PostAbsenRequest;
import com.example.presensimitratel.Rest.ApiClient;
import com.example.presensimitratel.Rest.ApiInterface;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    LocationRequest mLocationRequest;
    private GoogleMap mMap;
    ApiInterface mApiInterface;
    SharedPrefManager sharedPrefManager;
    Activity activity;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        activity = this;
        context = this;

        sharedPrefManager = new SharedPrefManager(this);

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission();
        }
        SupportMapFragment mapFragment = (SupportMapFragment)
                getSupportFragmentManager()
                        .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);

        String nik_tg = sharedPrefManager.getSPNIKTG();
        String status = sharedPrefManager.getSPMasukPulang();
        mApiInterface = ApiClient.getClient(getString(R.string.api_client_1)).create(ApiInterface.class);
        HashMap<String, String> params = new HashMap<>();
        params.put("nik_tg", nik_tg);
        params.put("status", status);
        Call<GetAbsenRequest> apiData = mApiInterface.getAbsenRequest(params);
        apiData.enqueue(new Callback<GetAbsenRequest>() {
            @Override
            public void onResponse(Call<GetAbsenRequest> call, Response<GetAbsenRequest> response) {
                Boolean stat = response.body().getStatus();
                List<DataRequestGet> dataRequestGet;
                String date, ket, time;
                TextInputLayout ketFrame = findViewById(R.id.alasanFrame);
                TextView jam = findViewById(R.id.jam);
                if (stat) {
                    dataRequestGet = response.body().getListDataRequestGet();
                    date = dataRequestGet.get(0).getDate();
                    ket = dataRequestGet.get(0).getKet();
                    sharedPrefManager.saveSPString(SharedPrefManager.SP_DATE_ABSEN, date);
                    sharedPrefManager.saveSPString(SharedPrefManager.SP_KET_ABSEN, ket);
                    time = date.split(" ")[1];
                    if (ket.equals("OK")) {
                        ketFrame.setVisibility(View.GONE);
                    } else if (ket.equals("LT")) {
                        ketFrame.setVisibility(View.VISIBLE);
                    } else {
                        ketFrame.setVisibility(View.GONE);
                    }
                    jam.setText(time);
                    if ((dataRequestGet.get(0).getLat() != 0) || (dataRequestGet.get(0).getLon() != 0)) {
                        String lat = "" + dataRequestGet.get(0).getLat();
                        String lon = "" + dataRequestGet.get(0).getLon();
                        sharedPrefManager.saveSPString(SharedPrefManager.SP_LAT_KANTOR, lat);
                        sharedPrefManager.saveSPString(SharedPrefManager.SP_LONG_KANTOR, lon);
                        LatLng latLng = new LatLng(dataRequestGet.get(0).getLat(), dataRequestGet.get(0).getLon());
                        MarkerOptions markerOptions = new MarkerOptions();
                        markerOptions.position(latLng);
                        markerOptions.title("Kantor Anda");
                        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                        mMap.addMarker(markerOptions);
                        // GET CURRENT LOCATION
                        FusedLocationProviderClient mFusedLocation = LocationServices.getFusedLocationProviderClient(activity);
                        mFusedLocation.getLastLocation().addOnSuccessListener(activity, new OnSuccessListener<Location>() {
                            @Override
                            public void onSuccess(Location location) {
                                double distance = distance(location.getLatitude(), location.getLongitude(), dataRequestGet.get(0).getLat(), dataRequestGet.get(0).getLon(), 0, 0);
                                double distance2 = distance2(location.getLatitude(), location.getLongitude(), dataRequestGet.get(0).getLat(), dataRequestGet.get(0).getLon());
                                double distancekm;
                                int dis;
                                String dis_text, text;
                                if (distance2 > 1000) {
                                    distancekm = distance2 / 1000;
                                    dis = (int) distancekm;
                                    dis_text = dis + "km";
                                } else {
                                    dis = (int) distance2;
                                    dis_text = dis + "m";
                                }
                                // text = "" + distance2;
                                // String loc = location.getLatitude() + "," + location.getLongitude();
                                TextView dis_display = findViewById(R.id.distance);
                                dis_display.setText(dis_text);
                                sharedPrefManager.saveSPString(SharedPrefManager.SP_DISTANCE, dis_text);
                            }
                        });
                    }
                } else {
                    ketFrame.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<GetAbsenRequest> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "System Error, GET API Failed", Toast.LENGTH_LONG).show();
            }
        });
        TextInputEditText form1 = findViewById(R.id.alasan);

        form1.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if(s.length() < 5){
                    TextInputLayout til = (TextInputLayout) findViewById(R.id.alasanFrame);
                    til.setError("Alasan Harus Lebih Dari 5 Karakter");
                } else {
                    TextInputLayout til = (TextInputLayout) findViewById(R.id.alasanFrame);
                    til.setError(null);
                }
            }
        });
        Button absenKlik = findViewById(R.id.absenKlik);
        absenKlik.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String keterangan = sharedPrefManager.getSPKetAbsen();
                String nik, nik_tg, name, posisi, unit, date, ket, cat, ip, lat, lon, emoji, manager_code,
                        vp_code, evp_code, distance, status;
                if (keterangan.equals("LT")){
                    TextInputEditText catatan = findViewById(R.id.alasan);
                    int alasan = catatan.getText().length();
                    if (alasan < 5){
                        TextInputLayout til = (TextInputLayout) findViewById(R.id.alasanFrame);
                        til.setError("Alasan Harus Lebih Dari 5 Karakter");
                    } else {
                        nik = sharedPrefManager.getSPNIK();
                        nik_tg = sharedPrefManager.getSPNIKTG();
                        name = sharedPrefManager.getSPName();
                        posisi = sharedPrefManager.getSPPosisi();
                        unit = sharedPrefManager.getSPUnit();
                        date = sharedPrefManager.getSPDateAbsen();
                        ket = sharedPrefManager.getSPKetAbsen();
                        cat = catatan.getText().toString();
                        ip = Utils.getIPAddress(true);
                        lat = sharedPrefManager.getSPLat();
                        lon = sharedPrefManager.getSPLong();
                        RadioGroup radioGroup = findViewById(R.id.emoji);
                        int selectedId = radioGroup.getCheckedRadioButtonId();
                        RadioButton radioButton = (RadioButton) findViewById(selectedId);
                        if (radioButton.getText().toString().equals("Tertekan")){
                            emoji = "tertekan";
                        } else if (radioButton.getText().toString().equals("Nyaman")){
                            emoji = "nyaman";
                        } else if (radioButton.getText().toString().equals("Semangat")){
                            emoji = "semangat";
                        } else {
                            emoji = "nyaman";
                        }
                        manager_code = sharedPrefManager.getSPManagerCode();
                        vp_code = sharedPrefManager.getSPVPCode();
                        evp_code = sharedPrefManager.getSPEVPCode();
                        distance = sharedPrefManager.getSpDistance();
                        status = sharedPrefManager.getSPMasukPulang();
                        Call<PostAbsenRequest> absenRequestCall = mApiInterface.postAbsenRequest(nik,nik_tg, name, posisi, unit, date,
                                ket, cat, ip, lat, lon, emoji, manager_code, vp_code, evp_code, distance,status);
                        absenRequestCall.enqueue(new Callback<PostAbsenRequest>() {
                            @Override
                            public void onResponse(Call<PostAbsenRequest> call, Response<PostAbsenRequest> response) {
                                String message = response.body().getMessage();
                                Intent returnIntent = new Intent();
                                returnIntent.putExtra("result",message);
                                setResult(RESULT_OK,returnIntent);
                                finish();
                            }

                            @Override
                            public void onFailure(Call<PostAbsenRequest> call, Throwable t) {
                                Toast.makeText(getApplicationContext(), "System Error, POST API Failed", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                } else {
                    nik = sharedPrefManager.getSPNIK();
                    nik_tg = sharedPrefManager.getSPNIKTG();
                    name = sharedPrefManager.getSPName();
                    posisi = sharedPrefManager.getSPPosisi();
                    unit = sharedPrefManager.getSPUnit();
                    date = sharedPrefManager.getSPDateAbsen();
                    ket = sharedPrefManager.getSPKetAbsen();
                    cat = "";
                    ip = Utils.getIPAddress(true);
                    lat = sharedPrefManager.getSPLat();
                    lon = sharedPrefManager.getSPLong();
                    RadioGroup radioGroup = findViewById(R.id.emoji);
                    int selectedId = radioGroup.getCheckedRadioButtonId();
                    RadioButton radioButton = (RadioButton) findViewById(selectedId);
                    if (radioButton.getText().toString().equals("Tertekan")){
                        emoji = "tertekan";
                    } else if (radioButton.getText().toString().equals("Nyaman")){
                        emoji = "nyaman";
                    } else if (radioButton.getText().toString().equals("Semangat")){
                        emoji = "semangat";
                    } else {
                        emoji = "nyaman";
                    }
                    manager_code = sharedPrefManager.getSPManagerCode();
                    vp_code = sharedPrefManager.getSPVPCode();
                    evp_code = sharedPrefManager.getSPEVPCode();
                    distance = sharedPrefManager.getSpDistance();
                    status = sharedPrefManager.getSPMasukPulang();
                    Call<PostAbsenRequest> absenRequestCall = mApiInterface.postAbsenRequest(nik,nik_tg, name, posisi, unit, date,
                            ket, cat, ip, lat, lon, emoji, manager_code, vp_code, evp_code, distance,status);
                    absenRequestCall.enqueue(new Callback<PostAbsenRequest>() {
                        @Override
                        public void onResponse(Call<PostAbsenRequest> call, Response<PostAbsenRequest> response) {
                            String message = response.body().getMessage();
                            Intent returnIntent = new Intent();
                            returnIntent.putExtra("result",message);
                            setResult(RESULT_OK,returnIntent);
                            finish();
                        }

                        @Override
                        public void onFailure(Call<PostAbsenRequest> call, Throwable t) {
                            Toast.makeText(getApplicationContext(), "System Error, POST API Failed", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });
    }
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if ( v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int)event.getRawX(), (int)event.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent( event );
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setZoomGesturesEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);
        //Initialize Google Play Services
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();
                mMap.setMyLocationEnabled(true);
            }
        } else {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }
    }
    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }
    @Override
    public void onConnected(Bundle bundle) {
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient,
                    mLocationRequest, this);
        }
    }
    @Override
    public void onConnectionSuspended(int i) {
    }
    @Override
    public void onLocationChanged(Location location) {
        sharedPrefManager = new SharedPrefManager(this);
        String lat = "" + location.getLatitude();
        String lon = "" + location.getLongitude();
        sharedPrefManager.saveSPString(SharedPrefManager.SP_LAT, lat);
        sharedPrefManager.saveSPString(SharedPrefManager.SP_LONG, lon);
        mLastLocation = location;
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }
//Showing Current Location Marker on Map
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        LocationManager locationManager = (LocationManager)
                getSystemService(Context.LOCATION_SERVICE);
        String provider = locationManager.getBestProvider(new Criteria(), true);
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        //Location locations = locationManager.getLastKnownLocation(provider);
        List<String> providerList = locationManager.getAllProviders();
        if (null != location && null != providerList && providerList.size() > 0) {
            double longitude = location.getLongitude();
            double latitude = location.getLatitude();
            Geocoder geocoder = new Geocoder(getApplicationContext(),
                    Locale.getDefault());
            try {
                List<Address> listAddresses = geocoder.getFromLocation(latitude,
                        longitude, 1);
                if (null != listAddresses && listAddresses.size() > 0) {
                    String state = listAddresses.get(0).getAdminArea();
                    String country = listAddresses.get(0).getCountryName();
                    String subLocality = listAddresses.get(0).getSubLocality();
                    String addressline = listAddresses.get(0).getAddressLine(0);
                    markerOptions.title("" + latLng + "," + subLocality + "," + state
                            + "," + country);
                    TextView alamat = findViewById(R.id.alamat);
                    String address = "Anda berada di " + addressline;
                    alamat.setText(address);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
        mCurrLocationMarker = mMap.addMarker(markerOptions);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom( latLng, 15 ),200, null);
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient,
                    this);
        }
    }
    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
    }
    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {
                        if (mGoogleApiClient == null) {
                            buildGoogleApiClient();
                        }
                        mMap.setMyLocationEnabled(true);
                    }
                } else {
                    Toast.makeText(this, "permission denied",
                            Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }

    /*
     * Calculate distance between two points in latitude and longitude taking
     * into account height difference. If you are not interested in height
     * difference pass 0.0. Uses Haversine method as its base.
     *
     * lat1, lon1 Start point lat2, lon2 End point el1 Start altitude in meters
     * el2 End altitude in meters
     * @returns Distance in Meters
     */
    public static double distance(double lat1, double lat2, double lon1,
                                  double lon2, double el1, double el2) {

        final int R = 6371; // Radius of the earth

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c * 1000; // convert to meters

        double height = el1 - el2;

        distance = Math.pow(distance, 2) + Math.pow(height, 2);

        return Math.sqrt(distance);
    }

    public static double distance2(double lat1, double lon1, double lat2, double lon2) {
        lat1 = Math.toRadians(lat1);
        lon1 = Math.toRadians(lon1);
        lat2 = Math.toRadians(lat2);
        lon2 = Math.toRadians(lon2);

        double earthRadius = 6371010; //Meters
        return earthRadius * Math.acos(Math.sin(lat1)*Math.sin(lat2) + Math.cos(lat1)*Math.cos(lat2)*Math.cos(lon1 - lon2));
    }
}