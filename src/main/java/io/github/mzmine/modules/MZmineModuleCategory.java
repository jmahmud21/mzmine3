/*
 * Copyright 2006-2016 The MZmine 3 Development Team
 * 
 * This file is part of MZmine 3.
 * 
 * MZmine 3 is free software; you can redistribute it and/or modify it under the terms of the GNU
 * General Public License as published by the Free Software Foundation; either version 2 of the
 * License, or (at your option) any later version.
 * 
 * MZmine 3 is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with MZmine 3; if not,
 * write to the Free Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301
 * USA
 */

package io.github.mzmine.modules;

public enum MZmineModuleCategory {

  PROJECTIO("Project I/O"), //
  PROJECT("Project"), //
  RAWDATA("Raw data methods"), //
  RAWDATAFILTERING("Raw data filtering"), //
  PEAKPICKING("Peak picking"), //
  GAPFILLING("Gap filling"), //
  ISOTOPES("Isotopes"), //
  PEAKLISTPICKING("Peak list processing"), //
  PEAKLISTFILTERING("Peak list filtering"), //
  ALIGNMENT("Alignment"), //
  NORMALIZATION("Normalization"), //
  IDENTIFICATION("Identification"), //
  PEAKLISTEXPORT("Peak list export"), //
  VISUALIZATIONRAWDATA("Visualization"), //
  VISUALIZATIONPEAKLIST("Visualization peak list"), //
  DATAANALYSIS("Data analysis"), //
  HELPSYSTEM("Help");

  private final String name;

  MZmineModuleCategory(String name) {
    this.name = name;
  }

  public String toString() {
    return name;
  }

}
