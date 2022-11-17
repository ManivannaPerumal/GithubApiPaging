package assessment.narayanagroup.githubapisearch.data.api

import assessment.narayanagroup.githubapisearch.BuildConfig
import assessment.narayanagroup.githubapisearch.base.BaseRestClient
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object AppRestClient : BaseRestClient() {

    val wServer by lazy {
        getServer(ApiServices::class.java, BuildConfig.BASE_URL) }

    override fun handleConverterFactory(builder: Retrofit.Builder) {
        builder.addCallAdapterFactory(CoroutineCallAdapterFactory())
        builder.addConverterFactory(GsonConverterFactory.create())
    }
}