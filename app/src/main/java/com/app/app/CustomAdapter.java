package com.app.app;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class CustomAdapter  extends BaseAdapter {

    public static int num;
    private Context context;
    public static ArrayList<Viewdatalist> modelArrayList;


    public CustomAdapter(Context context, ArrayList<Viewdatalist> modelArrayList) {

        this.context = context;
        this.modelArrayList = modelArrayList;

    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }
    @Override
    public int getItemViewType(int position) {

        return position;
    }

    @Override
    public int getCount() {
        return modelArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return modelArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder(); LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.lv_items, null, true);

            holder.checkBox = (CheckBox) convertView.findViewById(R.id.cb);
            holder.tvAnimal = (TextView) convertView.findViewById(R.id.a);

            convertView.setTag(holder);
        }else {
            // the getTag returns the viewHolder object set as a tag to the view
            holder = (ViewHolder)convertView.getTag();
        }


        //holder.checkBox.setText("Checkbox "+position);
        holder.tvAnimal.setText(modelArrayList.get(position).getTxt());

        holder.checkBox.setChecked(modelArrayList.get(position).getSelected());

        holder.tvAnimal.setTag( position);
        holder.tvAnimal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer pos = (Integer)  holder.tvAnimal.getTag();
                Toast.makeText(context,"text :"+pos,Toast.LENGTH_LONG).show();
                num = pos+1;
                Intent intent = new Intent(context,Dataview.class);
                context.startActivity(intent);
            }
        });

        holder.checkBox.setTag(R.integer.btnplusview, convertView);
        holder.checkBox.setTag( position);
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                View tempview = (View) holder.checkBox.getTag(R.integer.btnplusview);
                TextView tv = (TextView) tempview.findViewById(R.id.a);
                Integer pos = (Integer)  holder.checkBox.getTag();
                Toast.makeText(context, "Checkbox "+pos+" clicked!", Toast.LENGTH_SHORT).show();

                if(modelArrayList.get(pos).getSelected()){
                    modelArrayList.get(pos).setSelected(false);
                }else {
                    modelArrayList.get(pos).setSelected(true);
                }

            }
        });

        return convertView;
    }

    private class ViewHolder {

        protected CheckBox checkBox;
        private TextView tvAnimal;

    }

}