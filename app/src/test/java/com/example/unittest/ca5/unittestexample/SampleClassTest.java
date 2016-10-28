package com.example.unittest.ca5.unittestexample;

import android.content.Context;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.timeout;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;

/** * Created by ca5 on 2016/10/23. */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class SampleClassTest {

    Context mContext = null;

    @Before
    public void setUp(){
        mContext = RuntimeEnvironment.application;
        System.out.println("context: " + mContext.toString());
    }

    @Test
    public void testSharedPreference(){

        //Sync
        SampleClass sample = new SampleClass(mContext);
        sample.setSharedPreference("hoge");
        assertEquals("hoge", sample.getSharedPreference());

        //Async
        SampleClass sampleAsync = new SampleClass(mContext, new SampleClassListener(){
            //dummy
            @Override
            public void receiveString(String string) {
                return;
            }
        });
        SampleClass sampleSpy = spy(sampleAsync);
        sampleSpy.setSharedPreference("fuga");
        sampleSpy.getSharedPreferenceAsync();
        verify(sampleSpy, timeout(2000).times(1)).receiveSharedPreference(eq("fuga"));
    }

    @After
    public void tearDown(){
        //dummy
    }
}