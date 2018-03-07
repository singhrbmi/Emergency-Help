package hacker.l.emergency_help.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import hacker.l.emergency_help.R;


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
    LinearLayout lyout_flash, layout_contacts, layout_pilicesire, layout_whistle;
    private Camera camera;
    private boolean isFlashOn;
    private boolean hasFlash;
    Camera.Parameters params;
    private CameraManager camManager;
    MediaPlayer police, whistle;

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
        layout_whistle = view.findViewById(R.id.layout_whistle);
        lyout_flash.setOnClickListener(this);
        layout_contacts.setOnClickListener(this);
        layout_pilicesire.setOnClickListener(this);
        layout_whistle.setOnClickListener(this);
        whistle = MediaPlayer.create(context, R.raw.killbill);
        police = MediaPlayer.create(context, R.raw.pilice);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.lyout_suraksha:
                SurakshaCavachFragment fragment = SurakshaCavachFragment.newInstance("", "");
                moveFragment(fragment);
                break;
            case R.id.lyout_flash:
                runFlashLight();
                break;
            case R.id.layout_contacts:
                GetContactsFragment fragmentContacts = GetContactsFragment.newInstance("", "");
                moveFragment(fragmentContacts);
                break;
            case R.id.layout_pilicesire:
                piliceSiren();
                break;
            case R.id.layout_whistle:
                whisilePlay();
                break;
        }
    }

    public void piliceSiren() {

        if (!whistle.isPlaying()) {
            if (police.isPlaying()) {
                police.stop();
            } else {
                police.start();
                police.isPlaying();
            }
        } else {
            whistle.stop();
            piliceSiren();
        }

    }

    public void whisilePlay() {
        if (!police.isPlaying()) {
            if (whistle.isPlaying()) {
                whistle.stop();
            } else {
                whistle.start();
                whistle.isPlaying();
            }
        } else {
            police.stop();
            whisilePlay();
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
            Toast.makeText(context, "Off", Toast.LENGTH_SHORT).show();
        } else {
            turnOnFlash();
            Toast.makeText(context, "On", Toast.LENGTH_SHORT).show();
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
