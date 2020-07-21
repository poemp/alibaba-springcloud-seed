package org.poem;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author poem
 */
public class StringUtils extends org.apache.commons.lang3.StringUtils {


    /**
     * 判断一串字符串中是否全是中文，用来校验中文名
     *
     * @param str
     * @return
     */
    public static boolean isAllChinese(String str) {
        //遍历所有字符
        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            //中文在unicode编码中所在的区间为0x4E00-0x9FA5
            if (ch < 0x4E00 || ch > 0x9FA5) {
                //不在这个区间，说明不是中文字符，返回false
                return false;
            }
        }
        //全部在中文区间，说明全部是中文字符，返回true
        return true;
    }

    /**
     * 把特殊字符全替换成下划线
     *
     * @param character
     * @return
     */
    public static String getSpecialCharacter(String character) {
        String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(character);
        String name =  m.replaceAll("_").trim().toLowerCase();
        if (name.length() > 64){
            return name.substring(0,64);
        }
        return name;
    }

}
