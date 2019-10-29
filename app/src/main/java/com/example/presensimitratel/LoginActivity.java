package com.example.presensimitratel;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.ActivityOptions;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.StrictMode;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

import pl.bclogic.pulsator4droid.library.PulsatorLayout;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.example.presensimitratel.GlobalVar;
import com.example.presensimitratel.Model.DataUser;
import com.example.presensimitratel.Model.GetLogin;
import com.example.presensimitratel.Model.PostLogin;
import com.example.presensimitratel.Rest.ApiClient;
import com.example.presensimitratel.Rest.ApiInterface;

import static android.service.autofill.Validators.or;
import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

public class LoginActivity extends AppCompatActivity {

    private static GlobalVar globalVar;
    ApiInterface mApiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPrefManager sharedPrefManager;
        sharedPrefManager = new SharedPrefManager(this);
        if (sharedPrefManager.getSPSudahLogin()){
            //Bundle bundle = ActivityOptions.makeSceneTransitionAnimation(LoginActivity.this).toBundle();
            //startActivity(new Intent(LoginActivity.this, MainActivity.class), bundle);
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }
        setContentView(R.layout.activity_login);

        if (sharedPrefManager.getSPNIKTG() != null){
            EditText username = findViewById(R.id.username);
            username.setText(sharedPrefManager.getSPNIKTG());
        }

        mApiInterface = ApiClient.getClient(getString(R.string.api_client_1)).create(ApiInterface.class);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        ConstraintLayout rLayout= findViewById(R.id.login);
        Bitmap icon = BitmapFactory.decodeResource(this.getResources(),
                R.drawable.background_login);

        Drawable dr = new BitmapDrawable(icon);
        rLayout.setBackgroundDrawable(dr);
        Bitmap myImage = getBitmapFromURL(getString(R.string.background_login_url_1));

        //BitmapDrawable(obj) convert Bitmap object into drawable object.
        Drawable dr2 = new BitmapDrawable(myImage);
        rLayout.setBackgroundDrawable(dr2);

        PulsatorLayout pulsatorLayout = findViewById(R.id.pulsator);
        pulsatorLayout.start();

        globalVar = (GlobalVar) this.getApplication();
        globalVar.setSomeVariable("1");

        ImageView imageView = findViewById(R.id.logo);
        View.OnClickListener clickListener = new View.OnClickListener() {
            public void onClick(View v) {
                if (v.equals(imageView)) {
                    String s = globalVar.getSomeVariable();
                    if (s == "1"){
                        pulsatorLayout.stop();
                        globalVar.setSomeVariable("0");
                    } else {
                        pulsatorLayout.start();
                        globalVar.setSomeVariable("1");
                    }
                }
            }
        };
        imageView.setOnClickListener(clickListener);

