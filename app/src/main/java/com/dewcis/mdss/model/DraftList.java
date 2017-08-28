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
import com.dewcis.mdss.R;
import com.dewcis.mdss.constants.Constant;
import com.dewcis.mdss.databases.HouseholdDb;

import java.util.ArrayList;

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

        mListView = (ListView) findViewById(R.id.draft_view);

        SharedPreferences sharedPreferences = getSharedPreferences(Constant.HOUSEHOLD_INFORMATION, Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();


        final HouseholdDb householdDb = HouseholdDb.getsInstance(getApplicationContext());
        householdDb.getReadableDatabase();
        items = householdDb.getDataAll("sectionOne");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        mListView.setAdapter(adapter);

        Toast.makeText(getApplicationContext(), items.toString(), Toast.LENGTH_SHORT).show();

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String key = mListView.getAdapter().getItem(i).toString();
                String value = householdDb.getDataSpecific(key, HouseholdDb.TABLE_LOGIN);
                Intent intent = new Intent(getApplicationContext(), HouseHoldActivity.class);
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
                //DraftActivity.removeDraft(DraftList.this, i);
                ((BaseAdapter) mListView.getAdapter()).notifyDataSetChanged();
                return true;
            }
        });


    }
}
