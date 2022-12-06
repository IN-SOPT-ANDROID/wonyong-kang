package org.sopt.sample.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import dagger.hilt.android.AndroidEntryPoint
import org.sopt.sample.R
import org.sopt.sample.databinding.ActivityMainBinding
import org.sopt.sample.ui.home.HomeFragment
import org.sopt.sample.ui.music.MusicFragment
import org.sopt.sample.ui.profile.ProfileFragment

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        navigateTo<HomeFragment>()
        initBottomNavigation()
    }

    private fun initBottomNavigation() {
        binding.botNavMain.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_home -> navigateTo<HomeFragment>()
                R.id.menu_place -> navigateTo<MusicFragment>()
                R.id.menu_person -> navigateTo<ProfileFragment>()
            }
            return@setOnItemSelectedListener true
        }
    }

    private inline fun <reified T : Fragment> navigateTo() {
        supportFragmentManager.commit {
            replace<T>(R.id.fc_main, T::class.java.canonicalName)
        }
    }
}
