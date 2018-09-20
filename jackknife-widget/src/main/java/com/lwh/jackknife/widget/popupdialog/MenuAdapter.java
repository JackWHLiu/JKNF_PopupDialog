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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lwh.jackknife.widget.R;

import java.util.List;

class MenuAdapter extends BaseAdapter {

    private static final int INVALID = -1;
    private static final int INVALID_COLOR = 0;
    private List<String> mMenuItems;
    private LayoutInflater mInflater;
    private float mMenuItemTextSize = INVALID;
    private int mMenuItemTextColor = INVALID_COLOR;
    private int mMenuItemBackground = INVALID_COLOR;

    public MenuAdapter(LayoutInflater inflater, List<String> itemNames) {
        this.mInflater = inflater;
        this.mMenuItems = itemNames;
    }

    public float getMenuItemTextSize() {
        return mMenuItemTextSize;
    }

    public void setMenuItemTextSize(float textSize) {
        this.mMenuItemTextSize = textSize;
    }

    public void setMenuItemTextColor(int textColor) {
        this.mMenuItemTextColor = textColor;
    }

    public void setMenuBackground(int background) {
        this.mMenuItemBackground = background;
    }

    @Override
    public int getCount() {
        return mMenuItems.size();
    }

    @Override
    public Object getItem(int position) {
        return mMenuItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (null == convertView) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.jknf_item_popup_dialog_menu, parent, false);
            holder.textview_popup_dialog_menu_name = (TextView) convertView.findViewById(R.id.textview_popup_dialog_menu_name);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        convertView.requestFocus();
        holder.textview_popup_dialog_menu_name.setText(mMenuItems.get(position));
        if (mMenuItemTextSize != INVALID) {
            holder.textview_popup_dialog_menu_name.setTextSize(mMenuItemTextSize);
        }
        if (mMenuItemTextColor != INVALID_COLOR) {
            holder.textview_popup_dialog_menu_name.setTextColor(mMenuItemTextColor);
        }
        if (mMenuItemBackground != INVALID_COLOR) {
            GradientDrawable drawable = (GradientDrawable) holder.textview_popup_dialog_menu_name.getBackground();
            drawable.setColor(mMenuItemBackground);
        }
        if (getCount() == 1) {
            holder.textview_popup_dialog_menu_name.setBackgroundResource(R.drawable.jknf_popup_dialog_menu_single);
        } else if (getCount() == 2) {
            if (position == 0) {
                holder.textview_popup_dialog_menu_name.setBackgroundResource(R.drawable.jknf_popup_dialog_menu_top);
            }
            if (position == 1) {
                holder.textview_popup_dialog_menu_name.setBackgroundResource(R.drawable.jknf_popop_dialog_menu_bottom);
            }
        } else {
            if (position == 0) {
                holder.textview_popup_dialog_menu_name.setBackgroundResource(R.drawable.jknf_popup_dialog_menu_top);
            } else if (position == (mMenuItems.size() - 1)) {
                holder.textview_popup_dialog_menu_name.setBackgroundResource(R.drawable.jknf_popop_dialog_menu_bottom);
            }
        }
        return convertView;
    }

    private class ViewHolder {
        TextView textview_popup_dialog_menu_name;
    }
}
