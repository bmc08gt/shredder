package dev.bmcreations.shredder.core.di

import androidx.annotation.MainThread
import java.lang.RuntimeException
import kotlin.reflect.KClass
import kotlin.reflect.KProperty0

@MainThread
inline fun <reified C : Component> components(): Lazy<C> {
    return ComponentLazy(C::class)
}

class ComponentLazy<C : Component> (
    private val componentClass: KClass<C>
) : Lazy<C> {
    private var cached: C? = null

    override val value: C
        get() {
            val match = ComponentRouter.components.values.find { it.javaClass.interfaces.contains(componentClass.java) }

            requireNotNull(match) {
                ComponentNotRegisteredException(componentClass::simpleName)
            }

            return (match as C).also { cached = it }
        }

    override fun isInitialized() = cached != null
}

class ComponentNotRegisteredException(name: KProperty0<String?>): RuntimeException("${name.get()} not registered")
