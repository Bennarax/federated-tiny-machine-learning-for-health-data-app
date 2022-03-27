package fr.delcey.machine_learning;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class CompleteDatasetActivity extends AppCompatActivity {

    Button to_angiographic_activity, to_anaemia_activity, to_main_menu ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_dataset);

        to_angiographic_activity = (Button)findViewById(R.id.button_predict_menu_to_angiographic);
        to_anaemia_activity = (Button)findViewById(R.id.button_predict_menu_to_anaemia);
        to_main_menu = (Button)findViewById(R.id.button_predict_to_main_menu);


        to_angiographic_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(CompleteDatasetActivity.this, AngiographicCompleteDatasetActivity.class);
//                startActivity(intent);
            }
        });

        to_anaemia_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(CompleteDatasetActivity.this, AnaemiaCompleteDatasetActivity.class);
//                startActivity(intent);
            }
        });

        to_main_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CompleteDatasetActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}