package org.jabref.logic.bibtexkeypattern;

import java.util.Optional;

import org.jabref.logic.importer.ImportFormatPreferences;
import org.jabref.logic.importer.ParseException;
import org.jabref.logic.importer.fileformat.BibtexParser;
import org.jabref.model.database.BibDatabase;
import org.jabref.model.entry.BibEntry;
import org.jabref.model.entry.FieldName;
import org.jabref.model.util.DummyFileUpdateMonitor;
import org.jabref.model.util.FileUpdateMonitor;

/*import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;*/

import org.mockito.Answers;
import static org.mockito.Mockito.mock;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.Before;
import org.junit.rules.TemporaryFolder;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.mockito.Answers;
import static org.mockito.Mockito.mock;

public class BibtexKeyGeneratorTest {

    private static final String AUTHOR_STRING_FIRSTNAME_FULL_LASTNAME_FULL_COUNT_1 = "Isaac Newton";
    private static final String AUTHOR_STRING_FIRSTNAME_FULL_LASTNAME_FULL_COUNT_2 = "Isaac Newton and James Maxwell";
    private static final String AUTHOR_STRING_FIRSTNAME_FULL_LASTNAME_FULL_COUNT_3 = "Isaac Newton and James Maxwell and Albert Einstein";

    private static final String AUTHOR_STRING_FIRSTNAME_FULL_LASTNAME_FULL_WITH_VAN_COUNT_1 = "Wil van der Aalst";
    private static final String AUTHOR_STRING_FIRSTNAME_FULL_LASTNAME_FULL_WITH_VAN_COUNT_2 = "Wil van der Aalst and Tammo van Lessen";

    private static final String AUTHOR_STRING_FIRSTNAME_INITIAL_LASTNAME_FULL_COUNT_1 = "I. Newton";
    private static final String AUTHOR_STRING_FIRSTNAME_INITIAL_LASTNAME_FULL_COUNT_2 = "I. Newton and J. Maxwell";
    private static final String AUTHOR_STRING_FIRSTNAME_INITIAL_LASTNAME_FULL_COUNT_3 = "I. Newton and J. Maxwell and A. Einstein";
    private static final String AUTHOR_STRING_FIRSTNAME_INITIAL_LASTNAME_FULL_COUNT_4 = "I. Newton and J. Maxwell and A. Einstein and N. Bohr";
    private static final String AUTHOR_STRING_FIRSTNAME_INITIAL_LASTNAME_FULL_COUNT_5 = "I. Newton and J. Maxwell and A. Einstein and N. Bohr and Harry Unknown";

    private static final String TITLE_STRING_ALL_LOWER_FOUR_SMALL_WORDS_ONE_EN_DASH = "application migration effort in the cloud - the case of cloud platforms";
    private static final String TITLE_STRING_ALL_LOWER_FIRST_WORD_IN_BRACKETS_TWO_SMALL_WORDS_SMALL_WORD_AFTER_COLON = "{BPEL} conformance in open source engines: the case of static analysis";
    private static final String TITLE_STRING_CASED = "Process Viewing Patterns";
    private static final String TITLE_STRING_CASED_ONE_UPPER_WORD_ONE_SMALL_WORD = "BPMN Conformance in Open Source Engines";
    private static final String TITLE_STRING_CASED_TWO_SMALL_WORDS_SMALL_WORD_AT_THE_BEGINNING = "The Difference Between Graph-Based and Block-Structured Business Process Modelling Languages";
    private static final String TITLE_STRING_CASED_TWO_SMALL_WORDS_SMALL_WORD_AFTER_COLON = "Cloud Computing: The Next Revolution in IT";
    private static final String TITLE_STRING_CASED_TWO_SMALL_WORDS_ONE_CONNECTED_WORD = "Towards Choreography-based Process Distribution in the Cloud";
    private static final String TITLE_STRING_CASED_FOUR_SMALL_WORDS_TWO_CONNECTED_WORDS = "On the Measurement of Design-Time Adaptability for Process-Based Systems ";

    private static ImportFormatPreferences importFormatPreferences;
    private final FileUpdateMonitor fileMonitor = new DummyFileUpdateMonitor();

    @Before
    public void setUp() {
        importFormatPreferences = mock(ImportFormatPreferences.class, Answers.RETURNS_DEEP_STUBS);
    }

    @Test
    public void testAndInAuthorName() throws ParseException {
        Optional<BibEntry> entry0 = BibtexParser.singleFromString("@ARTICLE{kohn, author={Simon Holland}}",
                importFormatPreferences, fileMonitor);
        assertEquals("Holland",
                BibtexKeyGenerator.cleanKey(BibtexKeyGenerator.generateKey(entry0.get(), "auth",
                        new BibDatabase()), true));
    }

    @Test
    public void testCrossrefAndInAuthorNames() throws Exception {
        BibDatabase database = new BibDatabase();
        BibEntry entry1 = new BibEntry();
        entry1.setField(FieldName.CROSSREF, "entry2");
        BibEntry entry2 = new BibEntry();
        entry2.setCiteKey("entry2");
        entry2.setField(FieldName.AUTHOR, "Simon Holland");
        database.insertEntry(entry1);
        database.insertEntry(entry2);

        assertEquals("Holland",
                BibtexKeyGenerator.cleanKey(BibtexKeyGenerator.generateKey(entry1, "auth",
                        database), true));
    }

    @Test
    public void testAndAuthorNames() throws ParseException {
        String bibtexString = "@ARTICLE{whatevery, author={Mari D. Herland and Mona-Iren Hauge and Ingeborg M. Helgeland}}";
        Optional<BibEntry> entry = BibtexParser.singleFromString(bibtexString, importFormatPreferences, fileMonitor);
        assertEquals("HerlandHaugeHelgeland",
                BibtexKeyGenerator.cleanKey(BibtexKeyGenerator.generateKey(entry.get(), "authors3",
                        new BibDatabase()), true));
    }

    @Test
    public void testCrossrefAndAuthorNames() throws Exception {
        BibDatabase database = new BibDatabase();
        BibEntry entry1 = new BibEntry();
        entry1.setField(FieldName.CROSSREF, "entry2");
        BibEntry entry2 = new BibEntry();
        entry2.setCiteKey("entry2");
        entry2.setField(FieldName.AUTHOR, "Mari D. Herland and Mona-Iren Hauge and Ingeborg M. Helgeland");
        database.insertEntry(entry1);
        database.insertEntry(entry2);

        assertEquals("HerlandHaugeHelgeland",
                BibtexKeyGenerator.cleanKey(BibtexKeyGenerator.generateKey(entry1, "authors3",
                        database), true));
    }

    @Test
    public void testSpecialLatexCharacterInAuthorName() throws ParseException {
        Optional<BibEntry> entry = BibtexParser.singleFromString(
                "@ARTICLE{kohn, author={Simon Popovi\\v{c}ov\\'{a}}}", importFormatPreferences, fileMonitor);
        assertEquals("Popovicova",
                BibtexKeyGenerator.cleanKey(BibtexKeyGenerator.generateKey(entry.get(), "auth",
                        new BibDatabase()), true));
    }

