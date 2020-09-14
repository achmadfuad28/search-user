package com.achmadfuad.searchuser.domain.usecase

import kotlinx.coroutines.*

abstract class BaseUsecase<Params, Entity> : UseCase<Params, Entity> {

    suspend fun executeAsync(params: Params?): Entity {
        return GlobalScope.async(Dispatchers.Default) { execute(params) }.await()
    }
}