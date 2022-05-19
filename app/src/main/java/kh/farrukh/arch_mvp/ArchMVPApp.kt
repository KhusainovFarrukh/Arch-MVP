package kh.farrukh.arch_mvp

import android.app.Application
import kh.farrukh.arch_mvp.di.modules.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

/**
 *Created by farrukh_kh on 5/6/22 1:25 PM
 *kh.farrukh.arch_mvp
 **/
class ArchMVPApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            //There is bug in Koin with Kotlin 1.6: https://github.com/InsertKoinIO/koin/issues/1188
            //androidLogger()
            androidContext(this@ArchMVPApp)
            modules(appModule)
        }
    }
}