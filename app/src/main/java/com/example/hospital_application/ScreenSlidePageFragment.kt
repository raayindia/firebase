package com.example.hospital_application

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat


private const val ARG_TITLE = "arg_title"
private const val ARG_BG_COLOR = "arg_bg_color"

/**
 * A simple [Fragment] subclass.
 * Use the [ScreenSlidePageFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class ScreenSlidePageFragment : Fragment() {
    private var title: String? = "Default title."
    private var bgColorResId: Int = R.color.colorPrimary

    private lateinit var inflatedView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            title = it.getString(ARG_TITLE)
            bgColorResId = it.getInt(ARG_BG_COLOR)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        inflatedView = inflater.inflate(R.layout.fragment_screen_slide_page, container, false)
        return inflatedView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        inflatedView.setBackgroundColor(ContextCompat.getColor(requireContext(), bgColorResId))
//        screen_slide_title.text = title
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param title Title parameter
         * @param bgColorId Background Color
         * @return A new instance of fragment ScreenSlidePageFragment.
         */
        @JvmStatic
        fun newInstance(title: String, bgColorId: Int) =
            ScreenSlidePageFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_TITLE, title)
                    putInt(ARG_BG_COLOR, bgColorId)
                }
            }
    }
}
