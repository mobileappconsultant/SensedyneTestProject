package com.android.sensyneapplication.framework.domain.model

import com.android.sensyneapplication.domain.DBConstants
import com.squareup.moshi.FromJson
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class HospitalItem(
    @Json(name = DBConstants.ROOMTABLECONSTANTS.FIELD_Address1)
    val Address1: String?,
    @Json(name = DBConstants.ROOMTABLECONSTANTS.FIELD_Address2)
    val Address2: String?,
    @Json(name = DBConstants.ROOMTABLECONSTANTS.FIELD_Address3)
    val Address3: String?,
    @Json(name = DBConstants.ROOMTABLECONSTANTS.FIELD_City)
    val City: String?,
    @Json(name = DBConstants.ROOMTABLECONSTANTS.FIELD_County)
    val County: String?,
    @Json(name = DBConstants.ROOMTABLECONSTANTS.FIELD_Email)
    val Email: String?,
    @Json(name = DBConstants.ROOMTABLECONSTANTS.FIELD_Fax)
    val Fax: String?,
    @Json(name = DBConstants.ROOMTABLECONSTANTS.FIELD_IsPimsManaged)
    val IsPimsManaged: String?,
    @Json(name = DBConstants.ROOMTABLECONSTANTS.FIELD_Latitude)
    val Latitude: String?,
    @Json(name = DBConstants.ROOMTABLECONSTANTS.FIELD_Longitude)
    val Longitude: String?,
    @Json(name = DBConstants.ROOMTABLECONSTANTS.FIELD_OrganisationCode)
    val OrganisationCode: String?,
    @Json(name = DBConstants.ROOMTABLECONSTANTS.OrganisationID)
    val OrganisationID: String?,
    @Json(name = DBConstants.ROOMTABLECONSTANTS.FIELD_OrganisationName)
    val OrganisationName: String?,
    @Json(name = DBConstants.ROOMTABLECONSTANTS.FIELD_OrganisationStatus)
    val OrganisationStatus: String?,
    @Json(name = DBConstants.ROOMTABLECONSTANTS.FIELD_OrganisationType)
    val OrganisationType: String?,
    @Json(name = DBConstants.ROOMTABLECONSTANTS.FIELD_ParentName)
    val ParentName: String?,
    @Json(name = DBConstants.ROOMTABLECONSTANTS.FIELD_ParentODSCode)
    val ParentODSCode: String?,
    @Json(name = DBConstants.ROOMTABLECONSTANTS.FIELD_Phone)
    val Phone: String?,
    @Json(name = DBConstants.ROOMTABLECONSTANTS.FIELD_Postcode)
    val Postcode: String?,
    @Json(name = DBConstants.ROOMTABLECONSTANTS.FIELD_Sector)
    val Sector: String?,
    @Json(name = DBConstants.ROOMTABLECONSTANTS.FIELD_SubType)
    val SubType: String?,
    @Json(name = DBConstants.ROOMTABLECONSTANTS.FIELD_Website)
    val Website: String?
) {
    // this is messy
    // TODO IMPROVE THIS
    class HospitalItemAdapter {
        @FromJson
        fun fromJson(hospitalItem: HospitalItem): HospitalItem {

            return HospitalItem(
                hospitalItem.Address1,
                hospitalItem.Address2,
                hospitalItem.Address3,
                hospitalItem.City,
                hospitalItem.County,
                hospitalItem.Email,
                hospitalItem.Fax,
                hospitalItem.IsPimsManaged,
                hospitalItem.Latitude,
                hospitalItem.Longitude,
                hospitalItem.OrganisationCode,
                hospitalItem.OrganisationID,
                hospitalItem.OrganisationName,
                hospitalItem.OrganisationStatus,
                hospitalItem.OrganisationType,
                hospitalItem.ParentName,
                hospitalItem.ParentODSCode,
                hospitalItem.Phone?.replace("\\s".toRegex(), ""),
                hospitalItem.Postcode,
                hospitalItem.Sector,
                hospitalItem.SubType,
                hospitalItem.Website
            )
        }
    }
}
