package ircover.idlenation.utils

import java.lang.reflect.Method
import java.lang.reflect.Proxy

class EmptyInvocationHandler {

    companion object {
        fun <T> createEmptyInstance(interfaceClass: Class<T>): T =
                Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(), arrayOf(interfaceClass),
                        { _: Any, _: Method, _: Array<Any> -> }) as T
    }
}