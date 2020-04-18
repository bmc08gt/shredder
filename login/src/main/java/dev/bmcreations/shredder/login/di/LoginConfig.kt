package dev.bmcreations.shredder.login.di

import dev.bmcreations.shredder.core.di.Component
import dev.bmcreations.shredder.core.di.CoreComponent
import dev.bmcreations.shredder.di.NetworkComponent
import dev.bmcreations.shredder.environmentDimensionNetworkComponent
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
    override val viewModel: LoginViewModel = LoginViewModel.create()
}
