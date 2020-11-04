package com.android.sensyneapplication.domain

import java.util.regex.Pattern

typealias RoomQueryRegex = Triple<String, String, String>

class QueryBuilder {
    var bla = mutableListOf<RoomQueryRegex>()

    var emailPattern = Pattern.compile(DBConstants.TABLE_REGEXS.REGEX_Email)
}
// column name //regex
