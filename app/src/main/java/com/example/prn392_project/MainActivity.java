package com.example.prn392_project;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.IdRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.fragment.NavHostFragment;

import com.example.prn392_project.database_helper.ProductDatabaseHelper;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    NavController navController;
    BottomNavigationView bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.navHostFragment);
        navController = navHostFragment.getNavController();

        // References
        bottomNav = findViewById(R.id.bottomNavView);
        BottomNavigationView bottomNav = findViewById(R.id.bottomNavView);
        Button btnCart = findViewById(R.id.btnCart);
        ImageView imgLogo = findViewById(R.id.imgLogo);
        imgLogo.setImageResource(R.drawable.logo);

        // Set default fragment
        navigate(R.id.storeFragment, null, true);

        // Handles cart button
        btnCart.setOnClickListener(v -> {
            navigate(R.id.cartFragment);
//            goToCart();
        });

        // Handles BotNav
        bottomNav.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.botNavStore) {
                navigate(R.id.storeFragment, null, true);
            }
//            else if (item.getItemId() == R.id.botNavCart) {
//                navigate(R.id.cartFragment, null, true);
//            }

            return true;
        });
    }

    public void goBack() {
//        fragmentManager.popBackStack();
        navController.popBackStack();
    }

    public void navigate(@IdRes int destinationId, @Nullable Bundle args, boolean clearBackStack) {
        NavOptions navOptions = null;
        if (clearBackStack) {
            navOptions = new NavOptions.Builder()
                    .setPopUpTo(navController.getCurrentDestination().getId(), true)
                    .build();
        }

        navController.navigate(destinationId, args, navOptions);
    }

    public void navigate(@IdRes int destinationId, @Nullable Bundle args) {
        this.navigate(destinationId, args, false);
    }

    public void navigate(@IdRes int destinationId) {
        this.navigate(destinationId, null, false);
    }

    public void clearBackStack() {
        fragmentManager.clearBackStack(null);
    }

//    public void goToCart() {
//        Intent intent = new Intent(MainActivity.this, CartActivity.class);
//        activityResultLauncher_CartActivity.launch(intent);
//    }

    private final ActivityResultLauncher<Intent> activityResultLauncher_CartActivity =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                    new ActivityResultCallback<ActivityResult>() {
                        @Override
                        public void onActivityResult(ActivityResult result) {
                            if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                                var data = result.getData();

                            }
                        }
                    }
            );

    public void setCartBtnVisibility(int visibility) {
        Button btnCart = findViewById(R.id.btnCart);
        btnCart.setVisibility(visibility);
    }
}