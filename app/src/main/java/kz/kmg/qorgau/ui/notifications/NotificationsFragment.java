package kz.kmg.qorgau.ui.notifications;

import android.app.Notification;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.ConcatAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import butterknife.BindView;
import kz.kmg.qorgau.QorgauApp;
import kz.kmg.qorgau.R;
import kz.kmg.qorgau.data.local.LocalStorage;
import kz.kmg.qorgau.data.model.notifications.NotificationModel;
import kz.kmg.qorgau.data.network.api.NotificationsApi;
import kz.kmg.qorgau.ui.base.adapter.BaseAdapter;
import kz.kmg.qorgau.ui.base.adapter.BaseViewHolder;
import kz.kmg.qorgau.ui.base.fragment.BaseFragment;
import kz.kmg.qorgau.ui.dialogs.NotificationDialog;

public class NotificationsFragment extends BaseFragment {

    @BindView(R.id.progress)
    ProgressBar progressBar;

    @BindView(R.id.b_retry)
    Button retryButton;

    @BindView(R.id.rv_notifications)
    RecyclerView notificationsRecyclerView;

    @BindView(R.id.srl_notifications)
    SwipeRefreshLayout notificationsSwipeRefresh;

    @BindView(R.id.tv_no_notifications)
    TextView noNotificationsText;

