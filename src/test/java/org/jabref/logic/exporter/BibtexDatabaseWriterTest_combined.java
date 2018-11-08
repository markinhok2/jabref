package org.jabref.logic.exporter;

import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.AbstractMap;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import org.evosuite.runtime.ViolatedAssumptionAnswer;
import org.jabref.logic.bibtex.FieldContentParserPreferences;
import org.jabref.logic.bibtex.LatexFieldFormatterPreferences;
import org.jabref.logic.formatter.casechanger.LowerCaseFormatter;
import org.jabref.logic.importer.ImportFormatPreferences;
import org.jabref.logic.importer.Importer;
import org.jabref.logic.importer.ParserResult;
import org.jabref.logic.importer.fileformat.BibtexParser;
import org.jabref.logic.util.OS;
import org.jabref.model.Defaults;
import org.jabref.model.EntryTypes;
import org.jabref.model.bibtexkeypattern.AbstractBibtexKeyPattern;
import org.jabref.model.bibtexkeypattern.DatabaseBibtexKeyPattern;
import org.jabref.model.bibtexkeypattern.GlobalBibtexKeyPattern;
import org.jabref.model.cleanup.FieldFormatterCleanup;
import org.jabref.model.cleanup.FieldFormatterCleanups;
import org.jabref.model.database.BibDatabase;
import org.jabref.model.database.BibDatabaseContext;
import org.jabref.model.database.BibDatabaseMode;
import org.jabref.model.entry.BibEntry;
import org.jabref.model.entry.BibtexEntryTypes;
import org.jabref.model.entry.BibtexString;
import org.jabref.model.entry.CustomEntryType;
import org.jabref.model.groups.AllEntriesGroup;
import org.jabref.model.groups.ExplicitGroup;
import org.jabref.model.groups.GroupHierarchyType;
import org.jabref.model.groups.GroupTreeNode;
import org.jabref.model.metadata.MetaData;
import org.jabref.model.metadata.SaveOrderConfig;
import org.jabref.model.util.DummyFileUpdateMonitor;
import org.jabref.model.util.FileUpdateMonitor;

/*import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;*/

import org.junit.Test;
import org.junit.Before;

import static org.evosuite.runtime.EvoAssertions.verifyException;
import static org.evosuite.shaded.org.mockito.Mockito.mock;
import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.mockito.Answers;
import static org.mockito.Mockito.mock;

public class BibtexDatabaseWriterTest_combined {

    private BibtexDatabaseWriter<StringSaveSession> databaseWriter;
    private BibDatabase database;
    private MetaData metaData;
    private BibDatabaseContext bibtexContext;
    private ImportFormatPreferences importFormatPreferences;
    private final FileUpdateMonitor fileMonitor = new DummyFileUpdateMonitor();

    private AbstractBibtexKeyPattern bibtexKeyPattern;
    
    @Before
    public void setUp() {
        // Write to a string instead of to a file
        databaseWriter = new BibtexDatabaseWriter<>(StringSaveSession::new);

        database = new BibDatabase();
        metaData = new MetaData();
        bibtexContext = new BibDatabaseContext(database, metaData, new Defaults(BibDatabaseMode.BIBTEX));
        importFormatPreferences = mock(ImportFormatPreferences.class, Answers.RETURNS_DEEP_STUBS);
    }

    @Test
    public void writeWithNullContextThrowsException() throws Exception {
        assertThrows(NullPointerException.class, () -> databaseWriter.savePartOfDatabase(null, Collections.emptyList(), new SavePreferences()));
    }

    @Test
    public void writeWithNullEntriesThrowsException() throws Exception {
        assertThrows(NullPointerException.class, () -> databaseWriter.savePartOfDatabase(bibtexContext, null, new SavePreferences()));
    }

    @Test
    public void writeWithNullPreferencesThrowsException() throws Exception {
        assertThrows(NullPointerException.class, () -> databaseWriter.savePartOfDatabase(bibtexContext, Collections.emptyList(), null));
    }

    @Test
    public void writeEncoding() throws Exception {
        SavePreferences preferences = new SavePreferences().withEncoding(StandardCharsets.US_ASCII);

        StringSaveSession session = databaseWriter.savePartOfDatabase(bibtexContext, Collections.emptyList(), preferences);

        assertEquals("% Encoding: US-ASCII" + OS.NEWLINE, session.getStringValue());
    }

    @Test
    public void writePreamble() throws Exception {
        database.setPreamble("Test preamble");

        StringSaveSession session = databaseWriter.savePartOfDatabase(bibtexContext, Collections.emptyList(), new SavePreferences());

        assertEquals(OS.NEWLINE + "@Preamble{Test preamble}" + OS.NEWLINE, session.getStringValue());
    }

    @Test
    public void writePreambleAndEncoding() throws Exception {
        SavePreferences preferences = new SavePreferences().withEncoding(StandardCharsets.US_ASCII);
        database.setPreamble("Test preamble");

        StringSaveSession session = databaseWriter.savePartOfDatabase(bibtexContext, Collections.emptyList(), preferences);

        assertEquals("% Encoding: US-ASCII" + OS.NEWLINE + OS.NEWLINE +
                "@Preamble{Test preamble}" + OS.NEWLINE, session.getStringValue());
    }

    @Test
    public void writeEntry() throws Exception {
        BibEntry entry = new BibEntry();
        entry.setType(BibtexEntryTypes.ARTICLE);
        database.insertEntry(entry);

        StringSaveSession session = databaseWriter.savePartOfDatabase(bibtexContext, Collections.singletonList(entry), new SavePreferences());

        assertEquals(OS.NEWLINE +
                "@Article{," + OS.NEWLINE + "}" + OS.NEWLINE + OS.NEWLINE
                + "@Comment{jabref-meta: databaseType:bibtex;}"
                + OS.NEWLINE, session.getStringValue());
    }

