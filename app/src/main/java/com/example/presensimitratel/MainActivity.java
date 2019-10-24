package com.example.presensimitratel;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.StrictMode;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.presensimitratel.Adapter.DataKaryawanAdapter;
import com.example.presensimitratel.Rest.ApiInterface;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;

import de.hdodenhof.circleimageview.CircleImageView;


public class MainActivity extends AppCompatActivity {

    ListView listView;
    ApiInterface mApiInterface;

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

        final Animation slideUp = AnimationUtils.loadAnimation(this,R.anim.slide_up);
        CircleImageView element1 = findViewById(R.id.profile_image);
        element1.setVisibility(View.INVISIBLE);
        TextView element2 = findViewById(R.id.selamatDatang);
        element2.setVisibility(View.INVISIBLE);
        TextView element3 = findViewById(R.id.tanggal);
        element3.setVisibility(View.INVISIBLE);
        TextView element4 = findViewById(R.id.jamIn);
        element4.setVisibility(View.INVISIBLE);
        TextView element5 = findViewById(R.id.jamOut);
        element5.setVisibility(View.INVISIBLE);
        TextView element6 = findViewById(R.id.ipIn);
        element6.setVisibility(View.INVISIBLE);
        TextView element7 = findViewById(R.id.ipOut);
        element7.setVisibility(View.INVISIBLE);
        LinearLayout element8 = findViewById(R.id.miscIn);
        element8.setVisibility(View.INVISIBLE);
        LinearLayout element9 = findViewById(R.id.miscOut);
        element9.setVisibility(View.INVISIBLE);
        TextView element10 = findViewById(R.id.ketTag);
        element10.setVisibility(View.INVISIBLE);
        FrameLayout element11 = findViewById(R.id.keteranganFrame);
        element11.setVisibility(View.INVISIBLE);
        TextView element12 = findViewById(R.id.dataLabel);
        element12.setVisibility(View.INVISIBLE);
        ListView element13 = findViewById(R.id.dataKaryawan);
        element13.setVisibility(View.INVISIBLE);

        final Context context = MainActivity.this;

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                element1.startAnimation(AnimationUtils.loadAnimation(context, R.anim.slide_up));
                element1.setVisibility(View.VISIBLE);
            }
        }, 50);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                element2.startAnimation(AnimationUtils.loadAnimation(context, R.anim.slide_up));
                element2.setVisibility(View.VISIBLE);
            }
        }, 100);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                element3.startAnimation(AnimationUtils.loadAnimation(context, R.anim.slide_up));
                element3.setVisibility(View.VISIBLE);
            }
        }, 150);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                element4.startAnimation(AnimationUtils.loadAnimation(context, R.anim.slide_up));
                element4.setVisibility(View.VISIBLE);
                element5.startAnimation(AnimationUtils.loadAnimation(context, R.anim.slide_up));
                element5.setVisibility(View.VISIBLE);
            }
        }, 200);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                element6.startAnimation(AnimationUtils.loadAnimation(context, R.anim.slide_up));
                element6.setVisibility(View.VISIBLE);
                element7.startAnimation(AnimationUtils.loadAnimation(context, R.anim.slide_up));
                element7.setVisibility(View.VISIBLE);
            }
        }, 250);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                element8.startAnimation(AnimationUtils.loadAnimation(context, R.anim.slide_up));
                element8.setVisibility(View.VISIBLE);
                element9.startAnimation(AnimationUtils.loadAnimation(context, R.anim.slide_up));
                element9.setVisibility(View.VISIBLE);
            }
        }, 300);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                element10.startAnimation(AnimationUtils.loadAnimation(context, R.anim.slide_up));
                element10.setVisibility(View.VISIBLE);
            }
        }, 350);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                element11.startAnimation(AnimationUtils.loadAnimation(context, R.anim.slide_up));
                element11.setVisibility(View.VISIBLE);
            }
        }, 400);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                element12.startAnimation(AnimationUtils.loadAnimation(context, R.anim.slide_up));
                element12.setVisibility(View.VISIBLE);
                element13.startAnimation(AnimationUtils.loadAnimation(context, R.anim.slide_up));
                element13.setVisibility(View.VISIBLE);
            }
        }, 450);

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

        String nik = sharedPrefManager.getSPNIK();
        String[] labelArray = {"NIK TG", "Unit", "Posisi", "Band"};
        String nik_tg = sharedPrefManager.getSPNIKTG();
        String unit = sharedPrefManager.getSPUnit();
        String posisi = sharedPrefManager.getSPPosisi();
        String band = sharedPrefManager.getSPBand();
        String[] dataArray = {nik_tg,unit,posisi,band};

        DataKaryawanAdapter karyawan = new DataKaryawanAdapter(this,labelArray,dataArray);
        listView = findViewById(R.id.dataKaryawan);
        listView.setAdapter(karyawan);
        justifyListViewHeightBasedOnChildren(listView);

        //BitmapDrawable(obj) convert Bitmap object into drawable object.
//        Drawable dr = new BitmapDrawable(myImage);
//        Bitmap myImage = getBitmapFromURL(getString(R.string.profile_image).concat(nik).concat(".jpg"));
//        profileImage.setImageBitmap(myImage);

        ScrollView scroll = findViewById(R.id.scroll);
        scroll.smoothScrollTo(0,0);

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
            Writer writer = new StringWriter();
            e.printStackTrace(new PrintWriter(writer));
            String s = writer.toString();
            TextView errorLog = findViewById(R.id.errorLog);
            errorLog.setText(s);
//            e.printStackTrace();
            return null;
        }
    }

}
