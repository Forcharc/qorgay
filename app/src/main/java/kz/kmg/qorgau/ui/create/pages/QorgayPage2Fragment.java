package kz.kmg.qorgau.ui.create.pages;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

import butterknife.BindView;
import kz.kmg.qorgau.R;

public class QorgayPage2Fragment extends BaseQorgayPageFragment {
    @BindView(R.id.et_full_name)
    EditText fullNameEditText;

    public QorgayPage2Fragment() {
        super(2, R.string.full_name);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fullNameEditText.setText(viewModel.getPage2FullName());

        fullNameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                viewModel.setPage2FullName(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        fullNameEditText.setOnEditorActionListener(onEditorActionNextPage);
    }


    @Override
    public int getContentView() {
        return R.layout.fragment_qorgay_page2;
    }
}
