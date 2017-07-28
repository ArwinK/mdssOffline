package com.dewcis.mdss;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.InputFilter;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.dewcis.mdss.model.Area;
import com.dewcis.mdss.model.Success;
import com.dewcis.mdss.model.Survey;
import com.dewcis.mdss.utils.MUtil;
import com.dewcis.mdss.volley.VolleyErrorHelper;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.rengwuxian.materialedittext.MaterialEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import cn.pedant.SweetAlert.SweetAlertDialog;
import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

public class DssActivityMother extends ActionBarActivity implements GoogleApiClient.OnConnectionFailedListener, LocationListener, GoogleApiClient.ConnectionCallbacks {

    String TAG = MApplication.TAG;
    static String CTAG = Form513Activity.class.getName() + " : " ;
    static boolean doLog = MApplication.LOGDEBUG;
    public static final String AREA_SUB_LOCATION = "getSublocations", AREA_VILLAGE = "getVillages";
    Integer subLocationId = null;
    Integer villageId = null;
    Integer user = null;
    Spinner spnSublocation,spnVillage;

    String tel_num ,genderTypeValue = "";
    MaterialEditText age;
    MaterialEditText mobile, name, txtMother;
    RadioGroup gender;

    double uLatitude,uLongitude;
    private Location mLastLocation;
    private LocationRequest mLocationRequest;

    // Google client to interact with Google API
    private GoogleApiClient mGoogleApiClient;

    // boolean flag to toggle periodic location updates
    private boolean mRequestingLocationUpdates = false;

