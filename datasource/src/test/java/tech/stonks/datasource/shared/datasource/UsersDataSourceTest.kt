package tech.stonks.datasource.shared.datasource

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import tech.stonks.data.shared.datasource.UsersDataSource
import tech.stonks.data.shared.model.UserDataModel
import tech.stonks.datasource.shared.mapper.UserMapper
import tech.stonks.datasource.shared.model.api.UserApiModel
import tech.stonks.datasource.shared.service.UsersApiService

class UsersDataSourceTest {
    private lateinit var _usersApiService: UsersApiService
    private lateinit var _usersMapper: UserMapper
    private lateinit var _usersDataSource: UsersDataSource

    @Before
    fun setUp() {
        _usersApiService = mockk()
        _usersMapper = mockk()
        _usersDataSource = UsersDataSourceImpl(_usersApiService, _usersMapper)
    }

    @Test
    fun `getUsers should call getUsers from UsersApiService`() = runTest {
        // Given
        val listApi = listOf(
            UserApiModel(
                login = "login",
                id = 1,
                nodeId = "nodeId",
                avatarUrl = "avatarUrl",
                gravatarId = "gravatarId",
                url = "url",
                htmlUrl = "htmlUrl",
                followersUrl = "followersUrl",
                followingUrl = "followingUrl",
                gistsUrl = "gistsUrl",
                starredUrl = "starredUrl",
                subscriptionsUrl = "subscriptionsUrl",
                organizationsUrl = "organizationsUrl",
                reposUrl = "reposUrl",
                eventsUrl = "eventsUrl",
                receivedEventsUrl = "receivedEventsUrl",
                type = "type",
                siteAdmin = true,
                name = "name",
                location = "location",
                followers = 1
            )
        )
        val listData = listOf<UserDataModel>(
            UserDataModel(
                id = "1",
                name = "login",
            )
        )
        coEvery { _usersApiService.getUsers() } returns listApi
        coEvery { _usersMapper.mapApiToData(listApi.first()) } returns listData.first()

        // When
        val result = _usersDataSource.getUsers()

        // Then
        coVerify { _usersMapper.mapApiToData(listApi.first()) }
        coVerify { _usersApiService.getUsers() }
        assertEquals(listData, result)
    }
}
