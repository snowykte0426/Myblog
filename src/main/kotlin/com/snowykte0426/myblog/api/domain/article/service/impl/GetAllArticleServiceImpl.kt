package com.snowykte0426.myblog.api.domain.article.service.impl

import com.snowykte0426.myblog.api.domain.article.service.GetAllArticleService
import com.snowykte0426.myblog.api.domain.article.component.FindArticle
import com.snowykte0426.myblog.api.domain.article.presentation.dto.response.ArticleResponse
import com.snowykte0426.myblog.api.domain.article.exception.ArticleNotFoundException
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class GetAllArticleServiceImpl(
    @Value("\${aws.s3.dev-url}")
    private val devUrl: String,
    private val findArticle: FindArticle
) : GetAllArticleService {
    override fun execute(): List<ArticleResponse> {
        val articles = findArticle.execute()
        if (articles.isEmpty()) {
            throw ArticleNotFoundException("Article not found")
        }
        return articles.map {
            ArticleResponse(
                id = it.id!!,
                title = it.title,
                content = it.content,
                tag = it.tag,
                createdAt = it.createdAt.toString(),
                viewCount = it.viewCount,
                imageUrl = if (it.imageUrl != null) devUrl + "/" + it.imageName else null
            )
        }
    }
}