    public DssActivityMother() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dss_activity_mother);

        spnSublocation  = (Spinner) findViewById(R.id.spnSubLoc);
        spnVillage  = (Spinner) findViewById(R.id.spnVillage);

        Bundle extra = getIntent().getExtras();

        if(extra != null){
            tel_num = extra.getString("mobile");
            user = extra.getInt("user");
        }
        mobile = (MaterialEditText) findViewById(R.id.txtNumber);
        name = (MaterialEditText) findViewById(R.id.txtName);
        age = (MaterialEditText) findViewById(R.id.txtAgeId);

        name.setFilters(new InputFilter[]{new InputFilter.AllCaps()});

        mobile.setText(tel_num);

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(subLocationId == null) {
            getArea(getApplicationContext(), new JSONObject(), AREA_SUB_LOCATION);
        }

    }


    public void eventSelector() {
        int ageNow = 0;
        final String num = mobile.getText().toString();
        if(!age.getText().toString().equals("")){
            ageNow = Integer.valueOf(age.getText().toString());
        }

        final String identity = name.getText().toString();

        if(num.equals("")){
            Toast.makeText(getApplicationContext(),"Enter a valid mobile number", Toast.LENGTH_LONG).show();
        }else if(ageNow == 0){
            Toast.makeText(getApplicationContext(),"Enter a valid age", Toast.LENGTH_LONG).show();
        }else if(identity.equals("")){
            Toast.makeText(getApplicationContext(),"Enter a name", Toast.LENGTH_LONG).show();
        }else if(villageId == null){
            Toast.makeText(getApplicationContext(),"The location cannot be empty", Toast.LENGTH_LONG).show();
        }else{

            Intent intent = new Intent(getApplicationContext(), DangerSignsActivity.class);

            intent.putExtra("name", identity);
            intent.putExtra("age", ageNow);
            intent.putExtra("guardian", identity);
            intent.putExtra("gender", "F");
            intent.putExtra("number", num);
            intent.putExtra("age", ageNow);
            intent.putExtra("village_id", villageId);
            intent.putExtra("uLatitude", uLatitude);
            intent.putExtra("uLongitude", uLongitude);
            intent.putExtra("health_worker_id", user);
            intent.putExtra("type", 2);

            SharedPreferences sharedpreferences = getSharedPreferences(DssActivity.DSS_PREFERENCES, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putInt("village_id", villageId);
            editor.putInt("age", ageNow);
            editor.putString("gender", genderTypeValue);
            editor.putString("mobile", num);
            editor.putString("activity", "mother");
            editor.commit();

            startActivity(intent);
            finish();
        }

    }

    public void showDescription(View v){
        //Toast.makeText(getApplicationContext(), v.getContentDescription().toString(), Toast.LENGTH_SHORT).show();
        new SweetAlertDialog(DssActivityMother.this,SweetAlertDialog.NORMAL_TYPE)
                .setTitleText("Details")
                .setContentText(v.getContentDescription().toString())
                .show();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onConnected(Bundle bundle) {
        Log.i(TAG, CTAG + "Play Connection Succeeded");

        mLastLocation = LocationServices.FusedLocationApi .getLastLocation(mGoogleApiClient);

        if (mLastLocation != null) {
            double latitude = mLastLocation.getLatitude();
            double longitude = mLastLocation.getLongitude();
            uLatitude = latitude;
            uLongitude = longitude;
            Toast.makeText(getApplicationContext(),"Location : " + latitude + ", " + longitude, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getApplicationContext(),"Could Not Get Location", Toast.LENGTH_LONG).show();
        }
        if (mRequestingLocationUpdates) {
            startLocationUpdates();
        }

    }
    @Override
    public void onConnectionSuspended(int i) {

    }

    protected void startLocationUpdates() {
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    public void getArea(final Context context, final JSONObject details, final String whichArea){
        final ProgressDialog pDialog = new ProgressDialog(DssActivityMother.this);
        pDialog.setMessage("Please Wait...");
        pDialog.setCancelable(false);
        final Activity activity = DssActivityMother.this;
        MUtil.showProgressDialog(pDialog);
        String url = MApplication.url + whichArea;

        if(doLog) Log.i(MApplication.TAG, MainActivity.class.getName() + " : Details " + details.toString());
        JsonObjectRequest mVerifyReq = new JsonObjectRequest(Request.Method.POST, url, details,
                new Response.Listener<JSONObject>() {
                    public void onResponse(JSONObject response) {
                        Success success = Success.makeFromJson(response);
                        if(doLog) Log.i(TAG, CTAG + response.toString());
                        Log.i(TAG, LoginActivity.class.getName() + "Response : " +response.toString() + "\nSuccess : " + success.isSuccess() + "\nMessage : " + success.getMessage());
                        MUtil.hideProgressDialog(pDialog);
                        ArrayList<HashMap<String,String>> arrayListAreas;
                        if(success.isSuccess()){
                            Crouton.makeText(DssActivityMother.this , success.getMessage() , Style.CONFIRM).show();
                            try {
                                JSONArray jAreas = response.getJSONArray("areas");

                                if(whichArea.equals(AREA_SUB_LOCATION)){
                                    final ArrayList<Area> areasSubLocation = Area.makeArrayList(response.getJSONArray("areas"));
                                    spnSublocation.setAdapter(Area.getArrayAdapter(activity, areasSubLocation));

                                    spnSublocation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                        @Override
                                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                            if(position == 0){
                                                Toast.makeText(context, "Select Sub Location", Toast.LENGTH_SHORT).show();
                                                villageId = null;
                                                spnVillage.setVisibility(View.INVISIBLE);
                                            }else{
                                                subLocationId = areasSubLocation.get(position).getId();
                                                spnVillage.setVisibility(View.VISIBLE);

                                                getArea(context, MUtil.simpleJSOnMaker(Survey.SUB_LOCATION_ID, subLocationId), AREA_VILLAGE);
                                            }
                                        }

                                        @Override
                                        public void onNothingSelected(AdapterView<?> parent) { }
                                    });
                                }else if(whichArea.equals(AREA_VILLAGE)){
                                    final ArrayList<Area> areasVillages = Area.makeArrayList(response.getJSONArray("areas"));
                                    spnVillage.setAdapter(Area.getArrayAdapter(activity, areasVillages));
                                    spnVillage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                        @Override
                                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                            if(position == 0){
                                                villageId = null;
                                                //MUtil.setVisibility(View.GONE, new View[]{txtHouseholdNum, txtHouseholdMember});
                                            }else {
                                                villageId = areasVillages.get(position).getId();

                                            }
                                        }

                                        @Override
                                        public void onNothingSelected(AdapterView<?> parent) { }
                                    });
                                }
                            }catch (JSONException je) {
                                Log.e(TAG, CTAG + "Error Retrieving Json Array : " + je.toString());
                            }catch (Exception e) {
                                Log.e(TAG, CTAG + "Error Retrieving Json Array : " + e.toString());
                            }
                            //MainActivity.this.finish();
                        }else{
                            Crouton.makeText(DssActivityMother.this , success.getMessage() , Style.ALERT).show();
                        }
                    };
                } ,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(MApplication.TAG, error.toString());
                        MUtil.hideProgressDialog(pDialog);
                        Crouton.makeText(DssActivityMother.this, VolleyErrorHelper.getMessage(error, context), Style.ALERT).show();
                    }
                }){
        };
        mVerifyReq.setRetryPolicy(new DefaultRetryPolicy(5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MApplication.getInstance().addToRequestQueue(mVerifyReq, "getArea");
    }

    public void toDss(View view) {
        eventSelector();
    }

}

