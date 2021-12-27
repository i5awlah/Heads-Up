package com.example.headsup

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.headsup.databinding.ActivityDataBinding

class DataActivity : AppCompatActivity() {

    private val databaseHelper by lazy { DatabaseHelper(applicationContext) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityDataBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            btnAdd.setOnClickListener {
                val name = etCelebrity.text.toString()
                val taboo1 = etTaboo1.text.toString()
                val taboo2 = etTaboo2.text.toString()
                val taboo3 = etTaboo3.text.toString()

                etCelebrity.text.clear()
                etTaboo1.text.clear()
                etTaboo2.text.clear()
                etTaboo3.text.clear()

                if (name.isNotEmpty() && taboo1.isNotEmpty() && taboo2.isNotEmpty() && taboo3.isNotEmpty()) {
                    if (databaseHelper.saveData(name, taboo1, taboo2, taboo3).toInt() != -1)
                        Toast.makeText(this@DataActivity, "Added successfully!", Toast.LENGTH_LONG).show()
                }

            }
        }
    }
}