package com.golcha.golchaproduction;


import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import com.golcha.golchaproduction.ui.plan_production.PlannedOrderModel;

import java.util.ArrayList;
import java.util.List;

public class PlannedOrderArrayAdapter extends ArrayAdapter<PlannedOrderModel> {
    private final Context mContext;
    private final List<PlannedOrderModel> items;
    private final List<PlannedOrderModel> allItems;
    private final int mLayoutResourceId;
    private static final String TAG = "PlannedOrderArrayAdapte";
//    ArrayList<String> departments;


    public PlannedOrderArrayAdapter(Context context, int resource, ArrayList<PlannedOrderModel> items) {
        super(context, resource);
//        super(context, resource, items);
        this.mContext = context;
        this.mLayoutResourceId = resource;
//        this.departments = departments;
        this.items = new ArrayList<>(items);
        this.allItems = new ArrayList<>(items);
    }

    public int getCount() {
        return items.size();
    }

    public PlannedOrderModel getItem(int position) {
        return items.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        try {
            if (convertView == null) {
                LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
                convertView = inflater.inflate(mLayoutResourceId, parent, false);
            }
            PlannedOrderModel item = getItem(position);
            TextView textView1=(TextView) convertView.findViewById(R.id.source_no);
            TextView textView2 =(TextView) convertView.findViewById(R.id.desc);
            TextView textView3=(TextView) convertView.findViewById(R.id.no);
            TextView textView4 =(TextView) convertView.findViewById(R.id.quantity);
            TextView textView5 =(TextView) convertView.findViewById(R.id.routingno);

            textView1.setText(item.getSourceno());
            textView2.setText(item.getDesc());
            textView3.setText(item.getNo());
            textView4.setText(item.getQuantity());
            textView5.setText(item.getRoutingno());



        } catch (Exception e) {
            e.printStackTrace();
        }
        return convertView;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            public String convertResultToString(Object resultValue) {
                PlannedOrderModel plannedOrderModel = (PlannedOrderModel) resultValue;
                String result = plannedOrderModel.getNo() + " " + plannedOrderModel.getDesc() + " " + plannedOrderModel.getSourceno();
                return result;
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                List<PlannedOrderModel> departmentsSuggestion = new ArrayList<>();
                Log.i(TAG, "performFiltering: " + constraint);
                if (constraint != null) {
                    Log.i(TAG, "performFiltering: " + items.size());
                    for (PlannedOrderModel department : allItems) {
                        Log.i(TAG, "performFiltering: " + items);
                        String search_item = department.getNo() + " " + department.getDesc() + " " + department.getSourceno();
                        if (search_item.toLowerCase().contains(constraint.toString().toLowerCase())) {
                            departmentsSuggestion.add(department);
                        }
                    }
                    filterResults.values = departmentsSuggestion;
                    filterResults.count = departmentsSuggestion.size();
                }
                else
                {
                    Log.i(TAG, "performFiltering2: null");
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                items.clear();
                if (results != null && results.count > 0) {
                    // avoids unchecked cast warning when using items.addAll((ArrayList<Department>) results.values);
                    for (Object object : (List<?>) results.values) {
                        if (object instanceof PlannedOrderModel) {
                            items.add((PlannedOrderModel) object);
                        }
                    }
                    notifyDataSetChanged();
                } else if (constraint == null) {
                    // no filter, add entire original list back in
                    items.addAll(allItems);
                    notifyDataSetInvalidated();
                }
            }
        };
    }
}