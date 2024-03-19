package cn.cestc.os.desktop.utils;

public class ParamDataHandlerUtils
{

    //处理地址，在图片名前加s_变为缩略图名
    public static String getFileInfo(String str, String mode)
    {
        if (str != null && !str.equals(""))
        {
            String[] strsplit = str.split("\\.");
            if (mode != null && !mode.equals(""))
            {
                if (mode.equals("path"))
                {
                    return str.substring(0, str.lastIndexOf("/"));
                } else if (mode.equals("name"))
                {
                    return str.substring(str.lastIndexOf("/") + 1).split("\\.")[0];
                } else if (mode.equals("ext"))
                {
                    return strsplit[strsplit.length - 1];
                } else if (mode.equals("simg"))
                {
                    return getFileInfo(str, "path") + "/s_" + getFileInfo(str, "name") + ".jpg";
                }
            }
        }
        return "";
    }

    public static String getFileInfoAndSetSuffix(String str, String Suffix)
    {
        if (str != null && !str.equals(""))
        {
            return getFileInfo(str, "path") + "/s_" + getFileInfo(str, "name") + "." + Suffix;
        }
        return "";
    }
}
