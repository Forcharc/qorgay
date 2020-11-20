package kz.kmg.qorgau.ui.dialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import kz.kmg.qorgau.R;

public class QorgayCreatedDialog extends BaseBottomSheetDialogFragment{
    @BindView(R.id.b_close)
    public Button closeButton;

    public CreateQorgaySuccessDialogListener listener;

    public interface CreateQorgaySuccessDialogListener {
        void onDialogClosed();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.dialog_create_qorgay_success, container, false);
        ButterKnife.bind(this, root);
        return root;
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        if (listener != null) {
            listener.onDialogClosed();
        }
        super.onDismiss(dialog);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        closeButton.setOnClickListener(v -> {
                    dismiss();
                }
        );
    }


    @Override
    void configureLayout(FrameLayout bottomSheet) {
        BottomSheetBehavior.from(bottomSheet).setState(BottomSheetBehavior.STATE_COLLAPSED);
        BottomSheetBehavior.from(bottomSheet).setSkipCollapsed(true);
    }
}
