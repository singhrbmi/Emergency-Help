package hacker.l.emergency_help.activity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hacker.l.emergency_help.R;
import hacker.l.emergency_help.adapter.AdviseAdapter;
import hacker.l.emergency_help.database.DbHelper;
import hacker.l.emergency_help.fragments.AdminAdviseMgmtFragment;
import hacker.l.emergency_help.fragments.JharkhandAdminstrtiveFragment;
import hacker.l.emergency_help.models.MyPojo;
import hacker.l.emergency_help.models.Result;
import hacker.l.emergency_help.utility.Contants;
import hacker.l.emergency_help.utility.FontManager;


public class SplashActivity extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 3000;
    //    public static final double DESTROY_APP_TH = 131531.01001;
    public static final double DESTROY_APP_TH = 31539999999.9988899;
    TextView adminMsg;
    List<String> resultList, dateList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_ACTION_BAR);// Removes action bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);// Removes title bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        checkExpired();
        getAdminAdvise();
    }

    private void getAdminAdvise() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Contants.SERVICE_BASE_URL + Contants.getAdvise,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        MyPojo myPojo = new Gson().fromJson(response, MyPojo.class);
                        resultList = new ArrayList<>();
                        dateList = new ArrayList<>();
                        for (Result result : myPojo.getResult()) {
                            resultList.addAll(Arrays.asList(result.getAdvise()));
                            dateList.addAll(Arrays.asList(result.getDate()));
                        }
//                        Collections.reverse(resultList);
                        adminMsg.setText(resultList.get(resultList.size() - 1));
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void checkExpired() {
        Typeface ExtraOrnamentalNo = FontManager.getFontTypeface(this, "fonts/ExtraOrnamentalNo2.ttf");
        Animation left = AnimationUtils.loadAnimation(this, R.anim.left);
        Animation right = AnimationUtils.loadAnimation(this, R.anim.right);
        Animation top = AnimationUtils.loadAnimation(this, R.anim.top);
        Animation bottom = AnimationUtils.loadAnimation(this, R.anim.bottom);
        Animation bounce = AnimationUtils.loadAnimation(this, R.anim.bounce);
        ImageView imageView = findViewById(R.id.image);
        imageView.startAnimation(top);
        TextView textView = findViewById(R.id.tv);
        TextView tv1 = findViewById(R.id.tv1);
        adminMsg = findViewById(R.id.adminMsg);
        adminMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SplashActivity.this, AdviceViewerActivity.class);
                intent.putExtra("advise", adminMsg.getText().toString());
                intent.putExtra("date", dateList.get(dateList.size() - 1));
                startActivity(intent);
            }
        });
        Button btn_next = findViewById(R.id.btn_next);
        TextView tv2 = findViewById(R.id.tv2);
        TextView tv3 = findViewById(R.id.tv3);
        TextView tv4 = findViewById(R.id.tv4);
        TextView tv5 = findViewById(R.id.tv5);
        TextView tv6 = findViewById(R.id.tv6);
        TextView tv7 = findViewById(R.id.tv7);
        TextView tv8 = findViewById(R.id.tv8);
        TextView tv9 = findViewById(R.id.tv9);
        TextView tv10 = findViewById(R.id.tv10);
        TextView tv11 = findViewById(R.id.tv11);
        TextView tv12 = findViewById(R.id.tv12);
        TextView tv13 = findViewById(R.id.tv13);
        TextView tv14 = findViewById(R.id.tv14);
        TextView tv15 = findViewById(R.id.tv15);
        TextView tv16 = findViewById(R.id.tv16);
        TextView tv17 = findViewById(R.id.tv17);
        TextView tv18 = findViewById(R.id.tv18);
        TextView tv19 = findViewById(R.id.tv19);
        TextView tv20 = findViewById(R.id.tv20);
        TextView tv21 = findViewById(R.id.tv21);
        TextView tv22 = findViewById(R.id.tv22);
        textView.setTypeface(ExtraOrnamentalNo);
        tv1.setTypeface(ExtraOrnamentalNo);
        tv2.setTypeface(ExtraOrnamentalNo);
        tv3.setTypeface(ExtraOrnamentalNo);
        tv4.setTypeface(ExtraOrnamentalNo);
        tv5.setTypeface(ExtraOrnamentalNo);
        tv6.setTypeface(ExtraOrnamentalNo);
        tv7.setTypeface(ExtraOrnamentalNo);
        tv8.setTypeface(ExtraOrnamentalNo);
        tv9.setTypeface(ExtraOrnamentalNo);
        tv10.setTypeface(ExtraOrnamentalNo);
        tv11.setTypeface(ExtraOrnamentalNo);
        tv12.setTypeface(ExtraOrnamentalNo);
        tv13.setTypeface(ExtraOrnamentalNo);
        tv14.setTypeface(ExtraOrnamentalNo);
        tv15.setTypeface(ExtraOrnamentalNo);
        tv16.setTypeface(ExtraOrnamentalNo);
        tv17.setTypeface(ExtraOrnamentalNo);
        tv18.setTypeface(ExtraOrnamentalNo);
        tv19.setTypeface(ExtraOrnamentalNo);
        tv20.setTypeface(ExtraOrnamentalNo);
        tv21.setTypeface(ExtraOrnamentalNo);
        tv22.setTypeface(ExtraOrnamentalNo);
        textView.startAnimation(left);
        tv1.startAnimation(right);
        tv2.startAnimation(top);
        tv3.startAnimation(bottom);
        tv4.startAnimation(left);
        tv5.startAnimation(right);
        tv6.startAnimation(top);
        tv7.startAnimation(bottom);
        tv8.startAnimation(left);
        tv9.startAnimation(right);
        tv10.startAnimation(top);
        tv11.startAnimation(bottom);
        tv12.startAnimation(left);
        tv13.startAnimation(bounce);
        tv14.startAnimation(left);
        tv15.startAnimation(right);
        tv16.startAnimation(top);
        tv17.startAnimation(bottom);
        tv18.startAnimation(left);
        tv19.startAnimation(right);
        tv20.startAnimation(top);
        tv21.startAnimation(bottom);
        tv22.startAnimation(bounce);
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DbHelper dbHelper = new DbHelper(SplashActivity.this);
                Result result = dbHelper.getUserData();
                if (result == null) {
                    SplashActivity.this.startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                    SplashActivity.this.finish();
                } else {
                    Intent intent = new Intent(SplashActivity.this, DashBoardActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
//            }
//
//        }, SPLASH_TIME_OUT);
    }

    private void moveFragment(Fragment fragment) {
        FragmentManager fragmentManager = ((FragmentActivity) this).getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .addToBackStack(null)
                .commit();
    }

}
