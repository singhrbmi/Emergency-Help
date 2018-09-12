package hacker.l.emergency_help.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.Time;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.fido.u2f.api.common.RequestParams;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import hacker.l.emergency_help.R;
import hacker.l.emergency_help.activity.DashBoardActivity;
import hacker.l.emergency_help.adapter.AdviseAdapter;
import hacker.l.emergency_help.models.MyPojo;
import hacker.l.emergency_help.models.Result;
import hacker.l.emergency_help.utility.Contants;
import hacker.l.emergency_help.utility.FilePath;
import hacker.l.emergency_help.utility.Utility;
import hacker.l.emergency_help.utility.myUploadImage;

import static android.app.Activity.RESULT_OK;


public class AdminAdviseMgmtFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String district;

    // TODO: Rename and change types and number of parameters
    public static AdminAdviseMgmtFragment newInstance(String param1, String param2) {
        AdminAdviseMgmtFragment fragment = new AdminAdviseMgmtFragment();
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
            district = getArguments().getString(ARG_PARAM2);
        }
    }

    Context context;
    View view;
    EditText edt_message;
    TextView tv_submit;
    RecyclerView recycleView;
    ProgressDialog pd;
    List<Result> resultList;
    boolean aBoolean = false;
    ImageView imageView, image_camera;
    int id;
    CardView cardView;
    String selectedPath, message;


    Bitmap bitmap;

    boolean check = true;


    ProgressDialog progressDialog;


    String ADVISE = "adviseOwner";

    String ImagePath = "image";

    String ServerUploadPath = Contants.SERVICE_BASE_URL + Contants.uploadOwnerAdvise;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = getActivity();
        view = inflater.inflate(R.layout.fragment_admin_advise_mgmt, container, false);
        init();
        return view;
    }

    private void init() {

        DashBoardActivity dashBoardActivity = (DashBoardActivity) context;
        dashBoardActivity.setTitle("Advise Management");
        resultList = new ArrayList<>();

        cardView = view.findViewById(R.id.card);
        edt_message = view.findViewById(R.id.edt_message);
        tv_submit = view.findViewById(R.id.tv_submit);
        recycleView = view.findViewById(R.id.recycleView);
        imageView = view.findViewById(R.id.imageView);
        image_camera = view.findViewById(R.id.image_camera);
        if (!mParam1.equals("9431174521")) {
            image_camera.setVisibility(View.GONE);
            cardView.setVisibility(View.GONE);
        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        recycleView.setLayoutManager(linearLayoutManager);
//        setAdapter();
        tv_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                message = edt_message.getText().toString();
                if (mParam1.equalsIgnoreCase("9431174521")) {
                    if (Utility.isOnline(context)) {
                        if (bitmap != null) {
                            ImageUploadToServerFunction();
                        } else {
                            if (message.length() != 0) {
                                addOwnerAdviseData();
                            } else {
                                edt_message.setError("Enter Something");
                            }
                        }
                    } else {
                        Toast.makeText(context, "You are Offline. Please check your Internet Connection.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    addAdviseData();
                }


            }
        });
        image_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImageChooser();
            }
        });
    }


    @Override
    public void onActivityResult(int RC, int RQC, Intent I) {

        super.onActivityResult(RC, RQC, I);

        if (RC == 1 && RQC == RESULT_OK && I != null && I.getData() != null) {

            Uri uri = I.getData();

            try {

                bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), uri);

                imageView.setImageBitmap(bitmap);

            } catch (IOException e) {

                e.printStackTrace();
            }
        }
    }

    public void ImageUploadToServerFunction() {

        ByteArrayOutputStream byteArrayOutputStreamObject;

        byteArrayOutputStreamObject = new ByteArrayOutputStream();

        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStreamObject);

        byte[] byteArrayVar = byteArrayOutputStreamObject.toByteArray();

        final String ConvertImage = Base64.encodeToString(byteArrayVar, Base64.DEFAULT);

        class AsyncTaskUploadClass extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {

                super.onPreExecute();

                progressDialog = ProgressDialog.show(context, "Advise is Uploading", "Please Wait", false, false);
            }

            @Override
            protected void onPostExecute(String string1) {

                super.onPostExecute(string1);

                // Dismiss the progress dialog after done uploading.
                progressDialog.dismiss();
                edt_message.setText("");
                bitmap = null;
                // Printing uploading success message coming from server on android app.
                Toast.makeText(context, string1, Toast.LENGTH_LONG).show();

                // Setting image as transparent after done uploading.
//                imageView.setImageResource(android.R.color.transparent);
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_file_upload_black_24dp));

            }

            @Override
            protected String doInBackground(Void... params) {

                ImageProcessClass imageProcessClass = new ImageProcessClass();

                HashMap<String, String> HashMapParams = new HashMap<String, String>();
                HashMapParams.put(ADVISE, message);

                HashMapParams.put(ImagePath, ConvertImage);
                //HashMapParams.put(DATE, currentDateandTime);

                String FinalData = imageProcessClass.ImageHttpRequest(ServerUploadPath, HashMapParams);

                return FinalData;
            }
        }
        AsyncTaskUploadClass AsyncTaskUploadClassOBJ = new AsyncTaskUploadClass();

        AsyncTaskUploadClassOBJ.execute();
    }

    public class ImageProcessClass {

        public String ImageHttpRequest(String requestURL, HashMap<String, String> PData) {

            StringBuilder stringBuilder = new StringBuilder();

            try {

                URL url;
                HttpURLConnection httpURLConnectionObject;
                OutputStream OutPutStream;
                BufferedWriter bufferedWriterObject;
                BufferedReader bufferedReaderObject;
                int RC;

                url = new URL(requestURL);

                httpURLConnectionObject = (HttpURLConnection) url.openConnection();

                httpURLConnectionObject.setReadTimeout(19000);

                httpURLConnectionObject.setConnectTimeout(19000);

                httpURLConnectionObject.setRequestMethod("POST");

                httpURLConnectionObject.setDoInput(true);

                httpURLConnectionObject.setDoOutput(true);

                OutPutStream = httpURLConnectionObject.getOutputStream();

                bufferedWriterObject = new BufferedWriter(

                        new OutputStreamWriter(OutPutStream, "UTF-8"));

                bufferedWriterObject.write(bufferedWriterDataFN(PData));

                bufferedWriterObject.flush();

                bufferedWriterObject.close();

                OutPutStream.close();

                RC = httpURLConnectionObject.getResponseCode();

                if (RC == HttpsURLConnection.HTTP_OK) {

                    bufferedReaderObject = new BufferedReader(new InputStreamReader(httpURLConnectionObject.getInputStream()));

                    stringBuilder = new StringBuilder();

                    String RC2;

                    while ((RC2 = bufferedReaderObject.readLine()) != null) {

                        stringBuilder.append(RC2);
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return stringBuilder.toString();
        }

        private String bufferedWriterDataFN(HashMap<String, String> HashMapParams) throws UnsupportedEncodingException {

            StringBuilder stringBuilderObject;

            stringBuilderObject = new StringBuilder();

            for (Map.Entry<String, String> KEY : HashMapParams.entrySet()) {

                if (check)

                    check = false;
                else
                    stringBuilderObject.append("&");

                stringBuilderObject.append(URLEncoder.encode(KEY.getKey(), "UTF-8"));

                stringBuilderObject.append("=");

                stringBuilderObject.append(URLEncoder.encode(KEY.getValue(), "UTF-8"));
            }

            return stringBuilderObject.toString();
        }

    }

    private void showImageChooser() {
        Intent intent = new Intent();
        //sets the select file to all types of files
        intent.setType("image/*");
        //allows to select data and return it
        intent.setAction(Intent.ACTION_GET_CONTENT);
        //starts new activity to select file and return data
        startActivityForResult(Intent.createChooser(intent, "Choose File to Upload.."), 1);
    }

//    @RequiresApi(api = Build.VERSION_CODES.M)
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == Activity.RESULT_OK) {
//            if (requestCode == 1) {
//                Uri selectedFileUri = data.getData();
//                selectedPath = FilePath.getPath(context, selectedFileUri);
//                //chat_list_view.setVisibility(View.GONE);
//                Picasso.with(context).load(selectedFileUri).into(imageView);
//            }
//        }
//    }

    private void addOwnerAdviseData() {
        pd = new ProgressDialog(context);
        pd.setMessage("Submit Your Advise Please wait...");
        pd.show();
        pd.setCancelable(false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Contants.SERVICE_BASE_URL + Contants.uploadOwnerTextAdvise,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        pd.dismiss();
                        Toast.makeText(context, "Send Successfully", Toast.LENGTH_SHORT).show();
                        edt_message.setText("");
                        bitmap = null;
                        imageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_file_upload_black_24dp));
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pd.dismiss();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("adviseOwner", message);
//                params.put("image", selectedPath);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }

    private void addAdviseData() {
        final String message = edt_message.getText().toString();
        if (message.length() != 0) {
            if (Utility.isOnline(context)) {
                pd = new ProgressDialog(context);
                pd.setMessage("Submit Your Advise Please wait...");
                pd.show();
                pd.setCancelable(false);
                StringRequest stringRequest = new StringRequest(Request.Method.POST, Contants.SERVICE_BASE_URL + Contants.addAdvise,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                pd.dismiss();
                                Toast.makeText(context, "Send Successfully", Toast.LENGTH_SHORT).show();
                                edt_message.setText("");
//                                imageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_file_upload_black_24dp));
//                                setAdapter();
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                pd.dismiss();
                            }
                        }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("advise", message);
                        params.put("district", district);
                        return params;
                    }
                };
                RequestQueue requestQueue = Volley.newRequestQueue(context);
                requestQueue.add(stringRequest);
            } else {
                Toast.makeText(context, "You are Offline. Please check your Internet Connection.", Toast.LENGTH_SHORT).show();
            }
        } else {
            edt_message.setError("Enter Advise Please");
        }
    }

    public void setAdapter() {
        pd = new ProgressDialog(context);
        pd.setMessage("getting wait...");
        pd.show();
        pd.setCancelable(false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Contants.SERVICE_BASE_URL + Contants.getAdvise,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        pd.dismiss();
                        MyPojo myPojo = new Gson().fromJson(response, MyPojo.class);
                        resultList.clear();
                        resultList.addAll(Arrays.asList(myPojo.getResult()));
                        Collections.reverse(resultList);
                        AdviseAdapter adapter = new AdviseAdapter(context, resultList, AdminAdviseMgmtFragment.this);
                        recycleView.setAdapter(adapter);
                        edt_message.setText("");
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pd.dismiss();
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
