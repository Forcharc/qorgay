package kz.kmg.qorgau.ui.questionnaire;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import br.com.simplepass.loadingbutton.customViews.CircularProgressButton;
import butterknife.BindView;
import kz.kmg.qorgau.QorgauApp;
import kz.kmg.qorgau.R;
import kz.kmg.qorgau.data.local.LocalStorage;
import kz.kmg.qorgau.data.model.questionnaires.AnswerVariantsItem;
import kz.kmg.qorgau.data.model.questionnaires.QuestionnaireFormModel;
import kz.kmg.qorgau.data.model.questionnaires.QuestionnaireModel;
import kz.kmg.qorgau.data.model.questionnaires.QuestionsItem;
import kz.kmg.qorgau.data.network.api.QuestionnaireApi;
import kz.kmg.qorgau.ui.base.adapter.BaseAdapter;
import kz.kmg.qorgau.ui.base.adapter.BaseViewHolder;
import kz.kmg.qorgau.ui.base.fragment.BaseFragment;
import kz.kmg.qorgau.ui.create.CreateFragment;
import kz.kmg.qorgau.ui.dialogs.QorgayCreatedDialog;

public class QuestionnaireFragment extends BaseFragment {

    @BindView(R.id.progress)
    ProgressBar progressBar;

    @BindView(R.id.b_retry)
    Button retryButton;

    @BindView(R.id.rv)
    RecyclerView recyclerView;

    @BindView(R.id.b_done)
    CircularProgressButton doneButton;

    public static final String PARAM_QUESTIONNAIRE = "param_questionnaire";
    QuestionnaireViewModel viewModel;

    QuestionnaireModel questionnaireModel;

    QuestionnaireFormModel questionnaireFormModel;

    QuestionsAdapter adapter = new QuestionsAdapter();

    View.OnClickListener onRetryButtonClickListener = v -> {
        if (questionnaireModel != null) {
            viewModel.loadQuestionnaire(questionnaireModel.getId());
        }
    };

    @Override
    public int getContentView() {
        return R.layout.fragment_questionnaire;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        QorgauApp app = (QorgauApp) getActivity().getApplication();
        QuestionnaireApi api = app.questionnaireApi;
        LocalStorage prefStorage = app.prefStorage;
        viewModel = new ViewModelProvider(this).get(QuestionnaireViewModel.class);
        viewModel.init(api, prefStorage.getCookie());

        if (getArguments() != null) {
            questionnaireModel = (QuestionnaireModel) getArguments().getSerializable(PARAM_QUESTIONNAIRE);
            if (questionnaireModel != null) {
                viewModel.loadQuestionnaire(questionnaireModel.getId());
            }
        }

        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        doneButton.setOnClickListener(v -> {
            viewModel.saveQuestionnaire(questionnaireFormModel);
        });

        initObservers();
    }

    private void initObservers() {
        viewModel.getQuestionnaireLiveData().observe(getViewLifecycleOwner(), response -> {
            switch (response.status) {
                case ERROR:
                    showRetryButton(onRetryButtonClickListener);
                    onToast(response.apiError.getMessage());
                    break;
                case SUCCESS:
                    hideStatusViews();
                    questionnaireFormModel = response.data;
                    showQuestionnaire(questionnaireFormModel);
                    break;
                case LOADING:
                    showProgress();
                    break;
            }
        });

        viewModel.getSaveQuestionnaireResponseLiveData().observe(getViewLifecycleOwner(), resource-> {
            switch (resource.status) {
                case ERROR:
                    doneButton.setEnabled(true);
                    doneButton.revertAnimation();
                    onToast(resource.apiError.getMessage());
                    break;
                case SUCCESS:
                    doneButton.setEnabled(true);
                    doneButton.revertAnimation();
                    Boolean isSuccess = resource.data.getIsSuccess();
                    if (isSuccess) {
                        QorgayCreatedDialog dialog = new QorgayCreatedDialog();
                        dialog.listener = () -> NavHostFragment.findNavController(QuestionnaireFragment.this).popBackStack(R.id.questionnaireListFragment, false);
                        dialog.show(getChildFragmentManager(), null);
                    } else {
                        onToast(getString(R.string.fill_empty_fields));
                    }
                    break;
                case LOADING:
                    doneButton.setEnabled(false);
                    doneButton.startAnimation();
                    break;
            }
        });
    }

