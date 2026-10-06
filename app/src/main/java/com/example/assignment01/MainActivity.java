package com.example.assignment01;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.ScrollView;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.appcompat.widget.AppCompatTextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private static final boolean USE_XML_LAYOUT = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        if (USE_XML_LAYOUT) {
            setContentView(R.layout.activity_main);
        } else {
            LinearLayout registrationRoot = createRegistrationLayout();
            setContentView(registrationRoot);
        }
        enablePasswordVisibilityToggle(findViewById(R.id.input_password));
        enablePasswordVisibilityToggle(findViewById(R.id.input_confirm_password));
        ViewCompat.setAccessibilityHeading(findViewById(R.id.text_registration_title), true);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    // Both UI versions use the same listener for their existing end eye drawable.
    @SuppressLint("ClickableViewAccessibility") // The eye has an equivalent TalkBack action below.
    private void enablePasswordVisibilityToggle(EditText passwordInput) {
        ViewCompat.addAccessibilityAction(passwordInput,
                getString(R.string.toggle_password_visibility), (view, arguments) -> {
                    togglePasswordVisibility(passwordInput);
                    return true;
                });
        passwordInput.setOnTouchListener(new View.OnTouchListener() {
            private boolean touchingEye;

            @Override
            public boolean onTouch(View view, MotionEvent event) {
                boolean isRightToLeft = view.getLayoutDirection() == View.LAYOUT_DIRECTION_RTL;
                boolean insideEye = isRightToLeft
                        ? event.getX() <= dp(48)
                        : event.getX() >= view.getWidth() - dp(48);
                insideEye = insideEye && event.getY() >= 0
                        && event.getY() <= view.getHeight()
                        && event.getX() >= 0 && event.getX() <= view.getWidth();

                if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {
                    touchingEye = insideEye;
                    return touchingEye;
                }
                if (!touchingEye) {
                    return false;
                }
                if (event.getActionMasked() == MotionEvent.ACTION_UP) {
                    touchingEye = false;
                    if (insideEye) {
                        view.performClick();
                        togglePasswordVisibility(passwordInput);
                    }
                } else if (event.getActionMasked() == MotionEvent.ACTION_CANCEL) {
                    touchingEye = false;
                }
                return true;
            }
        });
    }

    private void togglePasswordVisibility(EditText passwordInput) {
        int selectionStart = passwordInput.getSelectionStart();
        int selectionEnd = passwordInput.getSelectionEnd();
        boolean isHidden = passwordInput.getTransformationMethod()
                instanceof PasswordTransformationMethod;
        passwordInput.setTransformationMethod(isHidden
                ? HideReturnsTransformationMethod.getInstance()
                : PasswordTransformationMethod.getInstance());
        if (selectionStart >= 0 && selectionEnd >= 0) {
            passwordInput.setSelection(selectionStart, selectionEnd);
        }
    }

    private LinearLayout createRegistrationLayout() {
        LinearLayout registrationRoot = new LinearLayout(this);
        registrationRoot.setId(R.id.main);
        registrationRoot.setOrientation(LinearLayout.VERTICAL);
        registrationRoot.setBackgroundColor(getColor(R.color.white));
        registrationRoot.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        LinearLayout titleBar = new LinearLayout(this);
        titleBar.setOrientation(LinearLayout.HORIZONTAL);
        titleBar.setGravity(Gravity.CENTER_VERTICAL);
        titleBar.setBackgroundColor(getColor(R.color.registration_purple));
        titleBar.setPaddingRelative(dp(16), 0, dp(16), 0);
        registrationRoot.addView(titleBar, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, dp(56)));

        ImageView backIcon = new ImageView(this);
        backIcon.setImageResource(R.drawable.ic_registration_back);
        backIcon.setImportantForAccessibility(View.IMPORTANT_FOR_ACCESSIBILITY_NO);
        titleBar.addView(backIcon, new LinearLayout.LayoutParams(dp(24), dp(24)));

        AppCompatTextView titleText = new AppCompatTextView(this);
        titleText.setId(R.id.text_registration_title);
        titleText.setText(R.string.user_registration);
        titleText.setTextColor(getColor(R.color.white));
        titleText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        titleText.setTypeface(titleText.getTypeface(), Typeface.BOLD);
        LinearLayout.LayoutParams titleParams = new LinearLayout.LayoutParams(
                0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f);
        titleParams.setMarginStart(dp(20));
        titleBar.addView(titleText, titleParams);

        ScrollView registrationScroll = new ScrollView(this);
        registrationScroll.setId(R.id.scroll_registration);
        registrationScroll.setFillViewport(true);
        registrationRoot.addView(registrationScroll, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, 0, 1f));

        LinearLayout registrationForm = new LinearLayout(this);
        registrationForm.setOrientation(LinearLayout.VERTICAL);
        registrationForm.setFocusableInTouchMode(true);
        registrationForm.setPadding(dp(20), dp(20), dp(20), dp(20));
        registrationScroll.addView(registrationForm, new ScrollView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        addLabel(registrationForm, R.string.full_name, 6);
        AppCompatEditText fullNameInput = addInput(registrationForm, R.id.input_full_name,
                R.string.full_name_hint, R.drawable.ic_registration_person, 0,
                InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PERSON_NAME,
                "name", EditorInfo.IME_ACTION_NEXT);
        fullNameInput.setNextFocusForwardId(R.id.input_email);

        addLabel(registrationForm, R.string.email, 6);
        AppCompatEditText emailInput = addInput(registrationForm, R.id.input_email,
                R.string.email_hint, R.drawable.ic_registration_email, 0,
                InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS,
                "emailAddress", EditorInfo.IME_ACTION_NEXT);
        emailInput.setNextFocusForwardId(R.id.input_password);

        addLabel(registrationForm, R.string.password, 6);
        AppCompatEditText passwordInput = addInput(registrationForm, R.id.input_password,
                R.string.password_hint, R.drawable.ic_registration_lock,
                R.drawable.ic_registration_eye,
                InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD,
                "newPassword", EditorInfo.IME_ACTION_NEXT);
        passwordInput.setNextFocusForwardId(R.id.input_confirm_password);

        addLabel(registrationForm, R.string.confirm_password, 6);
        AppCompatEditText confirmPasswordInput = addInput(registrationForm,
                R.id.input_confirm_password, R.string.confirm_password_hint,
                R.drawable.ic_registration_lock, R.drawable.ic_registration_eye,
                InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD,
                "newPassword", EditorInfo.IME_ACTION_NEXT);
        confirmPasswordInput.setNextFocusForwardId(R.id.input_phone_number);

        addLabel(registrationForm, R.string.phone_number, 6);
        addInput(registrationForm, R.id.input_phone_number, R.string.phone_number_hint,
                R.drawable.ic_registration_phone, 0, InputType.TYPE_CLASS_PHONE,
                "phone", EditorInfo.IME_ACTION_DONE);

        AppCompatTextView genderLabel = addLabel(registrationForm, R.string.gender, 0);
        genderLabel.setId(R.id.text_gender_label);

        RadioGroup genderGroup = new RadioGroup(this);
        genderGroup.setId(R.id.group_gender);
        genderGroup.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams genderParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        genderParams.bottomMargin = dp(12);
        registrationForm.addView(genderGroup, genderParams);
        addGenderOption(genderGroup, R.id.radio_male, R.string.male);
        addGenderOption(genderGroup, R.id.radio_female, R.string.female);
        addGenderOption(genderGroup, R.id.radio_other, R.string.other);
        genderGroup.check(R.id.radio_male);

        AppCompatButton registerButton = new AppCompatButton(
                this, null, android.R.attr.borderlessButtonStyle);
        registerButton.setId(R.id.button_register);
        registerButton.setBackgroundResource(R.drawable.registration_button_background);
        registerButton.setBackgroundTintList(
                ColorStateList.valueOf(getColor(R.color.registration_purple)));
        registerButton.setText(R.string.register);
        registerButton.setAllCaps(true);
        registerButton.setTextColor(getColor(R.color.white));
        registerButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        registerButton.setTypeface(registerButton.getTypeface(), Typeface.BOLD);
        registrationForm.addView(registerButton, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, dp(48)));

        return registrationRoot;
    }

    // Java equivalents of RegistrationLabel, RegistrationInput and RegistrationGenderOption.
    private AppCompatTextView addLabel(LinearLayout parent, int textResource,
            int bottomMarginDp) {
        AppCompatTextView label = new AppCompatTextView(this);
        label.setText(textResource);
        label.setTextColor(getColor(R.color.registration_text));
        label.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
        LinearLayout.LayoutParams labelParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        labelParams.bottomMargin = dp(bottomMarginDp);
        parent.addView(label, labelParams);
        return label;
    }

    private AppCompatEditText addInput(LinearLayout parent, int viewId, int hintResource,
            int startIconResource, int endIconResource, int inputType, String autofillHint,
            int imeAction) {
        AppCompatEditText input = new AppCompatEditText(this);
        input.setId(viewId);
        input.setBackgroundResource(R.drawable.registration_input_background);
        input.setBackgroundTintList(null);
        input.setPaddingRelative(dp(12), input.getPaddingTop(), dp(12),
                input.getPaddingBottom());
        input.setCompoundDrawablesRelativeWithIntrinsicBounds(
                startIconResource, 0, endIconResource, 0);
        input.setCompoundDrawablePadding(dp(12));
        input.setSingleLine(true);
        input.setInputType(inputType);
        input.setHint(hintResource);
        input.setTextColor(getColor(R.color.registration_text));
        input.setHintTextColor(getColor(R.color.registration_hint));
        input.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        input.setImeOptions(imeAction);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            input.setAutofillHints(autofillHint);
        }
        LinearLayout.LayoutParams inputParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, dp(48));
        inputParams.bottomMargin = dp(12);
        parent.addView(input, inputParams);
        return input;
    }

    private void addGenderOption(RadioGroup parent, int viewId, int textResource) {
        AppCompatRadioButton genderOption = new AppCompatRadioButton(this);
        genderOption.setId(viewId);
        genderOption.setText(textResource);
        genderOption.setTextColor(getColor(R.color.registration_text));
        genderOption.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        genderOption.setButtonTintList(
                ColorStateList.valueOf(getColor(R.color.registration_purple)));
        parent.addView(genderOption, new RadioGroup.LayoutParams(0, dp(48), 1f));
    }

    private int dp(int value) {
        return Math.round(value * getResources().getDisplayMetrics().density);
    }
}
