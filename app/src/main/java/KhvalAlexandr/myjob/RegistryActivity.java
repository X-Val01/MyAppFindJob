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

import KhvalAlexandr.myjob.UsersInfo.User;

public class RegistryActivity extends AppCompatActivity {
private Button buttonZareg;
private EditText editTextEmail,editTextPsw,editTextPsw2;
    private FirebaseAuth mAuth;
    private FirebaseDatabase db;
    private DatabaseReference users;
    RelativeLayout regWindow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registry);
        buttonZareg = (Button)findViewById(R.id.buttonZareg);
        editTextEmail = (EditText)findViewById(R.id.editTextInputEmail);
        editTextPsw = (EditText)findViewById(R.id.editTextInputPsw);
        editTextPsw2 = (EditText)findViewById(R.id.editTextInputPsw2);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        users = db.getReference("Users");
        regWindow = findViewById(R.id.layoutRegistr);

        buttonZareg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            inputInfoAboutUsers();
            }
        });
    }
private void inputInfoAboutUsers(){
     if(TextUtils.isEmpty(editTextEmail.getText().toString())){
         Snackbar.make(regWindow,"Введите вашу почту", Snackbar.LENGTH_SHORT).show();
         return;
     }
    if(editTextPsw.getText().length()<6){
        Snackbar.make(regWindow,"Введите пароль не менее 6 символов", Snackbar.LENGTH_SHORT).show();
        return;
    }
    if(!editTextPsw.getText().toString().equals(editTextPsw2.getText().toString())){
        Snackbar.make(regWindow,"Пароли не совпадают", Snackbar.LENGTH_SHORT).show();
        return;
    }
    // Registry user
    mAuth.createUserWithEmailAndPassword(editTextEmail.getText().toString(),
            editTextPsw.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
        @Override
        public void onSuccess(AuthResult authResult) {
            User user = new User();
            user.setEmail(editTextEmail.getText().toString());
            user.setPsw(editTextPsw.getText().toString());

            users.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).
                    setValue(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Intent registrIntent = new Intent(RegistryActivity.this,MainActivity.class);
                    startActivity(registrIntent);
                    finish();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Snackbar.make(regWindow,"Ошибка регистрации", Snackbar.LENGTH_SHORT).show();
                    return;
                }
            });
        }
    });

    }


}