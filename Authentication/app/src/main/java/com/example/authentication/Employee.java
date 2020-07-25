package com.example.authentication;

import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class Employee extends AppCompatActivity {
    private RequestQueue mRequestQueue;
    private RecyclerView recyclerView;
    private ArrayList<GsonData> mData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.employee);

        recyclerView = findViewById(R.id.recycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mData = new ArrayList<>();

        mRequestQueue = Volley.newRequestQueue(this);
        getData();
    }


    public void getData() {
        StringRequest stringRequest = new StringRequest("https://api.github.com/users?per_page=10&since=11", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Gson gson = new Gson();


                Type ListType = new TypeToken<List<GsonData>>(){}.getType();
                List<GsonData> user = gson.fromJson(response,ListType);
                Adapter adapter = new Adapter(Employee.this,mData);
                recyclerView.setAdapter(adapter);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Employee.this, "didn't work", Toast.LENGTH_SHORT).show();
            }
        });
        mRequestQueue.add(stringRequest);
    }
}
