package kh.farrukh.arch_mvp.ui.search

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import kh.farrukh.arch_mvp.data.remote.RemoteDataSource
import kh.farrukh.arch_mvp.utils.handle
import org.reactivestreams.Subscription

/**
 *Created by farrukh_kh on 5/6/22 12:51 PM
 *kh.farrukh.arch_mvp.ui.search
 **/
class SearchPresenter(
    private var viewInterface: SearchContract.ViewInterface,
    private var dataSource: RemoteDataSource
) : SearchContract.PresenterInterface {

    private var searchSubscription: Subscription? = null

    override fun getSearchResults(query: String) {
        dataSource.searchResults(query)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { subscription -> searchSubscription = subscription }
            .subscribe(
                { result -> result.handle(viewInterface::displayResult, viewInterface::displayError) },
                viewInterface::displayError
            )
    }

    override fun stop() {
        searchSubscription?.cancel()
        searchSubscription = null
    }
}