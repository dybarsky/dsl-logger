package dmax.dsl.logger

import org.slf4j.LoggerFactory

class Sfl4jLogWriter : LogWriter {

    override fun log(level: Level, tag: String?, throwable: Throwable?, message: String?) =
            LoggerFactory.getLogger(tag ?: generateTag()).let {
                when (level) {
                    Level.VERBOSE -> it.info(message, throwable)
                    Level.INFO -> it.info(message, throwable)
                    Level.DEBUG -> it.debug(message, throwable)
                    Level.WARNING -> it.warn(message, throwable)
                    Level.ERROR -> it.error(message, throwable)
                    Level.WTF -> it.error(message, throwable)
                }
            }
}
