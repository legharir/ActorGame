package com.actorgame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ConnectionActivity extends AppCompatActivity {

    private final String INFO_ENDPOINT = "https://api.themoviedb.org/3/search/person?api_key=cce092a36f7507dc701d800643e920b5&query="; //wrong api key
    private final String IMAGE_ENDPOINT = "https://image.tmdb.org/t/p/w200";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connection);

        // read actor names, and hash them to sequential integer values
        Map<String, Integer> map = new HashMap<>();
        Map<Integer, String> reverseMap = new HashMap<>();
        Graph G = null;


        // initialize graph
        Scanner in = new Scanner(getResources().openRawResource(R.raw.moviedata));
        G = new Graph(Integer.parseInt(in.nextLine()));

        // populate graph
        while (in.hasNextLine()) {
            String line = in.nextLine();

            String movie = line.split(",")[1];
            String[] fields = line.split("\"");
            String[] actors = fields[fields.length - 2].split(",");

            // put all the actors starring in the movie in the map
            for (String actor : actors) {
                if (!map.containsKey(actor.trim())) {
                    map.put(actor.trim(), map.size());
                }
                reverseMap.put(map.get(actor.trim()), actor.trim());
            }

            // create an edge in graph between every pair of actors starring in the same movie
            for (int i = 0; i < actors.length; i++) {
                for (int j = i + 1; j < actors.length; j++) {
                    G.addEdge(map.get(actors[i].trim()), map.get(actors[j].trim()), movie);
                }
            }
        }

        // find the shortest path between the two actors using breadth first search
        String startActor = getIntent().getStringExtra("end");
        String endActor = getIntent().getStringExtra("start");
        BFS bfs = new BFS(G, map.get(startActor));

        // store the path
        ArrayList<String> path = new ArrayList<>();
        for (Connection<Integer, Integer, String> c : bfs.pathTo(map.get(endActor))) {
            Connection<String, String, String> connection = new Connection<>(reverseMap.get(c.first), reverseMap.get(c.second), c.connection);
            String firstActor = connection.first.replaceAll("\\s+", "+");
            String secondActor = connection.second.replaceAll("\\s+", "+");
            loadImage(INFO_ENDPOINT + firstActor);
            loadImage(INFO_ENDPOINT + secondActor);
            path.add(connection.toString());
        }

        // add the paths to the ListView
        /*ListView pathList = (ListView) findViewById(R.id.pathList);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                path
        );
        pathList.setAdapter(adapter);
        */
    }

    private void loadImage(String url) {
        Ion.with(this)
                .load(url)
                .asString()
                .setCallback(new FutureCallback<String>() {
                    @Override
                    public void onCompleted(Exception e, String result) {
                        try {
                            JSONObject json = new JSONObject(result);
                            JSONArray results = json.getJSONArray("results");
                            JSONObject actorInfo = results.getJSONObject(0);
                            loadImageFromUrl(IMAGE_ENDPOINT + actorInfo.getString("profile_path"));
                        } catch (JSONException jsonE) {
                            jsonE.printStackTrace();
                        }
                    }
                });
    }

    private void loadImageFromUrl(String imageUrl) {
        System.out.println(imageUrl);
        ImageView imageView = new ImageView(this);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        imageView.setLayoutParams(params);
        GridLayout ll = (GridLayout) findViewById(R.id.gridLayout);
        ll.addView(imageView);

        Picasso.with(this)
                .load(imageUrl)
                .into(imageView);
    }
}
