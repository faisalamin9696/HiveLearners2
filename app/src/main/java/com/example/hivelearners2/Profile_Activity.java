package com.example.hivelearners2;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;

import java.io.ByteArrayOutputStream;
import java.io.File;

public class Profile_Activity extends AppCompatActivity {

    private EditText username_tv;
    private TextView email_tv;
    private ImageView profile_image;
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private MaterialButton editProfile_btn;
    private Uri profileImageUri, profileImageDownloadUri = null;
    private ProgressDialog progressDialog;
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private StorageReference storageRef = storage.getReference();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        username_tv = findViewById(R.id.profile_username_tv);
        email_tv = findViewById(R.id.profile_email_tv);
        profile_image = findViewById(R.id.profile_image_iv);
        editProfile_btn = findViewById(R.id.editProfile_btn);
        progressDialog = new ProgressDialog(Profile_Activity.this);
        username_tv.setText(firebaseAuth.getCurrentUser().getDisplayName());
        email_tv.setText(firebaseAuth.getCurrentUser().getEmail());
        profileImageUri = firebaseAuth.getCurrentUser().getPhotoUrl();
        disableEdit();

        ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Bitmap image = (Bitmap) result.getData().getExtras().get("data");
                            // CALL THIS METHOD TO GET THE URI FROM THE BITMAP
                            Uri tempUri = getImageUri(getApplicationContext(), image);
                            // CALL THIS METHOD TO GET THE ACTUAL PATH
                            File finalFile = new File(getRealPathFromURI(tempUri));
                            profile_image.setImageBitmap(image);
                            profileImageUri = Uri.fromFile(finalFile);
                            //upload_image(Uri.fromFile(finalFile));
                        }
                    }
                });

        try {
            Glide.with(Profile_Activity.this).load(firebaseAuth.getCurrentUser().getPhotoUrl()).into(profile_image);
        } catch (Exception e) {
            e.printStackTrace();
        }
        profile_image.setOnClickListener(view -> {
            Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
            someActivityResultLauncher.launch(intent);

        });


        editProfile_btn.setOnClickListener(view -> {
            if (editProfile_btn.getText().toString().equalsIgnoreCase("edit")) {
                enableEdit();
                editProfile_btn.setText("UPDATE");
            } else if (editProfile_btn.getText().toString().equalsIgnoreCase("update")) {
                String username = username_tv.getText().toString().trim();
                if (TextUtils.isEmpty(username)) {
                    username_tv.setError("Invalid Name");
                    return;
                }
                updateProfile(username);
                disableEdit();
                editProfile_btn.setText("EDIT");
            }

        });
    }


    private void disableEdit() {
        profile_image.setEnabled(false);
        username_tv.setEnabled(false);
    }

    private void enableEdit() {
        profile_image.setEnabled(true);
        username_tv.setEnabled(true);
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public String getRealPathFromURI(Uri uri) {
        String path = "";
        if (getContentResolver() != null) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            if (cursor != null) {
                cursor.moveToFirst();
                int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                path = cursor.getString(idx);
                cursor.close();
            }
        }
        return path;
    }

    private void upload_image_updateUser(String username) {
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        progressDialog.setCancelable(false);

        StorageReference photoRef = storageRef.child("profile_images").child(FirebaseAuth.getInstance().getUid() + ".jpg");
        photoRef.delete().addOnCompleteListener(task3 -> {
            if (task3.isSuccessful()) {
                photoRef.putFile(profileImageUri).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        photoRef.getDownloadUrl().addOnCompleteListener(task1 -> {
                            if (task1.isSuccessful()) {
                                profileImageDownloadUri = task1.getResult();
                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                        .setDisplayName(username)
                                        .setPhotoUri(profileImageDownloadUri)
                                        .build();
                                assert user != null;
                                user.updateProfile(profileUpdates).addOnCompleteListener(task2 -> {
                                    if (task2.isSuccessful()) {
                                        if (progressDialog.isShowing()) {
                                            progressDialog.cancel();
                                        }
                                        Toast.makeText(this, "Updated Successfully", Toast.LENGTH_SHORT).show();
                                        disableEdit();

                                    } else {
                                        Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show();

                                    }
                                });

                            } else {
                                if (progressDialog.isShowing()) {
                                    progressDialog.cancel();
                                }
                                Toast.makeText(Profile_Activity.this, "Fail to upload image", Toast.LENGTH_SHORT).show();
                            }

                        });
                    } else {
                        Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show();
                        if (progressDialog.isShowing()) {
                            progressDialog.cancel();
                        }
                    }

                });

            } else {
                Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show();
                if (progressDialog.isShowing()) {
                    progressDialog.cancel();
                }
            }

        });

    }

    private void updateProfile(String username) {

        if (firebaseAuth.getCurrentUser().getPhotoUrl() == profileImageUri) {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                    .setDisplayName(username)
                    .setPhotoUri(profileImageDownloadUri)
                    .build();
            assert user != null;
            user.updateProfile(profileUpdates).addOnCompleteListener(task2 -> {
                if (task2.isSuccessful()) {
                    if (progressDialog.isShowing()) {
                        progressDialog.cancel();
                    }
                    Toast.makeText(this, "Updated Successfully", Toast.LENGTH_SHORT).show();
                    disableEdit();


                } else {
                    Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show();

                }

            });

        } else {
            upload_image_updateUser(username);
        }
    }
}