package com.example.nguyendangquang_19dh110604;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.nguyendangquang_19dh110604.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserpasswordFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserpasswordFragment extends Fragment {
    TextInputEditText txtEmail, txtPassword, txtConfirmPassword;
    Button btnRegister;
    //    NavController navController;
    FirebaseAuth fAuth;
    FirebaseDatabase fDatabase;
    String userID;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public UserpasswordFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UserpasswordFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UserpasswordFragment newInstance(String param1, String param2) {
        UserpasswordFragment fragment = new UserpasswordFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_userpassword, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fAuth = FirebaseAuth.getInstance();
        fDatabase = FirebaseDatabase.getInstance();

        txtEmail = view.findViewById(R.id.txtEmail);
        txtPassword = view.findViewById(R.id.txtPass);
        txtConfirmPassword = view.findViewById(R.id.txtCfPass);
        btnRegister = view.findViewById(R.id.btnRegister);

//        String address = getArguments().getString("address");
//        String firstname = getArguments().getString("firstname");
//        String lastname = getArguments().getString("lastname");
//        double latitude = getArguments().getDouble("latitude");
//        double longitude = getArguments().getDouble("longitude");
//        String mobile = getArguments().getString("mobile");
//        String email = txtEmail.getText().toString();
//        String password = txtPassword.getText().toString();


        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Firebase
                String address = getArguments().getString("address");
                String firstname = getArguments().getString("firstname");
                String lastname = getArguments().getString("lastname");
                double latitude = getArguments().getDouble("latitude");
                double longitude = getArguments().getDouble("longitude");
                String mobile = getArguments().getString("mobile");
                String email = txtEmail.getText().toString();
                String password = txtPassword.getText().toString();
                if (!isValid(email)) {
                    txtEmail.setError("Invalid Email Address");
                    return;
                }
                if (txtPassword.getText().toString().isEmpty()) {
                    txtPassword.setError("Password required");
                    return;
                } else if (txtPassword.getText().toString().length() < 6) {
                    txtPassword.setError("Minimum 6 number");
                    return;
                }
                if (txtConfirmPassword.getText().toString().isEmpty()) {
                    txtConfirmPassword.setError("Password required");
                }
                if (!txtPassword.getText().toString().equals(txtConfirmPassword.getText().toString())) {

                    txtPassword.setError("Password and confirm password does not match");
                    txtConfirmPassword.setText("");
                    return;
                }


//        fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//            @Override
//            public void onComplete(@NonNull Task<AuthResult> task) {
//                if (task.isComplete()){
//                    userID.fAuth.getCurrentUser().getUid;
//                    DatabaseReference databaseReference= fDatabase.getReference();
                // đẩy user lên firebase
                fAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(getContext(), "Đăng kí thành công", Toast.LENGTH_LONG).show();
                                    userID = fAuth.getCurrentUser().getUid();
                                    DatabaseReference databaseReference = fDatabase.getReference();

                                    Map<String, Object> user = new HashMap<>();
                                    user.put("firstname", firstname);
                                    user.put("lastname", lastname);
                                    user.put("address", address);
                                    user.put("email", email);
                                    user.put("mobile", mobile);
                                    user.put("password", password);
                                    user.put("latitude", latitude);
                                    user.put("longitude", longitude);
                                    databaseReference.child("users").child(userID)
                                            .setValue(user)
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> aVoid) {
                                                    if (aVoid.isSuccessful()) {
                                                        Toast.makeText(getContext(), "OKE", Toast.LENGTH_SHORT).show();
                                                        Intent intent = new Intent(getContext(), LoginActivity.class);
                                                        intent.putExtra("email", email);
                                                        getActivity().setResult(Activity.RESULT_OK, intent);
                                                        getActivity().finish();
                                                    }
                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();

                                        }
                                    });
                                }
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                            }
                        });
            }
        });
    }


    public static boolean isValid(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." +
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";
        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }
}
