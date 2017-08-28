package com.dewcis.mdss;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.text.InputFilter;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.dewcis.mdss.SharedPreferences.SharedPreferences514;
import com.dewcis.mdss.constants.Constant;
import com.dewcis.mdss.databases.HouseholdDb;
import com.dewcis.mdss.fragments.BabyDssDetails;
import com.dewcis.mdss.model.MSession;
import com.dewcis.mdss.model.Success;
import com.dewcis.mdss.utils.AboutDialog;
import com.dewcis.mdss.utils.ExpandablePanel;
import com.dewcis.mdss.utils.MUtil;
import com.dewcis.mdss.volley.VolleyErrorHelper;
import com.rengwuxian.materialedittext.MaterialEditText;

import org.json.JSONException;
import org.json.JSONObject;

import cn.pedant.SweetAlert.SweetAlertDialog;
import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;


/**
 * Created by henriquedn on 4/14/15.
 * henrydkm@gmail.com
 * henry.maina@dewcis.com
 */

public class MainActivity extends ActionBarActivity implements ExpandablePanel.OnExpandListener {
    MSession session;
    String TAG = MApplication.TAG;
    static boolean doLog = MApplication.LOGDEBUG;
    static String CTAG = MainActivity.class.getName() + " : ";
    boolean isEdit = false;
    int postpartum = 0, pregnant = 0, infants = 0, children = 0, other_women = 0, other_members;
    ExpandablePanel panelMotherInfo, panelChildInfo, panelReferralsInfo, panelDefaultersInfo;
    public MaterialEditText txtHouseMobile, txtHouseholdNum, txtHouseholdMember, txtRemarks,
            txtName;//txtVillageName
    TextView lblDisplayUser, lblReturnReason, lblReturnTitle;
    Button btnSubmit;
    int survey = 0;

    public static final String HOUSEHOLD_INFORMATION = "house_hold";

    //Basic Information
    Spinner spnSublocation, spnVillage;

    LinearLayout mother_ln, child_in;
    SharedPreferences514 shared514;

    //mother information
    Spinner spnAgeGroup, spnPregnant, spnF, spnG, spnH, spnI, spnJ, spnK;
    //child information
    Spinner spnChildGender, spnL, spnM, spnN, spnO;
    //referral info
    Spinner spnP, spnQ, spnR, spnS, spnT, spnU, spnV, spnW, spnX;
    //defaulters info
    Spinner spnZ, spnAA, spnAB, spnAC, spnAL;

    int category;
    int survey_status = 0;

    JSONObject jMotherInfo = new JSONObject();
    JSONObject jReferralInfo = new JSONObject();
    JSONObject jDefaultersInfo = new JSONObject();
    JSONObject jChildInfo = new JSONObject();
    JSONObject jSurveyMother = new JSONObject();
    JSONObject categoryInfo = new JSONObject();

    String draft_key = null;
    int draft_index = 0;
    private boolean isDraft;
    private JSONObject draftedJson;
    private String storeKey;
    private int member_no;
    HouseholdDb householdDb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        session = new MSession(getApplicationContext());

