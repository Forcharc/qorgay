package kz.kmg.qorgau.ui.create.pages;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import butterknife.BindView;
import kz.kmg.qorgau.R;

public class QorgayPage12Fragment extends BaseQorgayPageFragment {

    @BindView(R.id.et_measures)
    EditText measuresEditText;

    public QorgayPage12Fragment() {
        super(12, R.string.measures);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        measuresEditText.setText(viewModel.getPage12Measure());

        measuresEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                viewModel.setPage12Measure(s.toString());
            }
            @Override
            public void afterTextChanged(Editable s) { }
        });
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_qorgay_page12;
    }
}
