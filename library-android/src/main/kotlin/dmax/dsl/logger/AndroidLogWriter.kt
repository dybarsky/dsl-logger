package dmax.dsl.logger

import dmax.dsl.logger.Level.*
import java.util.regex.Pattern

class AndroidLogWriter : LogWriter {

    companion object {
        private const val MAX_TAG_LENGTH = 23
        private const val CALL_STACK_INDEX = 3 // Depth of `getCallerClassName` method
        private val ANONYMOUS_CLASS_PATTERN = Pattern.compile("(\\$\\d+)+$")
    }

    override fun log(level: Level, tag: String?, throwable: Throwable?, message: String?) {
        val generatedTag = tag ?: getCallerClassName()
        when (level) {
            VERBOSE -> android.util.Log.v(generatedTag, message, throwable)
            INFO -> android.util.Log.i(generatedTag, message, throwable)
            DEBUG -> android.util.Log.d(generatedTag, message, throwable)
            WARNING -> android.util.Log.w(generatedTag, message, throwable)
            ERROR -> android.util.Log.e(generatedTag, message, throwable)
            WTF -> android.util.Log.wtf(generatedTag, message, throwable)
        }
    }

    private fun getCallerClassName(): String = Throwable()
            .stackTrace
            .extractClassName()
            .reduceAnonymousClasses()
            .substringAfterLast('.')
            .fitTagLength()

    private fun Array<StackTraceElement>.extractClassName() =
            if (this.size > CALL_STACK_INDEX) {
                this[CALL_STACK_INDEX].className
            } else {
                throw IllegalStateException("Synthetic stacktrace didn't have enough elements")
            }

    private fun String.reduceAnonymousClasses() =
            ANONYMOUS_CLASS_PATTERN.matcher(this).let {
                if (it.find()) it.replaceAll("") else this
            }

    private fun String.fitTagLength() =
            take(MAX_TAG_LENGTH)
}
