package kz.kmg.qorgau.ui.create;

import android.app.Dialog;
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

public class CreateQorgaySuccessDialog extends BottomSheetDialogFragment {
    @BindView(R.id.b_close)
    Button closeButton;

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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setCancelable(false);

        closeButton.setOnClickListener(v -> {
                    if (listener != null) {
                        listener.onDialogClosed();
                    }
                    dismiss();
                }
        );
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        BottomSheetDialog bottomSheetDialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        bottomSheetDialog.setOnShowListener(dialog -> {
            BottomSheetDialog d = (BottomSheetDialog) dialog;
            FrameLayout bottomSheet = (FrameLayout) d.findViewById(com.google.android.material.R.id.design_bottom_sheet);
            CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams) bottomSheet.getLayoutParams();


            Resources r = getContext().getResources();
            int px = (int) TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    20,
                    r.getDisplayMetrics()
            );
            lp.setMargins(px, 0, px, 0);
            bottomSheet.requestLayout();


            BottomSheetBehavior.from(bottomSheet).setState(BottomSheetBehavior.STATE_COLLAPSED);
            BottomSheetBehavior.from(bottomSheet).setSkipCollapsed(true);
        });

        return bottomSheetDialog;
    }
}
