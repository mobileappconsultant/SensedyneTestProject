package com.android.sensyneapplication.domain.search

import com.android.sensyneapplication.domain.database.DBConstants
import junit.framework.TestCase.assertTrue
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class SearchEntryRegexProcessorTest {

    var searchEntryRegexProcessor: SearchEntryRegexProcessor = SearchEntryRegexProcessor()

    @Test
    fun checkOrganizationCodeMatches_Pass() {

        val organizationCode = "NLL20"
        assertEquals(
            searchEntryRegexProcessor.process(organizationCode).first(),
            DBConstants.ROOMTABLECONSTANTS.FIELD_OrganisationCode
        )
    }

    @Test
    fun checkIsPimsManagedMatches_Pass() {
        var isPim = true
        assertTrue(
            searchEntryRegexProcessor.process(isPim.toString())
                .any { it == DBConstants.ROOMTABLECONSTANTS.FIELD_IsPimsManaged || it == DBConstants.ROOMTABLECONSTANTS.FIELD_IsPimsManaged }
        )
        isPim = false
        assertTrue(
            searchEntryRegexProcessor.process(isPim.toString())
                .any { it == DBConstants.ROOMTABLECONSTANTS.FIELD_IsPimsManaged || it == DBConstants.ROOMTABLECONSTANTS.FIELD_IsPimsManaged }
        )
    }

    @Test
    fun checkPostCodeMatches_Pass() {
        val postCodeString = "NW10 4HB"
        assertEquals(
            searchEntryRegexProcessor.process(postCodeString).first(),
            DBConstants.ROOMTABLECONSTANTS.FIELD_Postcode
        )
    }

    @Test
    fun checkPostCodeMatches_Fail() {
        val postCodeString = "NW10 4H"
        assertEquals(
            searchEntryRegexProcessor.process(postCodeString).size,
            0
        )
    }

    @Test
    fun checkWebsiteMatches_Pass() {
        val websiteString = "http://www.sussexcommunity.nhs.uk/services"
        assertEquals(
            searchEntryRegexProcessor.process(websiteString).first(),
            DBConstants.ROOMTABLECONSTANTS.FIELD_Website
        )
    }

    @Test
    fun checkWebsiteMatches_Fail() {
        val websiteString = "http://www."
        assertEquals(
            searchEntryRegexProcessor.process(websiteString).size,
            0
        )
    }

    @Test
    fun checkEmailMatches_Pass() {
        val email = "aa@aa.com"
        assertEquals(
            searchEntryRegexProcessor.process(email).first(),
            DBConstants.ROOMTABLECONSTANTS.FIELD_Email
        )
    }

    @Test
    fun checkEmailMatches_Fail() {
        val email = "aa@aa"
        assertEquals(
            searchEntryRegexProcessor.process(email).size,
            0
        )
    }

    @Test
    fun checkPhoneMatches_Pass() {
        val phone = "07401227777"
        assertTrue(
            searchEntryRegexProcessor.process(phone)
                .any { it == DBConstants.ROOMTABLECONSTANTS.FIELD_Phone || it == DBConstants.ROOMTABLECONSTANTS.FIELD_Fax }
        )
    }

    @Test
    fun checkPhoneMatches_Fail() {
        val phone = "07401q227777"
        assertEquals(
            searchEntryRegexProcessor.process(phone).size,
            0
        )
    }

    @Test
    fun checkLongitudeMatches_Pass() {
        val phone = "-0.5006316304206848"
        assertTrue(
            searchEntryRegexProcessor.process(phone)
                .any { it == DBConstants.ROOMTABLECONSTANTS.FIELD_Latitude || it == DBConstants.ROOMTABLECONSTANTS.FIELD_Longitude }
        )
    }

    @Test
    fun checkLatitudeMatches_Pass() {
        val latitude = "50.80788040161133"
        assertTrue(
            searchEntryRegexProcessor.process(latitude)
                .any { it == DBConstants.ROOMTABLECONSTANTS.FIELD_Latitude || it == DBConstants.ROOMTABLECONSTANTS.FIELD_Longitude }
        )
    }

    @Test
    fun checkParentODSCodeMatches_Pass() {
        val parentOdsCode = "RDR"
        assertTrue(
            searchEntryRegexProcessor.process(parentOdsCode)
                .any { it == DBConstants.ROOMTABLECONSTANTS.FIELD_ParentODSCode }
        )
    }

    @Test
    fun checkParentODSCodeMatches_Fail() {
        val parentOdsCode = "RDwwwwwwR"
        assertEquals(
            searchEntryRegexProcessor.process(parentOdsCode).size,
            0
        )
    }

    @Test
    fun checkOrganiationIdMatches_Pass() {
        val organizationId = "16131"
        assertEquals(
            searchEntryRegexProcessor.process(organizationId).first(),
            DBConstants.ROOMTABLECONSTANTS.FIELD_OrganisationID
        )
    }

    @Test
    fun checkOrganiationCodeMatches_Fail() {
        val organizationCode = "1622222211"
        searchEntryRegexProcessor.process(organizationCode)
            .any { it == DBConstants.ROOMTABLECONSTANTS.FIELD_OrganisationCode }
    }
}
