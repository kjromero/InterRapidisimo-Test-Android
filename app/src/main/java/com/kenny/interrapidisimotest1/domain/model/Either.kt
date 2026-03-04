package com.kenny.interrapidisimotest1.domain.model

/**
 * Functional pattern to represent either a value of type [L] or [R].
 * The responses from the app's API or DB calls will be represented in two ways.
 *  Left: Error
 *  Right: Success
 */
sealed class Either<out L, out R> {
    class Left<out L>(val value: L) : Either<L, Nothing>()
    class Right<out R>(val value: R) : Either<Nothing, R>()
}