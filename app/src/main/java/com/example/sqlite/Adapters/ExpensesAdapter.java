package com.example.sqlite.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sqlite.R;
import com.example.sqlite.objects.Expenses;

import java.util.ArrayList;

public class ExpensesAdapter extends BaseAdapter {
    public Context context;
    public LayoutInflater layoutInflater;
    public ArrayList<String> expensetype = new ArrayList<String>();
    public ArrayList<String> expensedescription = new ArrayList<String>();
    public ArrayList<String> expensecost = new ArrayList<String>();
    public ExpensesAdapter(Context context,ArrayList<String> expensetype,
                           ArrayList<String> expensedescription,ArrayList<String> expensecost){
        this.context = context;
        this.expensecost = expensecost;
        this.expensedescription = expensedescription;
        this.expensetype= expensetype;
    }



    @Override
    public int getCount() {
        return expensecost.size();
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
            convertView =layoutInflater.inflate(R.layout.expenses_layout,null);
        viewHolder holder = new viewHolder();
        holder.expensetype = (TextView) convertView.findViewById(R.id.expensetype);
        holder.expensecost = (TextView) convertView.findViewById(R.id.expensecost);
        holder.expensedescription = (TextView) convertView.findViewById(R.id.expensedescription);

        holder.typelabel = (TextView) convertView.findViewById(R.id.expensetypelabel);
        holder.desclabel = (TextView) convertView.findViewById(R.id.expensedescriptionlabel);
        holder.costlabel = (TextView) convertView.findViewById(R.id.cost);

        holder.expensetype.setText(expensetype.get(position));
        holder.expensecost.setText(expensecost.get(position));
        holder.expensedescription.setText(expensedescription.get(position));

        return convertView;
    }
    public class viewHolder{
        TextView expensetype;
        TextView expensedescription;
        TextView expensecost;

        //labels
        TextView typelabel;
        TextView desclabel;
        TextView costlabel;
    }
}
