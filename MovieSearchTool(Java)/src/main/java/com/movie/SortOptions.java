package com.movie;

public enum SortOptions {
  TITLE("제목"),
  RATING("평점"),
  VOTING("VOTE");

  private String displayName;

  private SortOptions(String displayName) {
    this.displayName = displayName;
  }

  public String getDisplayName() {
    return displayName;
  }

  public static SortOptions fromName(String name) {
    for (SortOptions value : values()) {
      if (value.getDisplayName().equals(name)) {
        return value;
      }
    }
    return null;
  }
}
