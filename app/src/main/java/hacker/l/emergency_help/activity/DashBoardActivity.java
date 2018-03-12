package hacker.l.emergency_help.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import hacker.l.emergency_help.R;
import hacker.l.emergency_help.database.DbHelper;
import hacker.l.emergency_help.fragments.AboutFragment;
import hacker.l.emergency_help.fragments.AccountFragment;
import hacker.l.emergency_help.fragments.HelpFragment;
import hacker.l.emergency_help.fragments.HomeFragment;
import hacker.l.emergency_help.fragments.QRScannerFragment;
import hacker.l.emergency_help.fragments.SettingsFragment;
import hacker.l.emergency_help.fragments.ShareFragment;
import hacker.l.emergency_help.fragments.SurakshaCavachFragment;
import hacker.l.emergency_help.models.MyPojo;
import hacker.l.emergency_help.models.Result;
import hacker.l.emergency_help.utility.Contants;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

import static android.content.ContentValues.TAG;

public class DashBoardActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    LinearLayout lyout_suraksha, lyout_help, lyout_about, lyout_account, lyout_barCode, lyout_share, lyout_setting;
    DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        init();
    }

    private void init() {
        lyout_suraksha = findViewById(R.id.lyout_suraksha);
        lyout_help = findViewById(R.id.lyout_help);
        lyout_about = findViewById(R.id.lyout_about);
        lyout_account = findViewById(R.id.lyout_account);
        lyout_barCode = findViewById(R.id.lyout_barCode);
        lyout_share = findViewById(R.id.lyout_share);
        lyout_setting = findViewById(R.id.lyout_setting);
        lyout_suraksha.setOnClickListener(this);
        lyout_help.setOnClickListener(this);
        lyout_about.setOnClickListener(this);
        lyout_account.setOnClickListener(this);
        lyout_barCode.setOnClickListener(this);
        lyout_share.setOnClickListener(this);
        lyout_setting.setOnClickListener(this);
        HomeFragment fragment = HomeFragment.newInstance("", "");
        moveFragment(fragment);
        isStoragePermissionGranted();
        isConteactPermissionGranted();

    }


    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            try {
                super.onBackPressed();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dash_board, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.logout) {
            DbHelper dbHelper = new DbHelper(this);
            dbHelper.deleteUserData();
            Intent intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void navHide() {
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.lyout_suraksha:
                SurakshaCavachFragment fragment = SurakshaCavachFragment.newInstance("", "");
                moveFragment(fragment);
                navHide();
                break;
            case R.id.lyout_help:
                HelpFragment fragmentHelp = HelpFragment.newInstance("", "");
                moveFragment(fragmentHelp);
                navHide();
                break;
            case R.id.lyout_about:
                AboutFragment fragmentAbout = AboutFragment.newInstance("", "");
                moveFragment(fragmentAbout);
                navHide();
                break;
            case R.id.lyout_account:
                AccountFragment fragmentAccount = AccountFragment.newInstance("", "");
                moveFragment(fragmentAccount);
                navHide();
                break;
            case R.id.lyout_barCode:
                QRScannerFragment fragmentqr = QRScannerFragment.newInstance("", "");
                moveFragment(fragmentqr);
                navHide();
                break;
            case R.id.lyout_share:
                ShareFragment fragmentShre = ShareFragment.newInstance("", "");
                moveFragment(fragmentShre);
                navHide();
                break;
            case R.id.lyout_setting:
                SettingsFragment fragmentSetting = SettingsFragment.newInstance("", "");
                moveFragment(fragmentSetting);
                navHide();
                break;
        }
    }

    public boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            return true;
        }
    }

    public boolean isConteactPermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.READ_CONTACTS)
                    == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, 2);
                return false;
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            }
        }
        if (requestCode == 2) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            }
    }
    }

    private void moveFragment(Fragment fragment) {
        FragmentManager fragmentManager = ((FragmentActivity) this).getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .addToBackStack(null)
                .commit();
    }
}
