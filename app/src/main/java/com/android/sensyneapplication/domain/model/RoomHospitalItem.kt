package com.android.sensyneapplication.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.android.sensyneapplication.domain.database.DBConstants
import java.util.UUID

@Entity(tableName = DBConstants.ROOMTABLECONSTANTS.TABLE_NAME)
class RoomHospitalItem(
    @field:ColumnInfo(name = DBConstants.ROOMTABLECONSTANTS.FIELD_Address1)
    val Address1: String?,
    @field:ColumnInfo(name = DBConstants.ROOMTABLECONSTANTS.FIELD_Address2)
    val Address2: String?,
    @field:ColumnInfo(name = DBConstants.ROOMTABLECONSTANTS.FIELD_Address3)
    val Address3: String?,
    @field:ColumnInfo(name = DBConstants.ROOMTABLECONSTANTS.FIELD_City)
    val City: String?,
    @field:ColumnInfo(name = DBConstants.ROOMTABLECONSTANTS.FIELD_County)
    val County: String?,
    @field:ColumnInfo(name = DBConstants.ROOMTABLECONSTANTS.FIELD_Email)
    val Email: String?,
    @field:ColumnInfo(name = DBConstants.ROOMTABLECONSTANTS.FIELD_Fax)
    val Fax: String?,
    @field:ColumnInfo(name = DBConstants.ROOMTABLECONSTANTS.FIELD_IsPimsManaged)
    val IsPimsManaged: String?,
    @field:ColumnInfo(name = DBConstants.ROOMTABLECONSTANTS.FIELD_Latitude)
    val Latitude: String?,
    @field:ColumnInfo(name = DBConstants.ROOMTABLECONSTANTS.FIELD_Longitude)
    val Longitude: String?,
    @field:ColumnInfo(name = DBConstants.ROOMTABLECONSTANTS.FIELD_OrganisationCode)
    val OrganisationCode: String?,
    @field:ColumnInfo(name = DBConstants.ROOMTABLECONSTANTS.FIELD_OrganisationID)
    val OrganisationID: String?,
    @field:ColumnInfo(name = DBConstants.ROOMTABLECONSTANTS.FIELD_OrganisationName)
    val OrganisationName: String?,
    @field:ColumnInfo(name = DBConstants.ROOMTABLECONSTANTS.FIELD_OrganisationStatus)
    val OrganisationStatus: String?,
    @field:ColumnInfo(name = DBConstants.ROOMTABLECONSTANTS.FIELD_OrganisationType)
    val OrganisationType: String?,
    @field:ColumnInfo(name = DBConstants.ROOMTABLECONSTANTS.FIELD_ParentName)
    val ParentName: String?,
    @field:ColumnInfo(name = DBConstants.ROOMTABLECONSTANTS.FIELD_ParentODSCode)
    val ParentODSCode: String?,
    @field:ColumnInfo(name = DBConstants.ROOMTABLECONSTANTS.FIELD_Phone)
    val Phone: String?,
    @field:ColumnInfo(name = DBConstants.ROOMTABLECONSTANTS.FIELD_Postcode)
    val Postcode: String?,
    @field:ColumnInfo(name = DBConstants.ROOMTABLECONSTANTS.FIELD_Sector)
    val Sector: String?,
    @field:ColumnInfo(name = DBConstants.ROOMTABLECONSTANTS.FIELD_SubType)
    val SubType: String?,
    @field:ColumnInfo(name = DBConstants.ROOMTABLECONSTANTS.FIELD_Website)
    val Website: String?,
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
)
