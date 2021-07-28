package com.movie;

public class SearchOption {
  private String genre;
  private String occupation;
  private double minRating;
  private double maxRating;
  private SortOptions sortOptions;

  public SearchOption(String genre, String occupation, double minRating, double maxRating, SortOptions sortOptions) {
    this.genre = genre;
    this.occupation = occupation;
    this.minRating = minRating;
    this.maxRating = maxRating;
    this.sortOptions = sortOptions;
  }

  public String getGenre() {
    return genre;
  }

  public String getOccupation() {
    return occupation;
  }

  public double getMinRating() {
    return minRating;
  }

  public double getMaxRating() {
    return maxRating;
  }

  public SortOptions getSortOptions() {
    return sortOptions;
  }
}
