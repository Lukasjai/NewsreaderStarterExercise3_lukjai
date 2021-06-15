package at.ac.fhcampuswien.newsanalyzer.ctrl;

import at.ac.fhcampuswien.newsapi.NewsApi;

public class NewsApiExceptions extends Exception{

    public NewsApiExceptions(String msg){
        super(msg);
    }
}
