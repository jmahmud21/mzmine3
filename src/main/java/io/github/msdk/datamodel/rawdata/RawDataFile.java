/* 
 * (C) Copyright 2015 by MSDK Development Team
 *
 * This software is dual-licensed under either
 *
 * (a) the terms of the GNU Lesser General Public License version 2.1
 * as published by the Free Software Foundation
 *
 * or (per the licensee's choosing)
 *
 * (b) the terms of the Eclipse Public License v1.0 as published by
 * the Eclipse Foundation.
 */

package io.github.msdk.datamodel.rawdata;

import java.io.File;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.common.collect.Range;

/**
 * Raw data file, typically obtained by loading data from one of the supported
 * file formats. A raw data file is a collection of scans (MsScan).
 * 
 * @see MsScan
 */
public interface RawDataFile {

    /**
     * Returns the name of this raw data file. This can be any descriptive name,
     * not necessarily the original file name.
     * 
     * @return Raw data file name
     */
    @Nonnull
    String getName();

    /**
     * Updates the name of this raw data file.
     * 
     * @param name
     *            New name
     */
    void setName(@Nonnull String name);

    /**
     * Returns the original file name and path where the file was loaded from,
     * or null if this file was created by MSDK.
     * 
     * @return Original filename and path.
     */
    @Nullable
    File getOriginalFile();

    /**
     * Updates the original filename.
     * 
     * @param newOriginalFile
     *            New original filename.
     */
    void setOriginalFile(@Nullable File newOriginalFile);

    /**
     * Returns the file type of this raw data file.
     * 
     * @return Raw data file type
     */
    @Nonnull
    String getRawDataFileType();

    /**
     * Updates the file type of this raw data file.
     * 
     * @param RawDataFileType
     *            New file type
     */
    void setRawDataFileType(@Nonnull RawDataFileType rawDataFileType);

    /**
     * Returns all MS functions found in this raw data file.
     * 
     * @return A list of MS functions.
     */
    @Nonnull
    List<MsFunction> getMsFunctions();

    /**
     * Returns an immutable list of all scans. The list can be safely iterated
     * over, as it cannot be modified by another thread.
     * 
     * @return A list of all scans.
     */
    @Nonnull
    List<MsScan> getScans();

    /**
     * Returns an immutable list of all scans of a given MS function. The list
     * can be safely iterated over, as it cannot be modified by another thread.
     * 
     * @param function
     *            The scans of this function will be returned.
     * @return A list of matching scans.
     */
    @Nonnull
    List<MsScan> getScans(MsFunction function);

    /**
     * Returns an immutable list of all scans in a given retention time range.
     * The list can be safely iterated over, as it cannot be modified by another
     * thread.
     * 
     * @param chromatographyRange
     *            Range of retention times.
     * @return A list of matching scans.
     */
    @Nonnull
    List<MsScan> getScans(
            @Nonnull Range<ChromatographyInfo> chromatographyRange);

    /**
     * Returns an immutable list of all scans of a given MS function and in a
     * given retention time range. The list can be safely iterated over, as it
     * cannot be modified by another thread.
     * 
     * @param function
     *            The scans of this function will be returned.
     * @param chromatographyRange
     *            Range of retention times.
     * @return A list of matching scans.
     */
    @Nonnull
    List<MsScan> getScans(@Nonnull MsFunction function,
            @Nonnull Range<ChromatographyInfo> chromatographyRange);

    /**
     * Returns an immutable list of all chromatograms. The list can be safely
     * iterated over, as it cannot be modified by another thread.
     * 
     * @return A list of all chromatograms.
     */
    @Nonnull
    List<MsScan> getChromatograms();

    /**
     * Remove all data associated with this file from the disk. After this
     * method is called, any subsequent method calls on this object will throw
     * IllegalStateException.
     */
    void dispose();

}