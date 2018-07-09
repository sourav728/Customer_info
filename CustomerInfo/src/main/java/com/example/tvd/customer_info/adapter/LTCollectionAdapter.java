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

public class LTCollectionAdapter extends RecyclerView.Adapter<LTCollectionAdapter.CollectionHolder> {
    private ArrayList<GetSetValues> arrayList = new ArrayList<>();
    private Context context;
    public LTCollectionAdapter(Context context, ArrayList<GetSetValues>arrayList)
    {
        this.arrayList = arrayList;
        this.context = context;
    }
    @Override
    public CollectionHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lt_collection_recyclerview,null);
        return new CollectionHolder(view);
    }

    @Override
    public void onBindViewHolder(CollectionHolder holder, int position) {
        GetSetValues getSetValues = arrayList.get(position);
        holder.date.setText(getSetValues.getLt_receiptdate());
        holder.amount.setText(getSetValues.getLt_collection_amt());
        holder.receptno.setText(getSetValues.getLt_receiptno());
        holder.counter.setText(getSetValues.getLt_collection_counter());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class CollectionHolder extends RecyclerView.ViewHolder {
        TextView date,amount,receptno,counter;
        public CollectionHolder(View itemView) {
            super(itemView);
            date = (TextView) itemView.findViewById(R.id.txt_date);
            amount = (TextView) itemView.findViewById(R.id.txt_amt);
            receptno = (TextView) itemView.findViewById(R.id.txt_receptno);
            counter = (TextView) itemView.findViewById(R.id.txt_counter);
        }
    }
}
