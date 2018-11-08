/**
 * Scaffolding file used to store all the setups needed to run 
 * tests automatically generated by EvoSuite
 * Tue Oct 09 04:30:22 GMT 2018
 */

package org.jabref.logic.exporter;

import org.evosuite.runtime.annotation.EvoSuiteClassExclude;
import org.junit.BeforeClass;
import org.junit.Before;
import org.junit.After;
import org.junit.AfterClass;
import org.evosuite.runtime.sandbox.Sandbox;
import org.evosuite.runtime.sandbox.Sandbox.SandboxMode;

import static org.evosuite.shaded.org.mockito.Mockito.*;
@EvoSuiteClassExclude
public class BibtexDatabaseWriter_ESTest_scaffolding {

  @org.junit.Rule 
  public org.evosuite.runtime.vnet.NonFunctionalRequirementRule nfr = new org.evosuite.runtime.vnet.NonFunctionalRequirementRule();

  private static final java.util.Properties defaultProperties = (java.util.Properties) java.lang.System.getProperties().clone(); 

  private org.evosuite.runtime.thread.ThreadStopper threadStopper =  new org.evosuite.runtime.thread.ThreadStopper (org.evosuite.runtime.thread.KillSwitchHandler.getInstance(), 3000);


  @BeforeClass 
  public static void initEvoSuiteFramework() { 
    org.evosuite.runtime.RuntimeSettings.className = "org.jabref.logic.exporter.BibtexDatabaseWriter"; 
    org.evosuite.runtime.GuiSupport.initialize(); 
    org.evosuite.runtime.RuntimeSettings.maxNumberOfThreads = 100; 
    org.evosuite.runtime.RuntimeSettings.maxNumberOfIterationsPerLoop = 10000; 
    org.evosuite.runtime.RuntimeSettings.mockSystemIn = true; 
    org.evosuite.runtime.RuntimeSettings.sandboxMode = org.evosuite.runtime.sandbox.Sandbox.SandboxMode.RECOMMENDED; 
    org.evosuite.runtime.sandbox.Sandbox.initializeSecurityManagerForSUT(); 
    org.evosuite.runtime.classhandling.JDKClassResetter.init();
    setSystemProperties();
    initializeClasses();
    org.evosuite.runtime.Runtime.getInstance().resetRuntime(); 
    try { initMocksToAvoidTimeoutsInTheTests(); } catch(ClassNotFoundException e) {} 
  } 

  @AfterClass 
  public static void clearEvoSuiteFramework(){ 
    Sandbox.resetDefaultSecurityManager(); 
    java.lang.System.setProperties((java.util.Properties) defaultProperties.clone()); 
  } 

  @Before 
  public void initTestCase(){ 
    threadStopper.storeCurrentThreads();
    threadStopper.startRecordingTime();
    org.evosuite.runtime.jvm.ShutdownHookHandler.getInstance().initHandler(); 
    org.evosuite.runtime.sandbox.Sandbox.goingToExecuteSUTCode(); 
    setSystemProperties(); 
    org.evosuite.runtime.GuiSupport.setHeadless(); 
    org.evosuite.runtime.Runtime.getInstance().resetRuntime(); 
    org.evosuite.runtime.agent.InstrumentingAgent.activate(); 
  } 

  @After 
  public void doneWithTestCase(){ 
    threadStopper.killAndJoinClientThreads();
    org.evosuite.runtime.jvm.ShutdownHookHandler.getInstance().safeExecuteAddedHooks(); 
    org.evosuite.runtime.classhandling.JDKClassResetter.reset(); 
    resetClasses(); 
    org.evosuite.runtime.sandbox.Sandbox.doneWithExecutingSUTCode(); 
    org.evosuite.runtime.agent.InstrumentingAgent.deactivate(); 
    org.evosuite.runtime.GuiSupport.restoreHeadlessMode(); 
  } 

  public static void setSystemProperties() {
 
    java.lang.System.setProperties((java.util.Properties) defaultProperties.clone()); 
    java.lang.System.setProperty("file.encoding", "Cp1252"); 
    java.lang.System.setProperty("java.awt.headless", "true"); 
    java.lang.System.setProperty("java.io.tmpdir", "C:\\Users\\CLAUDI~1\\AppData\\Local\\Temp\\"); 
    java.lang.System.setProperty("user.country", "BR"); 
    java.lang.System.setProperty("user.dir", "E:\\ClaudineiBJr\\Outros\\K2\\source\\jabref-4.2"); 
    java.lang.System.setProperty("user.home", "C:\\Users\\Claudinei B Jr"); 
    java.lang.System.setProperty("user.language", "pt"); 
    java.lang.System.setProperty("user.name", "Claudinei B Jr"); 
    java.lang.System.setProperty("user.timezone", "America/Sao_Paulo"); 
  }

