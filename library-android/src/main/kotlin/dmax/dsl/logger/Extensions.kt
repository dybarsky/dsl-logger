package dmax.dsl.logger

import java.util.regex.Pattern

private const val DEFAULT_TAG = "DSL LOGGER"
private const val MAX_TAG_LENGTH = 23
private const val CALL_STACK_INDEX = 3 // Depth of `generateTag` method
private val ANONYMOUS_CLASS_PATTERN = Pattern.compile("(\\$\\d+)+$")

fun generateTag(): String = Throwable()
        .stackTrace
        .extractClassName()
        .reduceAnonymousClasses()
        .substringAfterLast('.')
        .fitTagLength()

private fun Array<StackTraceElement>.extractClassName() =
        if (this.size > CALL_STACK_INDEX) {
            this[CALL_STACK_INDEX].className
        } else {
            DEFAULT_TAG
        }

private fun String.reduceAnonymousClasses() =
        ANONYMOUS_CLASS_PATTERN.matcher(this).let {
            if (it.find()) it.replaceAll("") else this
        }

private fun String.fitTagLength() =
        take(MAX_TAG_LENGTH)
