package kz.kmg.qorgau.ui.profile;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import br.com.simplepass.loadingbutton.customViews.CircularProgressButton;
import butterknife.BindView;
import kz.kmg.qorgau.R;

public class LoginFragment extends BaseProfileFragment {

    public static String IS_LOGIN_SUCCESSFUL = "IS_LOGIN_SUCCESSFUL";

    @BindView(R.id.et_login)
    EditText loginEditText;

    @BindView(R.id.et_password)
    EditText passwordEditText;

    @BindView(R.id.b_enter)
    CircularProgressButton enterButton;

    private NavController navController;

    private SavedStateHandle savedStateHandle;

    private final View.OnClickListener onEnterButtonClickListener = v -> {
        hideKeyboard();

        String login = loginEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        if (login.length() > 0 && password.length() > 0) {


            viewModel.login(login, password).observe(getViewLifecycleOwner(), isSuccessResponseResource -> {
                switch (isSuccessResponseResource.status) {
                    case ERROR:
                        enterButton.setEnabled(true);
                        enterButton.revertAnimation();
                        onToast(isSuccessResponseResource.apiError.getMessage());
                        break;
                    case SUCCESS:
                        enterButton.setEnabled(true);
                        enterButton.revertAnimation();
                        Boolean isSuccess = isSuccessResponseResource.data.getIsSuccess();
                        if (isSuccess) {
                            savedStateHandle.set(IS_LOGIN_SUCCESSFUL, true);
                            navController.popBackStack(R.id.navigation_profile, false);
                        } else {
                            onToast(getString(R.string.incorrect_login_or_pass));
                        }
                        break;
                    case LOADING:
                        enterButton.setEnabled(false);
                        enterButton.startAnimation();
                        break;
                }
            });

        } else {

            onToast(getString(R.string.fill_login_and_pass));
        }
    };

    @Override
    public int getContentView() {
        return R.layout.fragment_login;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(ProfileViewModel.class);
        navController = NavHostFragment.findNavController(this);

        savedStateHandle = navController
                .getPreviousBackStackEntry()
                .getSavedStateHandle();
        savedStateHandle.set(IS_LOGIN_SUCCESSFUL, false);

        enterButton.setOnClickListener(onEnterButtonClickListener);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        loginEditText = null;
        passwordEditText = null;
        enterButton = null;

    }
}
