package com.mukeshteckwani.astro.astroapp.webhelper;


import com.mukeshteckwani.astro.astroapp.BuildConfig;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by mukeshteckwani on 17/12/17.
 */

public class RetrofitService {

    private static AstroAPi astroApi;

    private RetrofitService(){}

    public static AstroAPi getApiService() {
        if (astroApi == null) {
            Retrofit.Builder builder =
                    new Retrofit.Builder()
                            .addConverterFactory(GsonConverterFactory.create())
                            .baseUrl(BuildConfig.URL);

            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient client =
                    new OkHttpClient.Builder()
                            .addInterceptor(new Interceptor() {
                                @Override
                                public Response intercept(Interceptor.Chain chain) throws IOException {
                                    return chain.proceed(addPeHeaders(chain.request().newBuilder()).build());
                                }
                            })
                            .addInterceptor(httpLoggingInterceptor)
                            .build();

            builder.client(client);
            astroApi = builder.build().create(AstroAPi.class);
        }
        return astroApi;
    }

    private static Request.Builder addPeHeaders(Request.Builder builder) {
        builder.addHeader("city_id", "10");
        return builder;
    }
}
