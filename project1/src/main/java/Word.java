import se.kth.id1020.util.PartOfSpeech;

public class Word {
    public final PartOfSpeech pos;
    public final String word;

    public Word (PartOfSpeech pos, String word) {
        this.word = word;
        this.pos = pos;
    }
}
