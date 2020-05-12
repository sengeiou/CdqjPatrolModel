package com.cdqj.cdqjpatrolandroid.comstomview.loadingdialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.StringUtils;
import com.cdqj.cdqjpatrolandroid.R;


/**
 *  CustomProgressDialog
 *  自定义progressDialog  等待条
 */
public class CustomProgressDialog extends Dialog {

	private Context context;

	private static CustomProgressDialog customProgressDialog = null;

	public CustomProgressDialog(Context context, boolean cancelable,
                                OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
	}

	public CustomProgressDialog(Context context, int theme) {
		super(context, theme);
	}

	public CustomProgressDialog(Context context) {
		super(context);
		this.context = context;
	}

	public static CustomProgressDialog createDialog(Context context) {
		customProgressDialog = new CustomProgressDialog(context,
				R.style.CustomProgressDialog);
		customProgressDialog.setContentView(R.layout.cdqj_patrol_custom_progress_dialog);
		customProgressDialog.getWindow().getAttributes().gravity = Gravity.CENTER;

		return customProgressDialog;
	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		if (customProgressDialog == null) {
			return;
		}
		ImageView imageView = customProgressDialog
				.findViewById(R.id.loadingImageView);
		AnimationDrawable animationDrawable = (AnimationDrawable) imageView
				.getBackground();
		animationDrawable.start();
	}

	public CustomProgressDialog setTitle(String strTitle) {
		return customProgressDialog;
	}

	public CustomProgressDialog setMessage(String message) {
		TextView tvMsg = customProgressDialog
				.findViewById(R.id.id_tv_loading_msg);

		if (tvMsg != null) {
			tvMsg.setText(message);
			tvMsg.setVisibility(StringUtils.isTrimEmpty(message)?View.GONE:View.VISIBLE);
		}

		return customProgressDialog;
	}

}
