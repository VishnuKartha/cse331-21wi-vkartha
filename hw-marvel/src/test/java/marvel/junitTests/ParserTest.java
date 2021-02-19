package marvel.junitTests;

import graph.DirectedLabeledGraph;
import java.util.Iterator;
import marvel.CharacterAppearance;
import marvel.MarvelParser;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import static org.junit.Assert.assertEquals;

public class ParserTest {
    @Rule
    public Timeout globalTimeout = Timeout.seconds(10); // 10 seconds max per method tested


    String[] classes = new String[]{"CSE331","CSE403","CSE331","CSE403","CSE331","CSE401","CSE331","CSE332","CSE341"};
    String[] teachers = new String[]{"Ernst-the-Bicycling-Wizard","Ernst-the-Bicycling-Wizard",
                                    "Notkin-of-the-Superhuman-Beard",  "Notkin-of-the-Superhuman-Beard",
                                    "Perkins-the-Magical-Singing-Instructor","Perkins-the-Magical-Singing-Instructor",
                                    "Grossman-the-Youngest-of-them-all", "Grossman-the-Youngest-of-them-all",
                                     "Grossman-the-Youngest-of-them-all"};


    String[] connections2 = new String[]{"connection1","connection3","connection2","connection1","connection2","connection1","connection2","connection3"};
    String[] alphabet2 = new String[]{"A","A","B","D","D","C","C","C"};

    String[] country = new String[]{"India","USA","South Africa"};
    String[] person = new String[]{"Gandhi","Martin Luther King","Nelson Mandela"};



    // Test the Parser

    ///////////////////////////////////////////////////////////////////////////////////////
    ////  parseDataTests
    ///////////////////////////////////////////////////////////////////////////////////////
    @Test
    public void parseDataStaffSuperheroesTest() {
        Iterator<CharacterAppearance> iter= MarvelParser.parseData("staffSuperheroes.tsv");
        int i = 0;
        while(iter.hasNext()) {
            CharacterAppearance currEntry = iter.next();
            assertEquals(currEntry.getBook(),classes[i]);
            assertEquals(currEntry.getHero(),teachers[i]);
            i++;
        }
    }

    @Test
    public void parseNoConnectionsTest() {
        Iterator<CharacterAppearance> iter= MarvelParser.parseData("noConnections.tsv");
        int i = 0;
        while(iter.hasNext()) {
            CharacterAppearance currEntry = iter.next();
            assertEquals(country[i],currEntry.getBook());
            assertEquals(person[i],currEntry.getHero());
            i++;
        }
    }

    @Test
    public void parseDataAlphabetTest() {
        Iterator<CharacterAppearance> iter= MarvelParser.parseData("alphabet2.tsv");
        int i = 0;
        while(iter.hasNext()) {
            CharacterAppearance currEntry = iter.next();
            assertEquals(connections2[i],currEntry.getBook());
            assertEquals(alphabet2[i],currEntry.getHero());
            i++;
        }
    }



    ///////////////////////////////////////////////////////////////////////////////////////


}
