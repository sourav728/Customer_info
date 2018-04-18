package com.example.tvd.customer_info.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tvd.customer_info.R;

import java.util.ArrayList;

/**
 * Created by TVD on 4/17/2018.
 */

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.DetailsHoldewr> {
    ArrayList personNames;
    ArrayList personImages;
    Context context;
    public NotificationAdapter(Context context,ArrayList personNames, ArrayList personImages)
    {
        this.context = context;
        this.personNames = personNames;
        this.personImages = personImages;
    }
    @Override
    public NotificationAdapter.DetailsHoldewr onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout, null);
        return new DetailsHoldewr(view);
    }

    @Override
    public void onBindViewHolder(NotificationAdapter.DetailsHoldewr holder, int position) {
        holder.textView.setText(personNames.get(position)+"");
    }

    @Override
    public int getItemCount() {
        return personImages.size();
    }

    public class DetailsHoldewr extends RecyclerView.ViewHolder {
        TextView textView;
        public DetailsHoldewr(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.name);
        }
    }
}