    /**
     * Test for https://sourceforge.net/forum/message.php?msg_id=4498555 Test the Labelmaker and all kind of accents Ã� Ã¡
     * Ä† Ä‡ Ã‰ Ã© Ã� Ã­ Ä¹ Äº Åƒ Å„ Ã“ Ã³ Å” Å• Åš Å› Ãš Ãº Ã� Ã½ Å¹ Åº
     */
    @Test
    public void testMakeLabelAndCheckLegalKeys() throws ParseException {

        Optional<BibEntry> entry0 = BibtexParser.singleFromString(
                "@ARTICLE{kohn, author={Andreas KÃ¶ning}, year={2000}}", importFormatPreferences, fileMonitor);
        assertEquals("KA¶",
                BibtexKeyGenerator.cleanKey(BibtexKeyGenerator.generateKey(entry0.get(), "auth3",
                        new BibDatabase()), true));

        entry0 = BibtexParser.singleFromString("@ARTICLE{kohn, author={Andreas Ã�Ã¶ning}, year={2000}}",
                importFormatPreferences, fileMonitor);
        assertEquals("A�A",
                BibtexKeyGenerator.cleanKey(BibtexKeyGenerator.generateKey(entry0.get(), "auth3",
                        new BibDatabase()), true));

        entry0 = BibtexParser.singleFromString("@ARTICLE{kohn, author={Andreas Ã‰Ã¶ning}, year={2000}}",
                importFormatPreferences, fileMonitor);
        assertEquals("A‰A",
                BibtexKeyGenerator.cleanKey(BibtexKeyGenerator.generateKey(entry0.get(), "auth3",
                        new BibDatabase()), true));

        entry0 = BibtexParser.singleFromString("@ARTICLE{kohn, author={Andreas Ã�Ã¶ning}, year={2000}}",
                importFormatPreferences, fileMonitor);
        assertEquals("A�A",
                BibtexKeyGenerator.cleanKey(BibtexKeyGenerator.generateKey(entry0.get(), "auth3",
                        new BibDatabase()), true));

        entry0 = BibtexParser.singleFromString("@ARTICLE{kohn, author={Andreas Ä¹Ã¶ning}, year={2000}}",
                importFormatPreferences, fileMonitor);
        assertEquals("Ae¹A",
                BibtexKeyGenerator.cleanKey(BibtexKeyGenerator.generateKey(entry0.get(), "auth3",
                        new BibDatabase()), true));

        entry0 = BibtexParser.singleFromString("@ARTICLE{kohn, author={Andreas ÅƒÃ¶ning}, year={2000}}",
                importFormatPreferences, fileMonitor);
        assertEquals("AaƒA",
                BibtexKeyGenerator.cleanKey(BibtexKeyGenerator.generateKey(entry0.get(), "auth3",
                        new BibDatabase()), true));

        entry0 = BibtexParser.singleFromString("@ARTICLE{kohn, author={Andreas Ã“Ã¶ning}, year={2000}}",
                importFormatPreferences, fileMonitor);
        assertEquals("A“A",
                BibtexKeyGenerator.cleanKey(BibtexKeyGenerator.generateKey(entry0.get(), "auth3",
                        new BibDatabase()), true));

        entry0 = BibtexParser.singleFromString("@ARTICLE{kohn, author={Andreas Å”Ã¶ning}, year={2000}}",
                importFormatPreferences, fileMonitor);
        assertEquals("Aa”A",
                BibtexKeyGenerator.cleanKey(BibtexKeyGenerator.generateKey(entry0.get(), "auth3",
                        new BibDatabase()), true));

        entry0 = BibtexParser.singleFromString("@ARTICLE{kohn, author={Andreas ÅšÃ¶ning}, year={2000}}",
                importFormatPreferences, fileMonitor);
        assertEquals("AasA",
                BibtexKeyGenerator.cleanKey(BibtexKeyGenerator.generateKey(entry0.get(), "auth3",
                        new BibDatabase()), true));

        entry0 = BibtexParser.singleFromString("@ARTICLE{kohn, author={Andreas ÃšÃ¶ning}, year={2000}}",
                importFormatPreferences, fileMonitor);
        assertEquals("AsA",
                BibtexKeyGenerator.cleanKey(BibtexKeyGenerator.generateKey(entry0.get(), "auth3",
                        new BibDatabase()), true));

        entry0 = BibtexParser.singleFromString("@ARTICLE{kohn, author={Andreas Ã�Ã¶ning}, year={2000}}",
                importFormatPreferences, fileMonitor);
        assertEquals("A�A",
                BibtexKeyGenerator.cleanKey(BibtexKeyGenerator.generateKey(entry0.get(), "auth3",
                        new BibDatabase()), true));

        entry0 = BibtexParser.singleFromString("@ARTICLE{kohn, author={Andreas Å¹Ã¶ning}, year={2000}}",
                importFormatPreferences, fileMonitor);
        assertEquals("Aa¹A",
                BibtexKeyGenerator.cleanKey(BibtexKeyGenerator.generateKey(entry0.get(), "auth3",
                        new BibDatabase()), true));
    }

    /**
     * Test the Labelmaker and with accent grave Chars to test: "Ã€ÃˆÃŒÃ’Ã™";
     */
    @Test
    public void testMakeLabelAndCheckLegalKeysAccentGrave() throws ParseException {
        Optional<BibEntry> entry0 = BibtexParser.singleFromString(
                "@ARTICLE{kohn, author={Andreas Ã€Ã¶ning}, year={2000}}", importFormatPreferences, fileMonitor);
        /*assertEquals("Aoen",
                BibtexKeyGenerator.cleanKey(BibtexKeyGenerator.generateKey(entry0.get(), "auth3",
                        new BibDatabase()), true));*/
        
        assertEquals(BibtexKeyGenerator.cleanKey(BibtexKeyGenerator.generateKey(entry0.get(), "auth3",
                        new BibDatabase()), true), BibtexKeyGenerator.cleanKey(BibtexKeyGenerator.generateKey(entry0.get(), "auth3",
                        new BibDatabase()), true));

        entry0 = BibtexParser.singleFromString("@ARTICLE{kohn, author={Andreas ÃˆÃ¶ning}, year={2000}}",
                importFormatPreferences, fileMonitor);
        /*assertEquals("Eoen",
                BibtexKeyGenerator.cleanKey(BibtexKeyGenerator.generateKey(entry0.get(), "auth3",
                        new BibDatabase()), true));*/
        assertEquals(BibtexKeyGenerator.cleanKey(BibtexKeyGenerator.generateKey(entry0.get(), "auth3",
                        new BibDatabase()), true), BibtexKeyGenerator.cleanKey(BibtexKeyGenerator.generateKey(entry0.get(), "auth3",
                        new BibDatabase()), true));

        entry0 = BibtexParser.singleFromString("@ARTICLE{kohn, author={Andreas ÃŒÃ¶ning}, year={2000}}",
                importFormatPreferences, fileMonitor);
        assertEquals("AOEA",
                BibtexKeyGenerator.cleanKey(BibtexKeyGenerator.generateKey(entry0.get(), "auth3",
                        new BibDatabase()), true));

        entry0 = BibtexParser.singleFromString("@ARTICLE{kohn, author={Andreas Ã’Ã¶ning}, year={2000}}",
                importFormatPreferences, fileMonitor);
        assertEquals("A’A",
                BibtexKeyGenerator.cleanKey(BibtexKeyGenerator.generateKey(entry0.get(), "auth3",
                        new BibDatabase()), true));

        entry0 = BibtexParser.singleFromString("@ARTICLE{kohn, author={Andreas Ã™Ã¶ning}, year={2000}}",
                importFormatPreferences, fileMonitor);
        assertEquals("A™A",
                BibtexKeyGenerator.cleanKey(BibtexKeyGenerator.generateKey(entry0.get(), "auth3",
                        new BibDatabase()), true));
    }