  private static void initializeClasses() {
    org.evosuite.runtime.classhandling.ClassStateSupport.initializeClasses(BibtexDatabaseWriter_ESTest_scaffolding.class.getClassLoader() ,
      "org.jabref.model.database.shared.DatabaseConnectionProperties",
      "org.jabref.model.entry.event.EntryEvent",
      "org.jabref.model.database.BibDatabase",
      "org.jabref.model.groups.AutomaticKeywordGroup",
      "org.jabref.logic.bibtex.LatexFieldFormatterPreferences",
      "org.jabref.model.metadata.ContentSelector",
      "org.jabref.model.entry.event.FieldChangedEvent",
      "org.jabref.model.entry.BibtexString",
      "org.jabref.model.database.event.EntryAddedEvent",
      "org.jabref.model.groups.KeywordGroup",
      "org.jabref.model.entry.CustomEntryType",
      "org.jabref.model.entry.Keyword",
      "org.jabref.model.database.shared.DBMSType",
      "org.jabref.model.entry.event.EntryChangedEvent",
      "org.jabref.model.groups.GroupHierarchyType",
      "org.jabref.logic.bibtex.FieldContentParserPreferences",
      "org.jabref.model.TreeNode",
      "org.jabref.model.database.BibDatabaseMode",
      "org.jabref.model.entry.BibEntry",
      "org.jabref.model.groups.AutomaticGroup",
      "org.jabref.model.ChainNode",
      "org.jabref.logic.exporter.BibDatabaseWriter",
      "org.jabref.logic.exporter.VerifyingWriter",
      "org.jabref.model.cleanup.FieldFormatterCleanups",
      "org.jabref.model.entry.Date",
      "org.jabref.logic.exporter.SavePreferences$DatabaseSaveType",
      "org.jabref.logic.l10n.Languages",
      "org.jabref.model.entry.BibEntry$GetFieldInterface",
      "org.jabref.logic.util.OS",
      "org.jabref.logic.exporter.BibDatabaseWriter$SaveSessionFactory",
      "org.jabref.model.database.event.ChangePropagation",
      "org.jabref.logic.exporter.SavePreferences",
      "org.jabref.logic.exporter.BibtexDatabaseWriter",
      "org.jabref.model.entry.EntryConverter",
      "org.jabref.logic.bibtex.FieldContentParser",
      "org.jabref.model.strings.StringUtil",
      "org.jabref.logic.bibtex.LatexFieldFormatter",
      "org.jabref.model.entry.KeywordList",
      "org.jabref.model.database.KeyCollisionException",
      "org.jabref.model.metadata.ContentSelectors",
      "org.jabref.model.util.OptionalUtil",
      "org.jabref.model.entry.LinkedFile",
      "org.jabref.model.groups.AbstractGroup",
      "org.jabref.logic.bibtex.InvalidFieldValueException",
      "org.jabref.model.groups.ExplicitGroup",
      "org.jabref.model.database.shared.DatabaseNotSupportedException",
      "org.jabref.model.database.DuplicationChecker",
      "org.jabref.model.groups.GroupTreeNode",
      "org.jabref.logic.exporter.SaveException",
      "org.jabref.model.metadata.MetaData",
      "org.jabref.model.groups.WordKeywordGroup",
      "org.jabref.logic.l10n.Localization",
      "org.jabref.model.bibtexkeypattern.DatabaseBibtexKeyPattern",
      "org.jabref.model.database.shared.DatabaseLocation",
      "org.jabref.model.database.shared.DatabaseConnection",
      "org.jabref.model.database.BibDatabaseContext",
      "org.jabref.logic.l10n.EncodingControl",
      "org.jabref.model.groups.AllEntriesGroup",
      "org.jabref.model.database.shared.DatabaseSynchronizer",
      "org.jabref.logic.exporter.SaveSession",
      "org.jabref.logic.l10n.Localization$LocalizationBundle",
      "org.jabref.model.entry.event.EntryEventSource",
      "org.jabref.model.metadata.SaveOrderConfig",
      "org.jabref.model.bibtexkeypattern.GlobalBibtexKeyPattern",
      "org.jabref.model.bibtexkeypattern.AbstractBibtexKeyPattern",
      "org.jabref.model.metadata.FileDirectoryPreferences",
      "org.jabref.model.entry.BibtexString$Type",
      "org.jabref.model.entry.IdGenerator",
      "org.jabref.logic.bibtex.BibEntryWriter",
      "org.jabref.model.entry.SharedBibEntryData",
      "org.jabref.model.metadata.SaveOrderConfig$SortCriterion",
      "org.jabref.model.database.event.BibDatabaseContextChangedEvent",
      "org.jabref.model.strings.UnicodeToReadableCharMap",
      "org.jabref.model.search.SearchMatcher",
      "org.jabref.model.groups.GroupEntryChanger",
      "org.jabref.model.entry.Month",
      "org.jabref.logic.exporter.FileSaveSession",
      "org.jabref.model.entry.EntryType",
      "org.jabref.model.database.event.EntryRemovedEvent",
      "org.jabref.model.Defaults"
    );
  } 
  private static void initMocksToAvoidTimeoutsInTheTests() throws ClassNotFoundException { 
    mock(Class.forName("org.jabref.logic.exporter.BibDatabaseWriter$SaveSessionFactory", false, BibtexDatabaseWriter_ESTest_scaffolding.class.getClassLoader()));
  }

