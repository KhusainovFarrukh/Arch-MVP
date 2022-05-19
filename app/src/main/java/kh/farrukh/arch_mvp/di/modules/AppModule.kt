package kh.farrukh.arch_mvp.di.modules

import kh.farrukh.arch_mvp.data.local.LocalDataSource
import kh.farrukh.arch_mvp.data.local.LocalDatabase
import kh.farrukh.arch_mvp.data.remote.RemoteDataSource
import kh.farrukh.arch_mvp.data.remote.RetrofitClient
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module

/**
 *Created by farrukh_kh on 4/7/22 11:47 PM
 *kh.farrukh.arch_mvc.di.modules
 **/
val appModule = module {

    single { LocalDataSource(LocalDatabase.getInstance(get()).movieDao(), Dispatchers.IO) }

    single { RemoteDataSource(RetrofitClient.moviesApi, Dispatchers.IO) }
}