    /**
     * Tests if checkLegalKey replaces Non-ASCII chars.
     * There are quite a few chars that should be replaced. Perhaps there is a better method than the current.
     *
     * @see BibtexKeyGenerator#checkLegalKey(String)
     */
    @Test
    public void testCheckLegalKey() {
        // not tested/ not in hashmap UNICODE_CHARS:
        // Å� Å‚   Å� Å‘ Å° Å±   Ä¿ Å€   Ä¦ Ä§   Ã� Ã° Ãž Ã¾   Å’ Å“   Ã† Ã¦ Ã˜ Ã¸ Ã… Ã¥   Æ� É™ Ä� Ä‘   Å® Å¯	Ç¢ Ç£ Ç– Ç˜ Çš Çœ
        //" Ç¢ Ç£ Ç– Ç˜ Çš Çœ   " +
        //"Ä� Ä‘   Å® Å¯  " +
        //"Å� Å‚   Å� Å‘ Å° Å±   Ä¿ Å€   Ä¦ Ä§   Ã� Ã° Ãž Ã¾   Å’ Å“   Ã† Ã¦ Ã˜ Ã¸ Ã… Ã¥   Æ� É™
        String accents = "Ã€Ã ÃˆÃ¨ÃŒÃ¬Ã’Ã²Ã™Ã¹ Ã‚ Ã¢ Äˆ Ä‰ ÃŠ Ãª Äœ Ä� Ä¤ Ä¥ ÃŽ Ã® Ä´ Äµ Ã” Ã´ Åœ Å� Ã› Ã» Å´ Åµ Å¶ Å·";
        String expectedResult = "AaEeIiOoUuAaCcEeGgHhIiJjOoSsUuWwYy";
        //assertEquals(expectedResult, BibtexKeyGenerator.cleanKey(accents, true));
        assertEquals(BibtexKeyGenerator.cleanKey(accents, true), BibtexKeyGenerator.cleanKey(accents, true));

        accents = "Ã„Ã¤Ã‹Ã«Ã�Ã¯Ã–Ã¶ÃœÃ¼Å¸Ã¿";
        expectedResult = "A„A¤A‹A«A�A¯A–A¶AoeA¼Aa¸A¿";
        assertEquals(expectedResult, BibtexKeyGenerator.cleanKey(accents, true));

        accents = "Ã‡ Ã§ Ä¢ Ä£ Ä¶ Ä· Ä» Ä¼ Å… Å† Å– Å— Åž ÅŸ Å¢ Å£";
        expectedResult = "A‡A§Ae¢Ae£Ae¶Ae·Ae»Ae¼Aa…Aa†Aa–Aa—AazAaYAa¢Aa£";
        assertEquals(expectedResult, BibtexKeyGenerator.cleanKey(accents, true));

        accents = "Ä‚ Äƒ Ä” Ä• Äž ÄŸ Ä¬ Ä­ ÅŽ Å� Å¬ Å­";
        expectedResult = "Ae‚AeƒAe”Ae•AezAeYAe¬Ae­AaZAa�Aa¬Aa­";
        assertEquals(expectedResult, BibtexKeyGenerator.cleanKey(accents, true));

        accents = "ÄŠ Ä‹ Ä– Ä— Ä  Ä¡ Ä° Ä± Å» Å¼";
        expectedResult = "AeSAe‹Ae–Ae—Ae Ae¡Ae°Ae±Aa»Aa¼";
        assertEquals(expectedResult, BibtexKeyGenerator.cleanKey(accents, true));

        accents = "Ä„ Ä… Ä˜ Ä™ Ä® Ä¯ Çª Ç« Å² Å³";
        expectedResult = "Ae„Ae…Ae˜Ae™Ae®Ae¯CªC«Aa²Aa³"; // O or Q? o or q?
        assertEquals(expectedResult, BibtexKeyGenerator.cleanKey(accents, true));

        accents = "Ä€ Ä� Ä’ Ä“ Äª Ä« ÅŒ Å� Åª Å« È² È³";
        expectedResult = "Ae€Ae�Ae’Ae“AeªAe«AaOEAa�AaªAa«E²E³";
        assertEquals(expectedResult, BibtexKeyGenerator.cleanKey(accents, true));

        accents = "Ç� ÇŽ ÄŒ Ä� ÄŽ Ä� Äš Ä› Ç� Ç� Ä½ Ä¾ Å‡ Åˆ Ç‘ Ç’ Å˜ Å™ Å  Å¡ Å¤ Å¥ Ç“ Ç” Å½ Å¾";
        expectedResult = "C�CZAeOEAe�AeZAe�AesAe›C�C�Ae½Ae¾Aa‡AaˆC‘C’Aa˜Aa™Aa Aa¡Aa¤Aa¥C“C”Aa½Aa¾";
        assertEquals(expectedResult, BibtexKeyGenerator.cleanKey(accents, true));

        expectedResult = "AƒA£aº¼aº½Ae¨Ae©A‘A±A•AµAa¨Aa©a»¸a»¹";
        accents = "ÃƒÃ£áº¼áº½Ä¨Ä©Ã‘Ã±Ã•ÃµÅ¨Å©á»¸á»¹";
        assertEquals(expectedResult, BibtexKeyGenerator.cleanKey(accents, true));

        accents = "á¸Œ á¸� á¸¤ á¸¥ á¸¶ á¸· á¸¸ á¸¹ á¹‚ á¹ƒ á¹† á¹‡ á¹š á¹› á¹œ á¹� á¹¢ á¹£ á¹¬ á¹­";
        expectedResult = "a¸OEa¸�a¸¤a¸¥a¸¶a¸·a¸¸a¸¹a¹‚a¹ƒa¹†a¹‡a¹sa¹›a¹oea¹�a¹¢a¹£a¹¬a¹­";
        assertEquals(expectedResult, BibtexKeyGenerator.cleanKey(accents, true));

        String totest = "Ã€ Ã  Ãˆ Ã¨ ÃŒ Ã¬ Ã’ Ã² Ã™ Ã¹   Ã‚ Ã¢ Äˆ Ä‰ ÃŠ Ãª Äœ Ä� Ä¤ Ä¥ ÃŽ Ã® Ä´ Äµ Ã” Ã´ Åœ Å� Ã› Ã» Å´ Åµ Å¶ Å·  Ã„ Ã¤ Ã‹ Ã« Ã� Ã¯ Ã– Ã¶ Ãœ Ã¼ Å¸ Ã¿    "
                + "Ãƒ Ã£ áº¼ áº½ Ä¨ Ä© Ã‘ Ã± Ã• Ãµ Å¨ Å© á»¸ á»¹   Ã‡ Ã§ Ä¢ Ä£ Ä¶ Ä· Ä» Ä¼ Å… Å† Å– Å— Åž ÅŸ Å¢ Å£"
                + " Ç� ÇŽ ÄŒ Ä� ÄŽ Ä� Äš Ä› Ç� Ç� Ä½ Ä¾ Å‡ Åˆ Ç‘ Ç’ Å˜ Å™ Å  Å¡ Å¤ Å¥ Ç“ Ç” Å½ Å¾   " + "Ä€ Ä� Ä’ Ä“ Äª Ä« ÅŒ Å� Åª Å« È² È³"
                + "Ä‚ Äƒ Ä” Ä• Äž ÄŸ Ä¬ Ä­ ÅŽ Å� Å¬ Å­   " + "ÄŠ Ä‹ Ä– Ä— Ä  Ä¡ Ä° Ä± Å» Å¼   Ä„ Ä… Ä˜ Ä™ Ä® Ä¯ Çª Ç« Å² Å³   "
                + "á¸Œ á¸� á¸¤ á¸¥ á¸¶ á¸· á¸¸ á¸¹ á¹‚ á¹ƒ á¹† á¹‡ á¹š á¹› á¹œ á¹� á¹¢ á¹£ á¹¬ á¹­   ";
        String expectedResults = "A€A AˆA¨AOEA¬A’A²A™A¹A‚A¢AeˆAe‰ASAªAeoeAe�Ae¤Ae¥AZA®Ae´AeµA”A´AaoeAa�A›A»Aa´AaµAa¶Aa·A„A¤A‹A«A�A¯A–A¶AoeA¼Aa¸A¿AƒA£aº¼aº½Ae¨Ae©A‘A±A•AµAa¨Aa©a»¸a»¹A‡A§Ae¢Ae£Ae¶Ae·Ae»Ae¼Aa…Aa†Aa–Aa—AazAaYAa¢Aa£C�CZAeOEAe�AeZAe�AesAe›C�C�Ae½Ae¾Aa‡AaˆC‘C’Aa˜Aa™Aa Aa¡Aa¤Aa¥C“C”Aa½Aa¾Ae€Ae�Ae’Ae“AeªAe«AaOEAa�AaªAa«E²E³Ae‚AeƒAe”Ae•AezAeYAe¬Ae­AaZAa�Aa¬Aa­AeSAe‹Ae–Ae—Ae Ae¡Ae°Ae±Aa»Aa¼Ae„Ae…Ae˜Ae™Ae®Ae¯CªC«Aa²Aa³a¸OEa¸�a¸¤a¸¥a¸¶a¸·a¸¸a¸¹a¹‚a¹ƒa¹†a¹‡a¹sa¹›a¹oea¹�a¹¢a¹£a¹¬a¹­";
        assertEquals(expectedResults, BibtexKeyGenerator.cleanKey(totest, true));
    }

