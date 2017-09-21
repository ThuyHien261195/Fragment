package com.example.thuyhien.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by thuyhien on 9/19/17.
 */

public class SignUpStep3Fragment extends Fragment {

    private OnSignUpStep3Listener signUpListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            signUpListener = (OnSignUpStep3Listener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + R.string.error_implement_sign_up_step_3_listener);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (container == null) {
            return null;
        }
        View view = inflater.inflate(R.layout.fragment_sign_up_step3, container, false);
        ButterKnife.bind(this, view);
        setActionBarTitle();
        return view;
    }

    @OnClick(R.id.button_send_mail)
    public void onBtnSendEmailClick() {
        signUpListener.onBtnSendMailClick();
    }

    @OnClick(R.id.button_restart)
    public void onBtnRestartClick() {
        signUpListener.onBtnRestartClick();
    }

    private void setActionBarTitle() {
        getActivity().setTitle(R.string.title_fragment_sign_up_step3);
    }

    public interface OnSignUpStep3Listener {
        void onBtnSendMailClick();

        void onBtnRestartClick();
    }
}
