package hacker.l.emergency_help.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hacker.l.emergency_help.R;
import hacker.l.emergency_help.fragments.BlockAdministiveFragment;
import hacker.l.emergency_help.models.Result;
import hacker.l.emergency_help.utility.Contants;
import hacker.l.emergency_help.utility.Utility;

/**
 * Created by lalitsingh on 23/03/18.
 */

public class AdminPermsstionAdapter extends RecyclerView.Adapter<AdminPermsstionAdapter.MyViewHolder> {
    private Typeface materialdesignicons_font, ProximaNovaRegular;
    private Context mContext;
    private List<Result> userList, FilteruserList;
        BlockAdministiveFragment blockAdministiveFragment;
//    UnBlockAdministiveFragment unBlockAdministiveFragment;
    ProgressDialog pd;
    boolean b = false;

    public AdminPermsstionAdapter(Context mContext, List<Result> userList, boolean b,BlockAdministiveFragment blockAdministiveFragment) {
        this.mContext = mContext;
        this.userList = userList;
        this.FilteruserList = userList;
        this.b = b;
        this.blockAdministiveFragment = blockAdministiveFragment;
//        this.materialdesignicons_font = FontManager.getFontTypefaceMaterialDesignIcons(mContext, "fonts/materialdesignicons-webfont.otf");
    }

    @Override
    public AdminPermsstionAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_admin_permission, parent, false);

        return new AdminPermsstionAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AdminPermsstionAdapter.MyViewHolder holder, final int position) {
//        if (position % 2 == 1) {
//            holder.linearLayout.setBackgroundColor(Color.parseColor("#ffffff"));
//        } else {
//            holder.linearLayout.setBackgroundColor(Color.parseColor("#ffffff"));
//        }
        String name = FilteruserList.get(position).getAdminName();
        if (name != null && !name.equalsIgnoreCase("")) {
            holder.tv_name.setText("Name:- " + name);
            holder.tv_phone.setText("Phone:- " + FilteruserList.get(position).getAdminPhone());
            holder.tv_pass.setText("Password:- " + FilteruserList.get(position).getAdminPass());
            holder.tv_District.setText("District:- " + FilteruserList.get(position).getDistrict());
            String stus = FilteruserList.get(position).getIsActive();
            holder.tv_status.setText("Status:- " + stus);
            if (stus.equalsIgnoreCase("Block")) {
                holder.tv_status.setTextColor(Color.RED);
            }
        } else {
            holder.cardView.setVisibility(View.GONE);
        }
        //block true....
        if (b) {
            holder.id_switch.setChecked(false);
        } else {
            holder.id_switch.setChecked(true);
        }
//        holder.id_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (!isChecked) {
//                    blockAdmin(position);
//                } else {
//                    unBlockAdmin(position);
//                }
//            }
//        });
        holder.id_switch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (b) {
                    unBlockAdmin(position);
                } else {
                    blockAdmin(position);
                }
            }
        });
    }

    private void unBlockAdmin(final int position) {
        if (Utility.isOnline(mContext)) {
            pd = new ProgressDialog(mContext);
            pd.setMessage("UnBlocking wait......");
            pd.show();
            pd.setCancelable(false);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Contants.SERVICE_BASE_URL + Contants.UnBlockAdmin,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            pd.dismiss();
                            FilteruserList.remove(position);
                            notifyDataSetChanged();
                            blockAdministiveFragment.setBlockAdapter();
                            Toast.makeText(mContext, "UnBlock Successully", Toast.LENGTH_LONG).show();
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
                    params.put("adminId", String.valueOf(FilteruserList.get(position).getAdminId()));
                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(mContext);
            requestQueue.add(stringRequest);
//            }
        } else {
            Toast.makeText(mContext, "Enable Internet Connection.", Toast.LENGTH_SHORT).show();
        }
    }

    private void blockAdmin(final int position) {
        if (Utility.isOnline(mContext)) {
            pd = new ProgressDialog(mContext);
            pd.setMessage("Blocking wait......");
            pd.show();
            pd.setCancelable(false);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Contants.SERVICE_BASE_URL + Contants.BlockAdmin,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            pd.dismiss();
                            FilteruserList.remove(position);
                            notifyDataSetChanged();
                            blockAdministiveFragment.setUnBlockAdapter();
                            Toast.makeText(mContext, "Block Successully", Toast.LENGTH_LONG).show();
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
                    params.put("adminId", String.valueOf(FilteruserList.get(position).getAdminId()));
                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(mContext);
            requestQueue.add(stringRequest);
//            }
        } else {
            Toast.makeText(mContext, "Enable Internet Connection.", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public int getItemCount() {
        return FilteruserList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_status, tv_pass, tv_phone, tv_name, tv_edit, tv_District, tv_delete;
        CardView cardView;
        Switch id_switch;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv_status = (TextView) itemView.findViewById(R.id.tv_status);
            tv_pass = (TextView) itemView.findViewById(R.id.tv_pass);
            tv_phone = (TextView) itemView.findViewById(R.id.tv_phone);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            tv_District = (TextView) itemView.findViewById(R.id.tv_District);
            cardView = (CardView) itemView.findViewById(R.id.card);
            id_switch = (Switch) itemView.findViewById(R.id.id_switch);
        }
    }
}
