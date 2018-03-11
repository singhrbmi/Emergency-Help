package hacker.l.emergency_help.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.Result;

import org.json.JSONException;
import org.json.JSONObject;

import hacker.l.emergency_help.R;
import hacker.l.emergency_help.activity.AfterSacnActivity;
import hacker.l.emergency_help.activity.DashBoardActivity;
import hacker.l.emergency_help.activity.LoginActivity;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;
import static android.content.ContentValues.TAG;

public class QRScannerFragment extends Fragment implements ZXingScannerView.ResultHandler {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public static QRScannerFragment newInstance(String param1, String param2) {
        QRScannerFragment fragment = new QRScannerFragment();
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
    TextView tv_data;
    private ZXingScannerView mScannerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        context = getActivity();
        view = inflater.inflate(R.layout.fragment_qrscanner, container, false);
        init();
        return view;
    }

    private void init() {
        tv_data = view.findViewById(R.id.tv_data);
        mScannerView = new ZXingScannerView(context);   // Programmatically initialize the scanner view
        getActivity().setContentView(mScannerView);
    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
        mScannerView.startCamera();          // Start camera on resume
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();           // Stop camera on pause
    }

    @Override
    public void handleResult(Result rawResult) {
        // Do something with the result here
        Log.v(TAG, rawResult.getText()); // Prints scan results
        Log.v(TAG, rawResult.getBarcodeFormat().toString()); // Prints the scan format (qrcode, pdf417 etc.)
        // If you would like to resume scanning, call this method below:
        mScannerView.resumeCameraPreview(this);
        Intent intent = new Intent(context, AfterSacnActivity.class);
        intent.putExtra("data", rawResult.toString());
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        tv_data.setText(rawResult.toString());
    }
}