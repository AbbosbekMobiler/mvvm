package mobiler.abbosbek.mvvm.api

import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.google.gson.Gson
import mobiler.abbosbek.mvvm.MyApp
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkManager {
    var api : Api ?= null

    fun getRetrofit() : Retrofit{
        return Retrofit.Builder()
            .baseUrl("http://osonsavdo.herokuapp.com/api/")
            .client(getOkHttpClient())
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .build()
    }
    fun getOkHttpClient() : OkHttpClient{
        return OkHttpClient.Builder()
            .addInterceptor(ChuckerInterceptor.Builder(MyApp.app)
                .collector(ChuckerCollector(MyApp.app))
                .maxContentLength(250000L)
                .redactHeaders(emptySet())
                .alwaysReadResponseBody(false)
                .build())
            .build()
    }

    fun getApiInstance() : Api{
        if (api == null){
            api = getRetrofit().create(Api::class.java)
        }
        return api!!
    }
}