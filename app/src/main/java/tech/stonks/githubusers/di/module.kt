package tech.stonks.githubusers.di

import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.Module
import org.koin.dsl.module
import tech.stonks.data.users.GetUsersRepositoryImpl
import tech.stonks.presentation.users.UsersViewModel
import tech.stonks.presentation.users.repository.GetUsersRepository

private val presentationModule = module {
    viewModelOf(::UsersViewModel)
}

private val dataModule = module {
    single<GetUsersRepository> {
        GetUsersRepositoryImpl()
    }
}

val appModule: List<Module> = listOf(
    dataModule,
    presentationModule,
)
