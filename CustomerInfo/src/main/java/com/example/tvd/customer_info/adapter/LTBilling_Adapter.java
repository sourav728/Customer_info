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

public class LTBilling_Adapter extends RecyclerView.Adapter<LTBilling_Adapter.BillingHolder> {
    private ArrayList<GetSetValues>arrayList = new ArrayList<>();
    private Context context;
    public LTBilling_Adapter(Context context, ArrayList<GetSetValues>arrayList)
    {
        this.arrayList = arrayList;
        this.context = context;
    }
    @Override
    public BillingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lt_billing_recyclerview1,null);
        return new BillingHolder(view);
    }

    @Override
    public void onBindViewHolder(BillingHolder holder, int position) {
        GetSetValues getSetValues = arrayList.get(position);
        holder.date.setText(getSetValues.getLtbill_date());
        holder.con.setText(getSetValues.getLtbill_con());
        holder.arrears.setText(getSetValues.getLtbill_arrears());
        holder.demand.setText(getSetValues.getLtbill_demand());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class BillingHolder extends RecyclerView.ViewHolder {
        TextView date,con,arrears,demand;
        public BillingHolder(View itemView) {
            super(itemView);
            date = (TextView) itemView.findViewById(R.id.txt_date);
            con = (TextView) itemView.findViewById(R.id.txt_consumption);
            arrears = (TextView) itemView.findViewById(R.id.txt_arrayers);
            demand = (TextView) itemView.findViewById(R.id.txt_demand);
        }
    }
}
