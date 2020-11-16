package kz.kmg.qorgau.ui.create;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

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

        closeButton.setOnClickListener(v -> {
                    if (listener != null) {
                        listener.onDialogClosed();
                    }
                    dismiss();
                }
        );
    }
}