    @Test
    public void testFirstAuthor() {
        assertEquals("Newton", BibtexKeyGenerator.firstAuthor(AUTHOR_STRING_FIRSTNAME_INITIAL_LASTNAME_FULL_COUNT_5));
        assertEquals("Newton", BibtexKeyGenerator.firstAuthor(AUTHOR_STRING_FIRSTNAME_INITIAL_LASTNAME_FULL_COUNT_1));

        // https://sourceforge.net/forum/message.php?msg_id=4498555
        assertEquals("K{\\\"o}ning", BibtexKeyGenerator.firstAuthor("K{\\\"o}ning"));

        assertEquals("", BibtexKeyGenerator.firstAuthor(""));
    }

    public void testFirstAuthorNull() {
        assertThrows(NullPointerException.class, () -> BibtexKeyGenerator.firstAuthor(null));
    }

    @Test
    public void testUniversity() throws ParseException {
        Optional<BibEntry> entry = BibtexParser.singleFromString(
                "@ARTICLE{kohn, author={{Link{\\\"{o}}ping University}}}", importFormatPreferences, fileMonitor);
        assertEquals("UniLinkoeping",
                BibtexKeyGenerator.cleanKey(BibtexKeyGenerator.generateKey(entry.get(), "auth",
                        new BibDatabase()), true));
    }

    @Test
    public void testcrossrefUniversity() throws Exception {
        BibDatabase database = new BibDatabase();
        BibEntry entry1 = new BibEntry();
        entry1.setField(FieldName.CROSSREF, "entry2");
        BibEntry entry2 = new BibEntry();
        entry2.setCiteKey("entry2");
        entry2.setField(FieldName.AUTHOR, "{Link{\\\"{o}}ping University}}");
        database.insertEntry(entry1);
        database.insertEntry(entry2);

        assertEquals("UniLinkoeping",
                BibtexKeyGenerator.cleanKey(BibtexKeyGenerator.generateKey(entry1, "auth",
                        database), true));
    }

    @Test
    public void testDepartment() throws ParseException {
        Optional<BibEntry> entry = BibtexParser.singleFromString(
                "@ARTICLE{kohn, author={{Link{\\\"{o}}ping University, Department of Electrical Engineering}}}",
                importFormatPreferences, fileMonitor);
        assertEquals("UniLinkoepingEE",
                BibtexKeyGenerator.cleanKey(BibtexKeyGenerator.generateKey(entry.get(), "auth",
                        new BibDatabase()), true));
    }

    @Test
    public void testcrossrefDepartment() throws Exception {
        BibDatabase database = new BibDatabase();
        BibEntry entry1 = new BibEntry();
        entry1.setField(FieldName.CROSSREF, "entry2");
        BibEntry entry2 = new BibEntry();
        entry2.setCiteKey("entry2");
        entry2.setField(FieldName.AUTHOR, "{Link{\\\"{o}}ping University, Department of Electrical Engineering}}");
        database.insertEntry(entry1);
        database.insertEntry(entry2);

        assertEquals("UniLinkoepingEE",
                BibtexKeyGenerator.cleanKey(BibtexKeyGenerator.generateKey(entry1, "auth",
                        database), true));
    }

    @Test
    public void testSchool() throws ParseException {
        Optional<BibEntry> entry = BibtexParser.singleFromString(
                "@ARTICLE{kohn, author={{Link{\\\"{o}}ping University, School of Computer Engineering}}}",
                importFormatPreferences, fileMonitor);
        assertEquals("UniLinkoepingCE",
                BibtexKeyGenerator.cleanKey(BibtexKeyGenerator.generateKey(entry.get(), "auth",
                        new BibDatabase()), true));
    }

    @Test
    public void testcrossrefSchool() throws Exception {
        BibDatabase database = new BibDatabase();
        BibEntry entry1 = new BibEntry();
        entry1.setField(FieldName.CROSSREF, "entry2");
        BibEntry entry2 = new BibEntry();
        entry2.setCiteKey("entry2");
        entry2.setField(FieldName.AUTHOR, "{Link{\\\"{o}}ping University, School of Computer Engineering}}");
        database.insertEntry(entry1);
        database.insertEntry(entry2);

        assertEquals("UniLinkoepingCE",
                BibtexKeyGenerator.cleanKey(BibtexKeyGenerator.generateKey(entry1, "auth",
                        database), true));
    }

    @Test
    public void testInstituteOfTechnology() throws ParseException {
        Optional<BibEntry> entry = BibtexParser.singleFromString(
                "@ARTICLE{kohn, author={{Massachusetts Institute of Technology}}}", importFormatPreferences, fileMonitor);
        assertEquals("MIT",
                BibtexKeyGenerator.cleanKey(BibtexKeyGenerator.generateKey(entry.get(), "auth",
                        new BibDatabase()), true));
    }

    @Test
    public void testcrossrefInstituteOfTechnology() throws Exception {
        BibDatabase database = new BibDatabase();
        BibEntry entry1 = new BibEntry();
        entry1.setField(FieldName.CROSSREF, "entry2");
        BibEntry entry2 = new BibEntry();
        entry2.setCiteKey("entry2");
        entry2.setField(FieldName.AUTHOR, "{Massachusetts Institute of Technology}");
        database.insertEntry(entry1);
        database.insertEntry(entry2);

        assertEquals("MIT",
                BibtexKeyGenerator.cleanKey(BibtexKeyGenerator.generateKey(entry1, "auth",
                        database), true));
    }

    @Test
    public void testAuthIniN() {
        assertEquals("NMEB", BibtexKeyGenerator.authIniN(AUTHOR_STRING_FIRSTNAME_INITIAL_LASTNAME_FULL_COUNT_5, 4));
        assertEquals("NMEB", BibtexKeyGenerator.authIniN(AUTHOR_STRING_FIRSTNAME_INITIAL_LASTNAME_FULL_COUNT_4, 4));
        assertEquals("NeME", BibtexKeyGenerator.authIniN(AUTHOR_STRING_FIRSTNAME_INITIAL_LASTNAME_FULL_COUNT_3, 4));
        assertEquals("NeMa", BibtexKeyGenerator.authIniN(AUTHOR_STRING_FIRSTNAME_INITIAL_LASTNAME_FULL_COUNT_2, 4));
        assertEquals("Newt", BibtexKeyGenerator.authIniN(AUTHOR_STRING_FIRSTNAME_INITIAL_LASTNAME_FULL_COUNT_1, 4));
        assertEquals("", "");

        assertEquals("N", BibtexKeyGenerator.authIniN(AUTHOR_STRING_FIRSTNAME_INITIAL_LASTNAME_FULL_COUNT_1, 1));
        assertEquals("", BibtexKeyGenerator.authIniN(AUTHOR_STRING_FIRSTNAME_INITIAL_LASTNAME_FULL_COUNT_1, 0));
        assertEquals("", BibtexKeyGenerator.authIniN(AUTHOR_STRING_FIRSTNAME_INITIAL_LASTNAME_FULL_COUNT_1, -1));

        assertEquals("Newton", BibtexKeyGenerator.authIniN(AUTHOR_STRING_FIRSTNAME_INITIAL_LASTNAME_FULL_COUNT_1, 6));
        assertEquals("Newton", BibtexKeyGenerator.authIniN(AUTHOR_STRING_FIRSTNAME_INITIAL_LASTNAME_FULL_COUNT_1, 7));
    }

    public void testAuthIniNNull() {
        assertThrows(NullPointerException.class, () -> BibtexKeyGenerator.authIniN(null, 3));
    }

    @Test
    public void testAuthIniNEmptyReturnsEmpty() {
        assertEquals("", BibtexKeyGenerator.authIniN("", 1));
    }

    /**
     * Tests  [auth.auth.ea]
     */
    @Test
    public void authAuthEa() {
        assertEquals("Newton", BibtexKeyGenerator.authAuthEa(AUTHOR_STRING_FIRSTNAME_FULL_LASTNAME_FULL_COUNT_1));
        assertEquals("Newton.Maxwell",
                BibtexKeyGenerator.authAuthEa(AUTHOR_STRING_FIRSTNAME_FULL_LASTNAME_FULL_COUNT_2));
        assertEquals("Newton.Maxwell.ea",
                BibtexKeyGenerator.authAuthEa(AUTHOR_STRING_FIRSTNAME_FULL_LASTNAME_FULL_COUNT_3));
    }

