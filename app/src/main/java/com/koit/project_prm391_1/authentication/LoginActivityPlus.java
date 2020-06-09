package com.koit.project_prm391_1.authentication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.koit.project_prm391_1.MainScreenActivity;
import com.koit.project_prm391_1.R;
import com.koit.project_prm391_1.SplashActivity;
import com.koit.project_prm391_1.object.Product;


public class LoginActivityPlus extends AppCompatActivity implements FirebaseAuth.AuthStateListener {

    private static final String TAGE_MAIL = "EmailPassword";
    private static final String TAG_FB = "FacebookLogin";
    private static final String TAG_GG = "GGLogin";
    private static final int RC_SIGN_IN = 9001;

    private EditText etEmail, etPassword;
    private Button btnLogin, btnFbLogin;
    private LoginButton loginButton;

    private FirebaseAuth mAuth;
    private CallbackManager callbackManager;
    private GoogleSignInClient mGoogleSignInClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_plus);

        mAuth = FirebaseAuth.getInstance();
        callbackManager = CallbackManager.Factory.create();

        findViewByID();

    }

    private void findViewByID() {
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        loginButton = findViewById(R.id.login_button);
        btnFbLogin = findViewById(R.id.btnFbLogin);
    }

    public void signInWithEmail(View view) {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        if (validateForm(etEmail) && validateForm(etPassword)) {
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Log.d(TAGE_MAIL, "signInWithEmail:success");
                        Toast.makeText(LoginActivityPlus.this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginActivityPlus.this, MainScreenActivity.class);
                        startActivity(intent);
                    } else {
                        Log.d(TAGE_MAIL, "signInWithEmail:failure", task.getException());
                        Toast.makeText(LoginActivityPlus.this, "Đăng nhập không thành công! Email hoặc mật khẩu không đúng!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private boolean validateForm(EditText editText) {
        boolean valid = true;
        String text = editText.getText().toString().trim();
        if (TextUtils.isEmpty(text)) {
            editText.setError("Ở đây không được rỗng");
            valid = false;
        } else {
            etEmail.setError(null);
        }
        return valid;
    }

    public void signInWithFacebook(View view) {
        loginButton.setReadPermissions("email", "public_profile");
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                String tokenID = loginResult.getAccessToken().getToken();
                AuthCredential authCredential = FacebookAuthProvider.getCredential(tokenID);
                mAuth.signInWithCredential(authCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG_FB, "signInWithCredential:success");
                            Toast.makeText(LoginActivityPlus.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginActivityPlus.this, MainScreenActivity.class);
                            startActivity(intent);
                        } else {
                            Log.d(TAG_FB, "signInWithCredential:failure", task.getException());
                            Toast.makeText(LoginActivityPlus.this, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {
                Log.d(TAG_FB, "signInWithCredential:failure");
                Toast.makeText(LoginActivityPlus.this, "" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        if (view == btnFbLogin) {
            loginButton.performClick();
        }

    }

    public void signInWithGoogle(View view) {
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestProfile()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions);

        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);


    }

    private void fireAuthWithGoogle(GoogleSignInAccount account) {
        Log.d(TAG_GG, "firebaseAuthWithGoogle:" + account.getId());
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG_GG, "signInWithCredential:success");
                            Toast.makeText(LoginActivityPlus.this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginActivityPlus.this, MainScreenActivity.class);
                            startActivity(intent);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG_GG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(LoginActivityPlus.this, "Đăng nhập thất bại!", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }

    public void clickToRegister(View view) {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.register_loyout);

        final EditText etEmail = dialog.findViewById(R.id.etEmail);
        final EditText etPassword = dialog.findViewById(R.id.etPassword);
        final EditText etName = dialog.findViewById(R.id.etName);
        Button btnRegister = dialog.findViewById(R.id.btnRegister);
        Button btnCancel = dialog.findViewById(R.id.btnCancel);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etEmail.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                final String name = etName.getText().toString().trim();

                if (validateForm(etEmail) && validateForm(etPassword) && validateForm(etName)) {
                    mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Log.d(TAGE_MAIL, "createUserWithEmail:success");
                                UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder()
                                        .setDisplayName(name).build();
                                FirebaseUser user = mAuth.getCurrentUser();
                                user.updateProfile(profileChangeRequest);
                                Intent intent = new Intent(LoginActivityPlus.this, SplashActivity.class);
                                startActivity(intent);

                            } else {
                                Log.d(TAGE_MAIL, "createUserWithEmail:failure", task.getException());
                                Toast.makeText(LoginActivityPlus.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }


            }


        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        dialog.show();
    }

    public void resetPassword(View view) {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.reset_password_layout);
        final EditText etEmail = dialog.findViewById(R.id.etEmail);
        Button btnConfirm = dialog.findViewById(R.id.btnConfirm);
        Button btnCancel = dialog.findViewById(R.id.btnCancel);

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateForm(etEmail)) {
                    String email = etEmail.getText().toString().trim();
                    mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Log.d(TAGE_MAIL, "Email sent.");
                            AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivityPlus.this);
                            builder.setMessage("Đã gửi!Bạn vui lòng kiểm tra email để đặt lại mật khẩu!");
                            builder.setCancelable(false);
                            builder.setPositiveButton("Đồng ý", null);
                            AlertDialog alertDialog = builder.create();
                            alertDialog.show();
                            dialog.cancel();
                        }
                    });

                }
            }
        });


        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

        dialog.show();
    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                fireAuthWithGoogle(account);
            } catch (ApiException e) {
                Log.w(TAG_GG, "Google sign in failed", e);
            }
        }
    }

    @Override
    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null) {
            Intent intent = new Intent(this, SplashActivity.class);
            startActivity(intent);
        }
    }
}
