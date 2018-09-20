/*
 * Copyright (C) 2018 The JackKnife Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.lwh.jackknife.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

public class FlowLayout extends ViewGroup {

    public FlowLayout(Context context) {
        this(context, null);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int width = 0, height = 0, lineWidth = 0, lineHeight = 0;
        int childCount = getChildCount();
        for (int i=0;i<childCount;i++) {
            View child = getChildAt(i);
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
            MarginLayoutParams mlp = (MarginLayoutParams) child.getLayoutParams();
            int childWidth = child.getWidth()+mlp.leftMargin+mlp.rightMargin;
            int childHeight = child.getHeight()+mlp.topMargin+mlp.bottomMargin;
            if (childWidth+lineWidth > widthSize) {
                width = Math.max(width, lineWidth);
                lineWidth = childWidth;
                height += lineHeight;
                lineHeight = childHeight;
            } else {
                lineWidth += childWidth;
                lineHeight = Math.max(lineHeight, childHeight);
            }
            if (i == childCount - 1) {
                width = Math.max(width, lineWidth);
                height += lineHeight;
            }
        }
        int measuredWidth = widthMode == MeasureSpec.EXACTLY?widthSize:width;
        int measuredHeight = heightMode == MeasureSpec.EXACTLY?heightSize:height;
        setMeasuredDimension(measuredWidth, measuredHeight);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int width = getWidth();
        int lineWidth = 0, lineHeight = 0, maxChildHeight = 0;
        int childCount = getChildCount();
        for (int i=0;i<childCount;i++) {
            View child = getChildAt(i);
            MarginLayoutParams mlp = (MarginLayoutParams) child.getLayoutParams();
            int childWidth = child.getMeasuredWidth() + mlp.leftMargin + mlp.rightMargin;
            int childHeight = child.getMeasuredHeight() + mlp.topMargin + mlp.bottomMargin;
            if (childWidth + lineWidth > width) {
                lineWidth = 0;
                lineHeight += maxChildHeight;
                maxChildHeight = 0;
            }
            int left = lineWidth + mlp.leftMargin;
            int top = lineHeight + mlp.topMargin;
            int right = left + childWidth - mlp.leftMargin - mlp.rightMargin;
            int bottom = top + childHeight - mlp.topMargin - mlp.bottomMargin;
            lineWidth = right + mlp.rightMargin;
            maxChildHeight = Math.max(maxChildHeight, childHeight);
            child.layout(left, top, right, bottom);
        }
    }
}
