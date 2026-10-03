package com.mohsiniqbalcui.kmpinspector.sample.data

import com.mohsiniqbalcui.kmpinspector.Inspector
import com.mohsiniqbalcui.kmpinspector.domain.model.DbColumn
import com.mohsiniqbalcui.kmpinspector.domain.model.DbInfo
import com.mohsiniqbalcui.kmpinspector.domain.model.DbTable
import com.mohsiniqbalcui.kmpinspector.domain.model.DbValue
import com.mohsiniqbalcui.kmpinspector.sample.data.local.NewsDatabase

/**
 * Mirrors the real Room contents into the inspector's Database tab.
 *
 * The library deliberately has no database driver of its own — it shows whatever the host app hands
 * it — so this is the mapping a consuming app writes once.
 */
internal suspend fun publishDatabaseToInspector(database: NewsDatabase) {
    val articles = database.articles().all()
    val users = database.users().all()

    Inspector.setDatabase(
        info = DbInfo(
            fileName = "news.db",
            engine = "Room",
            sizeLabel = "${articles.size + users.size} rows",
        ),
        tables = listOf(
            DbTable(
                name = "articles",
                columns = listOf(
                    DbColumn("id", "INTEGER PK"),
                    DbColumn("title", "TEXT"),
                    DbColumn("newsSite", "TEXT"),
                    DbColumn("publishedAtMillis", "INTEGER"),
                    DbColumn("summary", "TEXT"),
                    DbColumn("imageUrl", "TEXT NULL"),
                    DbColumn("url", "TEXT"),
                ),
                rows = articles.map { a ->
                    listOf(
                        DbValue.Number(a.id.toString()),
                        DbValue.Text(a.title),
                        DbValue.Text(a.newsSite),
                        DbValue.Number(a.publishedAtMillis.toString()),
                        DbValue.Text(a.summary),
                        a.imageUrl?.let { DbValue.Text(it) } ?: DbValue.Null,
                        DbValue.Text(a.url),
                    )
                },
            ),
            DbTable(
                name = "users",
                columns = listOf(
                    DbColumn("id", "INTEGER PK"),
                    DbColumn("displayName", "TEXT"),
                    DbColumn("email", "TEXT"),
                    DbColumn("lastLoginMillis", "INTEGER"),
                ),
                rows = users.map { u ->
                    listOf(
                        DbValue.Number(u.id.toString()),
                        DbValue.Text(u.displayName),
                        DbValue.Text(u.email),
                        DbValue.Number(u.lastLoginMillis.toString()),
                    )
                },
            ),
        ),
    )
}
