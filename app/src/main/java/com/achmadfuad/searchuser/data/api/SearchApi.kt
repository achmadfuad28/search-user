package com.achmadfuad.searchuser.data.api

import com.achmadfuad.searchuser.data.dto.UsersApiDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchApi {

    @GET("search/users")
    fun searchUsers(@Query("q") q: String?,
                    @Query("page") page: Int): Call<UsersApiDto>

}