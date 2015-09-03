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

package io.github.msdk.datamodel.peaklists;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import io.github.msdk.datamodel.rawdata.RawDataFile;

/* 
 * WARNING: the interfaces in this package are still under construction
 */

/**
 * 
 */
public interface Sample {

    /**
     * @return Short descriptive name
     */
    @Nonnull String getName();

    /**
     * Change the name
     */
    void setName(@Nonnull String name);
    
    /**
     * Returns a raw data file
     */
    @Nullable RawDataFile getRawDataFile();


}
