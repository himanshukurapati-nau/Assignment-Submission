package com.himanshusampath.assignmentcreationfaculty;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.github.barteksc.pdfviewer.PDFView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class PDFViewerActivity extends AppCompatActivity
{

    String pdfUrl;
    PDFView pdfView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdfviewer);

        Log.d("Astro","Called 2");
        Intent intent = getIntent();
        pdfUrl=intent.getStringExtra("pdfurl");
        pdfView=findViewById(R.id.pdfView);

        new pdfDownload().execute(pdfUrl);
    }

    private class pdfDownload extends AsyncTask<String, Void, InputStream>
    {

        @Override
        protected InputStream doInBackground(String... strings)
        {
            InputStream inputStream = null;

            try
            {
                URL url=new URL(strings[0]);
                HttpURLConnection urlConnection= (HttpURLConnection) url.openConnection();

                if(urlConnection.getResponseCode()==200)
                {
                    inputStream=new BufferedInputStream(urlConnection.getInputStream());
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }

            return inputStream;
        }

        @Override
        protected void onPostExecute(InputStream inputStream)
        {
            super.onPostExecute(inputStream);
            pdfView.fromStream(inputStream).load();
        }
    }
}