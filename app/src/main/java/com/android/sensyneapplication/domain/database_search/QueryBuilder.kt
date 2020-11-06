package com.android.sensyneapplication.domain.database_search

import androidx.sqlite.db.SimpleSQLiteQuery
import com.android.sensyneapplication.domain.database.DBConstants

class QueryBuilder(val searchEntryRegexProcessor: SearchEntryRegexProcessor) {

    fun generateQuery(queryString: String): SimpleSQLiteQuery {
        var queryTableHits = searchEntryRegexProcessor.process(queryString)

        return if (queryTableHits.isEmpty()) {
            SimpleSQLiteQuery(DBConstants.ROOMTABLECONSTANTS.DEFAULT_QUERY_STRING)
        } else {
            val SQL_WHERE_CLAUSE = " WHERE "
            val SQL_LIKE_CLAUSE = " LIKE "

            var finalQueryString =
                DBConstants.ROOMTABLECONSTANTS.DEFAULT_QUERY_STRING.plus(SQL_WHERE_CLAUSE)
                    .plus(queryTableHits.first())
                    .plus(SQL_LIKE_CLAUSE)
                    .plus("'$queryString%'")
            return SimpleSQLiteQuery(finalQueryString)
        }
    }
}
