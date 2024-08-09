package com.example.ar_visualiser

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isGone
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.ar.sceneform.math.Vector3
import dev.romainguy.kotlin.math.Float3
import io.github.sceneview.SceneView
import io.github.sceneview.ar.node.ArModelNode

class MainActivity : AppCompatActivity() {

    lateinit var sceneView: SceneView
    lateinit var placeButton: ExtendedFloatingActionButton
    private lateinit var modelNode: ArModelNode

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        sceneView = findViewById(R.id.sceneview)
        placeButton = findViewById(R.id.place)

        placeButton.setOnClickListener {
           placeModel()
        }

        modelNode = ArModelNode().apply {
            loadModelGlbAsync(
                glbFileLocation = "models/laptop.glb"
            ) {
                // Optionally handle model load completion here
            }

            onAnchorChanged = {
                placeButton.isGone
            }

            // Apply scale transformation if supported
            // If scaling isn't directly supported, consider adjusting in the 3D modeling tool.
            scale = Float3(0.01f, 0.01f, 0.01f) // Ensure `scale` property or method is correct
        }

        sceneView.addChild(modelNode)
    }

    private fun placeModel() {
        modelNode?.anchor()
        // sceneView.planeRenderer.isVisible = false
    }
}
