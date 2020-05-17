package com.golcha.golchaproduction.ui.home;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.golcha.golchaproduction.R;

import java.util.ArrayList;

public class PlanProduc_ListAdaper extends RecyclerView.Adapter<PlanProduc_ListAdaper.ViewHolder> {
    ArrayList<Getplanarraylist> list2;
    private Activity context;

    public PlanProduc_ListAdaper(ArrayList<Getplanarraylist> list, Activity context){
        this.list2=list;
        this.context = context;
    }

    @NonNull
    @Override
    public PlanProduc_ListAdaper.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View listitem=layoutInflater.inflate(R.layout.listitems,parent,false);
        PlanProduc_ListAdaper.ViewHolder viewHolder=new PlanProduc_ListAdaper.ViewHolder(listitem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final Getplanarraylist getarraylist=list2.get(position);
        holder.itemView.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AppCompatActivity activity = (AppCompatActivity) view.getContext();
                        Fragment myFragment = new HomExtendFrag();
                        Bundle bundle=new Bundle();
                        bundle.putString("no",getarraylist.getNo());
                        myFragment.setArguments(bundle);
                        activity.getSupportFragmentManager().beginTransaction().replace(R.id.home, myFragment).addToBackStack(null)
                                .remove(new HomeFragment()).commit();


                    }
                }
        );

        holder.textView1.setText(getarraylist.getSourceno());
        holder.textView2.setText(getarraylist.getDesc());
        holder.textView3.setText(getarraylist.getNo());
        holder.textView4.setText(getarraylist.getQuantity());
        holder.textView5.setText(getarraylist.getRoutingno());

    }


    @Override
    public int getItemCount() {
        return list2.size();
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
