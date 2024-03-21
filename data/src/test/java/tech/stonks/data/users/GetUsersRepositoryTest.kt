package tech.stonks.data.users

import io.mockk.*
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import tech.stonks.data.shared.datasource.UsersDataSource
import tech.stonks.data.shared.mapper.ExceptionPresentationMapper
import tech.stonks.data.shared.mapper.UserPresentationMapper
import tech.stonks.data.shared.model.UnknownDataException
import tech.stonks.data.shared.model.UserDataModel
import tech.stonks.presentation.shared.model.UnknownPresentationException
import tech.stonks.presentation.shared.model.UserPresentationModel
import tech.stonks.presentation.users.repository.GetUsersRepository

class GetUsersRepositoryTest {
    private lateinit var exceptionMapper: ExceptionPresentationMapper
    private lateinit var userDataSource: UsersDataSource
    private lateinit var userMapper: UserPresentationMapper
    private lateinit var getUsersRepository: GetUsersRepository

    @Before
    fun setUp() {
        userDataSource = mockk()
        exceptionMapper = mockk()
        userMapper = mockk()
        getUsersRepository = GetUsersRepositoryImpl(userDataSource, userMapper, exceptionMapper)
    }

    @Test
    fun `when getUsers is called then return list of users`() = runTest {
        // Given
        val users = listOf(
            UserDataModel("1", "John", "url", 10),
            UserDataModel("2", "Doe", "url", 20),
        )
        val userPresentationModels = listOf(
            UserPresentationModel("1", "John", "url", 10),
            UserPresentationModel("2", "Doe", "url", 20),
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
    fun `when getUsers throws error then map exception`() = runTest {
        // Given
        val error = UnknownDataException(null)
        val presentationException = UnknownPresentationException(null)
        coEvery { userDataSource.getUsers() } throws error
        every { exceptionMapper.map(error) } returns presentationException

        // When
        val result = runCatching { getUsersRepository.getUsers() }

        // Then
        verify { exceptionMapper.map(error) }
        assertEquals(presentationException, result.exceptionOrNull())
    }
}
