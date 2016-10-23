package com.example.unittest.ca5.unittestexample;

import android.content.Context;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.Mockito.*;
import org.mockito.ArgumentMatcher;
import org.robolectric.RobolectricGradleTestRunner; import org.robolectric.RuntimeEnvironment; import org.robolectric.annotation.Config; import static org.junit.Assert.assertEquals; import static org.junit.Assert.assertTrue; import static org.mockito.Matchers.argThat; import static org.mockito.Mockito.spy; /** * Created by ca5 on 2016/10/23. */
@RunWith(RobolectricGradleTestRunner.class)
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
        SampleClass sample = new SampleClass(mContext);

        sample.setSharedPreference("hoge");
        assertEquals("hoge", sample.getSharedPreference());

        //Async

    }

    @After
    public void tearDown(){
        //dummy
    }
}