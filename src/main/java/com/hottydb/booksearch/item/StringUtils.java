package com.hottydb.booksearch.item;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class StringUtils {
    public static String normalize(String in) {
        if (in == null) return null;
        String s = in.toLowerCase();
        return Normalizer.normalize(s, Normalizer.Form.NFKC);
    }

    public static List<String> extractKeywords(String in) {
        List<String> keywords = new ArrayList<>();
        if (in == null) return keywords;
        String s = normalize(in);
        Pattern p = Pattern.compile("[a-z]{3,}");
        Matcher m = p.matcher(s);
        while (m.find()) keywords.add(m.group());
        return keywords.stream().distinct().collect(Collectors.toList());
    }

    public static String removeMeaningless(String in) {
        return in.replaceAll("[あ-ん]+", " ");
    }

    public static String reviewRating(double reviewAverage) {
        for (double i = 0.0; i < 5.0; i++) {
            if (reviewAverage < i + 0.25) return String.format("%d", (int)i);
            if (reviewAverage < i + 0.75) return String.format("%.1f", i + 0.5);
        }
        return "5";
    }
}
