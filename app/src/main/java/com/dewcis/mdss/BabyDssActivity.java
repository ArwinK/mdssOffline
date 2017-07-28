package com.dewcis.mdss;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.dewcis.mdss.d_model.BabyModel;
import com.dewcis.mdss.d_model.DraftModel;
import com.dewcis.mdss.d_model.SevenQModel;
import com.dewcis.mdss.d_model.TwentyEightQModel;
import com.dewcis.mdss.databases.DssDetailsDb;
import com.dewcis.mdss.databases.DssDb;
import com.dewcis.mdss.fragments.BabyDssDetails;
import com.dewcis.mdss.fragments.ChildDraftFragment;
import com.dewcis.mdss.fragments.SevenDays;
import com.dewcis.mdss.fragments.TwentyEight;
import com.dewcis.mdss.utils.I_fragmentlistener;

import java.util.UUID;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class BabyDssActivity extends AppCompatActivity implements I_fragmentlistener<BabyModel, SevenQModel, TwentyEightQModel, DraftModel> {
    Button btn_back, btn_next;
    int i = 0;
    private int days;
    private String rand;

    public static final String UID = "uid";
    public static final String RAND_ID = "rand";
    private boolean bol;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baby_dss);

        rand = UUID.randomUUID().toString();

        Bundle extra = getIntent().getExtras();

        if(extra != null){
            //tel_num = extra.getString("mobile");
            //user = extra.getInt("user");
            //patient_name = extra.getString("name");
            //patient_age = extra.getString("age");
            //patient_num = extra.getString("mobile");
            //mother = extra.getString("guardian");

        }

        //Add baby details fragment
        Fragment fragment = new BabyDssDetails();
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_view, fragment).commit();


        btn_back = (Button) findViewById(R.id.btn_back);
        btn_next = (Button) findViewById(R.id.btn_next);
    }

    @Override
    public void onData(BabyModel babyModel) {
        days = (int) babyModel.getClient_days();
        rand = babyModel.getClient_rand();
        DssDetailsDb babyDetailsDb = DssDetailsDb.getInstance(getApplicationContext());
        babyDetailsDb.getWritableDatabase();

        if (!bol) {
            babyDetailsDb.saveBaby(babyModel, rand);
        }

    }

    @Override
    public void ansQuestion(SevenQModel sevenQModel) {
        DssDb sevenDaysDb = DssDb.getInstance(getApplicationContext());
        sevenDaysDb.getWritableDatabase();
        if (!bol) {
            sevenDaysDb.saveSevenDays(sevenQModel, rand);
        }
    }

    @Override
    public void ansQuestionPregnant(TwentyEightQModel twentyEightQModel) {
        DssDb twentyEightDb = DssDb.getInstance(getApplicationContext());
        twentyEightDb.getWritableDatabase();
        if (!bol) {
            twentyEightDb.saveTwentyEight(twentyEightQModel, rand);
        }

    }

    @Override
    public void isDraft(boolean t) {
        bol = t;
    }

    @Override
    public boolean getBol() {
        return bol;
    }

    public void next(View view) {
        switch (i) {
            case 0:
                // Get values from
                BabyDssDetails fragment = (BabyDssDetails) getSupportFragmentManager().findFragmentById(R.id.fragment_view);
                fragment.doTask(rand);
                break;
            case 1:
                if (days == 1) {
                    SevenDays fragment2 = (SevenDays) getSupportFragmentManager().findFragmentById(R.id.fragment_view);
                    fragment2.setClientDetails();
                } else {
                    TwentyEight fragment1 = (TwentyEight) getSupportFragmentManager().findFragmentById(R.id.fragment_view);
                    fragment1.setClientDetails();
                }
        }
        i++;
        change_fragment(i);
    }

    public void back(View view) {

        if (i == 0) {
            Toast.makeText(getApplicationContext(), "Cannot go back", Toast.LENGTH_SHORT).show();
            return;
        }
        if (i == 2) {
            btn_next.setText("Next");
        }
        i = i - 1;
        change_fragment(i);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_draft, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_draft:
                change_fragment(10);
                break;
            case R.id.action_mother:
                Intent intent = new Intent(getApplicationContext(), MotherDssActivity.class);
                startActivity(intent);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void change_fragment(int id) {
        Log.e("Fragment Position", String.valueOf(id));
        Fragment fragchange = null;
        switch (id) {
            case 0:
                fragchange = new BabyDssDetails();
                break;
            case 1:
                if (days == 1) {
                    fragchange = new SevenDays();
                } else {
                    fragchange = new TwentyEight();
                }
                break;
            case 10:
                fragchange = new ChildDraftFragment();
                break;
        }

        if (id == 2) {
            Intent referral = new Intent(this, Form100Dss.class);
            startActivity(referral);

        } else {
            // Replace the current fragment
            if (fragchange != null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_view, fragchange).commit();
            }
        }

    }

    public void showDescription(View v) {
        new SweetAlertDialog(BabyDssActivity.this, SweetAlertDialog.NORMAL_TYPE)
                .setTitleText("Details")
                .setContentText(v.getContentDescription().toString())
                .show();
    }

}