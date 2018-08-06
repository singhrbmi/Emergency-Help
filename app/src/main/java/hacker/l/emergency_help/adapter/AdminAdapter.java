package hacker.l.emergency_help.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
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
import hacker.l.emergency_help.fragments.AddUpdateAdministiveFragment;
import hacker.l.emergency_help.fragments.AdminAdviseMgmtFragment;
import hacker.l.emergency_help.models.Result;
import hacker.l.emergency_help.utility.Contants;
import hacker.l.emergency_help.utility.FontManager;
import hacker.l.emergency_help.utility.Utility;

/**
 * Created by lalitsingh on 23/03/18.
 */

public class AdminAdapter extends RecyclerView.Adapter<AdminAdapter.MyViewHolder> {
    private Typeface materialdesignicons_font, ProximaNovaRegular;
    private Context mContext;
    private List<Result> userList, FilteruserList;
    AddUpdateAdministiveFragment addUpdateAdministiveFragment;
    ProgressDialog pd;
    boolean b = false;

    public AdminAdapter(Context mContext, List<Result> userList, boolean b, AddUpdateAdministiveFragment addUpdateAdministiveFragment) {
        this.mContext = mContext;
        this.userList = userList;
        this.FilteruserList = userList;
        this.b = b;
        this.addUpdateAdministiveFragment = addUpdateAdministiveFragment;
        this.materialdesignicons_font = FontManager.getFontTypefaceMaterialDesignIcons(mContext, "fonts/materialdesignicons-webfont.otf");
    }

    public AdminAdapter(Context mContext, List<Result> userList, boolean b) {
        this.mContext = mContext;
        this.userList = userList;
        this.FilteruserList = userList;
        this.b = b;
        this.materialdesignicons_font = FontManager.getFontTypefaceMaterialDesignIcons(mContext, "fonts/materialdesignicons-webfont.otf");
    }

    @Override
    public AdminAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_admin, parent, false);

        return new AdminAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AdminAdapter.MyViewHolder holder, final int position) {
//        if (position % 2 == 1) {
//            holder.linearLayout.setBackgroundColor(Color.parseColor("#ffffff"));
//        } else {
//            holder.linearLayout.setBackgroundColor(Color.parseColor("#ffffff"));
//        }
        final String name = FilteruserList.get(position).getAdminName();
        final String phone = FilteruserList.get(position).getAdminPhone();
        final String pass = FilteruserList.get(position).getAdminPass();
        final String dist = FilteruserList.get(position).getDistrict();
        final String stus = FilteruserList.get(position).getIsActive();
        final int adminId = FilteruserList.get(position).getAdminId();
        if (name != null && !name.equalsIgnoreCase("")) {
            holder.tv_name.setText("Name:- " + name);
            holder.tv_phone.setText("Phone:- " + phone);
            holder.tv_pass.setText("Password:- " + pass);
            holder.tv_District.setText("District:- " + dist);
            holder.tv_status.setText("Status:- " + stus);
            if (stus.equalsIgnoreCase("Block")) {
                holder.tv_status.setTextColor(Color.RED);
            }
            if (b) {
                holder.tv_delete.setVisibility(View.GONE);
                holder.tv_edit.setVisibility(View.GONE);
            }
            holder.tv_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addUpdateAdministiveFragment.updateShowData(true, adminId, name, phone, pass, dist, stus);
                }
            });
            holder.tv_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.setTitle("Alert");
                    builder.setIcon(R.drawable.places_ic_clear);
                    builder.setMessage("Are Your Sure !\n Delete It.");
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            deleteVariety(position);
                        }
                    });
                    builder.show();
                }
            });
        } else {
            holder.cardView.setVisibility(View.GONE);
        }

    }


    private void deleteVariety(final int position) {
        if (Utility.isOnline(mContext)) {
            pd = new ProgressDialog(mContext);
            pd.setMessage("Deleting wait......");
            pd.show();
            pd.setCancelable(false);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Contants.SERVICE_BASE_URL + Contants.deleteAdmin,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            pd.dismiss();
                            FilteruserList.remove(position);
                            addUpdateAdministiveFragment.setAdapter();
                            Toast.makeText(mContext, "Delete Successully", Toast.LENGTH_LONG).show();
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

        public MyViewHolder(View itemView) {
            super(itemView);
            tv_status = (TextView) itemView.findViewById(R.id.tv_status);
            tv_pass = (TextView) itemView.findViewById(R.id.tv_pass);
            tv_phone = (TextView) itemView.findViewById(R.id.tv_phone);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            tv_edit = (TextView) itemView.findViewById(R.id.tv_edit);
            tv_District = (TextView) itemView.findViewById(R.id.tv_District);
            tv_delete = (TextView) itemView.findViewById(R.id.tv_delete);
            cardView = (CardView) itemView.findViewById(R.id.card);
            tv_edit.setTypeface(materialdesignicons_font);
            tv_delete.setTypeface(materialdesignicons_font);
            tv_edit.setText(Html.fromHtml("&#xf64f;"));
            tv_delete.setText(Html.fromHtml("&#xf5ad;"));
        }
    }
}
