package kz.kmg.qorgau.ui.create.pages;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import butterknife.BindView;
import kz.kmg.qorgau.R;

public class QorgayPage15Fragment extends BaseQorgayPageFragment {

    @BindView(R.id.rb_yes)
    RadioButton yesRadio;

    @BindView(R.id.rb_no)
    RadioButton noRadio;

    public QorgayPage15Fragment() {
        super(15, R.string.is_informed);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Boolean isInformed = viewModel.isPage15IsInformed();
        if (isInformed != null) {
            yesRadio.setChecked(isInformed);
            noRadio.setChecked(!isInformed);
        }

        yesRadio.setOnCheckedChangeListener((buttonView, isChecked) -> {
            viewModel.setPage15IsInformed(isChecked);
        });

        noRadio.setOnCheckedChangeListener((buttonView, isChecked) -> {
            viewModel.setPage15IsInformed(!isChecked);
        });

    }

    @Override
    public int getContentView() {
        return R.layout.fragment_qorgay_page15;
    }
}
