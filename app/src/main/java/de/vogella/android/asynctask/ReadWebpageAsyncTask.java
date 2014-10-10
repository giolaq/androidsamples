package de.vogella.android.asynctask;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import de.vogella.android.asyntask.R;

public class ReadWebpageAsyncTask extends Activity {
    private TextView textView;
    private EditText placeEdTxt;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        textView = (TextView) findViewById(R.id.TextView01);
        placeEdTxt = (EditText) findViewById(R.id.editText);
    }

    private class DownloadWebPageTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            String response = "";
            for (String url : urls) {
                DefaultHttpClient client = new DefaultHttpClient();
                HttpGet httpGet = new HttpGet(url);

                HttpPost httpPost = new HttpPost(url);


                List<NameValuePair> myArgs = new ArrayList<NameValuePair>();


                String parameter =
                        placeEdTxt.getText().toString();


                myArgs.add(new BasicNameValuePair("nome", parameter));


                try {
                    httpPost.setEntity(new UrlEncodedFormEntity(myArgs));

                    HttpResponse execute = client.execute(httpPost);
                    InputStream content = execute.getEntity().getContent();

                    BufferedReader buffer = new BufferedReader(
                            new InputStreamReader(content));
                    String s = "";
                    while ((s = buffer.readLine()) != null) {
                        response += s;
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return response;
        }

        @Override
        protected void onPostExecute(String result) {

            Gson gson = new Gson();

            //Attivita attivita = gson.fromJson(result, Attivita.class);


            StringBuilder sBuil = new StringBuilder();
            sBuil.append("Attività\n");


            Type collectionType = new TypeToken<Collection<Attivita>>(){}.getType();
            Collection<Attivita> attivitalist = gson.fromJson(result, collectionType);
                for (Attivita a : attivitalist) {
                      sBuil.append(a+"\n");
            }


            textView.setText(sBuil.toString());
        }
    }

    public void onClick(View view) {
        DownloadWebPageTask task = new DownloadWebPageTask();
        EditText placeEdTxt = (EditText) findViewById(R.id.editText);
        String url = "http://api.openweathermap.org/data/2.5/weather?q=" +
                placeEdTxt.getText().toString() +
                ",it";

        String urlAttivita = "http://192.168.6.20/attivita/attivita/jsoncategorie";

        task.execute(urlAttivita);

    }
}

