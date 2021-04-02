package com.swaptech.habitstwo.uitests

import androidx.fragment.app.Fragment
import androidx.fragment.app.testing.launchFragment
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.agoda.kakao.common.views.KBaseView
import com.agoda.kakao.delegate.ViewInteractionDelegate
import com.agoda.kakao.screen.Screen
import com.agoda.kakao.text.KButton
import com.agoda.kakao.text.KTextView
import com.swaptech.habitstwo.R
import com.swaptech.habitstwo.actionwithhabit.ActionsWithHabitFragmentViewModel
import com.swaptech.habitstwo.listhabits.FragmentWithViewModel
import com.swaptech.habitstwo.navigation.AboutFragment
import com.swaptech.habitstwo.navigation.MainActivity
import kotlinx.android.synthetic.main.fragment_about.view.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(AndroidJUnit4::class)
class AboutFragmentUITest: Screen<AboutFragmentUITest>() {
    @JvmField
    @Rule
    val rule = ActivityTestRule(MainActivity::class.java)

    inner class AboutFragmentScreen: Screen<AboutFragmentScreen>() {
        val versionText = KTextView {
            withId(R.id.version_text_view)
        }

    }

    val screen = AboutFragmentScreen()

    @Test
    fun test() {

        val scenario = launchFragmentInContainer<AboutFragment>()
        scenario.moveToState(Lifecycle.State.RESUMED)
        screen {
            versionText {
                isVisible()
                hasText(R.string.version_of_app)
            }
        }

    }
}
