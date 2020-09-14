package com.achmadfuad.searchuser.data.di.module


import com.achmadfuad.searchuser.data.api.SearchApi
import com.achmadfuad.searchuser.data.common.SearchUserRepository
import com.achmadfuad.searchuser.data.repository.SearchUserRepositoryImpl
import com.achmadfuad.searchuser.domain.mapper.SearchUsersMapper
import com.achmadfuad.searchuser.domain.usecase.SearchUsersInteractor
import com.achmadfuad.searchuser.framework.webapi.ServiceFactory
import org.koin.dsl.module

val searchUsersRepositoryModule = module {
    single {
        get<ServiceFactory>().createService(SearchApi::class.java)
    }
    single { SearchUsersMapper() }
    single<SearchUserRepository> {
        SearchUserRepositoryImpl(
            service = get(),
            searchUsersMapper = get())
    }

    factory { SearchUsersInteractor(get()) }
}