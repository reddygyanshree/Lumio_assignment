package com.example.tvassignment.data.repository

import com.example.tvassignment.TvAssignmentApplication
import com.example.tvassignment.data.local.ArticleEntity
import com.example.tvassignment.data.local.DatabaseProvider
import com.example.tvassignment.data.model.Article
import com.example.tvassignment.data.remote.RetrofitInstance
import com.example.tvassignment.util.ConnectivityHelper

class NewsRepositoryImpl:NewsRepository {

    private val dao = DatabaseProvider.get(TvAssignmentApplication.instance).articleDao()
    private val context get() = TvAssignmentApplication.instance

    /** Returns only cached articles from local DB (for use when network is lost). */
    override suspend fun getCachedNews(): List<Article> = dao.getAll().map { it.toArticle() }

    /**
     * Fetches news: if no internet, returns cached data with fromCache=true.
     * If online, tries API; on success caches and returns with fromCache=false.
     * On API failure, returns cached data with fromCache=true, or failure if empty.
     */
    override suspend fun fetchNews(apiKey: String): Result<NewsResult> {
        if (!ConnectivityHelper.isConnected(context)) {
            val cached = dao.getAll().map { it.toArticle() }
            return if (cached.isNotEmpty()) {
                Result.success(NewsResult(cached, fromCache = true))
            } else {
                Result.failure(Exception("No internet connection. Please check your network and try again."))
            }
        }
        return try {
            val response = RetrofitInstance.api.getTopHeadlines(apiKey = apiKey)
            val articles = response.articles ?: emptyList()
            cacheArticles(articles)
            Result.success(NewsResult(articles, fromCache = false))
        } catch (e: Exception) {
            val cached = dao.getAll().map { it.toArticle() }
            if (cached.isNotEmpty()) {
                Result.success(NewsResult(cached, fromCache = true))
            } else {
                Result.failure(e)
            }
        }
    }

    private suspend fun cacheArticles(articles: List<Article>) {
        dao.deleteAll()
        dao.insertAll(articles.map { it.toEntity() })
    }
}

private fun Article.toEntity() = ArticleEntity(
    title = title,
    description = description,
    urlToImage = urlToImage
)

private fun ArticleEntity.toArticle() = Article(
    title = title,
    description = description,
    urlToImage = urlToImage
)