  private static void resetClasses() {
    org.evosuite.runtime.classhandling.ClassResetter.getInstance().setClassLoader(BibtexDatabaseWriter_ESTest_scaffolding.class.getClassLoader()); 

    org.evosuite.runtime.classhandling.ClassStateSupport.resetClasses(
      "org.jabref.logic.exporter.BibDatabaseWriter",
      "org.jabref.logic.exporter.BibtexDatabaseWriter",
      "org.jabref.logic.util.OS",
      "org.jabref.model.database.BibDatabase",
      "org.jabref.model.database.DuplicationChecker",
      "org.jabref.model.entry.CustomEntryType",
      "org.jabref.model.strings.UnicodeToReadableCharMap",
      "org.jabref.model.strings.StringUtil",
      "org.jabref.model.groups.AbstractGroup",
      "org.jabref.model.groups.KeywordGroup",
      "org.jabref.model.groups.WordKeywordGroup",
      "org.jabref.model.groups.ExplicitGroup",
      "org.jabref.model.entry.KeywordList",
      "org.jabref.model.ChainNode",
      "org.jabref.model.entry.Keyword",
      "org.jabref.model.entry.BibEntry",
      "org.jabref.model.entry.IdGenerator",
      "org.jabref.model.metadata.MetaData",
      "org.jabref.logic.bibtex.LatexFieldFormatterPreferences",
      "org.jabref.logic.bibtex.FieldContentParserPreferences",
      "org.jabref.model.entry.EntryConverter",
      "org.jabref.model.entry.BibtexString",
      "org.jabref.model.entry.BibtexString$Type",
      "org.jabref.model.Defaults",
      "org.jabref.model.database.BibDatabaseContext",
      "org.jabref.logic.bibtex.BibEntryWriter",
      "org.jabref.logic.bibtex.LatexFieldFormatter",
      "org.jabref.logic.bibtex.FieldContentParser",
      "org.jabref.model.bibtexkeypattern.AbstractBibtexKeyPattern",
      "org.jabref.model.bibtexkeypattern.GlobalBibtexKeyPattern",
      "org.jabref.model.metadata.SaveOrderConfig",
      "org.jabref.model.metadata.SaveOrderConfig$SortCriterion",
      "org.jabref.logic.exporter.SavePreferences",
      "org.jabref.model.metadata.ContentSelector",
      "org.jabref.model.cleanup.FieldFormatterCleanups",
      "org.jabref.model.bibtexkeypattern.DatabaseBibtexKeyPattern",
      "org.jabref.model.metadata.FileDirectoryPreferences",
      "org.jabref.model.util.OptionalUtil",
      "org.jabref.model.groups.AllEntriesGroup",
      "org.jabref.model.TreeNode",
      "org.jabref.model.groups.GroupTreeNode",
      "org.jabref.model.groups.AutomaticGroup",
      "org.jabref.model.groups.AutomaticKeywordGroup"
    );
  }
}
