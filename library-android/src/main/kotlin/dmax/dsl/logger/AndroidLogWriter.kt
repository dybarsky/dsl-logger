package dmax.dsl.logger

import dmax.dsl.logger.Level.*

class AndroidLogWriter : LogWriter {

    override fun log(level: Level, tag: String?, throwable: Throwable?, message: String?) {
        val generatedTag = tag ?: generateTag()
        when (level) {
            VERBOSE -> android.util.Log.v(generatedTag, message, throwable)
            INFO -> android.util.Log.i(generatedTag, message, throwable)
            DEBUG -> android.util.Log.d(generatedTag, message, throwable)
            WARNING -> android.util.Log.w(generatedTag, message, throwable)
            ERROR -> android.util.Log.e(generatedTag, message, throwable)
            WTF -> android.util.Log.wtf(generatedTag, message, throwable)
        }
    }
}
