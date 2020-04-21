package dev.bmcreations.shredder.login.di

import dev.bmcreations.shredder.core.di.Component
import dev.bmcreations.shredder.core.di.CoreComponent
import dev.bmcreations.shredder.di.NetworkComponent
import dev.bmcreations.shredder.environmentDimensionNetworkComponent
import dev.bmcreations.shredder.login.repository.FakedLoginRepositoryImpl
import dev.bmcreations.shredder.login.repository.LoginRepositoryImpl
import dev.bmcreations.shredder.login.repository.LoginViewRepository
import dev.bmcreations.shredder.login.usecases.CreateAccountUsecase
import dev.bmcreations.shredder.login.usecases.LoginUsecase
import dev.bmcreations.shredder.login.usecases.ValidateLoginCredentialsUsecase
import dev.bmcreations.shredder.login.view.LoginViewModel
import dev.bmcreations.shredder.network.login.repository.LoginNetworkRepositoryImpl
import dev.bmcreations.shredder.network.login.repository.LoginRepository
import dev.bmcreations.shredder.network.login.service.LoginService
import retrofit2.Retrofit

interface LoginComponent : Component {
    val core: CoreComponent
    val viewModel: LoginViewModel
    val network: LoginNetworkComponent
}

abstract class LoginNetworkComponent : NetworkComponent {
    abstract val login: LoginRepository
}

class LoginNetworkComponentImpl(environment: NetworkComponent) : LoginNetworkComponent() {
    override val retrofit: Retrofit = environment.retrofit
    override val login: LoginRepository =  LoginNetworkRepositoryImpl(retrofit.create(LoginService::class.java))
}

class LoginComponentImpl(
    override val core: CoreComponent
) : LoginComponent {
    override val network: LoginNetworkComponent = LoginNetworkComponentImpl(core.app.environmentDimensionNetworkComponent())

    private enum class ImplType { LIVE, FAKED }

    private val implementation = ImplType.FAKED

    private val repository: LoginViewRepository = when (implementation) {
        ImplType.LIVE -> LoginRepositoryImpl(core.prefs, network.login)
        ImplType.FAKED -> FakedLoginRepositoryImpl(core.prefs)
    }

    override val viewModel: LoginViewModel = LoginViewModel.create(
        createAccountUseCase = CreateAccountUsecase(repository),
        loginUseCase = LoginUsecase(repository),
        validateCredentialsUseCase = ValidateLoginCredentialsUsecase(repository)
    )
}
