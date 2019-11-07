package com.example.presensimitratel;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.StrictMode;
import android.os.Bundle;
import android.util.LruCache;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.presensimitratel.Adapter.DataKaryawanAdapter;
import com.example.presensimitratel.Adapter.MonitoringAdapter;
import com.example.presensimitratel.Adapter.UlangTahunAdapter;
import com.example.presensimitratel.Model.DataAbsen;
import com.example.presensimitratel.Model.DataMonitoring;
import com.example.presensimitratel.Model.DataStatus;
import com.example.presensimitratel.Model.DataUlangTahun;
import com.example.presensimitratel.Model.DataUser;
import com.example.presensimitratel.Model.GetAbsenData;
import com.example.presensimitratel.Model.GetAbsenStatus;
import com.example.presensimitratel.Model.GetLogin;
import com.example.presensimitratel.Model.GetMonitoring;
import com.example.presensimitratel.Model.GetUlangTahun;
import com.example.presensimitratel.Rest.ApiClient;
import com.example.presensimitratel.Rest.ApiInterface;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.view.View.GONE;
import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;


public class MainActivity extends AppCompatActivity {

    ListView listView;
    ApiInterface mApiInterface;
    List<DataMonitoring> monitoringList = new ArrayList<>();
    RecyclerView monitoringBawahan;
    MonitoringAdapter monitoringAdapter;
    List<DataUlangTahun> ulangTahunList = new ArrayList<>();
    RecyclerView ulangTahun;
    UlangTahunAdapter ulangTahunAdapter;
    private Activity activity;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPrefManager sharedPrefManager;
        sharedPrefManager = new SharedPrefManager(this);
        if (!sharedPrefManager.getSPSudahLogin()){
            //Bundle bundle = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this).toBundle();
            //startActivity(new Intent(MainActivity.this, LoginActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK), bundle);
            startActivity(new Intent(MainActivity.this, LoginActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
            finish();
        }
        setContentView(R.layout.activity_main);

        activity = this;
        context = this;

//        animateElement();

        CircleImageView profileImage= findViewById(R.id.profile_image);
        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                sharedPrefManager.saveSPBoolean(SharedPrefManager.SP_SUDAH_LOGIN, false);
                //Bundle bundle = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this).toBundle();
                //startActivity(new Intent(MainActivity.this, LoginActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK), bundle);
                startActivity(new Intent(MainActivity.this, LoginActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                finish();
            }
        });

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        retrieveAbsenData(sharedPrefManager);
        retrieveKaryawanData(sharedPrefManager);
        retrievePrivilege(sharedPrefManager);
        retrieveMonitoringData(sharedPrefManager);
        retrieveUlangTahunData(sharedPrefManager);
        retrieveAbsenStatus(sharedPrefManager);

        Button absenButton= findViewById(R.id.absenButton);
        absenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                //Bundle bundle = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this).toBundle();
                //startActivity(new Intent(MainActivity.this, LoginActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK), bundle);
                //startActivity(new Intent(MainActivity.this, LoginActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                startActivity(new Intent(context, MapsActivity.class),
                        ActivityOptions.makeSceneTransitionAnimation(activity).toBundle());
                //finish();
            }
        });

        SwipeRefreshLayout swipeRefreshLayout = findViewById(R.id.pullToRefresh);
        swipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
