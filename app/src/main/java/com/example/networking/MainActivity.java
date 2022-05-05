package com.example.networking;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SuppressWarnings("FieldCanBeLocal")
public class MainActivity extends AppCompatActivity implements JsonTask.JsonTaskListener {

    private final String JSON_URL = "HTTPS_URL_TO_JSON_DATA_CHANGE_THIS_URL";
    private final String JSON_FILE = "mountains.json";

    RecyclerView recyclerView;
    ArrayList<Mountain> mountainArrayList;
    RecyclerViewAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new JsonFile(this, this).execute(JSON_FILE);

        RecyclerView view = findViewById(R.id.recycler_view);
        view.setLayoutManager(new LinearLayoutManager(this));
        view.setAdapter(adapter);

        ArrayList<Mountain> items = new ArrayList<>(Arrays.asList(
                new Mountain("Kinnekulle"),
                new Mountain("Matterhorn"),
                new Mountain("Mount Everest")
        ));

    }

    private class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>{

        public class MyViewHolder extends RecyclerView.ViewHolder {
            private TextView name;
            private TextView height;

            public MyViewHolder(@NonNull View view) {
                super(view);
                name = view.findViewById(R.id.title);
                height = view.findViewById(R.id.textView);
            }
        }

        @NonNull
        @Override
        public RecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return null;
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerViewAdapter.MyViewHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return mountainArrayList.size();
        }
    }

    @Override
    public void onPostExecute(String json){
        Log.d("MainActivity", json);
    }
}
