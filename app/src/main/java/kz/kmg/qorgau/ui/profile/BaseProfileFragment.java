package kz.kmg.qorgau.ui.profile;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import kz.kmg.qorgau.QorgauApp;
import kz.kmg.qorgau.data.local.LocalStorage;
import kz.kmg.qorgau.data.network.api.ProfileApi;
import kz.kmg.qorgau.ui.base.fragment.BaseFragment;

public abstract class BaseProfileFragment extends BaseFragment {

    ProfileViewModel viewModel;


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ProfileApi profileApi = ((QorgauApp) getActivity().getApplication()).profileApi;
        LocalStorage localStorage = ((QorgauApp) getActivity().getApplication()).prefStorage;
        viewModel = new ViewModelProvider(requireActivity()).get(ProfileViewModel.class);
        viewModel.init(localStorage, profileApi);
    }
}
