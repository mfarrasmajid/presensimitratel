package com.example.presensimitratel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Button;
import android.widget.TextView;


public class SideBarActivity extends AppCompatActivity {
    SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_side_bar);
        sharedPrefManager = new SharedPrefManager(this);

        TextView name = findViewById(R.id.name);
        name.setText(sharedPrefManager.getSPName());
        TextView email = findViewById(R.id.email);
        email.setText(sharedPrefManager.getSPEmail());

        AppCompatButton home = findViewById(R.id.home);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_OK, new Intent().putExtra("page", "home"));
                onBackPressed();
            }
        });
        AppCompatButton report = findViewById(R.id.report);
        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_OK, new Intent().putExtra("page", "report"));
                onBackPressed();
            }
        });
        AppCompatButton profile = findViewById(R.id.profile_change);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_OK, new Intent().putExtra("page", "profile"));
                onBackPressed();
            }
        });
        AppCompatButton logout = findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_OK, new Intent().putExtra("page", "logout"));
                sharedPrefManager.saveSPBoolean(SharedPrefManager.SP_SUDAH_LOGIN, false);
                startActivity(new Intent(SideBarActivity.this, LoginActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                finish();
            }
        });
    }

    public void goBack(View view) {
        setResult(RESULT_CANCELED);
        onBackPressed();
    }
}