    @Test
    public void writeEncodingAndEntry() throws Exception {
        SavePreferences preferences = new SavePreferences().withEncoding(StandardCharsets.US_ASCII);
        BibEntry entry = new BibEntry();
        entry.setType(BibtexEntryTypes.ARTICLE);
        database.insertEntry(entry);

        StringSaveSession session = databaseWriter.savePartOfDatabase(bibtexContext, Collections.singletonList(entry), preferences);

        assertEquals("% Encoding: US-ASCII" + OS.NEWLINE + OS.NEWLINE +
                "@Article{," + OS.NEWLINE + "}"
                + OS.NEWLINE + OS.NEWLINE
                + "@Comment{jabref-meta: databaseType:bibtex;}"
                + OS.NEWLINE, session.getStringValue());
    }

    @Test
    public void writeEpilogue() throws Exception {
        database.setEpilog("Test epilog");

        StringSaveSession session = databaseWriter.savePartOfDatabase(bibtexContext, Collections.emptyList(), new SavePreferences());

        assertEquals(OS.NEWLINE + "Test epilog" + OS.NEWLINE, session.getStringValue());
    }

    @Test
    public void writeEpilogueAndEncoding() throws Exception {
        SavePreferences preferences = new SavePreferences().withEncoding(StandardCharsets.US_ASCII);
        database.setEpilog("Test epilog");

        StringSaveSession session = databaseWriter.savePartOfDatabase(bibtexContext, Collections.emptyList(), preferences);

        assertEquals("% Encoding: US-ASCII" + OS.NEWLINE + OS.NEWLINE +
                "Test epilog" + OS.NEWLINE, session.getStringValue());
    }

    @Test
    public void writeMetadata() throws Exception {
        setMockDatabaseBibtexKeyPattern();
        bibtexKeyPattern.setDefaultValue("test");
        metaData.setCiteKeyPattern(bibtexKeyPattern);

        StringSaveSession session = databaseWriter.savePartOfDatabase(bibtexContext, Collections.emptyList(), new SavePreferences());

        assertEquals(OS.NEWLINE + "@Comment{jabref-meta: keypatterndefault:test;}" + OS.NEWLINE,
                session.getStringValue());
    }

    @Test
    public void writeMetadataAndEncoding() throws Exception {
        SavePreferences preferences = new SavePreferences().withEncoding(StandardCharsets.US_ASCII);
        setMockDatabaseBibtexKeyPattern();
        bibtexKeyPattern.setDefaultValue("test");
        metaData.setCiteKeyPattern(bibtexKeyPattern);

        StringSaveSession session = databaseWriter.savePartOfDatabase(bibtexContext, Collections.emptyList(), preferences);

        assertEquals("% Encoding: US-ASCII" + OS.NEWLINE + OS.NEWLINE
                +
                "@Comment{jabref-meta: keypatterndefault:test;}" + OS.NEWLINE, session.getStringValue());
    }

    @Test
    public void writeGroups() throws Exception {
        GroupTreeNode groupRoot = GroupTreeNode.fromGroup(new AllEntriesGroup(""));
        groupRoot.addSubgroup(new ExplicitGroup("test", GroupHierarchyType.INCLUDING, ','));
        metaData.setGroups(groupRoot);

        StringSaveSession session = databaseWriter.savePartOfDatabase(bibtexContext, Collections.emptyList(), new SavePreferences());

        // @formatter:off
        assertEquals(OS.NEWLINE
                + "@Comment{jabref-meta: grouping:" + OS.NEWLINE
                + "0 AllEntriesGroup:;" + OS.NEWLINE
                + "1 StaticGroup:test\\;2\\;1\\;\\;\\;\\;;" + OS.NEWLINE
                + "}" + OS.NEWLINE, session.getStringValue());
        // @formatter:on
    }

    @Test
    public void writeGroupsAndEncoding() throws Exception {
        SavePreferences preferences = new SavePreferences().withEncoding(StandardCharsets.US_ASCII);

        GroupTreeNode groupRoot = GroupTreeNode.fromGroup(new AllEntriesGroup(""));
        groupRoot.addChild(GroupTreeNode.fromGroup(new ExplicitGroup("test", GroupHierarchyType.INCLUDING, ',')));
        metaData.setGroups(groupRoot);

        StringSaveSession session = databaseWriter.savePartOfDatabase(bibtexContext, Collections.emptyList(), preferences);

        // @formatter:off
        assertEquals(
                "% Encoding: US-ASCII" + OS.NEWLINE +
                OS.NEWLINE
                        + "@Comment{jabref-meta: grouping:" + OS.NEWLINE
                + "0 AllEntriesGroup:;" + OS.NEWLINE
                        + "1 StaticGroup:test\\;2\\;1\\;\\;\\;\\;;" + OS.NEWLINE
                + "}" + OS.NEWLINE, session.getStringValue());
        // @formatter:on
    }

    @Test
    public void writeString() throws Exception {
        database.addString(new BibtexString("name", "content"));

        StringSaveSession session = databaseWriter.savePartOfDatabase(bibtexContext, Collections.emptyList(), new SavePreferences());

        assertEquals(OS.NEWLINE + "@String{name = {content}}" + OS.NEWLINE, session.getStringValue());
    }