        EditText form1 = findViewById(R.id.username);
        EditText form2 = findViewById(R.id.password);

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
                if(s.length() == 0){
                    TextInputLayout til = (TextInputLayout) findViewById(R.id.etUsernameLayout);
                    til.setError("NIK tidak boleh kosong");
                } else {
                    TextInputLayout til = (TextInputLayout) findViewById(R.id.etUsernameLayout);
                    til.setError(null);
                }
            }
        });

        form2.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if(s.length() == 0){
                    TextInputLayout til = (TextInputLayout) findViewById(R.id.etPasswordLayout);
                    til.setError("Password tidak boleh kosong");
                } else {
                    TextInputLayout til = (TextInputLayout) findViewById(R.id.etPasswordLayout);
                    til.setError(null);
                }
            }
        });

        final FrameLayout login_btn = findViewById(R.id.login_btn);
        login_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                TextView error = findViewById(R.id.error_message);
                error.setText("");
                EditText form1 = findViewById(R.id.username);
                EditText form2 = findViewById(R.id.password);
                String username = form1.getText().toString();
                String password = form2.getText().toString();
                if ((username.matches("")) || (password.matches(""))){
                    if (username.matches("")){
                        TextInputLayout til = (TextInputLayout) findViewById(R.id.etUsernameLayout);
                        til.setError("NIK tidak boleh kosong");
                    }
                    if (password.matches("")){
                        TextInputLayout til = (TextInputLayout) findViewById(R.id.etPasswordLayout);
                        til.setError("Password tidak boleh kosong");
                    }

                } else {
                    animateButtonWidth();
                    fadeOutTextAndShowProgressDialog();
                    Call<PostLogin> loginValidation = mApiInterface.postLogin(username, password);
                    loginValidation.enqueue(new Callback<PostLogin>() {
                        @Override
                        public void onResponse(Call<PostLogin> call, Response<PostLogin> response) {
                            Boolean status = response.body().getStatus();
                            if (status){
                                sharedPrefManager.saveSPBoolean(SharedPrefManager.SP_SUDAH_LOGIN, true);
                                String nik_tg = response.body().getNIKTG();
                                HashMap<String, String> params = new HashMap<>();
                                params.put("nik_tg", nik_tg);
                                Call<GetLogin> sessionData = mApiInterface.getLogin(params);
                                sessionData.enqueue(new Callback<GetLogin>() {
                                    @Override
                                    public void onResponse(Call<GetLogin> call, Response<GetLogin> response2) {
                                        Boolean status = response2.body().getStatus();
                                        if (status) {
                                            List<DataUser>  dataUser = response2.body().getListDataUser();
                                            setSession(sharedPrefManager, dataUser);
                                            nextAction();
                                        } else {
                                            hideProgressDialog();
                                            reverseAnimateButtonWidth();
                                            fadeInTextAndShowProgressDialog();
                                            String message = response2.body().getMessage();
                                            TextView error = findViewById(R.id.error_message);
                                            error.setText(message);
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<GetLogin> call, Throwable t) {
                                        hideProgressDialog();
                                        reverseAnimateButtonWidth();
                                        fadeInTextAndShowProgressDialog();
                                        String message = "System failure";
                                        TextView error = findViewById(R.id.error_message);
                                        error.setText(message);
                                    }
                                });
                            } else {
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        hideProgressDialog();
                                        reverseAnimateButtonWidth();
                                        fadeInTextAndShowProgressDialog();
                                        String message = response.body().getMessage();
                                        TextView error = findViewById(R.id.error_message);
                                        error.setText(message);
                                    }
                                }, 500);
                            }
                        }

                        @Override
                        public void onFailure(Call<PostLogin> call, Throwable t) {
                            Log.e("Retrofit Post", t.toString());
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

    private void reverseAnimateButtonWidth(){
        final FrameLayout login_btn = findViewById(R.id.login_btn);
        ValueAnimator anim = ValueAnimator.ofInt(login_btn.getMeasuredWidth(),(int) getResources().getDimension(R.dimen.login_btn_size));

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

    private void fadeInTextAndShowProgressDialog() {
        final TextView login_text = findViewById(R.id.login_text);
        login_text.animate().alpha(1f)
                .setDuration(250).start();

    }

    private void showProgressDialog() {
        ProgressBar login_loading = findViewById(R.id.login_loading);
        login_loading.getIndeterminateDrawable()
                .setColorFilter(Color.parseColor("#FFFFFF"),PorterDuff.Mode.SRC_IN);
        login_loading.animate().alpha(1f).setDuration(100).start();
        login_loading.setVisibility(VISIBLE);
    }

    private void hideProgressDialog() {
        ProgressBar login_loading = findViewById(R.id.login_loading);
//        login_loading.animate().alpha(0f).setDuration(100).start();
//        login_loading.setVisibility(View.GONE);
        AlphaAnimation alphaAnim = new AlphaAnimation(1.0f, 0f);
        alphaAnim.setDuration (400);
        login_loading.startAnimation(alphaAnim);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                login_loading.setVisibility(INVISIBLE);
            }
        }, 400);

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
                //Bundle bundle = ActivityOptions.makeSceneTransitionAnimation(LoginActivity.this).toBundle();
                //startActivity(new Intent(LoginActivity.this, MainActivity.class), bundle);
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
            }
        }, 100);
    }

    private void setSession(SharedPrefManager shared, List<DataUser> dataUser){
        shared.saveSPString(SharedPrefManager.SP_NIK, dataUser.get(0).getNIK());
        shared.saveSPString(SharedPrefManager.SP_NIK_TG, dataUser.get(0).getNIKTG());
        shared.saveSPString(SharedPrefManager.SP_NAME, dataUser.get(0).getName());
        shared.saveSPString(SharedPrefManager.SP_EMAIL, dataUser.get(0).getEmail());
        shared.saveSPString(SharedPrefManager.SP_BAND, dataUser.get(0).getBand());
        shared.saveSPString(SharedPrefManager.SP_UNIT, dataUser.get(0).getUnit());
        shared.saveSPString(SharedPrefManager.SP_POSISI, dataUser.get(0).getPosisi());
        shared.saveSPString(SharedPrefManager.SP_AREA, dataUser.get(0).getArea());
        shared.saveSPString(SharedPrefManager.SP_KOTA, dataUser.get(0).getKota());
        shared.saveSPString(SharedPrefManager.SP_ALAMAT, dataUser.get(0).getAlamat());
        shared.saveSPString(SharedPrefManager.SP_TIMEZONE, dataUser.get(0).getTimezone());
        shared.saveSPString(SharedPrefManager.SP_PRIVILEGE, dataUser.get(0).getPrivilege());
        shared.saveSPString(SharedPrefManager.SP_MANAGER_CODE, dataUser.get(0).getManagerCode());
        shared.saveSPString(SharedPrefManager.SP_VP_CODE, dataUser.get(0).getVPCode());
        shared.saveSPString(SharedPrefManager.SP_EVP_CODE, dataUser.get(0).getEVPCode());
    }

    private int getFabWidth() {
        return (int) getResources().getDimension(R.dimen.fab_size);
    }
}
