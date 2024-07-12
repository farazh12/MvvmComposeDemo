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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.android.mvvmcomposetest.R
import com.android.mvvmcomposetest.ui.widgets.LabeledCheckbox
import com.android.mvvmcomposetest.ui.widgets.LoginField
import com.android.mvvmcomposetest.ui.widgets.PasswordField

@Composable
fun LoginForm(modifier: Modifier = Modifier) {
    Surface {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 30.dp)
        ) {
            LoginField(
                value = stringResource(id = R.string.login),
                onChange = { },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = modifier.height(8.dp))
            PasswordField(value = "password",
                onChange = { },
                submit = { },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))
            LabeledCheckbox(
                label = "Remember Me", onCheckChanged = { }, isChecked = false
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                modifier = Modifier.fillMaxWidth(),
                content = {
                    Text("Login")
                },
                enabled = true,
                shape = RoundedCornerShape(8.dp),
                onClick = {

                }
            )
        }
    }
}

@Preview
@Composable
private fun PreviewLoginForm() {
    LoginForm(Modifier)
}