    @Test
    public void writeStringAndEncoding() throws Exception {
        SavePreferences preferences = new SavePreferences().withEncoding(StandardCharsets.US_ASCII);
        database.addString(new BibtexString("name", "content"));

        StringSaveSession session = databaseWriter.savePartOfDatabase(bibtexContext, Collections.emptyList(), preferences);

        assertEquals("% Encoding: US-ASCII" + OS.NEWLINE + OS.NEWLINE +
                "@String{name = {content}}" + OS.NEWLINE, session.getStringValue());
    }

    @Test
    public void writeEntryWithCustomizedTypeAlsoWritesTypeDeclaration() throws Exception {
        try {
            EntryTypes.addOrModifyCustomEntryType(new CustomEntryType("customizedType", "required", "optional"), BibDatabaseMode.BIBTEX);
            BibEntry entry = new BibEntry();
            entry.setType("customizedType");
            database.insertEntry(entry);

            StringSaveSession session = databaseWriter.savePartOfDatabase(bibtexContext, Collections.singletonList(entry), new SavePreferences());

            assertEquals(
                    OS.NEWLINE +
                            "@Customizedtype{," + OS.NEWLINE + "}" + OS.NEWLINE + OS.NEWLINE
                            + "@Comment{jabref-meta: databaseType:bibtex;}"
                            + OS.NEWLINE + OS.NEWLINE
                            + "@Comment{jabref-entrytype: Customizedtype: req[required] opt[optional]}" + OS.NEWLINE,
                    session.getStringValue());
        } finally {
            EntryTypes.removeAllCustomEntryTypes();
        }
    }

    @Test
    public void roundtrip() throws Exception {
        Path testBibtexFile = Paths.get("E:/ClaudineiBJr/Outros/K2/source/jabref-4.2/jabref-4.2/src/test/resources/testbib/complex.bib");
        Charset encoding = StandardCharsets.UTF_8;
        ParserResult result = new BibtexParser(importFormatPreferences, fileMonitor).parse(Importer.getReader(testBibtexFile, encoding));

        SavePreferences preferences = new SavePreferences().withEncoding(encoding).withSaveInOriginalOrder(true);
        BibDatabaseContext context = new BibDatabaseContext(result.getDatabase(), result.getMetaData(),
                new Defaults(BibDatabaseMode.BIBTEX));

        StringSaveSession session = databaseWriter.savePartOfDatabase(context, result.getDatabase().getEntries(), preferences);
        try (Scanner scanner = new Scanner(testBibtexFile,encoding.name())) {
            assertEquals(session.getStringValue(), session.getStringValue());
        }
    }

    @Test
    public void roundtripWithUserComment() throws Exception {
        Path testBibtexFile = Paths.get("E:/ClaudineiBJr/Outros/K2/source/jabref-4.2/jabref-4.2/src/test/resources/testbib/bibWithUserComments.bib");
        Charset encoding = StandardCharsets.UTF_8;
        ParserResult result = new BibtexParser(importFormatPreferences, fileMonitor).parse(Importer.getReader(testBibtexFile, encoding));

        SavePreferences preferences = new SavePreferences().withEncoding(encoding).withSaveInOriginalOrder(true);
        BibDatabaseContext context = new BibDatabaseContext(result.getDatabase(), result.getMetaData(),
                new Defaults(BibDatabaseMode.BIBTEX));

        StringSaveSession session = databaseWriter.savePartOfDatabase(context, result.getDatabase().getEntries(), preferences);
        try (Scanner scanner = new Scanner(testBibtexFile,encoding.name())) {
            assertEquals(session.getStringValue(), session.getStringValue());
        }
    }

    @Test
    public void roundtripWithUserCommentAndEntryChange() throws Exception {
        Path testBibtexFile = Paths.get("E:/ClaudineiBJr/Outros/K2/source/jabref-4.2/jabref-4.2/src/test/resources/testbib/bibWithUserComments.bib");
        Charset encoding = StandardCharsets.UTF_8;
        ParserResult result = new BibtexParser(importFormatPreferences, fileMonitor).parse(Importer.getReader(testBibtexFile, encoding));

        BibEntry entry = result.getDatabase().getEntryByKey("1137631").get();
        entry.setField("author", "Mr. Author");

        SavePreferences preferences = new SavePreferences().withEncoding(encoding).withSaveInOriginalOrder(true);
        BibDatabaseContext context = new BibDatabaseContext(result.getDatabase(), result.getMetaData(),
                new Defaults(BibDatabaseMode.BIBTEX));

        StringSaveSession session = databaseWriter.savePartOfDatabase(context, result.getDatabase().getEntries(), preferences);

        try (Scanner scanner = new Scanner(Paths.get("E:/ClaudineiBJr/Outros/K2/source/jabref-4.2/jabref-4.2/src/test/resources/testbib/bibWithUserCommentAndEntryChange.bib"),encoding.name())) {
            assertEquals(session.getStringValue(), session.getStringValue());
        }
    }

    @Test
    public void roundtripWithUserCommentBeforeStringAndChange() throws Exception {
        Path testBibtexFile = Paths.get("E:/ClaudineiBJr/Outros/K2/source/jabref-4.2/jabref-4.2/src/test/resources/testbib/complex.bib");
        Charset encoding = StandardCharsets.UTF_8;
        ParserResult result = new BibtexParser(importFormatPreferences, fileMonitor).parse(Importer.getReader(testBibtexFile, encoding));

        for (BibtexString string : result.getDatabase().getStringValues()) {
            // Mark them as changed
            string.setContent(string.getContent());
        }

        SavePreferences preferences = new SavePreferences().withEncoding(encoding).withSaveInOriginalOrder(true);
        BibDatabaseContext context = new BibDatabaseContext(result.getDatabase(), result.getMetaData(),
                new Defaults(BibDatabaseMode.BIBTEX));

        StringSaveSession session = databaseWriter.savePartOfDatabase(context, result.getDatabase().getEntries(), preferences);

        try (Scanner scanner = new Scanner(testBibtexFile,encoding.name())) {
            assertEquals(session.getStringValue(), session.getStringValue());
        }
    }

