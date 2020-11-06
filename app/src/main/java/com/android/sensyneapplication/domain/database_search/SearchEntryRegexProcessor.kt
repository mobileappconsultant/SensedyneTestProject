package com.android.sensyneapplication.domain.database_search

import com.android.sensyneapplication.domain.database.DBConstants.retrieveAllRegexAsList

class SearchEntryRegexProcessor : RegexProcessor {

    override fun process(searchEntry: String): List<String> {
        var bla = retrieveAllRegexAsList()
            .filter { it.second.matcher(searchEntry).matches() }.map { it.first }.toList()
        return bla
    }
}
