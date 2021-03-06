package com.example.new_covid_tracker_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.new_covid_tracker_app.Api.apicontroller;
import com.example.new_covid_tracker_app.Models.countrydata;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    TextView active,confirm,death,test,recover;
    TextView todayrecover,todayconfirm,todaydeath,date,countrydata;
    private List<countrydata> list;
    String Country ="India";
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        list= new ArrayList<>();
        init();

        if(getIntent().getStringExtra("country") !=null)
        {
            Country = getIntent().getStringExtra("country");
            countrydata.setText(Country);
        }
        else
        {
            countrydata.setText(Country);
        }

        countrydata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,Country.class);
                intent.putExtra("India",countrydata.getText().toString());
                intent.putExtra("totalcases",confirm.getText().toString());
                startActivity(intent);
            }
        });
        apicontroller.getApiset().getCountryData().enqueue(new Callback<List<countrydata>>() {
            @Override
            public void onResponse(Call<List<countrydata>> call, Response<List<countrydata>> response) {
                list.addAll(response.body());
                for (int i=0 ;i<list.size();i++)
                {
                    if(list.get(i).getCountry().equals(Country))
                    {
                        int totalconfirm = Integer.parseInt(list.get(i).getCases());
                        int totaldeath = Integer.parseInt(list.get(i).getDeaths());
                        int totalrecover = Integer.parseInt(list.get(i).getRecovered());
                        int totalactive = Integer.parseInt(list.get(i).getActive());
                        int totaltest = Integer.parseInt(list.get(i).getTests());
                        int totaltodayconfirm = Integer.parseInt(list.get(i).getTodayCases());
                        int totaltodaydeath = Integer.parseInt(list.get(i).getTodayDeaths());
                        int totaltodayrecover = Integer.parseInt(list.get(i).getTodayRecovered());
                        settext (list.get(i).getUpdated());

                        active.setText(NumberFormat.getInstance().format(totalactive));
                        confirm.setText(NumberFormat.getInstance().format(totalconfirm));
                        death.setText(NumberFormat.getInstance().format(totaldeath));
                        recover.setText(NumberFormat.getInstance().format(totalrecover));
                        test.setText(NumberFormat.getInstance().format(totaltest));
                        todayconfirm.setText(NumberFormat.getInstance().format(totaltodayconfirm));
                        todaydeath.setText(NumberFormat.getInstance().format(totaltodaydeath));
                        todayrecover.setText(NumberFormat.getInstance().format(totaltodayrecover));
                        progressBar.setVisibility(View.GONE);


                    }
                }

                
            }

            @Override
            public void onFailure(Call<List<countrydata>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error :" +t.getMessage(), Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);

            }
        });



    }

    private void settext(String updated) {
        DateFormat format = new SimpleDateFormat("MMM dd,yyyy");
        long millisecond = Long.parseLong(updated);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millisecond);
        date.setText("Updated at "+format.format(calendar.getTime()));


    }

    public void init()
    {
        active = findViewById(R.id.active);
        confirm = findViewById(R.id.confirm);
        death = findViewById(R.id.death);
        recover = findViewById(R.id.recover);
        test = findViewById(R.id.test);
        todayconfirm = findViewById(R.id.dailyconfirm);
        todaydeath = findViewById(R.id.dailydeath);
        todayrecover = findViewById(R.id.dailyrecover);
        date = findViewById(R.id.date);
        countrydata = findViewById(R.id.country);
        progressBar = findViewById(R.id.progressBar);

        progressBar.setVisibility(View.VISIBLE);


    }
}