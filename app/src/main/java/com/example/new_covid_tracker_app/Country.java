package com.example.new_covid_tracker_app;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.new_covid_tracker_app.Adapter.adapter;
import com.example.new_covid_tracker_app.Api.apicontroller;
import com.example.new_covid_tracker_app.Models.countrydata;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Country extends AppCompatActivity {
    private RecyclerView recyclerView;
    TextView defalutcountry,defaultcountrycases;
    EditText search;
    adapter countryadapter;
    ArrayList<countrydata> data;
    ProgressBar progressBar2;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country);

        recyclerView = findViewById(R.id.recycker);
        search = findViewById(R.id.search);
        defalutcountry= findViewById(R.id.defaultcountry);
        defaultcountrycases = findViewById(R.id.defaultcountrycases);
        progressBar2 = findViewById(R.id.progressBar2);
        progressBar2.setVisibility(View.VISIBLE);
        data = new ArrayList<>();

        String countryname = getIntent().getStringExtra("India");
        String totalcases = getIntent().getStringExtra("totalcases");

        defalutcountry.setText(countryname);
        defaultcountrycases.setText(totalcases);
         countryadapter = new adapter(data,this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(countryadapter);
        apicontroller.getApiset().getCountryData().enqueue(new Callback<List<countrydata>>() {
            @Override
            public void onResponse(Call<List<countrydata>> call, Response<List<countrydata>> response) {
                data.addAll(response.body());
                countryadapter.notifyDataSetChanged();
                progressBar2.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<List<countrydata>> call, Throwable t) {
                progressBar2.setVisibility(View.GONE);
                Toast.makeText(Country.this, t.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filterdata(s.toString());

            }
        });
    }

    private void filterdata(String filterdata) {
        ArrayList<countrydata> filterlist = new ArrayList<>();
        for (countrydata item :data)
        {
            if(item.getCountry().toLowerCase().contains(filterdata.toLowerCase()))
            {
                filterlist.add(item);

            }
        }

        countryadapter.Filterlist(filterlist);

    }
}