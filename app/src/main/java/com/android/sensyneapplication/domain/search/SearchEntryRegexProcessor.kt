package com.android.sensyneapplication.domain.search

import com.android.sensyneapplication.domain.database.DBConstants.retrieveAllRegexAsList

class SearchEntryRegexProcessor : RegexProcessor {

    override fun process(searchEntry: String): List<String> {
        return retrieveAllRegexAsList()
            .filter { it.second.matcher(searchEntry).matches() }.map { it.first }.toList()
    }
}
