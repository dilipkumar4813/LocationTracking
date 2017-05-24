package iamdilipkumar.com.locationtracking.ui.dialog;

import android.app.Activity;
import android.app.Dialog;
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

    public static void buildOneButtonDialog(final Activity activity, String title, String message) {
        final Dialog oneButtonDialog = new Dialog(activity);
        oneButtonDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        oneButtonDialog.setContentView(R.layout.dialog_one_button);

        try {
            oneButtonDialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        //TextView tvTitle = (TextView) twoButtonDialog.findViewById(R.id.tv_dialog_title);
        TextView titleText = ButterKnife.findById(oneButtonDialog, R.id.tv_dialog_title);
        titleText.setText(title);

        //TextView tvMessage = (TextView) twoButtonDialog.findViewById(R.id.tv_dialog_message);
        TextView messageText = ButterKnife.findById(oneButtonDialog, R.id.tv_dialog_message);
        messageText.setText(message);

        Button okButton = ButterKnife.findById(oneButtonDialog, R.id.btn_yes);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                oneButtonDialog.dismiss();
                activity.finish();
            }
        });

        oneButtonDialog.show();
    }
}
