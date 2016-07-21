package com.happyfuncorp.adlibapp;


public interface SetupListener {
    public void networkAvailabilityChanged(String networkKey, boolean isAdd);
    public void latLongChanged(String lat, String log);
    public void genderChanged(String gender);
    public void ageChanged(String age);
    public void adUnitChanged(String id);
}
