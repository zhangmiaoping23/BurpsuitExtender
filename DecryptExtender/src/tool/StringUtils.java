package tool;

import java.io.UnsupportedEncodingException;

/**
 * Created by zhangmp on 2018/12/5.
 */
public class StringUtils {
    public static String toUTF8(String s) {
      return toCodeString(s,"UTF-8");
    }

    public static String toCodeString(String s,String code) {
        String ret = "";
        try {
            ret = new String( s.getBytes(code) , code);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return ret;
    }
}
