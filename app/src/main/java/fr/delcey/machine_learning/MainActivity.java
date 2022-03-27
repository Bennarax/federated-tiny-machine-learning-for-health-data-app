package fr.delcey.machine_learning;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Button to_predict_menu, to_complete_dataset_menu ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        to_predict_menu = (Button)findViewById(R.id.button_main_to_predict_menu);
        to_complete_dataset_menu = (Button)findViewById(R.id.button_main_to_complete_dataset_menu);


        to_predict_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AngiographicPredictActivity.class);
                startActivity(intent);
            }
        });

        to_complete_dataset_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AnaemiaPredictActivity.class);
                startActivity(intent);
            }
        });
    }
}
