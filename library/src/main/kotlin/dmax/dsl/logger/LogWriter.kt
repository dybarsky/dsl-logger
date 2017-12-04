package dmax.dsl.logger

interface LogWriter {
    fun log(level: Level, tag: String?, throwable: Throwable?, message: String?)
}
