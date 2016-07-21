package com.happyfuncorp.adlibapp;


import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;

public class SetupFragment extends Fragment implements CompoundButton.OnCheckedChangeListener {

    public static final String TAG = "Choose Network Fragment";

    private SetupListener mCallback;

    public SetupFragment() {
        // Empty constructor.
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (SetupListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement NetworkAvailabilityListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallback = null;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.setup_layout, container, false);

        ((CheckBox) rootView.findViewById(R.id.bt_adlib)).setOnCheckedChangeListener(this);
        ((CheckBox) rootView.findViewById(R.id.bt_admob)).setOnCheckedChangeListener(this);
        ((CheckBox) rootView.findViewById(R.id.bt_inmobi)).setOnCheckedChangeListener(this);
        ((CheckBox) rootView.findViewById(R.id.bt_conversant)).setOnCheckedChangeListener(this);
        ((CheckBox) rootView.findViewById(R.id.bt_applovin)).setOnCheckedChangeListener(this);
        ((CheckBox) rootView.findViewById(R.id.bt_tapit)).setOnCheckedChangeListener(this);
        ((CheckBox) rootView.findViewById(R.id.bt_startapp)).setOnCheckedChangeListener(this);

        ((RadioButton) rootView.findViewById(R.id.bt_male)).setOnCheckedChangeListener(this);
        ((RadioButton) rootView.findViewById(R.id.bt_female)).setOnCheckedChangeListener(this);
        ((RadioButton) rootView.findViewById(R.id.bt_none)).setOnCheckedChangeListener(this);

        return rootView;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        String key = "";
        switch (buttonView.getId()) {
            case R.id.bt_adlib:
                key = "AdLib|active";
                break;
            case R.id.bt_admob:
                key = "AdMob|active";
                break;
            case R.id.bt_inmobi:
                key = "InMobi|active";
                break;
            case R.id.bt_conversant:
                key = "Conversant|active";
                break;
            case R.id.bt_applovin:
                key = "AppLovin|active";
                break;
            case R.id.bt_tapit:
                key = "TapIt|active";
                break;
            case R.id.bt_startapp:
                key = "StartApp|active";
                break;
            case R.id.bt_male:
                if (isChecked) {
                    mCallback.genderChanged("male");
                }
                break;
            case R.id.bt_female:
                if (isChecked) {
                    mCallback.genderChanged("female");
                }
                break;
            case R.id.bt_none:
                if (isChecked) {
                    mCallback.genderChanged("");
                }
                break;
            default:
                break;
        }

        if (!TextUtils.isEmpty(key)) {
            mCallback.networkAvailabilityChanged(key, isChecked);
        }
    }

    @Override
    public void onPause() {
        EditText etAge = ((EditText) getView().findViewById(R.id.et_age));
        EditText etLat = ((EditText) getView().findViewById(R.id.et_lat));
        EditText etLon = ((EditText) getView().findViewById(R.id.et_lon));
        EditText etBannerUnitUnit = ((EditText) getView().findViewById(R.id.et_bottom_banner_unit_id));

        mCallback.adUnitChanged(etBannerUnitUnit.getText().toString());
        mCallback.ageChanged(etAge.getText().toString());
        mCallback.latLongChanged(etLat.getText().toString(), etLon.getText().toString());
        mCallback.ageChanged(etAge.getText().toString());


        super.onPause();
    }
}
