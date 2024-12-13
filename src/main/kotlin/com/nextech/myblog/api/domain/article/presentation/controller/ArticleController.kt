package com.nextech.myblog.api.domain.article.presentation.controller

import com.nextech.myblog.api.domain.article.dto.request.PatchArticleRequest
import com.nextech.myblog.api.domain.article.dto.request.PostArticleRequest
import com.nextech.myblog.api.domain.article.dto.response.ArticleResponse
import com.nextech.myblog.api.domain.article.service.GetAllArticleService
import com.nextech.myblog.api.domain.article.service.GetArticleService
import com.nextech.myblog.api.domain.article.service.PatchArticleService
import com.nextech.myblog.api.domain.article.service.PostArticleService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/articles")
class ArticleController(
    private val getAllArticleService: GetAllArticleService,
    private val getArticleService: GetArticleService,
    private val postArticleService: PostArticleService,
    private val patchArticleService: PatchArticleService
) {
    @GetMapping
    fun getArticles(): ResponseEntity<List<ArticleResponse>> {
        return ResponseEntity.ok(getAllArticleService.execute())
    }

    @GetMapping("/{id}")
    fun getArticle(@PathVariable id: Long): ResponseEntity<ArticleResponse> {
        return ResponseEntity.ok(getArticleService.execute(id))
    }

    @PostMapping
    fun postArticle(@RequestBody request: PostArticleRequest): ResponseEntity<ArticleResponse> {
        return ResponseEntity.status(201).body(
            postArticleService.execute(
                title = request.title,
                content = request.content,
                tag = request.tag,
                image = request.image
            )
        )
    }

    @PatchMapping("/{id}")
    fun patchArticle(@PathVariable id: Long, @RequestBody request: PatchArticleRequest): ResponseEntity<ArticleResponse> {
        return ResponseEntity.ok(
            patchArticleService.execute(
                id = id,
                title = request.title,
                content = request.content,
                tag = request.tag,
                image = request.image
            )
        )
    }
}