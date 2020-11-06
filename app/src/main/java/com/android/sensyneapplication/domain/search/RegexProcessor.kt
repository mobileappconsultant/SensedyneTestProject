package com.android.sensyneapplication.domain.search
interface RegexProcessor {

    /**
     * This method is the basis of any regex validation
     * It takes a string that represents a generic search that has been fully typed in
     * or in being typed in. The processor runs a validation process and
     * returns a list of database tables that are being queried
     *
     *
     */
    fun process(searchEntry: String): List<String>
}
