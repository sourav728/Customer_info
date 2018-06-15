package com.example.tvd.customer_info.adapter;


import android.content.Context;

import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.tvd.customer_info.R;
import com.example.tvd.customer_info.SwitchConsumerActivity;
import com.example.tvd.customer_info.values.GetSetValues;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;
import static com.example.tvd.customer_info.values.ConstantValues.DEACTIVATE_ACCOUNT;

public class ConsumerListAdapter extends RecyclerView.Adapter<ConsumerListAdapter.ConsumerHolder> {
    private ArrayList<GetSetValues> arrayList;
    private Context context;
    private GetSetValues getsetvalues;
    private static int Current_position = 0;

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

        SharedPreferences sharedPreferences = context.getSharedPreferences("SWITCH_CONSUMER_ID", MODE_PRIVATE);
        String recycler_view_position = sharedPreferences.getString("Curr_position", "");
        if (!recycler_view_position.equals("")) {
            try {
                Current_position = Integer.parseInt(recycler_view_position);
                if (Current_position == position)
                    holder.active.setVisibility(View.VISIBLE);
                else holder.active.setVisibility(View.INVISIBLE);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        } else {

        }

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ConsumerHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView consumerid, rrno, relationship, active;
        ImageView delete, switch_account;

        public ConsumerHolder(View itemView) {
            super(itemView);
            // itemView.setOnClickListener(this);
            consumerid = (TextView) itemView.findViewById(R.id.txt_set_consumer_id);
            rrno = (TextView) itemView.findViewById(R.id.txt_set_rrno);
            active = (TextView) itemView.findViewById(R.id.txt_active);

            relationship = (TextView) itemView.findViewById(R.id.txt_set_relationship);
            delete = (ImageView) itemView.findViewById(R.id.img_delete);
            switch_account = (ImageView) itemView.findViewById(R.id.img_switch);
            delete.setOnClickListener(this);
            switch_account.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            GetSetValues getSetValues = arrayList.get(position);
            switch (v.getId()) {
                case R.id.img_delete:
                    ((SwitchConsumerActivity) context).show_deactivate_dialog(DEACTIVATE_ACCOUNT, position, arrayList);
                    break;
                case R.id.img_switch:
                    ((SwitchConsumerActivity) context).swap_Account(position, arrayList);
                    break;
            }

        }
    }

}
