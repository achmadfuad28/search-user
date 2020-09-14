package com.achmadfuad.searchuser.framework.webapi

interface ServiceFactory {

    fun <T> createService(service: Class<T>): T
}