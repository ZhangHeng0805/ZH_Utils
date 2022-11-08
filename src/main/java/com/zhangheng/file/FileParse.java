package com.zhangheng.file;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 文件解析
 * @author 张恒
 * @program: ZH_Utils
 * @email zhangheng.0805@qq.com
 * @date 2022-04-13 13:29
 */
public class FileParse {

    /**
     * 解析导出的通讯录vcf文件，获取昵称备注和对应的电话号码
     * 目前只解析过小米手机通讯录导出的vcf文件
     * @param path 文件路径
     * @param encoding 文件编码（有中文，通常使用UTF-8）
     * @return List<Sting>  昵称备注:电话号码
     */
    public static List<String> parseVCF(String path,String encoding){
        List<String> list = TxtOperation.readTxtFile(new File(path),encoding);
        List<String> strings = new ArrayList<String>();
        for (int i=0;i<list.size();i++){
            if (list.get(i).startsWith("TEL;")){
                String name="陌生人";
                int n=1;
                boolean flag=(!list.get(i-n).startsWith("FN:"))&&(!list.get(i-n).startsWith("BEGIN:"));
                if (list.get(i-n).startsWith("FN:")){
                    name=list.get(i-n).substring(list.get(i-n).lastIndexOf(":")+1);
                }
                while (flag){
                    if (list.get(i-n).startsWith("FN:")){
                        name=list.get(i-n).substring(list.get(i-n).lastIndexOf(":")+1);
                        break;
                    }
                    n++;
                }
                strings.add(name.replace(":","")+":"+list.get(i).substring(list.get(i).lastIndexOf(":")+1).replace(" ",""));
            }
        }
        return strings;
    }

    /**
     * 解析导出的通讯录vcf文件（UTF-8编码）
     * @param path 文件路径
     * @return List<Sting>  昵称备注:电话号码
     */
    public static List<String> parseVCF(String path){
        return parseVCF(path,"UTF-8");
    }

}
