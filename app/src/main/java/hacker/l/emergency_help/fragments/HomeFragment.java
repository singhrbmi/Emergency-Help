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
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
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
    LinearLayout layout_contacts, layout_pilicesire, layout_social, layout_hospital, lyout_whistle, layout_police, layout_ambulance, lyout_suraksha, lyout_help, lyout_about, lyout_account, lyout_barCode, lyout_share, lyout_setting;
    private Camera camera;
    TextView tv_address;
    private boolean isFlashOn;
    private boolean hasFlash;
    Camera.Parameters params;
    private CameraManager camManager;
    private int CAMERA_PERM = 0;
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
        showAdvise();
//        DashBoardActivity dashBoardActivity = (DashBoardActivity) context;
//        dashBoardActivity.setTitle("Home");
        police = MediaPlayer.create(context, R.raw.pilice);
        weshile = MediaPlayer.create(context, R.raw.killbill);
//        lyout_flash = view.findViewById(R.id.lyout_flash);
        layout_contacts = view.findViewById(R.id.layout_contacts);
        layout_pilicesire = view.findViewById(R.id.layout_pilicesire);
        layout_social = view.findViewById(R.id.layout_social);
        layout_hospital = view.findViewById(R.id.layout_hospital);
        layout_ambulance = view.findViewById(R.id.layout_ambulance);
        layout_police = view.findViewById(R.id.layout_police);
        lyout_suraksha = view.findViewById(R.id.lyout_suraksha);
//        lyout_help = view.findViewById(R.id.lyout_help);
        lyout_about = view.findViewById(R.id.lyout_about);
        lyout_account = view.findViewById(R.id.lyout_account);
        lyout_barCode = view.findViewById(R.id.lyout_barCode);
        tv_address = view.findViewById(R.id.tv_address);
        lyout_whistle = view.findViewById(R.id.lyout_whistle);
//        lyout_share = view.findViewById(R.id.lyout_share);
//        lyout_setting = view.findViewById(R.id.lyout_setting);
//        lyout_flash.setOnClickListener(this);
        layout_contacts.setOnClickListener(this);
        layout_pilicesire.setOnClickListener(this);
        layout_social.setOnClickListener(this);
        layout_hospital.setOnClickListener(this);
        layout_ambulance.setOnClickListener(this);
        layout_police.setOnClickListener(this);
        lyout_suraksha.setOnClickListener(this);
//        lyout_help.setOnClickListener(this);
        lyout_about.setOnClickListener(this);
        lyout_account.setOnClickListener(this);
        lyout_barCode.setOnClickListener(this);
        lyout_whistle.setOnClickListener(this);
//        lyout_share.setOnClickListener(this);
//        lyout_setting.setOnClickListener(this);
        appLocationService = new AppLocationService(context);
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
            tv_address.setVisibility(View.VISIBLE);
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
            case R.id.layout_police:
                UserComplentFragment userComplentFragment = UserComplentFragment.newInstance("", "");
                moveFragment(userComplentFragment);
                break;
            case R.id.layout_ambulance:
//                UserAdviseFragment userAdviseFragment = UserAdviseFragment.newInstance("", "");
//                moveFragment(userAdviseFragment);
                break;
//            case R.id.lyout_flash:
//                runFlashLight();
//                break;
            case R.id.layout_contacts:
                GetContactsFragment fragmentContacts = GetContactsFragment.newInstance("", "");
                moveFragment(fragmentContacts);
                break;
            case R.id.layout_pilicesire:
//                piliceSiren();
                break;
            case R.id.lyout_whistle:
//                whisilePlay();
                break;
            case R.id.layout_social:
                SocialFragment fragmentS = SocialFragment.newInstance("", "");
                moveFragment(fragmentS);
                break;
            case R.id.lyout_suraksha:
                SurakshaCavachFragment fragment = SurakshaCavachFragment.newInstance("", "");
                moveFragment(fragment);
                break;
            case R.id.lyout_help:
                HelpFragment fragmentHelp = HelpFragment.newInstance("", "");
                moveFragment(fragmentHelp);
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
            case R.id.lyout_share:
                ShareFragment fragmentShre = ShareFragment.newInstance("", "");
                moveFragment(fragmentShre);
                break;
            case R.id.lyout_setting:
                SettingsFragment fragmentSetting = SettingsFragment.newInstance("", "");
                moveFragment(fragmentSetting);
                break;
        }

    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
//        if (requestCode == CAMERA_PERM) {
//            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                runFlashLight();
//            } else {
//                Toast.makeText(context, "Permission was not Granted", Toast.LENGTH_SHORT).show();
//            }
//        } else {
//            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        }
//    }

    public void piliceSiren() {
        if (!police.isPlaying()) {
            police.start();
            police.isLooping();
        } else {
            police.pause();
        }
    }

    public void whisilePlay() {
        if (!weshile.isPlaying()) {
            weshile.start();
            weshile.isLooping();
        } else {
            weshile.pause();
        }
    }

    private void runFlashLight() {
        hasFlash = context.getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
        if (!hasFlash) {
            AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
            builder1.setMessage("Sorry, your device doesn't support flash light!");
            builder1.setCancelable(true);
            builder1.setTitle("Error");
            builder1.setPositiveButton(
                    "ok",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
                        }
                    });
            AlertDialog alert11 = builder1.create();
            alert11.show();
        }
        getCamera();
        if (isFlashOn) {
            turnOffFlash();
//            Toast.makeText(context, "Off", Toast.LENGTH_SHORT).show();
        } else {
            turnOnFlash();
//            Toast.makeText(context, "On", Toast.LENGTH_SHORT).show();
        }
    }

    private void getCamera() {

        if (camera == null) {
            try {
                camera = Camera.open();
                params = camera.getParameters();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    private void turnOnFlash() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            try {
                camManager = (CameraManager) context.getSystemService(Context.CAMERA_SERVICE);
                String cameraId = null; // Usually front camera is at 0 position.
                if (camManager != null) {
                    cameraId = camManager.getCameraIdList()[0];
                    camManager.setTorchMode(cameraId, true);
                }
            } catch (CameraAccessException e) {
                e.printStackTrace();
            }
        } else {
            camera = Camera.open();
            params = camera.getParameters();
            params.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
            camera.setParameters(params);
            camera.startPreview();
        }
        isFlashOn = true;
    }

    private void turnOffFlash() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            try {
                String cameraId;
                camManager = (CameraManager) context.getSystemService(Context.CAMERA_SERVICE);
                if (camManager != null) {
                    cameraId = camManager.getCameraIdList()[0]; // Usually front camera is at 0 position.
                    camManager.setTorchMode(cameraId, false);
                }
            } catch (CameraAccessException e) {
                e.printStackTrace();
            }
        } else {
            params = camera.getParameters();
            params.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
            camera.setParameters(params);
            camera.stopPreview();

        }
        isFlashOn = false;
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