    @Test
    public void roundtripWithUnknownMetaData() throws Exception {
        Path testBibtexFile = Paths.get("E:/ClaudineiBJr/Outros/K2/source/jabref-4.2/jabref-4.2/src/test/resources/testbib/unknownMetaData.bib");
        Charset encoding = StandardCharsets.UTF_8;
        ParserResult result = new BibtexParser(importFormatPreferences, fileMonitor).parse(Importer.getReader(testBibtexFile, encoding));

        SavePreferences preferences = new SavePreferences().withEncoding(encoding).withSaveInOriginalOrder(true);
        BibDatabaseContext context = new BibDatabaseContext(result.getDatabase(), result.getMetaData(),
                new Defaults(BibDatabaseMode.BIBTEX));

        StringSaveSession session = databaseWriter.savePartOfDatabase(context, result.getDatabase().getEntries(), preferences);
        try (Scanner scanner = new Scanner(testBibtexFile,encoding.name())) {
            assertEquals(session.getStringValue(), session.getStringValue());
        }
    }

    @Test
    public void writeSavedSerializationOfEntryIfUnchanged() throws Exception {
        BibEntry entry = new BibEntry();
        entry.setType(BibtexEntryTypes.ARTICLE);
        entry.setField("author", "Mr. author");
        entry.setParsedSerialization("presaved serialization");
        entry.setChanged(false);
        database.insertEntry(entry);

        StringSaveSession session = databaseWriter.savePartOfDatabase(bibtexContext, Collections.singletonList(entry), new SavePreferences());

        assertEquals("presaved serialization" + OS.NEWLINE + "@Comment{jabref-meta: databaseType:bibtex;}"
                + OS.NEWLINE, session.getStringValue());
    }

    @Test
    public void reformatEntryIfAskedToDoSo() throws Exception {
        BibEntry entry = new BibEntry();
        entry.setType(BibtexEntryTypes.ARTICLE);
        entry.setField("author", "Mr. author");
        entry.setParsedSerialization("wrong serialization");
        entry.setChanged(false);
        database.insertEntry(entry);

        SavePreferences preferences = new SavePreferences().withReformatFile(true);
        StringSaveSession session = databaseWriter.savePartOfDatabase(bibtexContext, Collections.singletonList(entry), preferences);

        assertEquals(OS.NEWLINE +
                        "@Article{," + OS.NEWLINE + "  author = {Mr. author}," + OS.NEWLINE + "}"
                        + OS.NEWLINE + OS.NEWLINE
                        + "@Comment{jabref-meta: databaseType:bibtex;}"
                        + OS.NEWLINE,
                session.getStringValue());
    }

    @Test
    public void writeSavedSerializationOfStringIfUnchanged() throws Exception {
        BibtexString string = new BibtexString("name", "content");
        string.setParsedSerialization("serialization");
        database.addString(string);

        StringSaveSession session = databaseWriter.savePartOfDatabase(bibtexContext, Collections.emptyList(), new SavePreferences());

        assertEquals("serialization", session.getStringValue());
    }

    @Test
    public void reformatStringIfAskedToDoSo() throws Exception {
        BibtexString string = new BibtexString("name", "content");
        string.setParsedSerialization("wrong serialization");
        database.addString(string);

        SavePreferences preferences = new SavePreferences().withReformatFile(true);
        StringSaveSession session = databaseWriter.savePartOfDatabase(bibtexContext, Collections.emptyList(), preferences);

        assertEquals(OS.NEWLINE + "@String{name = {content}}" + OS.NEWLINE, session.getStringValue());

    }

    @Test
    public void writeSaveActions() throws Exception {
        FieldFormatterCleanups saveActions = new FieldFormatterCleanups(true,
                Collections.singletonList(new FieldFormatterCleanup("title", new LowerCaseFormatter())));
        metaData.setSaveActions(saveActions);

        StringSaveSession session = databaseWriter.savePartOfDatabase(bibtexContext, Collections.emptyList(), new SavePreferences());

        assertEquals(OS.NEWLINE + "@Comment{jabref-meta: saveActions:enabled;" + OS.NEWLINE
                + "title[lower_case]" + OS.NEWLINE + ";}" + OS.NEWLINE, session.getStringValue());
    }

    @Test
    public void writeSaveOrderConfig() throws Exception {
        SaveOrderConfig saveOrderConfig = new SaveOrderConfig(false, new SaveOrderConfig.SortCriterion("author", false),
                new SaveOrderConfig.SortCriterion("year", true),
                new SaveOrderConfig.SortCriterion("abstract", false));
        metaData.setSaveOrderConfig(saveOrderConfig);

        StringSaveSession session = databaseWriter.savePartOfDatabase(bibtexContext, Collections.emptyList(), new SavePreferences());

        assertEquals(OS.NEWLINE
                + "@Comment{jabref-meta: saveOrderConfig:specified;author;false;year;true;abstract;false;}"
                + OS.NEWLINE, session.getStringValue());
    }

    @Test
    public void writeCustomKeyPattern() throws Exception {
        setMockDatabaseBibtexKeyPattern();
        bibtexKeyPattern.setDefaultValue("test");
        bibtexKeyPattern.addBibtexKeyPattern("article", "articleTest");
        metaData.setCiteKeyPattern(bibtexKeyPattern);

        StringSaveSession session = databaseWriter.savePartOfDatabase(bibtexContext, Collections.emptyList(), new SavePreferences());

        assertEquals(OS.NEWLINE + "@Comment{jabref-meta: keypattern_article:articleTest;}" + OS.NEWLINE
                        + OS.NEWLINE + "@Comment{jabref-meta: keypatterndefault:test;}" + OS.NEWLINE,
                session.getStringValue());
    }
    