        if (!session.isLoggedIn()) {
            session.logout(this);
        }

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            category = extras.getInt("type");
            member_no = extras.getInt(Constant.MEMBER_NO);
        }

        householdDb = HouseholdDb.getsInstance(getApplicationContext());
        householdDb.getReadableDatabase();

        lblDisplayUser = (TextView) findViewById(R.id.lblDisplayUser);
        lblReturnReason = (TextView) findViewById(R.id.lblReturnReason);
        lblReturnTitle = (TextView) findViewById(R.id.lblReturnTitle);

        child_in = (LinearLayout) findViewById(R.id.child_info);
        mother_ln = (LinearLayout) findViewById(R.id.mother_info);
        lblReturnTitle.setVisibility(View.GONE);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(getResources().getString(R.string.app_name));
        actionBar.setSubtitle("MOH 514");
        actionBar.setIcon(R.mipmap.ic_launcher);
        actionBar.setHomeButtonEnabled(false);

        lblReturnReason.setText("");

        panelMotherInfo = (ExpandablePanel) findViewById(R.id.panelMotherInfo);
        panelChildInfo = (ExpandablePanel) findViewById(R.id.panelChildInfo);
        panelReferralsInfo = (ExpandablePanel) findViewById(R.id.panelReferralsInfo);
        panelDefaultersInfo = (ExpandablePanel) findViewById(R.id.panelDefaultersInfo);

        btnSubmit = (Button) findViewById(R.id.btnSubmit);

        panelMotherInfo.setOnExpandListener(this);
        panelChildInfo.setOnExpandListener(this);
        panelReferralsInfo.setOnExpandListener(this);
        panelDefaultersInfo.setOnExpandListener(this);

        spnSublocation = (Spinner) findViewById(R.id.spnSublocation);
        spnVillage = (Spinner) findViewById(R.id.spnVillage);

        txtHouseholdNum = (MaterialEditText) findViewById(R.id.txtHouseholdNum);
        txtHouseholdMember = (MaterialEditText) findViewById(R.id.txtHouseholdMember);
        txtRemarks = (MaterialEditText) findViewById(R.id.txtRemarks);
        txtHouseMobile = (MaterialEditText) findViewById(R.id.txtHouseholdMobile);
        txtName = (MaterialEditText) findViewById(R.id.txtName);

        txtName.setFilters(new InputFilter[]{new InputFilter.AllCaps()});

        //mother information
        spnAgeGroup = (Spinner) findViewById(R.id.spnAgeGroup);
        spnPregnant = (Spinner) findViewById(R.id.spnPregnant);
        spnF = ((Spinner) findViewById(R.id.spnF));
        spnG = (Spinner) findViewById(R.id.spnG);
        spnH = (Spinner) findViewById(R.id.spnH);
        spnI = (Spinner) findViewById(R.id.spnI);
        spnJ = (Spinner) findViewById(R.id.spnJ);
        spnK = (Spinner) findViewById(R.id.spnK);

        //child information
