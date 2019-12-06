package com.example.sqlite.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.sqlite.R;

import java.util.ArrayList;

public class AllStockAdapter extends BaseAdapter {
    public Context context;
    public LayoutInflater layoutInflater;
    public ArrayList<String> date = new ArrayList<String>();
    public ArrayList<String> item = new ArrayList<String>();
    public ArrayList<String> description = new ArrayList<String>();
    public ArrayList<String> cost = new ArrayList<String>();
    public AllStockAdapter(Context context,ArrayList<String> date,ArrayList<String> item,
                           ArrayList<String> description,ArrayList<String> cost){
        this.context = context;
        this.cost = cost;
        this.date = date;
        this.description = description;
        this.item = item;
    }



    @Override
    public int getCount() {
        return cost.size();
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
            convertView =layoutInflater.inflate(R.layout.all_stock_layout,null);
        AllStockAdapter.viewHolder holder = new AllStockAdapter.viewHolder();
        holder.item = (TextView) convertView.findViewById(R.id.item);
        holder.description = (TextView) convertView.findViewById(R.id.description);
        holder.cost = (TextView) convertView.findViewById(R.id.price);
        holder.date = (TextView) convertView.findViewById(R.id.date);

        holder.datelabel = (TextView) convertView.findViewById(R.id.datelabel);
        holder.itemlabel = (TextView) convertView.findViewById(R.id.itemlabel);
        holder.descriptionlabel = (TextView) convertView.findViewById(R.id.descriptionlabel);
        holder.pricelabel = (TextView) convertView.findViewById(R.id.pricelabel);

        holder.date.setText(date.get(position));
        holder.item.setText(item.get(position));
        holder.description.setText(description.get(position));
        holder.cost.setText(cost.get(position));

        return convertView;
    }
    public class viewHolder{
        TextView date;
        TextView item;
        TextView description;
        TextView cost;

        //labels
        TextView datelabel;
        TextView itemlabel;
        TextView descriptionlabel;
        TextView pricelabel;
    }
}
