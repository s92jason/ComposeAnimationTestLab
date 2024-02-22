package com.jasonchen.workspace.study.composeanimationtestlab.ui.animation.complete

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.jasonchen.workspace.study.composeanimationtestlab.R
import com.jasonchen.workspace.study.composeanimationtestlab.databinding.ActivityBeatCircleBinding

class BeatCircleActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBeatCircleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBeatCircleBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.composeView.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                 //In Compose world
                AnimatedBeatCircle(
                    backgroundRes = R.drawable.img_cube_achieve,
                    contentDescription = ""
                )
            }
        }
    }
}