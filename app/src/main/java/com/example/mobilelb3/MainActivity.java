package com.example.mobilelb3;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ImageView imageView;
    List<String> imgList = new ArrayList<>();
    Button prevButton, nextButton, addButton;
    EditText urlTxt;

    DatabaseHelper dataHelper;
    ArrayList<String> id, url;


    int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dataHelper = new DatabaseHelper(MainActivity.this);
        Cursor cursor = dataHelper.readData();

        imageView = findViewById(R.id.imageView);
        nextButton = findViewById(R.id.nextBtn);
        prevButton = findViewById(R.id.prevBtn);
        addButton = findViewById(R.id.addBtn);
        urlTxt = findViewById(R.id.urlText);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i += 1;
                if(i == imgList.size()){
                    i = 0;
                    picassoShowImage(i);

                }else{
                    picassoShowImage(i);
                }
            }
        });

        prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i -=1;
                if(i < 0){
                    i = imgList.size() - 1;
                    picassoShowImage(i);
                }else{
                    picassoShowImage(i);
                }

            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getInputs();

                DatabaseHelper databaseHelper = new DatabaseHelper(MainActivity.this);

                boolean result = databaseHelper.addUrl(urlTxt.getText().toString());

                if(result) {
                    Toast.makeText(MainActivity.this, "Added Successfully", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(MainActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                }

                String url = urlTxt.getText().toString();
                if(!url.equals("")){

                    imgList.add(url);
                    i = imgList.size() - 1;
                    picassoShowImage(i);
                }else{
                    Toast.makeText(MainActivity.this, "Please insert URl to add", Toast.LENGTH_SHORT).show();
                }


            }
        });
        dataHelper = new DatabaseHelper(MainActivity.this);
        id = new ArrayList<>();
        url = new ArrayList<>();

        SaveDataInArray();
        picassoShowImage(0);
    }

    private void getInputs() {
        EditText urlTxt = findViewById(R.id.urlText);
        String url = urlTxt.getText().toString();
    }

    private void SaveDataInArray() {
        Cursor cursor = dataHelper.readData();
        if(cursor.getCount() == 0){
            Toast.makeText(this, "Nothing", Toast.LENGTH_SHORT).show();
        }else{
            while (cursor.moveToNext()){
                id.add(cursor.getString(0));
                url.add(cursor.getString(1));
            }
            imgList = url;

        }
    }

    private void picassoShowImage (int i){
        Picasso.get().load(imgList.get(i)).into(imageView);
    }
}