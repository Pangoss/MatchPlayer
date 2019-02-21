package fr.fengdavid.matchplayer.viewmodels;

import android.content.Context;
import android.databinding.BaseObservable;
import android.util.Log;

import fr.fengdavid.matchplayer.LoginApplication;
import fr.fengdavid.matchplayer.entities.User;
import fr.fengdavid.matchplayer.repositories.UserRepository;
import fr.fengdavid.matchplayer.validators.EmailValidator;
import fr.fengdavid.matchplayer.validators.PasswordValidator;

import com.rengwuxian.materialedittext.validation.METValidator;

import javax.inject.Inject;

import fr.fengdavid.matchplayer.requests.loginRequest;
import fr.fengdavid.matchplayer.LoginApplication;

public class LoginViewModel extends BaseObservable{

    private String mEmail, mPassword, mToken, mAuth;
    private int mId;
    private boolean mLoginEnabled;
    private ViewListener mListener;
    private EmailValidator mEmailValidator;
    private PasswordValidator mPasswordValidator;

    private UserRepository mUserRepository;
    private User user = new User(mId, mEmail, mPassword, mToken);

    public loginRequest loginRequest = new loginRequest(mEmail,mPassword);
    public Context context;

    //LoginApplication loginApplication = (LoginApplication) context;
    @Inject
    public LoginViewModel(
            EmailValidator emailValidator,
            PasswordValidator passwordValidator,
            UserRepository userRepository
    ) {
        mEmail = "";
        mPassword = "";
        mEmailValidator = emailValidator;
        mPasswordValidator = passwordValidator;
        this.mUserRepository = userRepository;
    }

    public void setViewListener(ViewListener listener) {
        this.mListener = listener;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        this.mEmail = email;
        notifyChange();
        setLoginEnabled(isInputValid());
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        this.mPassword = password;
        notifyChange();
        setLoginEnabled(isInputValid());
    }

    public boolean isLoginEnabled() {
        return mLoginEnabled;
    }

    public void setLoginEnabled(boolean loginEnabled) {
        this.mLoginEnabled = loginEnabled;
        notifyChange();
    }

    private boolean isInputValid() {
        return mEmailValidator.isValid(mEmail, mEmail.length() == 0) &&
                mPasswordValidator.isValid(mPassword, mPassword.length() == 0);
    }

    public void setmAuth(String auth){
        this.mAuth = auth;
        notifyChange();
    }

    public void setmId(int id){
        this.mId = id;
        notifyChange();
    }

    public void onLoginClick() {
        if (isInputValid()) {
            setLoginEnabled(false);
            try {
                //User user = mUserRepository.fetchByEmail(mEmail);
                /*if (user != null && user.getEmail().equals(mEmail)) {
                    // User exists in local DB, check for password
                    if (user.getPassword().equals(mPassword)) {
                        // Login successful
                        mListener.onLoginSuccess();
                    } else {
                        // Wrong mPassword
                        mListener.onMessage("Wrong password. Please retry.");
                    }
                } else {
                    // User not found
                    mListener.onMessage("Email not Registered. Please Register first.");
                }*/
                loginRequest.sendLoginDataToEc2(context,mEmail,mPassword);
                setmAuth(loginRequest.getAuth());
                setmId(loginRequest.getId());
                user.setid(mId);
                mToken = loginRequest.getToken();
                user.settoken(mToken);
                Log.i("mAuth ",mAuth);
                // pass token and id in global variable
                //loginApplication.setToken(mToken);
                //loginApplication.setId(mId);
                if(mAuth.equals("true")){
                    mListener.onLoginSuccess();
                }else{
                    Log.i("mAuth ",mAuth);
                    mListener.onMessage("Wrong password. Please retry.");
                }
            } catch (Exception e) {
                Log.d("LoginViewModel", "Error while saving: " + e.getMessage());
            } finally {
                setLoginEnabled(true);
            }
        }
    }

    public METValidator getEmailValidator() {
        return mEmailValidator;
    }

    public PasswordValidator getPasswordValidator() {
        return mPasswordValidator;
    }

    public interface ViewListener {

        void onLoginSuccess();

        void onMessage(String message);
    }
}
