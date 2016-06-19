package net.sf.jabref.bibtex;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Collection;
import java.util.Set;

import net.sf.jabref.Globals;
import net.sf.jabref.JabRefPreferences;
import net.sf.jabref.exporter.LatexFieldFormatter;
import net.sf.jabref.importer.ParserResult;
import net.sf.jabref.importer.fileformat.BibtexParser;
import net.sf.jabref.model.database.BibDatabaseMode;
import net.sf.jabref.model.entry.BibEntry;

import net.sf.jabref.testutils.AssertUtil;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class BibEntryTest_Article_Book {

    private BibEntryWriter writer;
    private static JabRefPreferences backup;

    @BeforeClass
    public static void setUp() {
        Globals.prefs = JabRefPreferences.getInstance();
        backup = Globals.prefs;
    }

    @AfterClass
    public static void tearDown() {
        Globals.prefs.overwritePreferences(backup);
    }

    @Before
    public void setUpWriter() {
        writer = new BibEntryWriter(new LatexFieldFormatter(), true);
    }

    @Test
    public void testArticleNoFields() throws IOException {
        StringWriter stringWriter = new StringWriter();

        BibEntry entry = new BibEntry();
        //set a type article
        entry.setType("article");

        writer.write(entry, stringWriter, BibDatabaseMode.BIBTEX);

        String actual = stringWriter.toString();

        // @formatter:off
        String expected = Globals.NEWLINE + "@Article{," + Globals.NEWLINE +
                "}" + Globals.NEWLINE;
        // @formatter:on

        assertEquals(expected, actual);
    }

    @Test
    public void testBookNoFields() throws IOException {
        StringWriter stringWriter = new StringWriter();

        BibEntry entry = new BibEntry();
        //set a type article
        entry.setType("book");

        writer.write(entry, stringWriter, BibDatabaseMode.BIBTEX);

        String actual = stringWriter.toString();

        // @formatter:off
        String expected = Globals.NEWLINE + "@Book{," + Globals.NEWLINE +
                "}" + Globals.NEWLINE;
        // @formatter:on

        assertEquals(expected, actual);
    }

    @Test
    public void testArticleAllRequiredFields() throws IOException{
        StringWriter stringWriter = new StringWriter();

        BibEntry entry = new BibEntry();
        entry.setType("article");
        //set a required field
        entry.setField("author", "Brownie");
        entry.setField("journal", "IJS");
        entry.setField("title", "The Best Brownie");
        entry.setField("year", "2016");


        writer.write(entry, stringWriter, BibDatabaseMode.BIBTEX);


        String actual = stringWriter.toString();

        // @formatter:off
        String expected = Globals.NEWLINE + "@Article{," + Globals.NEWLINE +
                "  author  = {Brownie}," + Globals.NEWLINE +
                "  title   = {The Best Brownie}," + Globals.NEWLINE +
                "  journal = {IJS}," + Globals.NEWLINE +
                "  year    = {2016}," + Globals.NEWLINE +
                "}" + Globals.NEWLINE;
        // @formatter:on

        assertEquals(expected, actual);
    }

    @Test
    public void testBookAllRequiredFields() throws IOException{
        StringWriter stringWriter = new StringWriter();

        BibEntry entry = new BibEntry();
        entry.setType("book");
        //set a required field
        entry.setField("author", "Brownie");
        entry.setField("publisher", "IJS");
        entry.setField("title", "The Best Brownie");
        entry.setField("year", "2016");
        entry.setField("editor","Duck Rogers");


        writer.write(entry, stringWriter, BibDatabaseMode.BIBTEX);


        String actual = stringWriter.toString();

        // @formatter:off
        String expected = Globals.NEWLINE + "@Book{," + Globals.NEWLINE +
                "  title     = {The Best Brownie}," + Globals.NEWLINE +
                "  publisher = {IJS}," + Globals.NEWLINE +
                "  year      = {2016}," + Globals.NEWLINE +
                "  author    = {Brownie}," + Globals.NEWLINE +
                "  editor    = {Duck Rogers}," + Globals.NEWLINE +
                "}" + Globals.NEWLINE;
        // @formatter:on
        assertEquals(expected, actual);
    }

    @Test
    public void testYearArticleMax() throws IOException, NumberFormatException{
        StringWriter stringWriter = new StringWriter();

        BibEntry entry = new BibEntry();
        entry.setType("article");
        //set a required field
        entry.setField("author", "Brownie");
        entry.setField("journal", "IJS");
        entry.setField("title", "The Best Brownie");
        entry.setField("year", "999999999");


        writer.write(entry, stringWriter, BibDatabaseMode.BIBTEX);


        String actual = stringWriter.toString();

        // @formatter:off
        String expected = Globals.NEWLINE + "@Article{," + Globals.NEWLINE +
                "  author  = {Brownie}," + Globals.NEWLINE +
                "  title   = {The Best Brownie}," + Globals.NEWLINE +
                "  journal = {IJS}," + Globals.NEWLINE +
                "  year    = {292278994}," + Globals.NEWLINE +
                "}" + Globals.NEWLINE;
        // @formatter:on

        assertEquals(expected, actual);
    }

    @Test
    public void testYearArticleMin() throws IOException, NumberFormatException{
        StringWriter stringWriter = new StringWriter();

        BibEntry entry = new BibEntry();
        entry.setType("article");
        //set a required field
        entry.setField("author", "Brownie");
        entry.setField("journal", "IJS");
        entry.setField("title", "The Best Brownie");
        entry.setField("year", "-999999999");


        writer.write(entry, stringWriter, BibDatabaseMode.BIBTEX);


        String actual = stringWriter.toString();

        // @formatter:off
        String expected = Globals.NEWLINE + "@Article{," + Globals.NEWLINE +
                "  author  = {Brownie}," + Globals.NEWLINE +
                "  title   = {The Best Brownie}," + Globals.NEWLINE +
                "  journal = {IJS}," + Globals.NEWLINE +
                "  year    = {1}," + Globals.NEWLINE +
                "}" + Globals.NEWLINE;
        // @formatter:on

        assertEquals(expected, actual);
    }

    @Test
    public void testBibtexkeyValid() throws IOException, NumberFormatException{
        StringWriter stringWriter = new StringWriter();

        BibEntry entry = new BibEntry();
        entry.setType("article");
        //set a required field
        entry.setField("bibtexkey", "Br1234");
        entry.setField("author", "Brownie");
        entry.setField("journal", "IJS");
        entry.setField("title", "The Best Brownie");
        entry.setField("year", "-999999999");


        writer.write(entry, stringWriter, BibDatabaseMode.BIBTEX);


        String actual = stringWriter.toString();

        // @formatter:off
        String expected = Globals.NEWLINE + "@Article{Br1234," + Globals.NEWLINE +
                "  author  = {Brownie}," + Globals.NEWLINE +
                "  title   = {The Best Brownie}," + Globals.NEWLINE +
                "  journal = {IJS}," + Globals.NEWLINE +
                "  year    = {1}," + Globals.NEWLINE +
                "}" + Globals.NEWLINE;
        // @formatter:on

        assertEquals(expected, actual);
    }

    @Test
    public void testBibtexkeyInvalid() throws IOException, NumberFormatException{
        StringWriter stringWriter = new StringWriter();

        BibEntry entry = new BibEntry();
        entry.setType("article");
        //set a required field
        entry.setField("bibtexkey", "1234");
        entry.setField("author", "Brownie");
        entry.setField("journal", "IJS");
        entry.setField("title", "The Best Brownie");
        entry.setField("year", "-999999999");


        writer.write(entry, stringWriter, BibDatabaseMode.BIBTEX);


        String actual = stringWriter.toString();

        // @formatter:off
        String expected = Globals.NEWLINE + "@Article{," + Globals.NEWLINE +
                "  author  = {Brownie}," + Globals.NEWLINE +
                "  title   = {The Best Brownie}," + Globals.NEWLINE +
                "  journal = {IJS}," + Globals.NEWLINE +
                "  year    = {1}," + Globals.NEWLINE +
                "}" + Globals.NEWLINE;
        // @formatter:on

        assertEquals(expected, actual);
    }

    @Test
    public void testArticleNumberValid() throws IOException, NumberFormatException{
        StringWriter stringWriter = new StringWriter();

        BibEntry entry = new BibEntry();
        entry.setType("article");
        //set a required field
        entry.setField("bibtexkey", "1234");
        entry.setField("author", "Brownie");
        entry.setField("journal", "IJS");
        entry.setField("title", "The Best Brownie");
        entry.setField("year", "-999999999");
        entry.setField("number", "32");


        writer.write(entry, stringWriter, BibDatabaseMode.BIBTEX);


        String actual = stringWriter.toString();

        // @formatter:off
        String expected = Globals.NEWLINE + "@Article{," + Globals.NEWLINE +
                "  author  = {Brownie}," + Globals.NEWLINE +
                "  title   = {The Best Brownie}," + Globals.NEWLINE +
                "  journal = {IJS}," + Globals.NEWLINE +
                "  year    = {1}," + Globals.NEWLINE +
                "  number  = {32}," + Globals.NEWLINE +
                "}" + Globals.NEWLINE;
        // @formatter:on

        assertEquals(expected, actual);
    }

    @Test
    public void testArticleNumberInvalid() throws IOException, NumberFormatException{
        StringWriter stringWriter = new StringWriter();

        BibEntry entry = new BibEntry();
        entry.setType("article");
        //set a required field
        entry.setField("bibtexkey", "1234");
        entry.setField("author", "Brownie");
        entry.setField("journal", "IJS");
        entry.setField("title", "The Best Brownie");
        entry.setField("year", "-999999999");
        entry.setField("number", "L32");


        writer.write(entry, stringWriter, BibDatabaseMode.BIBTEX);


        String actual = stringWriter.toString();

        // @formatter:off
        String expected = Globals.NEWLINE + "@Article{," + Globals.NEWLINE +
                "  author  = {Brownie}," + Globals.NEWLINE +
                "  title   = {The Best Brownie}," + Globals.NEWLINE +
                "  journal = {IJS}," + Globals.NEWLINE +
                "  year    = {1}," + Globals.NEWLINE +
                "}" + Globals.NEWLINE;
        // @formatter:on

        assertEquals(expected, actual);
    }

    @Test
    public void testArticlePagesValid() throws IOException, NumberFormatException{
        StringWriter stringWriter = new StringWriter();

        BibEntry entry = new BibEntry();
        entry.setType("article");
        //set a required field
        entry.setField("bibtexkey", "1234");
        entry.setField("author", "Brownie");
        entry.setField("journal", "IJS");
        entry.setField("title", "The Best Brownie");
        entry.setField("year", "-999999999");
        entry.setField("pages", "42");


        writer.write(entry, stringWriter, BibDatabaseMode.BIBTEX);


        String actual = stringWriter.toString();

        // @formatter:off
        String expected = Globals.NEWLINE + "@Article{," + Globals.NEWLINE +
                "  author  = {Brownie}," + Globals.NEWLINE +
                "  title   = {The Best Brownie}," + Globals.NEWLINE +
                "  journal = {IJS}," + Globals.NEWLINE +
                "  year    = {1}," + Globals.NEWLINE +
                "  pages   = {42}," + Globals.NEWLINE +
                "}" + Globals.NEWLINE;
        // @formatter:on

        assertEquals(expected, actual);
    }

    @Test
    public void testArticlePagesInvalid() throws IOException, NumberFormatException{
        StringWriter stringWriter = new StringWriter();

        BibEntry entry = new BibEntry();
        entry.setType("article");
        //set a required field
        entry.setField("bibtexkey", "1234");
        entry.setField("author", "Brownie");
        entry.setField("journal", "IJS");
        entry.setField("title", "The Best Brownie");
        entry.setField("year", "-999999999");
        entry.setField("pages", "L32");


        writer.write(entry, stringWriter, BibDatabaseMode.BIBTEX);


        String actual = stringWriter.toString();

        // @formatter:off
        String expected = Globals.NEWLINE + "@Article{," + Globals.NEWLINE +
                "  author  = {Brownie}," + Globals.NEWLINE +
                "  title   = {The Best Brownie}," + Globals.NEWLINE +
                "  journal = {IJS}," + Globals.NEWLINE +
                "  year    = {1}," + Globals.NEWLINE +
                "}" + Globals.NEWLINE;
        // @formatter:on

        assertEquals(expected, actual);
    }

    @Test
    public void testBookNumberValid() throws IOException{
        StringWriter stringWriter = new StringWriter();

        BibEntry entry = new BibEntry();
        entry.setType("book");
        //set a required field
        entry.setField("author", "Brownie");
        entry.setField("publisher", "IJS");
        entry.setField("title", "The Best Brownie");
        entry.setField("year", "2016");
        entry.setField("editor","Duck Rogers");
        entry.setField("number","40");


        writer.write(entry, stringWriter, BibDatabaseMode.BIBTEX);


        String actual = stringWriter.toString();

        // @formatter:off
        String expected = Globals.NEWLINE + "@Book{," + Globals.NEWLINE +
                "  title     = {The Best Brownie}," + Globals.NEWLINE +
                "  publisher = {IJS}," + Globals.NEWLINE +
                "  year      = {2016}," + Globals.NEWLINE +
                "  author    = {Brownie}," + Globals.NEWLINE +
                "  editor    = {Duck Rogers}," + Globals.NEWLINE +
                "  number    = {40}," + Globals.NEWLINE +
                "}" + Globals.NEWLINE;
        // @formatter:on
        assertEquals(expected, actual);
    }

    @Test
    public void testBookNumberInvalid() throws IOException{
        StringWriter stringWriter = new StringWriter();

        BibEntry entry = new BibEntry();
        entry.setType("book");
        //set a required field
        entry.setField("author", "Brownie");
        entry.setField("publisher", "IJS");
        entry.setField("title", "The Best Brownie");
        entry.setField("year", "2016");
        entry.setField("editor","Duck Rogers");
        entry.setField("number","L40");


        writer.write(entry, stringWriter, BibDatabaseMode.BIBTEX);


        String actual = stringWriter.toString();

        // @formatter:off
        String expected = Globals.NEWLINE + "@Book{," + Globals.NEWLINE +
                "  title     = {The Best Brownie}," + Globals.NEWLINE +
                "  publisher = {IJS}," + Globals.NEWLINE +
                "  year      = {2016}," + Globals.NEWLINE +
                "  author    = {Brownie}," + Globals.NEWLINE +
                "  editor    = {Duck Rogers}," + Globals.NEWLINE +
                "}" + Globals.NEWLINE;
        // @formatter:on
        assertEquals(expected, actual);
    }
}
