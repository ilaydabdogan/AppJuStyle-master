package com.pro.android.justyle;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class FrontPageActivity extends AppCompatActivity implements FragmentActionListener {

    private FirebaseAuth mFirebaseAuth;
    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;
    private Button mProfileButton, mActivityButton, mMoreButton;
    private ImageButton mCameraButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_front_page);
        mFragmentManager = getSupportFragmentManager();
        mFirebaseAuth = FirebaseAuth.getInstance();
        mActivityButton = (Button) findViewById(R.id.ActivityButton);
        mProfileButton = (Button) findViewById(R.id.ProfileButton);
        mMoreButton = (Button) findViewById(R.id.MoreButton);
        mCameraButton = (ImageButton) findViewById(R.id.CameraButton);

        mProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FrontPageActivity.this,ProfileActivity.class));
            }
        });
        mActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(FrontPageActivity.this, "Not set yet", Toast.LENGTH_SHORT).show();
            }
        });
        mMoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(FrontPageActivity.this, "Not set yet", Toast.LENGTH_SHORT).show();
            }
        });
        mCameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                startActivity(new Intent(FrontPageActivity.this,ActivityMenuList.class));
                Toast.makeText(FrontPageActivity.this, "Not set yet", Toast.LENGTH_SHORT).show();
            }
        });


        if (mFirebaseAuth.getCurrentUser() == null){
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }
        addFrontPageFragment();

    }
    private void addFrontPageFragment(){
        mFragmentTransaction = mFragmentManager.beginTransaction();

        FrontPageFragment frontPageFragment = new FrontPageFragment();
        frontPageFragment.setFragmentActionListener(this);

        mFragmentTransaction.add(R.id.fragment_container, frontPageFragment);
        mFragmentTransaction.commit();

    }

    private void addWardrobeFragment(){
        mFragmentTransaction = mFragmentManager.beginTransaction();

        WardrobeFragment wardrobeFragment = new WardrobeFragment();

        Bundle bundle = new Bundle();
        wardrobeFragment.setArguments(bundle);

        mFragmentTransaction.replace(R.id.fragment_container, wardrobeFragment);
        mFragmentTransaction.addToBackStack(null);
        mFragmentTransaction.commit();
    }

    private void addMarketplaceFragment() {
        mFragmentTransaction = mFragmentManager.beginTransaction();

        WardrobeFragment wardrobeFragment = new WardrobeFragment();

        Bundle bundle = new Bundle();
        wardrobeFragment.setArguments(bundle);

        mFragmentTransaction.replace(R.id.fragment_container, wardrobeFragment);
        mFragmentTransaction.addToBackStack(null);
        mFragmentTransaction.commit();

    }
    @Override
    public void onWardrobeFragmentClicked() {

        addWardrobeFragment();

    }
    @Override
    public void onMarketplaceFragmentClicked(){
        addMarketplaceFragment();


    }
}
