package com.dewcis.mdss.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.dewcis.mdss.LoginActivity;
import com.dewcis.mdss.MApplication;
import com.dewcis.mdss.MainActivity;
import com.dewcis.mdss.R;
import com.dewcis.mdss.constants.Constant;
import com.dewcis.mdss.d_model.BabyModel;
import com.dewcis.mdss.d_model.DraftModel;
import com.dewcis.mdss.d_model.SevenQModel;
import com.dewcis.mdss.d_model.TwentyEightQModel;
import com.dewcis.mdss.databases.LocationDb;
import com.dewcis.mdss.model.Area;
import com.dewcis.mdss.model.Success;
import com.dewcis.mdss.model.Survey;
import com.dewcis.mdss.utils.I_fragmentlistener;
import com.dewcis.mdss.utils.MUtil;
import com.dewcis.mdss.volley.VolleyErrorHelper;
import com.google.gson.Gson;
import com.rengwuxian.materialedittext.MaterialEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;


public class BabyDssDetails extends Fragment {

    SharedPreferences sharedPreferences;
    private MaterialEditText mName, bName, age, weight, mobile;
    private RadioGroup rGender;
    private Spinner days;

    BabyModel babyModel;

    String TAG = MApplication.TAG;
    static String CTAG = BabyDssDetails.class.getName() + " : ";
    static boolean doLog = MApplication.LOGDEBUG;
    public static final String AREA_SUB_LOCATION = "getSublocations", AREA_VILLAGE = "getVillages";
    Integer subLocationId = null;
    Integer villageId = 0;
    Integer user = null;
    Spinner spnSublocation, spnVillage;
    LinearLayout locs;

    I_fragmentlistener<BabyModel, SevenQModel, TwentyEightQModel, DraftModel> complete_listener;

