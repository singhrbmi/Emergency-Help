package hacker.l.emergency_help.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
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
import hacker.l.emergency_help.fragments.SocialNoViewFragment;
import hacker.l.emergency_help.models.Result;
import hacker.l.emergency_help.utility.Contants;
import hacker.l.emergency_help.utility.FontManager;
import hacker.l.emergency_help.utility.Utility;

/**
 * Created by lalitsingh on 23/03/18.
 */

public class SubCategoryAdapter extends RecyclerView.Adapter<SubCategoryAdapter.MyViewHolder> {
    //    private Typeface materialdesignicons_font, ProximaNovaRegular;
    private Context mContext;
    private List<Result> userList, FilteruserList;
    AddPhoneCategoryFragment varietyFragment;
    ProgressDialog pd;

    public SubCategoryAdapter(Context mContext, List<Result> userList) {
        this.mContext = mContext;
        this.userList = userList;
        this.FilteruserList = userList;
//        this.varietyFragment = varietyFragment;
//        this.ProximaNovaRegular = FontManager.getFontTypeface(mContext, "fonts/ProximaNova-Regular.otf");
//        this.materialdesignicons_font = FontManager.getFontTypefaceMaterialDesignIcons(mContext, "fonts/materialdesignicons-webfont.otf");
    }

    @Override
    public SubCategoryAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_sub_category, parent, false);

        return new SubCategoryAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(SubCategoryAdapter.MyViewHolder holder, final int position) {
        holder.tv_variety.setText(FilteruserList.get(position).getSubCategory());
        holder.tv_variety.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SocialNoViewFragment fragment = SocialNoViewFragment.newInstance(FilteruserList.get(position).getSocialName(), FilteruserList.get(position).getDistrict(), FilteruserList.get(position).getSubCategory(), true);
                moveFragment(fragment);
            }
        });
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
        TextView tv_variety, tv_edit, tv_delete;
        LinearLayout linearLayout;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv_variety = (TextView) itemView.findViewById(R.id.tv_socialName);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.linearLayout);
//            tv_variety.setTypeface(ProximaNovaRegular);
//            tv_edit.setTypeface(materialdesignicons_font);
//            tv_delete.setTypeface(materialdesignicons_font);
//            tv_edit.setText(Html.fromHtml("&#xf64f;"));
//            tv_delete.setText(Html.fromHtml("&#xf5ad;"));
        }
    }
}
