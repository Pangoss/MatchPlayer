package fr.fengdavid.matchplayer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button b1;
    EditText email,pwd;

    TextView tx1;
    int attemptcounter=3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Change the color of register
        TextView registerView = findViewById(R.id.txtRegister);
        String registerText = "Don\'t have an account ? REGISTER HERE";

        SpannableString ss = new SpannableString(registerText);
        ForegroundColorSpan fgcWhite = new ForegroundColorSpan(Color.WHITE);
        ss.setSpan(fgcWhite,24,37,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        registerView.setText(ss);

        // Definition of login page elements
        b1 = (Button)findViewById(R.id.button);
        email = (EditText)findViewById(R.id.editText);
        pwd = (EditText)findViewById(R.id.editText2);

    }
}