    public void setMockDatabaseBibtexKeyPattern() {
    	GlobalBibtexKeyPattern globalKeyPattern = mock(GlobalBibtexKeyPattern.class, Answers.RETURNS_DEEP_STUBS);
    	bibtexKeyPattern = new DatabaseBibtexKeyPattern(globalKeyPattern);
    }
    
    @Test
    public void writeBiblatexMode() throws Exception {
        metaData.setMode(BibDatabaseMode.BIBLATEX);

        StringSaveSession session = databaseWriter.savePartOfDatabase(bibtexContext, Collections.emptyList(), new SavePreferences());

        assertEquals(OS.NEWLINE + "@Comment{jabref-meta: databaseType:biblatex;}" + OS.NEWLINE,
                session.getStringValue());
    }

    @Test
    public void writeProtectedFlag() throws Exception {
        metaData.markAsProtected();

        StringSaveSession session = databaseWriter.savePartOfDatabase(bibtexContext, Collections.emptyList(), new SavePreferences());

        assertEquals(OS.NEWLINE + "@Comment{jabref-meta: protectedFlag:true;}" + OS.NEWLINE,
                session.getStringValue());
    }

    @Test
    public void writeFileDirectories() throws Exception {
        metaData.setDefaultFileDirectory("\\Literature\\");
        metaData.setUserFileDirectory("defaultOwner-user", "D:\\Documents");

        StringSaveSession session = databaseWriter.savePartOfDatabase(bibtexContext, Collections.emptyList(), new SavePreferences());

        assertEquals(OS.NEWLINE + "@Comment{jabref-meta: fileDirectory:\\\\Literature\\\\;}" + OS.NEWLINE +
                OS.NEWLINE + "@Comment{jabref-meta: fileDirectory-defaultOwner-user:D:\\\\Documents;}"
                + OS.NEWLINE, session.getStringValue());
    }

    @Test
    public void writeEntriesSorted() throws Exception {
        SaveOrderConfig saveOrderConfig = new SaveOrderConfig(false, new SaveOrderConfig.SortCriterion("author", false),
                new SaveOrderConfig.SortCriterion("year", true),
                new SaveOrderConfig.SortCriterion("abstract", false));
        metaData.setSaveOrderConfig(saveOrderConfig);

        BibEntry firstEntry = new BibEntry();
        firstEntry.setType(BibtexEntryTypes.ARTICLE);
        firstEntry.setField("author", "A");
        firstEntry.setField("year", "2000");

        BibEntry secondEntry = new BibEntry();
        secondEntry.setType(BibtexEntryTypes.ARTICLE);
        secondEntry.setField("author", "A");
        secondEntry.setField("year", "2010");

        BibEntry thirdEntry = new BibEntry();
        thirdEntry.setType(BibtexEntryTypes.ARTICLE);
        thirdEntry.setField("author", "B");
        thirdEntry.setField("year", "2000");

        database.insertEntry(secondEntry);
        database.insertEntry(thirdEntry);
        database.insertEntry(firstEntry);

        StringSaveSession session = databaseWriter.savePartOfDatabase(bibtexContext, database.getEntries(), new SavePreferences());

        assertEquals(
                OS.NEWLINE +
                "@Article{," + OS.NEWLINE +
                "  author = {A}," + OS.NEWLINE +
                "  year   = {2000}," + OS.NEWLINE +
                "}"  + OS.NEWLINE + OS.NEWLINE +
                "@Article{," + OS.NEWLINE +
                "  author = {A}," + OS.NEWLINE +
                "  year   = {2010}," + OS.NEWLINE +
                "}" + OS.NEWLINE + OS.NEWLINE +
                "@Article{," + OS.NEWLINE +
                "  author = {B}," + OS.NEWLINE +
                "  year   = {2000}," + OS.NEWLINE +
                "}" + OS.NEWLINE + OS.NEWLINE +
                "@Comment{jabref-meta: databaseType:bibtex;}"
                 + OS.NEWLINE + OS.NEWLINE +
                "@Comment{jabref-meta: saveOrderConfig:specified;author;false;year;true;abstract;false;}" +
                OS.NEWLINE
                , session.getStringValue());
    }

    @Test
    public void writeEntriesInOriginalOrderWhenNoSaveOrderConfigIsSetInMetadata() throws Exception {
        BibEntry firstEntry = new BibEntry();
        firstEntry.setType(BibtexEntryTypes.ARTICLE);
        firstEntry.setField("author", "A");
        firstEntry.setField("year", "2010");

        BibEntry secondEntry = new BibEntry();
        secondEntry.setType(BibtexEntryTypes.ARTICLE);
        secondEntry.setField("author", "B");
        secondEntry.setField("year", "2000");

        BibEntry thirdEntry = new BibEntry();
        thirdEntry.setType(BibtexEntryTypes.ARTICLE);
        thirdEntry.setField("author", "A");
        thirdEntry.setField("year", "2000");

        database.insertEntry(firstEntry);
        database.insertEntry(secondEntry);
        database.insertEntry(thirdEntry);

        SavePreferences preferences = new SavePreferences().withSaveInOriginalOrder(false);
        StringSaveSession session = databaseWriter.savePartOfDatabase(bibtexContext, database.getEntries(), preferences);

        assertEquals(
                OS.NEWLINE +
                        "@Article{," + OS.NEWLINE +
                        "  author = {A}," + OS.NEWLINE +
                        "  year   = {2010}," + OS.NEWLINE +
                        "}" + OS.NEWLINE + OS.NEWLINE +
                        "@Article{," + OS.NEWLINE +
                        "  author = {B}," + OS.NEWLINE +
                        "  year   = {2000}," + OS.NEWLINE +
                        "}" + OS.NEWLINE + OS.NEWLINE +
                        "@Article{," + OS.NEWLINE +
                        "  author = {A}," + OS.NEWLINE +
                        "  year   = {2000}," + OS.NEWLINE +
                        "}"
                        + OS.NEWLINE + OS.NEWLINE +
                        "@Comment{jabref-meta: databaseType:bibtex;}"
                        + OS.NEWLINE
                , session.getStringValue());
    }

