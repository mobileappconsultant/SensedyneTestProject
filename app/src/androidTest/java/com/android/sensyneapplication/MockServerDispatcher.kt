package com.android.sensyneapplication

import android.content.Context
import android.util.Log
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest
import java.net.HttpURLConnection

class MockServerDispatcher(var context: Context) {

    /**
     * Return ok response from mock server
     */
    internal inner class RequestDispatcher : Dispatcher() {
        override fun dispatch(request: RecordedRequest): MockResponse {
            Log.i("MOckserver", request.toString())
            when {
                request.path!!.contains(HOSPITAL_PATH) -> {
                    Log.i("MOckserver", request.toString())
                    val mockResponseAvailability = MockResponse()
                    val hospitalResponse = String().readAssets(
                        context,
                        MOCK_RESPONSE_PATH_AVAILABILITY
                    )

                    mockResponseAvailability.setResponseCode(HttpURLConnection.HTTP_OK)
                        .setBody(hospitalResponse.toString())
                    return mockResponseAvailability
                }
            }
            return MockResponse()
        }
    }

    /**
     * Return error response from mock server
     */
    internal inner class ErrorDispatcher : Dispatcher() {
        override fun dispatch(request: RecordedRequest): MockResponse {
            return MockResponse().setResponseCode(HttpURLConnection.HTTP_BAD_REQUEST)
        }
    }

    companion object {
        const val HOSPITAL_PATH = "/hospitals.json"
        const val MOCK_RESPONSE_PATH_AVAILABILITY = "mock_responses/hospital_response.json"

        @JvmStatic
        var hasAvailability: Boolean = true
    }
}
