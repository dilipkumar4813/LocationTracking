package iamdilipkumar.com.locationtracking.ui.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.provider.Settings;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import butterknife.ButterKnife;
import iamdilipkumar.com.locationtracking.R;

/**
 * Created on 24/05/17.
 *
 * @author dilipkumar4813
 * @version 1.0
 */

public class CustomDialog {

    private static Dialog buildDialog(Activity activity, String title, String message) {
        final Dialog builtDialog = new Dialog(activity);
        builtDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        builtDialog.setContentView(R.layout.custom_dialog);

        builtDialog.getWindow().
                setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                        WindowManager.LayoutParams.WRAP_CONTENT);

        //TextView tvTitle = (TextView) twoButtonDialog.findViewById(R.id.tv_dialog_title);
        TextView titleText = ButterKnife.findById(builtDialog, R.id.tv_dialog_title);
        titleText.setText(title);

        //TextView tvMessage = (TextView) twoButtonDialog.findViewById(R.id.tv_dialog_message);
        TextView messageText = ButterKnife.findById(builtDialog, R.id.tv_dialog_message);
        messageText.setText(message);

        return builtDialog;
    }

    public static void buildSingleButtonDialog(Activity activity, String title, String message) {
        final Dialog dialog = buildDialog(activity, title, message);

        Button cancelButton = ButterKnife.findById(dialog, R.id.btn_cancel);
        cancelButton.setVisibility(View.INVISIBLE);

        Button okButton = ButterKnife.findById(dialog, R.id.btn_yes);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    public static void buildGpsPermissionDialog(final Activity activity) {
        final Dialog dialog = buildDialog(activity, activity.getString(R.string.gps_error_title)
                , activity.getString(R.string.gps_error_description));
        dialog.setCanceledOnTouchOutside(false);

        Button cancelButton = ButterKnife.findById(dialog, R.id.btn_cancel);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        Button okButton = ButterKnife.findById(dialog, R.id.btn_yes);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                activity.startActivity(intent);
            }
        });

        dialog.show();
    }
}
