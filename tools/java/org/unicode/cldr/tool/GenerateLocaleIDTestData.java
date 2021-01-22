package org.unicode.cldr.tool;

import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;

import org.unicode.cldr.util.CLDRPaths;
import org.unicode.cldr.util.CldrUtility;
import org.unicode.cldr.util.LsrvCanonicalizer;
import org.unicode.cldr.util.LsrvCanonicalizer.TestDataTypes;
import org.unicode.cldr.util.StandardCodes.LstrType;
import org.unicode.cldr.util.TempPrintWriter;

public class GenerateLocaleIDTestData {
    static final LsrvCanonicalizer rrs = LsrvCanonicalizer.getInstance();

    public static void main(String[] args) throws IOException {
        try (TempPrintWriter pw = TempPrintWriter.openUTF8Writer(CLDRPaths.TEST_DATA + "localeIdentifiers", "localeCanonicalization.txt")) {
            pw.println("# Test data for locale identifier canonicalization");
            pw.println(CldrUtility.getCopyrightString("#  "));
            pw.println("#\n"
                + "# Format:\n"
                + "# <source locale identifier>\t;\t<expected canonicalized locale identifier>\n"
                + "#\n"
                + "# The data lines are divided into 4 sets:\n"
                + "#   " + LsrvCanonicalizer.TestDataTypes.explicit + ":    a short list of explicit test cases.\n"
                + "#   " + LsrvCanonicalizer.TestDataTypes.fromAliases + ": test cases generated from the alias data.\n"
                + "#   " + LsrvCanonicalizer.TestDataTypes.decanonicalized + ": test cases generated by reversing the normalization process.\n"
                + "#   " + LsrvCanonicalizer.TestDataTypes.withIrrelevants + ": test cases generated from the others by adding irrelevant fields where possible,\n"
                + "#                           to ensure that the canonicalization implementation is not sensitive to irrelevant fields. These include:\n"
                + "#     Language: " + rrs.getIrrelevantField(LstrType.language) + "\n"
                + "#     Script:   " + rrs.getIrrelevantField(LstrType.script) + "\n"
                + "#     Region:   " + rrs.getIrrelevantField(LstrType.region) + "\n"
                + "#     Variant:  " + rrs.getIrrelevantField(LstrType.variant) + "\n"
                + "######\n\n");
            for (Entry<TestDataTypes, Map<String, String>> mainEntry : rrs.getTestData(null).entrySet()) {
                TestDataTypes type = mainEntry.getKey();
                pw.println("\n# " + type + "\n");
                for (Entry<String, String> entry : mainEntry.getValue().entrySet()) {
                    String toTest = entry.getKey();
                    String expected = entry.getValue();
                    pw.println(toTest + "\t;\t" + expected);
                }
            }
        }
    }
}
