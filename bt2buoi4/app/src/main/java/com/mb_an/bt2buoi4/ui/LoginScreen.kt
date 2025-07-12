import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.mb_an.bt2buoi4.AuthState


@Composable
fun LoginScreen(viewModel: AuthViewModel, onLoginSuccess: () -> Unit, onGoToRegister: () -> Unit) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val authState by viewModel.authState.collectAsState()

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Login", fontSize = 28.sp)
        OutlinedTextField(value = email, onValueChange = { email = it }, label = { Text("Email") })
        OutlinedTextField(value = password, onValueChange = { password = it }, label = { Text("Password") }, visualTransformation = PasswordVisualTransformation())
        Button(onClick = { viewModel.login(email, password) }, modifier = Modifier.fillMaxWidth()) {
            Text("Login")
        }
        TextButton(onClick = onGoToRegister) {
            Text("Don't have an account? Register")
        }
        when (authState) {
            is AuthState.Loading -> CircularProgressIndicator()
            is AuthState.Success -> onLoginSuccess()
            is AuthState.Error -> Text((authState as AuthState.Error).message, color = Color.Red)
            else -> {}
        }
    }
}
