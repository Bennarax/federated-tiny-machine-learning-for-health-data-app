package fr.delcey.machine_learning;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class AngiographicPredictActivity extends AppCompatActivity {
    private TextView mTextViewResult;
    private EditText mCholEditText, mThalachEditText, mTrestbpsEditText;
    private Button mResultButton, mAngiographicToMain;

    private boolean chol_has_value = false;
    private boolean thalach_has_value = false;
    private boolean trestbps_has_value = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_predict_angiographic);

        mTextViewResult = findViewById(R.id.angiographic_textview_result);

        mCholEditText = findViewById(R.id.angiographic_edittext_chol);
        mThalachEditText = findViewById(R.id.angiographic_edittext_thalach);
        mTrestbpsEditText = findViewById(R.id.angiographic_edittext_trestbps);

        mResultButton = findViewById(R.id.angiographic_button_get_result);
        mResultButton.setEnabled(false);

        mAngiographicToMain = findViewById(R.id.button_angiographic_to_predict_menu);

        mCholEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                chol_has_value = !s.toString().isEmpty();
                mResultButton.setEnabled(chol_has_value && thalach_has_value & trestbps_has_value);
            }
        });

        mThalachEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                thalach_has_value = !s.toString().isEmpty();
                mResultButton.setEnabled(chol_has_value && thalach_has_value & trestbps_has_value);
            }
        });

        mTrestbpsEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                trestbps_has_value = !s.toString().isEmpty();
                mResultButton.setEnabled(chol_has_value && thalach_has_value & trestbps_has_value);
            }
        });

        mResultButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String chol = mCholEditText.getText().toString();
                String thalach = mThalachEditText.getText().toString();
                String trestbps = mTrestbpsEditText.getText().toString();
                get_predict(chol, thalach, trestbps);
            }

            public void get_predict(String chol, String thalach, String trestbps){
                HttpURLConnection conn = null;
                String output = "Error when getting prediction of angiographic !";

                try{
                    StrictMode.ThreadPolicy gfgPolicy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                    StrictMode.setThreadPolicy(gfgPolicy);

                    URL url = new URL("https://federated-ml-api.herokuapp.com:5000//predict");

                    String input_string = "angiographic" + ";" + chol + ";" + thalach + ";" + trestbps;
                    String[] inputData = {input_string};

                    for(String input: inputData){
                        byte[] postData = input.getBytes(StandardCharsets.UTF_8);
                        conn = (HttpURLConnection) url.openConnection();
                        conn.setDoOutput(true);
                        conn.setRequestMethod("POST");
                        conn.setRequestProperty("Content-Type", "application/json");
                        conn.setRequestProperty("charset", "utf-8");
                        conn.setRequestProperty("Content-Length", Integer.toString(input.length()));
                        DataOutputStream os = new DataOutputStream(conn.getOutputStream());
                        os.write(postData);
                        os.flush();

                        if (conn.getResponseCode() != 200) {
                            throw new RuntimeException("Failed : HTTP error code : "
                                    + conn.getResponseCode());
                        }

                        BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
                        output = br.readLine();

                        conn.disconnect();
                    }
                }

                catch (MalformedURLException e) {
                    e.printStackTrace();
                }

                catch (IOException e) {
                    e.printStackTrace();
                }

                finally {
                    if(conn != null) {
                        conn.disconnect();
                    }
                    mTextViewResult.setText(output);
                }
            }
        });

        mAngiographicToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AngiographicPredictActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
