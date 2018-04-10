package hacker.l.emergency_help.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import hacker.l.emergency_help.R;
import hacker.l.emergency_help.models.Result;

/**
 * Created by lalitsingh on 05/03/18.
 */

public class SocialAdapter extends RecyclerView.Adapter<SocialAdapter.MyViewHolder> {
    private Typeface materialdesignicons_font, ProximaNovaRegular, ProximaNovaLight;
    private Context mContext;
    private List<Result> dataList, FilteruserList;

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
        String name = FilteruserList.get(position).getSocialName();
        holder.tv_socialName.setText(name);
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
