package tech.stonks.githubusers.di

import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.Module
import org.koin.dsl.module
import tech.stonks.presentation.users.UsersViewModel

private val presentationModule = module {
    viewModelOf(::UsersViewModel)
}

val appModule: List<Module> = listOf(
    presentationModule,
)
