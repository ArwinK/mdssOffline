package com.dewcis.mdss;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.format.Time;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.dewcis.mdss.constants.Constant;
import com.dewcis.mdss.d_model.DraftModel;
import com.dewcis.mdss.d_model.MotherModel;
import com.dewcis.mdss.d_model.PostpartumQModel;
import com.dewcis.mdss.d_model.PregnantQModel;
import com.dewcis.mdss.databases.DssDb;
import com.dewcis.mdss.databases.DssDetailsDb;
import com.dewcis.mdss.fragments.MotherDraftFragment;
import com.dewcis.mdss.fragments.MotherDssDetails;
import com.dewcis.mdss.fragments.PostpartumDss;
import com.dewcis.mdss.fragments.PregnantDss;
import com.dewcis.mdss.utils.I_fragmentlistener;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class MotherDssActivity extends AppCompatActivity
        implements I_fragmentlistener<MotherModel, PostpartumQModel, PregnantQModel, DraftModel> {
    Button btn_back, btn_next;
    int i = 0;
    private int status;
    private String rand;

    private boolean bol;
    private int activity;
    private boolean hasExtras;
    DssDb db;
    Intent intent = null;
    private String mobile;
    private int survey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dss_new);
        db = DssDb.getInstance(getApplicationContext());
        // Create a random key for draft storage and access

        Time time = new Time();
        time.setToNow();

        String myKey = Long.toString(time.toMillis(false));
        rand = myKey.substring(0, 10);

        intent = new Intent(getApplicationContext(), Form100Dss.class);

        // Add the mother details Fragment

        Bundle extra = getIntent().getExtras();
        if (extra != null) {
            hasExtras = true;
            activity = extra.getInt("type");
            rand = extra.getString("rand");
            survey = extra.getInt("survey_id");

            Log.d("xxxxx", survey + "");

            // set dss key
            SharedPreferences sharedPreferences = getSharedPreferences(Constant.DSS_DRAFT, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(Constant.DSS_DRAFT_KEY, rand);
            editor.putBoolean(Constant.DRAFT_BOOLEAN, true);
            editor.apply();

            Toast.makeText(getApplicationContext(), rand, Toast.LENGTH_SHORT).show();
            switch (activity) {
                case 0: {
                    Fragment frag = new PostpartumDss();
                    getSupportFragmentManager().beginTransaction()
                            .add(R.id.fragment_view, frag).commit();
                    break;
                }
                case 1: {
                    Fragment frag = new PregnantDss();
                    getSupportFragmentManager().beginTransaction()
                            .add(R.id.fragment_view, frag).commit();
                    break;
                }
            }
        } else {
            Fragment frag = new MotherDssDetails();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_view, frag).commit();
        }

        btn_back = (Button) findViewById(R.id.btn_back);
        btn_next = (Button) findViewById(R.id.btn_next);
    }

    @Override
    public void onData(MotherModel clientModel) {
        mobile = clientModel.getClient_phone();
        status = clientModel.getClient_status();
        DssDetailsDb db = DssDetailsDb.getInstance(getApplicationContext());
        db.getWritableDatabase();
        if (!bol) {
            db.saveMother(clientModel, rand);
        }
    }

    @Override
    public void ansQuestion(PostpartumQModel postpartumQModel) {

        if(postpartumQModel.get_toDss()){
            intent.putExtra("mobile", mobile);
            intent.putExtra("trace", String.valueOf(survey));
            intent.putExtra("pos",  postpartumQModel.get_clientNumber());
            intent.putExtra("index", 11);
            intent.putExtra("mobile514", "514");
        } else {
            postpartumQModel.eventSelector(this, "514");
            finish();
        }
        db.getWritableDatabase();
        if (!bol) {
            db.savePostpartum(postpartumQModel, rand);
        }
    }

    @Override
    public void ansQuestionPregnant(PregnantQModel pregnantQModel) {
//        if(pregnantQModel.get_toDss()){
//            intent.putExtra("mobile", mobile);
//            intent.putExtra("trace", rand);
//            intent.putExtra("pos", postpartumQModel.get_clientNumber());
//            intent.putExtra("index", 11);
//        }
        db.getWritableDatabase();
        if (!bol) {
            db.savePregnant(pregnantQModel, rand);
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

        if (hasExtras) {
            switch (activity) {
                case 0:
                    PostpartumDss fragment2 = (PostpartumDss) getSupportFragmentManager().findFragmentById(R.id.fragment_view);
                    fragment2.context514();
                    fragment2.setClientDetails(rand);
                    break;
                case 1:
                    PregnantDss fragment1 = (PregnantDss) getSupportFragmentManager().findFragmentById(R.id.fragment_view);
                    fragment1.setClientDetails(rand);
                    break;
            }
            activity = activity + 2;
            change_fragment_514(activity);
        } else {
            switch (i) {
                case 0:
                    // Get values from
                    MotherDssDetails fragment = (MotherDssDetails) getSupportFragmentManager().findFragmentById(R.id.fragment_view);
                    fragment.doTask(rand);
                    break;
                case 1:
                    if (status == 2) {
                        PregnantDss fragment2 = (PregnantDss) getSupportFragmentManager().findFragmentById(R.id.fragment_view);
                        fragment2.setClientDetails(rand);
                    } else {
                        PostpartumDss fragment1 = (PostpartumDss) getSupportFragmentManager().findFragmentById(R.id.fragment_view);
                        fragment1.setClientDetails(rand);
                    }
                    break;
            }
            i++;
            change_fragment(i);
        }

    }

    private void change_fragment_514(int i) {
        Fragment fragchange = null;
        switch (i) {
            case 0:
                fragchange = new PostpartumDss();
                break;
            case 1:
                fragchange = new PregnantDss();
                break;
            case 10:
                fragchange = new MotherDraftFragment();
                break;
        }

        if (activity == 2 || activity == 3) {
            // replace with save function
            startActivity(intent);
            finish();
        } else {
            // Replace the current fragment
            if (fragchange != null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_view, fragchange).commit();
            }
        }
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
        }

        return super.onOptionsItemSelected(item);
    }

    public void change_fragment(int id) {
        Log.e("Fragment Position", String.valueOf(id));
        Fragment fragchange = null;
        switch (id) {
            case 0:
                fragchange = new MotherDssDetails();
                break;
            case 1:
                if (status == 1) {
                    fragchange = new PostpartumDss();
                } else {
                    fragchange = new PregnantDss();
                }
                break;
            case 10:
                fragchange = new MotherDraftFragment();
                break;
        }

        if (id == 2) {
            // replace with save function
            startActivity(intent);

        } else {
            // Replace the current fragment
            if (fragchange != null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_view, fragchange).commit();
            }
        }

    }

    public void showDescription(View v) {
        //Toast.makeText(getApplicationContext(), v.getContentDescription().toString(), Toast.LENGTH_SHORT).show();
        new SweetAlertDialog(MotherDssActivity.this, SweetAlertDialog.NORMAL_TYPE)
                .setTitleText("Details")
                .setContentText(v.getContentDescription().toString())
                .show();
    }

