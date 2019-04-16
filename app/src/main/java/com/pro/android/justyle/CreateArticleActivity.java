package com.pro.android.justyle;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.firebase.storage.FirebaseStorage;


public class CreateArticleActivity extends AppCompatActivity {

    private Button mCreateButton;
    private Firebase mRootRef;
    private EditText mArticleText;


    @Override
    public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
        setContentView(R.layout.create_article);
        Firebase.setAndroidContext(this);
// ref to firebase real time database
        mRootRef = new Firebase("https://justyle-1.firebaseio.com/Articles");

        mArticleText = (EditText) findViewById(R.id.ArticleTextId);



        mCreateButton = (Button) findViewById(R.id.buttonCreateArticleId);

        mCreateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 String description = mArticleText.getText().toString();


                Firebase mRefChild= mRootRef.child("Description");
                //set value from the user input
                mRefChild.setValue(description);

            }
        });
    }

}




