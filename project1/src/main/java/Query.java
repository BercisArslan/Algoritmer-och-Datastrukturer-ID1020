import se.kth.id1020.util.Attributes;

public class Query {

    Attributes attributes;
    int count;

    public Query (Attributes attributes, int count) {
        this.attributes = attributes;
        this.count = count;
    }

    public int switchcompareTo (Query query , int i) {

        switch(i) {
            case 1:
                if(count > query.count)
                    return 1;
                if(count < query.count)
                    return -1;
                else
                    return 0;

            case 2:
                if(attributes.document.popularity > query.attributes.document.popularity)
                    return 1;
                if(attributes.document.popularity < query.attributes.document.popularity)
                    return -1;
                else
                    return 0;
            case 3:
                if(attributes.occurrence > query.attributes.occurrence)
                    return 1;
                if(attributes.occurrence < query.attributes.occurrence)
                    return -1;
                else
                    return 0;
        }
        return 0;
    }
}
