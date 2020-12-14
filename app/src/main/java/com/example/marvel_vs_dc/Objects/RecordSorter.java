package com.example.marvel_vs_dc.Objects;

import java.util.Comparator;

// Comparator for sorting topten list
public class RecordSorter implements Comparator<Record> {
    @Override
    public int compare(Record o1, Record o2) {
        return o2.getHero().getScore() - o1.getHero().getScore();
    }
}
