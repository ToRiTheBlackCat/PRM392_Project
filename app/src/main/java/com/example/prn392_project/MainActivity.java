package com.example.prn392_project;

import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.prn392_project.cart.CartFragment;
import com.example.prn392_project.product_detail.ProductDetailFragment;
import com.example.prn392_project.store.StoreFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

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

        // Setup fragment transaction
        fragmentManager = getSupportFragmentManager();
        StoreFragment firstFragment = new StoreFragment();
//        ProductDetailFragment firstFragment = new ProductDetailFragment();

        setCurrentFragment(firstFragment, true);

        BottomNavigationView bottomNav = findViewById(R.id.bottomNavView);
        Button btnCart = findViewById(R.id.btnCart);
        btnCart.setOnClickListener(v -> {
            CartFragment cartFragment = new CartFragment();
            setCurrentFragment(cartFragment, true);
        });


        bottomNav.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.botNavStore) {
                setCurrentFragment(firstFragment, true);
            } else if (item.getItemId() == R.id.botNavCart) {
                CartFragment cartFragment = new CartFragment();
                setCurrentFragment(cartFragment, true);
            }

            return true;
        });
    }

    public void setCurrentFragment(Fragment fragment, boolean addBackStack) {
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setReorderingAllowed(true);
        fragmentTransaction.replace(R.id.mainFragment, fragment);
        if (addBackStack) {
            fragmentTransaction.addToBackStack(null);
        }
        fragmentTransaction.commit();

//        fragmentManager.beginTransaction()
//            .replace(R.id.mainFragment, fragment)
//            .commit();
    }

    public void setCurrentFragment(Fragment fragment) {
        setCurrentFragment(fragment, false);
    }

    public void goBack() {
        fragmentManager.popBackStack();
    }

    public void clearBackStack() {
        fragmentManager.clearBackStack(null);
    }
}