package com.toan_itc.core.kotlinify.collections

import java.util.*

/**
 * Created by gilgoldzweig on 04/09/2017.
 */

private val random = Random()

//region collections
fun <E> Collection<E>?.isNullOrEmpty(): Boolean = this == null || this.isEmpty()
fun <E> Collection<E>?.isNotNullOrEmpty() = !isNullOrEmpty()
//endregion collections

//region maps
fun <K, V> Map<K, V>?.isNullOrEmpty() = this == null || this.isEmpty()
fun <K, V> Map<K, V>?.isNotNullOrEmpty() = !isNullOrEmpty()
//endregion maps

//region sets
fun <E> Set<E>?.isNullOrEmpty() = this == null || this.isEmpty()
fun <E> Set<E>?.isNotNullOrEmpty() = !isNullOrEmpty()
//endregion sets


/**
 * Extension function to execute a [callback] when the provided [input] is not null.
 */
inline fun <T : Any, R> whenNotNull(input: T?, callback: (T) -> R): R? = input?.let(callback)

/**
 * Extension function to execute a [callback] when the provided [input] is null.
 */
inline fun <T : Any, R> whenNull(input: T?, callback: () -> R) = input?.let { }
        ?: run { callback() }

/**
 * Extension function to execute a [block] when the given [condition] is false.
 */
inline fun whenFalse(condition: Boolean, block: () -> Unit) {
    if (!condition) {
        block()
    }
}

/**
 * Extension function to execute a [block] when the given [condition] is true.
 */
inline fun whenTrue(condition: Boolean, block: () -> Unit) {
    if (condition) {
        block()
    }
}

//region lists
fun <E>List<E>?.isNullOrEmpty() = this == null || this.isEmpty()

fun <E>List<E>?.isNotNullOrEmpty(): Boolean = !isNullOrEmpty()

fun <E>List<E>.random() = this[random.nextInt(size)]

operator fun <E>List<E>.get(e: E) = this[indexOf(e)]
//endregion lists

//region array lists
operator fun<E> ArrayList<E>.plusAssign(obj: E) = add(size, obj)
infix fun <E> ArrayList<E>.addIfNotExist(obj: E) = if (!contains(obj)) add(obj) else false
infix fun <E> ArrayList<E>.removeIfExist(obj: E) = if (contains(obj)) remove(obj) else false
//endregion array lists

//region Iterable
fun <T, E> E.asIterable(hasNext: E.() -> Boolean, next: E.() -> T) =
        object : Iterable<T> {
            override fun iterator(): Iterator<T> {
                return object : Iterator<T> {
                    override fun hasNext() = hasNext(this@asIterable)
                    override fun next() = next(this@asIterable)
                }
            }
        }

fun <T, E> E.asIterable(hasNext: Boolean, next: (it: E) -> T) =
        object : Iterable<T> {
            override fun iterator(): Iterator<T> {
                return object : Iterator<T> {
                    override fun hasNext() = hasNext
                    override fun next() = next(this@asIterable)
                }
            }
        }

fun <T, E> E.asIterableIndexed(hasNext: E.(index: Int) -> Boolean,
                               next: E.(index: Int) -> T,
                               changeOnNext: (index: Int) -> Int): Iterable<T> {
    var index = 0
   return object : Iterable<T> {
        override fun iterator(): Iterator<T> {
            return object : Iterator<T> {
                override fun hasNext() = this@asIterableIndexed.hasNext(index)
                override fun next(): T {
                    val element = this@asIterableIndexed.next(index)
                    index = changeOnNext(index)
                    return element
                }
            }
        }
    }
}

fun <T, E> E.asIterableIndexed(hasNext: Boolean,
                               next: E.(index: Int) -> T,
                               changeOnNext: (index: Int) -> Int) =
        asIterableIndexed({ hasNext }, next, changeOnNext)
//endregion Iterable