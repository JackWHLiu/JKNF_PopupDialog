package cn.jackwhliu.jknf.popupdialog;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.lwh.jackknife.widget.popupdialog.AbstractDialogView;
import com.lwh.jackknife.widget.popupdialog.DialogView;
import com.lwh.jackknife.widget.popupdialog.PopupDialog;

public class MainActivity extends Activity {

    private Button btn_toggle_popupdialog;
    PopupDialog popupDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_toggle_popupdialog = findViewById(R.id.btn_toggle_popupdialog);
        btn_toggle_popupdialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupDialog.toggle();
            }
        });
        final DialogView dialogView = new DialogView(R.layout.dialog_view,
                AbstractDialogView.DEFAULT_SHADOW_COLOR);   //0x60000000
        dialogView.setCanTouchOutside(false);    //仅当设置了阴影背景有效，默认false，阴影处控件不可点
        dialogView.setOnInflateListener(new DialogView.OnInflateListener() {
            @Override
            public void onInflateFinish(View contentView) {
                TextView tv = (TextView) dialogView.findViewById(R.id.tv_menu_box);
                tv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(MainActivity.this, "菜单面板", Toast.LENGTH_SHORT).show();
                        popupDialog.dismiss();
                    }
                });
            }
        });
        popupDialog = new PopupDialog.Builder(this)
                .setDialogView(dialogView)
                .create();
    }
}
