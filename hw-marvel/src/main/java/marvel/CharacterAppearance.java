package marvel;

import com.opencsv.bean.CsvBindByName;
// A Java Bean for each row entry in a tsv.
public class CharacterAppearance {

    @CsvBindByName
    private String hero;
    @CsvBindByName
    private String book;

    // getters and setters for hero
    public String getHero() { return this.hero; }
    public void setHero(String v) { this.hero = v; }

    // getters and setter for book
    public String getBook() { return this.book; }
    public void setBook(String v) { this.book = v; }
}
