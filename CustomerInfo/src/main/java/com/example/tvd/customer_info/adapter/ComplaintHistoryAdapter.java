package com.example.tvd.customer_info.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.tvd.customer_info.R;
import com.example.tvd.customer_info.values.GetSetValues;

import java.util.ArrayList;

public class ComplaintHistoryAdapter extends RecyclerView.Adapter<ComplaintHistoryAdapter.ComplaintHolder> {
    private ArrayList<GetSetValues> arrayList;
    private Context context;
    private GetSetValues getSetValues;
    public ComplaintHistoryAdapter(ArrayList<GetSetValues>arrayList,Context context,GetSetValues getSetValues)
    {
        this.arrayList = arrayList;
        this.context = context;
        this.getSetValues = getSetValues;
    }
    @Override
    public ComplaintHistoryAdapter.ComplaintHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.complaint_history_layout, null);
       return new ComplaintHolder(view);
    }

    @Override
    public void onBindViewHolder(ComplaintHistoryAdapter.ComplaintHolder holder, int position) {
        GetSetValues getSetValues = arrayList.get(position);
        holder.c_type.setText(getSetValues.getComplaint_type());
        holder.c_sub_type.setText(getSetValues.getComplaint_sub_type());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ComplaintHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView c_type,c_sub_type;
        public ComplaintHolder(View itemView) {
            super(itemView);
            c_type = (TextView) itemView.findViewById(R.id.txt_ctype);
            c_sub_type = (TextView) itemView.findViewById(R.id.txt_c_subtype);
        }

        @Override
        public void onClick(View v) {

        }
    }
}
