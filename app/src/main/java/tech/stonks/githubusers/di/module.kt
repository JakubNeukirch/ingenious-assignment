package tech.stonks.githubusers.di

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import tech.stonks.data.shared.datasource.UsersDataSource
import tech.stonks.data.shared.mapper.UserPresentationMapper
import tech.stonks.data.users.GetUsersRepositoryImpl
import tech.stonks.datasource.shared.datasource.UsersDataSourceImpl
import tech.stonks.datasource.shared.mapper.UserDataMapper
import tech.stonks.datasource.shared.service.UsersApiService
import tech.stonks.presentation.users.UsersViewModel
import tech.stonks.presentation.users.repository.GetUsersRepository

private val presentationModule = module {
    viewModelOf(::UsersViewModel)
}

private val dataModule = module {
    single<GetUsersRepository> { get<GetUsersRepositoryImpl>() }
    singleOf(::GetUsersRepositoryImpl)
    single { UserPresentationMapper() }
}

private val dataSourceModule = module {
    single { UserDataMapper() }
    single<UsersDataSource> { get<UsersDataSourceImpl>() }
    singleOf(::UsersDataSourceImpl)
    single<Moshi> {
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }
    single<UsersApiService> {
        Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(MoshiConverterFactory.create(get<Moshi>()))
            .build()
            .create(UsersApiService::class.java)
    }
    single { UserDataMapper() }
}

val appModule: List<Module> = listOf(
    dataModule,
    presentationModule,
    dataSourceModule,
)
