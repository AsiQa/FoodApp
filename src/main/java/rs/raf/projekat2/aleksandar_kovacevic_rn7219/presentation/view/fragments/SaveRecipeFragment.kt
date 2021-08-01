package rs.raf.projekat2.aleksandar_kovacevic_rn7219.presentation.view.fragments

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import rs.raf.projekat2.aleksandar_kovacevic_rn7219.R
import rs.raf.projekat2.aleksandar_kovacevic_rn7219.databinding.SaveRecipeFragmentBinding
import rs.raf.projekat2.aleksandar_kovacevic_rn7219.presentation.viewmodels.SaveRecipeViewModel
import rs.raf.projekat2.aleksandar_kovacevic_rn7219.utils.Resource
import rs.raf.projekat2.aleksandar_kovacevic_rn7219.utils.viewbinding.ViewBindingFragment
import java.io.*
import java.util.*

@AndroidEntryPoint
class SaveRecipeFragment : ViewBindingFragment<SaveRecipeFragmentBinding>({
    SaveRecipeFragmentBinding.inflate(it)
}) {

    private val saveRecipeViewModel: SaveRecipeViewModel by viewModels()
    private val saveRecipeFragmentArgs by navArgs<SaveRecipeFragmentArgs>()

    private var path: String? = null
    private var fileName: String? = null

    private val getPicture =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == AppCompatActivity.RESULT_OK) {
                val bundle = it.data?.extras
                val finalPhoto: Bitmap = bundle?.get("data") as Bitmap
                fileName = "slika_${saveRecipeFragmentArgs.recipeDetails.recipe_id}"
                path = saveToInternalStorage(finalPhoto, fileName!!)
                if (path != null) {
                    loadImageFromStorage(path!!, fileName!!)
                }
            }
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recipeDetails = saveRecipeFragmentArgs.recipeDetails
        var selectedCategory = "Breakfast"

        with(viewBinding) {
            tvRecipeTitle.text = recipeDetails.title
            Glide.with(ivRecipeIcon).load(recipeDetails.image_url).into(ivRecipeIcon)

            val cal = Calendar.getInstance()
            val year = cal.get(Calendar.YEAR)
            val month = cal.get(Calendar.MONTH)
            val day = cal.get(Calendar.DAY_OF_MONTH)
            var selectedDate = "$day.${month + 1}.$year"
            tvSelectedDate.text = selectedDate

            tvSelectedDate.setOnClickListener {
                val listener = OnDateSetListener { _, year, month, day ->
                    selectedDate = "$day.${month + 1}.$year"
                    tvSelectedDate.text = selectedDate
                }
                DatePickerDialog(requireContext(), listener, year, month, day).show()
            }

            ArrayAdapter.createFromResource(
                requireContext(),
                R.array.recipe_category,
                android.R.layout.simple_spinner_item
            ).also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spCategories.adapter = adapter
            }

            spCategories.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    pos: Int,
                    id: Long
                ) {
                    selectedCategory = parent?.getItemAtPosition(pos).toString()
                }

                override fun onNothingSelected(p0: AdapterView<*>?) = Unit
            }

            ivRecipeIcon.setOnClickListener {
                val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                try {
                    getPicture.launch(takePictureIntent)
                } catch (e: ActivityNotFoundException) {
                    Toast.makeText(requireContext(), "Camera app not found!", Toast.LENGTH_SHORT)
                        .show()
                }
            }

            btnAdd.setOnClickListener {
                saveRecipeViewModel.insertRecipe(
                    selectedDate,
                    selectedCategory,
                    recipeDetails,
                    path,
                    fileName
                )
            }

            saveRecipeViewModel.insertRecipeResult.observe(viewLifecycleOwner) {
                when (it) {
                    is Resource.Success -> {
                        AlertDialog.Builder(requireContext())
                            .setNeutralButton("Continue") { _, _ ->
                                findNavController().navigateUp()
                            }
                            .setTitle("Success!")
                            .setMessage("Successfully saved the recipe!")
                            .create().show()
                    }

                    is Resource.Error -> {
                        AlertDialog.Builder(requireContext())
                            .setNeutralButton("Continue") { _, _ ->
                                findNavController().navigateUp()
                            }
                            .setTitle("Failure!")
                            .setMessage("Failed to save the recipe!")
                            .create().show()
                    }
                }
            }
        }
    }

    private fun saveToInternalStorage(bitmapImage: Bitmap, fileName: String): String? {
        val cw = ContextWrapper(requireContext())
        val directory: File = cw.getDir("imageDir", Context.MODE_PRIVATE)
        val myPath = File(directory, "$fileName.jpg")
        var fos: FileOutputStream? = null
        try {
            fos = FileOutputStream(myPath)
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            try {
                fos?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        return directory.absolutePath
    }

    private fun loadImageFromStorage(path: String, fileName: String) {
        try {
            val f = File(path, "$fileName.jpg")
            val b = BitmapFactory.decodeStream(FileInputStream(f))
            Glide.with(viewBinding.ivRecipeIcon).load(b).into(viewBinding.ivRecipeIcon)
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }
    }
}
