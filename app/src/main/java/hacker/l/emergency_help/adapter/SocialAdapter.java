package hacker.l.emergency_help.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hacker.l.emergency_help.R;
import hacker.l.emergency_help.fragments.SocialNoViewFragment;
import hacker.l.emergency_help.fragments.SubCategoryFragment;
import hacker.l.emergency_help.models.MyPojo;
import hacker.l.emergency_help.models.Result;
import hacker.l.emergency_help.utility.Contants;
import hacker.l.emergency_help.utility.Utility;

/**
 * Created by lalitsingh on 05/03/18.
 */

public class SocialAdapter extends RecyclerView.Adapter<SocialAdapter.MyViewHolder> {
    private Typeface materialdesignicons_font, ProximaNovaRegular, ProximaNovaLight;
    private Context mContext;
    private List<Result> dataList, FilteruserList;
    ProgressDialog pd;

    public SocialAdapter(Context mContext, List<Result> dataList) {
        this.mContext = mContext;
        this.dataList = dataList;
        this.FilteruserList = dataList;
//        this.ProximaNovaRegular = FontManager.getFontTypeface(mContext, "fonts/ProximaNova-Regular.otf");
//        this.ProximaNovaLight = FontManager.getFontTypeface(mContext, "fonts/ProximaNova-Light.otf");
//        this.materialdesignicons_font = FontManager.getFontTypefaceMaterialDesignIcons(mContext, "fonts/materialdesignicons-webfont.otf");
    }

    @Override
    public SocialAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_social, parent, false);
        return new SocialAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(SocialAdapter.MyViewHolder holder, final int position) {
        final String name = FilteruserList.get(position).getSocialName();
        final String dist = FilteruserList.get(position).getDistrict();
        holder.tv_socialName.setText(name);
        holder.tv_socialName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkSubCategory(name, dist);
            }
        });
    }

    private void checkSubCategory(final String name, final String dist) {
        if (Utility.isOnline(mContext)) {
            pd = new ProgressDialog(mContext);
            pd.setMessage("Fetching Data wait...");
            pd.show();
            pd.setCancelable(false);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Contants.SERVICE_BASE_URL + Contants.getAllSubCategory,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if (response.equalsIgnoreCase("no")) {
                                SocialNoViewFragment fragment = SocialNoViewFragment.newInstance(name, dist, null, false);
                                moveFragment(fragment);
                            } else {
                                SubCategoryFragment fragment = SubCategoryFragment.newInstance(response, "");
                                moveFragment(fragment);
                            }
                            try {
                                if (pd.isShowing()) {
                                    pd.dismiss();
                                }
                            }catch (Exception e){
                                e.printStackTrace();
                            }
//                            MyPojo myPojo = new Gson().fromJson(response, MyPojo.class);
//                            if (myPojo != null) {
//                                for (Result result:myPojo.getResult()){
//                                    String suCat=result.getSubCategory();
//                                }
//                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            try {
                                if (pd.isShowing()) {
                                    pd.dismiss();
                                }
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("district", dist);
                    params.put("socialName", name);
                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(mContext);
            requestQueue.add(stringRequest);

        } else {
            Toast.makeText(mContext, "You are Offline. Please check your Internet Connection.", Toast.LENGTH_SHORT).show();
        }
    }

    private void moveFragment(Fragment fragment) {
        FragmentManager fragmentManager = ((FragmentActivity) mContext).getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public int getItemCount() {
        return FilteruserList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_socialName;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv_socialName = (TextView) itemView.findViewById(R.id.tv_socialName);
        }

    }

}
