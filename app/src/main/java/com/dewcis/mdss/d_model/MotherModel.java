package com.dewcis.mdss.d_model;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import com.dewcis.mdss.Home;
import com.dewcis.mdss.HouseholdMembersActivity;
import com.dewcis.mdss.R;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Arwin KIsh on 7/11/2017.
 */
public class MotherModel {

    private String client_id;

    private int client_status;
    private String client_rand;
    private long client_community;
    private int weight;
    private String age_type;
    private Integer villageId;
    private String age;
    private int status;
    private String name;
    private String mobile;
    private String latitude;
    private String longitude;
    private int org_id;
    private int dsselection;


    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public void setClient_name(String name) {
        this.name = name;
    }

    public void setClient_phone(String mobile) {
        this.mobile = mobile;
    }

    public void setClient_latitude(String latitude) {
        this.latitude = latitude;
    }

    public void setClient_longitude(String longitude) {
        this.longitude = longitude;
    }

    public void setClient_status(int status) {
        this.status = status;
    }

    public void setClient_mobile(String mobile) {
        this.mobile = mobile;
    }

    public void setClient_age(String age) {
        this.age = age;
    }

    public String getClient_id() {
        return client_id;
    }

    public String getClient_age() {
        return age;
    }

    public String getClient_name() {
        return name;
    }

    public String getClient_phone() {
        return mobile;
    }

    public String getClient_latitude() {
        return latitude;
    }

    public String getClient_longitude() {
        return longitude;
    }

    public int getClient_status() {
        return status;
    }

    public String getClient_rand() {
        return client_rand;
    }

    public void setClient_rand(String client_rand) {
        this.client_rand = client_rand;
    }

    public long getClient_community() {
        return client_community;
    }

    public void setClient_community(long client_community) {
        this.client_community = client_community;
    }

    public static MotherModel makeFromJSON(JSONObject json) {
        MotherModel motherModel = new MotherModel();
        try {
            motherModel.setClient_name(json.getString("name"));
            motherModel.setClient_age(json.getString("age"));
            motherModel.setClient_phone(json.getString("mobile"));
            motherModel.setClient_rand(json.getString("client_rand"));

        } catch (JSONException e) {
            //if(doLog) Log.e(TAG, CTAG  + e.toString());
        }
        return motherModel;
    }

    public static ArrayList<MotherModel> makeArraylist(JSONArray jsonArray) {
        ArrayList<MotherModel> messages = new ArrayList<MotherModel>();

        try {
            for (int i = 0; i < jsonArray.length(); i++){
                messages.add(MotherModel.makeFromJSON(jsonArray.getJSONObject(i)));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return messages;
    }

//    public ValidateModel isSet(Context context){
//
//        if (a_dX.getSelectedItemId() == 0) {
//            showInvalidMessage(R.string.abdominal_discharge, context);
//            return;
//        }
//        if (b_aX.getSelectedItemId() == 0) {
//            showInvalidMessage(R.string.bad_abdominal, context);
//            return;
//        }
//        if (f_nX.getSelectedItemId() == 0) {
//            showInvalidMessage(R.string.fever_chills, context);
//            return;
//        }
//        if (f_cX.getSelectedItemId() == 0) {
//            showInvalidMessage(R.string.fever_nochills, context);
//            return;
//        }
//        if (h_bX.getSelectedItemId() == 0) {
//            showInvalidMessage(R.string.high_blood, context);
//            return;
//        }
//        if (l_pX.getSelectedItemId() == 0) {
//            showInvalidMessage(R.string.labour_pain, context);
//            return;
//        }
//        if (l_cX.getSelectedItemId() == 0) {
//            showInvalidMessage(R.string.loss_consciousness, context);
//            return;
//        }
//        if (h_dX.getSelectedItemId() == 0) {
//            showInvalidMessage(R.string.head_dizzy, context);
//            return;
//        }
//        if (b_vX.getSelectedItemId() == 0) {
//            showInvalidMessage(R.string.Blurred_vision, context);
//            return;
//        }
//        if (d_bX.getSelectedItemId() == 0) {
//            showInvalidMessage(R.string.diff_breathing, context);
//            return;
//        }
//        if (p_uX.getSelectedItemId() == 0) {
//            showInvalidMessage(R.string.pass_urine, context);
//            return;
//        }
//        if (p_fX.getSelectedItemId() == 0) {
//            showInvalidMessage(R.string.palm_feet, context);
//            return;
//        }
//        if (w_bX.getSelectedItemId() == 0) {
//            showInvalidMessage(R.string.water_break, context);
//            return;
//        }
//        if (a_lX.getSelectedItemId() == 0) {
//            showInvalidMessage(R.string.arm_legs, context);
//            return;
//        }
//        if (p_sX.getSelectedItemId() == 0) {
//            showInvalidMessage(R.string.placenta, context);
//            return;
//        }
//        if (f_sX.getSelectedItemId() == 0) {
//            showInvalidMessage(R.string.foul_smelling, context);
//            return;
//        }
//        if (t_bS.getSelectedItemPosition() == 0) {
//            showInvalidMessage(R.string.time_birth, context);
//            return;
//        }
//        if (f_bS.getSelectedItemPosition() == 0) {
//            showInvalidMessage(R.string.first_baby, context);
//            return;
//        }
//        if (p_aS.getSelectedItemPosition() == 0) {
//            showInvalidMessage(R.string.pnc_attended, context);
//            return;
//        }
//    }
//
//    public void showInvalidMessage(int message, Context context) {
//        new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
//                .setContentText("Select If " + context.getResources().getString(message)).show();
//    }

    @Override
    public String toString() {
        return "danger_signs{" +
                "client_rand='" + client_rand + '\'' +
                ", client_id='" + client_id + '\'' +
                ", org_id='" + org_id + '\'' +
                ", name='" + name + "" + '\'' +
                ", mobile=" + mobile + '\'' +
                ", latitude=" + latitude + '\'' +
                ", longitude=" + longitude + '\'' +
                ", mobile='" + mobile + '\'' +
                ", status='" + status + '\'' +
                ", age='" + age + '\'' +
                ", villageId='" + villageId + '\'' +
                ", age_type='" + age_type + '\'' +
                ", weight='" + weight + '\'' +
                ", dsselection='" + dsselection + '\'' +
                '}';
    }


}
