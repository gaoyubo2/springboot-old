package cn.cestc.os.desktop.utils;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 字符串处理类
 *
 * @author weigang
 */
@SuppressWarnings("unchecked")
public class StringUtil {

    /**
     * 静态工厂方法，禁止New新实例
     */
    private StringUtil() {

    }

    /**
     * 对字符串进行UTF-8编码 对于s为null时转为空字符串。
     */
    public static String urlEncode(String s) {
        String str = "";
        try {
            if (s == null)
                s = "";
            str = URLEncoder.encode(s, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return str;
    }

    /**
     * 对字符串进行UTF-8解码 对于s为null时转为空字符串
     */
    public static String urlDecode(String s) {
        String str = "";
        try {
            if (s == null)
                s = "";
            str = URLDecoder.decode(s, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }

    /**
     * 从指定的属性文件中读取指定字符串.
     *
     * @param propfile
     * @return
     */
    public static String getStrFromProp(String propfile, String prop) {
        String propStr = "";
        try {
            InputStream inad = StringUtil.class.getResourceAsStream(propfile);
            Properties pros = new Properties();
            pros.load(inad);
            inad.close();
            propStr = pros.getProperty(prop);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
        return propStr;
    }

    /**
     * 将"null"字符串或者null值转换成""
     */
    public static String nullStringToEmptyString(String str) {
        if (str == null || str.equals("null")) {
            str = "";
        }
        return str;
    }

    /**
     * 是否是数字
     */
    public static boolean isNumber(String str) {
        str = StringUtil.nullStringToEmptyString(str);
        String regex = "\\d+.?\\d+";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(str);
        boolean validate = m.matches();
        return validate;
    }

    /**
     * wml编码
     */
    public static String wmlEncode(String str) {
        if (str == null) {
            return "";
        } else {
            str = !str.contains("&amp;")
                    && !str.contains("&lt;")
                    && !str.contains("&gt;")
                    && !str.contains("&apos;")
                    && !str.contains("&quot;") ? str.replaceAll("&", "&amp;") : str;
            str = str.contains("&lt;") ? str : str.replaceAll("<", "&lt;");
            str = str.contains("&gt;") ? str : str.replaceAll(">", "&gt;");
            str = str.contains("&apos;") ? str : str.replaceAll("'", "&apos;");
            str = str.contains("&quot;") ? str : str.replaceAll("\"", "&quot;");
            return str;
        }
    }

    /**
     * 转换16进制为字符
     *
     * @param source
     * @return
     */
    public static String HexToChar(String source) {
        String flag = "&#";
        if (null == source || "".equals(source)) {
            return "";
        }
        if (source.indexOf(flag) >= 0) {
            StringBuffer newValue = new StringBuffer();
            StringTokenizer st = new StringTokenizer(source, ";");
            while (st.hasMoreElements()) {
                String value = "" + st.nextElement();
                if ("".equals(value)) {
                    continue;
                }
                int flagPosition = value.indexOf(flag);

                if (flagPosition >= 0) {//
                    if (flagPosition > 0) {// 标志位前还有非unicode字符
                        String others = value.substring(0, flagPosition);
                        newValue.append(others);
                    }
                    value = value.substring(flagPosition + flag.length());

                    boolean is16Int = false;
                    if (value.startsWith("x")) {
                        value = value.replace("x", "");
                        is16Int = true;
                    }
                    try {
                        int OctalInt = 0;
                        if (is16Int) {
                            OctalInt = Integer.parseInt(value, 16);
                        } else {
                            OctalInt = Integer.parseInt(value);
                        }
                        newValue.append(String.valueOf((char) OctalInt));
                    } catch (NumberFormatException e) {// 过滤unicode编码中包含的&nbsp;等符号
                        // add by norby
                        continue;
                    }
                } else {
                    newValue.append(value);
                }
            }
            source = newValue.toString();
        }
        return source;
    }

    /**
     * 检测字符串里是否有中文字符
     */
    public static boolean chinese(String str) {
        if (str == null) {
            return false;
        }
        if (str.getBytes().length == str.length()) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 检测字符串是否为空，或者空字符串
     *
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {
        str = StringUtil.nullStringToEmptyString(str);
        String regex = "\\s*";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(str);
        boolean validate = m.matches();
        return validate;
    }

    /**
     * 将字符串变为list（用asArraylist方法无法删除，在此通过循环处理）
     *
     * @param str,String separator
     * @return
     */
    public static List<String> StringToList(String str, String separator) {
        List<String> list = new ArrayList<String>();
        String[] strlist = str.split(separator);
        for (String string : strlist) {
            list.add(string);
        }
        return list;
    }

    /**
     * 将list变为字符串并用separator间隔
     *
     * @param
     * @return
     */
    public static String listToString(List<String> list, String separator) {
        String str = "";
        for (String string : list) {
            str += string + separator;
        }
        if (list.size() > 0) {
            str = str.substring(0, str.length() - separator.length());
        }
        return str;
    }

    /**
     * 将stringlist变为字符串并用separator间隔
     *
     * @param str
     * @return
     */
    public static String stringlistToString(String[] list, String separator) {
        String str = "";
        for (String string : list) {
            str += string + separator;
        }
        if (list.length > 0) {
            str = str.substring(0, str.length() - separator.length());
        }
        return str;
    }

    /**
     * 删除字符串list里面的值
     *
     * @param str
     * @return
     */
    public static List<String> deleStringlistByValue(List<String> list, String value) {
        if (list.size() > 0) {
            Iterator<String> listIterator = list.iterator();
            while (listIterator.hasNext()) {
                String e = listIterator.next();
                if (e.equals(value)) {
                    listIterator.remove();
                }
            }
        }
        return list;
    }

    public static String toStrList(List<String> list) {
        StringBuilder strResult = new StringBuilder();
        for (String str : list) {
            strResult.append("'").append(str).append("',");
        }

        String appNameList = "";

        if (list.size() > 0) {
            appNameList = strResult.substring(0, strResult.length() - 1);
        }
        return appNameList;
    }

}
