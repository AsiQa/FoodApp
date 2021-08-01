package rs.raf.projekat2.aleksandar_kovacevic_rn7219.presentation.view.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import rs.raf.projekat2.aleksandar_kovacevic_rn7219.databinding.ActivityLoginBinding
import rs.raf.projekat2.aleksandar_kovacevic_rn7219.utils.viewbinding.Activity

class LoginActivity : Activity<ActivityLoginBinding>({ ActivityLoginBinding.inflate(it) }) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityBinding.btnLogin.setOnClickListener {
            getSharedPreferences(packageName, Context.MODE_PRIVATE).edit()
                .putBoolean("LOGGEDIN", true).apply()
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}