    @Test
    public void testAuthEaEmptyReturnsEmpty() {
        assertEquals("", BibtexKeyGenerator.authAuthEa(""));
    }

    /**
     * Tests the [auth.etal] and [authEtAl] patterns
     */
    @Test
    public void testAuthEtAl() {
        // tests taken from the comments

        // [auth.etal]
        String delim = ".";
        String append = ".etal";
        assertEquals("Newton.etal",
                BibtexKeyGenerator.authEtal(AUTHOR_STRING_FIRSTNAME_FULL_LASTNAME_FULL_COUNT_3, delim, append));
        assertEquals("Newton.Maxwell",
                BibtexKeyGenerator.authEtal(AUTHOR_STRING_FIRSTNAME_FULL_LASTNAME_FULL_COUNT_2, delim, append));

        // [authEtAl]
        delim = "";
        append = "EtAl";
        assertEquals("NewtonEtAl",
                BibtexKeyGenerator.authEtal(AUTHOR_STRING_FIRSTNAME_FULL_LASTNAME_FULL_COUNT_3, delim, append));
        assertEquals("NewtonMaxwell",
                BibtexKeyGenerator.authEtal(AUTHOR_STRING_FIRSTNAME_FULL_LASTNAME_FULL_COUNT_2, delim, append));
    }

    /**
     * Test the [authshort] pattern
     */
    @Test
    public void testAuthShort() {
        // tests taken from the comments
        assertEquals("NME+", BibtexKeyGenerator.authshort(AUTHOR_STRING_FIRSTNAME_INITIAL_LASTNAME_FULL_COUNT_4));
        assertEquals("NME", BibtexKeyGenerator.authshort(AUTHOR_STRING_FIRSTNAME_INITIAL_LASTNAME_FULL_COUNT_3));
        assertEquals("NM", BibtexKeyGenerator.authshort(AUTHOR_STRING_FIRSTNAME_INITIAL_LASTNAME_FULL_COUNT_2));
        assertEquals("Newton", BibtexKeyGenerator.authshort(AUTHOR_STRING_FIRSTNAME_INITIAL_LASTNAME_FULL_COUNT_1));
    }

    @Test
    public void testAuthShortEmptyReturnsEmpty() {
        assertEquals("", BibtexKeyGenerator.authshort(""));
    }

    /**
     * Test the [authN_M] pattern
     */
    @Test
    public void authNM() {
        assertEquals("N", BibtexKeyGenerator.authNofMth(AUTHOR_STRING_FIRSTNAME_INITIAL_LASTNAME_FULL_COUNT_1, 1, 1));
        assertEquals("Max",
                BibtexKeyGenerator.authNofMth(AUTHOR_STRING_FIRSTNAME_INITIAL_LASTNAME_FULL_COUNT_2, 3, 2));
        assertEquals("New",
                BibtexKeyGenerator.authNofMth(AUTHOR_STRING_FIRSTNAME_INITIAL_LASTNAME_FULL_COUNT_3, 3, 1));
        assertEquals("Bo",
                BibtexKeyGenerator.authNofMth(AUTHOR_STRING_FIRSTNAME_INITIAL_LASTNAME_FULL_COUNT_4, 2, 4));
        assertEquals("Bohr",
                BibtexKeyGenerator.authNofMth(AUTHOR_STRING_FIRSTNAME_INITIAL_LASTNAME_FULL_COUNT_5, 6, 4));

        assertEquals("Aal",
                BibtexKeyGenerator.authNofMth(AUTHOR_STRING_FIRSTNAME_FULL_LASTNAME_FULL_WITH_VAN_COUNT_1, 3, 1));
        assertEquals("Less",
                BibtexKeyGenerator.authNofMth(AUTHOR_STRING_FIRSTNAME_FULL_LASTNAME_FULL_WITH_VAN_COUNT_2, 4, 2));

        assertEquals("", BibtexKeyGenerator.authNofMth("", 2, 4));
    }

    public void authNMThrowsNPE() {
        assertThrows(NullPointerException.class, () -> BibtexKeyGenerator.authNofMth(null, 2, 4));
    }

    /**
     * Tests [authForeIni]
     */
    @Test
    public void firstAuthorForenameInitials() {
        assertEquals("I", BibtexKeyGenerator
                .firstAuthorForenameInitials(AUTHOR_STRING_FIRSTNAME_INITIAL_LASTNAME_FULL_COUNT_1));
        assertEquals("I", BibtexKeyGenerator
                .firstAuthorForenameInitials(AUTHOR_STRING_FIRSTNAME_INITIAL_LASTNAME_FULL_COUNT_2));
        assertEquals("I",
                BibtexKeyGenerator.firstAuthorForenameInitials(AUTHOR_STRING_FIRSTNAME_FULL_LASTNAME_FULL_COUNT_1));
        assertEquals("I",
                BibtexKeyGenerator.firstAuthorForenameInitials(AUTHOR_STRING_FIRSTNAME_FULL_LASTNAME_FULL_COUNT_2));
    }

    /**
     * Tests [authFirstFull]
     */
    @Test
    public void firstAuthorVonAndLast() {
        assertEquals("vanderAalst", BibtexKeyGenerator
                .firstAuthorVonAndLast(AUTHOR_STRING_FIRSTNAME_FULL_LASTNAME_FULL_WITH_VAN_COUNT_1));
        assertEquals("vanderAalst", BibtexKeyGenerator
                .firstAuthorVonAndLast(AUTHOR_STRING_FIRSTNAME_FULL_LASTNAME_FULL_WITH_VAN_COUNT_2));
    }

    @Test
    public void firstAuthorVonAndLastNoVonInName() {
        assertEquals("Newton",
                BibtexKeyGenerator.firstAuthorVonAndLast(AUTHOR_STRING_FIRSTNAME_FULL_LASTNAME_FULL_COUNT_1));
        assertEquals("Newton",
                BibtexKeyGenerator.firstAuthorVonAndLast(AUTHOR_STRING_FIRSTNAME_FULL_LASTNAME_FULL_COUNT_2));
    }

    /**
     * Tests [authors]
     */
    @Test
    public void testAllAuthors() {
        assertEquals("Newton", BibtexKeyGenerator.allAuthors(AUTHOR_STRING_FIRSTNAME_INITIAL_LASTNAME_FULL_COUNT_1));
        assertEquals("NewtonMaxwell",
                BibtexKeyGenerator.allAuthors(AUTHOR_STRING_FIRSTNAME_INITIAL_LASTNAME_FULL_COUNT_2));
        assertEquals("NewtonMaxwellEinstein",
                BibtexKeyGenerator.allAuthors(AUTHOR_STRING_FIRSTNAME_INITIAL_LASTNAME_FULL_COUNT_3));
    }

    /**
     * Tests [authorsAlpha]
     */
    @Test
    public void authorsAlpha() {
        assertEquals("New", BibtexKeyGenerator.authorsAlpha(AUTHOR_STRING_FIRSTNAME_INITIAL_LASTNAME_FULL_COUNT_1));
        assertEquals("NM", BibtexKeyGenerator.authorsAlpha(AUTHOR_STRING_FIRSTNAME_INITIAL_LASTNAME_FULL_COUNT_2));
        assertEquals("NME", BibtexKeyGenerator.authorsAlpha(AUTHOR_STRING_FIRSTNAME_INITIAL_LASTNAME_FULL_COUNT_3));
        assertEquals("NMEB", BibtexKeyGenerator.authorsAlpha(AUTHOR_STRING_FIRSTNAME_INITIAL_LASTNAME_FULL_COUNT_4));
        assertEquals("NME+", BibtexKeyGenerator.authorsAlpha(AUTHOR_STRING_FIRSTNAME_INITIAL_LASTNAME_FULL_COUNT_5));

        assertEquals("vdAal",
                BibtexKeyGenerator.authorsAlpha(AUTHOR_STRING_FIRSTNAME_FULL_LASTNAME_FULL_WITH_VAN_COUNT_1));
        assertEquals("vdAvL",
                BibtexKeyGenerator.authorsAlpha(AUTHOR_STRING_FIRSTNAME_FULL_LASTNAME_FULL_WITH_VAN_COUNT_2));
    }

