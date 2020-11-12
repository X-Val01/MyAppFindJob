package KhvalAlexandr.myjob;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import KhvalAlexandr.myjob.createannoucement.ActivityCreateAnnoucement;


public class bordBulletin extends AppCompatActivity {
private ImageButton buttonBoard, buttonFavorites, buttonAdd,buttonDialogs,buttonMain;
private ListView listView_bord;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bord_bulletin);
        buttonBoard = (ImageButton)findViewById(R.id.imageButtonBoard);
        buttonFavorites = (ImageButton)findViewById(R.id.imageButtonFavorites);
        buttonAdd = (ImageButton)findViewById(R.id.imageButtonAdd);
        buttonDialogs = (ImageButton)findViewById(R.id.imageButtonDialogs);
        buttonMain = (ImageButton)findViewById(R.id.imageButtonMain);
        listView_bord = (ListView) findViewById(R.id.listView_bord);


        final String[] catNames = new String[] {
                "Рыжик", "Барсик", "Мурзик", "Мурка", "Васька",
                "Томасина", "Кристина", "Пушок", "Дымка", "Кузя",
                "Китти", "Масяня", "Симба","ff","f","fg"
        };

// используем адаптер данных
        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this, R.layout.custom_list_item, catNames);

        listView_bord.setAdapter(adapter);

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(bordBulletin.this, ActivityCreateAnnoucement.class));
                finish();



            }
        });

    }


}