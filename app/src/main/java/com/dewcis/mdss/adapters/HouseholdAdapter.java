package com.dewcis.mdss.adapters;

/**
 * Created by Arwin Kish on 12/22/2016.
 */


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import com.dewcis.mdss.MainActivity;
import com.dewcis.mdss.R;
import com.dewcis.mdss.constants.Constant;
import com.dewcis.mdss.model.Household;

import java.util.List;

public class HouseholdAdapter extends RecyclerView.Adapter<HouseholdAdapter.MyViewHolder> {
    private Context mContext;
    private List<Household> householdList;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, count, content, content_main;
        public CardView cardView;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            count = (TextView) view.findViewById(R.id.count);
            content = (TextView) view.findViewById(R.id.content);
            content_main = (TextView) view.findViewById(R.id.content_description);
            cardView = (CardView) view.findViewById(R.id.card_view);

        }
    }

    public HouseholdAdapter(Context mContext, List<Household> householdList) {
        this.mContext = mContext;
        this.householdList = householdList;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.candidate_card, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final Household household = householdList.get(position);
        holder.content.setText(household.getName() + " No: " + household.getNumOfMember());

        final int survey = household.getSurvey();
        final int type = household.getType();
        final String key = household.getKey();
        final int no = household.getNumOfMember();

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, MainActivity.class);
                intent.putExtra("type", type);
                intent.putExtra(Constant.DRAFT_KEY, key);
                intent.putExtra(Constant.DRAFT_INDEX, position);
                intent.putExtra("survey_id", survey);
                intent.putExtra("member_no", no);
                mContext.startActivity(intent);


            }
        });
        holder.content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, MainActivity.class);
                intent.putExtra("type", type);
                intent.putExtra(Constant.DRAFT_KEY, key);
                intent.putExtra(Constant.DRAFT_INDEX, position);
                intent.putExtra("survey_id", survey);
                intent.putExtra(Constant.MEMBER_NO, no);
                mContext.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return householdList.size();
    }
}