    public BabyDssDetails() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_baby_details, container, false);
        babyModel = new BabyModel();

        mName = (MaterialEditText) rootView.findViewById(R.id.txtMother);
        bName = (MaterialEditText) rootView.findViewById(R.id.txtName);
        age = (MaterialEditText) rootView.findViewById(R.id.txtAgeId);
        weight = (MaterialEditText) rootView.findViewById(R.id.txtBabyWeight);
        mobile = (MaterialEditText) rootView.findViewById(R.id.txtNumber);
        spnSublocation = (Spinner) rootView.findViewById(R.id.spnSublocation);
        spnVillage = (Spinner) rootView.findViewById(R.id.spnVillage);
        days = (Spinner) rootView.findViewById(R.id.spnDays);
        rGender = (RadioGroup) rootView.findViewById(R.id.rGender);

        locs = (LinearLayout) rootView.findViewById(R.id.locations);

        sharedPreferences = getActivity().getSharedPreferences(Constant.HOUSEHOLD_INFORMATION, Context.MODE_PRIVATE);
        String populate = sharedPreferences.getString(Constant.DRAFT_VALUE, null);

        //Log.d("searchItem", populate);

        if (populate != null && complete_listener.getBol()) {
            populateFields(populate);
        }
        return rootView;
    }

    public void populateFields(String populate) {
        Gson gson = new Gson();
        BabyModel babyModel = gson.fromJson(populate, BabyModel.class);
        mName.setText(babyModel.getClient_mName());
        bName.setText(babyModel.getClient_bName());
        age.setText(babyModel.getClient_age());
        weight.setText(babyModel.getClient_weight());
        mobile.setText(babyModel.getClient_phone());
        days.setSelection((int) babyModel.getClient_days());
        rGender.check(babyModel.getClient_gender());

        villageId = (int) babyModel.getClient_community();

        if(villageId != null && villageId != 0){
            locs.setVisibility(View.GONE);
            Toast.makeText(getActivity(), "Village Auto loaded", Toast.LENGTH_SHORT).show();
        }

    }

    public void doTask(String rand) {

        babyModel.setClient_rand(rand);
        babyModel.setClient_age(age.getText().toString());
        babyModel.setClient_days(days.getSelectedItemId());
        babyModel.setClient_sublocation(spnSublocation.getSelectedItemId());
        babyModel.setClient_community(spnVillage.getSelectedItemId());
        babyModel.setClient_mName(mName.getText().toString());
        babyModel.setClient_bName(bName.getText().toString());
        babyModel.setClient_phone(mobile.getText().toString());
        babyModel.setClient_weight(weight.getText().toString());
        babyModel.setClient_gender(rGender.getCheckedRadioButtonId());

        complete_listener.onData(babyModel);

    }

    @Override
    public void onResume() {
        super.onResume();
        if (subLocationId == null && !complete_listener.getBol()) {
            getArea(getActivity(), new JSONObject(), AREA_SUB_LOCATION);
        }
    }

    private void getLocalArea() throws JSONException {

        LocationDb communityUnitDb = LocationDb.getInstance(getActivity());
        communityUnitDb.getWritableDatabase();

        communityUnitDb.getCommunity();
        final ArrayList<Area> areasSubLocation = Area.makeArrayList(communityUnitDb.getCommunity().getJSONArray("areas"));

        spnSublocation.setAdapter(Area.getArrayAdapter(getActivity(), areasSubLocation));

        spnSublocation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    Toast.makeText(getActivity(), "Select Sub Location", Toast.LENGTH_SHORT).show();
                    villageId = null;
                    spnVillage.setVisibility(View.INVISIBLE);
                } else {
                    subLocationId = areasSubLocation.get(position).getId();
                    spnVillage.setVisibility(View.VISIBLE);

                    try {
                        getLocalSublocation(subLocationId);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void getLocalSublocation(Integer subLocationId) throws JSONException {

        LocationDb communityUnitDb = LocationDb.getInstance(getActivity());
        communityUnitDb.getWritableDatabase();

        communityUnitDb.getSublocation(subLocationId + "");
        final ArrayList<Area> areasVillages = Area.makeArrayList(communityUnitDb.getSublocation(subLocationId + "").getJSONArray("areas"));

        spnVillage.setAdapter(Area.getArrayAdapter(getActivity(), areasVillages));
        spnVillage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    villageId = null;
                } else {
                    villageId = areasVillages.get(position).getId();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            complete_listener = (I_fragmentlistener<BabyModel, SevenQModel, TwentyEightQModel, DraftModel>) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString() + " must implement onSomeEventListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public void getArea(final Context context, final JSONObject details, final String whichArea) {
        final ProgressDialog pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Please Wait...");
        pDialog.setCancelable(false);
        final Activity activity = getActivity();
        MUtil.showProgressDialog(pDialog);
        String url = MApplication.url + whichArea;

        if (doLog)
            Log.i(MApplication.TAG, MainActivity.class.getName() + " : Details " + details.toString());
        JsonObjectRequest mVerifyReq = new JsonObjectRequest(Request.Method.POST, url, details,
                new Response.Listener<JSONObject>() {
                    public void onResponse(JSONObject response) {
                        Success success = Success.makeFromJson(response);
                        if (doLog) Log.i(TAG, CTAG + response.toString());
                        Log.i(TAG, LoginActivity.class.getName() + "Response : " + response.toString() + "\nSuccess : " + success.isSuccess() + "\nMessage : " + success.getMessage());
                        MUtil.hideProgressDialog(pDialog);

                        LocationDb communityUnitDb = LocationDb.getInstance(context);

                        if (success.isSuccess()) {
                            Crouton.makeText(getActivity(), success.getMessage(), Style.CONFIRM).show();
                            try {
                                JSONArray jAreas = response.getJSONArray("areas");

                                if (whichArea.equals(AREA_SUB_LOCATION)) {
                                    final ArrayList<Area> areasSubLocation = Area.makeArrayList(response.getJSONArray("areas"));
                                    final ArrayList<Area> areasAll = Area.makeArrayListArea(response.getJSONArray("areaAll"));

                                    //Toast.makeText(context, areasSubLocation.toString(), Toast.LENGTH_LONG).show();
                                    communityUnitDb.getWritableDatabase();
                                    communityUnitDb.resetTables();

                                    communityUnitDb.saveSublocation(areasSubLocation);
                                    communityUnitDb.saveVillages(areasAll);

                                    spnSublocation.setAdapter(Area.getArrayAdapter(activity, areasSubLocation));

                                    spnSublocation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                        @Override
                                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                            if (position == 0) {
                                                Toast.makeText(context, "Select Sub Location", Toast.LENGTH_SHORT).show();
                                                villageId = null;
                                                spnVillage.setVisibility(View.INVISIBLE);
                                            } else {
                                                subLocationId = areasSubLocation.get(position).getId();
                                                spnVillage.setVisibility(View.VISIBLE);

                                                getArea(context, MUtil.simpleJSOnMaker(Survey.SUB_LOCATION_ID, subLocationId), AREA_VILLAGE);
                                            }
                                        }

                                        @Override
                                        public void onNothingSelected(AdapterView<?> parent) {

                                        }
                                    });
                                } else if (whichArea.equals(AREA_VILLAGE)) {
                                    final ArrayList<Area> areasVillages = Area.makeArrayList(response.getJSONArray("areas"));

                                    spnVillage.setAdapter(Area.getArrayAdapter(activity, areasVillages));
                                    spnVillage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                        @Override
                                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                            if (position == 0) {
                                                villageId = null;
                                                //MUtil.setVisibility(View.GONE, new View[]{txtHouseholdNum, txtHouseholdMember});
                                            } else {
                                                villageId = areasVillages.get(position).getId();

                                            }
                                        }

                                        @Override
                                        public void onNothingSelected(AdapterView<?> parent) {

                                        }
                                    });
                                }
                            } catch (JSONException je) {
                                Log.e(TAG, CTAG + "Error Retrieving Json Array : " + je.toString());
                            } catch (Exception e) {
                                Log.e(TAG, CTAG + "Error Retrieving Json Array : " + e.toString());
                            }
                            //MainActivity.this.finish();
                        } else {
                            Crouton.makeText(getActivity(), success.getMessage(), Style.ALERT).show();
                        }
                    }

                    ;
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(MApplication.TAG, error.toString());
                        MUtil.hideProgressDialog(pDialog);
                        Crouton.makeText(getActivity(), VolleyErrorHelper.getMessage(error, context), Style.ALERT).show();
                        try {
                            getLocalArea();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }) {
        };
        mVerifyReq.setRetryPolicy(new DefaultRetryPolicy(5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MApplication.getInstance().addToRequestQueue(mVerifyReq, "getArea");
    }

}
