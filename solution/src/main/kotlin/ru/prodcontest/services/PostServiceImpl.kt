package ru.prodcontest.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import ru.prodcontest.db.entities.Post
import ru.prodcontest.db.exceptions.ApiException
import ru.prodcontest.db.reposetories.PostRepository
import ru.prodcontest.requests.NewPostRequest
import java.util.*

@Service
class PostServiceImpl(
    @Autowired private val postRepo: PostRepository,
    @Autowired private val tableCreationService: TableCreationService,
    @Autowired private val friendshipService: FriendshipService,
    @Autowired private val userService: UserService
) : PostService {

    init {
        tableCreationService.createPostsTableIfNotExists()
        tableCreationService.createUsersTableIfNotExists()
    }

    override fun findPostById(id: Long, client: String): Post {
        val post = postRepo.findByIdOrNull(id) ?: throw ApiException("No post with given id")
        if (hasAccess(post.author!!, client)) {
            return post
        }

        throw ApiException("No access")
    }

    private fun hasAccess(author: String, client: String) = author == client ||
            userService.findWoPasswordByLogin(author).isPublic ||
            friendshipService.isFriendOf(client, author)

    override fun create(author: String, newPostRequest: NewPostRequest): Post {
        val post = Post(
            content = newPostRequest.content,
            author = author,
            tags = newPostRequest.tags!!.joinToString(separator = ","),
            createdAt = Date()
        )

        return postRepo.save(post)
    }

    override fun getPosts(login: String, limit: Int, offset: Int): MutableList<Post> {

        val posts = ArrayList<Post>()
        val tuples = postRepo.findAllByLogin(login, limit, offset)
        tuples.forEach {
            posts.add(
                Post(
                    id = (it[0] as Int?)!!.toLong(),
                    content = it[1] as String?,
                    author = it[2] as String?,
                    tags = it[3] as String?,
                    createdAt = it[4] as Date?,
                    likesCount = it[5] as Int,
                    dislikesCount = it[6] as Int,
                )
            )
        }

        return posts
    }

    override fun getPosts(clientLogin: String, ownerLogin: String, limit: Int, offset: Int): MutableList<Post> {
        if (!hasAccess(ownerLogin, clientLogin)) {
            throw ApiException("No access")
        }

        return getPosts(ownerLogin, limit, offset)
    }

    override fun exists(id: Long): Boolean {
        return postRepo.existsById(id)
    }

    override fun save(post: Post) {
        postRepo.save(post)
    }
}
