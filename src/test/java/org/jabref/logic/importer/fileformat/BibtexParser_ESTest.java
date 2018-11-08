/*
 * This file was automatically generated by EvoSuite
 * Tue Oct 09 04:27:50 GMT 2018
 */

package org.jabref.logic.importer.fileformat;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.PipedReader;
import java.io.PushbackInputStream;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.LinkedHashSet;
import java.util.Set;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.jabref.logic.bibtex.FieldContentParserPreferences;
import org.jabref.logic.bibtexkeypattern.BibtexKeyPatternPreferences;
import org.jabref.logic.importer.ImportFormatPreferences;
import org.jabref.logic.importer.fileformat.BibtexParser;
import org.jabref.logic.importer.fileformat.CustomImporter;
import org.jabref.model.bibtexkeypattern.GlobalBibtexKeyPattern;
import org.jabref.model.entry.Keyword;
import org.jabref.model.util.DummyFileUpdateMonitor;
import org.jabref.model.util.FileUpdateMonitor;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class) @EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = true, useJEE = true) 
public class BibtexParser_ESTest extends BibtexParser_ESTest_scaffolding {

  @Test(timeout = 4000)
  public void test00()  throws Throwable  {
      DummyFileUpdateMonitor dummyFileUpdateMonitor0 = new DummyFileUpdateMonitor();
      // Undeclared exception!
      try { 
        BibtexParser.singleFromString("", (ImportFormatPreferences) null, dummyFileUpdateMonitor0);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
         verifyException("java.util.Objects", e);
      }
  }

  @Test(timeout = 4000)
  public void test01()  throws Throwable  {
      Charset charset0 = Charset.defaultCharset();
      Character character0 = Keyword.DEFAULT_HIERARCHICAL_DELIMITER;
      GlobalBibtexKeyPattern globalBibtexKeyPattern0 = GlobalBibtexKeyPattern.fromPattern("/");
      BibtexKeyPatternPreferences bibtexKeyPatternPreferences0 = new BibtexKeyPatternPreferences((String) null, "eventdate", true, true, true, globalBibtexKeyPattern0, character0);
      FieldContentParserPreferences fieldContentParserPreferences0 = new FieldContentParserPreferences();
      ImportFormatPreferences importFormatPreferences0 = new ImportFormatPreferences((Set<CustomImporter>) null, charset0, character0, bibtexKeyPatternPreferences0, fieldContentParserPreferences0, true);
      DummyFileUpdateMonitor dummyFileUpdateMonitor0 = new DummyFileUpdateMonitor();
      // Undeclared exception!
      try { 
        BibtexParser.singleFromString("/", importFormatPreferences0, dummyFileUpdateMonitor0);
        fail("Expecting exception: NoClassDefFoundError");
      
      } catch(NoClassDefFoundError e) {
         //
         // com/google/common/eventbus/EventBus
         //
         verifyException("org.jabref.model.database.BibDatabase", e);
      }
  }

  @Test(timeout = 4000)
  public void test02()  throws Throwable  {
      LinkedHashSet<CustomImporter> linkedHashSet0 = new LinkedHashSet<CustomImporter>();
      Character character0 = new Character('I');
      GlobalBibtexKeyPattern globalBibtexKeyPattern0 = GlobalBibtexKeyPattern.fromPattern("");
      BibtexKeyPatternPreferences bibtexKeyPatternPreferences0 = new BibtexKeyPatternPreferences("", "", true, true, true, globalBibtexKeyPattern0, character0);
      FieldContentParserPreferences fieldContentParserPreferences0 = new FieldContentParserPreferences();
      ImportFormatPreferences importFormatPreferences0 = new ImportFormatPreferences(linkedHashSet0, (Charset) null, character0, bibtexKeyPatternPreferences0, fieldContentParserPreferences0, true);
      DummyFileUpdateMonitor dummyFileUpdateMonitor0 = new DummyFileUpdateMonitor();
      BibtexParser bibtexParser0 = new BibtexParser(importFormatPreferences0, dummyFileUpdateMonitor0);
      // Undeclared exception!
      try { 
        bibtexParser0.parseSingleEntry((String) null);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
         verifyException("java.io.StringReader", e);
      }
  }

