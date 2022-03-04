package com.example.new_covid_tracker_app.Api;

import com.example.new_covid_tracker_app.Models.countrydata;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface apiset {
    static  final String BASEURl ="https://corona.lmao.ninja/v2/";

    @GET("countries")
    Call<List<countrydata>> getCountryData();
}
