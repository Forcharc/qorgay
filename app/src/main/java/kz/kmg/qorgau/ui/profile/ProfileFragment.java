package kz.kmg.qorgau.ui.profile;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.SavedStateHandle;
import androidx.navigation.NavBackStackEntry;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;
import kz.kmg.qorgau.R;
import kz.kmg.qorgau.data.model.user.ProfileModel;

public class ProfileFragment extends BaseProfileFragment {

    @BindView(R.id.progress)
    ProgressBar progressBar;

    @BindView(R.id.b_retry)
    Button retryButton;

    @BindView(R.id.ll_profile)
    LinearLayout profileLinearLayout;

    @BindView(R.id.tv_full_name)
    TextView fullNameTextView;

    @BindView(R.id.tv_email_value)
    TextView emailTextView;

    @BindView(R.id.tv_phone_value)
    TextView phoneTextView;

    @BindView(R.id.tv_birth_value)
    TextView birthTextView;

    @BindView(R.id.tv_skype_value)
    TextView skypeTextView;

    @BindView(R.id.i_profile)
    CircleImageView profileImage;

    @BindView(R.id.b_exit)
    Button exitButton;

    String cookie = null;

    View.OnClickListener onRetryButtonClickListener = v -> {
        getProfile(cookie);
    };

    View.OnClickListener onExitButtonClickListener = v -> {
        viewModel.exit();
    };

    public ProfileFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        NavController navController = NavHostFragment.findNavController(this);

        NavBackStackEntry navBackStackEntry = navController.getCurrentBackStackEntry();
        SavedStateHandle savedStateHandle = navBackStackEntry.getSavedStateHandle();
        savedStateHandle.getLiveData(LoginFragment.IS_LOGIN_SUCCESSFUL)
                .observe(navBackStackEntry, success -> {
                    Boolean isSuccess = (boolean) success;
                    if (!isSuccess) {
                        int startDestination = navController.getGraph().getStartDestination();
                        NavOptions navOptions = new NavOptions.Builder()
                                .setPopUpTo(startDestination, true)
                                .build();
                        navController.navigate(startDestination, null, navOptions);
                    }
                });
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_profile;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        hideProfile();

        final NavController navController = Navigation.findNavController(view);
        viewModel.getCookieLiveData().observe(getViewLifecycleOwner(), (Observer<String>) cookie -> {
            if (cookie != null) {
                this.cookie = cookie;
                getProfile(cookie);
            } else {
                navController.navigate(R.id.loginFragment);
            }
        });

        exitButton.setOnClickListener(onExitButtonClickListener);
    }

    private void getProfile(String cookie) {
        viewModel.getProfile(cookie).observe(getViewLifecycleOwner(), profileModelResource -> {
            switch (profileModelResource.status) {
                case ERROR:
                    hideProfile();
                    showRetryButton(onRetryButtonClickListener);
                    onToast(profileModelResource.apiError.getMessage());
                    break;
                case SUCCESS:
                    hideStatusViews();
                    showProfile(profileModelResource.data);
                    break;
                case LOADING:
                    hideProfile();
                    profileLinearLayout.setVisibility(View.GONE);
                    showProgress();
                    break;
            }
        });
    }

    private void hideProfile() {
        profileLinearLayout.setVisibility(View.GONE);
        exitButton.setVisibility(View.GONE);
    }

    private void showProfile(ProfileModel data) {
        profileLinearLayout.setVisibility(View.VISIBLE);
        exitButton.setVisibility(View.VISIBLE);

        Glide.with(this).load("").centerCrop().placeholder(R.drawable.img_profile_placeholder).into(profileImage);

        setTextAvoidingNull(fullNameTextView, data.getFullName());
        setTextAvoidingNull(emailTextView, data.getEmail());
        setTextAvoidingNull(phoneTextView, data.getPhoneNumber());
        if (data.getBirthDateLong() != null) {
            String date = SimpleDateFormat.getDateInstance(SimpleDateFormat.MEDIUM, Locale.getDefault()).format(new Date(data.getBirthDateLong()));
            setTextAvoidingNull(birthTextView, date);
        } else {
            setTextAvoidingNull(birthTextView, null);
        }
        setTextAvoidingNull(skypeTextView, data.getSkype());

    }

    private void setTextAvoidingNull(TextView textView, String text) {
        if (text == null || text.length() == 0) {
            textView.setText(R.string.not_provided);
        } else {
            textView.setText(text);
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