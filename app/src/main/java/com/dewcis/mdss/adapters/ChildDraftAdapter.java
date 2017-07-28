package com.dewcis.mdss.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.dewcis.mdss.d_model.BabyModel;
import com.dewcis.mdss.R;

import java.util.ArrayList;

/**
 * Created by Arwin KIsh on 7/13/2017.
 */


public class ChildDraftAdapter extends BaseAdapter {

    Context context;
    ArrayList<BabyModel> babyModels;
    boolean _show_button = true;
    public ChildDraftAdapter(Context context, ArrayList<BabyModel> babyModels){
        this.context = context;
        this.babyModels = babyModels;
    }

    @Override
    public int getCount() {
        return null == babyModels ? 0 : babyModels.size();
    }

    @Override
    public Object getItem(int position) {
        return babyModels.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(this.getCount() == 0){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View emptyView = inflater.inflate(R.layout.mother_list_item, null);
            convertView = emptyView.findViewById(R.id.empty);
        }
        else{
            if(convertView == null){
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.mother_list_item, null);
            }

            TextView txtMessageId  = (TextView) convertView.findViewById(R.id.txtMessageId);
            TextView txtMessageDetails  = (TextView) convertView.findViewById(R.id.txtMessageDetails);

            BabyModel s = babyModels.get(position);

            txtMessageId.setText("Name : " + "");
            txtMessageDetails.setText("\n Age  : " + s.getClient_age()
                    + "\n Phone  : " + s.getClient_phone()
                    + "\n Location:  " + ""
                    + "\n Time :  " + s.getClient_longitude()
            );

        }

        return convertView;
    }

}
