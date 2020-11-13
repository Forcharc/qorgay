package kz.kmg.qorgau.ui.create.pages;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import butterknife.BindView;
import kz.kmg.qorgau.R;

public class QorgayPage17Fragment extends BaseQorgayPageFragment {

    @BindView(R.id.rb_yes)
    RadioButton yesRadio;

    @BindView(R.id.rb_no)
    RadioButton noRadio;

    public QorgayPage17Fragment() {
        super(17, R.string.is_eliminated);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Boolean isEliminated = viewModel.isPage17IsEliminated();
        if (isEliminated != null) {
            yesRadio.setChecked(isEliminated);
            noRadio.setChecked(!isEliminated);
        }

        yesRadio.setOnCheckedChangeListener((buttonView, isChecked) -> {
            viewModel.setPage17IsEliminated(isChecked);
        });

        noRadio.setOnCheckedChangeListener((buttonView, isChecked) -> {
            viewModel.setPage17IsEliminated(!isChecked);
        });

    }

    @Override
    public int getContentView() {
        return R.layout.fragment_qorgay_page17;
    }
}