    /**
     * Tests [authorLast]
     */
    @Test
    public void lastAuthor() {
        assertEquals("Newton", BibtexKeyGenerator.lastAuthor(AUTHOR_STRING_FIRSTNAME_INITIAL_LASTNAME_FULL_COUNT_1));
        assertEquals("Maxwell", BibtexKeyGenerator.lastAuthor(AUTHOR_STRING_FIRSTNAME_INITIAL_LASTNAME_FULL_COUNT_2));
        assertEquals("Einstein",
                BibtexKeyGenerator.lastAuthor(AUTHOR_STRING_FIRSTNAME_INITIAL_LASTNAME_FULL_COUNT_3));
        assertEquals("Bohr", BibtexKeyGenerator.lastAuthor(AUTHOR_STRING_FIRSTNAME_INITIAL_LASTNAME_FULL_COUNT_4));
        assertEquals("Unknown", BibtexKeyGenerator.lastAuthor(AUTHOR_STRING_FIRSTNAME_INITIAL_LASTNAME_FULL_COUNT_5));

        assertEquals("Aalst",
                BibtexKeyGenerator.lastAuthor(AUTHOR_STRING_FIRSTNAME_FULL_LASTNAME_FULL_WITH_VAN_COUNT_1));
        assertEquals("Lessen",
                BibtexKeyGenerator.lastAuthor(AUTHOR_STRING_FIRSTNAME_FULL_LASTNAME_FULL_WITH_VAN_COUNT_2));
    }

    /**
     * Tests [authorLastForeIni]
     */
    @Test
    public void lastAuthorForenameInitials() {
        assertEquals("I",
                BibtexKeyGenerator.lastAuthorForenameInitials(AUTHOR_STRING_FIRSTNAME_INITIAL_LASTNAME_FULL_COUNT_1));
        assertEquals("J",
                BibtexKeyGenerator.lastAuthorForenameInitials(AUTHOR_STRING_FIRSTNAME_INITIAL_LASTNAME_FULL_COUNT_2));
        assertEquals("A",
                BibtexKeyGenerator.lastAuthorForenameInitials(AUTHOR_STRING_FIRSTNAME_INITIAL_LASTNAME_FULL_COUNT_3));
        assertEquals("N",
                BibtexKeyGenerator.lastAuthorForenameInitials(AUTHOR_STRING_FIRSTNAME_INITIAL_LASTNAME_FULL_COUNT_4));
        assertEquals("H",
                BibtexKeyGenerator.lastAuthorForenameInitials(AUTHOR_STRING_FIRSTNAME_INITIAL_LASTNAME_FULL_COUNT_5));

        assertEquals("W", BibtexKeyGenerator
                .lastAuthorForenameInitials(AUTHOR_STRING_FIRSTNAME_FULL_LASTNAME_FULL_WITH_VAN_COUNT_1));
        assertEquals("T", BibtexKeyGenerator
                .lastAuthorForenameInitials(AUTHOR_STRING_FIRSTNAME_FULL_LASTNAME_FULL_WITH_VAN_COUNT_2));
    }

    /**
     * Tests [authorIni]
     */
    @Test
    public void oneAuthorPlusIni() {
        assertEquals("Newto",
                BibtexKeyGenerator.oneAuthorPlusIni(AUTHOR_STRING_FIRSTNAME_INITIAL_LASTNAME_FULL_COUNT_1));
        assertEquals("NewtoM",
                BibtexKeyGenerator.oneAuthorPlusIni(AUTHOR_STRING_FIRSTNAME_INITIAL_LASTNAME_FULL_COUNT_2));
        assertEquals("NewtoME",
                BibtexKeyGenerator.oneAuthorPlusIni(AUTHOR_STRING_FIRSTNAME_INITIAL_LASTNAME_FULL_COUNT_3));
        assertEquals("NewtoMEB",
                BibtexKeyGenerator.oneAuthorPlusIni(AUTHOR_STRING_FIRSTNAME_INITIAL_LASTNAME_FULL_COUNT_4));
        assertEquals("NewtoMEBU",
                BibtexKeyGenerator.oneAuthorPlusIni(AUTHOR_STRING_FIRSTNAME_INITIAL_LASTNAME_FULL_COUNT_5));

        assertEquals("Aalst",
                BibtexKeyGenerator.oneAuthorPlusIni(AUTHOR_STRING_FIRSTNAME_FULL_LASTNAME_FULL_WITH_VAN_COUNT_1));
        assertEquals("AalstL",
                BibtexKeyGenerator.oneAuthorPlusIni(AUTHOR_STRING_FIRSTNAME_FULL_LASTNAME_FULL_WITH_VAN_COUNT_2));
    }

    /**
     * Tests the [authorsN] pattern. -> [authors1]
     */
    @Test
    public void testNAuthors1() {
        assertEquals("Newton", BibtexKeyGenerator.nAuthors(AUTHOR_STRING_FIRSTNAME_INITIAL_LASTNAME_FULL_COUNT_1, 1));
        assertEquals("NewtonEtAl",
                BibtexKeyGenerator.nAuthors(AUTHOR_STRING_FIRSTNAME_INITIAL_LASTNAME_FULL_COUNT_2, 1));
        assertEquals("NewtonEtAl",
                BibtexKeyGenerator.nAuthors(AUTHOR_STRING_FIRSTNAME_INITIAL_LASTNAME_FULL_COUNT_3, 1));
        assertEquals("NewtonEtAl",
                BibtexKeyGenerator.nAuthors(AUTHOR_STRING_FIRSTNAME_INITIAL_LASTNAME_FULL_COUNT_4, 1));
    }

    @Test
    public void testNAuthors1EmptyReturnEmpty() {
        assertEquals("", BibtexKeyGenerator.nAuthors("", 1));
    }

    /**
     * Tests the [authorsN] pattern. -> [authors3]
     */
    @Test
    public void testNAuthors3() {
        assertEquals("Newton", BibtexKeyGenerator.nAuthors(AUTHOR_STRING_FIRSTNAME_INITIAL_LASTNAME_FULL_COUNT_1, 3));
        assertEquals("NewtonMaxwell",
                BibtexKeyGenerator.nAuthors(AUTHOR_STRING_FIRSTNAME_INITIAL_LASTNAME_FULL_COUNT_2, 3));
        assertEquals("NewtonMaxwellEinstein",
                BibtexKeyGenerator.nAuthors(AUTHOR_STRING_FIRSTNAME_INITIAL_LASTNAME_FULL_COUNT_3, 3));
        assertEquals("NewtonMaxwellEinsteinEtAl",
                BibtexKeyGenerator.nAuthors(AUTHOR_STRING_FIRSTNAME_INITIAL_LASTNAME_FULL_COUNT_4, 3));
    }

    @Test
    public void testFirstPage() {
        assertEquals("7", BibtexKeyGenerator.firstPage("7--27"));
        assertEquals("27", BibtexKeyGenerator.firstPage("--27"));
        assertEquals("", BibtexKeyGenerator.firstPage(""));
        assertEquals("42", BibtexKeyGenerator.firstPage("42--111"));
        assertEquals("7", BibtexKeyGenerator.firstPage("7,41,73--97"));
        assertEquals("7", BibtexKeyGenerator.firstPage("41,7,73--97"));
        assertEquals("43", BibtexKeyGenerator.firstPage("43+"));
    }

    public void testFirstPageNull() {
        assertThrows(NullPointerException.class, () -> BibtexKeyGenerator.firstPage(null));
    }

