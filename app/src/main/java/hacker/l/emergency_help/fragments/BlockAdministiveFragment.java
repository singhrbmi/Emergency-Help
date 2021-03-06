package hacker.l.emergency_help.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hacker.l.emergency_help.R;
import hacker.l.emergency_help.activity.DashBoardActivity;
import hacker.l.emergency_help.adapter.AdminAdapter;
import hacker.l.emergency_help.adapter.AdminPermsstionAdapter;
import hacker.l.emergency_help.models.MyPojo;
import hacker.l.emergency_help.models.Result;
import hacker.l.emergency_help.utility.Contants;
import hacker.l.emergency_help.utility.Utility;


public class BlockAdministiveFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private boolean mParam1;
    private String mParam2;


    public BlockAdministiveFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static BlockAdministiveFragment newInstance(boolean param1, String param2) {
        BlockAdministiveFragment fragment = new BlockAdministiveFragment();
        Bundle args = new Bundle();
        args.putBoolean(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getBoolean(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    Context context;
    View view;
    List<Result> resultList;
    RecyclerView recycleView;
    TextView tv_size;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        context = getActivity();
        view = inflater.inflate(R.layout.fragment_block_administive, container, false);
        init();
        return view;
    }

    private void init() {
        DashBoardActivity dashBoardActivity = (DashBoardActivity) context;
        resultList = new ArrayList<>();
        recycleView = view.findViewById(R.id.recycleView);
        tv_size = view.findViewById(R.id.tv_size);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        recycleView.setLayoutManager(linearLayoutManager);
        if (mParam1) {
            setBlockAdapter();
            dashBoardActivity.setTitle("Block Administrative");
        } else {
            setUnBlockAdapter();
            dashBoardActivity.setTitle("UnBlock Administrative");

        }
    }

    public void setBlockAdapter() {
        if (Utility.isOnline(context)) {
            final ProgressDialog progressDialog = new ProgressDialog(context);
            progressDialog.setMessage("Getting Wait");
            progressDialog.show();
            progressDialog.setCancelable(false);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Contants.SERVICE_BASE_URL + Contants.getAllBlockAdmin,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if (!response.equalsIgnoreCase("\nno")) {
                                resultList.clear();
                                MyPojo myPojo = new Gson().fromJson(response, MyPojo.class);
                                if (myPojo != null) {
                                    for (Result result : myPojo.getResult()) {
                                        if (result != null) {
                                            resultList.addAll(Arrays.asList(result));
                                        }
                                    }
                                    if (resultList.size() != 0) {
                                        tv_size.setText("Total:-" + (resultList.size()));
                                        Collections.reverse(resultList);
                                        AdminPermsstionAdapter adminAdapter = new AdminPermsstionAdapter(context, resultList, true, BlockAdministiveFragment.this);
                                        recycleView.setAdapter(adminAdapter);

                                    }
                                }
                            } else {
                                Toast.makeText(context, "No Data Found", Toast.LENGTH_SHORT).show();
                            }

                            progressDialog.dismiss();
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            progressDialog.dismiss();
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
        } else

        {
            Toast.makeText(context, "You are Offline. Please check your Internet Connection.", Toast.LENGTH_SHORT).show();
        }
    }

    public void setUnBlockAdapter() {
        if (Utility.isOnline(context)) {
            final ProgressDialog progressDialog = new ProgressDialog(context);
            progressDialog.setMessage("Getting Wait");
            progressDialog.show();
            progressDialog.setCancelable(false);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Contants.SERVICE_BASE_URL + Contants.getAllUnBlockAdmin,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if (!response.equalsIgnoreCase("\nno")) {
                                resultList.clear();
                                MyPojo myPojo = new Gson().fromJson(response, MyPojo.class);
                                if (myPojo != null) {
                                    for (Result result : myPojo.getResult()) {
                                        if (result != null) {
                                            resultList.addAll(Arrays.asList(result));
                                        }
                                    }
                                    if (resultList.size() != 0) {
                                        tv_size.setText("Total:-" + (resultList.size()-1));
                                        Collections.reverse(resultList);
                                        AdminPermsstionAdapter adminAdapter = new AdminPermsstionAdapter(context, resultList, false, BlockAdministiveFragment.this);
                                        recycleView.setAdapter(adminAdapter);

                                    }
                                }
                            } else {
                                Toast.makeText(context, "No Data Found", Toast.LENGTH_SHORT).show();
                            }

                            progressDialog.dismiss();
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            progressDialog.dismiss();
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
        } else

        {
            Toast.makeText(context, "You are Offline. Please check your Internet Connection.", Toast.LENGTH_SHORT).show();
        }
    }
}
