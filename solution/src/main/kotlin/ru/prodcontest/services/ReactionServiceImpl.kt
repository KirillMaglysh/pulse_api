package ru.prodcontest.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import ru.prodcontest.db.entities.Post
import ru.prodcontest.db.entities.Reaction
import ru.prodcontest.db.reposetories.ReactionRepository

@Service
class ReactionServiceImpl(
    @Autowired private val reactionRepo: ReactionRepository,
    @Autowired private val tableCreationService: TableCreationService,
    @Autowired private val postService: PostService
) : ReactionService {

    init {
        tableCreationService.createReactionsTableIfNotExists()
    }

    override fun react(postId: Long, clientLogin: String, isLike: Boolean): Post {
        val post = postService.findPostById(postId, clientLogin)
        val reaction = reactionRepo.findFirstByLoginAndPostId(clientLogin, postId)
        if (reaction == null) {
            reactionRepo.save(Reaction(login = clientLogin, postId = postId, isLike = isLike))
            if (isLike) {
                ++post.likesCount
            } else {
                ++post.dislikesCount
            }
        } else if (reaction.isLike != isLike) {
            reaction.isLike = isLike
            reactionRepo.save(reaction)
            if (isLike) {
                ++post.likesCount
                --post.dislikesCount
            } else {
                ++post.dislikesCount
                --post.likesCount
            }
        }

        postService.save(post)
        return post
    }
}