package tech.stonks.presentation.user_details

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.jraska.livedata.test
import io.mockk.coEvery
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
import tech.stonks.presentation.shared.model.BackPresentationDestination
import tech.stonks.presentation.shared.model.UnknownPresentationException
import tech.stonks.presentation.shared.model.UserPresentationModel
import tech.stonks.presentation.user_details.model.UserDetailsState
import tech.stonks.presentation.user_details.repository.GetUserRepository

class UserDetailsViewModelTest {
    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    private lateinit var _viewModel: UserDetailsViewModel
    private lateinit var _getUserDetailsRepository: GetUserRepository

    companion object {
        private const val USER_ID = "1"
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
        _getUserDetailsRepository = mockk()
        _viewModel = UserDetailsViewModel(USER_ID, _getUserDetailsRepository)
    }

    @Test
    fun `when idle should publish initial state`() = runTest {
        _viewModel.state.test().assertValue(UserDetailsState.initial())
    }

    @Test
    fun `when onEntered should publish user details`() = runTest {
        val user = UserPresentationModel(
            id = "1",
            login = "John Doe",
            avatarUrl = "url",
            followers = 10
        )
        val state = UserDetailsState(
            isLoading = false,
            user = user,
            error = null
        )
        coEvery { _getUserDetailsRepository.getUser(USER_ID) } returns user


        _viewModel.onEntered()

        _viewModel.state.test().assertValue(state)
    }

    @Test
    fun `when onEntered and load error should publish error`() = runTest {
        val exception = UnknownPresentationException(null)
        val state = UserDetailsState(
            isLoading = false,
            user = null,
            error = exception
        )
        coEvery { _getUserDetailsRepository.getUser(USER_ID) } throws exception

        _viewModel.onEntered()

        _viewModel.state.test().assertValue(state)
    }

    @Test
    fun `when onBackPressed should navigate back`() = runTest {
        _viewModel.onBackPressed()

        _viewModel.destination.test().assertValue(BackPresentationDestination)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}
