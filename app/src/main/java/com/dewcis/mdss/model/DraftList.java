package com.dewcis.mdss.model;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.dewcis.mdss.HouseHoldActivity;
import com.dewcis.mdss.MainActivity;
import com.dewcis.mdss.R;
import com.dewcis.mdss.constants.Constant;
import com.dewcis.mdss.databases.HouseholdDb;
import com.dewcis.mdss.utils.DraftActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Arwin Kish on 5/17/2017.
 */
public class DraftList extends ActionBarActivity {
    ArrayList items;
    ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_view_draft);

        items = new ArrayList<>();

        //items = DraftActivity.getDraftItems(this);

        mListView = (ListView) findViewById(R.id.draft_view);

        SharedPreferences sharedPreferences = getSharedPreferences(Constant.HOUSEHOLD_INFORMATION, Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();


        final HouseholdDb householdDb = HouseholdDb.getsInstance(getApplicationContext());
        householdDb.getReadableDatabase();
        items = householdDb.getDataAll();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        mListView.setAdapter(adapter);

        Toast.makeText(getApplicationContext(), items.toString(), Toast.LENGTH_SHORT).show();

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String key = mListView.getAdapter().getItem(i).toString();
                String value = householdDb.getDataSpecific(key);
                Intent intent = new Intent(getApplicationContext(), HouseHoldActivity.class);
                //Toast.makeText(getApplicationContext(), value, Toast.LENGTH_SHORT).show();
//                if(value.contains("postpartum") && value.contains("x5")){
//                    type = 0;
//                }else if(value.contains("pregnant") && value.contains("x5")){
//                    type = 1;
//                }else if(value.contains("children") && value.contains("x5")){
//                    type = 2;
//                }else if(value.contains("newborns") && value.contains("x5")){
//                    type = 3;
//                }else if(value.contains("other_mothers") && value.contains("x5")){
//                    type = 4;
//                }else if(value.contains("other_members") && value.contains("x5")){
//                    type = 5;
//                }else {
//                    value = "";
//                }
                if(!value.equals("")){
                    intent.putExtra("draft_index", i);
                    intent.putExtra("draft_key", key);
                    intent.putExtra("draft_value", value);
                    startActivity(intent);

                }


            }
        });

        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                DraftActivity.removeDraft(DraftList.this, i);
                ((BaseAdapter) mListView.getAdapter()).notifyDataSetChanged();
                return true;
            }
        });


    }
}
