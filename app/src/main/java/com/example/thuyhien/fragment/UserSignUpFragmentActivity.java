package com.example.thuyhien.fragment;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

public class UserSignUpFragmentActivity extends AppCompatActivity implements
        SignUpStep1Fragment.OnBtnNextClickListener,
        SignUpStep2Fragment.OnBtnDoneClickListener,
        SignUpStep3Fragment.OnSignUpStep3Listener {

    public static final String FRAGMENT_SIGN_UP_STEP_1 = "FragmentSignUp1";
    public static final String FRAGMENT_SIGN_UP_STEP_2 = "FragmentSignUp2";
    private Bundle bundleRegistrationInfo;
    private String salary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_sign_up_fragment);
        ButterKnife.bind(this);
        initViews();
    }

    @Override
    public void onBtnNextClick(Bundle bundleInfo) {
        bundleRegistrationInfo = bundleInfo;
        replaceFragment(FRAGMENT_SIGN_UP_STEP_1, new SignUpStep2Fragment());
    }

    @Override
    public void onBtnDoneClick(String salary) {
        this.salary = salary;
        replaceFragment(FRAGMENT_SIGN_UP_STEP_2, new SignUpStep3Fragment());
    }

    @Override
    public void onBtnSendMailClick() {
        Intent intentSendMail = new Intent(Intent.ACTION_SENDTO);
        intentSendMail.setData(Uri.parse("mailto:"));
        if (bundleRegistrationInfo != null) {
            intentSendMail.putExtra(Intent.EXTRA_EMAIL,
                    new String[]{bundleRegistrationInfo.getString(ConstantKeyBundle.BUNDLE_EMAIL)});
            intentSendMail.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.sign_up_mail_subject));
            intentSendMail.putExtra(Intent.EXTRA_TEXT, createContentEmail());
        }

        String intentChooseTitle = getString(R.string.asking_send_mail);
        Intent intentChooser = Intent.createChooser(intentSendMail, intentChooseTitle);

        if (intentSendMail.resolveActivity(getPackageManager()) != null) {
            startActivity(intentChooser);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onBtnRestartClick() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        while (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStackImmediate();
        }
        replaceFragment(null, new SignUpStep1Fragment());
    }

    private void initViews() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.frame_layout_sign_up_contain, new SignUpStep1Fragment());
        fragmentTransaction.commit();
    }

    private String createContentEmail() {
        return bundleRegistrationInfo.getString(ConstantKeyBundle.BUNDLE_FIRST_NAME) + "_"
                + bundleRegistrationInfo.getString(ConstantKeyBundle.BUNDLE_LAST_NAME) + "\n"
                + bundleRegistrationInfo.getString(ConstantKeyBundle.BUNDLE_PHONE_NUMBER) + "\n"
                + salary + getString(R.string.title_dollars);
    }

    private void replaceFragment(String backStackName, Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout_sign_up_contain, fragment);
        if (backStackName != null) {
            fragmentTransaction.addToBackStack(backStackName);
        }
        fragmentTransaction.commit();
    }
}
