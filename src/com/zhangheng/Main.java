package com.zhangheng;

import com.zhangheng.bean.Message;
import com.zhangheng.util.FiletypeUtil;

import java.io.File;

public class Main {

    public static void main(String[] args) {

//        String now = TimeUtil.toTime(new Date(),TimeUtil.enDateFormat_Detailed);
////        System.out.println(now);
////        String now1 = TimeUtil.toTime(new Date(),TimeUtil.enDateFormat_Detailed);
////        System.out.println(now1);

//        String ago="2022-03-04 13:50:00";
//        int difference = TimeUtil.timeDifference(now, now1,TimeUtil.enDateFormat_Detailed,TimeUtil.MilliSecond);
//        System.out.println(difference);

//        System.out.println(TimeUtil.fewDaysAgo(TimeUtil.toTime(new Date()),2));
//        System.out.println(FormatUtil.isLetterDigit(RandomrUtil.createPassWord(12,RandomrUtil.Number+RandomrUtil.Lowercase)));

//        System.out.println(RandomrUtil.createRandom(2));

//        Message.printLog(MathUtil.twoDecimalPlaces(16.6656));
        File file = new File("D:\\Users\\张恒\\IdeaProjects\\ZH_Utils\\src\\com\\zhangheng\\bean\\Message.java");
        System.out.println(FiletypeUtil.getFileType(file));
        Message.printLog(1);
    }
}