    private void showQuestionnaire(QuestionnaireFormModel data) {
        adapter.submitList(data.getQuestions());
    }

    public class QuestionsAdapter extends BaseAdapter<QuestionsItem> {

        @NonNull
        @Override
        public DiffUtil.ItemCallback<QuestionsItem> getDiffCallback() {
            return new DiffUtil.ItemCallback<QuestionsItem>() {
                @Override
                public boolean areItemsTheSame(@NonNull QuestionsItem oldItem, @NonNull QuestionsItem newItem) {
                    return oldItem.getId() == newItem.getId();
                }

                @Override
                public boolean areContentsTheSame(@NonNull QuestionsItem oldItem, @NonNull QuestionsItem newItem) {
                    return oldItem.equals(newItem);
                }
            };
        }

        @NonNull
        @Override
        public QuestionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_question, parent, false);
            return new QuestionViewHolder(root);
        }

        class QuestionViewHolder extends BaseViewHolder<QuestionsItem> {

            @BindView(R.id.tv_title)
            TextView titleTextView;

            @BindView(R.id.tv_number)
            TextView numberTextView;

            @BindView(R.id.radio_group)
            RadioGroup radioGroup;

            @BindView(R.id.ll_checkboxes)
            LinearLayout checkBoxesLayout;

            Context context = itemView.getContext();

            public QuestionViewHolder(@NonNull View itemView) {
                super(itemView);
            }

            @Override
            public void setData(QuestionsItem item) {
                titleTextView.setText(item.getText());
                numberTextView.setText(String.valueOf(getBindingAdapterPosition() + 1));

                radioGroup.removeAllViews();
                checkBoxesLayout.removeAllViews();

                if (item.getType() == 1) {
                    // Radio
                    radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
                        item.setSelectedAnswer(checkedId);
                    });
                    for (int i = 0; i < item.getAnswerVariants().size(); i++ ) {
                        AnswerVariantsItem answer = item.getAnswerVariants().get(i);
                        addRadioOption(radioGroup, answer, item.getSelectedAnswer(), i == (item.getAnswerVariants().size() - 1));
                    }
                } else {
                    // Checkboxes
                    for (int i = 0; i < item.getAnswerVariants().size(); i++ ) {
                        AnswerVariantsItem answer = item.getAnswerVariants().get(i);
                        addCheckboxOption(checkBoxesLayout, answer, i == (item.getAnswerVariants().size() - 1));
                    }
                }
            }

            void addRadioOption(RadioGroup radioGroup, AnswerVariantsItem item, Integer selectedAnswer, boolean isLast) {
                View optionView = LayoutInflater.from(context).inflate(R.layout.item_radio_button, radioGroup);
                RadioButton radioButton = optionView.findViewById(R.id.radio_button);
                if (selectedAnswer != null) {
                    radioButton.setSelected(selectedAnswer.equals(item.getId()));
                }
                radioButton.setText(item.getText());
                radioButton.setId(item.getId());
                if (!isLast) LayoutInflater.from(context).inflate(R.layout.item_line, radioGroup);
            }

            void addCheckboxOption(LinearLayout layout, AnswerVariantsItem item, boolean isLast) {
                View checkBoxView = LayoutInflater.from(context).inflate(R.layout.item_checkbox_button, layout);
                CheckBox checkBox = checkBoxView.findViewById(R.id.check_box);
                checkBox.setChecked(item.isIsSelected());
                checkBox.setText(item.getText());
                checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                    item.setSelected(isChecked);
                });
                checkBox.setId(item.getId());
                if (!isLast) LayoutInflater.from(context).inflate(R.layout.item_line, layout);
            }
        }
    }

    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
        retryButton.setVisibility(View.GONE);
    }

    public void showRetryButton(View.OnClickListener onRetryButtonClick) {
        retryButton.setOnClickListener(onRetryButtonClick);
        progressBar.setVisibility(View.GONE);
        retryButton.setVisibility(View.VISIBLE);
    }

    public void hideStatusViews() {
        progressBar.setVisibility(View.GONE);
        retryButton.setVisibility(View.GONE);
    }
}
