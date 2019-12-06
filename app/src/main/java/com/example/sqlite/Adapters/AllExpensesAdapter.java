package com.example.sqlite.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.sqlite.R;

import java.util.ArrayList;

public class AllExpensesAdapter extends BaseAdapter {
    public LayoutInflater layoutInflater;
    public Context context;
    public ArrayList<String> id;
    public ArrayList<String> date = new ArrayList<String>();
    public ArrayList<String> expensetype = new ArrayList<String>();
    public ArrayList<String> expensecost = new ArrayList<String>();
    public ArrayList<String> expensedescription = new ArrayList<String>();

    public AllExpensesAdapter(Context context,ArrayList<String> date, ArrayList<String> expensetype,
                              ArrayList<String> expensecost,ArrayList<String> expensedescription){
        this.context = context;
        this.date = date;
        this.expensetype = expensetype;
        this.expensecost = expensecost;
        this.expensedescription = expensedescription;
    }
    @Override
    public int getCount() {
        return date.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(layoutInflater == null)
            layoutInflater = LayoutInflater.from(context);
        if(convertView == null)
            convertView =layoutInflater.inflate(R.layout.all_expenses_layout,null);
        AllExpensesAdapter.viewHolder holder = new AllExpensesAdapter.viewHolder();
        holder.date =(TextView) convertView.findViewById(R.id.dateid);
        holder.expensetype = (TextView) convertView.findViewById(R.id.expensetype);
        holder.expensecost = (TextView) convertView.findViewById(R.id.expensecost);
        holder.expensedescription = (TextView) convertView.findViewById(R.id.expensedescription);

        //labels
        holder.datelabel = (TextView) convertView.findViewById(R.id.datelabel);
        holder.typelabel = (TextView) convertView.findViewById(R.id.expenselabel);
        holder.desclabel = (TextView) convertView.findViewById(R.id.descriptionlabel);
        holder.costlabel = (TextView) convertView.findViewById(R.id.cost);

        holder.date.setText(date.get(position));
        holder.expensetype.setText(expensetype.get(position));
        holder.expensecost.setText(expensecost.get(position));
        holder.expensedescription.setText(expensedescription.get(position));

        return convertView;
    }
    public class viewHolder{
        TextView date;
        TextView expensetype;
        TextView expensedescription;
        TextView expensecost;
        TextView datelabel;
        TextView typelabel;
        TextView desclabel;
        TextView costlabel;
    }
}
