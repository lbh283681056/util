package com.base.util;

import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;

public class TextUtil {

	/**
	 * 设置TextView部分文字变色
	 * 
	 * 属性 textView
	 *            控件
	 * 属性 foregroundColorSpan
	 *            颜色
	 * 属性 starIndex
	 *            变色初始位置
	 * 属性 endIndex
	 *            变色结束位置
	 */
	public static void setTextForColor(TextView textView,ForegroundColorSpan foregroundColorSpan, int starIndex, int endIndex) {
		SpannableStringBuilder style = new SpannableStringBuilder(textView.getText().toString());
		style.setSpan(foregroundColorSpan, starIndex, endIndex,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		textView.setText(style);
	}
}
