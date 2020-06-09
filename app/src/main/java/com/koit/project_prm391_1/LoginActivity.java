package com.koit.project_prm391_1;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Application;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.firebase.auth.FirebaseAuth;
import com.koit.project_prm391_1.dao.UserDAO;
import com.koit.project_prm391_1.object.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class LoginActivity extends AppCompatActivity {

    private CallbackManager callbackManager;
    private User user;
    private LinearLayout layout1;
    private RelativeLayout layout2;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_key);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        mAuth = FirebaseAuth.getInstance();


        FacebookSdk.sdkInitialize(this.getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                onBackPressed();
            }

            @Override
            public void onCancel() {
                Toast.makeText(LoginActivity.this, "Đã hủy đăng nhập", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(FacebookException error) {
                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                builder.setMessage("Không thể đăng nhập. Kiểm tra lại kết nối mạng");
                builder.setCancelable(false);
                builder.setPositiveButton("Đồng ý", null);
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

    }

    public void loginWithFacebook(View view) {
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile"));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (callbackManager.onActivityResult(requestCode, resultCode, data)) {
            return;
        }
    }

    AccessTokenTracker tokenTracker = new AccessTokenTracker() {
        @Override
        protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
            if (currentAccessToken == null) {

            } else {
                loadUserProfile(currentAccessToken);
            }
        }
    };

    private void loadUserProfile(AccessToken accessTokenTracker) {
        GraphRequest request = GraphRequest.newMeRequest(accessTokenTracker, new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject jsonObject, GraphResponse response) {
                try {
                    String name = jsonObject.getString("name");
                    String email = jsonObject.getString("email");
                    String id = jsonObject.getString("id");
                    String image_url = "https://graph.facebook.com/" + id + "/picture?type=normal";

//                    user = new User(id,name,email,null,null,null,image_url);

                    UserDAO userDAO = new UserDAO();

                    user = new User(userDAO.getMaxUserId(),name, email, image_url);
                    if(!email.trim().equals(userDAO.getLastEmail())){
                        int UserId = userDAO.getMaxUserId() + 1;
                        user = new User(UserId,name, email, image_url);
                        userDAO.addNewUser(user);
                    }
                    Intent intent = new Intent(LoginActivity.this.getApplicationContext(), MainScreenActivity.class);
                    intent.putExtra("isFirstTime", true);
                    startActivity(intent);
                    Toast.makeText(LoginActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

        Bundle parameters = new Bundle();
        parameters.putSerializable("fields", "name,first_name,last_name,email,id");
        request.setParameters(parameters);
        request.executeAsync();
    }

    public void login(View view) {
        Toast.makeText(this, "Tính năng đang được bảo trì. Quý khách vui lòng quay lại sau. Xin cảm ơn!", Toast.LENGTH_LONG).show();

    }

    @Override
    public void onBackPressed() {
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setMessage("Bạn có muốn thoát không?");
//        builder.setCancelable(false);
//        builder.setPositiveButton("Không", null);
//        builder.setNegativeButton("Có", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                Activity.finish();
//            }
//        });
//
//        AlertDialog dialog = builder.create();
//        dialog.show();
    }

    public void getUpdateActivity(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Tính năng đang được cập nhật.Quý khách có thể bằng nhập bằng Facebook để tiếp tục. Xin cảm ơn!");
        builder.setCancelable(false);
        builder.setPositiveButton("Đồng ý", null);
        AlertDialog dialog = builder.create();
        dialog.show();

    }

}
