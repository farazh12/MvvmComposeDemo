package com.android.mvvmcomposetest

import LoginForm
import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.printToLog
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class LoginTest {
    @get:Rule
    val rule = createComposeRule()

    @Before
    fun setUp(){
        rule.setContent {
            LoginForm()
        }

    }

    fun SemanticsNodeInteraction.imeActionValue(): String?{
        for((key, value) in fetchSemanticsNode().config){
            if(key.name == "ImeAction"){
                return value?.toString()
            }
        }
        return null
    }

    fun SemanticsNodeInteraction.currentText(): String?{
        for((key, value) in fetchSemanticsNode().config){
            if(key.name == "EditableText"){
                return value?.toString()
            }
        }
        return null
    }

    @Test
    fun onLogin_login_status_successfullyLogin(){
        val username = rule.onNodeWithTag("username")
        val password = rule.onNodeWithTag("password")
        username.performTextInput("User")
        password.performTextInput("test123")

        assertEquals("Next", username.imeActionValue())
        assertEquals("User", username.currentText())

//        username.printToLog("Login")
//        password.printToLog("Login")
        assertEquals("Done", password.imeActionValue())

        assertEquals("•••••••", password.currentText())


    }
}