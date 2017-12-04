package dmax.dsl.logger.sample

import android.app.Activity
import android.os.Bundle
import dmax.dsl.logger.AndroidLogWriter
import dmax.dsl.logger.Level
import dmax.dsl.logger.LogWriter
import dmax.dsl.logger.Logger.log
import dmax.dsl.logger.Logger.registerWriter
import dmax.dsl.logger.Sfl4jLogWriter
import java.io.IOException

class TestActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        registerWriter(customLogger)
        registerWriter(AndroidLogWriter())
        registerWriter(Sfl4jLogWriter())

        use()
    }

}

private val customLogger = object : LogWriter {
    override fun log(level: Level, tag: String?, throwable: Throwable?, message: String?) {
        val stringBuffer = StringBuffer("%8s | %12s : %s")
        throwable?.let { stringBuffer.append(" >>> %s") }
        val log = stringBuffer.toString().format(level.name.toLowerCase(), tag ?: "MAIN", message, throwable)
        println(log)
    }
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
        level { Level.WARNING }
        throwable { UnsupportedOperationException() }
    }

    //~

    log {
        wtf { "o_O" }
    }
}

private fun connect(): Nothing = throw IOException("Server unreachable")
