package dmax.dsl.logger

class Log {

    var level: Level = Level.DEBUG
    var tag: String? = null
    var throwable: Throwable? = null
    var message: String? = null

    inline fun tag(crossinline tag: () -> String) {
        this.tag = tag.invoke()
    }

    inline fun throwable(crossinline throwable: () -> Throwable) {
        this.throwable = throwable.invoke()
    }

    inline fun level(crossinline level: () -> Level) {
        this.level = level.invoke()
    }

    inline fun message(crossinline message: () -> String) {
        this.message = message.invoke()
    }

    inline fun verbose(crossinline message: () -> String?) {
        level { Level.VERBOSE }
        this.message = message.invoke()
    }

    inline fun info(crossinline message: () -> String?) {
        level { Level.INFO }
        this.message = message.invoke()
    }

    inline fun debug(crossinline message: () -> String?) {
        level { Level.DEBUG }
        this.message = message.invoke()
    }

    inline fun warning(crossinline message: () -> String?) {
        level { Level.WARNING }
        this.message = message.invoke()
    }

    inline fun error(crossinline message: () -> String?) {
        level { Level.ERROR }
        this.message = message.invoke()
    }

    inline fun wtf(crossinline message: () -> String?) {
        level { Level.WTF }
        this.message = message.invoke()
    }
}
