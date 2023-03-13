package com.example.magicnumbers;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;

public class DetailWindowNumber extends AppCompatActivity {
    TextToSpeech tts;
    String text = "";
    ListView listViewTablet;

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        super.onBackPressed();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tts = new TextToSpeech(DetailWindowNumber.this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                tts.setLanguage(Locale.CHINESE);
            }
        });

        DisplayMetrics metrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(metrics);

        float yInches= metrics.heightPixels/metrics.ydpi;
        float xInches= metrics.widthPixels/metrics.xdpi;
        double diagonalInches = Math.sqrt(xInches*xInches + yInches*yInches);
        if (diagonalInches>=3.5 && getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE ){
            setContentView(R.layout.activity_detail_window_tablets);
            TextView tvName = findViewById(R.id.textViewNameTablet);
            TextView tvText = findViewById(R.id.textViewTextTablet);
            ImageView imageView = findViewById(R.id.imageViewImage);
            listViewTablet = findViewById(R.id.listViewTablet);
            landscapeTablets(tvName, tvText, imageView);



        }else{
            setContentView(R.layout.activity_detail_window_number);
            TextView tvName = findViewById(R.id.textViewName);
            TextView tvText = findViewById(R.id.textViewText);
            ImageView imageView = findViewById(R.id.imageViewImage);
            Button button_speak = findViewById(R.id.button_to_speak);
            portraitMobileAndTablets(tvName, tvText, imageView, button_speak);
        }
    }

    private void landscapeTablets(TextView tvName, TextView tvText, ImageView imageView) {
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        if (name != null){
            System.out.println("z intenta name = " + name);
            MainActivity.extracted(listViewTablet);
        }
        else {
            Toast.makeText(this, "Something went wrong :-/", Toast.LENGTH_SHORT).show();;
        }

    }

    private void portraitMobileAndTablets(TextView tvName, TextView tvText, ImageView imageView, Button button_speak) {
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String basicUrlWithSingleObject = "https://dev.tapptic.com/test/json.php?name=" + name;
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, basicUrlWithSingleObject,
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    text = response.getString("text");
                    tvName.setText(response.getString("name"));
                    tvText.setText(text);
                    Picasso.with(DetailWindowNumber.this).load(response.getString("image")).into(imageView);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                System.out.println();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(DetailWindowNumber.this, "Check youre internet connection", Toast.LENGTH_LONG).show();
            }
        }
        );
        queue.add(jsonObjectRequest);


        button_speak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, "");
            }
        });
    }
}