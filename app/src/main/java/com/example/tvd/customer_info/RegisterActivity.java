package com.example.tvd.customer_info;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tvd.customer_info.invoke.SendingData;
import com.example.tvd.customer_info.values.FunctionCall;

import static com.example.tvd.customer_info.values.ConstantValues.REGISTRATION_FAILURE;
import static com.example.tvd.customer_info.values.ConstantValues.REGISTRATION_SUCCESS;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    Button signup;
    EditText name, email, phonenumber, password;
    SendingData sendingData;
    String TokenId = "", cust_name = "", cust_email = "", cust_phone = "", cust_pass = "";
    ProgressDialog progressdialog;
    FunctionCall fcall;
    Typeface typeface;

    private FloatingActionButton fab;
    private CardView cvAdd;

    private final Handler mHandler;

    {
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case REGISTRATION_SUCCESS:
                        progressdialog.dismiss();
                        Intent intent = new Intent(RegisterActivity.this, StartActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                    case REGISTRATION_FAILURE:
                        progressdialog.dismiss();
                        Toast.makeText(RegisterActivity.this, "Please Check the Registration details..", Toast.LENGTH_SHORT).show();
                        name.setText("");
                        email.setText("");
                        phonenumber.setText("");
                        password.setText("");
                        name.requestFocus();
                        break;
                }
                super.handleMessage(msg);
            }
        };
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //For Hiding softkeys
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        initialize();

        // Check user login
        // If User is not logged in , This will redirect user to LoginActivity.
        /*******************I have to remove the comment line to activate session management ***************/
       /* if (sessionManager.checkLogin())
            finish();*/
        /************************************End***********************************************************/

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        signup.setOnClickListener(this);
        TokenId = "0x9851FFA7317D3E4F191A969454138816104173F9";

        ShowEnterAnimation();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animateRevealClose();
            }
        });
    }

    public void initialize()
    {
        typeface = Typeface.createFromAsset(getAssets(),"calibri.ttf");
        fab = (FloatingActionButton) findViewById(R.id.fab);
        cvAdd = (CardView) findViewById(R.id.cv_add);
        signup = (Button) findViewById(R.id.signup_btn);
        name = (EditText) findViewById(R.id.edit_name);
        email = (EditText) findViewById(R.id.edit_email);
        phonenumber = (EditText) findViewById(R.id.edit_phonenumber);
        password = (EditText) findViewById(R.id.edit_password);
        sendingData = new SendingData();
        fcall = new FunctionCall();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void ShowEnterAnimation() {
        Transition transition = TransitionInflater.from(this).inflateTransition(R.transition.fabtransition);
        getWindow().setSharedElementEnterTransition(transition);

        transition.addListener(new Transition.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {
                cvAdd.setVisibility(View.GONE);
            }

            @Override
            public void onTransitionEnd(Transition transition) {
                transition.removeListener(this);
                animateRevealShow();
            }

            @Override
            public void onTransitionCancel(Transition transition) {

            }

            @Override
            public void onTransitionPause(Transition transition) {

            }

            @Override
            public void onTransitionResume(Transition transition) {

            }


        });
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void animateRevealShow() {
        Animator mAnimator = ViewAnimationUtils.createCircularReveal(cvAdd, cvAdd.getWidth()/2,0, fab.getWidth() / 2, cvAdd.getHeight());
        mAnimator.setDuration(500);
        mAnimator.setInterpolator(new AccelerateInterpolator());
        mAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
            }

            @Override
            public void onAnimationStart(Animator animation) {
                cvAdd.setVisibility(View.VISIBLE);
                super.onAnimationStart(animation);
            }
        });
        mAnimator.start();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void animateRevealClose() {
        Animator mAnimator = ViewAnimationUtils.createCircularReveal(cvAdd,cvAdd.getWidth()/2,0, cvAdd.getHeight(), fab.getWidth() / 2);
        mAnimator.setDuration(500);
        mAnimator.setInterpolator(new AccelerateInterpolator());
        mAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                cvAdd.setVisibility(View.INVISIBLE);
                super.onAnimationEnd(animation);
                fab.setImageResource(R.drawable.plus);
                RegisterActivity.super.onBackPressed();
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
            }
        });
        mAnimator.start();
    }
    @Override
    public void onBackPressed() {
        animateRevealClose();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.signup_btn:
                if (fcall.isInternetOn(RegisterActivity.this))
                    register_user();
                else
                    Toast.makeText(this, "Please connect to internet..", Toast.LENGTH_SHORT).show();
                break;
            case R.id.signup:
                break;
        }
    }
    public void register_user() {
        cust_name = name.getText().toString();
        cust_email = email.getText().toString();
        cust_phone = phonenumber.getText().toString();
        cust_pass = password.getText().toString();
        if (name.getText().length() <= 0) {
            name.setError("Enter Name");
        } else if (email.getText().length() <= 0) {
            email.setError("Enter Email");
        } else if (phonenumber.getText().length() <= 0) {
            phonenumber.setError("Enter phone");
        } else if (password.getText().length() <= 0) {
            password.setError("Enter password");
        } else {
            //Below code for email and mobile no validation
            if (android.util.Patterns.EMAIL_ADDRESS.matcher(cust_email).matches()) {
                if (android.util.Patterns.PHONE.matcher(cust_phone).matches()) {
                    /*progressdialog = ProgressDialog.show(SignUpActivity.this, "Registration",
                            "Fetching details please wait..", true);*/
                    progressdialog = new ProgressDialog(RegisterActivity.this, R.style.MyProgressDialogstyle);
                    progressdialog.setTitle("Connecting To Server");
                    progressdialog.setMessage("Please Wait..");
                    progressdialog.show();

                    SendingData.Sign_UP signup = sendingData.new Sign_UP(mHandler);
                    signup.execute(cust_name, cust_email, cust_phone, cust_pass, TokenId);
                }else Toast.makeText(this, "Phone number is not valid!!", Toast.LENGTH_SHORT).show();
            }else Toast.makeText(this, "Email is not valid!!", Toast.LENGTH_SHORT).show();
        }
    }
}

