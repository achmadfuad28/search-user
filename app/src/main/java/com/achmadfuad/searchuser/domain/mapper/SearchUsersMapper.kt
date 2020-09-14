package com.achmadfuad.searchuser.domain.mapper

import com.achmadfuad.searchuser.data.dto.UsersApiDto
import com.achmadfuad.searchuser.domain.entities.SearchUsersResult
import com.achmadfuad.searchuser.domain.entities.UserItemResult
import com.achmadfuad.searchuser.framework.common.protocol.Mapper


class SearchUsersMapper : Mapper<UsersApiDto, SearchUsersResult> {

    override fun transform(from: UsersApiDto): SearchUsersResult {
        val repositoryList = arrayListOf<UserItemResult>()

        from.items?.let {
            for (item in it) {

                val repositoryResult = UserItemResult(item.id)
                    .apply {
                        name = item.login
                        avatar = item.avatar_url
                    }

                repositoryList.add(repositoryResult)
            }
        }

        return SearchUsersResult().apply {
            data = repositoryList
            lastPage = from.incompleteResult
            count = from.totalCount
        }
    }
}