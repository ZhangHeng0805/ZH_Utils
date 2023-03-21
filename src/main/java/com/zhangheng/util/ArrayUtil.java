package com.zhangheng.util;


/**
 * 数组工具
 * @author 张恒
 * @program: ZH_Utils
 * @email zhangheng.0805@qq.com
 * @date 2022-03-22 11:19
 */
public class ArrayUtil extends cn.hutool.core.util.ArrayUtil {

    /**
     * 判断数组里是否存在
     * @param arr 检查的数组
     * @param obj 被检查的对象
     * @return 如果存在则返回true，否则返回false
     */
    public static boolean exist(Object[] arr,Object obj){
        if (obj!=null) {
            if (arr != null && arr.length > 0) {
                if (arr[0].getClass()==obj.getClass()) {
                    for (Object o : arr) {
                        if (o.equals(obj)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }


}