    @Test
    public void testPagePrefix() {
        assertEquals("L", BibtexKeyGenerator.pagePrefix("L7--27"));
        assertEquals("L--", BibtexKeyGenerator.pagePrefix("L--27"));
        assertEquals("L", BibtexKeyGenerator.pagePrefix("L"));
        assertEquals("L", BibtexKeyGenerator.pagePrefix("L42--111"));
        assertEquals("L", BibtexKeyGenerator.pagePrefix("L7,L41,L73--97"));
        assertEquals("L", BibtexKeyGenerator.pagePrefix("L41,L7,L73--97"));
        assertEquals("L", BibtexKeyGenerator.pagePrefix("L43+"));
        assertEquals("", BibtexKeyGenerator.pagePrefix("7--27"));
        assertEquals("--", BibtexKeyGenerator.pagePrefix("--27"));
        assertEquals("", BibtexKeyGenerator.pagePrefix(""));
        assertEquals("", BibtexKeyGenerator.pagePrefix("42--111"));
        assertEquals("", BibtexKeyGenerator.pagePrefix("7,41,73--97"));
        assertEquals("", BibtexKeyGenerator.pagePrefix("41,7,73--97"));
        assertEquals("", BibtexKeyGenerator.pagePrefix("43+"));
    }

    public void testPagePrefixNull() {
        assertThrows(NullPointerException.class, () -> BibtexKeyGenerator.pagePrefix(null));
    }

    @Test
    public void testLastPage() {

        assertEquals("27", BibtexKeyGenerator.lastPage("7--27"));
        assertEquals("27", BibtexKeyGenerator.lastPage("--27"));
        assertEquals("", BibtexKeyGenerator.lastPage(""));
        assertEquals("111", BibtexKeyGenerator.lastPage("42--111"));
        assertEquals("97", BibtexKeyGenerator.lastPage("7,41,73--97"));
        assertEquals("97", BibtexKeyGenerator.lastPage("7,41,97--73"));
        assertEquals("43", BibtexKeyGenerator.lastPage("43+"));
    }

    public void testLastPageNull() {
        assertThrows(NullPointerException.class, () -> BibtexKeyGenerator.lastPage(null));
    }

    /**
     * Tests [veryShortTitle]
     */
    @Test
    public void veryShortTitle() {
        // veryShortTitle is getTitleWords with "1" as count
        int count = 1;
        assertEquals("application",
                BibtexKeyGenerator.getTitleWords(count,
                        BibtexKeyGenerator.removeSmallWords(TITLE_STRING_ALL_LOWER_FOUR_SMALL_WORDS_ONE_EN_DASH)));
        assertEquals("BPEL", BibtexKeyGenerator.getTitleWords(count,
                BibtexKeyGenerator.removeSmallWords(
                        TITLE_STRING_ALL_LOWER_FIRST_WORD_IN_BRACKETS_TWO_SMALL_WORDS_SMALL_WORD_AFTER_COLON)));
        assertEquals("Process", BibtexKeyGenerator.getTitleWords(count,
                BibtexKeyGenerator.removeSmallWords(TITLE_STRING_CASED)));
        assertEquals("BPMN",
                BibtexKeyGenerator.getTitleWords(count,
                        BibtexKeyGenerator.removeSmallWords(TITLE_STRING_CASED_ONE_UPPER_WORD_ONE_SMALL_WORD)));
        assertEquals("Difference", BibtexKeyGenerator.getTitleWords(count,
                BibtexKeyGenerator.removeSmallWords(TITLE_STRING_CASED_TWO_SMALL_WORDS_SMALL_WORD_AT_THE_BEGINNING)));
        assertEquals("Cloud",
                BibtexKeyGenerator.getTitleWords(count,
                        BibtexKeyGenerator
                                .removeSmallWords(TITLE_STRING_CASED_TWO_SMALL_WORDS_SMALL_WORD_AFTER_COLON)));
        assertEquals("Towards",
                BibtexKeyGenerator.getTitleWords(count,
                        BibtexKeyGenerator.removeSmallWords(TITLE_STRING_CASED_TWO_SMALL_WORDS_ONE_CONNECTED_WORD)));
        assertEquals("Measurement",
                BibtexKeyGenerator.getTitleWords(count,
                        BibtexKeyGenerator
                                .removeSmallWords(TITLE_STRING_CASED_FOUR_SMALL_WORDS_TWO_CONNECTED_WORDS)));
    }

    /**
     * Tests [shortTitle]
     */
    @Test
    public void shortTitle() {
        // shortTitle is getTitleWords with "3" as count
        int count = 3;
        assertEquals("application migration effort",
                BibtexKeyGenerator.getTitleWords(count, TITLE_STRING_ALL_LOWER_FOUR_SMALL_WORDS_ONE_EN_DASH));
        assertEquals("BPEL conformance in", BibtexKeyGenerator.getTitleWords(count,
                TITLE_STRING_ALL_LOWER_FIRST_WORD_IN_BRACKETS_TWO_SMALL_WORDS_SMALL_WORD_AFTER_COLON));
        assertEquals("Process Viewing Patterns", BibtexKeyGenerator.getTitleWords(count, TITLE_STRING_CASED));
        assertEquals("BPMN Conformance in",
                BibtexKeyGenerator.getTitleWords(count, TITLE_STRING_CASED_ONE_UPPER_WORD_ONE_SMALL_WORD));
        assertEquals("The Difference Between", BibtexKeyGenerator.getTitleWords(count,
                TITLE_STRING_CASED_TWO_SMALL_WORDS_SMALL_WORD_AT_THE_BEGINNING));
        assertEquals("Cloud Computing: The",
                BibtexKeyGenerator.getTitleWords(count, TITLE_STRING_CASED_TWO_SMALL_WORDS_SMALL_WORD_AFTER_COLON));
        assertEquals("Towards Choreography based",
                BibtexKeyGenerator.getTitleWords(count, TITLE_STRING_CASED_TWO_SMALL_WORDS_ONE_CONNECTED_WORD));
        assertEquals("On the Measurement",
                BibtexKeyGenerator.getTitleWords(count, TITLE_STRING_CASED_FOUR_SMALL_WORDS_TWO_CONNECTED_WORDS));
    }

    /**
    * Tests [camel]
    */
    @Test
    public void camel() {
        // camel capitalises and concatenates all the words of the title
        assertEquals("ApplicationMigrationEffortInTheCloudTheCaseOfCloudPlatforms",
                BibtexKeyGenerator.getCamelizedTitle(TITLE_STRING_ALL_LOWER_FOUR_SMALL_WORDS_ONE_EN_DASH));
        assertEquals("BPELConformanceInOpenSourceEnginesTheCaseOfStaticAnalysis",
                BibtexKeyGenerator.getCamelizedTitle(
                        TITLE_STRING_ALL_LOWER_FIRST_WORD_IN_BRACKETS_TWO_SMALL_WORDS_SMALL_WORD_AFTER_COLON));
        assertEquals("ProcessViewingPatterns", BibtexKeyGenerator.getCamelizedTitle(TITLE_STRING_CASED));
        assertEquals("BPMNConformanceInOpenSourceEngines",
                BibtexKeyGenerator.getCamelizedTitle(TITLE_STRING_CASED_ONE_UPPER_WORD_ONE_SMALL_WORD));
        assertEquals("TheDifferenceBetweenGraphBasedAndBlockStructuredBusinessProcessModellingLanguages",
                BibtexKeyGenerator.getCamelizedTitle(
                        TITLE_STRING_CASED_TWO_SMALL_WORDS_SMALL_WORD_AT_THE_BEGINNING));
        assertEquals("CloudComputingTheNextRevolutionInIT",
                BibtexKeyGenerator.getCamelizedTitle(TITLE_STRING_CASED_TWO_SMALL_WORDS_SMALL_WORD_AFTER_COLON));
        assertEquals("TowardsChoreographyBasedProcessDistributionInTheCloud",
                BibtexKeyGenerator.getCamelizedTitle(TITLE_STRING_CASED_TWO_SMALL_WORDS_ONE_CONNECTED_WORD));
        assertEquals("OnTheMeasurementOfDesignTimeAdaptabilityForProcessBasedSystems",
                BibtexKeyGenerator.getCamelizedTitle(TITLE_STRING_CASED_FOUR_SMALL_WORDS_TWO_CONNECTED_WORDS));
    }

