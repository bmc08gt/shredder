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
import dev.bmcreations.shredder.network.user.repository.UserNetworkRepositoryImpl
import dev.bmcreations.shredder.network.user.repository.UserRepository
import dev.bmcreations.shredder.network.user.service.UserService
import retrofit2.Retrofit

interface LoginComponent : Component {
    val core: CoreComponent
    val viewModel: LoginViewModel
    val network: LoginNetworkComponent
}

abstract class LoginNetworkComponent : NetworkComponent {
    abstract val login: LoginRepository
    abstract val user: UserRepository
}

class LoginNetworkComponentImpl(environment: NetworkComponent) : LoginNetworkComponent() {
    override val retrofit: Retrofit = environment.retrofit
    override val login: LoginRepository = LoginNetworkRepositoryImpl(
        retrofit.create(LoginService::class.java)
    )
    override val user: UserRepository = UserNetworkRepositoryImpl(
        retrofit.create(UserService::class.java)
    )
}

class LoginComponentImpl(
    override val core: CoreComponent
) : LoginComponent {
    override val network: LoginNetworkComponent = LoginNetworkComponentImpl(core.app.environmentDimensionNetworkComponent())

    private enum class ImplType { LIVE, FAKED }

    private val implementation = ImplType.LIVE

    private val repository: LoginViewRepository = when (implementation) {
        ImplType.LIVE -> LoginRepositoryImpl(core.prefs, network.login, network.user)
        ImplType.FAKED -> FakedLoginRepositoryImpl(core.prefs)
    }

    override val viewModel: LoginViewModel = LoginViewModel.create(
        createAccountUseCase = CreateAccountUsecase(repository),
        loginUseCase = LoginUsecase(repository),
        validateCredentialsUseCase = ValidateLoginCredentialsUsecase(repository)
    )
}
