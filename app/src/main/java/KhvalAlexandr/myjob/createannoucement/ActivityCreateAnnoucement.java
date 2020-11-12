package KhvalAlexandr.myjob.createannoucement;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.FileNotFoundException;
import java.io.InputStream;

import KhvalAlexandr.myjob.R;
import KhvalAlexandr.myjob.bordBulletin;
import io.paperdb.Paper;

public class ActivityCreateAnnoucement extends AppCompatActivity {

    protected  String[] arrayCategory = {"Категории","Отделочные работы", "Монтажные работы","Столярные работы",
            "Кладочные работы","Сантехнические работы","Электромонтажные работы", "Однодневные подработки","Прочие работы"};
    private ImageButton imageButtonBack, imageButtonAddImage;
    private Button buttonAddAnnoucement;
    private ImageView image1;
    private final int Pick_image = 1;
    private FirebaseDatabase db;
    private DatabaseReference db_Annoucement;
    private EditText editText_description,editText_nameAnnoucement;
    private RelativeLayout relativeLayout_createAnnoucement;
   

    private Uri imageUri;
    FirebaseStorage storage;
    StorageReference storageReference;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_annoucement);
        imageButtonBack = (ImageButton)findViewById(R.id.imageButton_Back);
        imageButtonAddImage = (ImageButton)findViewById(R.id.imageButton_AddImages);
        buttonAddAnnoucement = (Button)findViewById(R.id.buttonAddAnnoucement);
        image1 = (ImageView)findViewById(R.id.image1);
        editText_description = (EditText) findViewById(R.id.editText_description);
        editText_nameAnnoucement = (EditText) findViewById(R.id.editText_nameAnnoucement);
        relativeLayout_createAnnoucement = (RelativeLayout)findViewById(R.id.relativeLayout_createAnnoucement);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        Paper.init(this);
        db = FirebaseDatabase.getInstance();
        db_Annoucement = db.getReference("Annoucement");




        imageButtonAddImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_selectPhoto = new Intent(Intent.ACTION_PICK);
                intent_selectPhoto.setType("image/*");
                startActivityForResult(intent_selectPhoto, Pick_image);

            }
        });


         Spinner spinner = (Spinner) findViewById(R.id.spinner);
            ArrayAdapter<String> defaultaAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,arrayCategory );
            defaultaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(defaultaAdapter);
            spinner.setPrompt("Категории");

 //           back in bord menu
            imageButtonBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(ActivityCreateAnnoucement.this, bordBulletin.class));
                    finish();
                }
            });
            buttonAddAnnoucement.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    uploadImage();
                     if(editText_nameAnnoucement.getText().toString().length()<=0) {
                        Snackbar.make(relativeLayout_createAnnoucement,"Введите наименование работы", Snackbar.LENGTH_SHORT).show();
                        return;
                    }
                    else if(spinner.getSelectedItemId()==0){
                        Snackbar.make(relativeLayout_createAnnoucement,"Выберите категорию", Snackbar.LENGTH_SHORT).show();
                         return;
                    }

                    else if(editText_description.getText().toString().length()<=0) {
                        Snackbar.make(relativeLayout_createAnnoucement,"Введите описание", Snackbar.LENGTH_SHORT).show();
                         return;
                    }
                    NewAnnoucement newAnnoucement = new NewAnnoucement();
                    newAnnoucement.setDescriptionAnnoucement(editText_description.getText().toString());
                    newAnnoucement.setCategoryAnnoucement(spinner.getSelectedItem().toString());
                    newAnnoucement.setNameAnnoucement(editText_nameAnnoucement.getText().toString());

                    db_Annoucement.child(Paper.book().read("key")+editText_nameAnnoucement.getText().toString()).setValue(newAnnoucement).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Intent registrIntent = new Intent(ActivityCreateAnnoucement.this, bordBulletin.class);
                            startActivity(registrIntent);
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Snackbar.make(relativeLayout_createAnnoucement,"Error", Snackbar.LENGTH_SHORT).show();
                        }
                    });





                }
            });







        }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        switch(requestCode) {
            case Pick_image:
                if(resultCode == RESULT_OK){
                    try {

                        //Получаем URI изображения, преобразуем его в Bitmap
                        //объект и отображаем в элементе ImageView нашего интерфейса:
                        imageUri = imageReturnedIntent.getData();
                        final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                        final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                            if(image1.getDrawable() == null) {
                                image1.setImageBitmap(selectedImage);
                                return;
                            }


                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
        }}
    private void uploadImage() {

        if(imageUri != null)
        {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            StorageReference ref = storageReference.child("images/"+ Paper.book().read("key")+
                    editText_nameAnnoucement.getText().toString());
            ref.putFile(imageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            progressDialog.dismiss();
                            Toast.makeText(ActivityCreateAnnoucement.this, "Uploaded", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(ActivityCreateAnnoucement.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded "+(int)progress+"%");
                        }
                    });
        }
    }
    }

