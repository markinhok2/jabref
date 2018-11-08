/*
 * This file was automatically generated by EvoSuite
 * Tue Oct 09 04:30:22 GMT 2018
 */

package org.jabref.logic.exporter;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.nio.charset.Charset;
import java.util.AbstractMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.ViolatedAssumptionAnswer;
import org.jabref.logic.bibtex.FieldContentParserPreferences;
import org.jabref.logic.bibtex.LatexFieldFormatterPreferences;
import org.jabref.logic.exporter.BibDatabaseWriter;
import org.jabref.logic.exporter.BibtexDatabaseWriter;
import org.jabref.logic.exporter.FileSaveSession;
import org.jabref.model.database.BibDatabaseContext;
import org.jabref.model.database.BibDatabaseMode;
import org.jabref.model.entry.BibEntry;
import org.jabref.model.entry.BibtexString;
import org.jabref.model.entry.CustomEntryType;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class) @EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = true, useJEE = true) 
public class BibtexDatabaseWriter_ESTest extends BibtexDatabaseWriter_ESTest_scaffolding {
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
