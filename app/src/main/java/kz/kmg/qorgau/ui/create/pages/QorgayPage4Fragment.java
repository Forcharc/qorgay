package kz.kmg.qorgau.ui.create.pages;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import butterknife.BindView;
import kz.kmg.qorgau.R;

public class QorgayPage4Fragment extends BaseQorgayPageFragment implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    @BindView(R.id.tv_choose_date)
    TextView chooseDateTextView;

    @BindView(R.id.tv_choose_time)
    TextView chooseTimeTextView;

    Calendar nowCalendar = Calendar.getInstance();

    DateFormat dateFormat = SimpleDateFormat.getDateInstance(SimpleDateFormat.MEDIUM, Locale.getDefault());
    DateFormat timeFormat = SimpleDateFormat.getTimeInstance(SimpleDateFormat.SHORT, Locale.getDefault());

    public QorgayPage4Fragment() {
        super(4, R.string.date_time);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        chooseDateTextView.setText(viewModel.getPage4Date());
        chooseTimeTextView.setText(viewModel.getPage4Time());

        chooseDateTextView.setOnClickListener(
                v -> {
                    new DatePickerDialog(requireContext(), this,
                            nowCalendar.get(Calendar.YEAR),
                            nowCalendar.get(Calendar.MONTH),
                            nowCalendar.get(Calendar.DAY_OF_MONTH)).show();
                }
        );

        chooseTimeTextView.setOnClickListener(
                v -> {
                    new TimePickerDialog(requireContext(),
                            this,
                            nowCalendar.get(Calendar.HOUR_OF_DAY),
                            nowCalendar.get(Calendar.MINUTE), true).show();
                }
        );
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_qorgay_page4;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar date = Calendar.getInstance();
        date.set(Calendar.YEAR, year);
        date.set(Calendar.MONTH, month);
        date.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        String dateString = dateFormat.format(date.getTime());
        chooseDateTextView.setText(dateString);
        viewModel.setPage4Date(dateString);
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Calendar time = Calendar.getInstance();
        time.set(Calendar.HOUR_OF_DAY, hourOfDay);
        time.set(Calendar.MINUTE, minute);

        String timeString = timeFormat.format(time.getTime());
        chooseTimeTextView.setText(timeString);
        viewModel.setPage4Time(timeString);
    }
}