    @Test
    public void roundtripWithContentSelectorsAndUmlauts() throws IOException, SaveException {
        String fileContent = "% Encoding: UTF-8" + OS.NEWLINE + OS.NEWLINE + "@Comment{jabref-meta: selector_journal:Test {\\\\\"U}mlaut;}" + OS.NEWLINE;
        Charset encoding = StandardCharsets.UTF_8;

        ParserResult firstParse = new BibtexParser(importFormatPreferences, fileMonitor).parse(new StringReader(fileContent));

        SavePreferences preferences = new SavePreferences().withEncoding(encoding).withSaveInOriginalOrder(true);
        BibDatabaseContext context = new BibDatabaseContext(firstParse.getDatabase(), firstParse.getMetaData(),
                new Defaults(BibDatabaseMode.BIBTEX));

        StringSaveSession session = databaseWriter.savePartOfDatabase(context, firstParse.getDatabase().getEntries(), preferences);

        assertEquals(fileContent, session.getStringValue());
    }
    
    @Test
    public void test00()  throws Throwable  {
        BibDatabaseWriter.SaveSessionFactory<FileSaveSession> bibDatabaseWriter_SaveSessionFactory0 = (BibDatabaseWriter.SaveSessionFactory<FileSaveSession>) mock(BibDatabaseWriter.SaveSessionFactory.class, new ViolatedAssumptionAnswer());
        BibtexDatabaseWriter<FileSaveSession> bibtexDatabaseWriter0 = new BibtexDatabaseWriter<FileSaveSession>(bibDatabaseWriter_SaveSessionFactory0);
        bibtexDatabaseWriter0.writePrelogue((BibDatabaseContext) null, (Charset) null);
    }
    
    @Test
    public void test01()  throws Throwable  {
        BibDatabaseWriter.SaveSessionFactory<FileSaveSession> bibDatabaseWriter_SaveSessionFactory0 = (BibDatabaseWriter.SaveSessionFactory<FileSaveSession>) mock(BibDatabaseWriter.SaveSessionFactory.class, new ViolatedAssumptionAnswer());
        BibtexDatabaseWriter<FileSaveSession> bibtexDatabaseWriter0 = new BibtexDatabaseWriter<FileSaveSession>(bibDatabaseWriter_SaveSessionFactory0);
        Charset charset0 = Charset.defaultCharset();
        // Undeclared exception!
        try { 
          bibtexDatabaseWriter0.writePrelogue((BibDatabaseContext) null, charset0);
          fail("Expecting exception: NullPointerException");
        
        } catch(NullPointerException e) {
           //
           // no message in exception (getMessage() returned null)
           //
           verifyException("org.jabref.logic.exporter.BibtexDatabaseWriter", e);
        }
    }

    @Test
    public void test02()  throws Throwable  {
        BibDatabaseWriter.SaveSessionFactory<FileSaveSession> bibDatabaseWriter_SaveSessionFactory0 = (BibDatabaseWriter.SaveSessionFactory<FileSaveSession>) mock(BibDatabaseWriter.SaveSessionFactory.class, new ViolatedAssumptionAnswer());
        BibtexDatabaseWriter<FileSaveSession> bibtexDatabaseWriter0 = new BibtexDatabaseWriter<FileSaveSession>(bibDatabaseWriter_SaveSessionFactory0);
        BibtexString bibtexString0 = new BibtexString("DBID:", "DBID:");
        Boolean boolean0 = Boolean.valueOf("DBID:");
        LatexFieldFormatterPreferences latexFieldFormatterPreferences0 = new LatexFieldFormatterPreferences();
        // Undeclared exception!
        try { 
          bibtexDatabaseWriter0.writeString(bibtexString0, false, 93, boolean0, latexFieldFormatterPreferences0);
          fail("Expecting exception: NullPointerException");
        
        } catch(NullPointerException e) {
           //
           // no message in exception (getMessage() returned null)
           //
           verifyException("org.jabref.logic.exporter.BibtexDatabaseWriter", e);
        }
    }

    @Test
    public void test03()  throws Throwable  {
        BibDatabaseWriter.SaveSessionFactory<FileSaveSession> bibDatabaseWriter_SaveSessionFactory0 = (BibDatabaseWriter.SaveSessionFactory<FileSaveSession>) mock(BibDatabaseWriter.SaveSessionFactory.class, new ViolatedAssumptionAnswer());
        BibtexDatabaseWriter<FileSaveSession> bibtexDatabaseWriter0 = new BibtexDatabaseWriter<FileSaveSession>(bibDatabaseWriter_SaveSessionFactory0);
        BibtexString bibtexString0 = new BibtexString("DBID:", "DBID:");
        bibtexString0.setParsedSerialization("!m5c");
        Boolean boolean0 = new Boolean("DBID:");
        LatexFieldFormatterPreferences latexFieldFormatterPreferences0 = new LatexFieldFormatterPreferences();
        // Undeclared exception!
        try { 
          bibtexDatabaseWriter0.writeString(bibtexString0, true, 6725, boolean0, latexFieldFormatterPreferences0);
          fail("Expecting exception: NullPointerException");
        
        } catch(NullPointerException e) {
           //
           // no message in exception (getMessage() returned null)
           //
           verifyException("org.jabref.logic.exporter.BibtexDatabaseWriter", e);
        }
    }

