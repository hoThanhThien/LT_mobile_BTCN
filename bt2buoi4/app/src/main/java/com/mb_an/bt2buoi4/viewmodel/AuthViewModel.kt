import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.mb_an.bt2buoi4.AuthState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class AuthViewModel : ViewModel() {
    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState = _authState.asStateFlow()

    fun login(email: String, password: String) {
        _authState.value = AuthState.Loading
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                _authState.value = AuthState.Success(it.user?.uid ?: "")
            }
            .addOnFailureListener {
                _authState.value = AuthState.Error(it.message ?: "Login failed")
            }
    }

    fun register(email: String, password: String) {
        _authState.value = AuthState.Loading
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                _authState.value = AuthState.Success(it.user?.uid ?: "")
            }
            .addOnFailureListener {
                _authState.value = AuthState.Error(it.message ?: "Register failed")
            }
    }

    fun logout() {
        FirebaseAuth.getInstance().signOut()
        _authState.value = AuthState.Idle
    }
}


