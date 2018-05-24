package com.boyong.youhuishou.data;

import com.boyong.youhuishou.BuildConfig;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {
    private final static String SORT = "QSFETTUGBVNEREWR";
    private final static String VERSION_CODE = BuildConfig.VERSION_NAME;
    private final static String DEVICES = "Android";


    public static String getMD5(String... str) {
        try {
            MessageDigest bmd5 = MessageDigest.getInstance("MD5");
            String result = SORT + "|" + VERSION_CODE + "|" + DEVICES;

            for (String temp : str) {
                result += "|" + temp;
            }
            bmd5.update(result.getBytes());
            int i;
            StringBuffer buf = new StringBuffer();
            byte[] b = bmd5.digest();// 加密
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }

            return buf.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getMD5WithoutBasic(String... str) {
        try {
            MessageDigest bmd5 = MessageDigest.getInstance("MD5");
            String result = "";
            for (String temp : str) {
                result = temp + "|";
            }
            result = result.substring(0, result.length() - 2);
            bmd5.update(result.getBytes());
            int i;
            StringBuffer buf = new StringBuffer();
            byte[] b = bmd5.digest();// 加密
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            return buf.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
}
