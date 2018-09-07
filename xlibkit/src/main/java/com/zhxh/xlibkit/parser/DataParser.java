package com.zhxh.xlibkit.parser;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhxh on 2018/9/7
 */
public class DataParser {

    public Map<String, List<? extends ITypeItem>> listToMap(List<? extends ITypeItem> list) {
        Map<String, List<? extends ITypeItem>> map = new HashMap<>();

        String type = "";//必须初始化为""
        List<? extends ITypeItem> typeList = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            if (type.equals(list.get(i).getType())) {
                typeList.add(null);
            } else {
                if (!TextUtils.isEmpty(type)) {
                    map.put(type, typeList);
                }
                type = list.get(i).getType();
                list.get(i).isSubTitle(true);
                typeList.clear();
            }
        }
        return map;
    }

}
