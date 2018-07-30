package hacker.l.emergency_help.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
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
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import hacker.l.emergency_help.R;
import hacker.l.emergency_help.database.DbHelper;
import hacker.l.emergency_help.fragments.AboutFragment;
import hacker.l.emergency_help.fragments.AccountFragment;
import hacker.l.emergency_help.fragments.AdminLoginFragment;
import hacker.l.emergency_help.fragments.HelpFragment;
import hacker.l.emergency_help.fragments.HomeFragment;
import hacker.l.emergency_help.fragments.JharkhandAdminstrtiveFragment;
import hacker.l.emergency_help.fragments.SettingsFragment;
import hacker.l.emergency_help.fragments.ShareFragment;
import hacker.l.emergency_help.fragments.SurakshaCavachFragment;

public class DashBoardActivity extends AppCompatActivity
        implements View.OnClickListener {
    LinearLayout lyout_suraksha, lyout_help, lyout_about, lyout_account, lyout_barCode, lyout_share, lyout_setting, lyout_home;
    DrawerLayout drawer;
    TextView tv_title;

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
        init();
    }

    private void init() {
        lyout_suraksha = findViewById(R.id.lyout_suraksha);
        lyout_help = findViewById(R.id.lyout_help);
        tv_title = findViewById(R.id.tv_title);
        lyout_about = findViewById(R.id.lyout_about);
        lyout_account = findViewById(R.id.lyout_account);
        lyout_barCode = findViewById(R.id.lyout_barCode);
        lyout_share = findViewById(R.id.lyout_share);
        lyout_setting = findViewById(R.id.lyout_setting);
        lyout_home = findViewById(R.id.lyout_home);
        lyout_suraksha.setOnClickListener(this);
        lyout_help.setOnClickListener(this);
        lyout_about.setOnClickListener(this);
        lyout_account.setOnClickListener(this);
        lyout_barCode.setOnClickListener(this);
        lyout_share.setOnClickListener(this);
        lyout_setting.setOnClickListener(this);
        lyout_home.setOnClickListener(this);
        Intent intent = getIntent();
        String keeys = intent.getStringExtra("key");
        if (keeys!=null&&keeys.equalsIgnoreCase("jhar")) {
            JharkhandAdminstrtiveFragment jharkhandAdminstrtiveFragment = JharkhandAdminstrtiveFragment.newInstance("", "");
            moveFragment(jharkhandAdminstrtiveFragment);
        } else {
            HomeFragment fragment = HomeFragment.newInstance("", "");
            moveHomeFragment(fragment);
        }
        isStoragePermissionGranted();
        isConteactPermissionGranted();
        isPhoneCallPermissionGranted();
        isSmsCallPermissionGranted();
        isCameraCallPermissionGranted();
        isLocationPermissionGranted();
        setTitle("Dashboard");
    }

    public void setTitle(String title) {
        tv_title.setText(title);
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
        if (id == R.id.admin) {
            AdminLoginFragment adminLoginFragment = AdminLoginFragment.newInstance("", "");
            moveFragment(adminLoginFragment);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void navHide() {
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.lyout_home:
                this.init();
                navHide();
                break;
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
                startActivity(new Intent(this, QrcodeScannerActivity.class));
//                navHide();
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

    public boolean isLocationPermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 6);
                return false;
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            return true;
        }
    }

    public boolean isPhoneCallPermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.CALL_PHONE)
                    == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 3);
                return false;
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            return true;
        }
    }

    public boolean isSmsCallPermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.SEND_SMS)
                    == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, 4);
                return false;
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            return true;
        }
    }

    public boolean isCameraCallPermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.CAMERA)
                    == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 5);
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
//        if (requestCode == 2) {
//            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//
//            }
//        }
    }

    private void moveFragment(Fragment fragment) {
        FragmentManager fragmentManager = ((FragmentActivity) this).getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .addToBackStack(null)
                .commit();
    }

    private void moveHomeFragment(Fragment fragment) {
        FragmentManager fragmentManager = ((FragmentActivity) this).getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                // .addToBackStack(null)
                .commit();
    }

    //for hid keyboard when tab outside edittext box
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int) event.getRawX(), (int) event.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent(event);
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }
}
