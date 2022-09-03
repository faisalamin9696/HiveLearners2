package com.example.hivelearners2;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.ByteArrayOutputStream;
import java.io.File;

public class Signup_Activity extends AppCompatActivity {

    private EditText name_et, email_et, pass_et, confirm_pass_et;
    private MaterialButton signup_btn, already_act_btn;
    private SharedPreferences sharedPreferences;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    private AlertDialog alertDialog;
    private ImageView profile_image;
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private StorageReference storageRef = storage.getReference();

    private Uri profileImageUri, profileImageDownloadUri = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        firebaseAuth = FirebaseAuth.getInstance();
        name_et = findViewById(R.id.signup_name_et);
        email_et = findViewById(R.id.signup_email_et);
        pass_et = findViewById(R.id.signup_pass_et);
        confirm_pass_et = findViewById(R.id.signup_confirm_pass_et);
        signup_btn = findViewById(R.id.signup_btn);
        already_act_btn = findViewById(R.id.already_act_btn);
        sharedPreferences = getSharedPreferences("sharedPreferences", Context.MODE_PRIVATE);
        progressDialog = new ProgressDialog(Signup_Activity.this);
        alertDialog = new AlertDialog.Builder(Signup_Activity.this).create();
        profile_image = findViewById(R.id.profileImg_iv);

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
        profile_image.setOnClickListener(view -> {
            Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
            someActivityResultLauncher.launch(intent);

        });


        signup_btn.setOnClickListener(view -> {
            String email = email_et.getText().toString().toLowerCase().trim();
            String password = pass_et.getText().toString();
            String username = name_et.getText().toString();

            if (TextUtils.isEmpty(username)) {
                name_et.setError("Invalid Name");
                return;
            }
            if (TextUtils.isEmpty(email)) {
                email_et.setError("Invalid Email");
                return;
            }
            if (TextUtils.isEmpty(password)) {
                pass_et.setError("Invalid Password");
                return;
            }
            if (!(password.equals(confirm_pass_et.getText().toString()))) {
                Toast.makeText(Signup_Activity.this, "Password not matching", Toast.LENGTH_SHORT).show();
                return;
            }

            if (profileImageUri == null) {
                Toast.makeText(Signup_Activity.this, "Select Profile Image", Toast.LENGTH_SHORT).show();
                return;
            }
            upload_image_createUser(email, password, username);


        });

        already_act_btn.setOnClickListener(view -> startActivity(new Intent(Signup_Activity.this, SignIn_Activity.class)));
    }


    private void upload_image_createUser(String email, String password, String username) {
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        progressDialog.setCancelable(false);

        StorageReference photoRef = storageRef.child("profile_images").child(FirebaseAuth.getInstance().getUid() + ".jpg");
        photoRef.putFile(profileImageUri).addOnCompleteListener(task -> {

            if (task.isSuccessful()) {
                photoRef.getDownloadUrl().addOnCompleteListener(task1 -> {
                    if (task1.isSuccessful()) {
                        profileImageDownloadUri = task1.getResult();
                        create_user(email, password, username);

                    } else {
                        if (progressDialog.isShowing()) {
                            progressDialog.cancel();
                        }
                        Toast.makeText(Signup_Activity.this, "Fail to upload image", Toast.LENGTH_SHORT).show();
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

    private void create_user(String email, String password, String username) {
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                        .setDisplayName(username)
                        .setPhotoUri(profileImageDownloadUri)
                        .build();
                assert user != null;
                user.updateProfile(profileUpdates)
                        .addOnCompleteListener(task1 -> {
                            if (task1.isSuccessful()) {
                                firebaseAuth.signOut();
                                if (progressDialog.isShowing())
                                    progressDialog.cancel();
                                alertDialog.setTitle("Done");
                                alertDialog.setMessage("Your account is created successfully");
                                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK", (dialog, which) -> {
                                    dialog.cancel();

                                });
                                alertDialog.setCancelable(false);
                            } else {

                                // Delete the account if username not save successfully to let the user retry
                                assert firebaseAuth.getCurrentUser() != null;
                                firebaseAuth.getCurrentUser().delete();
                                alertDialog.setTitle("Failed");
                                alertDialog.setMessage(task1.getException().getMessage());
                                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK", (dialog, which) -> {
                                    dialog.cancel();

                                });
                            }
                            alertDialog.show();
                        });


                //Sig up Successful
            } else {

                if (progressDialog.isShowing())
                    progressDialog.cancel();

                alertDialog.setTitle("Failed");
                alertDialog.setMessage(task.getException().getMessage());
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK", (dialog, which) -> {
                    dialog.cancel();

                });
                alertDialog.show();
                // Sign up failed
            }

        });
    }
}
