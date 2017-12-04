package dmax.dsl.logger

import dmax.dsl.logger.Logger.log
import dmax.dsl.logger.Logger.registerWriter
import java.io.IOException

fun main(args: Array<String>) {
    setup()
    use()
}

private fun setup() {
    registerWriter(object : LogWriter {
        override fun log(level: Level, tag: String?, throwable: Throwable?, message: String?) {
            val stringBuffer = StringBuffer("%8s | %12s : %s")
            throwable?.let { stringBuffer.append(" >>> %s") }
            val log = stringBuffer.toString().format(level.name.toLowerCase(), tag ?: "MAIN", message, throwable)
            println(log)
        }
    })
}

private fun use() {
    try {
        connect()
    } catch (e: IOException) {
        log {
            level { Level.INFO }
            tag { "NETWORKING" }
            message { "Connection failed" }
            throwable { e }
        }
    }

    //~

    try {
        connect()
    } catch (e: IOException) {
        log {
            info { "Connection failed" }
            throwable { e }
        }
    }

    //~

    log {
        tag { "Parser" }
        throwable { UnsupportedOperationException() }
    }

    //~

    log {
        wtf { "o_O" }
    }
}

private fun connect(): Nothing = throw IOException("Server unreachable")