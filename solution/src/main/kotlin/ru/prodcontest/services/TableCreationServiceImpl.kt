package ru.prodcontest.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Service

@Service
class TableCreationServiceImpl(@Autowired private val jdbcTemplate: JdbcTemplate) : TableCreationService {
    override fun createUsersTableIfNotExists() {
        val createTableQuery = "CREATE TABLE IF NOT EXISTS users" +
                "(login       VARCHAR(30) PRIMARY KEY," +
                "email       VARCHAR(50)," +
                "password    VARCHAR(100)," +
                "countryCode VARCHAR(2)," +
                "isPublic    BOOLEAN," +
                "phone       VARCHAR(20)  NULL," +
                "image       VARCHAR(200) NULL" +
                ");"
        jdbcTemplate.execute(createTableQuery)
    }

    override fun createTokensTableIfNotExists() {
        val createTableQuery = "CREATE TABLE IF NOT EXISTS tokens" +
                "(" +
                "token       VARCHAR(300) PRIMARY KEY" +
                ");"
        jdbcTemplate.execute(createTableQuery)
    }

    override fun createPasswordUpdatesTableIfNotExists() {
        val createTableQuery = "CREATE TABLE IF NOT EXISTS password_updates" +
                "(" +
                "    login   VARCHAR(30) PRIMARY KEY," +
                "    updated BIGINT" +
                ");"
        jdbcTemplate.execute(createTableQuery)
    }

    override fun createFriendshipsTableIfNotExists() {
        val createTableQuery = "CREATE TABLE IF NOT EXISTS friendships" +
                "(" +
                "    id  SERIAL PRIMARY KEY ," +
                "    login    VARCHAR(30) NOT NULL," +
                "    friend   VARCHAR(30) NOT NULL," +
                "    added_at TIMESTAMP          NOT NULL" +
                ")"
        jdbcTemplate.execute(createTableQuery)
    }

    override fun createPostsTableIfNotExists() {
        val createTableQuery = "CREATE TABLE IF NOT EXISTS posts" +
                "(" +
                "    id            SERIAL PRIMARY KEY," +
                "    content       VARCHAR(1000)," +
                "    author        VARCHAR(30)," +
                "    tags          TEXT," +
                "    created_at    TIMESTAMP," +
                "    likes_count    INT," +
                "    dislikes_count INT" +
                ");"
        jdbcTemplate.execute(createTableQuery)
    }

    override fun createReactionsTableIfNotExists() {
        val createTableQuery = "CREATE TABLE IF NOT EXISTS reactions" +
                "(" +
                "    id     SERIAL PRIMARY KEY," +
                "    login  VARCHAR(30)," +
                "    post_id BIGINT," +
                "    is_like BOOLEAN" +
                ");"
        jdbcTemplate.execute(createTableQuery)
    }
}
