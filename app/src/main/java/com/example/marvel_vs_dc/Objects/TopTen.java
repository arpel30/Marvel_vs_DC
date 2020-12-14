package com.example.marvel_vs_dc.Objects;
import java.util.ArrayList;

public class TopTen {

    private ArrayList<Record> records;
    private int numRecs = 10;
    public TopTen() { this.records = new ArrayList<>();}

    public TopTen(ArrayList<Record> records) {
        this.records = records;
    }

    public ArrayList<Record> getRecords() {
        return records;
    }

    public TopTen setRecords(ArrayList<Record> records) {
        this.records = records;
        return this;
    }

    public void newRecord(Record r){
        records.add(r);
        records.sort(new RecordSorter());
        if(records.size() > numRecs){
           records.remove(numRecs);
        }
    }
}

/*


        TopTen topTen = generateData();
        String json = gson.toJson(topTen);
        editor.putString("TopTen", json);


        String jsonFromMemory = prefs.getString("TopTen", "");
        TopTen topTenFromMemory = gson.fromJson(jsonFromMemory, TopTen.class);

    }

    private TopTen generateData() {
        TopTen topTen = new TopTen();
        for (int i = 0; i < 10; i++) {
            Record r = new Record()
                    .setDate(System.currentTimeMillis() - (i * 1000l * 60 * 60 *24))
                    .setName("Or"+i)
                    .setScore(new Random().nextInt(100));

            topTen.getRecords().add(r);
        }
        return topTen;
    }
 */