//    public void save(final Context context, final JSONObject details, String whereTo, final int index) {
//        final ProgressDialog pDialog = new ProgressDialog(MotherDssActivity.this);
//        pDialog.setMessage("Please Wait...");
//        pDialog.setCancelable(false);
//
//        MUtil.showProgressDialog(pDialog);
//        String url = "";
//        url = MApplication.url + whereTo;
//
//        Log.i(MApplication.TAG, MainActivity.class.getName() + " : Details " + details.toString());
//        JsonObjectRequest mVerifyReq = new JsonObjectRequest(Request.Method.POST, url, details,
//                new Response.Listener<JSONObject>() {
//                    public void onResponse(JSONObject response) {
//                        Success success = Success.makeFromJson(response);
//                        if (doLog) Log.i(TAG, CTAG + response.toString());
//                        MUtil.hideProgressDialog(pDialog);
//
//                        if (success.isSuccess()) {
//
//                            Crouton.makeText(MotherDssActivity.this, success.getMessage(), Style.CONFIRM).show();
//
//                            if (toDss) {
//                                Intent intent = new Intent(getApplicationContext(), Form100Activity.class);
//                                intent.putExtra("mobile", mobile100);
//                                intent.putExtra("pos", number); //mothers number
//                                intent.putExtra("trace", String.valueOf(survey));
//                                intent.putExtra("index", index); //dss option
//                                intent.putExtra("mobile514", my514);
//                                startActivity(intent);
//                                finish();
//
//                            } else if (!toDss) {
//                                eventSelector();
//                            }
//
//                        } else {
//                            Crouton.makeText(MotherDssActivity.this, success.getMessage(), Style.ALERT).show();
//                        }
//                    }
//
//                    ;
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Log.e(MApplication.TAG, error.toString());
//                        MUtil.hideProgressDialog(pDialog);
//                        Crouton.makeText(MotherDssActivity.this, VolleyErrorHelper.getMessage(error, context), Style.ALERT).show();
//                    }
//                }) {
//        };
//        mVerifyReq.setRetryPolicy(new DefaultRetryPolicy(5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        MApplication.getInstance().addToRequestQueue(mVerifyReq, "survey");
//    }
}
