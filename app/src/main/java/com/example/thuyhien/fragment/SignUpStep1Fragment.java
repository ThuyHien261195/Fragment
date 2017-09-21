package com.example.thuyhien.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

/**
 * Created by thuyhien on 9/19/17.
 */

public class SignUpStep1Fragment extends Fragment {
    @BindView(R.id.edit_first_name)
    EditText editFirstName;

    @BindView(R.id.edit_last_name)
    EditText editLastName;

    @BindView(R.id.edit_email)
    EditText editEmail;

    @BindView(R.id.edit_phone)
    EditText editPhone;

    @BindString(R.string.error_empty_field)
    String errorEmptyField;

    @BindString(R.string.error_invalid_field)
    String errorInvalidField;

    @BindString(R.string.hint_first_name)
    String hintFirstName;

    @BindString(R.string.hint_last_name)
    String hintLastName;

    @BindString(R.string.hint_email)
    String hintEmail;

    @BindString(R.string.hint_phone_number)
    String hintPhoneNumber;

    private OnBtnNextClickListener nextListener;

    public static SignUpStep1Fragment newInstance() {
        SignUpStep1Fragment fragment = new SignUpStep1Fragment();
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            nextListener = (OnBtnNextClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + R.string.error_implement_sign_up_step_1_listener);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (container == null) {
            return null;
        }
        View view = inflater.inflate(R.layout.fragment_sign_up_step1, container, false);
        ButterKnife.bind(this, view);
        setActionBarTitle();
        return view;
    }

    @OnClick(R.id.button_next)
    public void onBtnNextClick() {
        if (checkValidInput()) {
            Bundle bundleRegistrationInfo = createInfoBundle();
            nextListener.onBtnNextClick(bundleRegistrationInfo);
        }
    }

    @OnTextChanged(R.id.edit_email)
    public void onEditTextEmailChange(CharSequence cs, int start, int count, int after) {
        validateEmail();
    }

    @OnTextChanged(R.id.edit_phone)
    public void onEditTextPhoneChange(CharSequence cs, int start, int count, int after) {
        validatePhone();
    }

    private boolean validateFirstName() {
        if (TextUtils.isEmpty(editFirstName.getText())) {
            editFirstName.setError(String.format(errorEmptyField, hintFirstName));
            return false;
        }
        return true;
    }

    private boolean validateLastName() {
        if (TextUtils.isEmpty(editFirstName.getText())) {
            editLastName.setError(String.format(errorEmptyField, hintLastName));
            return false;
        }
        return true;
    }

    private boolean validateEmail() {
        if (TextUtils.isEmpty(editEmail.getText())) {
            editEmail.setError(String.format(errorEmptyField, hintEmail));
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(editEmail.getText()).matches()) {
            editEmail.setError(String.format(errorInvalidField, hintEmail));
            return false;
        }
        return true;
    }

    private boolean validatePhone() {
        if (TextUtils.isEmpty(editPhone.getText())) {
            editPhone.setError(String.format(errorEmptyField, hintPhoneNumber));
            return false;
        } else if (!Patterns.PHONE.matcher(editPhone.getText()).matches()) {
            editPhone.setError(String.format(errorInvalidField, hintPhoneNumber));
            return false;
        }
        return true;
    }

    private boolean checkValidInput() {
        boolean isValidFirstName = validateFirstName();
        boolean isValidLastName = validateLastName();
        boolean isValidEmail = validateEmail();
        boolean isValidPhone = validatePhone();
        return isValidFirstName && isValidLastName && isValidEmail && isValidPhone;
    }

    private Bundle createInfoBundle() {
        Bundle bundleRegistrationInfo = new Bundle();
        bundleRegistrationInfo.putString(ConstantKeyBundle.BUNDLE_FIRST_NAME, editFirstName.getText().toString());
        bundleRegistrationInfo.putString(ConstantKeyBundle.BUNDLE_LAST_NAME, editLastName.getText().toString());
        bundleRegistrationInfo.putString(ConstantKeyBundle.BUNDLE_EMAIL, editEmail.getText().toString());
        bundleRegistrationInfo.putString(ConstantKeyBundle.BUNDLE_PHONE_NUMBER, editPhone.getText().toString());
        return bundleRegistrationInfo;
    }

    private void setActionBarTitle() {
        getActivity().setTitle(R.string.title_fragment_sign_up_step1);
    }

    public interface OnBtnNextClickListener {
        void onBtnNextClick(Bundle bundleInfo);
    }
}
