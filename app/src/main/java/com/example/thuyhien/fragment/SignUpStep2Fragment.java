package com.example.thuyhien.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by thuyhien on 9/19/17.
 */

public class SignUpStep2Fragment extends Fragment {
    @BindView(R.id.txt_salary)
    TextView txtSalary;

    @BindView(R.id.text_end_dollar)
    TextView txtEndDollar;

    @BindView(R.id.seek_bar_salary)
    SeekBar seekBarSalary;

    @BindString(R.string.title_seek_bar_value)
    String ckbValueTitle;

    @BindViews({R.id.chk_football, R.id.chk_tennis, R.id.chk_ping_pong,
            R.id.chk_swimming, R.id.chk_volleyball, R.id.chk_basketball})
    List<CheckBox> allChecksSport;

    private OnBtnDoneClickListener doneListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            doneListener = (OnBtnDoneClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + R.string.error_implement_sign_up_step_2_listener);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (container == null) {
            return null;
        }

        View view = inflater.inflate(R.layout.fragment_sign_up_step2, container, false);
        ButterKnife.bind(this, view);
        initViews();
        setActionBarTitle();
        return view;
    }

    @OnClick(R.id.button_done)
    public void onBtnDoneClick() {
        if (checkValidSport()) {
            String salary = String.valueOf(seekBarSalary.getProgress());
            doneListener.onBtnDoneClick(salary);
        } else {
            Toast.makeText(getActivity(), R.string.warning_select_sport, Toast.LENGTH_LONG).show();
        }
    }

    private void initViews() {
        txtSalary.setText(String.format(ckbValueTitle, 0));
        setEndDollarTitle();
        seekBarSalary.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                int newProgress = i / 100;
                newProgress *= 100;
                seekBar.setProgress(newProgress);

                String result = String.format(ckbValueTitle, seekBarSalary.getProgress());
                txtSalary.setText(result);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private boolean checkValidSport() {
        for (CheckBox checkBoxSport : allChecksSport) {
            if (checkBoxSport.isChecked()) {
                return true;
            }
        }
        return false;
    }

    private void setEndDollarTitle() {
        txtEndDollar.setText(getString(R.string.title_seek_bar_end_value, seekBarSalary.getMax()));
    }

    private void setActionBarTitle() {
        getActivity().setTitle(R.string.title_fragment_sign_up_step2);
    }

    public interface OnBtnDoneClickListener {
        void onBtnDoneClick(String salary);
    }
}
