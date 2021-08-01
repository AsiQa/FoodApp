package rs.raf.projekat2.aleksandar_kovacevic_rn7219.presentation.view.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import rs.raf.projekat2.aleksandar_kovacevic_rn7219.databinding.ActivitySplashBinding
import rs.raf.projekat2.aleksandar_kovacevic_rn7219.utils.viewbinding.Activity


class SplashActivity : Activity<ActivitySplashBinding>({ ActivitySplashBinding.inflate(it) }) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val loggedIn =
            getSharedPreferences(packageName, Context.MODE_PRIVATE).getBoolean("LOGGEDIN", false)
        if (loggedIn) startActivity(Intent(this, MainActivity::class.java))
        else startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}
