package kz.kmg.qorgau.ui.questionnaire;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.fragment.NavHostFragment;

import butterknife.BindView;
import kz.kmg.qorgau.R;
import kz.kmg.qorgau.data.model.questionnaires.QuestionnaireModel;
import kz.kmg.qorgau.ui.base.fragment.BaseFragment;

public class QuestionnaireDescriptionFragment extends BaseFragment {

    public static final String PARAM_QUESTIONNAIRE = "param_questionnaire";

    @BindView(R.id.tv_description)
    TextView descriptionTextView;

    @BindView(R.id.b_start)
    Button startButton;

    @BindView(R.id.tv_hint)
    TextView hintTextView;

    @Override
    public int getContentView() {
        return R.layout.fragment_questionnaire_description;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null) {
            QuestionnaireModel item = (QuestionnaireModel) getArguments().getSerializable(PARAM_QUESTIONNAIRE);
            showDescription(item);
        }
    }

    private void showDescription(QuestionnaireModel item) {
        if (item != null) {
            descriptionTextView.setText(item.getDescription());
            if (item.isIsPassed()) {
                startButton.setVisibility(View.GONE);
                hintTextView.setText(R.string.questionnaire_is_passed);
            } else {
                startButton.setVisibility(View.VISIBLE);
                hintTextView.setText(R.string.enter_questionnaire);
            }
            startButton.setOnClickListener(v -> {
                Bundle bundle = new Bundle();
                bundle.putSerializable(QuestionnaireFragment.PARAM_QUESTIONNAIRE, item);
                NavHostFragment.findNavController(this).navigate(R.id.action_questionnaireDescriptionFragment_to_questionnaireFragment, bundle);
            });
        }
    }
}
