package com.example.presensimitratel;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.StrictMode;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import pl.bclogic.pulsator4droid.library.PulsatorLayout;

import static android.view.View.VISIBLE;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Bitmap myImage = getBitmapFromURL(getString(R.string.background_login_url_2));

        ConstraintLayout rLayout= findViewById(R.id.login);

        //BitmapDrawable(obj) convert Bitmap object into drawable object.
        Drawable dr = new BitmapDrawable(myImage);
        rLayout.setBackgroundDrawable(dr);

        PulsatorLayout pulsatorLayout = findViewById(R.id.pulsator);
        pulsatorLayout.start();

        final FrameLayout login_btn = findViewById(R.id.login_btn);
        login_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                animateButtonWidth();
                fadeOutTextAndShowProgressDialog();
                nextAction();
            }
        });
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
            e.printStackTrace();
            return null;
        }
    }

    private void animateButtonWidth(){
        final FrameLayout login_btn = findViewById(R.id.login_btn);
        ValueAnimator anim = ValueAnimator.ofInt(login_btn.getMeasuredWidth(), getFabWidth());

        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int val = (Integer) valueAnimator.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams = login_btn.getLayoutParams();
                layoutParams.width = val;
                login_btn.setLayoutParams(layoutParams);
            }
        });
        anim.setDuration(250);
        anim.start();
    }

    private void fadeOutTextAndShowProgressDialog() {
        final TextView login_text = findViewById(R.id.login_text);
        login_text.animate().alpha(0f)
                .setDuration(250)
                .setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    showProgressDialog();
                }
            });

    }

    private void showProgressDialog() {
        final ProgressBar login_loading = findViewById(R.id.login_loading);
        login_loading.getIndeterminateDrawable()
                .setColorFilter(Color.parseColor("#FFFFFF"),PorterDuff.Mode.SRC_IN);
        login_loading.setVisibility(VISIBLE);
    }

    private void nextAction() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                revealButton();
                fadeOutProgressDialog();
                delayedStartNextActivity();
            }
        }, 2000);
    }

    private void revealButton() {
        FrameLayout login_btn = findViewById(R.id.login_btn);
        View login_reveal = findViewById(R.id.login_reveal);

        login_btn.setElevation(0f);
        login_reveal.setVisibility(VISIBLE);

        int cx = login_reveal.getWidth();
        int cy = login_reveal.getHeight();

        int x = (int) (getFabWidth() / 2 + login_btn.getX());
        int y = (int) (getFabWidth() / 2 + login_btn.getY());

        float finalRadius = Math.max(cx,cy) * 1.2f;

        Animator reveal = ViewAnimationUtils
                .createCircularReveal(login_reveal,x,y,getFabWidth(),finalRadius);

        reveal.setDuration(350);
        reveal.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                finish();
            }
        });

        reveal.start();
    }

    private void fadeOutProgressDialog() {
        ProgressBar login_loading = findViewById(R.id.login_loading);
        login_loading.animate().alpha(0f).setDuration(200).start();
    }

    private void delayedStartNextActivity(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
            }
        }, 100);
    }

    private int getFabWidth() {
        return (int) getResources().getDimension(R.dimen.fab_size);
    }
}
