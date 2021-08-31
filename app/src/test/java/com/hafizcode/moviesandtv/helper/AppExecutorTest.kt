package com.hafizcode.moviesandtv.helper

import java.util.concurrent.Executor

class AppExecutorTest : Executor {
    override fun execute(command: Runnable) {
        command.run()
    }
}