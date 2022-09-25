package com.singularitycoder.datapic

import jetbrains.datalore.plot.base.DataFrame
import jetbrains.datalore.plot.builder.data.OrderOptionUtil
import jetbrains.datalore.plot.config.DataMetaUtil
import jetbrains.datalore.plot.config.LayerConfig
import jetbrains.datalore.plot.config.Option
import jetbrains.datalore.plot.config.PlotConfig

class ImagePlot(opts: Map<String, Any>, isClientSide: Boolean) : PlotConfig(opts, isClientSide) {
    override fun createLayerConfig(
        layerOptions: Map<String, Any>,
        sharedData: DataFrame,
        plotMappings: Map<*, *>,
        plotDataMeta: Map<*, *>,
        plotOrderOptions: List<OrderOptionUtil.OrderOption>
    ): LayerConfig {
        val layerConfigs = ArrayList<LayerConfig>()
        val layerOptionsList = getList(Option.Plot.LAYERS)
        for (layerOpts in layerOptionsList) {
            require(layerOpts is Map<*, *>) { "Layer options: expected Map but was ${layerOpts!!::class.simpleName}" }
            @Suppress("UNCHECKED_CAST")
            layerOpts as Map<String, Any>

            val layerConfig = createLayerConfig(
                layerOptions = layerOpts,
                sharedData = sharedData,
                plotMappings = getMap(Option.PlotBase.MAPPING),
                plotDataMeta = getMap(Option.Meta.DATA_META),
                plotOrderOptions = DataMetaUtil.getOrderOptions(this.mergedOptions, getMap(Option.PlotBase.MAPPING))
            )
            layerConfigs.add(layerConfig)
        }
        return layerConfigs.first()
    }
}