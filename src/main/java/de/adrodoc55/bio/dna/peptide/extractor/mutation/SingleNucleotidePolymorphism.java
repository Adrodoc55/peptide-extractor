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
package de.adrodoc55.bio.dna.peptide.extractor.mutation;

import static de.adrodoc55.bio.dna.peptide.extractor.mutation.Mutations.checkAmino;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.adrodoc55.bio.dna.AminoAcid;
import de.adrodoc55.bio.dna.peptide.extractor.UnknownAminoAcidException;
import de.adrodoc55.bio.dna.peptide.extractor.ValidationException;

/**
 * @author Adrodoc55
 */
public class SingleNucleotidePolymorphism implements Mutation {
  private static Pattern PATTERN = Pattern.compile("^>.*:p\\.([A-Za-z]{3})(\\d+)([A-Za-z]{3})$");

  public static SingleNucleotidePolymorphism parse(CharSequence header)
      throws UnknownAminoAcidException {
    Matcher matcher = PATTERN.matcher(header);
    if (matcher.find()) {
      AminoAcid nativeAmino = AminoAcid.from3LetterCode(matcher.group(1));
      int mutationIndex = Integer.parseInt(matcher.group(2));
      AminoAcid mutatedAmino = AminoAcid.from3LetterCode(matcher.group(3));
      return new SingleNucleotidePolymorphism(nativeAmino, mutationIndex, mutatedAmino);
    } else {
      return null;
    }
  }

  private final AminoAcid nativeAmino;
  private final int mutationIndex;
  private final AminoAcid mutatedAmino;

  public SingleNucleotidePolymorphism(AminoAcid nativeAmino, int mutationIndex,
      AminoAcid mutatedAmino) {
    this.nativeAmino = nativeAmino;
    this.mutationIndex = mutationIndex;
    this.mutatedAmino = mutatedAmino;
  }

  public AminoAcid getNaitveAmino() {
    return nativeAmino;
  }

  public int getMutationIndex() {
    return mutationIndex;
  }

  public AminoAcid getMutatedAmino() {
    return mutatedAmino;
  }

  @Override
  public CharSequence extractFromProtein(CharSequence protein, int enclosing)
      throws ValidationException {
    validateProtein(protein);
    int beginIndex = Math.max(0, mutationIndex - 1 - enclosing);
    int endIndex = Math.min(protein.length(), mutationIndex + enclosing);
    return protein.subSequence(beginIndex, endIndex);
  }

  @Override
  public void validateProtein(CharSequence protein) throws ValidationException {
    checkAmino(protein, mutationIndex, mutatedAmino);
  }

  @Override
  public String getUniqueSolution(CharSequence output) {
    return "SNP-" + output;
  }
}
