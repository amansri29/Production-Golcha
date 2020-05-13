package com.golcha.golchaproduction.ui.dashboard;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.golcha.golchaproduction.R;

import java.util.ArrayList;

public class ReleaseProd_Listadapter extends RecyclerView.Adapter<ReleaseProd_Listadapter.ViewHolder> {
    ArrayList<GetReleasearraylist> list;
    private Context mContext;
    public ReleaseProd_Listadapter(ArrayList<GetReleasearraylist> list){
        this.list=list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View listitem=layoutInflater.inflate(R.layout.listitems,parent,false);
        ViewHolder viewHolder=new ViewHolder(listitem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final GetReleasearraylist getReleasearraylist =list.get(position);
        holder.textView1.setText(getReleasearraylist.getSourceno());
        holder.textView2.setText(getReleasearraylist.getDesc());
        holder.textView3.setText(getReleasearraylist.getNo());
        holder.textView4.setText(getReleasearraylist.getQuantity());
        holder.textView5.setText(getReleasearraylist.getRoutingno());


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
        public CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.textView1=(TextView)itemView.findViewById(R.id.source_no);
            this.textView2=(TextView)itemView.findViewById(R.id.desc);
            this.textView3=(TextView)itemView.findViewById(R.id.no);
            this.textView4=(TextView)itemView.findViewById(R.id.quantity);
            this.textView5=(TextView)itemView.findViewById(R.id.routingno);
            this.cardView=(CardView) itemView.findViewById(R.id.card_view);
        }
    }

}
