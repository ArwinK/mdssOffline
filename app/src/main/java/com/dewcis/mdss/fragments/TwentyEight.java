package com.dewcis.mdss.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.dewcis.mdss.BabyDssActivity;
import com.dewcis.mdss.R;
import com.dewcis.mdss.constants.Constant;
import com.dewcis.mdss.d_model.BabyModel;
import com.dewcis.mdss.d_model.DraftModel;
import com.dewcis.mdss.d_model.SevenQModel;
import com.dewcis.mdss.d_model.TwentyEightQModel;
import com.dewcis.mdss.databases.DssDb;
import com.dewcis.mdss.utils.ExpandablePanel;
import com.dewcis.mdss.utils.I_fragmentlistener;


public class TwentyEight extends Fragment implements ExpandablePanel.OnExpandListener {

    public TwentyEight() {

    }

    TwentyEightQModel twentyEightQModel;
    ExpandablePanel panelSectionTwo,panelSectionThree,panelBreastfeedingHistory;
    Spinner feet_blue_2,fever_chills,yellow_skin_2,breathing_difficulties,abdominal,
            not_sucking_2,constipation,urinate,start_feeding,fed_birth,child_fed;

    private I_fragmentlistener<BabyModel,SevenQModel,TwentyEightQModel,DraftModel> i_fragmentlistener;
    private SharedPreferences sharedPreferences,shared;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_twenty_eight,container,false);

        shared = getActivity().getSharedPreferences(Constant.ACTIVITY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = shared.edit();

        editor.putInt(Constant.KEY_ACTIVITY, 2);
        editor.apply();

        twentyEightQModel = new TwentyEightQModel();
        panelSectionTwo = (ExpandablePanel) view.findViewById(R.id.panelSectionTwo);
        panelSectionThree = (ExpandablePanel)view.findViewById(R.id.panelSectionThree);
        panelBreastfeedingHistory = (ExpandablePanel)view.findViewById(R.id.panelBreastfeedingHistory);

        feet_blue_2 = (Spinner)view.findViewById(R.id.feet_blue_2);
        fever_chills = (Spinner)view.findViewById(R.id.fever_chills);
        yellow_skin_2 = (Spinner)view.findViewById(R.id.yellow_skin_2);
        breathing_difficulties = (Spinner)view.findViewById(R.id.breathing_difficulties);
        abdominal = (Spinner)view.findViewById(R.id.abdominal);
        not_sucking_2 = (Spinner)view.findViewById(R.id.not_sucking_2);
        constipation = (Spinner)view.findViewById(R.id.constipation);
        urinate = (Spinner)view.findViewById(R.id.urinate);
        start_feeding = (Spinner)view.findViewById(R.id.start_feeding);
        fed_birth = (Spinner)view.findViewById(R.id.fed_birth);
        child_fed = (Spinner)view.findViewById(R.id.child_fed);

        panelSectionTwo.setOnExpandListener(this);
        panelSectionTwo.setOnExpandListener(this);
        panelBreastfeedingHistory.setOnExpandListener(this);

        //Create an instance of SevenDaysDb
        DssDb twentyEightDb = DssDb.getInstance(getActivity());
        twentyEightDb.getWritableDatabase();

        sharedPreferences = getActivity().getSharedPreferences(Constant.HOUSEHOLD_INFORMATION, Context.MODE_PRIVATE);
        String search = sharedPreferences.getString(Constant.DRAFT_KEY, null);

        if(twentyEightDb.getRowCount()>0 && i_fragmentlistener.getBol()){
            twentyEightQModel = twentyEightDb.getTwentyEight(search);

            feet_blue_2.setSelection((int)twentyEightQModel.getFeet_blue_2());
            fever_chills.setSelection((int)twentyEightQModel.getFever_chills());
            yellow_skin_2.setSelection((int)twentyEightQModel.getYellow_skin_2());
            breathing_difficulties.setSelection((int)twentyEightQModel.getBreathing_difficulties());
            abdominal.setSelection((int)twentyEightQModel.getAbdominal());
            not_sucking_2.setSelection((int)twentyEightQModel.getNot_sucking_2());
            constipation.setSelection((int)twentyEightQModel.getConstipation());
            urinate.setSelection((int)twentyEightQModel.getUrinate());
            start_feeding.setSelection((int)twentyEightQModel.getStart_feeding());
            fed_birth.setSelection((int)twentyEightQModel.getFed_birth());
            child_fed.setSelection((int)twentyEightQModel.getChild_fed());
        }

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            i_fragmentlistener = (I_fragmentlistener<BabyModel,SevenQModel,TwentyEightQModel,DraftModel>)getActivity();
        }catch (ClassCastException e){
            throw new ClassCastException(getActivity().toString() + " must implement onSomeEventListener");
        }
    }

    @Override
    public void onExpand(View handle, View content) {
        TextView header = (TextView) handle;
        header.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_expand_more_white_18dp,0);
    }

    @Override
    public void onCollapse(View handle, View content) {
        TextView header = (TextView) handle;
        header.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_expand_less_white_18dp,0);
    }

    public void setClientDetails(){
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(BabyDssActivity.UID, Context.MODE_PRIVATE);
        String rand = sharedPreferences.getString(BabyDssActivity.RAND_ID,null);

        twentyEightQModel.setFeet_blue_2(feet_blue_2.getSelectedItemId());
        twentyEightQModel.setFever_chills(fever_chills.getSelectedItemId());
        twentyEightQModel.setYellow_skin_2(yellow_skin_2.getSelectedItemId());
        twentyEightQModel.setBreathing_difficulties(breathing_difficulties.getSelectedItemId());
        twentyEightQModel.setAbdominal(abdominal.getSelectedItemId());
        twentyEightQModel.setNot_sucking_2(not_sucking_2.getSelectedItemId());
        twentyEightQModel.setConstipation(constipation.getSelectedItemId());
        twentyEightQModel.setUrinate(urinate.getSelectedItemId());
        twentyEightQModel.setStart_feeding(start_feeding.getSelectedItemId());
        twentyEightQModel.setFed_birth(fed_birth.getSelectedItemId());
        twentyEightQModel.setChild_fed(child_fed.getSelectedItemId());

        i_fragmentlistener.ansQuestionPregnant(twentyEightQModel);

    }
}
