package com.example.demo;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;


import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

// main application class
@SpringBootApplication
public class DemoApplication {

	
	@Bean
	CommandLineRunner demo(MovieRepository movieRepository) {
		CommandLineRunner runner;
		runner = (args)->{
		     movieRepository.deleteAll().subscribe();
			List<String> list = Arrays.asList("hai","adsfas","sdfasdf","gasfddsa","asdghas");
			list.stream().map(title -> new Movie(UUID.randomUUID().toString(),title,"romance"))
			.map(movie -> movieRepository.save(movie))
			.forEach(movie -> movie.subscribe(System.out::println));
			
		};
		
		return runner;
	}

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	
	}
	
	
	
	
}

@Repository
interface MovieRepository extends ReactiveMongoRepository<Movie, String>{
	
}


@Document
class Movie{
	@Id
	private String id;
	
	private String title,genre;

	public Movie() {
		// TODO Auto-generated constructor stub
	}
	public Movie(String id, String title, String genre) {
		super();
		this.id = id;
		this.title = title;
		this.genre = genre;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}
	
	
	
}
