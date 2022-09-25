package com.singularitycoder.datapic

import android.os.Bundle
import android.text.Editable
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.Legend.LegendForm
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.XAxis.XAxisPosition
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.components.YAxis.YAxisLabelPosition
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.singularitycoder.datapic.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import jetbrains.datalore.plot.PlotImageExport
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jetbrains.kotlinx.dataframe.DataColumn
import org.jetbrains.kotlinx.dataframe.api.*


// For now just modify data with lets plot.
// Show the graph with MPAndroidCharts

// Upload CSV
// Show that data in graph
// Convert graph to image
// Save it in local
// Provide share option

// https://github.com/PhilJay/MPAndroidChart/tree/master/MPChartExample

// https://github.com/JetBrains/lets-plot-kotlin
// https://github.com/JetBrains/lets-plot-kotlin/blob/master/README_DEV.md

// https://stackoverflow.com/questions/71029840/which-android-view-should-be-used-to-display-a-jetbrains-letsplot-geomhistog
// https://stackoverflow.com/questions/43055661/reading-csv-file-in-android-app

// https://holgerbrandl.github.io/krangl/
// https://github.com/holgerbrandl/krangl

// https://stackoverflow.com/questions/7620401/how-to-convert-image-file-data-in-a-byte-array-to-a-bitmap

// https://github.com/Kotlin/dataframe
// https://datalore.jetbrains.com/view/notebook/89IMYb1zbHZxHfwAta6eKP
// https://github.com/Kotlin/dataframe/tree/master/examples
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var linearLayoutManager: LinearLayoutManager

    private var duplicateContactsList = mutableListOf<DataPic>()
    private val dataPicsAdapter = DataPicsAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.setupUI()
        binding.setupUserActionListeners()
    }

    private fun ActivityMainBinding.setupUI() {
        linearLayoutManager = LinearLayoutManager(this@MainActivity)
        rvContacts.apply {
            layoutManager = linearLayoutManager
            adapter = dataPicsAdapter
        }
//        plotDataWithLetsPlot()
        plotDataWithAndroidCharts()
    }

    private fun plotDataWithAndroidCharts() {
//        val barChart = BarChart(this)
        binding.barChart.apply {
//            val xAxisFormatter: IAxisValueFormatter = DayAxisValueFormatter(chart)

            val xAxis: XAxis = this.xAxis
            xAxis.position = XAxisPosition.BOTTOM
            xAxis.setDrawGridLines(false)
            xAxis.granularity = 1f // only intervals of 1 day
            xAxis.labelCount = 7
//            xAxis.setValueFormatter(xAxisFormatter)

//            val custom: IAxisValueFormatter = MyAxisValueFormatter()

            val leftAxis: YAxis = this.axisLeft
            leftAxis.setLabelCount(8, false)
//            leftAxis.setValueFormatter(custom)
            leftAxis.setPosition(YAxisLabelPosition.OUTSIDE_CHART)
            leftAxis.spaceTop = 15f
            leftAxis.axisMinimum = 0f // this replaces setStartAtZero(true)


            val rightAxis: YAxis = this.axisRight
            rightAxis.setDrawGridLines(false)
            rightAxis.setLabelCount(8, false)
//            rightAxis.setValueFormatter(custom)
            rightAxis.spaceTop = 15f
            rightAxis.axisMinimum = 0f // this replaces setStartAtZero(true)


            val l: Legend = this.legend
            l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM)
            l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT)
            l.setOrientation(Legend.LegendOrientation.HORIZONTAL)
            l.setDrawInside(false)
            l.setForm(LegendForm.SQUARE)
            l.setFormSize(9f)
            l.setTextSize(11f)
            l.setXEntrySpace(4f)

//            val mv = MarkerView(this, xAxisFormatter)
//            mv.setChartView(chart) // For bounds control

//            this.marker = mv // Set the marker to the chart


            // setting data

            // setting data
