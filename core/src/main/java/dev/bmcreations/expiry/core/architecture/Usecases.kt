package dev.bmcreations.expiry.core.architecture

abstract class GetUsecase<T> {
    abstract fun execute(): T
}

abstract class SuspendingGetUsecase<T> {
    abstract suspend fun execute(): T
}

abstract class SetUsecase<T> {
    abstract fun execute(arg: T)
}

abstract class Parameter2Usecase<S, T> {
    abstract fun execute(p0: S, p1: T)
}

abstract class Parameter3Usecase<Q, S, T> {
    abstract fun execute(p0: Q, p1: S, p2: T)
}

abstract class Parameter4Usecase<P, Q, S, T> {
    abstract fun execute(p0: P, p1: Q, p2: S, p3: T)
}

abstract class ParameterUsecaseWithReturn<T, R> {
    abstract fun execute(arg: T): R
}

abstract class Parameter2UsecaseWithReturn<S, T, R> {
    abstract fun execute(p0: S, p1: T): R
}

abstract class Parameter3UsecaseWithReturn<Q, S, T, R> {
    abstract fun execute(p0: Q, p1: S, p2: T): R
}

abstract class Parameter4UsecaseWithReturn<P, Q, S, T, R> {
    abstract fun execute(p0: P, p1: Q, p2: S, p3: T): R
}

abstract class ParameterUsecaseWithCallback<T, R> {
    abstract fun execute(p0: T, cb: ((R) -> Unit))
}

abstract class Parameter2UsecaseWithCallback<S, T, R> {
    abstract fun execute(p0: S, p1: T, cb: ((R) -> Unit))
}

abstract class Parameter3UsecaseWithCallback<Q, S, T, R> {
    abstract fun execute(p0: Q, p1: S, p2: T, cb: ((R) -> Unit))
}

abstract class Parameter4UsecaseWithCallback<P, Q, S, T, R> {
    abstract fun execute(p0: P, p1: Q, p2: S, p3: T, cb: ((R) -> Unit))
}

abstract class UsecaseWithCallback<R> {
    abstract fun execute(cb: ((R) -> Unit))
}
