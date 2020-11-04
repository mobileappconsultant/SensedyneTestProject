
package com.android.sensyneapplication

import android.app.Activity
import androidx.annotation.IdRes
import androidx.annotation.StringRes
import androidx.test.espresso.Espresso
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.RootMatchers
import androidx.test.espresso.matcher.ViewMatchers
import org.hamcrest.CoreMatchers
import org.hamcrest.Matchers

abstract class BaseScreenWrapper<T : BaseScreenWrapper<T>> {

    private var activityContext: Activity? = null

    fun checkIsDisplayed(@IdRes vararg viewIds: Int): T {
        for (viewId in viewIds) {
            Espresso.onView(ViewMatchers.withId(viewId))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        }
        return this as T
    }

    fun checkIsHidden(@IdRes vararg viewIds: Int): T {
        for (viewId in viewIds) {
            Espresso.onView(ViewMatchers.withId(viewId))
                .check(ViewAssertions.matches(CoreMatchers.not(ViewMatchers.isDisplayed())))
        }
        return this as T
    }

    fun checkViewHasText(@IdRes viewId: Int, expected: String): T {
        Espresso.onView(ViewMatchers.withId(viewId))
            .check(ViewAssertions.matches(ViewMatchers.withText(expected)))
        return this as T
    }

    fun checkViewHasText(@IdRes viewId: Int, @StringRes messageResId: Int): T {
        Espresso.onView(ViewMatchers.withId(viewId))
            .check(ViewAssertions.matches(ViewMatchers.withText(messageResId)))
        return this as T
    }

    fun checkViewHasHint(@IdRes viewId: Int, @StringRes messageResId: Int): T {
        Espresso.onView(ViewMatchers.withId(viewId))
            .check(ViewAssertions.matches(ViewMatchers.withHint(messageResId)))
        return this as T
    }

    /**
     * Find a view with a specific view id
     * @param viewId The id of the view to matched
     */
    private fun findWithId(viewId: Int): ViewInteraction {
        return Espresso.onView(
            Matchers.allOf(
                ViewMatchers.withId(viewId)
            )
        )
    }
    /**
     * Perform scrollTo action for a specific view id when parent is a ScrollView
     * View can be displayed or off screen and action will succeed
     * @param viewId The id of the view to matched
     */
    fun scrollTo(viewId: Int) {
        findWithId(viewId).perform(ViewActions.scrollTo())
    }

    fun clickOkOnView(@IdRes viewId: Int): T {
        Espresso.onView(ViewMatchers.withId(viewId))
            .perform(ViewActions.click())
        return this as T
    }

    fun enterTextIntoView(@IdRes viewId: Int, text: String): T {
        Espresso.onView(ViewMatchers.withId(viewId))
            .perform(ViewActions.typeText(text))
        return this as T
    }

    fun provideActivityContext(activityContext: Activity): T {
        this.activityContext = activityContext
        return this as T
    }

    fun checkDialogWithTextIsDisplayed(@StringRes messageResId: Int): T {
        Espresso.onView(ViewMatchers.withText(messageResId))
            .inRoot(RootMatchers.withDecorView(CoreMatchers.not(activityContext!!.window.decorView)))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        return this as T
    }

    fun swipeLeftOnView(@IdRes viewId: Int): T {
        Espresso.onView(ViewMatchers.withId(viewId))
            .perform(ViewActions.swipeLeft())
        return this as T
    }

    companion object {

        fun <T : BaseScreenWrapper<*>> withRobot(screenRobotClass: Class<T>?): T {
            if (screenRobotClass == null) {
                throw IllegalArgumentException("instance class == null")
            }

            try {
                return screenRobotClass.newInstance()
            } catch (iae: IllegalAccessException) {
                throw RuntimeException("IllegalAccessException", iae)
            } catch (ie: InstantiationException) {
                throw RuntimeException("InstantiationException", ie)
            }
        }
    }
}
