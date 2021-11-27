package com.example.lenovocompetitionapp.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Surface Pro 6
 */
public class StringHelper {
    private static final Pattern IMAGE_TAG_PATTERN = Pattern.compile("<(img|IMG)(.*?)>");
    private static final Pattern IMAGE_SRC_PATTERN = Pattern.compile("(src|SRC)=\"(.*?)\"");

    public static ArrayList<String> toImage(String srcStr) {
        ArrayList<String> targets = new ArrayList<>();
        // 针对src标签
        // 先匹配img标签
        Matcher imageTagMatcher = IMAGE_TAG_PATTERN.matcher(srcStr);
        while (imageTagMatcher.find()) {
            String image = Objects.requireNonNull(imageTagMatcher.group(2)).trim();
            // 获取src后面的内容
            Matcher imageSrcMatcher = IMAGE_SRC_PATTERN.matcher(image);
            String src = null;
            if (imageSrcMatcher.find()) {
                src = Objects.requireNonNull(imageSrcMatcher.group(2)).trim();
            }
            if (src == null || src.isEmpty()) {
                continue;
            }
            targets.add(src);
        }
        return targets;
    }

    /**
     * 时间戳转换成日期格式字符串
     * @param seconds 精确到秒的字符串
     * @return
     */
    public static String timeStamp2Date(String seconds) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
        return sdf.format(new Date(Long.parseLong(seconds+"000")));
    }
}
