package com.geekbrains.tests.presenter.search

import com.geekbrains.tests.model.SearchResponse
import com.geekbrains.tests.repository.GitHubRepository
import com.geekbrains.tests.repository.GitHubRepository.GitHubRepositoryCallback
import com.geekbrains.tests.view.search.ViewSearchContract
import retrofit2.Response

/**
 * В архитектуре MVP все запросы на получение данных адресуются в Репозиторий.
 * Запросы могут проходить через Interactor или UseCase, использовать источники
 * данных (DataSource), но суть от этого не меняется.
 * Непосредственно Презентер отвечает за управление потоками запросов и ответов,
 * выступая в роли регулировщика движения на перекрестке.
 */

internal class SearchPresenter internal constructor(
    private val repository: GitHubRepository
) : PresenterSearchContract, GitHubRepositoryCallback {

    private var currentView: ViewSearchContract? = null

    override fun searchGitHub(searchQuery: String) {
        currentView?.displayLoading(true)
        repository.searchGithub(searchQuery, this)
    }

    override fun onAttach(view: ViewSearchContract) {
        currentView = view
    }

    override fun onDetach() {
        currentView = null
    }

    override fun handleGitHubResponse(response: Response<SearchResponse?>?) {
        currentView?.displayLoading(false)
        if (response != null && response.isSuccessful) {
            val searchResponse = response.body()
            val searchResults = searchResponse?.searchResults
            val totalCount = searchResponse?.totalCount
            if (searchResults != null && totalCount != null) {
                currentView?.displaySearchResults(
                    searchResults,
                    totalCount
                )
            } else {
                currentView?.displayError("Search results or total count are null")
            }
        } else {
            currentView?.displayError("Response is null or unsuccessful")
        }
    }

    override fun handleGitHubError() {
        currentView?.let {
            it.displayLoading(false)
            it.displayError()
        }
    }
}
