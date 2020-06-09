package com.koit.project_prm391_1.object;

import java.text.NumberFormat;
import android.os.Build;
import java.util.Locale;

public class CurrencyProcessing {
    public static String covertString(int money) {
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
        Locale localeVN = new Locale("vi", "VN");
        NumberFormat currencyVN = NumberFormat.getCurrencyInstance(localeVN);
        String str1 = currencyVN.format(money);
        String words[] = str1.split("\\s+");
        return words[0]+" VNĐ";
    }
}
