package com.liuyanggang.microdream;

import com.liuyanggang.microdream.utils.MyListUtil;

import org.junit.Test;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        String str = "logo-20200528071112227.png,logo-20200528071112281.png";
        System.out.println(MyListUtil.setString(str));
    }
}