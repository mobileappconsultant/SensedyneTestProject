package com.android.sensyneapplication.domain.database

import com.android.sensyneapplication.domain.database.DBConstants.ROOMTABLECONSTANTS.FIELD_Email
import com.android.sensyneapplication.domain.database.DBConstants.ROOMTABLECONSTANTS.FIELD_Fax
import com.android.sensyneapplication.domain.database.DBConstants.ROOMTABLECONSTANTS.FIELD_IsPimsManaged
import com.android.sensyneapplication.domain.database.DBConstants.ROOMTABLECONSTANTS.FIELD_Latitude
import com.android.sensyneapplication.domain.database.DBConstants.ROOMTABLECONSTANTS.FIELD_Longitude
import com.android.sensyneapplication.domain.database.DBConstants.ROOMTABLECONSTANTS.FIELD_OrganisationCode
import com.android.sensyneapplication.domain.database.DBConstants.ROOMTABLECONSTANTS.FIELD_OrganisationID
import com.android.sensyneapplication.domain.database.DBConstants.ROOMTABLECONSTANTS.FIELD_ParentODSCode
import com.android.sensyneapplication.domain.database.DBConstants.ROOMTABLECONSTANTS.FIELD_Phone
import com.android.sensyneapplication.domain.database.DBConstants.ROOMTABLECONSTANTS.FIELD_Postcode
import com.android.sensyneapplication.domain.database.DBConstants.ROOMTABLECONSTANTS.FIELD_Website
import java.util.regex.Pattern

object DBConstants {
    object ROOMTABLECONSTANTS {
        const val FIELD_IsPimsManaged = "IsPimsManaged"
        const val FIELD_OrganisationName = "OrganisationName"
        const val FIELD_Fax = "Fax"
        const val FIELD_OrganisationCode = "OrganisationCode"
        const val FIELD_OrganisationID = "OrganisationID"
        const val FIELD_OrganisationType = "OrganisationType"
        const val FIELD_SubType = "SubType"
        const val FIELD_Sector = "Sector"
        const val FIELD_OrganisationStatus = "OrganisationStatus"
        const val FIELD_Email = "Email"
        const val FIELD_Phone = "Phone"
        const val FIELD_ParentName = "ParentName"
        const val FIELD_ParentODSCode = "ParentODSCode"
        const val FIELD_Longitude = "Longitude"
        const val FIELD_Latitude = "Latitude"
        const val FIELD_Postcode = "Postcode"
        const val FIELD_County = "County"
        const val FIELD_City = "City"
        const val FIELD_Address3 = "Address3"
        const val FIELD_Address2 = "Address2"
        const val FIELD_Address1 = "Address1"
        const val FIELD_Website = "Website"
        const val TABLE_NAME = "RoomHospitalItem"
        const val DATABASE_NAME = "Hospitals"
        const val DEFAULT_QUERY_STRING = "SELECT * FROM RoomHospitalItem"
    }
    object TABLE_REGEXS {
        private const val DEFAULT_REGEX = "^[a-zA-Z0-9_ ]*\$"
        private const val REGEX_OrganisationType = DEFAULT_REGEX
        private const val REGEX_OrganisationStatus = DEFAULT_REGEX
        private const val REGEX_OrganisationName = DEFAULT_REGEX
        private const val REGEX_SubType = DEFAULT_REGEX
        private const val REGEX_Sector = DEFAULT_REGEX
        private const val REGEX_ParentName = DEFAULT_REGEX
        private const val REGEX_County = DEFAULT_REGEX
        private const val REGEX_City = DEFAULT_REGEX
        private const val REGEX_Address3 = DEFAULT_REGEX
        private const val REGEX_Address2 = DEFAULT_REGEX
        const val REGEX_Address1 = DEFAULT_REGEX
        const val REGEX_OrganisationCode = "^[\\w.-]{5}"
        const val OrganisationID = "[0-9]{4}"
        const val REGEX_Website =
            "^((https?\\:\\/\\/)?([\\w\\d\\-]+\\.){2,}([\\w\\d]{2,})((\\/[\\w\\d\\-\\.]+)*(\\/[\\w\\d\\-]+\\.[\\w\\d]{3,4}(\\?.*)?)?)?)\$"
        const val REGEX_Email = "[^@ \\t\\r\\n]+@[^@ \\t\\r\\n]+\\.[^@ \\t\\r\\n]+"
        const val REGEX_Phone =
            "^[\\+]?[(]?[0-9]{3}[)]?[-\\s\\.]?[0-9]{3}[-\\s\\.]?[0-9]{4,6}\$"
        const val REGEX_ParentODSCode = "^[\\w.-]{3}"
        const val REGEX_Longitude =
            // "^-?([1]?[1-7][1-9]|[1]?[1-8][0]|[1-9]?[0-9])\\.{1}\\d{1,16}"
            "^[-+]?([1-8]?\\d(\\.\\d+)?|90(\\.0+)?)\$"
        const val REGEX_Latitude = "[-+]?([1-8]?\\d(\\.\\d+)?|90(\\.0+)?)"
        const val REGEX_Postcode =
            "([Gg][Ii][Rr] 0[Aa]{2})|((([A-Za-z][0-9]{1,2})|(([A-Za-z][A-Ha-hJ-Yj-y][0-9]{1,2})|(([A-Za-z][0-9][A-Za-z])|([A-Za-z][A-Ha-hJ-Yj-y][0-9][A-Za-z]?))))\\s?[0-9][A-Za-z]{2})"
        const val REGEX_Fax = REGEX_Phone
        const val REGEX_IsPimsManaged = "^(?i)(true|false)\$"
    }

    fun retrieveAllRegexAsList(): ArrayList<Pair<String, Pattern>> =
        arrayListOf<Pair<String, Pattern>>().apply {
            add(Pair(FIELD_IsPimsManaged, Pattern.compile(TABLE_REGEXS.REGEX_IsPimsManaged)))
            add(Pair(FIELD_Fax, Pattern.compile(TABLE_REGEXS.REGEX_Fax)))
            add(Pair(FIELD_Postcode, Pattern.compile(TABLE_REGEXS.REGEX_Postcode)))
            add(Pair(FIELD_Latitude, Pattern.compile(TABLE_REGEXS.REGEX_Latitude)))
            add(Pair(FIELD_Longitude, Pattern.compile(TABLE_REGEXS.REGEX_Longitude)))
            add(Pair(FIELD_ParentODSCode, Pattern.compile(TABLE_REGEXS.REGEX_ParentODSCode)))
            add(Pair(FIELD_Email, Pattern.compile(TABLE_REGEXS.REGEX_Email)))
            add(Pair(FIELD_Website, Pattern.compile(TABLE_REGEXS.REGEX_Website)))
            add(Pair(FIELD_Phone, Pattern.compile(TABLE_REGEXS.REGEX_Phone)))
            add(Pair(FIELD_OrganisationCode, Pattern.compile(TABLE_REGEXS.REGEX_OrganisationCode)))
            add(Pair(FIELD_OrganisationID, Pattern.compile(TABLE_REGEXS.OrganisationID)))
            // add(Pair(FIELD_Address1, Pattern.compile(TABLE_REGEXS.REGEX_Address1)))
        }
}
