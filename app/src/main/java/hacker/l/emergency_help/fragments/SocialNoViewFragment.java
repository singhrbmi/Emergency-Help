package hacker.l.emergency_help.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
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
import hacker.l.emergency_help.adapter.AddContactsAdapter;
import hacker.l.emergency_help.adapter.SocialContactsAdapter;
import hacker.l.emergency_help.models.MyPojo;
import hacker.l.emergency_help.models.Result;
import hacker.l.emergency_help.utility.Contants;
import hacker.l.emergency_help.utility.Utility;

public class SocialNoViewFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String TYPE = "type";

    // TODO: Rename and change types of parameters
    private String socialname;
    private String district, subCategory;
    private boolean flag = false;

    public static SocialNoViewFragment newInstance(String socialname, String district, String subCategory, boolean flag) {
        SocialNoViewFragment fragment = new SocialNoViewFragment();
        Bundle args = new Bundle();
        args.putString(TYPE, socialname);
        args.putString("district", district);
        args.putString("subCategory", subCategory);
        args.putBoolean("flag", flag);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            socialname = getArguments().getString(TYPE);
            district = getArguments().getString("district");
            subCategory = getArguments().getString("subCategory");
            flag = getArguments().getBoolean("flag");
        }
    }

    RecyclerView recycleView;
    View view;
    Context context;
    LinearLayoutManager linearLayoutManager;
    Result result;
    TextView tv_type, tv_District, subcategory;
    SearchView search_barUser;
    SocialContactsAdapter socialContactsAdapter;
    ProgressDialog pd;
    List<Result> resultList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        context = getActivity();
        view = inflater.inflate(R.layout.fragment_social_no_view, container, false);
        init();
        return view;
    }

    private void init() {
        DashBoardActivity dashBoardActivity = (DashBoardActivity) context;
        dashBoardActivity.setTitle("Contacts");
        resultList = new ArrayList<>();
        recycleView = view.findViewById(R.id.recycleView);
        tv_type = view.findViewById(R.id.type);
        tv_District = view.findViewById(R.id.tv_District);
        subcategory = view.findViewById(R.id.subcategory);
        tv_type.setText(socialname);
        if (subCategory != null) {
            subcategory.setText(subCategory);
        }
        if (district != null) {
            tv_District.setText(district);
//        } else {
//            tv_District.setText("Ranchi");
        }
        linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        recycleView.setLayoutManager(linearLayoutManager);
        if (flag) {
            getSubCategoryData();
        } else {
            getCategoryData();
        }
        search_barUser = (SearchView) view.findViewById(R.id.search_barUser);
//        search_barUser.setIconified(true);
        search_barUser.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // filter recycler view when query submitted
                if (query != null && socialContactsAdapter != null) {
                    socialContactsAdapter.getFilter().filter(query);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                // filter recycler view when text is changed
                if (query != null && socialContactsAdapter != null) {
                    socialContactsAdapter.getFilter().filter(query);
                }
                return false;
            }
        });
    }

    private void getSubCategoryData() {
        if (Utility.isOnline(context)) {
            pd = new ProgressDialog(context);
            pd.setMessage("Fetching Data wait...");
            pd.show();
            pd.setCancelable(false);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Contants.SERVICE_BASE_URL + Contants.getAllSubCategoryPhone,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                if (pd.isShowing()) {
                                    pd.dismiss();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            MyPojo myPojo = new Gson().fromJson(response, MyPojo.class);
                            if (myPojo != null) {
                                resultList.addAll(Arrays.asList(myPojo.getResult()));
                                if (resultList != null) {
//                                    Collections.reverse(resultList);
                                    socialContactsAdapter = new SocialContactsAdapter(context, resultList);
                                    recycleView.setAdapter(socialContactsAdapter);
                                } else {
                                    Toast.makeText(context, "No Data Found", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(context, "No Data Found", Toast.LENGTH_SHORT).show();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            try {
                                if (pd.isShowing()) {
                                    pd.dismiss();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("district", district);
                    params.put("subCategory", subCategory);
                    params.put("category", socialname);
                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(context);
            requestQueue.add(stringRequest);
        } else {
            Toast.makeText(context, "You are Offline. Please check your Internet Connection.", Toast.LENGTH_SHORT).show();
        }
    }

    private void getCategoryData() {
        if (Utility.isOnline(context)) {
            pd = new ProgressDialog(context);
            pd.setMessage("Fetching Data wait...");
            pd.show();
            pd.setCancelable(false);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Contants.SERVICE_BASE_URL + Contants.getAllPhone,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                if (pd.isShowing()) {
                                    pd.dismiss();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            MyPojo myPojo = new Gson().fromJson(response, MyPojo.class);
                            if (myPojo != null) {
                                resultList.addAll(Arrays.asList(myPojo.getResult()));
                                if (resultList != null) {
//                                    Collections.reverse(resultList);
                                    socialContactsAdapter = new SocialContactsAdapter(context, resultList);
                                    recycleView.setAdapter(socialContactsAdapter);
                                } else {
                                    Toast.makeText(context, "No Data Found", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(context, "No Data Found", Toast.LENGTH_SHORT).show();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            try {
                                if (pd.isShowing()) {
                                    pd.dismiss();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("district", district);
                    params.put("category", socialname);
                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(context);
            requestQueue.add(stringRequest);
        } else {
            Toast.makeText(context, "You are Offline. Please check your Internet Connection.", Toast.LENGTH_SHORT).show();
        }
    }
}