  @Test(timeout = 4000)
  public void test03()  throws Throwable  {
      GlobalBibtexKeyPattern globalBibtexKeyPattern0 = GlobalBibtexKeyPattern.fromPattern("");
      Character character0 = Keyword.DEFAULT_HIERARCHICAL_DELIMITER;
      BibtexKeyPatternPreferences bibtexKeyPatternPreferences0 = new BibtexKeyPatternPreferences((String) null, (String) null, false, false, false, globalBibtexKeyPattern0, character0);
      FieldContentParserPreferences fieldContentParserPreferences0 = new FieldContentParserPreferences();
      ImportFormatPreferences importFormatPreferences0 = new ImportFormatPreferences((Set<CustomImporter>) null, (Charset) null, (Character) null, bibtexKeyPatternPreferences0, fieldContentParserPreferences0, false);
      DummyFileUpdateMonitor dummyFileUpdateMonitor0 = new DummyFileUpdateMonitor();
      BibtexParser bibtexParser0 = new BibtexParser(importFormatPreferences0, dummyFileUpdateMonitor0);
      // Undeclared exception!
      try { 
        bibtexParser0.parseEntries((String) null);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
         verifyException("java.io.StringReader", e);
      }
  }

  @Test(timeout = 4000)
  public void test04()  throws Throwable  {
      LinkedHashSet<CustomImporter> linkedHashSet0 = new LinkedHashSet<CustomImporter>();
      Character character0 = new Character('R');
      FieldContentParserPreferences fieldContentParserPreferences0 = new FieldContentParserPreferences();
      ImportFormatPreferences importFormatPreferences0 = new ImportFormatPreferences(linkedHashSet0, (Charset) null, character0, (BibtexKeyPatternPreferences) null, fieldContentParserPreferences0, true);
      DummyFileUpdateMonitor dummyFileUpdateMonitor0 = new DummyFileUpdateMonitor();
      BibtexParser bibtexParser0 = new BibtexParser(importFormatPreferences0, dummyFileUpdateMonitor0);
      // Undeclared exception!
      try { 
        bibtexParser0.parseEntries((Reader) null);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
         verifyException("java.util.Objects", e);
      }
  }

  @Test(timeout = 4000)
  public void test05()  throws Throwable  {
      LinkedHashSet<CustomImporter> linkedHashSet0 = new LinkedHashSet<CustomImporter>();
      Character character0 = new Character(':');
      BibtexKeyPatternPreferences bibtexKeyPatternPreferences0 = new BibtexKeyPatternPreferences((String) null, (String) null, false, false, false, (GlobalBibtexKeyPattern) null, character0);
      FieldContentParserPreferences fieldContentParserPreferences0 = new FieldContentParserPreferences();
      ImportFormatPreferences importFormatPreferences0 = new ImportFormatPreferences(linkedHashSet0, (Charset) null, character0, bibtexKeyPatternPreferences0, fieldContentParserPreferences0, false);
      BibtexParser bibtexParser0 = new BibtexParser(importFormatPreferences0, (FileUpdateMonitor) null);
      // Undeclared exception!
      try { 
        bibtexParser0.parseEntries((InputStream) null);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
         verifyException("java.io.Reader", e);
      }
  }

  @Test(timeout = 4000)
  public void test06()  throws Throwable  {
      Charset charset0 = Charset.defaultCharset();
      Character character0 = Character.valueOf('9');
      FieldContentParserPreferences fieldContentParserPreferences0 = new FieldContentParserPreferences();
      ImportFormatPreferences importFormatPreferences0 = new ImportFormatPreferences((Set<CustomImporter>) null, charset0, character0, (BibtexKeyPatternPreferences) null, fieldContentParserPreferences0, false);
      DummyFileUpdateMonitor dummyFileUpdateMonitor0 = new DummyFileUpdateMonitor();
      BibtexParser bibtexParser0 = new BibtexParser(importFormatPreferences0, dummyFileUpdateMonitor0);
      // Undeclared exception!
      try { 
        bibtexParser0.parse((Reader) null);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
         verifyException("java.util.Objects", e);
      }
  }

