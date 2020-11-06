package com.android.sensyneapplication

import com.android.sensyneapplication.domain.mapper.HospitalResponseModelToRoomItemMapper
import junit.framework.TestCase.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class HospitalResponseModelMapperTest {

    private val mapper = HospitalResponseModelToRoomItemMapper()

    @Test
    fun mapFromModelMapsData() {
        val model = HospitalDataFactory.makeSingleHospitalEntry()
        val entity = mapper.mapFromModel(model)
        assertEquals(model.Address1, entity.Address1)
        assertEquals(model.Address2, entity.Address2)
        assertEquals(model.Address3, entity.Address3)
        assertEquals(model.City, entity.City)
        assertEquals(model.County, entity.County)
        assertEquals(model.OrganisationCode, entity.OrganisationCode)
        assertEquals(model.OrganisationID, entity.OrganisationID)
        assertEquals(model.Email, entity.Email)
        assertEquals(model.ParentName, entity.ParentName)
        assertEquals(model.Latitude, entity.Latitude)
        assertEquals(model.Longitude, entity.Longitude)
        assertEquals(model.Postcode, entity.Postcode)
        assertEquals(model.Sector, entity.Sector)
        assertEquals(model.SubType, entity.SubType)
        assertEquals(model.Website, entity.Website)
    }
}
