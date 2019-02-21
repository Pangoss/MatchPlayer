package fr.fengdavid.matchplayer.views;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;

import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import android.databinding.DataBindingUtil;
import android.support.design.widget.Snackbar;

import fr.fengdavid.matchplayer.R;
import fr.fengdavid.matchplayer.databinding.ActivityLoginBinding;
import fr.fengdavid.matchplayer.di.DaggerAppComponent;
import fr.fengdavid.matchplayer.viewmodels.LoginViewModel;
import com.rengwuxian.materialedittext.MaterialEditText;

import javax.inject.Inject;

public class LoginActivity extends AppCompatActivity implements LoginViewModel.ViewListener {

    @Inject
    LoginViewModel viewModel;
    private LinearLayout mLlParent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityLoginBinding binding = DataBindingUtil.setContentView(
                this, R.layout.activity_login
        );

        mLlParent = binding.llParent;
        DaggerAppComponent.builder().build().inject(this);
        viewModel.setViewListener(this);
        binding.setViewModel(viewModel);

        MaterialEditText emailEditText = binding.etEmailLogin;
        MaterialEditText passwordEditText = binding.etPasswordLogin;

        emailEditText.setAutoValidate(true);
        passwordEditText.setAutoValidate(true);

        emailEditText.addValidator(viewModel.getEmailValidator());
        passwordEditText.addValidator(viewModel.getPasswordValidator());

        Button btnRegister = findViewById(R.id.btn_create);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(i);
            }
        });

        TextView tvPasswordForgot = findViewById(R.id.tv_password_forgot);
        tvPasswordForgot.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this, ForgotPasswordActivity.class); //change
                startActivity(i);
            }
        });

    }

    public void onLoginSuccess() {
        // Handle successful login
        Intent i = new Intent(LoginActivity.this, ProfileActivity.class);
        i.putExtra("email", viewModel.getEmail());
        startActivity(i);
    }

    public void onMessage(String message) {
        // Hide soft keyboard
        InputMethodManager imm = (InputMethodManager) this.getSystemService(
                Activity.INPUT_METHOD_SERVICE
        );
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        Snackbar.make(mLlParent, message, Snackbar.LENGTH_LONG).show();
    }

}