  @Test(timeout = 4000)
  public void test07()  throws Throwable  {
      DummyFileUpdateMonitor dummyFileUpdateMonitor0 = new DummyFileUpdateMonitor();
      BibtexParser bibtexParser0 = null;
      try {
        bibtexParser0 = new BibtexParser((ImportFormatPreferences) null, dummyFileUpdateMonitor0);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
         verifyException("java.util.Objects", e);
      }
  }

  @Test(timeout = 4000)
  public void test08()  throws Throwable  {
      LinkedHashSet<CustomImporter> linkedHashSet0 = new LinkedHashSet<CustomImporter>();
      Charset charset0 = Charset.defaultCharset();
      Character character0 = new Character('d');
      GlobalBibtexKeyPattern globalBibtexKeyPattern0 = GlobalBibtexKeyPattern.fromPattern("Ew");
      BibtexKeyPatternPreferences bibtexKeyPatternPreferences0 = new BibtexKeyPatternPreferences("", "{hN$bB}b:Vg(", false, true, false, globalBibtexKeyPattern0, character0);
      FieldContentParserPreferences fieldContentParserPreferences0 = new FieldContentParserPreferences();
      ImportFormatPreferences importFormatPreferences0 = new ImportFormatPreferences(linkedHashSet0, charset0, character0, bibtexKeyPatternPreferences0, fieldContentParserPreferences0, false);
      DummyFileUpdateMonitor dummyFileUpdateMonitor0 = new DummyFileUpdateMonitor();
      BibtexParser bibtexParser0 = new BibtexParser(importFormatPreferences0, dummyFileUpdateMonitor0);
      // Undeclared exception!
      try { 
        bibtexParser0.parseEntries("{hN$bB}b:Vg(");
        fail("Expecting exception: NoClassDefFoundError");
      
      } catch(NoClassDefFoundError e) {
         //
         // com/google/common/eventbus/EventBus
         //
         verifyException("org.jabref.model.database.BibDatabase", e);
      }
  }

  @Test(timeout = 4000)
  public void test09()  throws Throwable  {
      LinkedHashSet<CustomImporter> linkedHashSet0 = new LinkedHashSet<CustomImporter>();
      Charset charset0 = Charset.defaultCharset();
      Character character0 = new Character('a');
      GlobalBibtexKeyPattern globalBibtexKeyPattern0 = GlobalBibtexKeyPattern.fromPattern(": ");
      BibtexKeyPatternPreferences bibtexKeyPatternPreferences0 = new BibtexKeyPatternPreferences(": ", ": ", false, false, false, globalBibtexKeyPattern0, character0);
      FieldContentParserPreferences fieldContentParserPreferences0 = new FieldContentParserPreferences();
      ImportFormatPreferences importFormatPreferences0 = new ImportFormatPreferences(linkedHashSet0, charset0, character0, bibtexKeyPatternPreferences0, fieldContentParserPreferences0, true);
      DummyFileUpdateMonitor dummyFileUpdateMonitor0 = new DummyFileUpdateMonitor();
      BibtexParser bibtexParser0 = new BibtexParser(importFormatPreferences0, dummyFileUpdateMonitor0);
      byte[] byteArray0 = new byte[8];
      ByteArrayInputStream byteArrayInputStream0 = new ByteArrayInputStream(byteArray0, (byte)0, (byte)19);
      PushbackInputStream pushbackInputStream0 = new PushbackInputStream(byteArrayInputStream0);
      // Undeclared exception!
      try { 
        bibtexParser0.parseEntries((InputStream) pushbackInputStream0);
        fail("Expecting exception: NoClassDefFoundError");
      
      } catch(NoClassDefFoundError e) {
         //
         // com/google/common/eventbus/EventBus
         //
         verifyException("org.jabref.model.database.BibDatabase", e);
      }
  }

