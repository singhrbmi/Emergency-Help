package hacker.l.emergency_help.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hacker.l.emergency_help.R;
import hacker.l.emergency_help.adapter.AllLoginAdapter;
import hacker.l.emergency_help.adapter.CategoryAdapter;
import hacker.l.emergency_help.models.MyPojo;
import hacker.l.emergency_help.models.Result;
import hacker.l.emergency_help.utility.Contants;
import hacker.l.emergency_help.utility.Utility;

public class AdminAllLoginDataFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    // TODO: Rename and change types and number of parameters
    public static AdminAllLoginDataFragment newInstance(String param1, String param2) {
        AdminAllLoginDataFragment fragment = new AdminAllLoginDataFragment();
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
    RecyclerView recyclerView;
    ProgressDialog pd;
    List<Result> resultList;
    TextView tv_save, tv_share;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        context = getActivity();
        view = inflater.inflate(R.layout.fragment_admin_all_login_data, container, false);
        init();
        return view;
    }

    private void init() {
        resultList = new ArrayList<>();
        recyclerView = view.findViewById(R.id.recycleView);
        tv_save = view.findViewById(R.id.tv_save);
        tv_share = view.findViewById(R.id.tv_share);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, true);
        recyclerView.setLayoutManager(linearLayoutManager);
        if (Utility.isOnline(context)) {
            pd = new ProgressDialog(context);
            pd.setMessage("Getting  wait...");
            pd.show();
            pd.setCancelable(false);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Contants.SERVICE_BASE_URL + Contants.getAllLogin,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            pd.dismiss();
                            if (!response.equalsIgnoreCase("no")) {
                                MyPojo myPojo = new Gson().fromJson(response, MyPojo.class);
                                resultList.clear();
                                resultList.addAll(Arrays.asList(myPojo.getResult()));
                                if (resultList != null) {
                                    AllLoginAdapter adapter = new AllLoginAdapter(context, resultList);
                                    recyclerView.setAdapter(adapter);
                                }
                            }
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
        } else {
            Toast.makeText(context, "You are Offline. Please check your Internet Connection.", Toast.LENGTH_SHORT).show();
        }
        tv_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareData();
            }
        });
        tv_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
            }
        });
    }

    private void saveData() {
        if (resultList != null && resultList.size() != 0) {
            DecimalFormat decimalFormat = new DecimalFormat("#0.0");
            try {
                File myFile = new File("/sdcard/allLoginUsers.xlsx");
                myFile.createNewFile();
                FileOutputStream fOut = new FileOutputStream(myFile);
                OutputStreamWriter myOutWriter =
                        new OutputStreamWriter(fOut);
                Result[] results = resultList.toArray(new Result[resultList.size() + 1]);
                for (int i = 0; i < resultList.size(); i++) {
                    myOutWriter.append(String.valueOf(decimalFormat.format(results[i].getLoginId())) + "\n");
                    myOutWriter.append(results[i].getUsername() + "\n");
                    myOutWriter.append(results[i].getUserPhone() + "\n");
                    myOutWriter.append(results[i].getEmailId() + "\n");
                }

                myOutWriter.close();
                fOut.close();
                Toast.makeText(context,
                        "Saving..  'allLoginUsers.xlsx'",
                        Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Toast.makeText(context, e.getMessage(),
                        Toast.LENGTH_SHORT).show();

            }
        }
    }

    private void shareData() {
        if (resultList != null && resultList.size() != 0) {
            try {
                File myFile = new File("/sdcard/allLoginUsers.xlsx");
                myFile.createNewFile();
                FileOutputStream fOut = new FileOutputStream(myFile);
                OutputStreamWriter myOutWriter =
                        new OutputStreamWriter(fOut);
                Result[] results = resultList.toArray(new Result[resultList.size() + 1]);
                for (int i = 0; i < resultList.size(); i++) {
                    myOutWriter.append(String.valueOf(results[i].getLoginId()) + "\n");
                    myOutWriter.append(results[i].getUsername() + "\n");
                    myOutWriter.append(results[i].getUserPhone() + "\n");
                    myOutWriter.append(results[i].getEmailId() + "\n");
                }

                myOutWriter.close();
                fOut.close();
                Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
                emailIntent.setType("image/jpeg");
                emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]
                        {"rambangar@hotmail.com"});
                emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
                        "All Users Login Details");
                emailIntent.putExtra(android.content.Intent.EXTRA_TEXT,
                        "Thank You");
                emailIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(myFile));
                startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            } catch (Exception e) {
                Toast.makeText(context, e.getMessage(),
                        Toast.LENGTH_SHORT).show();

            }

        }
    }
}

