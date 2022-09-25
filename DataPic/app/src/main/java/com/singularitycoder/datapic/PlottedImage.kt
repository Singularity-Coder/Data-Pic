package com.singularitycoder.datapic

import jetbrains.datalore.base.geometry.DoubleRectangle
import jetbrains.datalore.base.geometry.DoubleVector
import jetbrains.datalore.base.interval.DoubleSpan
import jetbrains.datalore.plot.base.*
import jetbrains.datalore.plot.base.interact.GeomTargetCollector
import jetbrains.datalore.plot.base.render.SvgRoot
import jetbrains.datalore.vis.svg.SvgNode

class PlottedImage(
    override val isEmpty: Boolean,
    override val flipped: Boolean,
    override val targetCollector: GeomTargetCollector
) : SvgRoot, Aesthetics, PositionAdjustment, CoordinateSystem, GeomContext {
    override fun dataPointAt(index: Int): DataPointAesthetics {
        TODO("Not yet implemented")
    }

    override fun dataPointCount(): Int {
        TODO("Not yet implemented")
    }

    override fun dataPoints(): Iterable<DataPointAesthetics> {
        TODO("Not yet implemented")
    }

    override fun groups(): Iterable<Int> {
        TODO("Not yet implemented")
    }

    override fun numericValues(aes: Aes<Double>): Iterable<Double?> {
        TODO("Not yet implemented")
    }

    override fun range(aes: Aes<Double>): DoubleSpan? {
        TODO("Not yet implemented")
    }

    override fun resolution(aes: Aes<Double>, naValue: Double): Double {
        TODO("Not yet implemented")
    }

    override fun flip(): CoordinateSystem {
        TODO("Not yet implemented")
    }

    override fun toClient(p: DoubleVector): DoubleVector? {
        TODO("Not yet implemented")
    }

    override fun unitSize(p: DoubleVector): DoubleVector {
        TODO("Not yet implemented")
    }

    override fun estimateTextSize(text: String, family: String, size: Double, isBold: Boolean, isItalic: Boolean): DoubleVector {
        TODO("Not yet implemented")
    }

    override fun getAesBounds(): DoubleRectangle {
        TODO("Not yet implemented")
    }

    override fun getResolution(aes: Aes<Double>): Double {
        TODO("Not yet implemented")
    }

    override fun isMappedAes(aes: Aes<*>): Boolean {
        TODO("Not yet implemented")
    }

    override fun withTargetCollector(targetCollector: GeomTargetCollector): GeomContext {
        TODO("Not yet implemented")
    }

    override fun handlesGroups(): Boolean {
        TODO("Not yet implemented")
    }

    override fun translate(v: DoubleVector, p: DataPointAesthetics, ctx: GeomContext): DoubleVector {
        TODO("Not yet implemented")
    }

    override fun add(node: SvgNode) {
        TODO("Not yet implemented")
    }
}