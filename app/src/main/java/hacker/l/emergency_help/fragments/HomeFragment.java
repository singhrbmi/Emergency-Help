package hacker.l.emergency_help.fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.location.Location;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
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
import com.squareup.picasso.Picasso;

import org.jsoup.Jsoup;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hacker.l.emergency_help.R;
import hacker.l.emergency_help.activity.DashBoardActivity;
import hacker.l.emergency_help.activity.PlaceActivity;
import hacker.l.emergency_help.activity.QrcodeScannerActivity;
import hacker.l.emergency_help.adapter.AdviseAdapter;
import hacker.l.emergency_help.models.MyPojo;
import hacker.l.emergency_help.models.Result;
import hacker.l.emergency_help.utility.AppLocationService;
import hacker.l.emergency_help.utility.Contants;
import hacker.l.emergency_help.utility.LocationAddress;
import hacker.l.emergency_help.utility.Utility;


public class HomeFragment extends Fragment implements View.OnClickListener {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    View view;
    Context context;
    LinearLayout layout_utility, layout_bOrg, layout_social, layout_panic, layout_hospital, layout_seggestion, layout_police, layout_jharadmistrtive, lyout_suraksha, lyout_help, lyout_about, lyout_account, lyout_barCode, lyout_share, lyout_setting;
    TextView tv_address;
    Camera.Parameters params;
    AppLocationService appLocationService;
    MediaPlayer police, weshile;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        context = getActivity();
        view = inflater.inflate(R.layout.fragment_home, container, false);
        init();
        return view;
    }

    private void init() {
//        showAdvise();// pending ........
        DashBoardActivity dashBoardActivity = (DashBoardActivity) context;
        dashBoardActivity.setTitle("Dashboard");
        police = MediaPlayer.create(context, R.raw.pilice);
        weshile = MediaPlayer.create(context, R.raw.killbill);
        layout_social = view.findViewById(R.id.layout_social);
        layout_hospital = view.findViewById(R.id.layout_hospital);
        layout_jharadmistrtive = view.findViewById(R.id.layout_jharadmistrtive);
        layout_police = view.findViewById(R.id.layout_police);
        lyout_suraksha = view.findViewById(R.id.lyout_suraksha);
        lyout_about = view.findViewById(R.id.lyout_about);
        lyout_account = view.findViewById(R.id.lyout_account);
        lyout_barCode = view.findViewById(R.id.lyout_barCode);
        tv_address = view.findViewById(R.id.tv_address);
        layout_utility = view.findViewById(R.id.layout_utility);
        layout_seggestion = view.findViewById(R.id.layout_seggestion);
        layout_panic = view.findViewById(R.id.layout_panic);
        layout_bOrg = view.findViewById(R.id.layout_bOrg);
        layout_seggestion.setOnClickListener(this);
        layout_social.setOnClickListener(this);
        layout_hospital.setOnClickListener(this);
        layout_jharadmistrtive.setOnClickListener(this);
        layout_police.setOnClickListener(this);
        lyout_suraksha.setOnClickListener(this);
        lyout_about.setOnClickListener(this);
        lyout_account.setOnClickListener(this);
        lyout_barCode.setOnClickListener(this);
        layout_utility.setOnClickListener(this);
        layout_panic.setOnClickListener(this);
        layout_bOrg.setOnClickListener(this);
        appLocationService = new AppLocationService(context);
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.fade_in);
        animation.reset();
        layout_social.clearAnimation();
        layout_hospital.clearAnimation();
        layout_police.clearAnimation();
        lyout_suraksha.clearAnimation();
        lyout_about.clearAnimation();
        lyout_account.clearAnimation();
        lyout_barCode.clearAnimation();
        layout_jharadmistrtive.clearAnimation();
        layout_utility.clearAnimation();
        layout_seggestion.clearAnimation();
        layout_panic.clearAnimation();
        layout_bOrg.clearAnimation();
        layout_social.setAnimation(animation);
        layout_hospital.setAnimation(animation);
        layout_police.setAnimation(animation);
        lyout_suraksha.setAnimation(animation);
        lyout_about.setAnimation(animation);
        lyout_account.setAnimation(animation);
        lyout_barCode.setAnimation(animation);
        layout_jharadmistrtive.setAnimation(animation);
        layout_utility.setAnimation(animation);
        layout_seggestion.setAnimation(animation);
        layout_panic.setAnimation(animation);
        layout_bOrg.setAnimation(animation);
        checkForceUpdate();
    }
    public void checkForceUpdate() {

        new AsyncTask<Void, String, String>() {
            @Override
            protected String doInBackground(Void... voids) {

                String newVersion = null;
                try {
                    newVersion = Jsoup.connect("https://play.google.com/store/apps/details?id=" + context.getPackageName() + "&hl=it")
                            .timeout(10*1000)
                            .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
                            .referrer("http://www.google.com")
                            .get()
                            .select("div[itemprop=softwareVersion]")
                            .first()
                            .ownText();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return newVersion;
            }

            @Override
            protected void onPostExecute(String onlineVersion) {
                super.onPostExecute(onlineVersion);
                if (onlineVersion != null && !onlineVersion.isEmpty()) {
                    if (Float.valueOf(Utility.getAppVersionName(context)) < Float.valueOf(onlineVersion)) {
                        //show dialog
                        showUpdateDialog(context);
                    } else {
                        //  MoveNextScreen();
                    }
                }

                Log.d("update", "Current version " + Utility.getAppVersionName(context) + "playstore version " + onlineVersion);
            }
        }.execute();
    }

    private void showUpdateDialog(final Context context) {
        //alert for error message

        final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);
        final android.app.AlertDialog alert = builder.create();
        View view = alert.getLayoutInflater().inflate(R.layout.custom_update_alert, null);
        TextView title = (TextView) view.findViewById(R.id.textMessage);
        TextView title2 = (TextView) view.findViewById(R.id.textMessage2);
        Button ok = (Button) view.findViewById(R.id.buttonUpdate);
        Button buttonCancel = (Button) view.findViewById(R.id.buttonCancel);
        alert.setCustomTitle(view);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=" + "fusionsoftware.loop.emergency_help" + "&hl=en"));
                context.startActivity(intent);
                alert.dismiss();
            }
        });
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert.dismiss();
                //MoveNextScreen();
            }
        });
        alert.show();
        alert.setCanceledOnTouchOutside(false);
    }
    @Override
    public void onStart() {
        super.onStart();
        Location location = appLocationService
                .getLocation(LocationManager.GPS_PROVIDER);
        if (location != null) {
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();
            LocationAddress locationAddress = new LocationAddress();
            locationAddress.getAddressFromLocation(latitude, longitude, context.getApplicationContext(), new GeocoderHandler());
        } else {
            //showSettingsAlert();
        }
    }

    public void showSettingsAlert() {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                context);
        alertDialog.setTitle("SETTINGS");
        alertDialog.setMessage("Enable Location Provider! Go to settings menu?");
        alertDialog.setPositiveButton("Settings",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(
                                Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        context.startActivity(intent);
                        dialog.dismiss();
                    }
                });
        alertDialog.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        alertDialog.show();
    }

    private class GeocoderHandler extends Handler {
        @Override
        public void handleMessage(Message message) {
            String locationAddress;
            switch (message.what) {
                case 1:
                    Bundle bundle = message.getData();
                    locationAddress = bundle.getString("address");
                    break;
                default:
                    locationAddress = null;
            }
//            tv_address.setVisibility(View.VISIBLE);
            tv_address.setText(locationAddress);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_hospital:
                Intent intent = new Intent(context, PlaceActivity.class);
//                intent.putExtra("key", "hospital");
                startActivity(intent);
                break;
            case R.id.layout_seggestion:
                UserComplentFragment userComplentFragment = UserComplentFragment.newInstance("", "");
                moveFragment(userComplentFragment);
                break;
            case R.id.layout_utility:
                UtilityFragment utilityFragment = UtilityFragment.newInstance("", "");
                moveFragment(utilityFragment);
                break;
            case R.id.layout_police:
                PoliceAdminstrtiveFragment policeAdminstrtiveFragment = PoliceAdminstrtiveFragment.newInstance("", "");
                moveFragment(policeAdminstrtiveFragment);
                break;
            case R.id.layout_jharadmistrtive:
                JharkhandAdminstrtiveFragment jharkhandAdminstrtiveFragment = JharkhandAdminstrtiveFragment.newInstance("", "");
                moveFragment(jharkhandAdminstrtiveFragment);
                break;
            case R.id.layout_bOrg:
                BusinessOrgFragment businessOrgFragment = BusinessOrgFragment.newInstance("", "");
                moveFragment(businessOrgFragment);
                break;
            case R.id.layout_social:
                SocialFragment fragmentS = SocialFragment.newInstance("", "");
                moveFragment(fragmentS);
                break;
            case R.id.lyout_suraksha:
                SurakshaCavachFragment fragment = SurakshaCavachFragment.newInstance("", "");
                moveFragment(fragment);
                break;
            case R.id.lyout_about:
                AboutFragment fragmentAbout = AboutFragment.newInstance("", "");
                moveFragment(fragmentAbout);
                break;
            case R.id.lyout_account:
                AccountFragment fragmentAccount = AccountFragment.newInstance("", "");
                moveFragment(fragmentAccount);
                break;
            case R.id.lyout_barCode:
                startActivity(new Intent(context, QrcodeScannerActivity.class));
                break;
        }
    }

    private void moveFragment(Fragment fragment) {
        FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .addToBackStack(null)
                .commit();
    }

    void showAdvise() {
        if (!Utility.isOnline(context)) {
            Toast.makeText(context, "Connect Internet connection", Toast.LENGTH_SHORT).show();
        }
        final List<Result> resultList = new ArrayList<>();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Contants.SERVICE_BASE_URL + Contants.getAdvise,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        MyPojo myPojo = new Gson().fromJson(response, MyPojo.class);
                        if (myPojo != null) {
                            resultList.addAll(Arrays.asList(myPojo.getResult()));
                            if (resultList != null && resultList.size() != 0) {
                                final Dialog dialog = new Dialog(context);
                                dialog.setCancelable(false);
                                dialog.setCanceledOnTouchOutside(false);
                                dialog.setContentView(R.layout.custom_advise_dialog);
                                Window window = dialog.getWindow();
                                window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//                            dialog.show();
                                TextView date = dialog.findViewById(R.id.tv_date);
                                TextView advise = dialog.findViewById(R.id.tv_advise);
                                TextView ok = dialog.findViewById(R.id.tv_ok);
                                ImageView image = dialog.findViewById(R.id.image);
                                date.setText(resultList.get(resultList.size() - 1).getDate());
                                advise.setText(resultList.get(resultList.size() - 1).getAdvise());
                                if (resultList.get(resultList.size() - 1).getImage() != null && !resultList.get(resultList.size() - 1).getImage().equalsIgnoreCase("") && !resultList.get(resultList.size() - 1).getImage().equalsIgnoreCase("no")) {
                                    Picasso.with(context).load(resultList.get(resultList.size() - 1).getImage()).into(image);
                                } else {
                                    image.setImageDrawable(getResources().getDrawable(R.drawable.logo));
                                }
                                final SharedPreferences sharedPreferences = context.getSharedPreferences("advise", Context.MODE_PRIVATE);
                                String data = sharedPreferences.getString("key", "");
                                if (data.isEmpty() || !resultList.get(resultList.size() - 1).getAdvise().equalsIgnoreCase(data)) {
                                    dialog.show();
                                } else {
                                    dialog.dismiss();
                                }
                                ok.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.putString("key", resultList.get(resultList.size() - 1).getAdvise());
                                        editor.apply();
                                        dialog.dismiss();
                                    }
                                });
                            }
                        }
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
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);

    }
}
