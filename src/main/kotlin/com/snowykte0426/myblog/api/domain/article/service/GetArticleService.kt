package com.snowykte0426.myblog.api.domain.article.service

import com.snowykte0426.myblog.api.domain.article.presentation.dto.response.ArticleResponse

interface GetArticleService {
    fun execute(id: Long): ArticleResponse
}