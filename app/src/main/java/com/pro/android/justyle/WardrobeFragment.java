package com.pro.android.justyle;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.Context;
import android.content.ContentResolver;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.net.URI;
import java.util.UUID;

import static android.app.Activity.RESULT_OK;


public class WardrobeFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "WardrobeFragment";
    private static final int PICK_IMAGE_REQUEST = 234;

    private FirebaseAuth mFirebaseAuth;
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private TextView mTextViewNickname;
    private FirebaseFirestore mFirebaseFirestore;
    private DocumentReference mDocumentReference;
    private Button uploadButton, chooseButton;
    private ImageView imageView;
    private Uri filePath;

    StorageReference storageReference;




    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.fragment_wardrobe, container, false);

        mFirebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mFirebaseAuth.getCurrentUser();
        mFirebaseFirestore = FirebaseFirestore.getInstance();
        mTextViewNickname = (TextView) v.findViewById(R.id.Nickname);
        uploadButton = (Button) v.findViewById(R.id.uploadButtonId);
        chooseButton = (Button) v.findViewById(R.id.ChooseButtonId);
        imageView = (ImageView) v.findViewById(R.id.imageViewId);

        chooseButton.setOnClickListener(this);
        uploadButton.setOnClickListener(this);

        storageReference = FirebaseStorage.getInstance().getReference();





        mFirebaseFirestore.collection("users").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()){
                        Log.d(TAG, documentSnapshot.getId()+ " => " +documentSnapshot.getData());
                    }
                }else{
                    Log.w(TAG, "Error getting document", task.getException());
                }
            }
        });


        String userID = getString(getId());
        mDocumentReference = mFirebaseFirestore.collection("users").document(userID);

        mTextViewNickname.setText(user.getEmail());


        return v;

        }

        private void showFileChoose(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"select an image"), PICK_IMAGE_REQUEST);
        }

        private void uploadFile (){

        if (filePath != null){


            StorageReference riversRef = storageReference.child("images/items.jpg");

            riversRef.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            // Get a URL to the uploaded content
                            Task<Uri> downloadUrl = taskSnapshot.getMetadata().getReference().getDownloadUrl();


                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Handle unsuccessful uploads
                            // ...
                        }
                    })
            ;
        }else {
            // error toast
        }
        }




    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() !=null){
            filePath = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), filePath);
                //getActiviy() method was used before getContentResolver() because this is a fragment;
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public void onClick(View v) {
        if (v == chooseButton ) {

            showFileChoose();
            //open camera roll
        }
        else if (v == uploadButton){

            //upload picture to firebase storage
            uploadFile();

        }
        }

}



