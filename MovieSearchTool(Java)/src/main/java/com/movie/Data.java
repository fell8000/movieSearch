package com.movie;

public class Data {
  private int user_id;
  private int movie_id;
  private int rating;

  public Data(int movie_id, int user_id, int rating) {
    this.movie_id = movie_id;
    this.user_id = user_id;
    this.rating = rating;
  }

  public int getMovie_id() {
    return movie_id;
  }

  public int getUser_id() {
    return user_id;
  }

  public int getRating() {
    return rating;
  }
}
