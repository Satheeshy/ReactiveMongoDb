package com.example.demo;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

// reactive extension spring rest controller
@RestController
public class MovieController {
	
	@Autowired
	private MovieRepository movieRepository;
	
	@GetMapping("/movies")
	public Flux<Movie> getAllMovies(){
		return movieRepository.findAll();
	}
	
	@PostMapping("/movies")
	public Mono<Movie> createMovie(@Valid @RequestBody Movie movie)
	{
		return movieRepository.save(movie);
	}
	
	@GetMapping("/movies{id}")
	public Mono<ResponseEntity<Movie>> getMovieById(@PathVariable(value="id") String movieid){
		return movieRepository.findById(movieid)
				.map(m-> ResponseEntity.ok(m))
				.defaultIfEmpty(ResponseEntity.notFound().build());
	}
	
	@PutMapping("/movies/{id}")
	public Mono<ResponseEntity<Movie>> updateTweet(@PathVariable String id,@Valid @RequestBody Movie m){
		return movieRepository.findById(id)
				.flatMap(movie -> {
					movie.setTitle(m.getTitle());
					return movieRepository.save(movie);
				})
				.map(movie -> new ResponseEntity<>(movie,HttpStatus.OK))
				.defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
				
				
	}
	@DeleteMapping("/movies/{id}")
    public Mono<ResponseEntity<Void>> deleteMovie(@PathVariable(value = "id") String movieId) {
		return movieRepository.findById(movieId)
                .flatMap(movie ->
                movieRepository.delete(movie)
                            .then(Mono.just(new ResponseEntity<Void>(HttpStatus.OK)))
                )
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
				
	}
	
	@GetMapping(value = "/stream/movies", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Movie> streamAllTweets() {
        return movieRepository.findAll();
    }
	
	
}
