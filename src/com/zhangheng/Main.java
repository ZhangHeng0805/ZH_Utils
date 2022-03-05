package com.zhangheng;

import com.zhangheng.util.FormatUtil;
import com.zhangheng.util.RandomrUtil;
import com.zhangheng.util.TimeUtil;

import java.util.Date;

public class Main {

    public static void main(String[] args) {

        String now = TimeUtil.toTime(new Date(),TimeUtil.enDateFormat_Detailed);
        System.out.println(now);
        String now1 = TimeUtil.toTime(new Date(),TimeUtil.enDateFormat_Detailed);
        System.out.println(now1);
//        String ago="2022-03-04 13:50:00";
        int difference = TimeUtil.timeDifference(now, now1,TimeUtil.enDateFormat_Detailed,TimeUtil.MilliSecond);
        System.out.println(difference);

        System.out.println(TimeUtil.fewDaysAgo(TimeUtil.toTime(new Date()),2));
        System.out.println(FormatUtil.isLetterDigit(RandomrUtil.createPassWord(12,RandomrUtil.Number+RandomrUtil.Lowercase)));

    }
}
