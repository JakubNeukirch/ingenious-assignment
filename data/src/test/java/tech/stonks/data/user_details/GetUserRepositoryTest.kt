package tech.stonks.data.user_details

import io.mockk.*
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import tech.stonks.data.shared.datasource.UsersDataSource
import tech.stonks.data.shared.mapper.ExceptionPresentationMapper
import tech.stonks.data.shared.mapper.UserPresentationMapper
import tech.stonks.data.shared.model.UnknownDataException
import tech.stonks.data.shared.model.UserDataModel
import tech.stonks.presentation.shared.model.UnknownPresentationException
import tech.stonks.presentation.shared.model.UserPresentationModel
import tech.stonks.presentation.user_details.repository.GetUserRepository

class GetUserRepositoryTest {
    private lateinit var _userDataSource: UsersDataSource
    private lateinit var _userMapper: UserPresentationMapper
    private lateinit var _exceptionMapper: ExceptionPresentationMapper
    private lateinit var _getUserRepository: GetUserRepository

    @Before
    fun setUp() {
        _userDataSource = mockk()
        _userMapper = mockk()
        _exceptionMapper = mockk()
        _getUserRepository = GetUserRepositoryImpl(_userDataSource, _userMapper, _exceptionMapper)
    }

    @Test
    fun `when getUser is called then return list of users`() = runTest {
        // Given
        val user = UserDataModel("1", "John", "url", 10)
        val userPresentationModel = UserPresentationModel("1", "John", "url", 10)
        coEvery { _userDataSource.getUser("1") } returns user
        every { _userMapper.map(user) } returns userPresentationModel

        // When
        val result = _getUserRepository.getUser("1")

        // Then
        coVerify { _userDataSource.getUser("1") }
        coVerify { _userMapper.map(user) }
        assertEquals(userPresentationModel, result)
    }

    @Test
    fun `when getUser throws error then map exception`() = runTest {
        // Given
        val error = UnknownDataException(null)
        val presentationException = UnknownPresentationException(null)
        coEvery { _userDataSource.getUser("1") } throws error
        every { _exceptionMapper.map(error) } returns presentationException

        // When
        val result = runCatching { _getUserRepository.getUser("1") }

        // Then
        coVerify { _userDataSource.getUser("1") }
        verify { _exceptionMapper.map(error) }
        assertEquals(presentationException, result.exceptionOrNull())
    }
}
