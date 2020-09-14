package com.achmadfuad.searchuser.domain.usecase

interface UseCase<Params, Entity> {

    @Throws(Exception::class)
    fun execute(params: Params?): Entity
}