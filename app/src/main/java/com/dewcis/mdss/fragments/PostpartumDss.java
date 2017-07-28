package com.dewcis.mdss.fragments;

/**
 * Created by ArwinKish on 7/11/2017.
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.dewcis.mdss.SharedPreferences.SharedPreference;
import com.dewcis.mdss.constants.Constant;
import com.dewcis.mdss.d_model.DraftModel;
import com.dewcis.mdss.d_model.MotherModel;
import com.dewcis.mdss.d_model.PostpartumQModel;
import com.dewcis.mdss.d_model.PregnantQModel;
import com.dewcis.mdss.R;
import com.dewcis.mdss.utils.ExpandablePanel;
import com.dewcis.mdss.utils.I_fragmentlistener;
import com.dewcis.mdss.databases.DssDb;

public class PostpartumDss extends Fragment implements ExpandablePanel.OnExpandListener {

    ExpandablePanel panelSectionFive, panelSectionSix, panelPostpartumHistory;
    Spinner a_dX, b_aX, f_cX, f_nX, l_pX, l_cX, h_bX, h_dX, b_vX, d_bX,
            p_uX, p_fX, w_bX, a_lX, p_sX, f_sX, m_sP, a_sV, t_bS, f_bS, p_aS;

    PostpartumQModel clientDetails;
    private I_fragmentlistener<MotherModel, PostpartumQModel, PregnantQModel, DraftModel> complete_listener;
    private SharedPreferences sharedPreferences, shared;
    private int survey_status = 0;
    private int number = -1;

    public PostpartumDss() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_postpartum, container, false);

        shared = getActivity().getSharedPreferences(Constant.ACTIVITY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = shared.edit();

        editor.putInt(Constant.KEY_ACTIVITY, 4);
        editor.apply();

        clientDetails = new PostpartumQModel();

        a_dX = ((Spinner) rootView.findViewById(R.id.abdominal_discharge));
        b_aX = ((Spinner) rootView.findViewById(R.id.bad_abdominal));
        f_cX = ((Spinner) rootView.findViewById(R.id.fever_chills));
        f_nX = ((Spinner) rootView.findViewById(R.id.fever_nochills));
        h_bX = ((Spinner) rootView.findViewById(R.id.high_blood));
        l_pX = ((Spinner) rootView.findViewById(R.id.labor_pains));
        l_cX = ((Spinner) rootView.findViewById(R.id.loss_consciousness));
        h_dX = ((Spinner) rootView.findViewById(R.id.head_dizzy));
        b_vX = ((Spinner) rootView.findViewById(R.id.blurred_visionPm));
        d_bX = ((Spinner) rootView.findViewById(R.id.diff_breathing));
        p_uX = ((Spinner) rootView.findViewById(R.id.pass_urine));
        p_fX = ((Spinner) rootView.findViewById(R.id.palm_feet));
        w_bX = ((Spinner) rootView.findViewById(R.id.water_break));
        a_lX = ((Spinner) rootView.findViewById(R.id.arms_legs));
        p_sX = ((Spinner) rootView.findViewById(R.id.placenta));
        f_sX = ((Spinner) rootView.findViewById(R.id.foul_smell));

        t_bS = ((Spinner) rootView.findViewById(R.id.time_birth));
        f_bS = ((Spinner) rootView.findViewById(R.id.first_baby));
        p_aS = ((Spinner) rootView.findViewById(R.id.pnc_attended));

        panelSectionFive = (ExpandablePanel) rootView.findViewById(R.id.panelSectionFive);
        panelSectionSix = (ExpandablePanel) rootView.findViewById(R.id.panelSectionSix);
        panelPostpartumHistory = (ExpandablePanel) rootView.findViewById(R.id.panelPostpartumHistory);

        panelSectionFive.setOnExpandListener(this);
        panelSectionSix.setOnExpandListener(this);
        panelPostpartumHistory.setOnExpandListener(this);

        DssDb dssDb = DssDb.getInstance(getActivity());
        dssDb.getWritableDatabase();

        // static storage
        sharedPreferences = getActivity().getSharedPreferences(Constant.HOUSEHOLD_INFORMATION, Context.MODE_PRIVATE);
        String search = sharedPreferences.getString(Constant.DRAFT_KEY, null);
        boolean is_draft = sharedPreferences.getBoolean(Constant.DRAFT_BOOLEAN, false);

        if (dssDb.getRowCount() > 0 && complete_listener.getBol()) {
            clientDetails = dssDb.getPostpartum(search);
            populate(clientDetails);
        } else if (is_draft && dssDb.getRowCount() > 0) {
            clientDetails = dssDb.getPostpartum(search);
            populate(clientDetails);
        }

        return rootView;
    }

    private void populate(PostpartumQModel clientDetails) {

        a_dX.setSelection((int) clientDetails.get_a_dX());
        b_aX.setSelection((int) clientDetails.get_b_aX());
        f_cX.setSelection((int) clientDetails.get_f_cX());
        f_nX.setSelection((int) clientDetails.get_f_nX());
        h_bX.setSelection((int) clientDetails.get_h_bX());
        l_pX.setSelection((int) clientDetails.get_l_pX());
        l_cX.setSelection((int) clientDetails.get_l_cX());
        h_dX.setSelection((int) clientDetails.get_h_dX());
        b_vX.setSelection((int) clientDetails.get_b_vX());
        d_bX.setSelection((int) clientDetails.get_d_bX());
        p_uX.setSelection((int) clientDetails.get_p_uX());
        p_fX.setSelection((int) clientDetails.get_p_fX());
        w_bX.setSelection((int) clientDetails.get_w_bX());
        a_lX.setSelection((int) clientDetails.get_a_lX());
        p_sX.setSelection((int) clientDetails.get_p_sX());
        f_sX.setSelection((int) clientDetails.get_f_sX());
        t_bS.setSelection((int) clientDetails.get_t_bS());
        f_bS.setSelection((int) clientDetails.get_f_bS());
        p_aS.setSelection((int) clientDetails.get_p_aS());

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            complete_listener = (I_fragmentlistener<MotherModel, PostpartumQModel, PregnantQModel, DraftModel>) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString() + " must implement onSomeEventListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
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

    public void setClientDetails(String rand) {

        clientDetails.set_a_dX(a_dX.getSelectedItemId());
        clientDetails.set_b_aX(b_aX.getSelectedItemId());
        clientDetails.set_f_cX(f_cX.getSelectedItemId());
        clientDetails.set_f_nX(f_nX.getSelectedItemId());
        clientDetails.set_l_pX(l_pX.getSelectedItemId());
        clientDetails.set_l_cX(l_cX.getSelectedItemId());
        clientDetails.set_h_bX(h_bX.getSelectedItemId());
        clientDetails.set_h_dX(h_dX.getSelectedItemId());
        clientDetails.set_b_vX(b_vX.getSelectedItemId());
        clientDetails.set_d_bX(d_bX.getSelectedItemId());
        clientDetails.set_p_uX(p_uX.getSelectedItemId());
        clientDetails.set_p_fX(p_fX.getSelectedItemId());
        clientDetails.set_w_bX(w_bX.getSelectedItemId());
        clientDetails.set_a_lX(a_lX.getSelectedItemId());
        clientDetails.set_p_sX(p_sX.getSelectedItemId());
        clientDetails.set_f_sX(f_sX.getSelectedItemId());
        clientDetails.set_t_bS(t_bS.getSelectedItemId());
        clientDetails.set_f_bS(f_bS.getSelectedItemId());
        clientDetails.set_p_aS(p_aS.getSelectedItemId());
        clientDetails.set_toDss(getTotal());

        clientDetails.set_clientNumber(number);

        // get the survey status *****
        clientDetails.setSurveyStatus(survey_status);

        clientDetails.setRand(rand);

        complete_listener.ansQuestion(clientDetails);

    }

    private boolean getTotal() {

        boolean toDss = true;

        long sum = a_dX.getSelectedItemId() + b_aX.getSelectedItemId() + f_nX.getSelectedItemId() + f_cX.getSelectedItemId() + h_bX.getSelectedItemId() +
                l_pX.getSelectedItemId() + l_cX.getSelectedItemId() + h_dX.getSelectedItemId() + b_vX.getSelectedItemId() + d_bX.getSelectedItemId() +
                p_uX.getSelectedItemId() + p_fX.getSelectedItemId() + w_bX.getSelectedItemId() + a_lX.getSelectedItemId() + p_sX.getSelectedItemId() + f_sX.getSelectedItemId();

        if (sum >= 32) {
            toDss = false;
        }

        return toDss;
    }

    // set context to Dss 514
    public void context514() {

        SharedPreference sharedDynamic = new SharedPreference();
        //int survey = sharedDynamic.getValue(getActivity(), "survey_id");

        int postpartum = sharedPreferences.getInt("postpartum", 0);
        survey_status = sharedPreferences.getInt("survey_status", 0);

        // dynamic sharedpreference
        int var_postpartum = sharedDynamic.getValue(getActivity(), "postpartum");

        number = postpartum - var_postpartum;
    }
}

