package fr.fengdavid.matchplayer.views;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import fr.fengdavid.matchplayer.R;
import fr.fengdavid.matchplayer.databinding.ActivityForgotPasswordBinding;
import fr.fengdavid.matchplayer.di.DaggerAppComponent;
import fr.fengdavid.matchplayer.viewmodels.ForgotPasswordViewModel;
import com.rengwuxian.materialedittext.MaterialEditText;

import javax.inject.Inject;

public class ForgotPasswordActivity extends AppCompatActivity implements ForgotPasswordViewModel.ViewListener {

    @Inject
    ForgotPasswordViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityForgotPasswordBinding binding = DataBindingUtil.setContentView(
                this, R.layout.activity_forgot_password
        );
        DaggerAppComponent.builder().build().inject(this);
        viewModel.setViewListener(this);
        binding.setViewModel(viewModel);
        MaterialEditText emailEditText = binding.etEmailForgot;
        emailEditText.setAutoValidate(true);
        emailEditText.addValidator(viewModel.getEmailValidator());
    }


    @Override
    public void onEmailSentSuccess() {
        Intent intent = new Intent(ForgotPasswordActivity.this, LoginActivity.class);
        intent.setFlags(
                Intent.FLAG_ACTIVITY_NEW_TASK |
                        Intent.FLAG_ACTIVITY_CLEAR_TASK
        );
        startActivity(intent);
    }

    @Override
    public void onMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.DialogTheme);
        builder.setTitle(title).setMessage(message).create().show();
    }
}
