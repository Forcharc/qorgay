package kz.kmg.qorgau.ui.dialogs;

import android.app.Dialog;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.TypedValue;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public abstract class BaseBottomSheetDialogFragment extends BottomSheetDialogFragment {
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

            configureLayout(bottomSheet);
        });

        return bottomSheetDialog;
    }

    abstract void configureLayout(FrameLayout bottomSheet);
}
