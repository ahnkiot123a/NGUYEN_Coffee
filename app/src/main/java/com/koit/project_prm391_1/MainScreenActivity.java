package com.koit.project_prm391_1;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.facebook.AccessToken;
import com.facebook.login.LoginManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.koit.project_prm391_1.object.User;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import de.hdodenhof.circleimageview.CircleImageView;


public class MainScreenActivity extends AppCompatActivity {

    private static final String CHANNEL_ID = "CHANNEL_ID";
    private final int NOTIFICATION_ID = 101;
    private NotificationManagerCompat managerCompat;

    private FirebaseUser currentUser;
    private TextView tvNameProfileLeft;
    private TextView tvEmailProfileLeft;
    private CircleImageView avatarLeft;
    private DrawerLayout navDrawer;
    private TextView tvNumberItemInCart;
    private int cartNumber = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        BottomNavigationView navView = findViewById(R.id.nav_view);
        NavigationView navigationView = findViewById(R.id.navigationView);
        View headerView = navigationView.inflateHeaderView(R.layout.header_nav_left);
        getSupportActionBar().hide();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        navDrawer = findViewById(R.id.drawer_layout);
        tvNameProfileLeft = headerView.findViewById(R.id.tvNameProfileLeft);
        tvEmailProfileLeft = headerView.findViewById(R.id.tvEmailLeft);
        avatarLeft = headerView.findViewById(R.id.avatarLeft);

//        Toolbar toolbar = this.findViewById(R.id.toolBarMainScreen);
//        Toolbar toolbar = tbMainView.findViewById(R.id.toolBarMainScreen);
//        tvNumberItemInCart = toolbar.findViewById(R.id.tvNumberItemInCart);
//        tvNumberItemInCart.setText("6");


        cartNumber = getCartNumber();

//
        tvNameProfileLeft.setText(currentUser.getDisplayName());
        tvEmailProfileLeft.setText(currentUser.getEmail());
        Glide.with(this).load(currentUser.getPhotoUrl()).into(avatarLeft);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_news, R.id.navigation_menu, R.id.navigation_account)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        managerCompat = NotificationManagerCompat.from(this);


        Intent intent = getIntent();
        if (intent != null) {
            if (intent.getBooleanExtra("isFirstTime", false)) {
                setNotification();
            }
        }

    }

    public void logout(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Bạn có muốn đăng xuất không?");
        builder.setCancelable(false);
        builder.setPositiveButton("Hủy", null);
        builder.setNegativeButton("Đăng xuất", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                LoginManager.getInstance().logOut();
                AccessToken.setCurrentAccessToken(null);
                Toast.makeText(MainScreenActivity.this, "Đăng xuất thành công", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainScreenActivity.this, LoginActivity.class);
                startActivity(intent);
                MainScreenActivity.this.finish();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void setNotification() {
        if (cartNumber != 0) {
            Intent intent = new Intent(this, CartActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

//            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
//                    .setSmallIcon(R.drawable.ic_shopping_cart_white_24dp)
//                    .setContentTitle("NGUYEN COFFEE")
//                    .setContentText("Bạn vẫn còn sản phẩm trong giỏ hàng. Mau thực hiện bước cuối cùng để thưởng thức...")
//                    .setStyle(new NotificationCompat.BigTextStyle()
//                            .bigText("Bạn vẫn còn sản phẩm trong giỏ hàng. Mau thực hiện bước cuối cùng để thưởng thức!..."))
//                    .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
//                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
//                    // Set the intent that will fire when the user taps the notification
//                    .setContentIntent(pendingIntent)
//                    .setAutoCancel(true);
//            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
//            // notificationId is a unique int for each notification that you must define
//            notificationManager.notify(NOTIFICATION_ID, builder.build());

            Notification notification = new NotificationCompat.Builder(this, App.NOTIFICATION_CHANEL)
                    .setSmallIcon(R.drawable.ic_shopping_cart_white_24dp)
                    .setContentTitle("NGUYEN COFFEE")
                    .setContentText("Bạn vẫn còn sản phẩm trong giỏ hàng. Mau thực hiện bước cuối cùng để thưởng thức...")
                    .setStyle(new NotificationCompat.BigTextStyle()
                            .bigText("Bạn vẫn còn sản phẩm trong giỏ hàng. Mau thực hiện bước cuối cùng để thưởng thức!..."))
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    // Set the intent that will fire when the user taps the notification
                    .setContentIntent(pendingIntent)
                    .setCategory(NotificationCompat.CATEGORY_RECOMMENDATION).build();
            managerCompat.notify(NOTIFICATION_ID, notification);
        }
    }


    public int getCartNumber() {
        InteractWithFile interactWithFile = new InteractWithFile(this);
        return interactWithFile.getCartNumber();
    }

    public void accountInfo(View view) {
        Intent intent = new Intent(this, UserDetailActivity.class);
        startActivity(intent);
    }

    public void deliveryAddress(View view) {
//        Toast.makeText(this, "Chưa có gì đâu ấn làm lol gì!", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, DiachiActivity.class);
        startActivity(intent);
    }

    public void getHistory(View view) {
        Intent intent = new Intent(this, HistoryActivity.class);
        intent.putExtra("currentUser", currentUser);
        startActivity(intent);
    }


    public void getMenuLeft(View view) {
        // If the navigation drawer is not open then open it, if its already open then close it.
        if (!navDrawer.isDrawerOpen(GravityCompat.START)) navDrawer.openDrawer(GravityCompat.START);

    }

    public void call(View view) {
        final String phoneNumber = "0395106907";

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Gọi NGUYEN COFFEE: " + phoneNumber + " ?");
        builder.setCancelable(false);
        builder.setPositiveButton("Hủy", null);
        builder.setNegativeButton("Gọi", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //String treatment = ((TextView)v.findViewById(R.id.item_name)).getText().toString();
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNumber));
                // Here, thisActivity is the current activity
                if (ContextCompat.checkSelfPermission(MainScreenActivity.this, android.Manifest.permission.CALL_PHONE)
                        != PackageManager.PERMISSION_GRANTED) {

                    ActivityCompat.requestPermissions(MainScreenActivity.this,
                            new String[]{android.Manifest.permission.CALL_PHONE},
                            100);

                    // MY_PERMISSIONS_REQUEST_CALL_PHONE is an
                    // app-defined int constant. The callback method gets the
                    // result of the request.
                } else {
                    //You already have permission
                    try {
                        startActivity(intent);
                    } catch (SecurityException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void shoppingCart(View view) {
        Intent intent = new Intent(this, CartActivity.class);
        startActivity(intent);
    }

    //địa chỉ nhà hàng
    public void getShopAddressActivity(View view) {
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);

    }

    public void getUpdateActivity(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Tính năng đang được cập nhật.Quý khách vui lòng quay lại sau. Xin cảm ơn!");
        builder.setCancelable(false);
        builder.setPositiveButton("Đồng ý", null);
        AlertDialog dialog = builder.create();
        dialog.show();

    }

    public void back(View view) {
        navDrawer.closeDrawers();
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        if (navDrawer.isDrawerOpen(GravityCompat.START)) {
            navDrawer.closeDrawers();
        }
    }
}
