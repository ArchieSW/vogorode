package dev.archie.stress.tester.entities;

import lombok.Data;

import java.util.List;

@Data
public class Page<T> {
    public List<T> content;
    public int totalPages;
    public int totalElements;
    public boolean last;
    public int size;
    public int number;
    public int numberOfElements;
    public boolean first;
    public boolean empty;
}


