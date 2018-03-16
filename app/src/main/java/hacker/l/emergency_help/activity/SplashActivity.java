package hacker.l.emergency_help.activity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import hacker.l.emergency_help.R;
import hacker.l.emergency_help.database.DbHelper;
import hacker.l.emergency_help.models.Result;

import static android.support.test.InstrumentationRegistry.getContext;

public class SplashActivity extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 3000;
    //    public static final double DESTROY_APP_TH = 31531;
    public static final double DESTROY_APP_TH = 31539999999.9988899;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_ACTION_BAR);// Removes action bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);// Removes title bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        checkExpired();
    }

    private void checkExpired() {
        String android_id = Settings.Secure.getString(getContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);
        PackageManager pm = getPackageManager();
        PackageInfo pi = null;
        try {
            pi = pm.getPackageInfo(this.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        long publishTimeInMilli = pi.firstInstallTime;
        // upload device id and publishTimeInMilli in api...............
        long now = System.currentTimeMillis();
        if ((now - publishTimeInMilli) > DESTROY_APP_TH) {
            //just finish the the activity (and thus the app) or do something else
            finish();
            Toast.makeText(this, "Trial period expired!!", Toast.LENGTH_SHORT).show();

        } else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    DbHelper dbHelper = new DbHelper(SplashActivity.this);
                    Result result = dbHelper.getUserData();
                    if (result == null) {
                        SplashActivity.this.startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                        SplashActivity.this.finish();
                    } else {
                        SplashActivity.this.startActivity(new Intent(SplashActivity.this, DashBoardActivity.class));
                        SplashActivity.this.finish();
                    }
                }
            }, SPLASH_TIME_OUT);
        }
    }
}
