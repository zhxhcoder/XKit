package com.zhxh.xlibkit.ui;

import android.content.Context;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.widget.TextView;

import com.zhxh.xlibkit.common.StringUtil;

/**
 * Created by zhxh on 2018/7/10
 */
public final class DisplayUtil {


    /**
     * 将px值转换为dip或dp值，保证尺寸大小不变
     *
     * @param pxValue
     * @return
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 将dip或dp值转换为px值，保证尺寸大小不变
     *
     * @param dipValue
     * @return
     */
    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     *
     * @param pxValue
     * @return
     */
    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param spValue
     * @return
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    public static int parseColor(String colorString) {

        if (StringUtil.isColorStr(colorString)) {
            return Color.parseColor(colorString);
        }
        return 0;
    }

    /**
     * 获取屏幕高度(px)
     */
    public static int getScreenHeight(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    /**
     * 获取屏幕宽度(px)
     */
    public static int getScreenWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    public static SpannableString getClickSpanStr(String srcStr, String clickStr, ClickableSpan clickableSpan) {

        if (TextUtils.isEmpty(srcStr))
            return new SpannableString("--");

        if (TextUtils.isEmpty(clickStr) || !srcStr.contains(clickStr))
            return new SpannableString(srcStr);

        if (TextUtils.isEmpty(clickStr)) clickStr = "";

        SpannableString resultSpan = new SpannableString(srcStr);

        if (srcStr.contains(clickStr)) {
            resultSpan.setSpan(clickableSpan,
                    srcStr.indexOf(clickStr),
                    srcStr.indexOf(clickStr) + clickStr.length(),
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        return resultSpan;
    }

    public static void setClickSpan(TextView textView, String srcStr, String clickStr, ClickableSpan clickableSpan) {

        textView.setMovementMethod(LinkMovementMethod.getInstance());

        if (TextUtils.isEmpty(srcStr))
            return;

        if (TextUtils.isEmpty(clickStr) || !srcStr.contains(clickStr))
            return;

        if (TextUtils.isEmpty(clickStr)) clickStr = "";

        SpannableString resultSpan = new SpannableString(srcStr);

        if (srcStr.contains(clickStr)) {
            resultSpan.setSpan(clickableSpan,
                    srcStr.indexOf(clickStr),
                    srcStr.indexOf(clickStr) + clickStr.length(),
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        textView.setText(resultSpan);
    }

}
