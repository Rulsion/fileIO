package com.rulsion.file.util;

public class SysUtil {

    public static boolean isEmpty(Object obj) {
        if (obj == null) {
            return true;
        } else {
            if (obj instanceof String) {
                if ("".equals(obj.toString().trim())) {
                    return true;
                }
            } else if (obj instanceof StringBuffer) {
                if ("".equals(obj.toString().trim())) {
                    return true;
                }
            }
        }
        return false;

    }

    public static boolean isNotEmpty(Object obj) {
        return !isEmpty(obj);
    }

    public static int hashCompare(Object j,Object o){
        int diff = j.hashCode() - o.hashCode();
        if (diff > 0) {
            return 1;
        } else {
            return -1;
        }
    }

}
