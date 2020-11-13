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

public class QorgayPage16Fragment extends BaseQorgayPageFragment {

    @BindView(R.id.et_inform_to)
    EditText informToEditText;

    public QorgayPage16Fragment() {
        super(16, R.string.inform_to);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        informToEditText.setText(viewModel.getPage16InformTo());

        informToEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                viewModel.setPage16InformTo(s.toString());
            }
            @Override
            public void afterTextChanged(Editable s) { }
        });

    }

    @Override
    public int getContentView() {
        return R.layout.fragment_qorgay_page16;
    }
}
