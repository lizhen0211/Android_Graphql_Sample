package com.lz.android_graphql_sample;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.ApolloClient;
import com.apollographql.apollo.exception.ApolloException;
import com.github.UserQuery;


import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onLoginClick(View view) {
        this.onLogin();
    }

    public void onLogin() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request()
                        .newBuilder()
                        .header("Authorization", "Bearer ad9e135eb9fe4f41b8e0d107e343cdd9bcb20596")
                        .build();
                return chain.proceed(request);
            }
        }).build();

        ApolloClient apolloClient = ApolloClient.builder()
                .okHttpClient(okHttpClient)
                .serverUrl("https://api.github.com/graphql")
                .build();

        apolloClient.query(UserQuery.builder().login("lizhen02111").build()).enqueue(new ApolloCall.Callback<UserQuery.Data>() {
            @Override
            public void onResponse(@NotNull com.apollographql.apollo.api.Response<UserQuery.Data> response) {
                Log.e(MainActivity.class.getSimpleName(), String.valueOf(response.getData()));
            }

            @Override
            public void onFailure(@NotNull ApolloException e) {

            }
        });
    }
}