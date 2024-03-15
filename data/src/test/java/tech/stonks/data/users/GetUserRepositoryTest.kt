package tech.stonks.data.users

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import tech.stonks.data.shared.datasource.UsersDataSource
import tech.stonks.data.shared.mapper.UserDataToPresentationMapper
import tech.stonks.data.shared.model.UserDataModel
import tech.stonks.presentation.shared.model.UserPresentationModel
import tech.stonks.presentation.users.repository.GetUsersRepository

class GetUserRepositoryTest {
    private lateinit var userDataSource: UsersDataSource
    private lateinit var userMapper: UserDataToPresentationMapper
    private lateinit var getUsersRepository: GetUsersRepository

    @Before
    fun setUp() {
        userDataSource = mockk()
        userMapper = mockk()
        getUsersRepository = GetUsersRepositoryImpl(userDataSource, userMapper)
    }

    @Test
    fun `when getUsers is called then return list of users`() = runTest {
        // Given
        val users = listOf(
            UserDataModel("1", "John"),
            UserDataModel("2", "Doe")
        )
        val userPresentationModels = listOf(
            UserPresentationModel("1", "John"),
            UserPresentationModel("2", "Doe")
        )
        coEvery { userDataSource.getUsers() } returns users
        every { userMapper.map(users[0]) } returns userPresentationModels[0]
        every { userMapper.map(users[1]) } returns userPresentationModels[1]

        // When
        val result = getUsersRepository.getUsers()

        // Then
        coVerify { userDataSource.getUsers() }
        coVerify { userMapper.map(users[0]) }
        coVerify { userMapper.map(users[1]) }
        assertEquals(userPresentationModels, result)
    }

    @Test
    fun `when getUsers throws error then forward error`() = runTest {
        // Given
        val error = Exception()
        coEvery { userDataSource.getUsers() } throws error

        // When
        val result = runCatching { getUsersRepository.getUsers() }

        // Then
        assertEquals(error, result.exceptionOrNull())
    }
}