  @Test(timeout = 4000)
  public void test10()  throws Throwable  {
      LinkedHashSet<CustomImporter> linkedHashSet0 = new LinkedHashSet<CustomImporter>();
      Charset charset0 = Charset.defaultCharset();
      Character character0 = new Character('j');
      GlobalBibtexKeyPattern globalBibtexKeyPattern0 = GlobalBibtexKeyPattern.fromPattern("?");
      BibtexKeyPatternPreferences bibtexKeyPatternPreferences0 = new BibtexKeyPatternPreferences((String) null, "?", false, false, false, globalBibtexKeyPattern0, character0);
      FieldContentParserPreferences fieldContentParserPreferences0 = new FieldContentParserPreferences();
      ImportFormatPreferences importFormatPreferences0 = new ImportFormatPreferences(linkedHashSet0, charset0, character0, bibtexKeyPatternPreferences0, fieldContentParserPreferences0, false);
      DummyFileUpdateMonitor dummyFileUpdateMonitor0 = new DummyFileUpdateMonitor();
      BibtexParser bibtexParser0 = new BibtexParser(importFormatPreferences0, dummyFileUpdateMonitor0);
      // Undeclared exception!
      try { 
        bibtexParser0.parseSingleEntry("?");
        fail("Expecting exception: NoClassDefFoundError");
      
      } catch(NoClassDefFoundError e) {
         //
         // com/google/common/eventbus/EventBus
         //
         verifyException("org.jabref.model.database.BibDatabase", e);
      }
  }

  @Test(timeout = 4000)
  public void test11()  throws Throwable  {
      LinkedHashSet<CustomImporter> linkedHashSet0 = new LinkedHashSet<CustomImporter>();
      Charset charset0 = Charset.defaultCharset();
      Character character0 = new Character('I');
      GlobalBibtexKeyPattern globalBibtexKeyPattern0 = GlobalBibtexKeyPattern.fromPattern("D90{,Jj53gf]i");
      BibtexKeyPatternPreferences bibtexKeyPatternPreferences0 = new BibtexKeyPatternPreferences("", "BIBTEX_DB", false, false, false, globalBibtexKeyPattern0, character0);
      FieldContentParserPreferences fieldContentParserPreferences0 = new FieldContentParserPreferences();
      ImportFormatPreferences importFormatPreferences0 = new ImportFormatPreferences(linkedHashSet0, charset0, character0, bibtexKeyPatternPreferences0, fieldContentParserPreferences0, false);
      BibtexParser bibtexParser0 = new BibtexParser(importFormatPreferences0, (FileUpdateMonitor) null);
      PipedReader pipedReader0 = new PipedReader();
      // Undeclared exception!
      try { 
        bibtexParser0.parseEntries((Reader) pipedReader0);
        fail("Expecting exception: NoClassDefFoundError");
      
      } catch(NoClassDefFoundError e) {
         //
         // com/google/common/eventbus/EventBus
         //
         verifyException("org.jabref.model.database.BibDatabase", e);
      }
  }

  @Test(timeout = 4000)
  public void test12()  throws Throwable  {
      LinkedHashSet<CustomImporter> linkedHashSet0 = new LinkedHashSet<CustomImporter>();
      Character character0 = Keyword.DEFAULT_HIERARCHICAL_DELIMITER;
      GlobalBibtexKeyPattern globalBibtexKeyPattern0 = GlobalBibtexKeyPattern.fromPattern("");
      BibtexKeyPatternPreferences bibtexKeyPatternPreferences0 = new BibtexKeyPatternPreferences("", "", false, true, false, globalBibtexKeyPattern0, character0);
      FieldContentParserPreferences fieldContentParserPreferences0 = new FieldContentParserPreferences();
      ImportFormatPreferences importFormatPreferences0 = new ImportFormatPreferences(linkedHashSet0, (Charset) null, character0, bibtexKeyPatternPreferences0, fieldContentParserPreferences0, false);
      DummyFileUpdateMonitor dummyFileUpdateMonitor0 = new DummyFileUpdateMonitor();
      BibtexParser bibtexParser0 = new BibtexParser(importFormatPreferences0, dummyFileUpdateMonitor0);
      PipedReader pipedReader0 = new PipedReader();
      // Undeclared exception!
      try { 
        bibtexParser0.parse(pipedReader0);
        fail("Expecting exception: NoClassDefFoundError");
      
      } catch(NoClassDefFoundError e) {
         //
         // com/google/common/eventbus/EventBus
         //
         verifyException("org.jabref.model.database.BibDatabase", e);
      }
  }
}