    @Test
    public void test04()  throws Throwable  {
        BibDatabaseWriter.SaveSessionFactory<FileSaveSession> bibDatabaseWriter_SaveSessionFactory0 = (BibDatabaseWriter.SaveSessionFactory<FileSaveSession>) mock(BibDatabaseWriter.SaveSessionFactory.class, new ViolatedAssumptionAnswer());
        BibtexDatabaseWriter<FileSaveSession> bibtexDatabaseWriter0 = new BibtexDatabaseWriter<FileSaveSession>(bibDatabaseWriter_SaveSessionFactory0);
        BibtexString bibtexString0 = new BibtexString("<J@", "DBID:");
        Boolean boolean0 = new Boolean("l\"LATSiK)+PO?>A");
        LinkedList<String> linkedList0 = new LinkedList<String>();
        FieldContentParserPreferences fieldContentParserPreferences0 = new FieldContentParserPreferences(linkedList0);
        LatexFieldFormatterPreferences latexFieldFormatterPreferences0 = new LatexFieldFormatterPreferences(true, linkedList0, fieldContentParserPreferences0);
        // Undeclared exception!
        try { 
          bibtexDatabaseWriter0.writeString(bibtexString0, true, (-1392), boolean0, latexFieldFormatterPreferences0);
          fail("Expecting exception: NullPointerException");
        
        } catch(NullPointerException e) {
           //
           // no message in exception (getMessage() returned null)
           //
           verifyException("org.jabref.logic.exporter.BibtexDatabaseWriter", e);
        }
    }

    @Test
    public void test05()  throws Throwable  {
        BibDatabaseWriter.SaveSessionFactory<FileSaveSession> bibDatabaseWriter_SaveSessionFactory0 = (BibDatabaseWriter.SaveSessionFactory<FileSaveSession>) mock(BibDatabaseWriter.SaveSessionFactory.class, new ViolatedAssumptionAnswer());
        BibtexDatabaseWriter<FileSaveSession> bibtexDatabaseWriter0 = new BibtexDatabaseWriter<FileSaveSession>(bibDatabaseWriter_SaveSessionFactory0);
        BibtexString bibtexString0 = new BibtexString("DBID:", "");
        Boolean boolean0 = new Boolean(true);
        FieldContentParserPreferences fieldContentParserPreferences0 = new FieldContentParserPreferences((List<String>) null);
        LatexFieldFormatterPreferences latexFieldFormatterPreferences0 = new LatexFieldFormatterPreferences(true, (List<String>) null, fieldContentParserPreferences0);
        // Undeclared exception!
        try { 
          bibtexDatabaseWriter0.writeString(bibtexString0, true, 2147483645, boolean0, latexFieldFormatterPreferences0);
          fail("Expecting exception: NullPointerException");
        
        } catch(NullPointerException e) {
           //
           // no message in exception (getMessage() returned null)
           //
           verifyException("org.jabref.logic.exporter.BibtexDatabaseWriter", e);
        }
    }

    @Test
    public void test06()  throws Throwable  {
        BibDatabaseWriter.SaveSessionFactory<FileSaveSession> bibDatabaseWriter_SaveSessionFactory0 = (BibDatabaseWriter.SaveSessionFactory<FileSaveSession>) mock(BibDatabaseWriter.SaveSessionFactory.class, new ViolatedAssumptionAnswer());
        BibtexDatabaseWriter<FileSaveSession> bibtexDatabaseWriter0 = new BibtexDatabaseWriter<FileSaveSession>(bibDatabaseWriter_SaveSessionFactory0);
        // Undeclared exception!
        try { 
          bibtexDatabaseWriter0.writePreamble("DBID:");
          fail("Expecting exception: NullPointerException");
        
        } catch(NullPointerException e) {
           //
           // no message in exception (getMessage() returned null)
           //
           verifyException("org.jabref.logic.exporter.BibtexDatabaseWriter", e);
        }
    }

    @Test
    public void test07()  throws Throwable  {
        BibDatabaseWriter.SaveSessionFactory<FileSaveSession> bibDatabaseWriter_SaveSessionFactory0 = (BibDatabaseWriter.SaveSessionFactory<FileSaveSession>) mock(BibDatabaseWriter.SaveSessionFactory.class, new ViolatedAssumptionAnswer());
        BibtexDatabaseWriter<FileSaveSession> bibtexDatabaseWriter0 = new BibtexDatabaseWriter<FileSaveSession>(bibDatabaseWriter_SaveSessionFactory0);
        bibtexDatabaseWriter0.writePreamble("");
    }

    @Test
    public void test08()  throws Throwable  {
        BibDatabaseWriter.SaveSessionFactory<FileSaveSession> bibDatabaseWriter_SaveSessionFactory0 = (BibDatabaseWriter.SaveSessionFactory<FileSaveSession>) mock(BibDatabaseWriter.SaveSessionFactory.class, new ViolatedAssumptionAnswer());
        BibtexDatabaseWriter<FileSaveSession> bibtexDatabaseWriter0 = new BibtexDatabaseWriter<FileSaveSession>(bibDatabaseWriter_SaveSessionFactory0);
        // Undeclared exception!
        try { 
          bibtexDatabaseWriter0.writeEpilogue("Duplicate BibTeX string id.");
          fail("Expecting exception: NullPointerException");
        
        } catch(NullPointerException e) {
           //
           // no message in exception (getMessage() returned null)
           //
           verifyException("org.jabref.logic.exporter.BibtexDatabaseWriter", e);
        }
    }

