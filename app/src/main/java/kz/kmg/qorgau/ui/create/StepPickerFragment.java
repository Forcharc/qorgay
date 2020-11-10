package kz.kmg.qorgau.ui.create;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.core.widget.TextViewCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import kz.kmg.qorgau.R;


public class StepPickerFragment extends Fragment implements OnStepClickListener {
    private static final String TAG = "StepPickerFragment";
    static final String ARGUMENT_STEP_COUNT = "ARGUMENT_STEP_COUNT";


    private OnStepClickListener onStepClickListener;

    private int stepsCount = 0;

    @BindView(R.id.ib_prev)
    AppCompatImageButton prevStepButton;

    @BindView(R.id.ib_next)
    AppCompatImageButton nextStepButton;

    @BindView(R.id.rv_steps)
    RecyclerView stepsRecyclerView;

    StepsAdapter stepsAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            stepsCount = args.getInt(ARGUMENT_STEP_COUNT);
        }

        stepsAdapter = new StepsAdapter(0, stepsCount, this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_step_picker, container, false);
        ButterKnife.bind(this, root);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        initStepPicker();
    }

    void initStepPicker() {
        nextStepButton.setOnClickListener(v -> {
            int futureStep = stepsAdapter.currentStep + 1;
            stepsAdapter.setCurrentStep(futureStep);
        });

        prevStepButton.setOnClickListener(v -> {
            int futureStep = stepsAdapter.currentStep - 1;
            stepsAdapter.setCurrentStep(futureStep);
        });

        stepsRecyclerView.setAdapter(stepsAdapter);
        stepsRecyclerView.setLayoutManager(new CenteredLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false) {
            @Override
            public boolean checkLayoutParams(RecyclerView.LayoutParams lp) {
                lp.width = (int) ((double) getWidth() * 0.2);
                return true;
            }
        });
        stepsRecyclerView.setHasFixedSize(true);
    }

    @Override
    public void onStepChosen(int stepNumber) {
        try {
            stepsRecyclerView.getLayoutManager().smoothScrollToPosition(stepsRecyclerView, null, stepNumber);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        if (onStepClickListener != null) {
            onStepClickListener.onStepChosen(stepNumber);
        }
    }

    class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.StepViewHolder> {

        public int getCurrentStep() {
            return currentStep;
        }

        public boolean setCurrentStep(int currentStep) {
            if (currentStep >= 0 && currentStep < stepsCount) {
                int lastStep = this.currentStep;
                this.currentStep = currentStep;
                notifyItemChanged(lastStep);
                notifyItemChanged(currentStep);
                onStepChosenListener.onStepChosen(currentStep);
                return true;
            } else {
                return false;
            }
        }

        public int getStepsCount() {
            return stepsCount;
        }

        // First step is 0
        private int currentStep;

        private int stepsCount;
        OnStepClickListener onStepChosenListener;

        public StepsAdapter(int currentStep, int stepsCount, OnStepClickListener onStepChosenListener) {
            this.currentStep = currentStep;
            this.stepsCount = stepsCount;
            this.onStepChosenListener = onStepChosenListener;
        }

        @NonNull
        @Override
        public StepViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_step_picker, parent, false);
            return new StepViewHolder(root);
        }

        @Override
        public void onBindViewHolder(@NonNull StepViewHolder holder, int position) {
            holder.bind(position, currentStep == position);
        }

        @Override
        public int getItemCount() {
            return stepsCount;
        }


        class StepViewHolder extends RecyclerView.ViewHolder {
            @BindView(R.id.tv_step)
            TextView stepTextView;

            public StepViewHolder(@NonNull View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }

            void bind(int step, boolean isPickedStep) {
                // TODO textview sometimes displays wrong number, but step value is right
                stepTextView.setText(String.valueOf(step + 1));
                stepTextView.setOnClickListener(v -> {
                    Log.d(TAG, "onclick: step" + (step + 1));
                    setCurrentStep(step);
                });

                if (isPickedStep) {
                    stepTextView.setBackgroundResource(R.drawable.ic_picker_step_background);
                    TextViewCompat.setTextAppearance(stepTextView, R.style.AppTheme_StepPickerNumber_Picked);
                } else {
                    stepTextView.setBackgroundResource(R.color.transparent);
                    TextViewCompat.setTextAppearance(stepTextView, R.style.AppTheme_StepPickerNumber);
                }
            }
        }

    }

    public void setOnStepClickListener(OnStepClickListener onStepClickListener) {
        this.onStepClickListener = onStepClickListener;
    }
}