//        spnAgeDays =  (Spinner) findViewById(R.id.spnAgeDays);
        spnChildGender = (Spinner) findViewById(R.id.spnChildGender);
        spnL = (Spinner) findViewById(R.id.spnL);
        spnM = (Spinner) findViewById(R.id.spnM);
        spnN = (Spinner) findViewById(R.id.spnN);
        spnO = (Spinner) findViewById(R.id.spnO);

        //referal info
        spnP = (Spinner) findViewById(R.id.spnP);
        spnQ = (Spinner) findViewById(R.id.spnQ);
        spnR = (Spinner) findViewById(R.id.spnR);
        spnS = (Spinner) findViewById(R.id.spnS);
        spnT = (Spinner) findViewById(R.id.spnT);
        spnU = (Spinner) findViewById(R.id.spnU);
        spnV = (Spinner) findViewById(R.id.spnV);
        spnW = (Spinner) findViewById(R.id.spnW);
        spnX = (Spinner) findViewById(R.id.spnX);

        //defaulters info
        spnZ = (Spinner) findViewById(R.id.spnZ);
        spnAA = (Spinner) findViewById(R.id.spnAA);
        spnAB = (Spinner) findViewById(R.id.spnAB);
        spnAC = (Spinner) findViewById(R.id.spnAC);
        spnAL = (Spinner) findViewById(R.id.spnAL);

        if (category == 0) {
            lblDisplayUser.setText("Category: Postpartum Mother");
            child_in.setVisibility(View.GONE);
            autoSelectionPostpartum();
        } else if (category == 1) {
            lblDisplayUser.setText("Category: Pregnant Mother");
            child_in.setVisibility(View.GONE);
            autoSelectionPregnant();
        } else if (category == 2) {
            lblDisplayUser.setText("Category: Child Below 5 years");
            mother_ln.setVisibility(View.GONE);
            autoSelectionChild();
        } else if (category == 3) {
            lblDisplayUser.setText("Category: New born");
            mother_ln.setVisibility(View.GONE);
            autoSelectionInfant();
        } else if (category == 4) {
            lblDisplayUser.setText("Category: Other mothers");
            child_in.setVisibility(View.GONE);
            autoSelectionOtherWomen();
        } else {
            lblDisplayUser.setText("Category: Other Members");
            child_in.setVisibility(View.GONE);
            mother_ln.setVisibility(View.GONE);
            autoSelectionOther();
        }

        SharedPreferences sharedpreferences = getSharedPreferences(DssActivity.DSS_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.remove("age");
        editor.remove("mobile");
        editor.apply();

        draft();

    }

    private void draft() {

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            draft_key = extras.getString(Constant.DRAFT_KEY);
            draft_index = extras.getInt(Constant.DRAFT_INDEX);
            member_no = extras.getInt(Constant.MEMBER_NO);
            survey = extras.getInt("survey_id");
        }

        if (draft_key != null) {

            String content = householdDb.getDataSpecific(draft_key, HouseholdDb.TABLE_SURVEY);
            JSONObject draftValue;

            if (content != null && !content.equals("")) {
                isDraft = true;
                try {
                    draftValue = new JSONObject(content);
                    if (category == 2 || category == 3) {
                        JSONObject childInfo = draftValue.getJSONObject("childInfo");
                        if (!childInfo.toString().equals("{}")) {
                            spnChildGender.setSelection(childInfo.getInt("1") - 3);
                            spnL.setSelection(childInfo.getInt("2"));
                            spnM.setSelection(childInfo.getInt("3"));
                            spnN.setSelection(childInfo.getInt("4"));
                            spnO.setSelection(childInfo.getInt("5"));
                            txtName.setText(childInfo.getString("6"));
                        }
                    }
                    JSONObject referralInfo = draftValue.getJSONObject("referralInfo");
                    JSONObject defaultersInfo = draftValue.getJSONObject("defaultersInfo");

                    if (category == 0 || category == 1 || category == 4) {
                        JSONObject motherInfo = draftValue.getJSONObject("motherInfo");

                        if (!motherInfo.toString().equals("{}")) {

                            spnPregnant.setSelection(motherInfo.getInt("1"));
                            spnF.setSelection(motherInfo.getInt("2"));
                            spnG.setSelection(motherInfo.getInt("3"));
                            spnH.setSelection(motherInfo.getInt("4"));
                            spnI.setSelection(motherInfo.getInt("5"));
                            spnJ.setSelection(motherInfo.getInt("6"));
                            spnK.setSelection(motherInfo.getInt("7"));
                            spnAgeGroup.setSelection(motherInfo.getInt("8"));
                            txtName.setText(motherInfo.getString("9"));

                        }
                    }

                    spnP.setSelection(referralInfo.getInt("1"));
                    spnQ.setSelection(referralInfo.getInt("2"));
                    spnR.setSelection(referralInfo.getInt("3"));
                    spnS.setSelection(referralInfo.getInt("4"));
                    spnT.setSelection(referralInfo.getInt("5"));
                    spnU.setSelection(referralInfo.getInt("6"));
                    spnV.setSelection(referralInfo.getInt("7"));
                    spnW.setSelection(referralInfo.getInt("8"));
                    spnX.setSelection(referralInfo.getInt("9"));

                    spnZ.setSelection(defaultersInfo.getInt("1"));
                    spnAA.setSelection(defaultersInfo.getInt("2"));
                    spnAB.setSelection(defaultersInfo.getInt("3"));
                    spnAC.setSelection(defaultersInfo.getInt("4"));
                    spnAL.setSelection(defaultersInfo.getInt("5"));

                    setDraftedJson(draftValue);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        }

    }

    private void setDraftedJson(JSONObject draftedJson) {
        this.draftedJson = draftedJson;
    }

    @Override
    protected void onResume() {
        super.onResume();
        //this.doubleBackToExitPressedOnce = false;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_logout:
                session.logout(this);
                break;
            case R.id.action_about:
                new AboutDialog(this).show();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onExpand(View handle, View content) {

        TextView header = (TextView) handle;
        header.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_expand_less_white_18dp, 0);
    }

    @Override
    public void onCollapse(View handle, View content) {
        TextView header = (TextView) handle;
        header.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_expand_more_white_18dp, 0);
    }

    public void showDescription(View v) {
        //Toast.makeText(getApplicationContext(), v.getContentDescription().toString(), Toast.LENGTH_SHORT).show();
        new SweetAlertDialog(MainActivity.this, SweetAlertDialog.NORMAL_TYPE)
                .setTitleText("Details")
                .setContentText(v.getContentDescription().toString())
                .show();
    }

    public void save(final Context context, final JSONObject details) {

        String url = "";
        if (isEdit) {
            url = MApplication.url + "editSurvey";
        } else {
            url = MApplication.url + "survey_category";
        }

        Log.i(MApplication.TAG, MainActivity.class.getName() + " : Details " + details.toString());
        JsonObjectRequest mVerifyReq = new JsonObjectRequest(Request.Method.POST, url, details,
                new Response.Listener<JSONObject>() {
                    public void onResponse(JSONObject response) {
                        Success success = Success.makeFromJson(response);
                        if (doLog) Log.i(TAG, CTAG + response.toString());
                        Log.i(TAG, LoginActivity.class.getName() + "Response : " + response.toString() + "\nSuccess : " + success.isSuccess() + "\nMessage : " + success.getMessage());
                        Toast.makeText(context, "success", Toast.LENGTH_SHORT).show();
                        if (success.isSuccess()) {

                            if (isDraft) {
                                //clearIndexedDraft(draft_key, draft_index);
                            }
                            Crouton.makeText(MainActivity.this, success.getMessage(), Style.CONFIRM).show();
                            if ((category == 2 && children != 0) || (category == 5 && other_members != 0) ||
                                    (category == 4 && other_women != 0)) {
                                restart();
                            }
                            finish();

                        } else {
                            Crouton.makeText(MainActivity.this, success.getMessage(), Style.ALERT).show();
                        }
                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(MApplication.TAG, error.toString());
                        Crouton.makeText(MainActivity.this, VolleyErrorHelper.getMessage(error, context), Style.ALERT).show();
                    }
                }) {
        };
        mVerifyReq.setRetryPolicy(new DefaultRetryPolicy(5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MApplication.getInstance().addToRequestQueue(mVerifyReq, "survey");
    }

    public void showInvalidMessage(int message) {
        new SweetAlertDialog(MainActivity.this, SweetAlertDialog.WARNING_TYPE)
                .setContentText("Select If " + getResources().getString(message)).show();
    }


    public void setSpinnerSelectedIndex(int index, Spinner[] spinners) {
        for (Spinner s : spinners) {
            s.setVisibility(View.GONE);
            s.setSelection(index);
        }
    }

    @Override
    public void onBackPressed() {

    }

    public void submitInformation() throws JSONException {

        jSurveyMother = new JSONObject();
        categoryInfo = new JSONObject();

        SharedPreferences storeDraft = getSharedPreferences(Constant.HOUSEHOLD_INFORMATION, Context.MODE_PRIVATE);
        storeKey = storeDraft.getString("key", "");

        if (isDraft) {
            Intent intent = new Intent();
            if (category == 0) {
                postpartum++;
                intent = new Intent(getApplicationContext(), MotherDssActivity.class);
            } else if (category == 1) {
                pregnant++;
                intent = new Intent(getApplicationContext(), MotherDssActivity.class);
            } else if (category == 2) {
                children++;
                intent = new Intent(getApplicationContext(), Home.class);
            } else if (category == 3) {
                infants++;
                intent = new Intent(getApplicationContext(), BabyDssDetails.class);
            } else if (category == 4) {
                other_members++;
                intent = new Intent(getApplicationContext(), Home.class);
            }

            if (MUtil.isNetworkConnected(getApplicationContext())) {
                //save(getApplicationContext(), getDraftedJson());
                //intent.putExtra("type", category);
                //intent.putExtra("pre", "514");

                householdDb.save(jSurveyMother.toString(), draft_key, HouseholdDb.TABLE_SURVEY);
                intent.putExtra("type", category);
                intent.putExtra("pre", "514");
                intent.putExtra("rand", draft_key);
            } else {

                householdDb.save(jSurveyMother.toString(), draft_key, HouseholdDb.TABLE_SURVEY);

                intent = new Intent(getApplicationContext(), MotherDssActivity.class);
                intent.putExtra("type", category);
                intent.putExtra("pre", "514");
                intent.putExtra("rand", draft_key);
            }
            startActivity(intent);

        } else {

            try {
                jReferralInfo.put("1", ((Spinner) findViewById(R.id.spnP)).getSelectedItemId());
                jReferralInfo.put("2", ((Spinner) findViewById(R.id.spnQ)).getSelectedItemPosition());
                jReferralInfo.put("3", ((Spinner) findViewById(R.id.spnR)).getSelectedItemPosition());
                jReferralInfo.put("4", ((Spinner) findViewById(R.id.spnS)).getSelectedItemPosition());
                jReferralInfo.put("5", ((Spinner) findViewById(R.id.spnT)).getSelectedItemPosition());
                jReferralInfo.put("6", ((Spinner) findViewById(R.id.spnU)).getSelectedItemPosition());
                jReferralInfo.put("7", ((Spinner) findViewById(R.id.spnV)).getSelectedItemPosition());
                jReferralInfo.put("8", ((Spinner) findViewById(R.id.spnW)).getSelectedItemPosition());
                jReferralInfo.put("9", ((Spinner) findViewById(R.id.spnX)).getSelectedItemPosition());

                jDefaultersInfo.put("1", ((Spinner) findViewById(R.id.spnZ)).getSelectedItemPosition());
                jDefaultersInfo.put("2", ((Spinner) findViewById(R.id.spnAA)).getSelectedItemPosition());
                jDefaultersInfo.put("3", ((Spinner) findViewById(R.id.spnAB)).getSelectedItemPosition());
                jDefaultersInfo.put("4", ((Spinner) findViewById(R.id.spnAC)).getSelectedItemPosition());
                jDefaultersInfo.put("5", ((Spinner) findViewById(R.id.spnAL)).getSelectedItemPosition());

                jMotherInfo.put("1", spnPregnant.getSelectedItemId());
                jMotherInfo.put("2", spnF.getSelectedItemId());
                jMotherInfo.put("3", spnG.getSelectedItemId());
                jMotherInfo.put("4", spnH.getSelectedItemId());
                jMotherInfo.put("5", spnI.getSelectedItemId());
                jMotherInfo.put("6", spnJ.getSelectedItemId());
                jMotherInfo.put("7", spnK.getSelectedItemId());
                jMotherInfo.put("8", spnAgeGroup.getSelectedItemId());
                jMotherInfo.put("9", txtName.getText().toString());

                jChildInfo.put("1", ((Spinner) findViewById(R.id.spnChildGender)).getSelectedItemPosition() + 3);
                jChildInfo.put("2", ((Spinner) findViewById(R.id.spnL)).getSelectedItemId());
                jChildInfo.put("3", ((Spinner) findViewById(R.id.spnM)).getSelectedItemPosition());
                jChildInfo.put("4", ((Spinner) findViewById(R.id.spnN)).getSelectedItemPosition());
                jChildInfo.put("5", ((Spinner) findViewById(R.id.spnO)).getSelectedItemPosition());
                jChildInfo.put("6", txtName.getText().toString());

            } catch (JSONException e) {
                e.printStackTrace();
            }

            if (category == 0 && member_no != 0) {
                function2();
            } else if (category == 1 && member_no != 0) {
                function2();
            } else if (category == 4 && member_no != 0) {
                // different logic
                function2();
            } else if (category == 5 && other_members != 0) {
                function();
                if (other_members == 0) {
                    btnSubmit.setText("Continue");
                    Intent intent = new Intent(getApplicationContext(), HouseholdMembersActivity.class);
                    startActivity(intent);
                }

            } else if (category == 2 && children != 0) {
                function();
                if (children == 0) {
                    btnSubmit.setText("Continue");
                    Intent intent = new Intent(getApplicationContext(), HouseholdMembersActivity.class);
                    startActivity(intent);
                }

            } else if (category == 3 && infants != 0) {

                try {
                    categoryInfo.put("type", category);
                    categoryInfo.put("number", member_no);
                    categoryInfo.put("survey_id", survey);
                    categoryInfo.put("survey_status", survey_status);
                    jSurveyMother.put("categoryInfo", categoryInfo);
                    jSurveyMother.put("childInfo", jChildInfo);
                    jSurveyMother.put("referralInfo", jReferralInfo);
                    jSurveyMother.put("defaultersInfo", jDefaultersInfo);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Intent intent = null;

//                if (MUtil.isNetworkConnected(getApplicationContext())) {
//                    save(getApplicationContext(), jSurveyMother);
//                    intent = new Intent(getApplicationContext(), BabyDssActivity.class);
//                } else {

                householdDb.save(jSurveyMother.toString(), draft_key, HouseholdDb.TABLE_SURVEY);

                intent = new Intent(getApplicationContext(), BabyDssActivity.class);
//                }


                intent.putExtra("rand", draft_key);
                intent.putExtra("type", category);
                intent.putExtra("pre", "514");
                startActivity(intent);
            }

        }

    }

    public void newClick(View view) {

        if (TextUtils.isEmpty(txtName.getText().toString())) {
            Toast.makeText(getApplicationContext(), "Enter members name", Toast.LENGTH_LONG).show();
            txtName.setError("Required");
            txtName.requestFocus();
            return;
        }

        if (category == 0 || category == 1 || category == 4) {

            //mother information
            //spnPregnant , spnF, spnG, spnH, spnI, spnJ, spnK;

            if (spnPregnant.getSelectedItemPosition() == 0) {
                showInvalidMessage(R.string.pregnant);
                return;
            }
            if (spnF.getSelectedItemPosition() == 0) {
                showInvalidMessage(R.string.f_name);
                return;
            }
            if (spnG.getSelectedItemPosition() == 0) {
                showInvalidMessage(R.string.g_name);
                return;
            }
            if (spnH.getSelectedItemPosition() == 0) {
                showInvalidMessage(R.string.h_name);
                return;
            }
            if (spnI.getSelectedItemPosition() == 0) {
                showInvalidMessage(R.string.i_name);
                return;
            }
            if (spnJ.getSelectedItemPosition() == 0) {
                showInvalidMessage(R.string.j_name);
                return;
            }
            if (spnK.getSelectedItemPosition() == 0) {
                showInvalidMessage(R.string.k_name);
                return;
            }
        }

        if (category == 2 || category == 3) {

            //child information
            //spnChildGender, spnL, spnM, spnN, spnO;
            if (spnChildGender.getSelectedItemPosition() == 0) {
                showInvalidMessage(R.string.gender);
                return;
            }
            if (spnL.getSelectedItemPosition() == 0) {
                showInvalidMessage(R.string.l_name);
                return;
            }
            if (spnM.getSelectedItemPosition() == 0) {
                showInvalidMessage(R.string.m_name);
                return;
            }
            if (spnN.getSelectedItemPosition() == 0) {
                showInvalidMessage(R.string.n_name);
                return;
            }
            if (spnO.getSelectedItemPosition() == 0) {
                showInvalidMessage(R.string.o_name);
                return;
            }
        }

        //referal info
        //spnP, spnQ, spnR, spnS, spnT, spnU, spnV, spnW, spnX, spnY_a, spnY_b, spnY_c, spnY_d ;

        if (spnP.getSelectedItemPosition() == 0) {
            showInvalidMessage(R.string.p_name);
            return;
        }
        if (spnQ.getSelectedItemPosition() == 0) {
            showInvalidMessage(R.string.q_name);
            return;
        }
        if (spnR.getSelectedItemPosition() == 0) {
            showInvalidMessage(R.string.r_name);
            return;
        }
        if (spnS.getSelectedItemPosition() == 0) {
            showInvalidMessage(R.string.s_name);
            return;
        }
        if (spnT.getSelectedItemPosition() == 0) {
            showInvalidMessage(R.string.t_name);
            return;
        }
        if (spnU.getSelectedItemPosition() == 0) {
            showInvalidMessage(R.string.u_name);
            return;
        }
        if (spnV.getSelectedItemPosition() == 0) {
            showInvalidMessage(R.string.v_name);
            return;
        }
        if (spnW.getSelectedItemPosition() == 0) {
            showInvalidMessage(R.string.w_name);
            return;
        }
        if (spnX.getSelectedItemPosition() == 0) {
            showInvalidMessage(R.string.x_name);
            return;
        }

        //defaulters info
        // spnZ, spnAA, spnAB, spnAC;
        if (spnZ.getSelectedItemPosition() == 0) {
            showInvalidMessage(R.string.z_name);
            return;
        }
        if (spnAA.getSelectedItemPosition() == 0) {
            showInvalidMessage(R.string.aa_name);
            return;
        }
        if (spnAB.getSelectedItemPosition() == 0) {
            showInvalidMessage(R.string.ab_name);
            return;
        }
        if (spnAC.getSelectedItemPosition() == 0) {
            showInvalidMessage(R.string.ac_name);
            return;
        }
        if (spnAL.getSelectedItemPosition() == 0) {
            showInvalidMessage(R.string.al_name);
            return;
        }

        try {
            submitInformation();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void autoSelectionInfant() {
        setSpinnerSelectedIndex(3, new Spinner[]{spnM, spnN, spnO, spnP, spnQ, spnR, spnU, spnZ, spnX, spnS});
    }

    public void autoSelectionChild() {
        setSpinnerSelectedIndex(3, new Spinner[]{spnP, spnQ, spnR, spnS, spnX, spnZ});
    }

    public void autoSelectionPregnant() {
        setSpinnerSelectedIndex(3, new Spinner[]{spnG, spnH, spnI, spnJ, spnK, spnR, spnS,
                spnT, spnU, spnX, spnAA, spnAL});
    }

    public void autoSelectionPostpartum() {
        setSpinnerSelectedIndex(3, new Spinner[]{spnP, spnQ, spnU, spnT, spnPregnant, spnF, spnZ,
                spnAA, spnAL});
    }

    private void autoSelectionOtherWomen() {
        setSpinnerSelectedIndex(3, new Spinner[]{spnPregnant, spnF, spnH, spnI, spnJ, spnP, spnQ, spnS, spnT,
                spnU, spnZ, spnAA, spnAL});
    }

    private void autoSelectionOther() {
        setSpinnerSelectedIndex(3, new Spinner[]{spnP, spnQ, spnS, spnT, spnU, spnZ, spnAA, spnAL});
    }

    private void restart() {
        Intent intent = getIntent();
        finish();
        startActivity(intent);

        Toast.makeText(getApplicationContext(), "Enter next members information", Toast.LENGTH_LONG).show();
    }

    public JSONObject getDraftedJson() {
        return draftedJson;
    }

    public void function() {

        try {
            categoryInfo.put("type", category);
            categoryInfo.put("number", draft_key);
            categoryInfo.put("survey_id", survey);
            categoryInfo.put("survey_status", survey_status);
            jSurveyMother.put("categoryInfo", categoryInfo);
            jSurveyMother.put("referralInfo", jReferralInfo);
            jSurveyMother.put("defaultersInfo", jDefaultersInfo);

        } catch (JSONException e) {
            e.printStackTrace();
        }

//        if (MUtil.isNetworkConnected(getApplicationContext())) {
//            save(getApplicationContext(), jSurveyMother);
//        } else {
        householdDb.save(jSurveyMother.toString(), draft_key, HouseholdDb.TABLE_SURVEY);
        Intent intent = new Intent(getApplicationContext(), HouseholdMembersActivity.class);
        startActivity(intent);
//        }
    }

    public void function2() {

        try {
            categoryInfo.put("type", category);
            categoryInfo.put("number", member_no);
            categoryInfo.put("survey_id", survey);
            categoryInfo.put("survey_status", survey_status);
            jSurveyMother.put("categoryInfo", categoryInfo);
            jSurveyMother.put("motherInfo", jMotherInfo);
            jSurveyMother.put("referralInfo", jReferralInfo);
            jSurveyMother.put("defaultersInfo", jDefaultersInfo);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        Intent intent;

//        if (MUtil.isNetworkConnected(getApplicationContext())) {
//            save(getApplicationContext(), jSurveyMother);
//            intent = new Intent(getApplicationContext(), MotherDssActivity.class);
//        } else {
        householdDb.save(jSurveyMother.toString(), draft_key, HouseholdDb.TABLE_SURVEY);

        intent = new Intent(getApplicationContext(), MotherDssActivity.class);
//        }
        intent.putExtra("rand", draft_key);
        intent.putExtra("survey_id", survey);
        intent.putExtra("type", category);
        intent.putExtra("pre", "514");
        startActivity(intent);
    }

}

