package at.ac.fhcampuswien.newsanalyzer.ctrl;

import at.ac.fhcampuswien.newsapi.NewsApi;
import at.ac.fhcampuswien.newsapi.NewsApiBuilder;
import at.ac.fhcampuswien.newsapi.beans.Article;
import at.ac.fhcampuswien.newsapi.beans.NewsResponse;
import at.ac.fhcampuswien.newsapi.enums.Category;
import at.ac.fhcampuswien.newsapi.enums.Endpoint;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Controller {

	public static final String APIKEY = "27015cb360cb4a658e22288eb48ad6a6";  //TODO add your api key

	public void process(NewsApi newsApi) {
		System.out.println("Start process");

		//TODO implement Error handling
	NewsResponse newsResponse = new NewsResponse();
	try{
			newsResponse = newsApi.getNews();
			List<Article> articles = newsResponse.getArticles();
			if(articles.isEmpty()){
				throw new NewsApiExceptions("Empty news response. Please check parameters.");
			}
		}
	catch (NewsApiExceptions e){
		System.out.println("No valid search results" + e.getMessage());
		return;
	}
		//TODO load the news based on the parameters
		List<Article> articles = newsResponse.getArticles();
		articles.stream().forEach(article -> System.out.println(article.toString()));

		//TODO implement methods for analysis
		System.out.println("\n \n");

		//a. amount of articles
		System.out.println("Amount of Articles: " +articles.stream().count());

		//b. Provider with the most Articles
		String provider = articles.stream()
				.collect(Collectors.groupingBy(article -> article.getSource().getName(), Collectors.counting()))
				.entrySet()
				.stream()
				.max(Comparator.comparingInt(t->t.getValue().intValue()))
				.get()
				.getKey();
		System.out.println("Provider with the most Articles: " +provider );

		//c. Author with the shortest Name
		Optional<String> shortestName = articles.stream()
				.map(Article::getAuthor)
				.filter(Objects::nonNull)
				.min(Comparator.comparing(String::length));
		System.out.println("Author with the shortest Name: " + shortestName);

		/*
		String author = articles.stream()
				.collect(Collectors.groupingBy(article -> article.getAuthor().length()))
				.entrySet()
				.stream()
				.min(Comparator.comparing(t->getClass().getName().length()))
				.get();
		System.out.println();
			*/


		//d. Longest Title sorted by alphabet
		List<String> sortedList = articles.stream()
				.map(Article::getTitle)
				.sorted(Comparator.comparing(String::length)
				.reversed()
				.thenComparing(Comparator.comparing(String::length)))
				.collect(Collectors.toList());
		System.out.println("Longest Title sorted by alphabet: " + sortedList);


	/*
		List<Article> articles = newsResponse.getArticles();
		Stream<Article> articleStream = articles.stream();
		System.out.println("Number of article:" + articles.stream().count());
		System.out.println("Shortest Author name: " +articles.stream().min((e1,e2) ->e1.getAuthor().compareTo(e2.getAuthor())));
		articles.stream().forEach(article -> System.out.println(article.toString()));

		System.out.println("End process");

*/



	}

	public Object getData() {
		
		return null;
	}
}
