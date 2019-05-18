package com.e.retodeezer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    EditText txtPlaylistName;
    private List<Object> playlist;
    private ImageButton btnConfirmar;
    private HTTPSWebUtilDomi httpsWebUtilDomi;
    public final String URL = "https://api.deezer.com/version/service/id/method/?parameters";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        httpsWebUtilDomi = new HTTPSWebUtilDomi();
        btnConfirmar = findViewById(R.id.searchButton);
        txtPlaylistName = findViewById(R.id.txtPlaylist);
        btnConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String playlist = txtPlaylistName.getText().toString();
                try {
                    httpsWebUtilDomi.GETrequest(URL);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }

            }
        });


    }
}
