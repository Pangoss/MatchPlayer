package fr.fengdavid.matchplayer.views;

import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import fr.fengdavid.matchplayer.R;
import fr.fengdavid.matchplayer.databinding.ActivityRegisterBinding;
import fr.fengdavid.matchplayer.di.DaggerAppComponent;
import fr.fengdavid.matchplayer.viewmodels.RegisterViewModel;
import com.rengwuxian.materialedittext.MaterialEditText;

import javax.inject.Inject;

public class RegisterActivity extends AppCompatActivity implements RegisterViewModel.ViewListener {

    @Inject
    RegisterViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityRegisterBinding binding = DataBindingUtil.setContentView(
                this, R.layout.activity_register
        );

        DaggerAppComponent.builder().build().inject(this);
        viewModel.setErrorListener(this);
        binding.setViewModel(viewModel);

        MaterialEditText phoneEditText = binding.etRegisterPhone;
        MaterialEditText emailEditText = binding.etRegisterEmail;
        MaterialEditText nameEditText = binding.etRegisterName;
        MaterialEditText passwordEditText = binding.etRegisterPassword;

        phoneEditText.setAutoValidate(true);
        emailEditText.setAutoValidate(true);
        nameEditText.setAutoValidate(true);
        passwordEditText.setAutoValidate(true);

        phoneEditText.addValidator(viewModel.getmPhoneValidator());
        emailEditText.addValidator(viewModel.getmEmailValidator());
        nameEditText.addValidator(viewModel.getmNameValidator());
        passwordEditText.addValidator(viewModel.getmPasswordValidator());

        viewModel.context=this;

    }

    @Override
    public void onLoginSuccess() {
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        intent.setFlags(
                Intent.FLAG_ACTIVITY_NEW_TASK |
                        Intent.FLAG_ACTIVITY_CLEAR_TASK
        );
        startActivity(intent);
    }

    @Override
    public void onError(String title, String message) {
        redirectToLoginWithMessage(title, message);
    }

    private void redirectToLoginWithMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.DialogTheme);
        builder.setTitle(title)
                .setMessage(message)
                .setNeutralButton(
                        "Login Now",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(
                                        RegisterActivity.this, LoginActivity.class
                                );
                                intent.setFlags(
                                        Intent.FLAG_ACTIVITY_NEW_TASK |
                                                Intent.FLAG_ACTIVITY_CLEAR_TASK
                                );
                                startActivity(intent);
                            }
                        }
                ).create()
                .show();
    }

}
