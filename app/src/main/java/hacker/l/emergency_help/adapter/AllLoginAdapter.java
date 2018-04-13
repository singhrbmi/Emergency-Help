package hacker.l.emergency_help.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hacker.l.emergency_help.R;
import hacker.l.emergency_help.fragments.AddPhoneNoFragment;
import hacker.l.emergency_help.models.Result;
import hacker.l.emergency_help.utility.Contants;
import hacker.l.emergency_help.utility.FontManager;
import hacker.l.emergency_help.utility.Utility;

/**
 * Created by lalitsingh on 05/03/18.
 */

public class AllLoginAdapter extends RecyclerView.Adapter<AllLoginAdapter.MyViewHolder> {
    private Typeface materialdesignicons_font, ProximaNovaRegular, ProximaNovaLight;
    private Context mContext;
    private List<Result> dataList, FilteruserList;
    ProgressDialog pd;
    AddPhoneNoFragment fragment;

    public AllLoginAdapter(Context mContext, List<Result> dataList) {
        this.mContext = mContext;
        this.dataList = dataList;
        this.FilteruserList = dataList;
        this.fragment = fragment;
//        this.ProximaNovaRegular = FontManager.getFontTypeface(mContext, "fonts/ProximaNova-Regular.otf");
//        this.ProximaNovaLight = FontManager.getFontTypeface(mContext, "fonts/ProximaNova-Light.otf");
        this.materialdesignicons_font = FontManager.getFontTypefaceMaterialDesignIcons(mContext, "fonts/materialdesignicons-webfont.otf");
    }

    @Override
    public AllLoginAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_all_login, parent, false);
        return new AllLoginAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AllLoginAdapter.MyViewHolder holder, final int position) {
        String name = FilteruserList.get(position).getUsername();
        holder.name.setText(name);
        holder.tv_email.setText(FilteruserList.get(position).getEmailId());
        holder.phone.setText(FilteruserList.get(position).getUserPhone());
        holder.tv_loginId.setText(FilteruserList.get(position).getLoginId() + "");
    }


    @Override
    public int getItemCount() {
        return FilteruserList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name, phone, tv_email, tv_loginId;

        public MyViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            phone = (TextView) itemView.findViewById(R.id.phone);
            tv_email = (TextView) itemView.findViewById(R.id.tv_email);
            tv_loginId = (TextView) itemView.findViewById(R.id.tv_loginId);
        }

    }

}
