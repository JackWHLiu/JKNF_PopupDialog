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

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

class MenuPopupDialog extends PopupDialog {

    private MenuPopupDialog(Builder builder) {
        super(builder);
        LayoutInflater inflater = LayoutInflater.from(getOwnActivity());
        ViewGroup contentView = (ViewGroup) mDialogView.performInflateView(inflater, mDecorView);
        contentView.setLayoutParams(mDialogView.getLayoutParams());
        contentView.addView(createView(builder, inflater));
    }

    private View createView(Builder builder, LayoutInflater inflater) {
        if (mDialogView instanceof MenuDialogView) {
            MenuDialogView menuDialogView = (MenuDialogView) mDialogView;
            menuDialogView.setOnMenuClickListener(builder.getOnMenuClickListener());
            menuDialogView.setMenuItems(builder.getMenuItems());
            menuDialogView.setShowCancelMenu(builder.isShowCancel());
            menuDialogView.setCancelText(builder.getCancelText());
            menuDialogView.setMenuTextColor(builder.getMenuItemTextColor());
            menuDialogView.setMenuTextSize(builder.getMenuItemTextSize());
            menuDialogView.setMenuItemBackground(builder.getMenuItemBackground());
            menuDialogView.setCancelMenuBackground(builder.getCancelBackground());
            mDialogView = menuDialogView;
        }
        return mDialogView.performInflateView(inflater, mDecorView);
    }

    static class Builder extends PopupDialog.Builder {

        private List<String> menuItems;
        private MenuDialogView.OnMenuClickListener mListener;
        private int cancelBackground = INVALID_COLOR;
        private boolean isShowCancel = true;
        private String cancelText;
        private float menuItemTextSize = INVALID;
        private int menuItemTextColor = INVALID_COLOR;
        private int menuItemBackground = INVALID_COLOR;

        public Builder(Context context) {
            super(context);
            dialogView = new MenuDialogView();
        }

        public List<String> getMenuItems() {
            return menuItems;
        }

        public Builder setMenuItems(List<String> itemNames) {
            this.menuItems = itemNames;
            return this;
        }

        public MenuDialogView.OnMenuClickListener getOnMenuClickListener() {
            return mListener;
        }

        public Builder setOnMenuClickListener(MenuDialogView.OnMenuClickListener
                                                      listener) {
            this.mListener = listener;
            return this;
        }

        public int getCancelBackground() {
            return cancelBackground;
        }

        public Builder setCancelBackground(int cancelBackground) {
            this.cancelBackground = cancelBackground;
            return this;
        }

        public MenuPopupDialog create() {
            if (dialogView == null) {
                throw new IllegalArgumentException("lack dialog view.");
            }
            return new MenuPopupDialog(this);
        }

        public boolean isShowCancel() {
            return isShowCancel;
        }

        public PopupDialog.Builder setShowCancel(boolean isShowCancel) {
            this.isShowCancel = isShowCancel;
            return this;
        }

        public String getCancelText() {
            return cancelText;
        }

        public PopupDialog.Builder setCancelText(String cancelText) {
            this.cancelText = cancelText;
            return this;
        }

        public int getMenuItemTextColor() {
            return menuItemTextColor;
        }

        public PopupDialog.Builder setMenuItemTextColor(int textColor) {
            this.menuItemTextColor = textColor;
            return this;
        }

        public float getMenuItemTextSize() {
            return menuItemTextSize;
        }

        public PopupDialog.Builder setMenuItemTextSize(float textSize) {
            this.menuItemTextSize = textSize;
            return this;
        }

        public int getMenuItemBackground() {
            return menuItemBackground;
        }

        public PopupDialog.Builder setMenuItemBackground(int background) {
            this.menuItemBackground = background;
            return this;
        }
    }
}
