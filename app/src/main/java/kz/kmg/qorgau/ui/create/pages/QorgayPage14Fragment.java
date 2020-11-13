package kz.kmg.qorgau.ui.create.pages;

import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import butterknife.BindView;
import kz.kmg.qorgau.R;

public class QorgayPage14Fragment extends BaseQorgayPageFragment {

    @BindView(R.id.rb_yes)
    RadioButton yesRadio;

    @BindView(R.id.rb_no)
    RadioButton noRadio;

    public QorgayPage14Fragment() {
        super(14, R.string.discussed);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Boolean isDiscussed = viewModel.isPage14IsDiscussed();
        if (isDiscussed != null) {
            yesRadio.setChecked(isDiscussed);
            noRadio.setChecked(!isDiscussed);
        }

        yesRadio.setOnCheckedChangeListener((buttonView, isChecked) -> {
            viewModel.setPage14IsDiscussed(isChecked);
        });

        noRadio.setOnCheckedChangeListener((buttonView, isChecked) -> {
            viewModel.setPage14IsDiscussed(!isChecked);
        });
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_qorgay_page14;
    }
}
