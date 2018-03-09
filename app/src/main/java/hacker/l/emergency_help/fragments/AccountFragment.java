package hacker.l.emergency_help.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
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
import com.google.android.gms.drive.Contents;
import com.google.gson.Gson;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.util.HashMap;
import java.util.Map;

import hacker.l.emergency_help.R;
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
    TextView tv_name, tv_phone, tv_email, tv_address, tv_city, tv_pincode, tv_emergency, tv_emergency2, tv_emergency3, tv_barcode;
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
        setSurakshaCavachData();
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
        Result data = dbHelper.getsurakshaData();
        if (data != null) {
            tv_name.setText(data.getUsername());
            tv_email.setText(data.getEmailId());
            tv_phone.setText(data.getUserPhone());
            tv_address.setText(data.getAddress());
            tv_city.setText(data.getCity());
            tv_pincode.setText(data.getPinCode());
            tv_emergency.setText(data.getEmergencyOne());
            tv_emergency2.setText(data.getEmergencyTwo());
            tv_emergency3.setText(data.getEmergencyThree());
            barcode = data.getBarCode();
        } else {
            layoutAddress.setVisibility(View.GONE);
            layoutone.setVisibility(View.GONE);
            layouttwo.setVisibility(View.GONE);
            layoutthree.setVisibility(View.GONE);
            layoutCity.setVisibility(View.GONE);
            layoutPincode.setVisibility(View.GONE);
            tv_barcode.setVisibility(View.GONE);
            barCodeImage.setVisibility(View.GONE);
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ProgressDialog pd = new ProgressDialog(context);
                pd.show();
                getBarCode(pd);
            }
        }, 2000);
    }


    private void getBarCode(ProgressDialog pd) {
        try {
            bitmap = TextToImageEncode(barcode);
            barCodeImage.setImageBitmap(bitmap);
            pd.dismiss();
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

    Bitmap TextToImageEncode(String Value) throws WriterException {
        BitMatrix bitMatrix;
        try {
            bitMatrix = new MultiFormatWriter().encode(
                    Value,
                    BarcodeFormat.DATA_MATRIX.QR_CODE,
                    QRcodeWidth, QRcodeWidth, null
            );

        } catch (IllegalArgumentException Illegalargumentexception) {

            return null;
        }
        int bitMatrixWidth = bitMatrix.getWidth();

        int bitMatrixHeight = bitMatrix.getHeight();

        int[] pixels = new int[bitMatrixWidth * bitMatrixHeight];

        for (int y = 0; y < bitMatrixHeight; y++) {
            int offset = y * bitMatrixWidth;

            for (int x = 0; x < bitMatrixWidth; x++) {

                pixels[offset + x] = bitMatrix.get(x, y) ?
                        getResources().getColor(R.color.black) : getResources().getColor(R.color.grey_bg);
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(bitMatrixWidth, bitMatrixHeight, Bitmap.Config.ARGB_4444);

        bitmap.setPixels(pixels, 0, 500, 0, 0, bitMatrixWidth, bitMatrixHeight);
        return bitmap;
    }
}

