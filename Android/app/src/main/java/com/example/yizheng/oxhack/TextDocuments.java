package com.example.yizheng.oxhack;

import java.util.*;

// Request body
interface Documents {

}

class TextDocuments implements Documents {
    public List<TextDocument> documents;

    public TextDocuments() {
        this.documents = new ArrayList<>();
    }
    public void add(String id, String language, String text) {
        this.documents.add (new TextDocument(id, language, text));
    }
}

class TextDocument {
    public String id, language, text;

    public TextDocument(String id, String language, String text){
        this.id = id;
        this.language = language;
        this.text = text;
    }
}

// Response
class TextResultDocuments implements Documents{

    public List<TextResultDocument> documents;

    public TextResultDocuments() {
        this.documents = new ArrayList<>();
    }
    public void add(String[] keyPhrases, String id) {
        this.documents.add (new TextResultDocument(keyPhrases, id));
    }

}

class TextResultDocument {
    public String[] keyPhrases;
    public String id;

    public TextResultDocument(String[] keyPhrases, String id){
        this.keyPhrases = keyPhrases;
        this.id = id;
    }
}