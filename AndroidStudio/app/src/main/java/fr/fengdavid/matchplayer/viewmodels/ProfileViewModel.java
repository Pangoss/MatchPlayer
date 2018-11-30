package fr.fengdavid.matchplayer.viewmodels;

import android.databinding.BaseObservable;

import android.util.Log;

import fr.fengdavid.matchplayer.entities.User;
import fr.fengdavid.matchplayer.repositories.UserRepository;
import fr.fengdavid.matchplayer.validators.NameValidator;
import fr.fengdavid.matchplayer.validators.PhoneValidator;
import com.rengwuxian.materialedittext.validation.METValidator;

import javax.inject.Inject;

public class ProfileViewModel extends BaseObservable{

    private String mName, mPhone, mEmail;
    private boolean mUpdateEnabled;
    private ViewListener mListener;

    private PhoneValidator mPhoneValidator;
    private NameValidator mNameValidator;
    private UserRepository mUserRepository;

    @Inject
    public ProfileViewModel(
            PhoneValidator phoneValidator,
            NameValidator nameValidator,
            UserRepository userRepository
    ) {
        mUpdateEnabled = false;
        this.mUserRepository = userRepository;
        mPhoneValidator = phoneValidator;
        mNameValidator = nameValidator;
    }

    public void setViewListener(ViewListener listener) {
        this.mListener = listener;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
        notifyChange();
        setUpdateEnabled(isInputValid());
    }

    public String getPhone() {
        return mPhone;
    }

    public void setPhone(String phone) {
        this.mPhone = phone;
        notifyChange();
        setUpdateEnabled(isInputValid());
    }

    public String getEmail() {
        return mEmail;

    }

    public void setEmail(String email) {
        this.mEmail = email;
        updateDetails();
    }

    private void updateDetails() {
        if (mEmail != null && mEmail.length() > 0) {
            User user = mUserRepository.fetchByEmail(mEmail);
            this.mName = user.getName();
            this.mPhone = user.getPhone();
        } else {
            mListener.onMessage("Error while signing in. Please retry.");
        }
    }

    public boolean isUpdateEnabled() {
        return mUpdateEnabled;
    }

    public void setUpdateEnabled(boolean updateEnabled) {
        this.mUpdateEnabled = updateEnabled;
        notifyChange();
    }

    private boolean isInputValid() {
        return mPhoneValidator.isValid(mPhone, mPhone.length() == 0) &&
                mNameValidator.isValid(mName, mName.length() == 0);
    }

    public void onUpdateClick() {
        if (isInputValid()) {
            setUpdateEnabled(false);
            // Update the user in DB
            try {
                User user = mUserRepository.fetchByEmail(mEmail);
                user.setName(mName);
                user.setPhone(mPhone);
                mUserRepository.update(user);
                mListener.onMessage("Profile details updated.");
            } catch (Exception e) {
                Log.d("UpdateViewModel", "Error while updating user: " + e.getMessage());
                mListener.onMessage("Oops some erro occured!");

            } finally {
                setUpdateEnabled(true);
            }
        }
    }

    public METValidator getPhoneValidator() {
        return mPhoneValidator;
    }

    public NameValidator getmNameValidator() {
        return mNameValidator;
    }

    public interface ViewListener {
        void onMessage(String message);
    }

}
