package com.platzi.platzigram2.post.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.platzi.platzigram2.PlatzigramApplication;
import com.platzi.platzigram2.R;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;

public class NewPostActivity extends AppCompatActivity {

    private static final String TAG = "NewPostActivity";
    private ImageView imgPhoto;
    private Button btnCreatePost;
    private String photoPath;
    private PlatzigramApplication app;
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);
        app = (PlatzigramApplication) getApplicationContext();
        storageReference = app.getStorageReference();

        imgPhoto = (ImageView) findViewById(R.id.imgPhoto);
        btnCreatePost = (Button) findViewById(R.id.btnCreatePost);

        if (getIntent().getExtras() != null){
            photoPath = getIntent().getExtras().getString("PHOTO_PATH_TEMP");
            showPhoto();
        }

        btnCreatePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploapPhoto();
            }
        });
    }

    private void uploapPhoto() {
        imgPhoto.setDrawingCacheEnabled(true);
        imgPhoto.buildDrawingCache();

        Bitmap bitmap = imgPhoto.getDrawingCache();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);

        byte[] photByte = baos.toByteArray();
        String photoName = photoPath.substring(photoPath.lastIndexOf("/")+1, photoPath.length());

        final StorageReference photoReference = storageReference.child("postImages/"+photoName);

        final UploadTask uploadTask = photoReference.putBytes(photByte);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w(TAG,"Error al subir la foto " + e.toString());
                e.printStackTrace();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                /*Task<Uri> uriPhoto = taskSnapshot.getStorage().getDownloadUrl();
                String photoURL = uriPhoto.toString();
                Log.w(TAG,"URL Photo > " + photoURL);
                finish();*/

                /*Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                while (!uriTask.isSuccessful());
                Uri downloadUrl = uriTask.getResult();
                String photoUrl = downloadUrl.toString();
                Log.w(TAG, "URL PHOTO > " + photoUrl);
                finish();*/

                photoReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uriPhoto){
                        String photoURL = uriPhoto.toString();
                        Log.w(TAG,"URL Photo > " + photoURL);
                    }
                });
                //destruyo el activity luego de subir la foto
                finish();
            }
        });

    }

    private void showPhoto(){
        Picasso.get().load(photoPath).into(imgPhoto);
    }
}