//                        animateElement();
                        retrieveAbsenData(sharedPrefManager);
                        retrieveKaryawanData(sharedPrefManager);
                        retrievePrivilege(sharedPrefManager);
                        retrieveMonitoringData(sharedPrefManager);
                        retrieveUlangTahunData(sharedPrefManager);
                        retrieveAbsenStatus(sharedPrefManager);
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }
        );
    }
    public void animateElement(){
        final Animation slideUp = AnimationUtils.loadAnimation(this,R.anim.slide_up);
        CircleImageView element1 = findViewById(R.id.profile_image);
        element1.setVisibility(INVISIBLE);
        TextView element2 = findViewById(R.id.selamatDatang);
        element2.setVisibility(INVISIBLE);
        TextView element3 = findViewById(R.id.tanggal);
        element3.setVisibility(INVISIBLE);
        TextView element4 = findViewById(R.id.jamIn);
        element4.setVisibility(INVISIBLE);
        TextView element5 = findViewById(R.id.jamOut);
        element5.setVisibility(INVISIBLE);
        TextView element6 = findViewById(R.id.ipIn);
        element6.setVisibility(INVISIBLE);
        TextView element7 = findViewById(R.id.ipOut);
        element7.setVisibility(INVISIBLE);
        LinearLayout element8 = findViewById(R.id.miscIn);
        element8.setVisibility(INVISIBLE);
        LinearLayout element9 = findViewById(R.id.miscOut);
        element9.setVisibility(INVISIBLE);
        TextView element10 = findViewById(R.id.ketTag);
        element10.setVisibility(INVISIBLE);
        FrameLayout element11 = findViewById(R.id.keteranganFrame);
        element11.setVisibility(INVISIBLE);
        TextView element12 = findViewById(R.id.dataLabel);
        element12.setVisibility(INVISIBLE);
        ListView element13 = findViewById(R.id.dataKaryawan);
        element13.setVisibility(INVISIBLE);

        final Context context = MainActivity.this;

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                element1.startAnimation(AnimationUtils.loadAnimation(context, R.anim.slide_up));
                element1.setVisibility(VISIBLE);
            }
        }, 50);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                element2.startAnimation(AnimationUtils.loadAnimation(context, R.anim.slide_up));
                element2.setVisibility(VISIBLE);
            }
        }, 100);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                element3.startAnimation(AnimationUtils.loadAnimation(context, R.anim.slide_up));
                element3.setVisibility(VISIBLE);
            }
        }, 150);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                element4.startAnimation(AnimationUtils.loadAnimation(context, R.anim.slide_up));
                element4.setVisibility(VISIBLE);
                element5.startAnimation(AnimationUtils.loadAnimation(context, R.anim.slide_up));
                element5.setVisibility(VISIBLE);
            }
        }, 200);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                element6.startAnimation(AnimationUtils.loadAnimation(context, R.anim.slide_up));
                element6.setVisibility(VISIBLE);
                element7.startAnimation(AnimationUtils.loadAnimation(context, R.anim.slide_up));
                element7.setVisibility(VISIBLE);
            }
        }, 250);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                element8.startAnimation(AnimationUtils.loadAnimation(context, R.anim.slide_up));
                element8.setVisibility(VISIBLE);
                element9.startAnimation(AnimationUtils.loadAnimation(context, R.anim.slide_up));
                element9.setVisibility(VISIBLE);
            }
        }, 300);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                element10.startAnimation(AnimationUtils.loadAnimation(context, R.anim.slide_up));
                element10.setVisibility(VISIBLE);
            }
        }, 350);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                element11.startAnimation(AnimationUtils.loadAnimation(context, R.anim.slide_up));
                element11.setVisibility(VISIBLE);
            }
        }, 400);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                element12.startAnimation(AnimationUtils.loadAnimation(context, R.anim.slide_up));
                element12.setVisibility(VISIBLE);
                element13.startAnimation(AnimationUtils.loadAnimation(context, R.anim.slide_up));
                element13.setVisibility(VISIBLE);
            }
        }, 450);
    }

    public void retrieveKaryawanData(SharedPrefManager sharedPrefManager){
        String nik = sharedPrefManager.getSPNIK();
        String nik_tg = sharedPrefManager.getSPNIKTG();
        String unit = sharedPrefManager.getSPUnit();
        String posisi = sharedPrefManager.getSPPosisi();
        String band = sharedPrefManager.getSPBand();
        String[] dataArray = {nik_tg,unit,posisi,band};
        String[] labelArray = {"NIK TG", "Unit", "Posisi", "Band"};
        String name  = sharedPrefManager.getSPName();

        DataKaryawanAdapter karyawan = new DataKaryawanAdapter(this,labelArray,dataArray);
        listView = findViewById(R.id.dataKaryawan);
        listView.setAdapter(karyawan);
        justifyListViewHeightBasedOnChildren(listView);

        TextView selamatDatang = findViewById(R.id.selamatDatang);
        String selamat = "Selamat Datang " + name;
        selamatDatang.setText(selamat);

        ScrollView scroll = findViewById(R.id.scroll);
        scroll.smoothScrollTo(0,0);

        CircleImageView profileImage= findViewById(R.id.profile_image);
        try{
//            Bitmap myImage = getBitmapFromURL(getString(R.string.profile_image).concat(nik).concat(".jpg"));
//            if (myImage != null){
//                profileImage.setImageBitmap(myImage);
                Picasso.get().load(getString(R.string.profile_image).concat(nik).concat(".jpg")).placeholder(R.drawable.def_user).error(R.drawable.def_user).resize(300,300).into(profileImage);
//            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    public void retrieveAbsenStatus (SharedPrefManager sharedPrefManager){
        String nik_tg = sharedPrefManager.getSPNIKTG();
        mApiInterface = ApiClient.getClient(getString(R.string.api_client_1)).create(ApiInterface.class);
        HashMap<String, String> params = new HashMap<>();
        params.put("nik_tg", nik_tg);
        Call<GetAbsenStatus> apiData = mApiInterface.getAbsenStatus(params);
        apiData.enqueue(new Callback<GetAbsenStatus>() {
            @Override
            public void onResponse(Call<GetAbsenStatus> call, Response<GetAbsenStatus> response) {
                Boolean status = response.body().getStatus();
                if (status){
                    List<DataStatus> dataStatusList = response.body().getListDataStatus();
                    TextView tanggal = findViewById(R.id.tanggal);
                    tanggal.setText(dataStatusList.get(0).getDateLong());
                    Button absenButton = findViewById(R.id.absenButton);
                    if (dataStatusList.get(0).getColor1() != null){
                        if (dataStatusList.get(0).getColor1().equals("Red")){
                            absenButton.setBackground(getDrawable(R.drawable.ripple_red));
                        } else if (dataStatusList.get(0).getColor1().equals("Blue")){
                            absenButton.setBackground(getDrawable(R.drawable.ripple_blue));
                        } else if (dataStatusList.get(0).getColor1().equals("Green")){
                            absenButton.setBackground(getDrawable(R.drawable.non_ripple_green));
                        } else {
                            absenButton.setBackground(getDrawable(R.drawable.non_ripple_green));
                        }
                    } else {
                        absenButton.setBackground(getDrawable(R.drawable.non_ripple_green));
                    }
                    if (dataStatusList.get(0).getClickable() != null){
                        if (dataStatusList.get(0).getClickable().equals("1")){
                            absenButton.setEnabled(true);
                        } else if (dataStatusList.get(0).getClickable().equals("0")){
                            absenButton.setEnabled(false);
                        } else {
                            absenButton.setEnabled(false);
                        }
                    } else {
                        absenButton.setEnabled(false);
                    }
                    if (dataStatusList.get(0).getNote() != null){
                        absenButton.setText(dataStatusList.get(0).getNote());
                    } else {
                        absenButton.setText("Selamat Istirahat");
                    }
                } else {
                    List<DataStatus> dataStatusList = response.body().getListDataStatus();
                    TextView tanggal = findViewById(R.id.tanggal);
                    tanggal.setText(dataStatusList.get(0).getDateLong());
                    Button absenButton = findViewById(R.id.absenButton);
                    if (dataStatusList.get(0).getColor1() != null){
                        if (dataStatusList.get(0).getColor1().equals("Red")){
                            absenButton.setBackground(getDrawable(R.drawable.ripple_red));
                        } else if (dataStatusList.get(0).getColor1().equals("Blue")){
                            absenButton.setBackground(getDrawable(R.drawable.ripple_blue));
                        } else if (dataStatusList.get(0).getColor1().equals("Green")){
                            absenButton.setBackground(getDrawable(R.drawable.non_ripple_green));
                        } else {
                            absenButton.setBackground(getDrawable(R.drawable.non_ripple_green));
                        }
                    } else {
                        absenButton.setBackground(getDrawable(R.drawable.non_ripple_green));
                    }
                    if (dataStatusList.get(0).getClickable() != null){
                        if (dataStatusList.get(0).getClickable().equals("1")){
                            absenButton.setEnabled(true);
                        } else if (dataStatusList.get(0).getClickable().equals("0")){
                            absenButton.setEnabled(false);
                        } else {
                            absenButton.setEnabled(false);
                        }
                    } else {
                        absenButton.setEnabled(false);
                    }
                    if (dataStatusList.get(0).getNote() != null){
                        absenButton.setText(dataStatusList.get(0).getNote());
                    } else {
                        absenButton.setText("Selamat Istirahat");
                    }
                }
            }

            @Override
            public void onFailure(Call<GetAbsenStatus> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"System Error, GET API Failed",Toast.LENGTH_LONG);
            }
        });
    }

    public void retrieveAbsenData(SharedPrefManager sharedPrefManager){
        String nik_tg = sharedPrefManager.getSPNIKTG();
        mApiInterface = ApiClient.getClient(getString(R.string.api_client_1)).create(ApiInterface.class);
        HashMap<String, String> params = new HashMap<>();
        params.put("nik_tg", nik_tg);
        Call<GetAbsenData> apiData = mApiInterface.getAbsenData(params);
        apiData.enqueue(new Callback<GetAbsenData>() {
            @Override
            public void onResponse(Call<GetAbsenData> call, Response<GetAbsenData> response) {
                Boolean status = response.body().getStatus();
                if (status) {
                    List<DataAbsen> dataAbsen = response.body().getListDataAbsen();
                    TextView jamIn = findViewById(R.id.jamIn);
                    TextView ipIn = findViewById(R.id.ipIn);
                    ImageView emojiIn = findViewById(R.id.emojiIn);
                    TextView longLatIn = findViewById(R.id.longLatIn);
                    TextView distanceIn = findViewById(R.id.distanceIn);
                    TextView jamOut = findViewById(R.id.jamOut);
                    TextView ipOut = findViewById(R.id.ipOut);
                    ImageView emojiOut = findViewById(R.id.emojiOut);
                    TextView longLatOut = findViewById(R.id.longLatOut);
                    TextView distanceOut = findViewById(R.id.distanceOut);
                    FrameLayout keteranganFrame = findViewById(R.id.keteranganFrame);
                    TextView keterangan = findViewById(R.id.keterangan);
                    if (dataAbsen.get(0).getDatetimeIn() != null){
                        String time_in = dataAbsen.get(0).getDatetimeIn().split(" ")[1];
                        String jam_in = time_in.split(":")[0] + ":" + time_in.split(":")[1];
                        jamIn.setText(jam_in);
                    } else {
                        jamIn.setText("--:--");
                    }
                    if (dataAbsen.get(0).getDatetimeOut() != null){
                        String time_out = dataAbsen.get(0).getDatetimeOut().split(" ")[1];
                        String jam_out = time_out.split(":")[0] + ":" + time_out.split(":")[1];
                        jamOut.setText(jam_out);
                    } else {
                        jamOut.setText("--:--");
                    }
                    if (dataAbsen.get(0).getIPIn() != null) {
                        ipIn.setText("IP " + dataAbsen.get(0).getIPIn());
                    } else {
                        ipIn.setText("IP 127.0.0.1");
                    }
                    if (dataAbsen.get(0).getIPOut() != null) {
                        ipOut.setText("IP " + dataAbsen.get(0).getIPOut());
                    } else {
                        ipOut.setText("IP 127.0.0.1");
                    }
                    if (dataAbsen.get(0).getLatitudeIn() != null){
                        longLatIn.setText(dataAbsen.get(0).getLatitudeIn() + " / " + dataAbsen.get(0).getLongitudeIn());
                    } else {
                        longLatIn.setText("- / -");
                    }
                    if (dataAbsen.get(0).getLatitudeOut() != null){
                        longLatOut.setText(dataAbsen.get(0).getLatitudeOut() + " / " + dataAbsen.get(0).getLongitudeOut());
                    } else {
                        longLatOut.setText("- / -");
                    }
                    if (dataAbsen.get(0).getDistanceIn() != null){
                        distanceIn.setText(dataAbsen.get(0).getDistanceIn());
                    } else {
                        distanceIn.setText("-");
                    }
                    if (dataAbsen.get(0).getDistanceOut() != null){
                        distanceOut.setText(dataAbsen.get(0).getDistanceOut());
                    } else {
                        distanceOut.setText("-");
                    }
                    if (dataAbsen.get(0).getEmojiIn() != null){
                        if (dataAbsen.get(0).getEmojiIn().equals("tertekan")){
                            emojiIn.setImageResource(R.drawable.tertekan);
                        } else if (dataAbsen.get(0).getEmojiIn().equals("nyaman")){
                            emojiIn.setImageResource(R.drawable.nyaman);
                        } else if (dataAbsen.get(0).getEmojiIn().equals("semangat")){
                            emojiIn.setImageResource(R.drawable.semangat);
                        } else {
                            emojiIn.setImageResource(R.drawable.neutral);
                        }
                    } else {
                        emojiIn.setImageResource(R.drawable.neutral);
                    }
                    if (dataAbsen.get(0).getEmojiOut() != null){
                        if (dataAbsen.get(0).getEmojiOut().equals("tertekan")){
                            emojiOut.setImageResource(R.drawable.tertekan);
                        } else if (dataAbsen.get(0).getEmojiOut().equals("nyaman")){
                            emojiOut.setImageResource(R.drawable.nyaman);
                        } else if (dataAbsen.get(0).getEmojiOut().equals("semangat")){
                            emojiOut.setImageResource(R.drawable.semangat);
                        } else {
                            emojiOut.setImageResource(R.drawable.neutral);
                        }
                    } else {
                        emojiOut.setImageResource(R.drawable.neutral);
                    }
                    if (dataAbsen.get(0).getKeteranganIn() != null) {
                        if (dataAbsen.get(0).getKeteranganIn().equals("OK")){
                            keteranganFrame.setBackgroundColor(getResources().getColor(R.color.colorBlue));
                        } else if (dataAbsen.get(0).getKeteranganIn().equals("LT")) {
                            keteranganFrame.setBackgroundColor(getResources().getColor(R.color.colorRed));
                        } else {
                            keteranganFrame.setBackgroundColor(getResources().getColor(R.color.colorRed));
                        }
                    } else {
                        keteranganFrame.setBackgroundColor(getResources().getColor(R.color.colorBlue));
                    }
                    if (dataAbsen.get(0).getCatatanIn() != null){
                        keterangan.setText(dataAbsen.get(0).getCatatanIn());
                    } else {
                        keterangan.setText("");
                    }
                } else {
                    TextView jamIn = findViewById(R.id.jamIn);
                    TextView ipIn = findViewById(R.id.ipIn);
                    ImageView emojiIn = findViewById(R.id.emojiIn);
                    TextView longLatIn = findViewById(R.id.longLatIn);
                    TextView distanceIn = findViewById(R.id.distanceIn);
                    TextView jamOut = findViewById(R.id.jamOut);
                    TextView ipOut = findViewById(R.id.ipOut);
                    ImageView emojiOut = findViewById(R.id.emojiOut);
                    TextView longLatOut = findViewById(R.id.longLatOut);
                    TextView distanceOut = findViewById(R.id.distanceOut);
                    jamIn.setText("--:--");
                    jamOut.setText("--:--");
                    ipIn.setText("IP 127.0.0.1");
                    ipOut.setText("IP 127.0.0.1");
                    longLatIn.setText("- / -");
                    longLatOut.setText("- / -");
                    distanceIn.setText("-");
                    distanceOut.setText("-");
                    emojiIn.setImageResource(R.drawable.neutral);
                    emojiOut.setImageResource(R.drawable.neutral);
                }
            }

            @Override
            public void onFailure(Call<GetAbsenData> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"System Error, GET API Failed",Toast.LENGTH_LONG);
            }
        });
    }

    public void retrievePrivilege (SharedPrefManager sharedPrefManager){
        String privilege = sharedPrefManager.getSPPrivilege();
        TextView monitoringLabel = findViewById(R.id.monitoringLabel);
        if (privilege != null){
            if (privilege.equals("MGR") || privilege.equals("VP") || privilege.equals("EVP")){
                monitoringLabel.setVisibility(VISIBLE);
            } else {
                monitoringLabel.setVisibility(GONE);
            }
        } else {
            monitoringLabel.setVisibility(GONE);
        }
    }

    public void retrieveMonitoringData (SharedPrefManager sharedPrefManager){
        monitoringBawahan = (RecyclerView) findViewById(R.id.monitoringBawahan);

        RecyclerView.LayoutManager mLayoutManager =
                new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        monitoringBawahan.setLayoutManager(mLayoutManager);
        monitoringBawahan.setItemAnimator(new DefaultItemAnimator());

        String nik_tg = sharedPrefManager.getSPNIKTG();
        mApiInterface = ApiClient.getClient(getString(R.string.api_client_1)).create(ApiInterface.class);
        HashMap<String, String> params = new HashMap<>();
        params.put("nik_tg", nik_tg);
        Call<GetMonitoring> apiData = mApiInterface.getMonitoring(params);
        apiData.enqueue(new Callback<GetMonitoring>() {
            @Override
            public void onResponse(Call<GetMonitoring> call, Response<GetMonitoring> response) {
                Boolean status = response.body().getStatus();
                if (status) {
                    monitoringList = response.body().getListDataMonitoring();
                    monitoringAdapter = new MonitoringAdapter(monitoringList);
                    monitoringBawahan.setAdapter(monitoringAdapter);
//                    monitoringAdapter.notifyDataSetChanged();
                } else {
                    TextView monitoringLabel = findViewById(R.id.monitoringLabel);
                    monitoringLabel.setVisibility(GONE);
                }
            }

            @Override
            public void onFailure(Call<GetMonitoring> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"System Error, GET API Failed",Toast.LENGTH_LONG);
            }
        });
    }

    public void retrieveUlangTahunData (SharedPrefManager sharedPrefManager){
        ulangTahun = (RecyclerView) findViewById(R.id.ulangTahun);

        RecyclerView.LayoutManager mLayoutManager =
                new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        ulangTahun.setLayoutManager(mLayoutManager);
        ulangTahun.setItemAnimator(new DefaultItemAnimator());

        mApiInterface = ApiClient.getClient(getString(R.string.api_client_1)).create(ApiInterface.class);
        HashMap<String, String> params = new HashMap<>();
        Call<GetUlangTahun> apiData = mApiInterface.getUlangTahun(params);
        apiData.enqueue(new Callback<GetUlangTahun>() {
            @Override
            public void onResponse(Call<GetUlangTahun> call, Response<GetUlangTahun> response) {
                Boolean status = response.body().getStatus();
                if (status) {
                    TextView ulangTahunLabel = findViewById(R.id.ulangTahunLabel);
                    ulangTahunLabel.setVisibility(VISIBLE);
                    ulangTahunList = response.body().getListDataUlangTahun();
                    ulangTahunAdapter = new UlangTahunAdapter(ulangTahunList);
                    ulangTahun.setAdapter(ulangTahunAdapter);
//                    monitoringAdapter.notifyDataSetChanged();
                } else {
                    TextView ulangTahunLabel = findViewById(R.id.ulangTahunLabel);
                    ulangTahunLabel.setVisibility(GONE);
                }
            }

            @Override
            public void onFailure(Call<GetUlangTahun> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"System Error, GET API Failed",Toast.LENGTH_LONG);
            }
        });
    }

    public static void justifyListViewHeightBasedOnChildren (ListView listView) {

        ListAdapter adapter = listView.getAdapter();

        if (adapter == null) {
            return;
        }
        ViewGroup vg = listView;
        int totalHeight = 0;
        for (int i = 0; i < adapter.getCount(); i++) {
            View listItem = adapter.getView(i, null, vg);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams par = listView.getLayoutParams();
        par.height = totalHeight + (listView.getDividerHeight() * (adapter.getCount() - 1));
        listView.setLayoutParams(par);
        listView.requestLayout();
    }


    public Bitmap getBitmapFromURL(String imageUrl) {
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
//            Writer writer = new StringWriter();
//            e.printStackTrace(new PrintWriter(writer));
//            String s = writer.toString();
//            TextView errorLog = findViewById(R.id.errorLog);
//            errorLog.setText(s);
            e.printStackTrace();
            return null;
        }
    }

}
