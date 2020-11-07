package com.android.sensyneapplication.presentation

sealed class SearchAction {
    class UserTypingAction(val searchString: String?) : SearchAction()

    class NoSearchStringAction() : SearchAction()

}