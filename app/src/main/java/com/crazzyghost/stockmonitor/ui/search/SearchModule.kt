package com.crazzyghost.stockmonitor.ui.search

import com.crazzyghost.stockmonitor.data.repo.CompanyRepository
import dagger.Module
import dagger.Provides

@Module
class SearchModule {

    @Provides
    fun presenter(repository: CompanyRepository) : SearchContract.Presenter{
        return SearchPresenter(repository)
    }
}