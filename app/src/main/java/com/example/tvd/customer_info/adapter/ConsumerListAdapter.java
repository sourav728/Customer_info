package com.example.tvd.customer_info.adapter;


import android.content.Context;

import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tvd.customer_info.R;
import com.example.tvd.customer_info.SwitchConsumerActivity;
import com.example.tvd.customer_info.values.GetSetValues;

import java.util.ArrayList;

import static com.example.tvd.customer_info.values.ConstantValues.DEACTIVATE_ACCOUNT;

public class ConsumerListAdapter extends RecyclerView.Adapter<ConsumerListAdapter.ConsumerHolder> {
    private ArrayList<GetSetValues> arrayList;
    private Context context;
    private GetSetValues getsetvalues;

    public ConsumerListAdapter(ArrayList<GetSetValues> arrayList, Context context, GetSetValues getsetvalues) {
        this.arrayList = arrayList;
        this.context = context;
        this.getsetvalues = getsetvalues;
    }

    @Override
    public ConsumerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.switch_consumer_adapter_layout, null);
        return new ConsumerHolder(view);
    }

    @Override
    public void onBindViewHolder(ConsumerHolder holder, int position) {
        GetSetValues getSetValues = arrayList.get(position);
        holder.consumerid.setText(getSetValues.getConsumer_id());
        holder.rrno.setText(getSetValues.getRrno());
        holder.relationship.setText(getSetValues.getRelationship());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ConsumerHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView consumerid, rrno, relationship;
        ImageView delete;

        public ConsumerHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            consumerid = (TextView) itemView.findViewById(R.id.txt_set_consumer_id);
            rrno = (TextView) itemView.findViewById(R.id.txt_set_rrno);
            relationship = (TextView) itemView.findViewById(R.id.txt_set_relationship);
            delete = (ImageView) itemView.findViewById(R.id.img_delete);

        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            ((SwitchConsumerActivity) context).show_deactivate_dialog(DEACTIVATE_ACCOUNT, position, arrayList);
        }
    }
}
