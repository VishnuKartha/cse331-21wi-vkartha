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



    // Test the Parser

    ///////////////////////////////////////////////////////////////////////////////////////
    ////  parseDataTest
    ///////////////////////////////////////////////////////////////////////////////////////
    @Test
    public void parseDataTest() {
        Iterator<CharacterAppearance> iter= MarvelParser.parseData("staffSuperheroes.tsv");
        while(iter.hasNext()) {
            CharacterAppearance currEntry = iter.next();
            System.out.println(currEntry.getHero() + " in " + currEntry.getBook());
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////


}
