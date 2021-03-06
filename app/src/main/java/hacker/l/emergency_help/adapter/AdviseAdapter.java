package hacker.l.emergency_help.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
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
import hacker.l.emergency_help.fragments.AddPhoneCategoryFragment;
import hacker.l.emergency_help.fragments.AdminAdviseMgmtFragment;
import hacker.l.emergency_help.models.Result;
import hacker.l.emergency_help.utility.Contants;
import hacker.l.emergency_help.utility.FontManager;
import hacker.l.emergency_help.utility.Utility;

/**
 * Created by lalitsingh on 23/03/18.
 */

public class AdviseAdapter extends RecyclerView.Adapter<AdviseAdapter.MyViewHolder> {
    private Typeface materialdesignicons_font, ProximaNovaRegular;
    private Context mContext;
    private List<Result> userList, FilteruserList;
    AdminAdviseMgmtFragment varietyFragment;
    ProgressDialog pd;
    boolean b = false;

    public AdviseAdapter(Context mContext, List<Result> userList, AdminAdviseMgmtFragment varietyFragment) {
        this.mContext = mContext;
        this.userList = userList;
        this.FilteruserList = userList;
        this.varietyFragment = varietyFragment;
        this.ProximaNovaRegular = FontManager.getFontTypeface(mContext, "fonts/ProximaNova-Regular.otf");
        this.materialdesignicons_font = FontManager.getFontTypefaceMaterialDesignIcons(mContext, "fonts/materialdesignicons-webfont.otf");
    }

    public AdviseAdapter(Context mContext, List<Result> userList, boolean b) {
        this.mContext = mContext;
        this.userList = userList;
        this.FilteruserList = userList;
        this.b = b;
    }

    @Override
    public AdviseAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_advise, parent, false);

        return new AdviseAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AdviseAdapter.MyViewHolder holder, final int position) {
        if (position % 2 == 1) {
            holder.linearLayout.setBackgroundColor(Color.parseColor("#ffffff"));
        } else {
            holder.linearLayout.setBackgroundColor(Color.parseColor("#ffffff"));
        }
        holder.tv_variety.setText(FilteruserList.get(position).getAdvise());
        holder.tv_date.setText(FilteruserList.get(position).getDate());
        if (b) {
            holder.tv_delete.setVisibility(View.GONE);
            holder.tv_edit.setVisibility(View.GONE);
        }
        holder.tv_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                varietyFragment.updateShowData(true, FilteruserList.get(position).getAdvise(), FilteruserList.get(position).getAdviseId());
//                varietyFragment.setVarietyAdapter();
            }
        });
        holder.tv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteVariety(position);
            }
        });
    }


    private void deleteVariety(final int position) {
        if (Utility.isOnline(mContext)) {
            pd = new ProgressDialog(mContext);
            pd.setMessage("Deleting wait......");
            pd.show();
            pd.setCancelable(false);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Contants.SERVICE_BASE_URL + Contants.deleteAdvise,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            pd.dismiss();
                            FilteruserList.remove(position);
                            varietyFragment.setAdapter();
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
                    params.put("adviseId", String.valueOf(FilteruserList.get(position).getAdviseId()));
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
        TextView tv_variety, tv_edit, tv_date, tv_delete;
        LinearLayout linearLayout;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv_variety = (TextView) itemView.findViewById(R.id.tv_variety);
            tv_edit = (TextView) itemView.findViewById(R.id.tv_edit);
            tv_date = (TextView) itemView.findViewById(R.id.tv_date);
            tv_delete = (TextView) itemView.findViewById(R.id.tv_delete);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.linearLayout);
            tv_variety.setTypeface(ProximaNovaRegular);
            tv_edit.setTypeface(materialdesignicons_font);
            tv_delete.setTypeface(materialdesignicons_font);
            tv_edit.setText(Html.fromHtml("&#xf64f;"));
            tv_delete.setText(Html.fromHtml("&#xf5ad;"));
        }
    }
}
