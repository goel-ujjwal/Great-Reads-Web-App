package com.goel_ujjwal.greatreads.search;

import java.util.List;

// Class for mapping the response of Open Library search api

public class SearchResult {
    
    private int numFound;
    private List<SearchResultBook> docs;
    
    public int getNumFound() {
        return numFound;
    }
    public void setNumFound(int numFound) {
        this.numFound = numFound;
    }
    public List<SearchResultBook> getDocs() {
        return docs;
    }
    public void setDocs(List<SearchResultBook> docs) {
        this.docs = docs;
    }
    
}
