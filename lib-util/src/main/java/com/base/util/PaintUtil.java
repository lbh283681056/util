package com.base.util;

import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;

/**
 * 文字工具类
  * 类名: PaintUtil
  * 描述
  * 作者 Comsys-linbinghuang
  * 时间 2014-11-3 下午4:31:59
  *
  */
public class PaintUtil {

    /**
     * 获取文字高度
     * 
     * 属性 paint
     * @return
     */
    public static float getTextHeight(Paint paint) {
        if (paint == null)
            return 0;
        FontMetrics fontMetrics = paint.getFontMetrics();
        return fontMetrics.bottom - fontMetrics.top;
    }

    /**
     * 获取文字宽度
     * 
     * 属性 paint
     * 属性 content
     * @return
     */
    public static float getTextWidth(Paint paint, String content) {
        if (paint == null)
            return 0;
        return paint.measureText(content);
    }
}
