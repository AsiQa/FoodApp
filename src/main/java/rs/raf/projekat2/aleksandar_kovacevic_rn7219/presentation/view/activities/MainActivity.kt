package rs.raf.projekat2.aleksandar_kovacevic_rn7219.presentation.view.activities

import dagger.hilt.android.AndroidEntryPoint
import rs.raf.projekat2.aleksandar_kovacevic_rn7219.databinding.ActivityMainBinding
import rs.raf.projekat2.aleksandar_kovacevic_rn7219.utils.viewbinding.Activity

@AndroidEntryPoint
class MainActivity : Activity<ActivityMainBinding>({ ActivityMainBinding.inflate(it) })
