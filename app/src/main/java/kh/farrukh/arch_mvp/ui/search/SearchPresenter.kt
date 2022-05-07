package kh.farrukh.arch_mvp.ui.search

import kh.farrukh.arch_mvp.data.remote.RemoteDataSource
import kh.farrukh.arch_mvp.utils.handle

/**
 *Created by farrukh_kh on 5/6/22 12:51 PM
 *kh.farrukh.arch_mvp.ui.search
 **/
class SearchPresenter(
    private var viewInterface: SearchContract.ViewInterface,
    private var dataSource: RemoteDataSource
) : SearchContract.PresenterInterface {

    override suspend fun getSearchResults(query: String) {
        dataSource.searchResults(query).handle(
            { response ->
                viewInterface.displayEmptyLayout(response.totalResults == 0 || response.totalResults == null)
                viewInterface.displayResult(response)
            },
            { error ->
                viewInterface.displayError(error?.message ?: "Unknown error")
            })
    }
}