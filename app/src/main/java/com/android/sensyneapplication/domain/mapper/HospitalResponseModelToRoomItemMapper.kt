package com.android.sensyneapplication.domain.mapper

import com.android.sensyneapplication.domain.model.RoomHospitalItem
import com.android.sensyneapplication.framework.domain.model.HospitalItem

class HospitalResponseModelToRoomItemMapper : ModelMapper<HospitalItem, RoomHospitalItem> {
    override fun mapFromModel(model: HospitalItem): RoomHospitalItem {
        return RoomHospitalItem(
            model.Address1,
            model.Address2,
            model.Address3,
            model.City,
            model.County,
            model.Email,
            model.Fax,
            model.IsPimsManaged,
            model.Latitude,
            model.Longitude,
            model.OrganisationCode,
            model.OrganisationID,
            model.OrganisationName,
            model.OrganisationStatus,
            model.OrganisationType,
            model.ParentName,
            model.ParentODSCode,
            model.Phone,
            model.Postcode,
            model.Sector,
            model.SubType,
            model.Website
        )
    }
}
