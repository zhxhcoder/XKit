package com.zhxh.xlibkit.design;

import android.graphics.Color;

/**
 * Created by zhxh on 2018/6/29
 */
public class ViewUtil {

    public static int parseColor(String colorString) {

        if (StringUtil.isColorStr(colorString)) {
            return Color.parseColor(colorString);
        }
        return 0;
    }
}