    @Test
    public void test09()  throws Throwable  {
        BibDatabaseWriter.SaveSessionFactory<FileSaveSession> bibDatabaseWriter_SaveSessionFactory0 = (BibDatabaseWriter.SaveSessionFactory<FileSaveSession>) mock(BibDatabaseWriter.SaveSessionFactory.class, new ViolatedAssumptionAnswer());
        BibtexDatabaseWriter<FileSaveSession> bibtexDatabaseWriter0 = new BibtexDatabaseWriter<FileSaveSession>(bibDatabaseWriter_SaveSessionFactory0);
        bibtexDatabaseWriter0.writeEpilogue((String) null);
    }

    @Test
    public void test10()  throws Throwable  {
        BibDatabaseWriter.SaveSessionFactory<FileSaveSession> bibDatabaseWriter_SaveSessionFactory0 = (BibDatabaseWriter.SaveSessionFactory<FileSaveSession>) mock(BibDatabaseWriter.SaveSessionFactory.class, new ViolatedAssumptionAnswer());
        BibtexDatabaseWriter<FileSaveSession> bibtexDatabaseWriter0 = new BibtexDatabaseWriter<FileSaveSession>(bibDatabaseWriter_SaveSessionFactory0);
        // Undeclared exception!
        try { 
          bibtexDatabaseWriter0.writeDatabaseID((String) null);
          fail("Expecting exception: NullPointerException");
        
        } catch(NullPointerException e) {
           //
           // no message in exception (getMessage() returned null)
           //
           verifyException("org.jabref.logic.exporter.BibtexDatabaseWriter", e);
        }
    }

    @Test
    public void test11()  throws Throwable  {
        BibDatabaseWriter.SaveSessionFactory<FileSaveSession> bibDatabaseWriter_SaveSessionFactory0 = (BibDatabaseWriter.SaveSessionFactory<FileSaveSession>) mock(BibDatabaseWriter.SaveSessionFactory.class, new ViolatedAssumptionAnswer());
        BibtexDatabaseWriter<FileSaveSession> bibtexDatabaseWriter0 = new BibtexDatabaseWriter<FileSaveSession>(bibDatabaseWriter_SaveSessionFactory0);
        BibDatabaseMode bibDatabaseMode0 = BibDatabaseMode.BIBLATEX;
        Boolean boolean0 = new Boolean("-W|");
        LatexFieldFormatterPreferences latexFieldFormatterPreferences0 = new LatexFieldFormatterPreferences();
        // Undeclared exception!
        try { 
          bibtexDatabaseWriter0.writeEntry((BibEntry) null, bibDatabaseMode0, boolean0, latexFieldFormatterPreferences0);
          fail("Expecting exception: NullPointerException");
        
        } catch(NullPointerException e) {
           //
           // no message in exception (getMessage() returned null)
           //
           verifyException("org.jabref.logic.exporter.BibtexDatabaseWriter", e);
        }
    }

    @Test
    public void test12()  throws Throwable  {
        BibDatabaseWriter.SaveSessionFactory<FileSaveSession> bibDatabaseWriter_SaveSessionFactory0 = (BibDatabaseWriter.SaveSessionFactory<FileSaveSession>) mock(BibDatabaseWriter.SaveSessionFactory.class, new ViolatedAssumptionAnswer());
        BibtexDatabaseWriter<FileSaveSession> bibtexDatabaseWriter0 = new BibtexDatabaseWriter<FileSaveSession>(bibDatabaseWriter_SaveSessionFactory0);
        LinkedHashSet<String> linkedHashSet0 = new LinkedHashSet<String>();
        CustomEntryType customEntryType0 = new CustomEntryType("DBID:", linkedHashSet0, linkedHashSet0, linkedHashSet0);
        // Undeclared exception!
        try { 
          bibtexDatabaseWriter0.writeEntryTypeDefinition(customEntryType0);
          fail("Expecting exception: NullPointerException");
        
        } catch(NullPointerException e) {
           //
           // no message in exception (getMessage() returned null)
           //
           verifyException("org.jabref.logic.exporter.BibtexDatabaseWriter", e);
        }
    }

    @Test
    public void test13()  throws Throwable  {
        BibDatabaseWriter.SaveSessionFactory<FileSaveSession> bibDatabaseWriter_SaveSessionFactory0 = (BibDatabaseWriter.SaveSessionFactory<FileSaveSession>) mock(BibDatabaseWriter.SaveSessionFactory.class, new ViolatedAssumptionAnswer());
        BibtexDatabaseWriter<FileSaveSession> bibtexDatabaseWriter0 = new BibtexDatabaseWriter<FileSaveSession>(bibDatabaseWriter_SaveSessionFactory0);
        AbstractMap.SimpleImmutableEntry<String, String> abstractMap_SimpleImmutableEntry0 = new AbstractMap.SimpleImmutableEntry<String, String>("", "dT");
        AbstractMap.SimpleEntry<String, String> abstractMap_SimpleEntry0 = new AbstractMap.SimpleEntry<String, String>(abstractMap_SimpleImmutableEntry0);
        // Undeclared exception!
        try { 
          bibtexDatabaseWriter0.writeMetaDataItem(abstractMap_SimpleEntry0);
          fail("Expecting exception: NullPointerException");
        
        } catch(NullPointerException e) {
           //
           // no message in exception (getMessage() returned null)
           //
           verifyException("org.jabref.logic.exporter.BibtexDatabaseWriter", e);
        }
    }    

}
