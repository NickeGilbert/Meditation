package com.myprojects.nicklasgilbertsson.meditation;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.annotation.NonNull;

import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.myprojects.nicklasgilbertsson.meditation.AccountActivity.LoginActivity;
import com.myprojects.nicklasgilbertsson.meditation.AccountActivity.SignupActivity;
import com.myprojects.nicklasgilbertsson.meditation.AccountActivity.User;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class ProfileActivity extends Fragment {

    View view;

    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("users");
    String userId;

    private Button btnChangePassword, btnRemoveUser, changePassword, remove, signOut;
    private TextView email;
    private EditText newPassword;
    private ProgressBar progressBar;
    private FirebaseAuth auth;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = inflater.inflate(R.layout.activity_profile, container, false);
        auth = FirebaseAuth.getInstance();
        email = (TextView) view.findViewById(R.id.useremail);

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userId = mDatabase.getKey();
        setDataToView(user);
       // removeUser();
        Log.d(TAG, "USEREMAIL: EMAIL " + user.getEmail() + " KEY" + userId);

        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                    getActivity().finish();
                }
            }
        };

        btnChangePassword = (Button) view.findViewById(R.id.change_password_button);
        btnRemoveUser = (Button) view.findViewById(R.id.remove_user_button);
        changePassword = (Button) view.findViewById(R.id.changePass);

        remove = (Button) view.findViewById(R.id.remove);
        signOut = (Button) view.findViewById(R.id.sign_out);

        newPassword = (EditText) view.findViewById(R.id.newPassword);

        newPassword.setVisibility(View.GONE);
        changePassword.setVisibility(View.GONE);
        remove.setVisibility(View.GONE);

        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);

        if (progressBar != null) {
            progressBar.setVisibility(View.GONE);
        }

        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newPassword.setVisibility(View.VISIBLE);
                changePassword.setVisibility(View.VISIBLE);
                remove.setVisibility(View.GONE);
            }
        });

        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user != null && !newPassword.getText().toString().trim().equals("")) {
                    if (newPassword.getText().toString().trim().length() < 6) {
                        newPassword.setError("Password to short, enter minimum 6 characters");
                        progressBar.setVisibility(View.GONE);
                    } else {
                        user.updatePassword(newPassword.getText().toString().trim()).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(getActivity(), "Password is updated, sign in with the new password!", Toast.LENGTH_SHORT).show();
                                    signOut();
                                    progressBar.setVisibility(View.GONE);
                                } else {
                                    Toast.makeText(getActivity(), "Failed to update password!", Toast.LENGTH_SHORT).show();
                                    progressBar.setVisibility(View.GONE);
                                }
                            }
                        });
                    }
                } else if (newPassword.getText().toString().trim().equals("")) {
                    newPassword.setError("Enter password");
                    progressBar.setVisibility(View.GONE);
                }
            }
        });

        btnRemoveUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                if (user != null) {
                    user.delete()
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {

                                DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                                Query usersQuery = ref.child("users").orderByChild("email").equalTo(user.getEmail());

                                usersQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        for (DataSnapshot usersQuery: dataSnapshot.getChildren()) {
                                            usersQuery.getRef().removeValue();
                                        }
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {
                                        Log.e(TAG, "onCancelled", databaseError.toException());
                                    }
                                });

                                Toast.makeText(getActivity(), "Your profile is deleted: (Create an accound now!)", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getActivity(), SignupActivity.class));
                                getActivity().finish();
                                progressBar.setVisibility(View.GONE);
                            } else {
                                Toast.makeText(getActivity(), "Failed to delete your account!", Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                            }
                        }
                    });
                }
            }
        });

        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
            }
        });
        return view;
    }

    private void removeUser() {




        //DatabaseReference userPath = FirebaseDatabase.getInstance().getReference("users").child(id);
       // userPath.removeValue();

    }

    @SuppressLint("SetTextI18n")
    private void setDataToView(FirebaseUser user) {
        email.setText("User Email: " + user.getEmail());
    }

    FirebaseAuth.AuthStateListener authListener = new FirebaseAuth.AuthStateListener() {
        @Override
        public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
            FirebaseUser user = firebaseAuth.getCurrentUser();
            if (user == null) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
            } else {
                setDataToView(user);
            }
        }
    };

    public void signOut() {
        auth.signOut();
    }

    FirebaseAuth.AuthStateListener authStateListener = new FirebaseAuth.AuthStateListener() {
        @Override
        public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
            FirebaseUser user = firebaseAuth.getCurrentUser();
            if (user == null) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);

        // Set title bar
        ((BottomNavigationActivity) getActivity())
                .setActionBarTitle("About");
    }

    @Override
    public void onStart() {
        super.onStart();
        auth.addAuthStateListener(authListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (authListener != null)
            auth.removeAuthStateListener(authListener);
    }


}