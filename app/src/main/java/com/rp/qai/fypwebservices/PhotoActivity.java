package com.rp.qai.fypwebservices;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.jsibbold.zoomage.ZoomageView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

public class PhotoActivity extends AppCompatActivity {
    ZoomageView zw1, zw2, zw3, zw4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

        zw1 = findViewById(R.id.zw1);
        zw2 = findViewById(R.id.zw2);
        zw3 = findViewById(R.id.zw3);
        zw4 = findViewById(R.id.zw4);

        Intent i = getIntent();
        int id = i.getIntExtra("poem_id", 1);

        //call getMenuCategories web service so that we can display list of Categories
        HttpRequest request = new HttpRequest
                ("https://literarytouristsingapore.000webhostapp.com/getPoemById.php?poem_id=" + id);
        request.setOnHttpResponseListener(mHttpResponseListener);
        request.setMethod("GET");
        request.execute();


    }
    private HttpRequest.OnHttpResponseListener mHttpResponseListener =
            new HttpRequest.OnHttpResponseListener() {
                @Override
                public void onResponse(String response){
                    // process response here
                    try {

                        Log.i("JSON Results", response);
                        JSONObject jsonObj = new JSONObject(response);
                        JSONArray picArr = jsonObj.getJSONArray("poem_images");
                        for (int i=0; i<picArr.length(); i++){
                            JSONObject picObj = picArr.getJSONObject(i);
                            String url = picObj.getString("photo_url");

//                            int poemId = jsonObj.getInt("poem_id");
//                            String title = jsonObj.getString("title");

//                            Poem poem = new Poem(poemId, title);
//                            alPoem.add(poem);
                            Log.i("Result", url);
                            if (i == 0){
                                Picasso.with(getApplicationContext()).load(url).into(zw1);
                            }
                            else if (i == 1){
                                Picasso.with(getApplicationContext()).load(url).into(zw2);
                            }
                            else if (i == 2){
                                Picasso.with(getApplicationContext()).load(url).into(zw3);
                            }
                            else if (i == 3){
                                Picasso.with(getApplicationContext()).load(url).into(zw4);
                            }


                        }
//                        aaPoem.notifyDataSetChanged();

                    } catch(Exception e){
                        e.printStackTrace();
                    }


                }
            };
}
