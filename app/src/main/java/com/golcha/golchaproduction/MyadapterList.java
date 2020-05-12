package com.golcha.golchaproduction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyadapterList extends RecyclerView.Adapter<MyadapterList.ViewHolder> {
    ArrayList<Getarraylist> list;
    public MyadapterList(ArrayList<Getarraylist> list){
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
        final Getarraylist getarraylist=list.get(position);
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
