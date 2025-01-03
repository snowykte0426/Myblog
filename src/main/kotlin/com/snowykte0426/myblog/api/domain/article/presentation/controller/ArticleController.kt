package com.snowykte0426.myblog.api.domain.article.presentation.controller

import com.snowykte0426.myblog.api.domain.article.presentation.dto.ArticleDto
import com.snowykte0426.myblog.api.domain.article.presentation.dto.request.PatchArticleRequest
import com.snowykte0426.myblog.api.domain.article.presentation.dto.request.PostArticleRequest
import com.snowykte0426.myblog.api.domain.article.presentation.dto.response.ArticleResponse
import com.snowykte0426.myblog.api.domain.article.presentation.dto.response.TagResponse
import com.snowykte0426.myblog.api.domain.article.service.*
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/articles")
class ArticleController(
    private val getAllArticleService: GetAllArticleService,
    private val getArticleService: GetArticleService,
    private val postArticleService: PostArticleService,
    private val patchArticleService: PatchArticleService,
    private val deleteArticleService: DeleteArticleService,
    private val patchViewCountService: PatchViewCountService,
    private val getTagService: GetTagService
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
    @ResponseStatus(HttpStatus.CREATED)
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
    fun patchArticle(
        @PathVariable id: Long,
        @RequestBody request: PatchArticleRequest
    ): ResponseEntity<ArticleResponse> {
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

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteArticle(@PathVariable id: Long) {
        deleteArticleService.execute(id)
    }

    @PatchMapping("/{id}/view")
    fun patchViewCount(@PathVariable id: Long): ResponseEntity<ArticleDto> {
        return ResponseEntity.ok(patchViewCountService.execute(id))
    }

    @GetMapping("/tags")
    fun getTags(): ResponseEntity<TagResponse> {
        return ResponseEntity.ok(getTagService.execute())
    }
}