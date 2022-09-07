package com.github.skiedrowski.tools.test

import io.mockk.mockk
import kotlin.reflect.KClass

/**
 * relaxed mockk by default
 */
inline fun <reified T : Any> rxMockk(
    name: String? = null,
    relaxed: Boolean = true,
    vararg moreInterfaces: KClass<*>,
    relaxUnitFun: Boolean = true,
    block: T.() -> Unit = {}
): T = mockk(
    name = name,
    relaxed = relaxed,
    moreInterfaces = moreInterfaces,
    relaxUnitFun = relaxUnitFun,
    block = block
)
