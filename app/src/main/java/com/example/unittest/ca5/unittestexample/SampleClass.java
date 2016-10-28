package com.example.unittest.ca5.unittestexample;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;

/**
 * Created by ca5 on 2016/10/23.
 */

public class SampleClass implements SharedPreferenceListener{
    private final static String SHARED_PREFERENCE_KEY = "sample_key";
    private Context mContext = null;
    private SampleClassListener mSampleClassListener = null;
    public SampleClass(Context context){
        mContext = context;
    }
    public SampleClass(Context context, SampleClassListener listener){
        mSampleClassListener = listener;
        mContext = context;
    }
    private SampleClass(){} // do nothing

    public void setSharedPreference(String str){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(mContext);
        sp.edit().putString(SHARED_PREFERENCE_KEY, str).commit();
        System.out.println("set:" + getSharedPreference());
    }


    public String getSharedPreference(){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(mContext);
        return sp.getString(SHARED_PREFERENCE_KEY, null);
    }

    public void getSharedPreferenceAsyncWithListener(SharedPreferenceListener listener){
        new SharedPreferenceTask(listener).execute();
    }

    public void getSharedPreferenceAsync(){
        new SharedPreferenceTask(this).execute();
    }

    @Override
    public void receiveSharedPreference(String string) {
        if (mSampleClassListener != null){
            mSampleClassListener.receiveString(string);
        }
    }

    public class SharedPreferenceTask extends AsyncTask {
        SharedPreferenceListener mListener;
        public SharedPreferenceTask(SharedPreferenceListener listener){
            mListener = listener;
        }
        private SharedPreferenceTask(){}
        @Override
        protected Object doInBackground(Object[] objects) {
            try{
                //5秒待つ
                Thread.sleep(5000);
                SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(mContext);
                mListener.receiveSharedPreference(sp.getString(SHARED_PREFERENCE_KEY, null));
            }catch(Exception e){}

            return null;
        }
    }
}
