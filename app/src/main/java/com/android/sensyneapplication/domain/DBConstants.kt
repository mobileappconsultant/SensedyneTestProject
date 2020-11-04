package com.android.sensyneapplication.domain

object DBConstants {
    object ROOMTABLECONSTANTS {
        const val FIELD_IsPimsManaged = "IsPimsManaged"
        const val FIELD_OrganisationName = "OrganisationName"
        const val FIELD_Fax = "Fax"
        const val FIELD_OrganisationCode = "OrganisationCode"
        const val OrganisationID = "OrganisationID"
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
    }
    object TABLE_REGEXS {
        const val DEFAULT_REGEX = "^[a-zA-Z0-9_ ]*\$"
        const val REGEX_OrganisationCode = "^[\\w.-]{5}"
        const val OrganisationID = "[0-9]{4}"
        const val REGEX_Website = "^((https?\\:\\/\\/)?([\\w\\d\\-]+\\.){2,}([\\w\\d]{2,})((\\/[\\w\\d\\-\\.]+)*(\\/[\\w\\d\\-]+\\.[\\w\\d]{3,4}(\\?.*)?)?)?)\$"
        const val REGEX_Email = "[^@ \\t\\r\\n]+@[^@ \\t\\r\\n]+\\.[^@ \\t\\r\\n]+"
        const val REGEX_Phone = "^[\\+]?[(]?[0-9]{3}[)]?[-\\s\\.]?[0-9]{3}[-\\s\\.]?[0-9]{4,6}\$"
        const val REGEX_ParentODSCode = "^[\\w.-]{3}"
        const val REGEX_Longitude = "^-?([1]?[1-7][1-9]|[1]?[1-8][0]|[1-9]?[0-9])\\.{1}\\d{1,16}"
        const val REGEX_Latitude = "[-+]?([1-8]?\\d(\\.\\d+)?|90(\\.0+)?)"
        const val REGEX_Postcode = "^([A-Z][A-HJ-Y]?\\d[A-Z\\d]? ?\\d[A-Z]{2}|GIR ?0A{2})\$"
        const val REGEX_OrganisationType = DEFAULT_REGEX
        const val REGEX_OrganisationStatus = DEFAULT_REGEX
        const val REGEX_OrganisationName = DEFAULT_REGEX
        const val REGEX_SubType = DEFAULT_REGEX
        const val REGEX_Sector = DEFAULT_REGEX
        const val REGEX_Fax = REGEX_Phone
        const val REGEX_ParentName = DEFAULT_REGEX
        const val REGEX_County = DEFAULT_REGEX
        const val REGEX_City = DEFAULT_REGEX
        const val REGEX_Address3 = DEFAULT_REGEX
        const val REGEX_Address2 = DEFAULT_REGEX
        const val REGEX_Address1 = DEFAULT_REGEX
        const val REGEX_IsPimsManaged = "^(?i)(true|false)\$"
    }
}
