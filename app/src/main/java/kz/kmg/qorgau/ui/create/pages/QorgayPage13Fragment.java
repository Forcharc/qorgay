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

public class QorgayPage13Fragment extends BaseQorgayPageFragment {

    @BindView(R.id.et_actions)
    EditText actionsEditText;

    public QorgayPage13Fragment() {
        super(13, R.string.action);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        actionsEditText.setText(viewModel.getPage13ActionToEncourage());

        actionsEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                viewModel.setPage13ActionToEncourage(s.toString());
            }
            @Override
            public void afterTextChanged(Editable s) { }
        });

        actionsEditText.setOnEditorActionListener(onEditorActionNextPage);
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_qorgay_page13;
    }
}
