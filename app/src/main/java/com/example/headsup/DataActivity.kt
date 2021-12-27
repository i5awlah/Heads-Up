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
                    val newCelebrity = Celebrity(name,0, taboo1, taboo2, taboo3)
                    if (databaseHelper.addCelebrity(newCelebrity) != -1)
                        Toast.makeText(this@DataActivity, "Added successfully!", Toast.LENGTH_LONG).show()
                }

            }

            btnUpdate.setOnClickListener {
                val pk = etPK.text.toString().toInt()
                val name = etCelebrity.text.toString()
                val taboo1 = etTaboo1.text.toString()
                val taboo2 = etTaboo2.text.toString()
                val taboo3 = etTaboo3.text.toString()

                etPK.text.clear()
                etCelebrity.text.clear()
                etTaboo1.text.clear()
                etTaboo2.text.clear()
                etTaboo3.text.clear()

                if (name.isNotEmpty() && taboo1.isNotEmpty() && taboo2.isNotEmpty() && taboo3.isNotEmpty()) {
                    val updatedCelebrity = Celebrity(name, pk, taboo1, taboo2, taboo3)
                    if (databaseHelper.updateCelebrity(updatedCelebrity) != -1)
                        Toast.makeText(this@DataActivity, "Updated successfully!", Toast.LENGTH_LONG).show()
                }
            }

            btnDelete.setOnClickListener {
                val pk = etPK.text.toString().toString().toInt()
                etPK.text.clear()

                    if (databaseHelper.deleteCelebrity(pk) != -1)
                        Toast.makeText(this@DataActivity, "Deleted successfully!", Toast.LENGTH_LONG).show()

            }
        }
    }
}