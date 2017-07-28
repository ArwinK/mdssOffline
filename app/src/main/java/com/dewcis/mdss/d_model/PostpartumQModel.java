package com.dewcis.mdss.d_model;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import com.dewcis.mdss.Home;
import com.dewcis.mdss.HouseholdMembersActivity;

/**
 * Created by Arwin KIsh on 7/11/2017.
 */
public class PostpartumQModel {

    private long _a_sV, _a_dX, _b_aX, _f_cX, _f_nX, _l_pX, _l_cX, _h_bX, _h_dX, _b_vX, _d_bX, _p_uX, _p_fX,
            _w_bX, _p_sX, _a_lX, _f_sX, _f_sP, _m_sP;
    private String client_rand;
    private long _t_bS;
    private long _f_bS;
    private long _p_aS;
    private boolean toDss;
    private int clientNumber;
    private int surveyStatus;

    public PostpartumQModel() {
    }

    public void set_a_dX(long _a_dX) {
        this._a_dX = _a_dX;
    }

    public void set_b_aX(long _b_aX) {
        this._b_aX = _b_aX;
    }

    public void set_f_cX(long _f_cX) {
        this._f_cX = _f_cX;
    }

    public void set_f_nX(long _f_nX) {
        this._f_nX = _f_nX;
    }

    public void set_l_pX(long _l_pX) {
        this._l_pX = _l_pX;
    }

    public void set_l_cX(long _l_cX) {
        this._l_cX = _l_cX;
    }

    public void set_h_bX(long _h_bX) {
        this._h_bX = _h_bX;
    }

    public void set_h_dX(long _h_dX) {
        this._h_dX = _h_dX;
    }

    public void set_b_vX(long _b_vX) {
        this._b_vX = _b_vX;
    }

    public void set_d_bX(long _d_bX) {
        this._d_bX = _d_bX;
    }

    public void set_p_uX(long _p_uX) {
        this._p_uX = _p_uX;
    }

    public void set_p_fX(long _p_fX) {
        this._p_fX = _p_fX;
    }

    public void set_w_bX(long _w_bX) {
        this._w_bX = _w_bX;
    }

    public void set_p_sX(long _p_sX) {
        this._p_sX = _p_sX;
    }

    public void set_a_lX(long _a_lX) {
        this._a_lX = _a_lX;
    }

    public void set_f_sX(long _f_sX) {
        this._f_sX = _f_sX;
    }

    public void set_t_bS(long _t_bS) {
        this._t_bS = _t_bS;
    }

    public void set_f_bS(long _f_bS) {
        this._f_bS = _f_bS;
    }

    public void set_p_aS(long _p_aS) {
        this._p_aS = _p_aS;
    }

    public void set_toDss(boolean toDss) {
        this.toDss = toDss;
    }

    public void set_clientNumber(int clientNumber) {
        this.clientNumber = clientNumber;
    }

    public void setSurveyStatus(int surveyStatus) {
        this.surveyStatus = surveyStatus;
    }

    public long get_a_dX() {
        return _a_dX;
    }

    public long get_b_aX() {
        return _b_aX;
    }

    public long get_f_cX() {
        return _f_cX;
    }

    public long get_f_nX() {
        return _f_nX;
    }

    public long get_h_bX() {
        return _h_bX;
    }

    public long get_l_pX() {
        return _l_pX;
    }

    public long get_l_cX() {
        return _l_cX;
    }

    public long get_h_dX() {
        return _h_dX;
    }

    public long get_b_vX() {
        return _b_vX;
    }

    public long get_d_bX() {
        return _d_bX;
    }

    public long get_p_uX() {
        return _p_uX;
    }

    public long get_p_fX() {
        return _p_fX;
    }

    public long get_w_bX() {
        return _w_bX;
    }

    public long get_a_lX() {
        return _a_lX;
    }

    public long get_p_sX() {
        return _p_sX;
    }

    public long get_f_sX() {
        return _f_sX;
    }

    public long get_t_bS() {
        return _t_bS;
    }

    public long get_f_bS() {
        return _f_bS;
    }

    public long get_p_aS() {
        return _p_aS;
    }

    public int get_clientNumber() {
        return clientNumber;
    }

    public boolean get_toDss() {
        return toDss;
    }

    public void eventSelector(final Context context, final String my514) {
        final CharSequence[] items = {"No need to refer this patient"};
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);
        builder.setTitle("Referral");

        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Intent intent;
                if (my514 != null) {
                    intent = new Intent(context, HouseholdMembersActivity.class);
                    context.startActivity(intent);

                } else {
                    intent = new Intent(context, Home.class);
                    context.startActivity(intent);
                }

            }
        });


        AlertDialog alert = builder.create();
        alert.show();
    }

    public void setRand(String client_rand) {
        this.client_rand = client_rand;
    }

    @Override
    public String toString() {
        return "QuestionsModel{" +
                " client_rand='" + client_rand + '\'' +
                ", _a_dX='" + _a_dX + '\'' +
                ", _b_aX='" + _b_aX + '\'' +
                ", _f_cX=" + _f_cX + '\'' +
                ", _f_nX=" + _f_nX + '\'' +
                ", _l_pX=" + _l_pX + '\'' +
                ", _l_cX='" + _l_cX + '\'' +
                ", _h_bX='" + _h_bX + '\'' +
                ", _h_dX='" + _h_dX + '\'' +
                ", _b_vX='" + _b_vX + '\'' +
                ", _d_bX='" + _d_bX + '\'' +
                ", _p_uX='" + _p_uX + '\'' +
                ", _p_fX='" + _p_fX + '\'' +
                ", _w_bX='" + _w_bX + '\'' +
                ", _p_sX='" + _p_sX + '\'' +
                ", _a_lX='" + _a_lX + '\'' +
                ", _f_sX='" + _f_sX + '\'' +
                ", _f_sP='" + _f_sP + '\'' +
                ", _m_sP='" + _m_sP + '\'' +
                ", _a_sV='" + _a_sV + '\'' +
                '}';
    }
}
