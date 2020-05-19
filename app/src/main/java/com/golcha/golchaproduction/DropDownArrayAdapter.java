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

import java.util.ArrayList;
import java.util.List;

public class DropDownArrayAdapter extends ArrayAdapter<String> {
    private final Context mContext;
    private final List<String> items;
    private final List<String> allItems;
    private final int mLayoutResourceId;
    private static final String TAG = "DropDownAdapter";
//    ArrayList<String> departments;
    

    public DropDownArrayAdapter(Context context, int resource, ArrayList<String> items) {
        super(context, resource, items);
        this.mContext = context;
        this.mLayoutResourceId = resource;
//        this.departments = departments;
        this.items = new ArrayList<>(items);
        this.allItems = new ArrayList<>(items);
    }

    public int getCount() {
        return items.size();
    }

    public String getItem(int position) {
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
             String item = getItem(position);
            TextView name = (TextView) convertView.findViewById(R.id.desc);
            name.setText(item);
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
                return (String) resultValue;
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                List<String> departmentsSuggestion = new ArrayList<>();
                Log.i(TAG, "performFiltering: " + constraint);
                if (constraint != null) {
                    Log.i(TAG, "performFiltering: " + items.size());
                    for (String department : allItems) {
                        Log.i(TAG, "performFiltering: " + items);
                        if (department.toLowerCase().contains(constraint.toString().toLowerCase())) {
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
                        if (object instanceof String) {
                            items.add((String) object);
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