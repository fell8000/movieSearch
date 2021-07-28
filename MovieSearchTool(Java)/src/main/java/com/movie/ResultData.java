package com.movie;

public class ResultData {
  private int movieId;
  private String movieTitle;
  private String genreNames;
  private double rating;
  private int voteCount;

  public ResultData(int movieId, String movieTitle, String genreNames, double rating, int voteCount) {
    this.movieId = movieId;
    this.movieTitle = movieTitle;
    this.genreNames = genreNames;
    this.rating = rating;
    this.voteCount = voteCount;
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

  public double getRating() {
    return rating;
  }

  public int getVoteCount() {
    return voteCount;
  }
}
