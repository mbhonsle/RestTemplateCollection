package com.mbhonsle.batch_resttemplate_sample;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.mbhonsle.batchresttemplate.RestTemplateCollection;

import org.springframework.http.converter.StringHttpMessageConverter;

import java.util.List;
import java.util.concurrent.ExecutionException;
//import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

public class MainActivity extends AppCompatActivity {

    private Button fetch;
    private static RestTemplateCollection restTemplateCollection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        addOnClickListener();
        initService();
    }

    private void initService() {
        restTemplateCollection = new RestTemplateCollection();
        restTemplateCollection.addAll(Data.getList());
        restTemplateCollection.getMessageConverters().add(new StringHttpMessageConverter());
    }

    private void addOnClickListener() {
        this.fetch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    List<String> results = new DownloadTask().execute().get();
                } catch (Exception e) {
                    Log.e("Error during download", e.getMessage(), e);
                }
            }
        });
    }

    private void initViews() {
        this.fetch = findViewById(R.id.fetch);
    }

    static class DownloadTask extends AsyncTask<Void, Void, List<String>> {

        @Override
        protected List<String> doInBackground(Void... voids) {
            return restTemplateCollection.getAll(String.class);
        }

        @Override
        protected void onPostExecute(List<String> l) {
            for (String s : l) {
                Log.i("Result", s);
            }
        }
    }

}
