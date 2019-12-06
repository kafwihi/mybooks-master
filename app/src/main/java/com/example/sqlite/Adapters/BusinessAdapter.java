package com.example.sqlite.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.sqlite.R;
import com.example.sqlite.objects.Businesses;

import java.util.ArrayList;
import java.util.List;

public class BusinessAdapter extends BaseAdapter{
    public LayoutInflater layoutInflater;
    public Context context;
    public ArrayList<String> businessname = new ArrayList<String>();
    public ArrayList<String> businessdescription = new ArrayList<String>();
    //public ArrayList<String> id = new ArrayList<String>();

    public BusinessAdapter(Context context,ArrayList<String> businessname,ArrayList<String> businessdescription){
        this.context = context;
        this.businessdescription= businessdescription;
        this.businessname = businessname;
    }
    @Override
    public int getCount() {
        return businessname.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
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
            convertView =layoutInflater.inflate(R.layout.business_list,null);
        viewHolder holder = new viewHolder();
        holder.businessname = (TextView) convertView.findViewById(R.id.bizname);
        holder.businessdescription = (TextView) convertView.findViewById(R.id.bizdesc);
        //holder.id = (TextView) convertView.findViewById(R.id.bizid);
        holder.biznameid = (TextView) convertView.findViewById(R.id.biznameid);
        holder.biznamedesc = (TextView) convertView.findViewById(R.id.biznamedesc);

        holder.businessname.setText(businessname.get(position));
        holder.businessdescription.setText(businessdescription.get(position));
        //holder.id.setText(id.get(position));
        return convertView;
    }
    public class viewHolder{
        //TextView id;
        TextView businessname;
        TextView businessdescription;
        //labels
        TextView biznameid;
        TextView biznamedesc;
    }
}
