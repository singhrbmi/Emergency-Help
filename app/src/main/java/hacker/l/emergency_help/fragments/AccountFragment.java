package hacker.l.emergency_help.fragments;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
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
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hacker.l.emergency_help.R;
import hacker.l.emergency_help.activity.DashBoardActivity;
import hacker.l.emergency_help.database.DbHelper;
import hacker.l.emergency_help.models.MyPojo;
import hacker.l.emergency_help.models.Result;
import hacker.l.emergency_help.utility.Contants;

import static android.content.Context.WINDOW_SERVICE;

public class AccountFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public static AccountFragment newInstance(String param1, String param2) {
        AccountFragment fragment = new AccountFragment();
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
    TextView tv_name, tv_phone, tv_email, tv_address, tv_city, tv_pincode, tv_emergency, tv_emergency2, tv_emergency3, tv_barcode, share;
    LinearLayout layoutone, layouttwo, layoutthree, layoutAddress, layoutCity, layoutPincode;
    ImageView barCodeImage;
    public final static int QRcodeWidth = 500;
    Bitmap bitmap;
    String barcode;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        context = getActivity();
        view = inflater.inflate(R.layout.fragment_account, container, false);
        init();
        return view;
    }

    private void init() {
        barCodeImage = view.findViewById(R.id.image_barcode);
        tv_name = view.findViewById(R.id.tv_name);
        tv_email = view.findViewById(R.id.tv_email);
        tv_phone = view.findViewById(R.id.tv_phone);
        tv_emergency = view.findViewById(R.id.tv_emergency);
        layoutone = view.findViewById(R.id.layoutone);
        layouttwo = view.findViewById(R.id.layouttwo);
        tv_emergency2 = view.findViewById(R.id.tv_emergency2);
        layoutthree = view.findViewById(R.id.layoutthree);
        tv_emergency3 = view.findViewById(R.id.tv_emergency3);
        tv_address = view.findViewById(R.id.tv_address);
        tv_city = view.findViewById(R.id.tv_city);
        tv_pincode = view.findViewById(R.id.tv_pincode);
        tv_barcode = view.findViewById(R.id.tv_barcode);
        layoutAddress = view.findViewById(R.id.layoutAddress);
        layoutCity = view.findViewById(R.id.layoutCity);
        layoutPincode = view.findViewById(R.id.layoutPincode);
        share = view.findViewById(R.id.share);
//        final ProgressDialog progressDialog = new ProgressDialog(context);
//        progressDialog.setMessage("Waiting");
//        progressDialog.show();
//        progressDialog.setCancelable(false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Contants.SERVICE_BASE_URL + Contants.getsurakshacavach,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        MyPojo myPojo = new Gson().fromJson(response, MyPojo.class);
                        if (myPojo != null) {
                            for (Result result : myPojo.getResult()) {
                                if (result != null) {
                                    new DbHelper(context).upsertsurakshaData(result);
                                    setSurakshaCavachData();
                                }
                            }
                        }
//                        progressDialog.dismiss();
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
                final DbHelper dbHelper = new DbHelper(context);
                final Result userdata = dbHelper.getUserData();
                params.put("loginId", String.valueOf(userdata.getLoginId()));
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
        setSurakshaCavachData();
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if(isStoragePermissionGranted()){
                String bitmapPath = null;
                bitmapPath = MediaStore.Images.Media.insertImage(context.getContentResolver(), bitmap, "Share Code", null);
                Uri bitmapUri = Uri.parse(bitmapPath);
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_STREAM, bitmapUri);
                startActivity(Intent.createChooser(intent, "Share Image"));
//            }
            }
        });
    }

    public boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (context.checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            //resume tasks needing this permission
            String bitmapPath = null;
            bitmapPath = MediaStore.Images.Media.insertImage(context.getContentResolver(), bitmap, "Share Code", null);
            Uri bitmapUri = Uri.parse(bitmapPath);
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("image/*");
            intent.putExtra(Intent.EXTRA_STREAM, bitmapUri);
            startActivity(Intent.createChooser(intent, "Share Image"));
        }
    }

    private void setSurakshaCavachData() {
        final DbHelper dbHelper = new DbHelper(context);
        final Result userdata = dbHelper.getUserData();
        if (userdata != null) {
            tv_name.setText(userdata.getUsername());
            tv_email.setText(userdata.getEmailId());
            tv_phone.setText(userdata.getUserPhone());
        }
        //set cavach data...
        List<Result> data = dbHelper.getAllsurakshaData();
        if (data != null && data.size() != 0) {
            for (int i = 0; i < data.size(); i++) {
                int l = data.size() - 1;
                tv_name.setText(data.get(l).getUsername());
                tv_email.setText(data.get(l).getEmailId());
                tv_phone.setText(data.get(l).getUserPhone());
                tv_address.setText(data.get(l).getAddress());
                tv_city.setText(data.get(l).getCity());
                tv_pincode.setText(data.get(l).getPinCode());
                tv_emergency.setText(data.get(l).getEmergencyOne());
                tv_emergency2.setText(data.get(l).getEmergencyTwo());
                tv_emergency3.setText(data.get(l).getEmergencyThree());
                barcode = data.get(l).getBarCode();
                byte[] encodeByte = Base64.decode(barcode, Base64.DEFAULT);
                bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
                barCodeImage.setImageBitmap(bitmap);
            }
        } else {
            layoutAddress.setVisibility(View.GONE);
            layoutone.setVisibility(View.GONE);
            layouttwo.setVisibility(View.GONE);
            layoutthree.setVisibility(View.GONE);
            layoutCity.setVisibility(View.GONE);
            layoutPincode.setVisibility(View.GONE);
            tv_barcode.setVisibility(View.GONE);
            barCodeImage.setVisibility(View.GONE);
            share.setVisibility(View.GONE);
        }
    }
}

