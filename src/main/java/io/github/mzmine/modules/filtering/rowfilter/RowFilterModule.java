/*
 * Copyright 2006-2016 The MZmine 3 Development Team
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

package io.github.mzmine.modules.filtering.rowfilter;

import java.util.Collection;

import javax.annotation.Nonnull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Range;

import io.github.msdk.datamodel.datastore.DataPointStore;
import io.github.msdk.datamodel.datastore.DataPointStoreFactory;
import io.github.msdk.datamodel.featuretables.FeatureTable;
import io.github.msdk.features.rowfilter.RowFilterMethod;
import io.github.msdk.util.MZTolerance;
import io.github.msdk.util.RTTolerance;
import io.github.mzmine.modules.MZmineProcessingModule;
import io.github.mzmine.parameters.ParameterSet;
import io.github.mzmine.parameters.parametertypes.selectors.FeatureTablesSelection;
import io.github.mzmine.project.MZmineProject;
import io.github.mzmine.taskcontrol.MSDKTask;
import javafx.concurrent.Task;

/**
 * Row filter module
 */
public class RowFilterModule implements MZmineProcessingModule {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final String MODULE_NAME = "Row filter";
    private static final String MODULE_DESCRIPTION = "This module removes certain rows based on given restrictions.";

    @Override
    public @Nonnull String getName() {
        return MODULE_NAME;
    }

    @Override
    public @Nonnull String getDescription() {
        return MODULE_DESCRIPTION;
    }

    @Override
    public void runModule(@Nonnull MZmineProject project,
            @Nonnull ParameterSet parameters,
            @Nonnull Collection<Task<?>> tasks) {

        // Boolean values
        final Boolean filterByMz = parameters
                .getParameter(RowFilterParameters.mzRange).getValue();
        final Boolean filterByRt = parameters
                .getParameter(RowFilterParameters.rtRange).getValue();
        final Boolean filterByDuration = parameters
                .getParameter(RowFilterParameters.durationRange).getValue();
        final Boolean filterByCount = parameters
                .getParameter(RowFilterParameters.minCount).getValue();
        final Boolean filterByIsotopes = parameters
                .getParameter(RowFilterParameters.minIsotopes).getValue();
        final Boolean filterByIonAnnotation = parameters
                .getParameter(RowFilterParameters.ionAnnotation).getValue();
        final Boolean requireAnnotation = parameters
                .getParameter(RowFilterParameters.requireAnnotation).getValue();

        // Embedded values
        final Range<Double> mzRange = parameters
                .getParameter(RowFilterParameters.mzRange)
                .getEmbeddedParameter().getValue();
        final Range<Double> rtRange = parameters
                .getParameter(RowFilterParameters.rtRange)
                .getEmbeddedParameter().getValue();
        final Range<Double> durationRange = parameters
                .getParameter(RowFilterParameters.durationRange)
                .getEmbeddedParameter().getValue();
        Double minCount = parameters
                .getParameter(RowFilterParameters.minCount)
                .getEmbeddedParameter().getValue();
        final Integer minIsotopes = parameters
                .getParameter(RowFilterParameters.minIsotopes)
                .getEmbeddedParameter().getValue();
        final String ionAnnotation = parameters
                .getParameter(RowFilterParameters.ionAnnotation)
                .getEmbeddedParameter().getValue();

        // Remove duplicate parameters
        final Boolean removeDuplicates = parameters
                .getParameter(RowFilterParameters.removeDuplicates).getValue();
        final MZTolerance duplicateMzTolerance = parameters
                .getParameter(RowFilterParameters.removeDuplicates)
                .getEmbeddedParameters()
                .getParameter(DuplicateFilterParameters.mzTolerance).getValue();
        final RTTolerance duplicateRtTolerance = parameters
                .getParameter(RowFilterParameters.removeDuplicates)
                .getEmbeddedParameters()
                .getParameter(DuplicateFilterParameters.rtTolerance).getValue();
        final Boolean duplicateRequireSameID = parameters
                .getParameter(RowFilterParameters.removeDuplicates)
                .getEmbeddedParameters()
                .getParameter(DuplicateFilterParameters.requireSameID)
                .getValue();

        // Other values
        final FeatureTablesSelection featureTables = parameters
                .getParameter(RowFilterParameters.featureTables).getValue();
        final Boolean removeOldTable = parameters
                .getParameter(RowFilterParameters.removeOldTable).getValue();
        final String nameSuffix = parameters
                .getParameter(RowFilterParameters.nameSuffix).getValue();

        if (featureTables == null || featureTables.getMatchingFeatureTables().isEmpty()) {
            logger.warn(
                    "Row filter module started with no feature table selected");
            return;
        }

        // Add a task for each feature table
        for (FeatureTable featureTable : featureTables
                .getMatchingFeatureTables()) {

            // Create the data structures
            DataPointStore dataStore = DataPointStoreFactory
                    .getMemoryDataStore();

            // Handle < 1 values for minCount
            if (minCount < 1)
                minCount = featureTable.getSamples().size() * minCount;
            // Round value down to nearest hole number
            int intMinCount = (int) (long) (double) minCount;

            // New row filter method
            RowFilterMethod method = new RowFilterMethod(featureTable,
                    dataStore, nameSuffix, filterByMz, filterByRt,
                    filterByDuration, filterByCount, filterByIsotopes,
                    filterByIonAnnotation, requireAnnotation, mzRange, rtRange,
                    durationRange, intMinCount, minIsotopes, ionAnnotation,
                    removeDuplicates, duplicateMzTolerance,
                    duplicateRtTolerance, duplicateRequireSameID);

            MSDKTask newTask = new MSDKTask("Row filtering features in table",
                    featureTable.getName(), method);

            // Add the feature table to the project
            newTask.setOnSucceeded(e -> {
                FeatureTable newFeatureTable = method.getResult();
                project.addFeatureTable(newFeatureTable);

                // If selected, remove old feature table
                if (removeOldTable != null && removeOldTable) {
                    project.removeFeatureTable(featureTable);
                }
            });

            // Add the task to the queue
            tasks.add(newTask);

        }
    }

    @Override
    public @Nonnull Class<? extends ParameterSet> getParameterSetClass() {
        return RowFilterParameters.class;
    }

}
