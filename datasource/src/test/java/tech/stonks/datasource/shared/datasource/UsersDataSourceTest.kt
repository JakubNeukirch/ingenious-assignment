package tech.stonks.datasource.shared.datasource

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import tech.stonks.data.shared.datasource.UsersDataSource
import tech.stonks.data.shared.model.UnknownDataException
import tech.stonks.data.shared.model.UserDataModel
import tech.stonks.datasource.shared.mapper.ExceptionDataMapper
import tech.stonks.datasource.shared.mapper.UserDataMapper
import tech.stonks.datasource.shared.model.api.UserApiModel
import tech.stonks.datasource.shared.service.UsersApiService

class UsersDataSourceTest {
    private lateinit var _usersApiService: UsersApiService
    private lateinit var _usersMapper: UserDataMapper
    private lateinit var _exceptionMapper: ExceptionDataMapper
    private lateinit var _usersDataSource: UsersDataSource

    @Before
    fun setUp() {
        _usersApiService = mockk()
        _usersMapper = mockk()
        _exceptionMapper = mockk()
        _usersDataSource = UsersDataSourceImpl(
            _usersApiService, _usersMapper, _exceptionMapper
        )
    }

    @Test
    fun `when getUsers called then should call getUsers from UsersApiService`() = runTest {
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
                bio = "bio",
                followers = 1
            )
        )
        val listData = listOf<UserDataModel>(
            UserDataModel(
                id = "1",
                login = "login",
                avatarUrl = "avatarUrl",
                followers = 1
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

    @Test
    fun `when getUsers throws exception then map exception`() = runTest {
        // Given
        val exception = Exception()
        val dataException = UnknownDataException(null)
        coEvery { _usersApiService.getUsers() } throws exception
        every { _exceptionMapper.map(exception) } returns dataException

        // When
        val result = kotlin.runCatching { _usersDataSource.getUsers() }

        // Then
        coVerify { _usersApiService.getUsers() }
        coVerify { _exceptionMapper.map(exception) }

        assertEquals(dataException, result.exceptionOrNull())
    }

    @Test
    fun `when getUser called then should call getUserByLogin from UsersApiService`() = runTest {
        // Given
        val userLogin = "login"
        val userApi = UserApiModel(
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
            bio = "bio",
            followers = 1
        )
        val userData = UserDataModel(
            id = "1",
            login = "login",
            avatarUrl = "avatarUrl",
            followers = 1
        )
        coEvery { _usersApiService.getUserByLogin(userLogin) } returns userApi
        coEvery { _usersMapper.mapApiToData(userApi) } returns userData

        // When
        val result = _usersDataSource.getUser(userLogin)

        // Then
        coVerify { _usersMapper.mapApiToData(userApi) }
        coVerify { _usersApiService.getUserByLogin(userLogin) }
        assertEquals(userData, result)
    }

    @Test
    fun `when getUser throws exception then map exception`() = runTest {
        // Given
        val userLogin = "login"
        val exception = Exception()
        val dataException = UnknownDataException(null)
        coEvery { _usersApiService.getUserByLogin(userLogin) } throws exception
        every { _exceptionMapper.map(exception) } returns dataException

        // When
        val result = kotlin.runCatching { _usersDataSource.getUser(userLogin) }

        // Then
        coVerify { _usersApiService.getUserByLogin(userLogin) }
        coVerify { _exceptionMapper.map(exception) }

        assertEquals(dataException, result.exceptionOrNull())
    }
}
