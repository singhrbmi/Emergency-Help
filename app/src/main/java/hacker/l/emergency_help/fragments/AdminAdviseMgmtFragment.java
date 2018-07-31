package hacker.l.emergency_help.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hacker.l.emergency_help.R;
import hacker.l.emergency_help.adapter.AdviseAdapter;
import hacker.l.emergency_help.models.MyPojo;
import hacker.l.emergency_help.models.Result;
import hacker.l.emergency_help.utility.Contants;
import hacker.l.emergency_help.utility.FilePath;
import hacker.l.emergency_help.utility.Utility;
import hacker.l.emergency_help.utility.myUploadImage;


public class AdminAdviseMgmtFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

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
            mParam2 = getArguments().getString(ARG_PARAM2);
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
    String selectedPath, imageUrl;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = getActivity();
        view = inflater.inflate(R.layout.fragment_admin_advise_mgmt, container, false);
        init();
        return view;
    }

    private void init() {
        resultList = new ArrayList<>();
        edt_message = view.findViewById(R.id.edt_message);
        tv_submit = view.findViewById(R.id.tv_submit);
        recycleView = view.findViewById(R.id.recycleView);
        imageView = view.findViewById(R.id.imageView);
        image_camera = view.findViewById(R.id.image_camera);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        recycleView.setLayoutManager(linearLayoutManager);
        setAdapter();
        tv_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addAdviseData();
//                if (aBoolean) {
//                    updateAdvise();
//                } else {
//                    uploadData();
//                }
            }
        });
        image_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImageChooser();
            }
        });
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

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 1) {
                Uri selectedFileUri = data.getData();
                selectedPath = FilePath.getPath(context, selectedFileUri);
                //chat_list_view.setVisibility(View.GONE);
                Picasso.with(context).load(selectedFileUri).into(imageView);
            }
        }
    }

    private void updateAdvise() {
        final String message = edt_message.getText().toString();
        if (message.length() != 0) {
            if (Utility.isOnline(context)) {
                pd = new ProgressDialog(context);
                pd.setMessage("Updating wait...");
                pd.show();
                pd.setCancelable(false);
                StringRequest stringRequest = new StringRequest(Request.Method.POST, Contants.SERVICE_BASE_URL + Contants.updateAdvise,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                pd.dismiss();
                                Toast.makeText(context, "Update Successfully", Toast.LENGTH_SHORT).show();
//                                setAdapter();
                                tv_submit.setText("Submit");
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
                        params.put("adviseId", String.valueOf(id));
                        params.put("advise", message);
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

    private void uploadData() {
        class UploadData extends AsyncTask<Void, Integer, String> {
            ProgressDialog uploading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                uploading = new ProgressDialog(context);
                uploading.setMessage("Please Wait..");
                uploading.setCancelable(false);
                uploading.setProgress(0);
                try {
                    if (uploading.isShowing()) {
                        uploading.dismiss();
                    } else {
                        uploading.show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected void onProgressUpdate(Integer... progress) {
                uploading.setProgress(progress[0]);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                uploading.dismiss();
                imageUrl = s;
                addAdviseData();
            }

            @Override
            protected String doInBackground(Void... params) {
                String msg = null;
                myUploadImage myUploadImage = new myUploadImage();
                if (selectedPath != null) {
                    msg = myUploadImage.uploadImageData(selectedPath);
                } else {
                    Toast.makeText(context, "Select Image Please", Toast.LENGTH_SHORT).show();
                }
                return msg;
            }
        }


        UploadData uv = new UploadData();
        uv.execute();
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
                                setAdapter();
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
//                        params.put("image", imageUrl);
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

    public void updateShowData(boolean b, String name, int id) {
        this.aBoolean = b;
        this.id = id;
        edt_message.setText(name);
        tv_submit.setText("Update");
        edt_message.setSelection(edt_message.length());
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
