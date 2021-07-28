package com.movie;

public class MovieData {
  private int movieId;
  private String movieTitle;
  private String genreNames;

  public MovieData(int movieId, String movieTitle, String genreNames) {
    this.movieId = movieId;
    this.movieTitle = movieTitle;
    this.genreNames = genreNames;
  }

  public int getMovieId() {
    return movieId;
  }

  public String getMovieTitle() {
    return movieTitle;
  }

  public String getGenreNames() {
    return genreNames;
  }
}
