@file:OptIn(ExperimentalCoroutinesApi::class, ExperimentalCoroutinesApi::class)

package tech.stonks.presentation.users

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.jraska.livedata.test
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import tech.stonks.presentation.shared.model.UserPresentationModel
import tech.stonks.presentation.users.model.UsersState
import tech.stonks.presentation.users.repository.GetUsersRepository

class UsersViewModelTest {
    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    private lateinit var _viewModel: UsersViewModel
    private lateinit var _getUsersRepository: GetUsersRepository

    @Before
    fun setUp() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
        _getUsersRepository = mockk()
        _viewModel = UsersViewModel(_getUsersRepository)
    }

    @Test
    fun `when idle should publish initial state`() {
        _viewModel.state.test().assertValue(UsersState.initial())
    }

    @Test
    fun `when onEntered should publish users`() = runTest {
        val users = listOf(
            UserPresentationModel("1", "John Doe"),
            UserPresentationModel("2", "Jane Doe"),
        )
        coEvery { _getUsersRepository.getUsers() } returns users

        _viewModel.onEntered()

        coVerify { _getUsersRepository.getUsers() }
        _viewModel.state.test().assertValue(UsersState(isLoading = false, users = users))
    }

    @Test
    fun `when onEntered with error should publish error`() = runTest {
        coEvery { _getUsersRepository.getUsers() } throws Exception()

        _viewModel.onEntered()

        coVerify { _getUsersRepository.getUsers() }
        _viewModel.state.test()
            .assertValue(UsersState(isLoading = false, error = UsersState.Error.UNKNOWN, users = emptyList()))
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}
