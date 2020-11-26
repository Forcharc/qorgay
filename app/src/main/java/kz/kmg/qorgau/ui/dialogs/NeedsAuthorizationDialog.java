package kz.kmg.qorgau.ui.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import kz.kmg.qorgau.R;

public class NeedsAuthorizationDialog extends DialogFragment {

    boolean goToAuth = false;

    public void setListener(NeedsAuthorizationDialogListener listener) {
        this.listener = listener;
    }

    NeedsAuthorizationDialogListener listener = null;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder =  new AlertDialog.Builder(getContext());
        builder.setPositiveButton(R.string.authorize, (dialog, which) -> {
            if (listener != null) {
                listener.onGoToAuth();
                goToAuth = true;
            }
            dialog.dismiss();
        });

        builder.setNegativeButton(R.string.cancel, (dialog, which) -> {
            dialog.dismiss();
        });


        builder.setTitle(R.string.error);
        builder.setMessage(R.string.auth_to_see);
        return builder.create();
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);

        if (listener != null && !goToAuth) {
            listener.onGoBack();
        }
    }

    public interface NeedsAuthorizationDialogListener {
        void onGoBack();
        void onGoToAuth();
    }
}
