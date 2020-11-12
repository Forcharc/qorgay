package kz.kmg.qorgau.ui.home.questionnaire;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import butterknife.BindView;
import kz.kmg.qorgau.R;
import kz.kmg.qorgau.ui.base.adapter.BaseAdapter;
import kz.kmg.qorgau.ui.base.adapter.BaseViewHolder;
import kz.kmg.qorgau.ui.base.fragment.BaseFragment;

public class QuestionnaireListFragment extends BaseFragment {
    @BindView(R.id.rv)
    RecyclerView recyclerView;

    @Override
    public int getContentView() {
        return R.layout.fragment_questionnaire_list;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setHasFixedSize(true);
    }

/*
    class QuestionnaireListAdapter extends BaseAdapter<BaseViewHolder<List<Object>>> {
    }
*/
}
