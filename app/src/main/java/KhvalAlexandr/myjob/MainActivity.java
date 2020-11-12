package KhvalAlexandr.myjob;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rey.material.widget.CheckBox;

import io.paperdb.Paper;


public class MainActivity extends AppCompatActivity {

    private Button buttonLogIn;
    private Button buttonRegistr;
    private EditText editTextLogin;
    private EditText editTextPsw;
    private FirebaseAuth mAuth;
    private FirebaseDatabase db;
    private DatabaseReference users;
    RelativeLayout mainMenuWindow;
    private CheckBox checkBox;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonLogIn = (Button)findViewById(R.id.buttonLogIn);
        buttonRegistr = (Button)findViewById(R.id.buttonRegistr);
        editTextLogin = (EditText)findViewById(R.id.editTextLogin);
        editTextPsw = (EditText)findViewById(R.id.editTextPsw);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        users = db.getReference("Users");
        mainMenuWindow = findViewById(R.id.mainMenu);
        checkBox = (CheckBox)findViewById(R.id.checkboxLogIn);
        Paper.init(this);
//      Login in app
        buttonLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    auntification();
            }
        });
        String login = Paper.book().read("login");
        String psw = Paper.book().read("psw");
        if(login != "" && psw != ""){
            if(!TextUtils.isEmpty(login) && !TextUtils.isEmpty(psw)){
                ValidateUser(login,psw);

            }

        }
        buttonRegistr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registrIntent = new Intent(MainActivity.this,RegistryActivity.class);
                startActivity(registrIntent);
                finish();
            }
        });
    }

    private void ValidateUser(String login, String psw) {
        mAuth.signInWithEmailAndPassword(login, psw).
                addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {

                        startActivity(new Intent(MainActivity.this,bordBulletin.class));
                        finish();

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Snackbar.make(mainMenuWindow,"Неверный логин или пароль", Snackbar.LENGTH_SHORT).show();
                return;
            }
        });

    }

    private void auntification(){
        if(TextUtils.isEmpty(editTextLogin.getText().toString())){
            Snackbar.make(mainMenuWindow,"Введите вашу почту", Snackbar.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(editTextPsw.getText().toString())){
            Snackbar.make(mainMenuWindow," Введите ваш пароль", Snackbar.LENGTH_SHORT).show();
            return;
        }


        // Auntification user
        mAuth.signInWithEmailAndPassword(editTextLogin.getText().toString(), editTextPsw.getText().toString()).
                addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Paper.book().write("key",FirebaseAuth.getInstance().getCurrentUser().getUid());
                        if(checkBox.isChecked()){
                            Paper.book().write("login", editTextLogin.getText().toString());
                            Paper.book().write("psw",editTextPsw.getText().toString());


                        }
                        startActivity(new Intent(MainActivity.this,bordBulletin.class));
                        finish();

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Snackbar.make(mainMenuWindow,"Неверный логин или пароль", Snackbar.LENGTH_SHORT).show();
                return;
            }
        });

    }

}