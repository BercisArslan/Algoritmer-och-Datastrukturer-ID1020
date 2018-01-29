import se.kth.id1020.util.Attributes;
import se.kth.id1020.util.Word;
import java.util.ArrayList;
import java.util.List;

//skapa nytt objekt som ska
public class WordList {

    Word word;
    Attributes attributes;
    List<Query> list = new ArrayList<Query>();

    public WordList (Word word, List<Query> list){
        this.word = word;
        this.list = list;
    }

    public WordList(Word word,Attributes attributes) {
        this.word = word;
        Query obj = new Query(attributes,1);
        list.add(obj);
    }
}
