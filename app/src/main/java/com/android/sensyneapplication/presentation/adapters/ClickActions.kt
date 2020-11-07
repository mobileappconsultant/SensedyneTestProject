package com.android.sensyneapplication.presentation.adapters

import com.android.sensyneapplication.framework.domain.model.HospitalItem

sealed class ClickActions {

    class MainAction(val hospitalItem: HospitalItem?) : ClickActions()

    class PhoneAction(val phoneNumber: String?) : ClickActions()

    class WebAction(val webAddress: String?) : ClickActions()

    class EmailAction(val emailAddress: String?) : ClickActions()

    class LocationAction(val latitude: String?, val longitude: String?) : ClickActions()
}