    DateFormat incomingDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.ENGLISH);
    DateFormat outgoingDate = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());


    private NotificationsViewModel viewModel;

    private final View.OnClickListener onRetryButtonClickListener = v -> {
        viewModel.loadNotifications();
    };

    public NotificationsFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_notifications;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(NotificationsViewModel.class);

        noNotificationsText.setVisibility(View.GONE);
        LocalStorage localStorage = ((QorgauApp) (getActivity().getApplication())).prefStorage;
        NotificationsApi notificationsApi = ((QorgauApp) (getActivity().getApplication())).notificationsApi;
        viewModel.init(localStorage, notificationsApi);

        viewModel.getNotificationsLiveData().observe(getViewLifecycleOwner(), response -> {
            switch (response.status) {
                case ERROR:
                    noNotificationsText.setVisibility(View.GONE);
                    notificationsRecyclerView.setVisibility(View.GONE);
                    showRetryButton(onRetryButtonClickListener);
                    onToast(response.apiError.getMessage());
                    break;
                case SUCCESS:
                    notificationsRecyclerView.setVisibility(View.VISIBLE);
                    hideStatusViews();
                    showNotifications(response.data);
                    break;
                case LOADING:
                    noNotificationsText.setVisibility(View.GONE);
                    notificationsRecyclerView.setVisibility(View.GONE);
                    showProgress();
                    break;
            }
        });

        viewModel.loadNotifications();

        notificationsSwipeRefresh.setOnRefreshListener(() -> {
            notificationsSwipeRefresh.setRefreshing(false);
            viewModel.loadNotifications();
        });

    }

    private void showNotifications(List<NotificationModel> data) {
        ConcatAdapter concatAdapter = new ConcatAdapter();

        if (data.size() == 0) {
            noNotificationsText.setVisibility(View.VISIBLE);
        } else {
            noNotificationsText.setVisibility(View.GONE);
        }

        notificationsRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        notificationsRecyclerView.setAdapter(concatAdapter);
        notificationsRecyclerView.setHasFixedSize(true);

        List<NotificationModel> newNotifications = data.stream()
                .filter(notificationModel -> !notificationModel.isIsRead())
                .collect(Collectors.toList());

        int newNotificationsCount = newNotifications.size();
        if (newNotificationsCount != 0) {
            NotificationsAdapter newNotificationsAdapter = new NotificationsAdapter(getResources().getQuantityString(R.plurals.new_notifications_count, newNotificationsCount, newNotificationsCount));
            newNotificationsAdapter.submitList(newNotifications);
            concatAdapter.addAdapter(newNotificationsAdapter);
        }


        List<NotificationModel> oldNotifications = data.stream()
                .filter(NotificationModel::isIsRead)
                .collect(Collectors.toList());

        int oldNotificationsCount = oldNotifications.size();
        if (oldNotificationsCount != 0) {
            NotificationsAdapter oldNotificationsAdapter = new NotificationsAdapter(getString(R.string.seen));
            oldNotificationsAdapter.submitList(oldNotifications);
            concatAdapter.addAdapter(oldNotificationsAdapter);
        }
    }

    class NotificationsAdapter extends BaseAdapter<NotificationModel> {
        private String headerTitle;

        public NotificationsAdapter(String headerTitle) {
            this.headerTitle = headerTitle;
        }

        private final int HEADER_VIEW_TYPE = 0;
        private final int NOTIFICATION_VIEW_TYPE = 1;

        @NonNull
        @Override
        public DiffUtil.ItemCallback<NotificationModel> getDiffCallback() {
            return null;
        }

        @Override
        public int getItemViewType(int position) {
            int viewType = NOTIFICATION_VIEW_TYPE;
            if (position == 0) {
                viewType = HEADER_VIEW_TYPE;
            }
            return viewType;
        }

        @Override
        public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
            if (holder.getClass() == HeaderViewHolder.class) {
                ((HeaderViewHolder) holder).setData(headerTitle);
            } else {
                ((NotificationViewHolder) holder).setData(getItem(position - 1));
            }
        }

        @Override
        public int getItemCount() {
            return super.getItemCount() + 1;
        }

        @NonNull
        @Override
        public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View root;
            switch (viewType) {
                case HEADER_VIEW_TYPE: {
                    root = inflater.inflate(R.layout.item_notifications_header, parent, false);
                    return new HeaderViewHolder(root);
                }
                default: {
                    root = inflater.inflate(R.layout.item_notification, parent, false);
                    return new NotificationViewHolder(root);
                }
            }
        }

        class HeaderViewHolder extends BaseViewHolder<String> {
            @BindView(R.id.tv_title)
            TextView titleTextView;

            public HeaderViewHolder(@NonNull View itemView) {
                super(itemView);
            }

            @Override
            public void setData(String item) {
                titleTextView.setText(item);
            }
        }

        class NotificationViewHolder extends BaseViewHolder<NotificationModel> {

            @BindView(R.id.tv_notification_body)
            TextView notificationBodyTextView;

            @BindView(R.id.tv_date)
            TextView dateTextView;

            public NotificationViewHolder(@NonNull View itemView) {
                super(itemView);
            }

            @Override
            public void setData(NotificationModel item) {
                notificationBodyTextView.setText(item.getTitle());

                if (item.isIsRead()) {
                    Typeface font = ResourcesCompat.getFont(requireContext(), R.font.sfui_display_regular);
                    notificationBodyTextView.setTypeface(font);
                    dateTextView.setTextColor(ResourcesCompat.getColor(getResources(), R.color.gray, null));
                } else {
                    Typeface font = ResourcesCompat.getFont(requireContext(), R.font.sfui_display_medium);
                    notificationBodyTextView.setTypeface(font);
                    dateTextView.setTextColor(ResourcesCompat.getColor(getResources(), R.color.color_blue2, null));
                }

                String dateString;
                try {
                    Date date = incomingDate.parse(item.getInsertDate());
                    if (DateUtils.isToday(date.getTime())) {
                        dateString = getString(R.string.today);
                    } else {
                        dateString = outgoingDate.format(date);
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                    dateString = "";
                }
                dateTextView.setText(dateString);

                itemView.setOnClickListener(v -> {
                    if (!item.isIsRead()) {
                        viewModel.setNotificationIsRead(item.getId());
                    }
                    new NotificationDialog(item.getTitle()).show(getChildFragmentManager(), null);
                });
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

    @Override
    public void onDestroyView() {
        progressBar = null;
        retryButton = null;
        notificationsRecyclerView = null;

        super.onDestroyView();
    }
}