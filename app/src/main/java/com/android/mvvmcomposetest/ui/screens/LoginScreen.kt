import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.android.mvvmcomposetest.R
import com.android.mvvmcomposetest.ui.widgets.LoginField
import com.android.mvvmcomposetest.ui.widgets.PasswordField

@Composable
fun LoginForm(
    modifier: Modifier = Modifier, onLogin: (String, String) -> Unit = { _, _ -> }
) {
    var userName by remember { mutableStateOf(TextFieldValue("")) }
    var password by remember { mutableStateOf(TextFieldValue("")) }
    Surface {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .fillMaxSize()
                .padding(horizontal = 10.dp)
        ) {
            LoginField(
                value = userName.text, label = stringResource(id = R.string.login), onChange = {
                    userName = TextFieldValue(it, selection = TextRange(it.length))
                }, modifier = modifier.fillMaxWidth().testTag("username")
            )
            Spacer(modifier = modifier.height(8.dp))
            PasswordField(
                value = password.text,
                label = stringResource(id = R.string.password),
                onChange = {
                    password = TextFieldValue(it, selection = TextRange(it.length))
                },
                submit = { onLogin(userName.text, password.text) },
                modifier = modifier.fillMaxWidth().testTag("password")
            )

            Spacer(modifier = modifier.height(16.dp))
            Button(modifier = modifier.fillMaxWidth(), content = {
                Text("Login")
            }, enabled = true, shape = RoundedCornerShape(8.dp), onClick = {
                onLogin(userName.text, password.text)
            })
        }
    }
}

@Preview
@Composable
private fun PreviewLoginForm() {
    LoginForm(Modifier)
}