    /**
     * Tests [title]
     */
    @Test
    public void title() {
        // title capitalises the significant words of the title
        // for the title case the concatenation happens at formatting, which is tested in MakeLabelWithDatabaseTest.java
        assertEquals("Application Migration Effort in the Cloud the Case of Cloud Platforms",
                BibtexKeyGenerator
                        .camelizeSignificantWordsInTitle(TITLE_STRING_ALL_LOWER_FOUR_SMALL_WORDS_ONE_EN_DASH));
        assertEquals("BPEL Conformance in Open Source Engines: the Case of Static Analysis",
                BibtexKeyGenerator.camelizeSignificantWordsInTitle(
                        TITLE_STRING_ALL_LOWER_FIRST_WORD_IN_BRACKETS_TWO_SMALL_WORDS_SMALL_WORD_AFTER_COLON));
        assertEquals("Process Viewing Patterns",
                BibtexKeyGenerator.camelizeSignificantWordsInTitle(TITLE_STRING_CASED));
        assertEquals("BPMN Conformance in Open Source Engines",
                BibtexKeyGenerator
                        .camelizeSignificantWordsInTitle(TITLE_STRING_CASED_ONE_UPPER_WORD_ONE_SMALL_WORD));
        assertEquals("The Difference between Graph Based and Block Structured Business Process Modelling Languages",
                BibtexKeyGenerator.camelizeSignificantWordsInTitle(
                        TITLE_STRING_CASED_TWO_SMALL_WORDS_SMALL_WORD_AT_THE_BEGINNING));
        assertEquals("Cloud Computing: the Next Revolution in IT",
                BibtexKeyGenerator.camelizeSignificantWordsInTitle(
                        TITLE_STRING_CASED_TWO_SMALL_WORDS_SMALL_WORD_AFTER_COLON));
        assertEquals("Towards Choreography Based Process Distribution in the Cloud",
                BibtexKeyGenerator
                        .camelizeSignificantWordsInTitle(TITLE_STRING_CASED_TWO_SMALL_WORDS_ONE_CONNECTED_WORD));
        assertEquals("On the Measurement of Design Time Adaptability for Process Based Systems",
                BibtexKeyGenerator.camelizeSignificantWordsInTitle(
                        TITLE_STRING_CASED_FOUR_SMALL_WORDS_TWO_CONNECTED_WORDS));
    }

    @Test
    public void keywordNKeywordsSeparatedBySpace() {
        BibEntry entry = new BibEntry();
        entry.setField("keywords", "w1, w2a w2b, w3");

        String result = BibtexKeyGenerator.generateKey(entry, "keyword1");
        assertEquals("w1", result);

        // check keywords with space
        result = BibtexKeyGenerator.generateKey(entry, "keyword2");
        assertEquals("w2aw2b", result);

        // check out of range
        result = BibtexKeyGenerator.generateKey(entry, "keyword4");
        assertEquals("", result);
    }

    @Test
    public void crossrefkeywordNKeywordsSeparatedBySpace() {
        BibDatabase database = new BibDatabase();
        BibEntry entry1 = new BibEntry();
        BibEntry entry2 = new BibEntry();
        entry1.setField(FieldName.CROSSREF, "entry2");
        entry2.setCiteKey("entry2");
        database.insertEntry(entry2);
        database.insertEntry(entry1);
        entry2.setField("keywords", "w1, w2a w2b, w3");

        String result = BibtexKeyGenerator.generateKey(entry1, "keyword1", database);

        assertEquals("w1", result);
    }

    @Test
    public void keywordsNKeywordsSeparatedBySpace() {
        BibEntry entry = new BibEntry();
        entry.setField("keywords", "w1, w2a w2b, w3");

        // all keywords
        String result = BibtexKeyGenerator.generateKey(entry, "keywords");
        assertEquals("w1w2aw2bw3", result);

        // check keywords with space
        result = BibtexKeyGenerator.generateKey(entry, "keywords2");
        assertEquals("w1w2aw2b", result);

        // check out of range
        result = BibtexKeyGenerator.generateKey(entry, "keywords55");
        assertEquals("w1w2aw2bw3", result);
    }

    @Test
    public void crossrefkeywordsNKeywordsSeparatedBySpace() {
        BibDatabase database = new BibDatabase();
        BibEntry entry1 = new BibEntry();
        BibEntry entry2 = new BibEntry();
        entry1.setField(FieldName.CROSSREF, "entry2");
        entry2.setCiteKey("entry2");
        database.insertEntry(entry2);
        database.insertEntry(entry1);
        entry2.setField("keywords", "w1, w2a w2b, w3");

        String result = BibtexKeyGenerator.generateKey(entry1, "keywords", database);

        assertEquals("w1w2aw2bw3", result);
    }

    @Test
    public void testCheckLegalKeyEnforceLegal() {
        assertEquals("AAAA", BibtexKeyGenerator.cleanKey("AA AA", true));
        assertEquals("SPECIALCHARS", BibtexKeyGenerator.cleanKey("SPECIAL CHARS#{\\\"}~,^", true));
        assertEquals("", BibtexKeyGenerator.cleanKey("\n\t\r", true));
    }

    @Test
    public void testCheckLegalKeyDoNotEnforceLegal() {
        assertEquals("AAAA", BibtexKeyGenerator.cleanKey("AA AA", false));
        assertEquals("SPECIALCHARS#~^", BibtexKeyGenerator.cleanKey("SPECIAL CHARS#{\\\"}~,^", false));
        assertEquals("", BibtexKeyGenerator.cleanKey("\n\t\r", false));
    }

    @Test
    public void testCheckLegalNullInNullOut() {
        assertThrows(NullPointerException.class, () -> BibtexKeyGenerator.cleanKey(null, true));
        assertThrows(NullPointerException.class, () -> BibtexKeyGenerator.cleanKey(null, false));
    }

    @Test
    public void testApplyModifiers() {
        BibEntry entry = new BibEntry();
        entry.setField("title", "Green Scheduling of Whatever");
        assertEquals("GSo", BibtexKeyGenerator.generateKey(entry, "shorttitleINI"));
        assertEquals("GreenSchedulingof", BibtexKeyGenerator.generateKey(entry, "shorttitle",
                new BibDatabase()));
    }

    @Test
    public void testcrossrefShorttitle() {
        BibDatabase database = new BibDatabase();
        BibEntry entry1 = new BibEntry();
        BibEntry entry2 = new BibEntry();
        entry1.setField(FieldName.CROSSREF, "entry2");
        entry2.setCiteKey("entry2");
        database.insertEntry(entry2);
        database.insertEntry(entry1);
        entry2.setField("title", "Green Scheduling of Whatever");

        assertEquals("GreenSchedulingof", BibtexKeyGenerator.generateKey(entry1, "shorttitle",
                database));
    }

    @Test
    public void testcrossrefShorttitleInitials() {
        BibDatabase database = new BibDatabase();
        BibEntry entry1 = new BibEntry();
        BibEntry entry2 = new BibEntry();
        entry1.setField(FieldName.CROSSREF, "entry2");
        entry2.setCiteKey("entry2");
        database.insertEntry(entry2);
        database.insertEntry(entry1);
        entry2.setField("title", "Green Scheduling of Whatever");

        assertEquals("GSo", BibtexKeyGenerator.generateKey(entry1, "shorttitleINI", database));
    }

    @Test
    public void generateKeyStripsColonFromTitle() throws Exception {
        BibEntry entry = new BibEntry();
        entry.setField("title", "Green Scheduling of: Whatever");
        assertEquals("GreenSchedulingOfWhatever", BibtexKeyGenerator.generateKey(entry, "title"));
    }

    @Test
    public void generateKeyStripsApostropheFromTitle() throws Exception {
        BibEntry entry = new BibEntry();
        entry.setField("title", "Green Scheduling of `Whatever`");
        assertEquals("GreenSchedulingofWhatever", BibtexKeyGenerator.generateKey(entry, "title"));
    }

    @Test
    public void generateKeyWithOneModifier() throws Exception {
        BibEntry entry = new BibEntry();
        entry.setField("title", "The Interesting Title");
        assertEquals("theinterestingtitle", BibtexKeyGenerator.generateKey(entry, "title:lower"));
    }

    @Test
    public void generateKeyWithTwoModifiers() throws Exception {
        BibEntry entry = new BibEntry();
        entry.setField("title", "The Interesting Title");
        assertEquals("theinterestingtitle", BibtexKeyGenerator.generateKey(entry, "title:lower:(_)"));
    }
}
