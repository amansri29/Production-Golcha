package com.golcha.golchaproduction.ui.release_production;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.golcha.golchaproduction.R;

import java.util.ArrayList;

public class ReleaseListAdapter extends RecyclerView.Adapter<ReleaseListAdapter.ViewHolder> {
    ArrayList<ReleaseModel> list;
    private Context mContext;

    public ReleaseListAdapter(Activity activity, ArrayList<ReleaseModel> list){
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
        final ReleaseModel getReleasearraylist =list.get(position);
        holder.itemView.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AppCompatActivity activity = (AppCompatActivity) view.getContext();
                        Fragment myFragment = new ReleaseDetailFragment();
                        Bundle bundle=new Bundle();
                        bundle.putString("Releaseno",getReleasearraylist.getNo());
                        myFragment.setArguments(bundle);
                        activity.getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, myFragment)
                               .commit();

                    }
                }
        );


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
