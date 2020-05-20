package com.golcha.golchaproduction.ui.finish_production;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.golcha.golchaproduction.R;
import com.golcha.golchaproduction.ui.plan_production.PlanListAdapter;
import com.golcha.golchaproduction.ui.plan_production.PlannedOrderModel;

import java.util.ArrayList;

public class FinishListAdapter extends RecyclerView.Adapter<FinishListAdapter.ViewHolder> {
    ArrayList<FinishOrderModel> list;
    private Activity context;
    public FinishListAdapter(ArrayList<FinishOrderModel> list, Activity context) {
        this.list=list;
        this.context = context;
    }

    public void updateList(ArrayList<FinishOrderModel> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FinishListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View listitem=layoutInflater.inflate(R.layout.listitems,parent,false);
        FinishListAdapter.ViewHolder viewHolder=new FinishListAdapter.ViewHolder(listitem);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FinishOrderModel getarraylist=list.get(position);

        holder.textView1.setText(getarraylist.getSourceno());
        holder.textView2.setText(getarraylist.getDesc());
        holder.textView3.setText(getarraylist.getNo());
        holder.textView4.setText(getarraylist.getQuantity());
        holder.textView5.setText(getarraylist.getRoutingno());

    }



    @Override
    public int getItemCount() {
        return list.size();
    }
    public  static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView textView1;
        public TextView textView2;
        public TextView textView3;
        public TextView textView4;
        public TextView textView5;
        public LinearLayout linearLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.textView1=(TextView)itemView.findViewById(R.id.source_no);
            this.textView2=(TextView)itemView.findViewById(R.id.desc);
            this.textView3=(TextView)itemView.findViewById(R.id.no);
            this.textView4=(TextView)itemView.findViewById(R.id.quantity);
            this.textView5=(TextView)itemView.findViewById(R.id.routingno);
            this.linearLayout=(LinearLayout)itemView.findViewById(R.id.list_item);
        }
    }
}
