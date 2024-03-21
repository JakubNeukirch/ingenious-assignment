package tech.stonks.presentation.user_details

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import tech.stonks.presentation.shared.BaseViewModel
import tech.stonks.presentation.shared.model.BackPresentationDestination
import tech.stonks.presentation.shared.model.PresentationException
import tech.stonks.presentation.user_details.model.UserDetailsState
import tech.stonks.presentation.user_details.repository.GetUserRepository

class UserDetailsViewModel(
    private val _userLogin: String,
    private val _getUserRepository: GetUserRepository,
) : BaseViewModel<UserDetailsState>(UserDetailsState.initial()) {
    fun onEntered() {
        loadData()
    }

    fun onRefresh() {
        loadData()
    }

    private fun loadData() {
        modifyState { it.withLoading(true) }
        viewModelScope.launch {
            try {
                val user = withContext(Dispatchers.IO) { _getUserRepository.getUser(_userLogin) }
                modifyState {
                    it.withUser(user).withLoading(false)
                        .withError(null)
                }
            } catch (ex: PresentationException) {
                ex.printStackTrace()
                modifyState {
                    it.withLoading(false)
                        .withError(ex)
                }
            } catch (ex: Exception) {
                ex.printStackTrace()
                throw IllegalStateException("Unhandled exception", ex)
            }
        }
    }

    fun onBackPressed() {
        navigateTo(BackPresentationDestination)
    }
}
