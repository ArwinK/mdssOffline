package com.dewcis.mdss;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.dewcis.mdss.SharedPreferences.SharedPreference;
import com.dewcis.mdss.adapters.HouseholdAdapter;
import com.dewcis.mdss.constants.Constant;
import com.dewcis.mdss.model.DraftList;
import com.dewcis.mdss.model.Household;
import com.dewcis.mdss.utils.AboutDialog;

import java.util.ArrayList;
import java.util.List;

public class HouseholdMembersActivity extends ActionBarActivity {

    List<Household> households;
    HouseholdAdapter householdAdapter;
    RecyclerView household;
    int postpartum, pregnant, children, infant, other_mothers, other_members, survey;
    TextView houseid;

    SharedPreferences housePrefsStatic;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_household_members);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(getResources().getString(R.string.app_name));
        actionBar.setSubtitle("MOH 514 Survey");
        actionBar.setIcon(R.mipmap.ic_launcher);
        actionBar.setHomeButtonEnabled(false);


        // check if is draft disabled
        housePrefsStatic = getSharedPreferences(Constant.HOUSEHOLD_INFORMATION, Context.MODE_PRIVATE);
        boolean bolDraft = housePrefsStatic.getBoolean("draft_boolean", false);

        //boolean bolOffline = housePrefs.getBoolean("offline", false);

        houseid = (TextView) findViewById(R.id.household_id);

        int total = getSurveyPreferences();
        if (total == 0) {
            Toast.makeText(this, "Survey 514 completed", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, Home.class);
            startActivity(intent);
        }

//        if (!bolDraft) {
//            getValues();
//        } else {
        getValues();
        //}

    }

    private int getSurveyPreferences() {

        SharedPreference sharedPreference = new SharedPreference();

        postpartum = sharedPreference.getValue(getApplicationContext(), "postpartum");
        pregnant = sharedPreference.getValue(getApplicationContext(), "pregnant");
        children = sharedPreference.getValue(getApplicationContext(), "children");
        infant = sharedPreference.getValue(getApplicationContext(), "infant");
        survey = sharedPreference.getValue(getApplicationContext(), "survey_id");
        other_mothers = sharedPreference.getValue(getApplicationContext(), "other_mothers");
        other_members = sharedPreference.getValue(getApplicationContext(), "other_members");

        int total = postpartum + pregnant + children + infant + other_members + other_mothers;

        return total;
    }

//    private void getDrafts() {
//        ArrayList<String> searchItems = new ArrayList<String>();
//        String search = "none";
//
//        // Get household key
//        SharedPreferences housePrefs = getSharedPreferences(Constant.HOUSEHOLD_INFORMATION, Context.MODE_PRIVATE);
//        search = housePrefs.getString(Constant.DRAFT_KEY, "");
//
//        // Search through drafts to get all the values in a household
//        ArrayList items = new ArrayList<>();
//        items = DraftActivity.getDraftItems(this);
//
//        householdDraftList = new ArrayList<>();
//        houseAdapter = new HouseAdapter(this, householdDraftList);
//        // filter all drafts using unique house key
//        for (int i = 0; i < items.size(); i++) {
//            if (items.get(i).toString().contains(search)) {
//                HouseholdDraft house = new HouseholdDraft(items.get(i).toString());
//                householdDraftList.add(house);
//                Log.d("keys", items.get(i).toString());
//            }
//        }
//
//        household = (RecyclerView) findViewById(R.id.lstMemberList);
//        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 1);
//        household.setLayoutManager(mLayoutManager);
//        household.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(1), true));
//        household.setItemAnimator(new DefaultItemAnimator());
//        household.setAdapter(houseAdapter);
//
//    }

    private List<Household> getValues() {
        houseid.setText("House Number: " + "KAM/" + survey + "/017");

        Household house = null;
        households = new ArrayList<>();

        // original member list
        postpartum = housePrefsStatic.getInt("postpartum", 0);
        pregnant = housePrefsStatic.getInt("pregnant", 0);
        children = housePrefsStatic.getInt("children", 0);
        infant = housePrefsStatic.getInt("infants", 0);
        other_mothers = housePrefsStatic.getInt("other_mothers", 0);
        other_members = housePrefsStatic.getInt("other_members", 0);

        for (int i = 0; i < postpartum; i++) {
            int y = i + 1;
            house = new Household(survey, "Postpartum", y, 0, "postpartum-" + y + "-" + survey);
            households.add(house);
        }
        for (int i = 0; i < pregnant; i++) {
            int y = i + 1;
            house = new Household(survey, "Pregnant", y, 1, "pregnant-" + y + "-" + survey);
            households.add(house);
        }
        for (int i = 0; i < infant; i++) {
            int y = i + 1;
            house = new Household(survey, "Newborns", y, 3, "newborns-" + y + "-" + survey);
            households.add(house);
        }
        for (int i = 0; i < children; i++) {
            int y = i + 1;
            house = new Household(survey, "Children", y, 2, "children-" + y + "-" + survey);
            households.add(house);
        }
        for (int i = 0; i < other_mothers; i++) {
            int y = i + 1;
            house = new Household(survey, "Other Mothers", y, 4, "other_mothers-" + y + "-" + survey);
            households.add(house);
        }
        for (int i = 0; i < other_members; i++) {
            int y = i + 1;
            house = new Household(survey, "Other Members", y, 5, "other_members-" + y + "-" + survey);
            households.add(house);
        }

        householdAdapter = new HouseholdAdapter(this, households);

        household = (RecyclerView) findViewById(R.id.lstMemberList);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 1);
        household.setLayoutManager(mLayoutManager);
        household.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(1), true));
        household.setItemAnimator(new DefaultItemAnimator());
        household.setAdapter(householdAdapter);

        return households;
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(HouseholdMembersActivity.this);
        builder.setCancelable(false);
        builder.setTitle("Collect data for all members first?");
        builder.setNegativeButton("Okay", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog alert = builder.create();
        alert.show();

    }

    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    /**
     * Converting dp to pixel
     */
    public int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(this, Home.class);

        switch (item.getItemId()) {
            case R.id.action_home:
                startActivity(intent);
                break;
            case R.id.action_about:
                new AboutDialog(this).show();
                break;
            case R.id.action_drafts:
                Intent draft = new Intent(this, DraftList.class);
                startActivity(draft);
                break;
            case R.id.action_logout:
                startActivity(intent);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

}


