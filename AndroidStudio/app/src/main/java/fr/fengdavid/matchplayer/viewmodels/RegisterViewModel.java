package fr.fengdavid.matchplayer.viewmodels;

import android.content.Context;
import android.databinding.BaseObservable;

import fr.fengdavid.matchplayer.entities.User;
import fr.fengdavid.matchplayer.repositories.UserAlreadyExistsException;
import fr.fengdavid.matchplayer.repositories.UserRepository;
import fr.fengdavid.matchplayer.validators.EmailValidator;
import fr.fengdavid.matchplayer.validators.NameValidator;
import fr.fengdavid.matchplayer.validators.PasswordValidator;
import fr.fengdavid.matchplayer.validators.PhoneValidator;


import com.rengwuxian.materialedittext.validation.METValidator;

import javax.inject.Inject;

import fr.fengdavid.matchplayer.requests.registerRequest;

public class RegisterViewModel extends BaseObservable {

    private String mName, mPhone, mEmail, mPassword;
    private boolean mRegisterEnabled;
    private ViewListener mListener;

    private PhoneValidator mPhoneValidator;
    private EmailValidator mEmailValidator;
    private NameValidator mNameValidator;
    private PasswordValidator mPasswordValidator;
    private UserRepository mUserRepository;

    public registerRequest register_request_data = new registerRequest(mName,mPassword,mPhone,mEmail);
    public Context context;

    @Inject
    public RegisterViewModel(
            PhoneValidator phoneValidator,
            EmailValidator emailValidator,
            NameValidator nameValidator,
            PasswordValidator passwordValidator,
            UserRepository userRepository
    ) {
        mName = "";
        mPhone = "";
        mEmail = "";
        mPassword = "";
        mRegisterEnabled = false;
        this.mPhoneValidator = phoneValidator;
        this.mEmailValidator = emailValidator;
        this.mNameValidator = nameValidator;
        this.mPasswordValidator = passwordValidator;
        this.mUserRepository = userRepository;
    }

    public void setErrorListener(ViewListener listener) {
        this.mListener = listener;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
        notifyChange();
        setRegisterEnabled(isInputValid());
    }

    public String getPhone() {
        return mPhone;
    }

    public void setPhone(String phone) {
        this.mPhone = phone;
        notifyChange();
        setRegisterEnabled(isInputValid());
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        this.mEmail = email;
        notifyChange();
        setRegisterEnabled(isInputValid());
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        this.mPassword = password;
        notifyChange();
        setRegisterEnabled(isInputValid());
    }

    public boolean isRegisterEnabled() {
        return mRegisterEnabled;
    }

    public void setRegisterEnabled(boolean registerEnabled) {
        this.mRegisterEnabled = registerEnabled;
        notifyChange();
    }

    public boolean isInputValid() {
        return mPhoneValidator.isValid(mPhone, mPhone.length() == 0) &&
                mEmailValidator.isValid(mEmail, mEmail.length() == 0) &&
                mNameValidator.isValid(mName, mName.length() == 0) &&
                mPasswordValidator.isValid(mPassword, mPassword.length() == 0);
    }

    public void onRegisterClick() {
        if (isInputValid()) {
            setRegisterEnabled(false);
            try {
                // Save the user in DB
                register_request_data.sendRegisterDataToEc2(context,mName,mPassword,mPhone,mEmail);
                //mUserRepository.save(new User(mEmail, mName, mPhone, mPassword));
                mListener.onLoginSuccess();
            } /*catch (UserAlreadyExistsException e) {
                Log.d("RegisterViewModel", "Error while saving: " + e.getMessage());
                mListener.onError("User Already Exists", "User with given mEmail already exists.");
            }*/
            finally {
                setRegisterEnabled(true);
            }
        }
    }

    public METValidator getmPhoneValidator() {
        return mPhoneValidator;
    }

    public METValidator getmEmailValidator() {
        return mEmailValidator;
    }

    public NameValidator getmNameValidator() {
        return mNameValidator;
    }

    public PasswordValidator getmPasswordValidator() {
        return mPasswordValidator;
    }

    public interface ViewListener {
        void onLoginSuccess();
        void onError(String header, String message);
    }

}
