package com.dewcis.mdss.fragments;

/**
 * Created by Arwin KIsh on 7/12/2017.
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


import com.dewcis.mdss.constants.Constant;
import com.dewcis.mdss.databases.DssDb;
import com.dewcis.mdss.d_model.DraftModel;
import com.dewcis.mdss.d_model.MotherModel;
import com.dewcis.mdss.d_model.PostpartumQModel;
import com.dewcis.mdss.d_model.PregnantQModel;
import com.dewcis.mdss.R;
import com.dewcis.mdss.utils.ExpandablePanel;
import com.dewcis.mdss.utils.I_fragmentlistener;



public class PregnantDss extends Fragment implements ExpandablePanel.OnExpandListener {

    ExpandablePanel panelSectionOne, panelSectionTwo, panelSectionThree,
            panelSectionFour, panelPregnancyHistory;
    Spinner b_nS, b_hS, b_vS, e_wS, s_hS, v_S, c_S, c_cS, f_S, d_sS, a_sS, b_bS, u_rS, u_nS,
            p_sP, g_uS, n_mS, t_bS, f_bS, p_aS, p_fA;

    PregnantQModel pregantQModel;
    private I_fragmentlistener<MotherModel, PostpartumQModel, PregnantQModel, DraftModel> complete_listener;
    private SharedPreferences sharedPreferences,shared;

    public PregnantDss(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_pregnant, container, false);

        shared = getActivity().getSharedPreferences(Constant.ACTIVITY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = shared.edit();

        editor.putInt(Constant.KEY_ACTIVITY, 3);
        editor.apply();

        pregantQModel = new PregnantQModel();

        b_nS = ((Spinner) rootView.findViewById(R.id.bleeding_vagina));
        b_hS = ((Spinner) rootView.findViewById(R.id.bad_headache));
        b_vS = ((Spinner) rootView.findViewById(R.id.blurred_vision));
        e_wS = ((Spinner) rootView.findViewById(R.id.eWeightLoss));
        s_hS = ((Spinner) rootView.findViewById(R.id.swelling_hands));
        v_S = ((Spinner) rootView.findViewById(R.id.vomiting));
        c_S = ((Spinner) rootView.findViewById(R.id.consciousness));
        f_S = ((Spinner) rootView.findViewById(R.id.fever));
        d_sS = ((Spinner) rootView.findViewById(R.id.discharge));
        c_cS = ((Spinner) rootView.findViewById(R.id.convulsion));
        a_sS = ((Spinner) rootView.findViewById(R.id.abdominal));
        b_bS = ((Spinner) rootView.findViewById(R.id.breathing));
        u_rS = ((Spinner) rootView.findViewById(R.id.urination));
        u_nS = ((Spinner) rootView.findViewById(R.id.urinate));
        p_sP = ((Spinner) rootView.findViewById(R.id.pale_pams));
        g_uS = ((Spinner) rootView.findViewById(R.id.genital_ulcers));
        n_mS = ((Spinner) rootView.findViewById(R.id.not_moving));

        t_bS = ((Spinner) rootView.findViewById(R.id.first_pregnancy));
        f_bS = ((Spinner) rootView.findViewById(R.id.months_pregnant));
        p_aS = ((Spinner) rootView.findViewById(R.id.anc_visit));
        p_fA = ((Spinner) rootView.findViewById(R.id.pregnant_first_anc));


        panelSectionOne = (ExpandablePanel) rootView.findViewById(R.id.panelSectionOne);
        panelSectionTwo = (ExpandablePanel) rootView.findViewById(R.id.panelSectionTwo);
        panelSectionThree = (ExpandablePanel) rootView.findViewById(R.id.panelSectionThree);
        panelSectionFour = (ExpandablePanel) rootView.findViewById(R.id.panelSectionFour);
        panelPregnancyHistory = (ExpandablePanel) rootView.findViewById(R.id.panelPregnancyHistory);

        panelSectionOne.setOnExpandListener(this);
        panelSectionTwo.setOnExpandListener(this);
        panelSectionThree.setOnExpandListener(this);
        panelSectionFour.setOnExpandListener(this);
        panelPregnancyHistory.setOnExpandListener(this);

        DssDb dssDb = DssDb.getInstance(getActivity());
        dssDb.getWritableDatabase();

        sharedPreferences = getActivity().getSharedPreferences(Constant.HOUSEHOLD_INFORMATION, Context.MODE_PRIVATE);
        String search = sharedPreferences.getString(Constant.DRAFT_KEY, null);

        if (dssDb.getRowCount() > 0 && complete_listener.getBol()) {
            pregantQModel = dssDb.getPregnant(search);

            b_nS.setSelection((int) pregantQModel.get_b_nS());
            b_hS.setSelection((int) pregantQModel.get_b_hS());
            b_vS.setSelection((int) pregantQModel.get_b_vS());
            e_wS.setSelection((int) pregantQModel.get_e_wS());
            s_hS.setSelection((int) pregantQModel.get_s_hS());
            v_S.setSelection((int) pregantQModel.get_v_S());
            c_S.setSelection((int) pregantQModel.get_c_S());
            f_S.setSelection((int) pregantQModel.get_f_S());
            d_sS.setSelection((int) pregantQModel.get_d_sS());
            c_cS.setSelection((int) pregantQModel.get_c_cS());
            a_sS.setSelection((int) pregantQModel.get_a_sS());
            b_bS.setSelection((int) pregantQModel.get_b_bS());
            u_rS.setSelection((int) pregantQModel.get_u_rS());
            u_nS.setSelection((int) pregantQModel.get_u_nS());
            p_sP.setSelection((int) pregantQModel.get_p_sP());
            g_uS.setSelection((int) pregantQModel.get_g_uS());
            n_mS.setSelection((int) pregantQModel.get_n_mS());

            t_bS.setSelection((int) pregantQModel.get_t_bS());
            f_bS.setSelection((int) pregantQModel.get_f_bS());
            p_aS.setSelection((int) pregantQModel.get_p_aS());
            p_fA.setSelection((int) pregantQModel.get_p_fA());
        }

        return rootView;
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
        header.setCompoundDrawablesWithIntrinsicBounds(0,0, R.drawable.ic_expand_less_white_18dp,0);
    }

    @Override
    public void onCollapse(View handle, View content) {
        TextView header = (TextView) handle;
        header.setCompoundDrawablesWithIntrinsicBounds(0,0, R.drawable.ic_expand_more_white_18dp,0);
    }

    public void setClientDetails(String rand){

        pregantQModel.set_b_nS(b_nS.getSelectedItemId());
        pregantQModel.set_b_hS(b_hS.getSelectedItemId());
        pregantQModel.set_b_vS(b_vS.getSelectedItemId());
        pregantQModel.set_e_wS(e_wS.getSelectedItemId());
        pregantQModel.set_s_hS(s_hS.getSelectedItemId());
        pregantQModel.set_v_S(v_S.getSelectedItemId());
        pregantQModel.set_c_S(c_S.getSelectedItemId());
        pregantQModel.set_f_S(f_S.getSelectedItemId());
        pregantQModel.set_d_sS(d_sS.getSelectedItemId());
        pregantQModel.set_c_cS(c_cS.getSelectedItemId());
        pregantQModel.set_a_sS(a_sS.getSelectedItemId());
        pregantQModel.set_b_bS(b_bS.getSelectedItemId());
        pregantQModel.set_u_rS(u_rS.getSelectedItemId());
        pregantQModel.set_u_nS(u_nS.getSelectedItemId());
        pregantQModel.set_p_sP(p_sP.getSelectedItemId());
        pregantQModel.set_g_uS(g_uS.getSelectedItemId());
        pregantQModel.set_n_mS(n_mS.getSelectedItemId());

        pregantQModel.set_t_bS(t_bS.getSelectedItemId());
        pregantQModel.set_f_bS(f_bS.getSelectedItemId());
        pregantQModel.set_p_aS(p_aS.getSelectedItemId());
        pregantQModel.set_p_fA(p_fA.getSelectedItemId());
        pregantQModel.setRand(rand);

        complete_listener.ansQuestionPregnant(pregantQModel);

    }
}
