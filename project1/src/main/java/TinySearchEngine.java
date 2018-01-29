import java.util.ArrayList;
import java.util.List;
import se.kth.id1020.TinySearchEngineBase;
import se.kth.id1020.util.Attributes;
import se.kth.id1020.util.Document;
import se.kth.id1020.util.Word;


public class TinySearchEngine implements TinySearchEngineBase {

    ArrayList<WordList> arraylist = new ArrayList<WordList>();

    //arraylist som vid varje index sparar ett ord av objekt Word och en lista av queary
    public void insert(Word word, Attributes attributes) {

        WordList obj = new WordList(word, attributes);
        if (arraylist.isEmpty()) {
            arraylist.add(obj);
        }
        //om ordet inte finns i arraylisten
        int index = bsearch(word.word, arraylist);
        boolean add = false;
        //jämför sträng vid index om det inte är lika lägg till ord vid den platsen
        if (!(arraylist.get(index).word.word.equals(word.word))) {
            arraylist.add(index, obj);
        }
        //om ordet finns i arraylistan
        else {
            //kolla hela listan om dokumentet finns i listan av query
            for (int i = 0; i < obj.list.size(); i++) {
                if ((attributes.document.name.equals  (arraylist.get(index).list.get(i).attributes.document.name))) {
                    add = true;
                    arraylist.get(index).list.get(i).count++;
                    break;
                }
            }
                if (!add)
                    arraylist.get(index).list.add(new Query(attributes, 1));
        }
    }

    public List<Document> search (String queary) {

        List<Query> results = new ArrayList<Query>();
        boolean orderby = false;
        List<Query> temp;
        int length;
        String property = null;
        String direction = null;
        List<Query> desc = new ArrayList<Query>();

        //split string into words
        String parts [] = queary.split(" ");

        //leta efter orderby
        for (int i = 0; i < parts.length; i++) {
            if (parts[i].equals("orderby")) {
                orderby = true;
            }
        }
        if (orderby) {
            property = parts[parts.length-2];
            direction = parts[parts.length-1];
            length = parts.length - 3;
        }
        else {
            length = parts.length;
        }
        //loopar orden
        for(int i = 0; i < length; i++) {
            temp = oldsearch(parts[i]);
            for(int j = 0; j < temp.size(); j++) {

                    if(!results.contains(temp.get(j))) {
                        results.add(temp.get(j));
                    }

            }
        }

        if(orderby) {

            if (property.equals("count")) {
                bsort(results,1);
            }
            else if(property.equals("popularity")) {
                bsort(results,2);
            }
            else if(property.equals("occurence")) {
                bsort(results,3);
            }
            if(direction.equals("desc")) {
                for (int i = results.size() - 1; i >= 0; i--) {
                    desc.add(results.get(i));
                }
                //return desc
                return queryToDoc(desc);
            }
            //return property asc
            return queryToDoc(results);
        }
        //return without orderby
        return queryToDoc(results);
    }

    public List<Document> queryToDoc (List<Query> list) {
        List<Document> docs = new ArrayList<Document>();

        for (int i = 0; i < list.size(); i++) {
            if(!docs.contains(list.get(i).attributes.document)) {
                //System.out.println(list.get(i).attributes+", Count = "+list.get(i).count);
                docs.add(list.get(i).attributes.document);
            }
        }
        return docs;
    }

    //använd binary search för att hitta en lista med documents som innehåller String queary
    public List<Query> oldsearch(String queary) {

        int index = bsearch(queary, arraylist);
        List<Query> queries = new ArrayList<Query>();

        //loopa genom list of attributes
        for (int i = 0; i < arraylist.get(index).list.size(); i++) {

                //kolla om dokumentet redan överförst innan gör inte det igen
            if(!(queries.contains(arraylist.get(index).list.get(i)))){

                //lägg till dok över till list of queries från list of attrb
                queries.add(arraylist.get(index).list.get(i));
                }
            }
        return queries;
    }
    //bubblesort asc
    static void bsort(List<Query> unsorted, int c) {
        Query temp;
        boolean swapped = true;
            for (int i = unsorted.size() -2; i >=0 && swapped; i--) {
                swapped = false;
                for (int j = 0; j <= i; j++) {
                    if(unsorted.get(j).switchcompareTo(unsorted.get(j+1),c) == 1) {
                        swapped = true;
                        temp = unsorted.get(j);
                        unsorted.set(j,unsorted.get(j+1));
                        unsorted.set(j+1,temp);
                    }
                }
            }
        }

    //binary search
    public static int bsearch(String word, ArrayList<WordList> list) {
        return search(word, list, 0, list.size()-1);
    }

    public static int search(String key, ArrayList<WordList> list, int lo, int hi) {
        // possible key indices in [lo, hi)
        int mid = lo + (hi - lo) / 2;
        if (hi <= lo) return mid;
        int cmp = list.get(mid).word.word.compareTo(key);
        if      (cmp > 0) return search(key, list, lo, mid);
        else if (cmp < 0) return search(key, list, mid+1, hi);
        else              return mid;
    }
}