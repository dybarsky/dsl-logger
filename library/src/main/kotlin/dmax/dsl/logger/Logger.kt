package dmax.dsl.logger

object Logger {

    @JvmStatic
    private val writers = mutableListOf<LogWriter>()

    @JvmStatic
    fun registerWriter(logWriter: LogWriter) =
            writers.add(logWriter)

    @JvmStatic
    fun unregisterWriter(logWriter: LogWriter) =
            writers.remove(logWriter)

    @JvmStatic
    inline fun log(crossinline configure: Log.() -> Unit) {
        if (isWritersAvailable) {
            Log().let {
                configure(it)
                write(it)
            }
        }
    }

    val isWritersAvailable: Boolean
        get() = writers.size > 0

    fun write(log: Log) = writers.forEach {
        it.log(log.level, log.tag, log.throwable, log.message)
    }
}
