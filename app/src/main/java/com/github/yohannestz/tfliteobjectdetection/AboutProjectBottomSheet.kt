package com.github.yohannestz.tfliteobjectdetection

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.yohannestz.tfliteobjectdetection.databinding.AboutProjectBottomsheetLayoutBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import io.noties.markwon.Markwon
import io.noties.markwon.core.CorePlugin
import java.io.IOException


class CustomBottomSheetDialogFragment : BottomSheetDialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val aboutProjectBottomSheetLayoutBinding = AboutProjectBottomsheetLayoutBinding.inflate(layoutInflater, container, false)
        val view: View = aboutProjectBottomSheetLayoutBinding.root
        val markWon = Markwon.builder(requireContext())
            .usePlugin(CorePlugin.create())
            .build()

        val markdownText = loadMarkdownFromAssets("about_project.md")
        markWon.setMarkdown(aboutProjectBottomSheetLayoutBinding.contentTextView, markdownText)
        return view
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return dialog
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.CustomBottomSheetDialog)
    }


    private fun loadMarkdownFromAssets(fileName: String): String {
        return try {
            val inputStream = requireContext().assets.open(fileName)
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            String(buffer, Charsets.UTF_8)
        } catch (e: IOException) {
            e.printStackTrace()
            ""
        }
    }
}
