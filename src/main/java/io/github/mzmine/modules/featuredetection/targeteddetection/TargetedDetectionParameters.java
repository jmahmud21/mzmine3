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

package io.github.mzmine.modules.featuredetection.targeteddetection;

import java.util.Arrays;

import io.github.msdk.util.MZTolerance;
import io.github.msdk.util.RTTolerance;
import io.github.mzmine.main.MZmineCore;
import io.github.mzmine.parameters.ParameterSet;
import io.github.mzmine.parameters.ParameterValidator;
import io.github.mzmine.parameters.parametertypes.DoubleParameter;
import io.github.mzmine.parameters.parametertypes.PercentParameter;
import io.github.mzmine.parameters.parametertypes.StringParameter;
import io.github.mzmine.parameters.parametertypes.TextAreaParameter;
import io.github.mzmine.parameters.parametertypes.selectors.RawDataFilesParameter;
import io.github.mzmine.parameters.parametertypes.tolerances.MZToleranceParameter;
import io.github.mzmine.parameters.parametertypes.tolerances.RTToleranceParameter;
import javafx.stage.FileChooser.ExtensionFilter;

public class TargetedDetectionParameters extends ParameterSet {

    public static final RawDataFilesParameter rawDataFiles = new RawDataFilesParameter();

    private static final ExtensionFilter filters[] = new ExtensionFilter[] {
            new ExtensionFilter("All supported files", "*.csv", "*.txt"),
            new ExtensionFilter("CSV files", "*.csv"),
            new ExtensionFilter("TXT files", "*.txt") };

    public static final TextAreaParameter features = new TextAreaParameter(
            "Features",
            "Features to search for in the raw data file(s). Any line not starting with a number will be ignored.\nExpected input is: m/z, retention time, name",
            "Features", ParameterValidator.createNonEmptyValidator(),
            Arrays.asList(filters));

    public static final StringParameter separator = new StringParameter(
            "Field separator",
            "Character(s) used to separate fields in the feature list",
            "Features", ParameterValidator.createNonEmptyValidator(), ",");

    public static final DoubleParameter minHeight = new DoubleParameter(
            "Min peak height",
            "Peaks with intensities less than this value are interpreted as noise",
            "Data", MZmineCore.getConfiguration().getIntensityFormat(),
            ParameterValidator.createNonEmptyValidator(), 5000d);

    public static final PercentParameter intensityTolerance = new PercentParameter(
            "Intensity tolerance",
            "Maximum allowed deviation of the peak chromatogram from the expected /\\ shape.",
            "Data", ParameterValidator.createNonEmptyValidator(), 0.15);

    public static final MZToleranceParameter mzTolerance = new MZToleranceParameter(
            "m/z tolerance",
            "Maximum allowed difference between two m/z values to be considered same.\n"
                    + "The value is specified both as absolute tolerance (in m/z) and relative tolerance (in ppm).\n"
                    + "The tolerance range is calculated using maximum of the absolute and relative tolerances.",
            "Data", ParameterValidator.createNonEmptyValidator(),
            new MZTolerance(0.001, 5.0));

    public static final RTToleranceParameter rtTolerance = new RTToleranceParameter(
            "RT tolerance",
            "Maximum allowed retention time difference between the peak apex and the retention times in the above feature input list.",
            "Data", ParameterValidator.createNonEmptyValidator(),
            new RTTolerance(10, true));

    public static final StringParameter nameSuffix = new StringParameter(
            "Name suffix",
            "Suffix to be added to the raw data file(s) when creating the feature table(s)",
            "Suffix", " targetedDetection");

    /**
     * Create the parameter set.
     */
    public TargetedDetectionParameters() {
        super(rawDataFiles, features, separator, minHeight, intensityTolerance,
                mzTolerance, rtTolerance, nameSuffix);
    }

}
