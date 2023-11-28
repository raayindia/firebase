package com.example.hospital_application.fragments

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import com.example.hospital_application.R
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.MPPointF
import com.mikhaellopez.circularfillableloaders.CircularFillableLoaders


class NotificationsFragment : Fragment() {
    private lateinit var pieChart: CircularFillableLoaders
    private lateinit var circularProgressBar: ProgressBar
    private lateinit var percentageProgressBar1: ProgressBar
    private lateinit var percentageProgressBar2: ProgressBar
    private lateinit var percentageProgressBar3: ProgressBar
    private lateinit var textPercent1: TextView
    private lateinit var textPercent2: TextView
    private lateinit var textPercent3: TextView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_notifications, container, false)

        pieChart = view.findViewById(R.id.pieChart)
//        percentageProgressBar1 = view.findViewById(R.id.progress)
//        percentageProgressBar2 = view.findViewById(R.id.progress2)
//        percentageProgressBar3 = view.findViewById(R.id.progress3)
//        textPercent1 = view.findViewById(R.id.textpercent)
//        textPercent2 = view.findViewById(R.id.textpercent2)
//        textPercent3 = view.findViewById(R.id.textpercent3)


        pieChart.setOnClickListener {
//            val r = Random()
//            val progress = r.nextInt(100 - 1) + 1
//            pieChart.setProgress(progress)
        }








//        val percentageProgressBar = view.findViewById<ProgressBar>(R.id.progress)
//        percentageProgressBar.setProgress(75) // Set the desired progress value (e.g., 75%)

//        circularProgressBar = view.findViewById(R.id.percentageProgressBar)

        // Set the progress percentage (e.g., 75%)
//        circularProgressBar.progress = 75
        val entries = ArrayList<PieEntry>().apply {
            add(PieEntry(50f))
            add(PieEntry(30f))
            add(PieEntry(20f))
//            add(PieEntry(8f, "IT"))
        }

        val dataSet = PieDataSet(entries, "Subjects").apply {
            setDrawIcons(false)
            sliceSpace = 3f
            iconsOffset = MPPointF(0f, 40f)
            selectionShift = 5f
        }

//        val colors = ArrayList<Int>().apply {
//            for (color in ColorTemplate.MATERIAL_COLORS) {
//                add(color)
//            }
//        }

        val MY_COLORS = intArrayOf(
            Color.parseColor("#148CA5"),
            Color.parseColor("#79D2DE"),
            Color.parseColor("#EC6666")


        )

        val colors = MY_COLORS.toList()

        dataSet.colors = colors

        val data = PieData(dataSet).apply {
            setValueFormatter(PercentFormatter())
            setValueTextSize(15f)
            setValueTypeface(Typeface.DEFAULT_BOLD)
            setValueTextColor(Color.WHITE)
        }

//        pieChart.data = data
//
//        pieChart.highlightValues(null)
        pieChart.invalidate()
        // Set the progress for the custom view (e.g., 75%)
//        percentageProgressBar.progress = 75

        // Invalidate the custom view to trigger onDraw
//        percentageProgressBar.invalidate()
//        updateProgressBarsAndText(dataSet)
        return view
    }
//    private fun updateProgressBarsAndText(dataSet: PieDataSet) {
//        val progressBarList = listOf(percentageProgressBar1, percentageProgressBar2, percentageProgressBar3)
//        val textPercentList = listOf(textPercent1, textPercent2, textPercent3)
//
//        for (i in 0 until dataSet.entryCount) {
//            val progress = dataSet.getEntryForIndex(i).value.toInt()
//
//            // Set the progress bar color based on the color of the corresponding PieChart entry
//            progressBarList[i].progressDrawable.setColorFilter(dataSet.getColor(i), android.graphics.PorterDuff.Mode.SRC_IN)
//
//            // Set the progress and text
//            progressBarList[i].progress = progress
//            textPercentList[i].text = "$progress%"
//        }
//    }

}