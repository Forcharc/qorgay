package kz.kmg.qorgau.ui.home;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import butterknife.BindView;
import kz.kmg.qorgau.R;
import kz.kmg.qorgau.data.model.UserModel;
import kz.kmg.qorgau.data.model.home.NewsModel;
import kz.kmg.qorgau.data.model.home.PromoModel;
import kz.kmg.qorgau.ui.base.adapter.OnItemClickListener;
import kz.kmg.qorgau.ui.base.fragment.BaseFragment;

public class HomeFragment extends BaseFragment implements OnItemClickListener {

    private static final String TAG = "HomeFragment";

    @BindView(R.id.rv_promo_cards)
    RecyclerView rvPromoCards;

    @BindView(R.id.rv_news)
    RecyclerView rvNews;

    @BindView(R.id.b_create)
    ImageButton createQorgayButton;

    @BindView(R.id.ib_work)
    ImageButton workButton;

    @BindView(R.id.ib_driving)
    ImageButton drivingButton;

    NavController navController;

    private PromoCardsAdapter promoCardsAdapter = new PromoCardsAdapter();
    private NewsAdapter newsAdapter = new NewsAdapter();

    public HomeFragment() {
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_home;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = NavHostFragment.findNavController(this);

/*
        initPromoCards();
        initNews();
*/

        createQorgayButton.setOnClickListener(v -> {
            navController.navigate(R.id.navigation_create);
        });

        workButton.setOnClickListener(v -> {
            navController.navigate(R.id.action_navigation_home_to_workObservationFragment);
        });

        drivingButton.setOnClickListener(v -> {
            navController.navigate(R.id.action_navigation_home_to_drivingObservationFragment);
        });
    }

    private void initNews() {
        ArrayList<NewsModel> newsList = new ArrayList<>();
        newsList.add(new NewsModel(new UserModel(0, "Иван", "Иванов", "https://whatever.com/img.png"), System.currentTimeMillis(), "Enjoy the beauty of italian cotton all over your body. This item will fit your body and warm you up all over and during spring. This item will fit your body and warm you up all over and during spring. ", "https://site.com/img.png"));
        newsList.add(new NewsModel(new UserModel(0, "Иван", "Иванов", "https://whatever.com/img.png"), System.currentTimeMillis(), "Enjoy the beauty of italian cotton all over your body. This item will fit your body and warm you up all over and during spring. This item will fit your body and warm you up all over and during spring. ", "https://site.com/img.png"));

        newsAdapter.submitList(newsList);

        rvNews.setLayoutManager(new LinearLayoutManager(requireContext()));
        rvNews.setHasFixedSize(false);
        rvNews.setAdapter(newsAdapter);
    }

    private void initPromoCards() {
        promoCardsAdapter.onItemClickListener = this;
        ArrayList<PromoModel> promoCardsList = new ArrayList<>();
        promoCardsList.add(new PromoModel(ContextCompat.getDrawable(requireContext(), R.drawable.img_sample), "Enjoy the beauty of italian cotton all over your body.", "some const", Color.parseColor("#9912207B")));
        promoCardsList.add(new PromoModel(ContextCompat.getDrawable(requireContext(), R.drawable.img_sample), "Enjoy the beauty of italian cotton all over your body.", "some const", Color.parseColor("#99FFFFFF")));

        promoCardsAdapter.submitList(promoCardsList);


        rvPromoCards.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false) {
            @Override
            public boolean checkLayoutParams(RecyclerView.LayoutParams lp) {
                lp.width = (int) ((double) getWidth() * 0.8);
                lp.height = (int) ((double) lp.width / 1.3);
                return true;
            }
        });

        rvPromoCards.setAdapter(promoCardsAdapter);
        rvPromoCards.setHasFixedSize(true);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        navController = null;
    }

    @Override
    public void onItemClick(Object object) {
        onToast(((PromoModel) object).toString());
    }
}