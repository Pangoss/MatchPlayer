package fr.fengdavid.matchplayer.viewmodels;

import android.content.Context;
import android.databinding.BaseObservable;

import android.util.Log;

import fr.fengdavid.matchplayer.entities.User;
import fr.fengdavid.matchplayer.repositories.UserRepository;
import fr.fengdavid.matchplayer.validators.NameValidator;
import fr.fengdavid.matchplayer.validators.PasswordValidator;
import fr.fengdavid.matchplayer.validators.PhoneValidator;
import com.rengwuxian.materialedittext.validation.METValidator;

import javax.inject.Inject;

import fr.fengdavid.matchplayer.requests.profilePostRequest;

public class ProfileViewModel extends BaseObservable{

    private String mName, mPhone, mEmail;
    private String mSurname, mGender, mPassword, mStreet_number,mStreet_name, mCity, mZIP_postcode, mCountry;
    private boolean mUpdateEnabled;
    private ViewListener mListener;

    private PhoneValidator mPhoneValidator;
    private NameValidator mNameValidator;
    private PasswordValidator mPasswordValidator;
    private UserRepository mUserRepository;

    public profilePostRequest profilePostRequest = new profilePostRequest(mName,mPassword,mPhone,mSurname);
    public Context context;

    @Inject
    public ProfileViewModel(
            PhoneValidator phoneValidator,
            NameValidator nameValidator,
            PasswordValidator passwordValidator,
            UserRepository userRepository
    ) {
        mName="";
        mSurname="";
        mPassword="";
        mPhone="";
        mUpdateEnabled = false;
        this.mUserRepository = userRepository;
        mPhoneValidator = phoneValidator;
        mNameValidator = nameValidator;
        mPasswordValidator = passwordValidator;
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
        //updateDetails();
    }

    public String getSurname() {
        return mSurname;
    }

    public void setSurname(String surname) {
        this.mSurname = surname;
        //updateDetails();
    }

    public String getGender() {
        return mGender;
    }

    public void setGender(String gender) {
        this.mGender = gender;
        //updateDetails();
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        this.mPassword = password;
        //updateDetails();
    }

    public String getStreet_number() {
        return mStreet_number;
    }

    public void setStreet_number(String street_number) {
        this.mStreet_number = street_number;
        //updateDetails();
    }

    public String getStreet_name() {
        return mStreet_name;
    }

    public void setStreet_name(String street_name) {
        this.mStreet_name = street_name;
        //updateDetails();
    }

    public String getCity() {
        return mCity;
    }

    public void setCity(String city) {
        this.mCity = city;
        //updateDetails();
    }

    public String getZIP_postcode() {
        return mZIP_postcode;
    }

    public void setZIP_postcode(String zip_postcode) {
        this.mZIP_postcode = zip_postcode;
        //updateDetails();
    }

    public String getCountry() {
        return mCountry;
    }

    public void setCountry(String country) {
        this.mCountry = country;
        //updateDetails();
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
        return mPhoneValidator.isValid(mPhone, mPhone.length() == 0) ||
                mNameValidator.isValid(mName, mName.length() == 0) ||
                mNameValidator.isValid(mSurname, mSurname.length() == 0) ||
                mPasswordValidator.isValid(mPassword, mPassword.length() == 0);
    }

    public void onUpdateClick() {
        if (isInputValid()) {
            setUpdateEnabled(false);
            // Update the user in DB
            try {
                /*User user = mUserRepository.fetchByEmail(mEmail);
                user.setName(mName);
                user.setPhone(mPhone);
                mUserRepository.update(user);*/
                profilePostRequest.sendProfileDataToEc2(context,mName,mPassword,mPhone,mSurname);
                mListener.onMessage("Profile details updated.");
            } catch (Exception e) {
                Log.d("UpdateViewModel", "Error while updating user: " + e.getMessage());
                mListener.onMessage("Oops some erro occured!");

            } finally {
                setUpdateEnabled(true);
            }
        }
    }

    public METValidator getmPhoneValidator() {
        return mPhoneValidator;
    }

    public NameValidator getmNameValidator() {
        return mNameValidator;
    }

    public PasswordValidator getmPasswordValidator() {
        return mPasswordValidator;
    }

    public interface ViewListener {
        void onMessage(String message);
    }

}
