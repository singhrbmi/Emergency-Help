package hacker.l.emergency_help.fragments;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.w3c.dom.Node;

import hacker.l.emergency_help.R;
import hacker.l.emergency_help.activity.QrcodeScannerActivity;


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
    LinearLayout lyout_flash, layout_contacts, layout_pilicesire, layout_social, layout_hospital, layout_police, layout_ambulance, lyout_suraksha, lyout_help, lyout_about, lyout_account, lyout_barCode, lyout_share, lyout_setting;
    private Camera camera;
    private boolean isFlashOn;
    private boolean hasFlash;
    Camera.Parameters params;
    private CameraManager camManager;
    private int CAMERA_PERM = 0;

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
        lyout_flash = view.findViewById(R.id.lyout_flash);
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
//        lyout_share = view.findViewById(R.id.lyout_share);
//        lyout_setting = view.findViewById(R.id.lyout_setting);
        lyout_flash.setOnClickListener(this);
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
//        lyout_share.setOnClickListener(this);
//        lyout_setting.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.layout_hospital:
//                Intent intent = new Intent(context, MapsActivity.class);
//                intent.putExtra("key", "hospital");
//                startActivity(intent);
//                break;
//            case R.id.layout_police:
//                Intent intentPolice = new Intent(context, MapsActivity.class);
//                intentPolice.putExtra("key", "police");
//                startActivity(intentPolice);
//                break;
//            case R.id.layout_ambulance:
//                Intent intentaa = new Intent(context, MapsActivity.class);
//                intentaa.putExtra("key", "ambulance");
//                startActivity(intentaa);
//                break;
            case R.id.lyout_flash:
                runFlashLight();
                break;
            case R.id.layout_contacts:
                GetContactsFragment fragmentContacts = GetContactsFragment.newInstance("", "");
                moveFragment(fragmentContacts);
                break;
            case R.id.layout_pilicesire:
                //piliceSiren();
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
        MediaPlayer ring = MediaPlayer.create(context, R.raw.pilice);
        if (ring.isPlaying()) {
            ring.pause();
        } else {
            ring.start();
            ring.isPlaying();
            ring.isLooping();
        }
    }

    public void whisilePlay() {
        MediaPlayer ring = MediaPlayer.create(context, R.raw.killbill);
        if (ring.isPlaying()) {
            ring.pause();
        } else {
            ring.start();
            ring.isPlaying();
            ring.isLooping();
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

}
