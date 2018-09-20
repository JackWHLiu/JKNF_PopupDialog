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

package com.lwh.jackknife.widget.popupdialog;

import android.graphics.drawable.GradientDrawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lwh.jackknife.widget.NestedScrollingListView;
import com.lwh.jackknife.widget.R;

import java.util.List;

class MenuDialogView extends DialogView {

    private MenuAdapter mMenuAdapter;
    private List<String> mMenuItems;
    private boolean mShowCancelMenu = true;
    private String mCancelText;
    private float mMenuItemTextSize = INVALID;
    private int mMenuItemTextColor = INVALID_COLOR;
    private int mMenuItemBackground = INVALID_COLOR;
    private int mCancelMenuBackground = INVALID_COLOR;
    private OnMenuClickListener mOnMenuClickListener;
    private static final int CANCEL_ITEM_POSITION = -1;

    public MenuDialogView() {
        super(R.layout.jknf_menu_popup_dialog);
        initShadow(DEFAULT_SHADOW_COLOR);
    }

    public interface OnMenuClickListener {
        void onClick(int position, String menuItem);
    }

    public void setMenuItems(List<String> itemNames) {
        this.mMenuItems = itemNames;
    }

    public void setOnMenuClickListener(OnMenuClickListener listener) {
        this.mOnMenuClickListener = listener;
    }

    public void setShowCancelMenu(boolean isShowCancel) {
        this.mShowCancelMenu = isShowCancel;
    }

    public void setMenuTextSize(float textSize) {
        this.mMenuItemTextSize = textSize;
    }

    public void setMenuTextColor(int textColor) {
        this.mMenuItemTextColor = textColor;
    }

    public void setCancelText(String text) {
        this.mCancelText = text;
    }

    public void setMenuItemBackground(int background) {
        this.mMenuItemBackground = background;
    }

    public void setCancelMenuBackground(int background) {
        this.mCancelMenuBackground = background;
    }

    @Override
    protected void addContent(LayoutInflater inflater, ViewGroup parent, LinearLayout viewRoot) {
        super.addContent(inflater, parent, viewRoot);
        mMenuAdapter = new MenuAdapter(inflater, mMenuItems);
        mMenuAdapter.setMenuItemTextSize(mMenuItemTextSize);
        mMenuAdapter.setMenuItemTextColor(mMenuItemTextColor);
        mMenuAdapter.setMenuBackground(mCancelMenuBackground);
        View contentView = getContentView();
        NestedScrollingListView listView = (NestedScrollingListView) contentView
                .findViewById(R.id.listview_popup_dialog_menu);
        listView.setAdapter(mMenuAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mOnMenuClickListener != null) {
                    mOnMenuClickListener.onClick(position, mMenuItems.get(position));
                }
                mOnCancelListener.onCancel();
            }
        });
        final TextView cancelTextView = (TextView) contentView
                .findViewById(R.id.textview_popup_dialog_cancel);
        if (mMenuItemTextSize != INVALID) {
            cancelTextView.setTextSize(mMenuItemTextSize);
        }
        if (mMenuItemTextColor != INVALID_COLOR) {
            cancelTextView.setTextColor(mMenuItemTextColor);
        }
        if (mMenuItemBackground != INVALID_COLOR) {
            GradientDrawable drawable = (GradientDrawable) cancelTextView.getBackground();
            drawable.setColor(mMenuItemBackground);
        }
        if (mCancelMenuBackground != INVALID_COLOR) {
            GradientDrawable drawable = (GradientDrawable) cancelTextView.getBackground();
            drawable.setColor(mCancelMenuBackground);
        }
        if (!TextUtils.isEmpty(mCancelText)) {
            cancelTextView.setText(mCancelText);
        }
        if (mShowCancelMenu) {
            cancelTextView.setVisibility(View.VISIBLE);
        } else {
            cancelTextView.setVisibility(View.GONE);
        }
        cancelTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnMenuClickListener != null) {
                    mOnMenuClickListener.onClick(CANCEL_ITEM_POSITION,
                            cancelTextView.getText().toString());
                }
                mOnCancelListener.onCancel();
            }
        });
    }
}
