package jp.ac.it_college.std.s23013.kotlinapikadai2

import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("spells")
    fun getSpells(): Call<List<Spell>>
}
