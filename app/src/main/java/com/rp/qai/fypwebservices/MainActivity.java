package com.rp.qai.fypwebservices;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView lvPoem;
    private ArrayList<Poem> alPoem;
    private PoemAdapter aaPoem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvPoem = findViewById(R.id.lvPoem);
        alPoem = new ArrayList<>();
        aaPoem = new PoemAdapter(this, R.layout.row, alPoem);
        lvPoem.setAdapter(aaPoem);

        //call getMenuCategories web service so that we can display list of Categories
        HttpRequest request = new HttpRequest
                ("https://literarytouristsingapore.000webhostapp.com/getPoem.php");
        request.setOnHttpResponseListener(mHttpResponseListener);
        request.setMethod("GET");
        request.execute();

        lvPoem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Poem po = alPoem.get(position);
                Intent intent = new Intent(MainActivity.this, PhotoActivity.class);
                intent.putExtra("Poem", po);
                startActivityForResult(intent, 1);
            }
        });
        DBHelper dbh = new DBHelper(MainActivity.this);
        alPoem.clear();
        alPoem.addAll(dbh.getAllPoems());
        dbh.close();
        aaPoem.notifyDataSetChanged();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == 1){
            DBHelper dbh = new DBHelper(MainActivity.this);
            alPoem.clear();
            alPoem.addAll(dbh.getAllPoems());
            dbh.close();
            aaPoem.notifyDataSetChanged();

        }

    }
    private HttpRequest.OnHttpResponseListener mHttpResponseListener =
            new HttpRequest.OnHttpResponseListener() {
                @Override
                public void onResponse(String response){
                    // process response here
                    try {

                        Log.i("JSON Results", response);
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i=0; i<jsonArray.length(); i++){
                            JSONObject jsonObj = jsonArray.getJSONObject(i);

                            int poemId = jsonObj.getInt("poem_id");
                            String title = jsonObj.getString("title");

                            Poem poem = new Poem(poemId, title);
                            alPoem.add(poem);
                        }
                        aaPoem.notifyDataSetChanged();

                    } catch(Exception e){
                        e.printStackTrace();
                    }


                }
            };
}
