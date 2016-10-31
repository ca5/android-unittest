package com.example.unittest.ca5.unittestexample;

import android.content.Context;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.Assert.assertTrue;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.timeout;

import org.robolectric.Robolectric;
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
    public void testSharedPreference() {

        //Sync
        SampleClass sample = new SampleClass(mContext);
        sample.setSharedPreference("hoge");
        assertEquals("hoge", sample.getSharedPreference());
    }


    @Test
    public void testSharedPreferenceAsync1() {
        //Async1 - 悪い例
        SampleClass sample = new SampleClass(mContext);
        sample.setSharedPreference("fuga");
        sample.getSharedPreferenceAsyncWithListener(new SharedPreferenceListener() {
            @Override
            public void receiveSharedPreference(String string) {
                //非同期処理なのでここが実行される前にテストが終わる可能性がある
                assertEquals("fuga", string);
                System.out.println("testSharedPreferenceAsync1:" + string);
            }
        });
    }

    boolean sharedPreference = true;
    @Test
    public void testSharedPreferenceAsync1_2() {
        //Async1 - とりあえずな例
        sharedPreference = false;
        SampleClass sample = new SampleClass(mContext);
        sample.setSharedPreference("piyo");
        sample.getSharedPreferenceAsyncWithListener(new SharedPreferenceListener() {
            @Override
            public void receiveSharedPreference(String string) {
                assertEquals("piyo", string);
                System.out.println("testSharedPreferenceAsync1_2:" + string);
                sharedPreference = true;
            }
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {}
        assertTrue(sharedPreference);
    }

    @Test
    public void testSharedPreferenceAsyncWithSpy() {
        //Async1
        SampleClass sampleAsync1 = new SampleClass(mContext);
        SampleClass sampleSpy1 = spy(sampleAsync1);
        sampleSpy1.setSharedPreference("hogehoge1");
        sampleSpy1.getSharedPreferenceAsync();
        verify(sampleSpy1, timeout(6000).times(1)).receiveSharedPreference(eq("hogehoge1"));


        //Async2
        SampleClass sampleAsync2 = new SampleClass(mContext, new SampleClassListener(){
            //dummy
            @Override
            public void receiveString(String string) {
                return;
            }
        });
        SampleClass sampleSpy2 = spy(sampleAsync2);
        sampleSpy2.setSharedPreference("hogehoge2");
        sampleSpy2.getSharedPreferenceAsync();
        verify(sampleSpy2, timeout(6000).times(1)).receiveSharedPreference(eq("hogehoge2"));
    }

    @After
    public void tearDown(){
        //dummy
    }
}