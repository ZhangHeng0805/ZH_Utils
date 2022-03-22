package com.zhangheng;

import com.zhangheng.bean.Message;

import com.zhangheng.file.TxtOperation;
import com.zhangheng.log.printLog.Log;
import com.zhangheng.util.*;


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

//        Message.printLog(EncryptUtil.getSignature("13733430842","10120812"));
//        Message.printLog(EncryptUtil.getSignature("13733430842","10120812."));
//        Message.printLog(EncryptUtil.getMyMd5("888888"));
//        Message.printLog(EncryptUtil.encrypt("888888",EncryptUtil.SHA1));

//        String datformat=TimeUtil.dateFormatArr.get(TimeUtil.Month)+"-"+TimeUtil.dateFormatArr.get(TimeUtil.Day);
//        String s = TimeUtil.toTime(TimeUtil.toDate("2022年03月18日 11:08:25", TimeUtil.cnDateFormat), datformat);
//        Message.printLog(s);
//        int i=0;
//        while (i<100){
//            Log.Info("张恒"+i);
//            i++;
//        }

        String passWord = RandomrUtil.createPassWord(32, "012");
        Log.Info(passWord);

    }
}
