package com.android.sensyneapplication.domain.mapper

import com.android.sensyneapplication.domain.model.RoomHospitalItem
import com.android.sensyneapplication.framework.domain.model.HospitalItem

class HospitalResponseModelToRoomItemMapper : ModelMapper<HospitalItem, RoomHospitalItem> {
    override fun mapFromModel(hospitalItem: HospitalItem): RoomHospitalItem {
        return RoomHospitalItem(
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
            hospitalItem.Phone,
            hospitalItem.Postcode,
            hospitalItem.Sector,
            hospitalItem.SubType,
            hospitalItem.Website
        )
    }
}
