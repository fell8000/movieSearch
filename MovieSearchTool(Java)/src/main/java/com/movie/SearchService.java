package com.movie;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;


public class SearchService {
  //MySQL Connection
  Connection connection;
  Statement statement;
  ResultSet resultSet;

  public SearchService(String user, String password) {
    //인스턴스 생성 시점에 mysql server와 연결을 맺는다.
    String driverName = "com.mysql.jdbc.Driver";
    String url = "jdbc:mysql://127.0.0.1/db_201711299?useSSL=false&characterEncoding=UTF-8&serverTimezone=UTC";
    try {
      // ① 로드
      Class.forName(driverName);
      // ② 연결
      connection = DriverManager.getConnection(url, user, password);
      statement = connection.createStatement();
    } catch (ClassNotFoundException e) {
      System.out.println("[로드 오류]\n" + e.getStackTrace());
    } catch (SQLException e) {
      System.out.println("[연결 오류]\n" + e.getStackTrace());
    }
  }

  public List<ResultData> search(SearchOption searchOption) throws SQLException {
    //movie 테이블에서 장르를 통해서 검색 (없으면 모두 가져옴)
    if (searchOption.getGenre() != null) {
      String queryString = "select * from movie where genres_names like '%" + searchOption.getGenre() + "%'";
      resultSet = statement.executeQuery(queryString);
    } else {
      String queryString = "select * from movie";
      resultSet = statement.executeQuery(queryString);
    }
    //movie id를 키로 가지는 Movie data 맵 생성 및 저장
    Map<Integer, MovieData> movieMap = new HashMap<>();
    while (resultSet.next()) {
      int movieId = resultSet.getInt("movie_id");
      movieMap.put(movieId, new MovieData(movieId, resultSet.getString("movie_title"), resultSet.getString("genres_names")));
    }

    //user 테이블에서 선택한 Occupation에 해당하는 user_id를 모두 추출 (없으면 넘어감)
    List<Integer> userIdList = null;
    if (searchOption.getOccupation() != null) {
      userIdList = new ArrayList<>();
      String queryString = "select * from user where occupation = '" + searchOption.getOccupation() + "'";
      resultSet = statement.executeQuery(queryString);
      while (resultSet.next()) {
        userIdList.add(resultSet.getInt("user_id"));
      }
    }

    //가져온 movie를 토대로 data 정보 조회
    Map<Integer, List<Data>> dataMap = new HashMap<>();
    for (MovieData movieData : movieMap.values()) {
      List<Data> dataList = new ArrayList<>();
      String queryString = "select * from data where movie_id = " + movieData.getMovieId();
      resultSet = statement.executeQuery(queryString);
      while (resultSet.next()) {
        int userId = resultSet.getInt("user_id");
        //occupation 조건이 있는 경우 위에서 초기화 된 userId 리스트에 해당 하는 것들만 저장하도록 함
        if (userIdList == null || userIdList.contains(userId)) {
          dataList.add(new Data(resultSet.getInt("movie_id"), resultSet.getInt("user_id"), resultSet.getInt("rating")));
        }
      }
      dataMap.put(movieData.getMovieId(), dataList);
    }

    //Rating 필터링 및 출력을 위한 Result Data로 저장
    List<ResultData> resultList = new ArrayList<>();
    for (Entry<Integer, List<Data>> entry : dataMap.entrySet()) {
      //Rating 옵션에 해당되는지 검사
      if (entry.getValue().size() != 0 && isValidRate(searchOption, entry.getValue())) {
        resultList.add(new ResultData(entry.getKey(), movieMap.get(entry.getKey()).getMovieTitle(),
            movieMap.get(entry.getKey()).getGenreNames(), Double.isNaN(getRateAvg(entry.getValue())) ? 0 : getRateAvg(entry.getValue()), entry.getValue().size()));
      }
    }
    //Sorting 옵션 수행
    if (searchOption.getSortOptions() != null) {
      switch (searchOption.getSortOptions()) {
        case RATING:
          resultList = resultList.stream().sorted(Comparator.comparing(ResultData::getRating).reversed())
              .collect(Collectors.toList());
          break;
        case TITLE:
          resultList = resultList.stream().sorted(Comparator.comparing(ResultData::getMovieTitle)).collect(Collectors.toList());
          break;
        case VOTING:
          resultList = resultList.stream().sorted(Comparator.comparing(ResultData::getVoteCount).reversed()).collect(Collectors.toList());
          break;
      }
    }
    return resultList;
  }


  /**
   * 사용자의 옵션에 맞게 Rating 옵션에 data가 해당되는지 확인
   * @param searchOption
   * @param data
   * @return 검사 여부
   */
  private boolean isValidRate(SearchOption searchOption, List<Data> data) {
    if (searchOption.getMinRating() == -1 && searchOption.getMaxRating() == -1) {
      return true;
    } else {
      if (searchOption.getMinRating() != -1 && searchOption.getMinRating() > getRateAvg(data)) {
        return false;
      }
      if (searchOption.getMaxRating() != -1 && searchOption.getMaxRating() < getRateAvg(data)) {
        return false;
      }
      return true;
    }
  }

  /**
   * data의 평점 평균을 구하는 메소드
   * @param data
   * @return 평점 평균
   */
  private double getRateAvg(List<Data> data) {
    int sum = 0;
    int count = 0;
    for (Data val : data) {
      sum += val.getRating();
      count++;
    }
    return (double) sum / count;
  }
}
