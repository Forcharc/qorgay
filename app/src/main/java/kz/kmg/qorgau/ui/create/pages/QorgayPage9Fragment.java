package kz.kmg.qorgau.ui.create.pages;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import butterknife.BindView;
import kz.kmg.qorgau.R;

public class QorgayPage9Fragment extends BaseQorgayPageFragment {

    @BindView(R.id.et_suggestion)
    EditText suggestionEditText;

    public QorgayPage9Fragment() {
        super(9, R.string.describe_suggestion);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        suggestionEditText.setText(viewModel.getPage9Suggestion());

        suggestionEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                viewModel.setPage9Suggestion(s.toString());
            }
            @Override
            public void afterTextChanged(Editable s) { }
        });
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_qorgay_page9;
    }
}
