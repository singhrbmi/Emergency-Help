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

public class AddContactsAdapter extends RecyclerView.Adapter<AddContactsAdapter.MyViewHolder> implements Filterable {
    private Typeface materialdesignicons_font, ProximaNovaRegular, ProximaNovaLight;
    private Context mContext;
    private List<Result> dataList, FilteruserList;
    ProgressDialog pd;
    AddPhoneNoFragment fragment;

    public AddContactsAdapter(Context mContext, List<Result> dataList, AddPhoneNoFragment fragment) {
        this.mContext = mContext;
        this.dataList = dataList;
        this.FilteruserList = dataList;
        this.fragment = fragment;
//        this.ProximaNovaRegular = FontManager.getFontTypeface(mContext, "fonts/ProximaNova-Regular.otf");
//        this.ProximaNovaLight = FontManager.getFontTypeface(mContext, "fonts/ProximaNova-Light.otf");
        this.materialdesignicons_font = FontManager.getFontTypefaceMaterialDesignIcons(mContext, "fonts/materialdesignicons-webfont.otf");
    }

    @Override
    public AddContactsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_add_contacts, parent, false);
        return new AddContactsAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AddContactsAdapter.MyViewHolder holder, final int position) {
        String name = FilteruserList.get(position).getName();
        holder.name.setText(name);
        holder.phone.setText(FilteruserList.get(position).getPhone());
        if (FilteruserList.get(position).getOffPhone() != null && !FilteruserList.get(position).getOffPhone().equalsIgnoreCase("")) {
            holder.offPhone.setVisibility(View.VISIBLE);
            holder.tv_offPhone.setVisibility(View.VISIBLE);
            holder.offPhone.setText(FilteruserList.get(position).getOffPhone());
        }
        if (FilteruserList.get(position).getNameOff() != null && !FilteruserList.get(position).getNameOff().equalsIgnoreCase("")) {
            holder.nameOff.setVisibility(View.VISIBLE);
            holder.tv_nameOff.setVisibility(View.VISIBLE);
            holder.nameOff.setText(FilteruserList.get(position).getNameOff());
        }
        holder.tv_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment.updateShowData(true, FilteruserList.get(position).getPhoneId(), FilteruserList.get(position).getName(), FilteruserList.get(position).getPhone(), FilteruserList.get(position).getNameOff(), FilteruserList.get(position).getOffPhone());
//                varietyFragment.setVarietyAdapter();
            }
        });
        holder.tv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteData(position);
            }
        });

    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString().trim();
                // name match condition. this might differ depending on your requirement
                // here we are looking for name or phone number match
                if (charString.isEmpty()) {
                    FilteruserList = dataList;
                } else {
                    List<Result> filteredList = new ArrayList<>();
                    for (Result row : dataList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getName().toLowerCase().trim().contains(charString.toLowerCase()) | row.getPhone().toLowerCase().trim().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }


                    FilteruserList = filteredList;
                }


                FilterResults filterResults = new FilterResults();
                filterResults.values = FilteruserList;
                return filterResults;
            }


            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                FilteruserList = (ArrayList<Result>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    private void deleteData(final int position) {
        if (Utility.isOnline(mContext)) {
            pd = new ProgressDialog(mContext);
            pd.setMessage("Deleting wait......");
            pd.show();
            pd.setCancelable(false);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Contants.SERVICE_BASE_URL + Contants.deletePhone,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            pd.dismiss();
                            FilteruserList.remove(position);
                            fragment.setPhoneAdapter();
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
                    params.put("phoneId", String.valueOf(FilteruserList.get(position).getPhoneId()));
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
        TextView name, phone, offPhone, nameOff, tv_offPhone, tv_nameOff, tv_edit, tv_delete;

        public MyViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            phone = (TextView) itemView.findViewById(R.id.phone);
            offPhone = (TextView) itemView.findViewById(R.id.offPhone);
            nameOff = (TextView) itemView.findViewById(R.id.nameOff);
            tv_edit = (TextView) itemView.findViewById(R.id.tv_edit);
            tv_delete = (TextView) itemView.findViewById(R.id.tv_delete);
            tv_offPhone = (TextView) itemView.findViewById(R.id.tv_offPhone);
            tv_nameOff = (TextView) itemView.findViewById(R.id.tv_nameOff);
            tv_edit.setTypeface(materialdesignicons_font);
            tv_delete.setTypeface(materialdesignicons_font);
            tv_edit.setText(Html.fromHtml("&#xf64f;"));
            tv_delete.setText(Html.fromHtml("&#xf5ad;"));
        }

    }

}
