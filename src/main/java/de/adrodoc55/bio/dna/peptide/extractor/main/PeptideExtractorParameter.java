/*
 * Peptide Extractor: A command line tool for transforming the output of the Ensembl Variant Effect
 * Predictor ProteinSeqs Plugin into NetMHC-readable peptide fragments that are affected by
 * mutation.
 *
 * © Copyright (C) 2017 Adrodoc55
 *
 * This file is part of Peptide Extractor.
 *
 * Peptide Extractor is free software: you can redistribute it and/or modify it under the terms of
 * the GNU General Public License as published by the Free Software Foundation, either version 3 of
 * the License, or (at your option) any later version.
 *
 * Peptide Extractor is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with Peptide Extractor.
 * If not, see <http://www.gnu.org/licenses/>.
 *
 *
 *
 * Peptide Extractor: Ein Kommandozeilen Werkzeug um den Output des Ensembl Variant Effect Predictor
 * ProteinSeqs Plugins in NetMHC-lesbare Peptid Fragmente die von einer Mutation beeinflusst sind
 * umzuwandeln.
 *
 * © Copyright (C) 2017 Adrodoc55
 *
 * Diese Datei ist Teil von Peptide Extractor.
 *
 * Peptide Extractor ist freie Software: Sie können diese unter den Bedingungen der GNU General
 * Public License, wie von der Free Software Foundation, Version 3 der Lizenz oder (nach Ihrer Wahl)
 * jeder späteren veröffentlichten Version, weiterverbreiten und/oder modifizieren.
 *
 * Peptide Extractor wird in der Hoffnung, dass es nützlich sein wird, aber OHNE JEDE
 * GEWÄHRLEISTUNG, bereitgestellt; sogar ohne die implizite Gewährleistung der MARKTFÄHIGKEIT oder
 * EIGNUNG FÜR EINEN BESTIMMTEN ZWECK. Siehe die GNU General Public License für weitere Details.
 *
 * Sie sollten eine Kopie der GNU General Public License zusammen mit Peptide Extractor erhalten
 * haben. Wenn nicht, siehe <http://www.gnu.org/licenses/>.
 */
package de.adrodoc55.bio.dna.peptide.extractor.main;

import java.io.File;
import java.util.List;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;

/**
 * @author Adrodoc55
 */
public class PeptideExtractorParameter {
  @Parameter(names = {"-h", "--help"}, help = true,
      description = "Print information about the commandline usage")
  private boolean help;

  @Parameter(required = true, description = "<input-file>")
  private List<File> input;

  @Parameter(names = {"-o", "--output"}, required = true, description = "Specify the output file")
  private File output;

  @Parameter(names = {"-f", "--offset"},
      description = "The number of aminoacids before and after each mutation that are extracted")
  private int offset = 8;

  @Parameter(names = {"-i", "--ignore-errors"},
      description = "Continue execution when an error occurs")
  private boolean ignoreErrors;

  public boolean isHelp() {
    return help;
  }

  public File getInput() throws ParameterException {
    if (input.size() != 1) {
      throw new ParameterException("Exactly one source file has to be specified");
    }
    return input.get(0).getAbsoluteFile();
  }

  public File getOutput() {
    return output;
  }

  public int getOffset() {
    return offset;
  }

  public boolean isIgnoreErrors() {
    return ignoreErrors;
  }
}
