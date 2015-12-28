/*
 * Copyright 2006-2015 The MZmine 3 Development Team
 * 
 * This file is part of MZmine 3.
 * 
 * MZmine 3 is free software; you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later
 * version.
 * 
 * MZmine 3 is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * MZmine 3; if not, write to the Free Software Foundation, Inc., 51 Franklin St,
 * Fifth Floor, Boston, MA 02110-1301 USA
 */

package io.github.mzmine.modules.plots.chromatogram;

import io.github.msdk.datamodel.chromatograms.Chromatogram;
import io.github.msdk.datamodel.rawdata.ChromatographyInfo;
import io.github.mzmine.main.MZmineCore;
import io.github.mzmine.util.JavaFXUtil;
import javafx.scene.Node;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.BorderPane;

/**
 * Chromatogram plot chart
 */
public class ChromatogramPlotChartNode extends BorderPane {

    private final NumberAxis xAxis, yAxis;
    private final LineChart<Number, Number> lineChart;

    ChromatogramPlotChartNode() {

        xAxis = new NumberAxis();
        yAxis = new NumberAxis();

        xAxis.setLabel("Retention time (min)");
        yAxis.setLabel("Intensity");

        lineChart = new LineChart<>(xAxis, yAxis);

        lineChart.setTitle("Chromatogram");
        lineChart.setCreateSymbols(true);

        Node zoomedChart = JavaFXUtil.addZoomSupport(lineChart);

        setCenter(zoomedChart);

    }

    void addChromatogram(Chromatogram chromatogram) {

        XYChart.Series newSeries = new XYChart.Series();
        newSeries.setName(
                "Chromatogram " + chromatogram.getChromatogramNumber());

        ChromatographyInfo rtValues[] = chromatogram.getRetentionTimes();
        double mzValues[] = chromatogram.getMzValues();
        float intensityValues[] = chromatogram.getIntensityValues();

        for (int i = 0; i < chromatogram.getNumberOfDataPoints(); i++) {
            final float rt = rtValues[i].getRetentionTime() / 60f;
            final float intensity = intensityValues[i];
            final double mz = mzValues[i];
            String tooltipText = MZmineCore.getConfiguration().getMZFormat()
                    .format(mz);
            XYChart.Data newData = new XYChart.Data(rt, intensity);
            Tooltip.install(newData.getNode(), new Tooltip(tooltipText));
            newSeries.getData().add(newData);
        }

        lineChart.getData().addAll(newSeries);

    }

}