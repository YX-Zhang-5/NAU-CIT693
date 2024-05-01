package edu.NAU.seclass.jobcompare;

import android.text.InputFilter;
import android.text.Spanned;

public class ChineseInputFilter implements InputFilter {

    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        // 检查源字符序列中的每个字符是否是中文字符
        for (int i = start; i < end; i++) {
            if (!isChinese(source.charAt(i))) {
                // 如果发现非中文字符，过滤掉这个字符
                return "";
            }
        }
        // 如果所有字符都是中文字符，允许输入
        return null;
    }

    private boolean isChinese(char ch) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(ch);
        return ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS;
    }
}