//            seekBarY.setProgress(50)
//            seekBarX.setProgress(12)

            val values = ArrayList<BarEntry>()

            for (i in 0 until 100) {
                val value = (Math.random() * (100 + 1)).toFloat()
                values.add(BarEntry(0f, value))
            }

            val set1: BarDataSet

            if (this.getData() != null &&
                this.getData().getDataSetCount() > 0
            ) {
                set1 = this.getData().getDataSetByIndex(0) as BarDataSet
                set1.values = values
                this.getData().notifyDataChanged()
                this.notifyDataSetChanged()
            }
        }
    }

    private fun ActivityMainBinding.setupUserActionListeners() {
        etSearch.doAfterTextChanged { keyWord: Editable? ->
            ibClearSearch.isVisible = keyWord.isNullOrBlank().not()
            tvAlphabet.isVisible = keyWord.isNullOrBlank()
            if (keyWord.isNullOrBlank()) {
                dataPicsAdapter.dataPicsList = duplicateContactsList
            } else {
                dataPicsAdapter.dataPicsList = dataPicsAdapter.dataPicsList.filter { it: DataPic -> it.title.contains(keyWord) }.toMutableList()
            }
            dataPicsAdapter.notifyDataSetChanged()
        }
        ibClearSearch.setOnClickListener {
            etSearch.setText("")
        }
    }

    // Try with HTML helper
    // https://stackoverflow.com/questions/71029840/which-android-view-should-be-used-to-display-a-jetbrains-letsplot-geomhistog
    // https://github.com/JetBrains/lets-plot-kotlin/blob/master/demo/browser/src/main/kotlin/frontendContextDemo/scripts/BarGeomAndCountStat.kt
    private fun plotDataWithLetsPlot() {
//        val df = DataFrame.Builder.emptyFrame()
//        val df2 = DataFrame.Builder().build()
//
//        val variable = DataFrame.Variable(name = "", source = DataFrame.Variable.Source.ORIGIN, label = "")
//        val orderSpec = DataFrame.OrderSpec(
//            variable = variable,
//            orderBy = variable,
//            direction = 0
//        )
//        val df3 = DataFrame.Builder().addOrderSpecs(listOf(orderSpec)).build()
////        val (xs, ys) = df3.variables()
//        val p = PlotImageExport.buildImageFromRawSpecs(
//            plotSpec = mutableMapOf<String, Any>(),
//            format = PlotImageExport.Format.PNG,
//            scalingFactor = 1.0,
//            targetDPI = Double.NaN
//        )
//        DataProcessing.defaultGroupingVariables()

        // Create data-frame in memory
//        val df4 = dataFrameOf(
//            "first_name",
//            "last_name",
//            "age",
//            "weight"
//        )(
//            "Max", "Doe", 23, 55,
//            "Franz", "Smith", 23, 88,
//            "Horst", "Keanes", 12, 82
//        )
//        df4.print("First plot", true, 10, 10, 10, true)

        // create columns
//        val fromTo by columnOf("LoNDon_paris", "MAdrid_miLAN", "londON_StockhOlm", "Budapest_PaRis", "Brussels_londOn")
//        val flightNumber by columnOf(10045.0, Double.NaN, 10065.0, Double.NaN, 10085.0)
//        val recentDelays by columnOf("23,47", null, "24, 43, 87", "13", "67, 32")
//        val airline by columnOf("KLM(!)", "{Air France} (12)", "(British Airways. )", "12. Air France", "'Swiss Air'")
//
//        val df = dataFrameOf(fromTo, flightNumber, recentDelays, airline)

        CoroutineScope(IO).launch {
            val plot = ImagePlot(
                opts = mutableMapOf<String, Any>(
                    "fromTo" to listOf("LoNDon_paris", "MAdrid_miLAN", "londON_StockhOlm", "Budapest_PaRis", "Brussels_londOn"),
                    "flightNumber" to listOf(10045.0, Double.NaN, 10065.0, Double.NaN, 10085.0),
                    "recentDelays" to listOf("23,47", null, "24, 43, 87", "13", "67, 32"),
                    "airline" to listOf("KLM(!)", "{Air France} (12)", "(British Airways. )", "12. Air France", "'Swiss Air'")
                ),
                isClientSide = true
            )
            val p = try {
                PlotImageExport.buildImageFromRawSpecs(
                    plotSpec = plot.getMap("").toMutableMap(),
                    format = PlotImageExport.Format.PNG,
                    scalingFactor = 0.5,
                    targetDPI = Double.NaN
                )
            } catch (e: Exception) {
                null
            }

            withContext(Main) {
//                binding.ivDataPic.load(p?.bytes)
            }
        }

//        cleanData(
//            fromTo = fromTo,
//            flightNumber = flightNumber,
//            recentDelays = recentDelays,
//            airline = airline
//        )

    }

    private fun cleanData(
        fromTo: DataColumn<String>,
        flightNumber: DataColumn<Double>,
        recentDelays: DataColumn<String?>,
        airline: DataColumn<String>
    ) {
        // create dataframe
        val df = dataFrameOf(fromTo, flightNumber, recentDelays, airline)
        // typed accessors for columns
        // that will appear during
        // dataframe transformation
        val origin by column<String>()
        val destination by column<String>()

        val clean = df
            // fill missing flight numbers
            .fillNA { flightNumber }.with { it: Double ->
    //                prev()
                it + 10
            }

            // convert flight numbers to int
            .convert { flightNumber }.toInt()

            // clean 'Airline' column
            .update { airline }.with { "([a-zA-Z\\s]+)".toRegex().find(it)?.value ?: "" }

            // split 'From_To' column into 'From' and 'To'
            .split { fromTo }.by("_").into(origin, destination)

            // clean 'From' and 'To' columns
            .update { origin and destination }.with { it.lowercase().replaceFirstChar(Char::uppercase) }

            // split lists of delays in 'RecentDelays' into separate columns
            // 'delay1', 'delay2'... and nest them inside original column `RecentDelays`
            .split { recentDelays }.inward { "delay$it" }

            // convert string values in `delay1`, `delay2` into ints
            .parse { recentDelays }

        clean
            // group by flight origin
            .groupBy { origin into "from" }.aggregate {
                // we are in the context of single data group

                // number of flights from origin
                count() into "count"

                // list of flight numbers
                flightNumber into "flight numbers"

                // counts of flights per airline
                airline.valueCounts() into "airlines"

                // max delay across all delays in `delay1` and `delay2`
    //                recentDelays.maxOrNull { delay1 and delay2 } into "major delay"

                // separate lists of recent delays for `delay1`, `delay2` and `delay3`
    //                recentDelays.implode(dropNulls = true) into "recent delays"

                // total delay per city of destination
    //                pivot { destination }.sum { recentDelays.intCols() } into "total delays to"
            }
    }
}