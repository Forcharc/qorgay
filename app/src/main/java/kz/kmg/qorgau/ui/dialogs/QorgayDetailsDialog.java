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
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import kz.kmg.qorgau.R;
import kz.kmg.qorgau.data.model.list.QorgayModel;

public class QorgayDetailsDialog extends BaseBottomSheetDialogFragment {
    @BindView(R.id.b_close)
    public Button closeButton;

    @BindView(R.id.ll_info)
    public LinearLayout infoLinearLayout;

    QorgayModel qorgay;

    String observationType;

    DateFormat dateTimeFormat = SimpleDateFormat.getDateTimeInstance(SimpleDateFormat.MEDIUM, SimpleDateFormat.SHORT);

    public QorgayDetailsDialog(QorgayModel qorgay, String observationType) {
        this.qorgay = qorgay;
        this.observationType = observationType;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.dialog_qorgay_details, container, false);
        ButterKnife.bind(this, root);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        closeButton.setOnClickListener(v -> dismiss());


        addInfo(getString(R.string.fullname), qorgay.getFullName());
        addInfo(getString(R.string.observation_type), observationType);
        addInfo(getString(R.string.date_time), dateTimeFormat.format(new Date(qorgay.getIncidentDateTimeLong())));
        addInfo(getString(R.string.organization_name), qorgay.getOrganizationName());
        addInfo(getString(R.string.contractor), qorgay.getContractor());
        addInfo(getString(R.string.suggestion), qorgay.getSuggestion());
        addInfo(getString(R.string.is_eliminated), qorgay.isIsEliminated()? getString(R.string.yes) : getString(R.string.no));
    }

    void addInfo(String infoTitle, String infoValue) {
        if (infoValue != null && infoTitle != null) {
            View root = getLayoutInflater().inflate(R.layout.item_qorgay_info, null);
            TextView nameTextView = root.findViewById(R.id.tv_name);
            nameTextView.setText(infoTitle);
            TextView valueTextView = root.findViewById(R.id.tv_value);
            valueTextView.setText(infoValue);
            infoLinearLayout.addView(root);
        }
    }


    @Override
    void configureLayout(FrameLayout bottomSheet) {
        BottomSheetBehavior.from(bottomSheet).setState(BottomSheetBehavior.STATE_COLLAPSED);
        BottomSheetBehavior.from(bottomSheet).setSkipCollapsed(true);
    }
}
