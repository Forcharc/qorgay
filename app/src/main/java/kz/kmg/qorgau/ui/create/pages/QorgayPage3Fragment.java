package kz.kmg.qorgau.ui.create.pages;

import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import butterknife.BindView;
import kz.kmg.qorgau.R;

public class QorgayPage3Fragment extends BaseQorgayPageFragment {
    @BindView(R.id.et_phone_number)
    EditText phoneNumberEditText;

    private final TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            viewModel.setPage3PhoneNumber(s.toString());
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    };
    private final TextView.OnEditorActionListener onEditorActionListener = (v, actionId, event) -> false;

    public QorgayPage3Fragment() {
        super(3, R.string.phone_number);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        phoneNumberEditText.setText(viewModel.getPage3PhoneNumber());

        phoneNumberEditText.addTextChangedListener(textWatcher);

        phoneNumberEditText.setOnEditorActionListener(onEditorActionListener);

        phoneNumberEditText.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_qorgay_page3;
    }
}
