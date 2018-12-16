package com.myprojects.nicklasgilbertsson.meditation;

import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.annotation.NonNull;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.login.LoginManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.myprojects.nicklasgilbertsson.meditation.account_activity.LoginActivity;

import static android.content.ContentValues.TAG;

public class ProfileActivity extends Fragment {

    View view;

    Button btnChangePassword, btnRemoveUser, changePassword, remove, signOut;
    private TextView email;
    private EditText newPassword;
    private ProgressBar progressBar;
    private FirebaseAuth auth;

    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = inflater.inflate(R.layout.activity_profile, container, false);
        auth = FirebaseAuth.getInstance();
        email = view.findViewById(R.id.useremail);

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        setDataToView(user);

        facebookPost();


        authStateListener = new FirebaseAuth.AuthStateListener() {
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

        btnChangePassword = view.findViewById(R.id.change_password_button);
        btnRemoveUser = view.findViewById(R.id.remove_user_button);
        changePassword = view.findViewById(R.id.changePass);
        remove = view.findViewById(R.id.remove);
        signOut = view.findViewById(R.id.sign_out);
        newPassword = view.findViewById(R.id.newPassword);
        progressBar = view.findViewById(R.id.progressBar);
        progressBar.setIndeterminateDrawable(getResources().getDrawable(R.drawable.progressbar_spinner));

        newPassword.setVisibility(View.GONE);
        changePassword.setVisibility(View.GONE);
        remove.setVisibility(View.GONE);

        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        if (accessToken == null) {
            btnRemoveUser.setVisibility(View.VISIBLE);
            Log.d(TAG, ">>>" + "Signed Out");
        } else {
            Log.d(TAG, ">>>" + "Signed In");
            btnChangePassword.setVisibility(View.GONE);

        }



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
                if (user.getEmail() != null && !newPassword.getText().toString().trim().equals("")) {
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
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                if (user.getEmail() != null) {
                                    user.delete()
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {

                                                        //Why doesnt this work https://stackoverflow.com/questions/37390864/how-to-delete-from-firebase-realtime-database
                                                        Query usersQuery = ref.child("users").orderByChild("email").equalTo(user.getEmail());

                                                        usersQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                                                            @Override
                                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                                for (DataSnapshot usersQuery: dataSnapshot.getChildren()) {
                                                                    usersQuery.getRef().removeValue();
                                                                }
                                                            }

                                                            @Override
                                                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                                                Log.e(TAG, "onCancelled", databaseError.toException());
                                                            }
                                                        });

                                                        Toast.makeText(getActivity(), "Your profile is deleted: (Create an accound now!)", Toast.LENGTH_SHORT).show();
                                                        startActivity(new Intent(getActivity(), LoginActivity.class));
                                                        LoginManager.getInstance().logOut();
                                                        getActivity().finish();

                                                        progressBar.setVisibility(View.GONE);
                                                    } else {
                                                        Toast.makeText(getActivity(), "Failed to delete your account!", Toast.LENGTH_SHORT).show();
                                                        progressBar.setVisibility(View.GONE);
                                                    }
                                                }
                                            });
                                }
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();
            }
        });

        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
                LoginManager.getInstance().logOut();
            }
        });
        return view;
    }

    private void facebookPost() {
        //check login

    }

    @SuppressLint("SetTextI18n")
    private void setDataToView(FirebaseUser user) {

        if (user.getDisplayName() != null) {
            email.setText("Welcome back: " + user.getDisplayName());
        } else {
        }
    }


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
        auth.addAuthStateListener(authStateListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (authStateListener != null)
            auth.removeAuthStateListener(authStateListener);
    }
}