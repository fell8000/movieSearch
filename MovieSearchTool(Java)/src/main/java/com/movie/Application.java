package com.movie;

import java.util.List;
import java.util.Scanner;

public class Application {

  static Scanner scanner = new Scanner(System.in);
  static SearchService searchService;

  public static void main(String[] args) {
    searchService = new SearchService(args[0], args[1]); //아이디 비밀번호
    while (true) {
      doAsk();
      System.out.print("종료를 원하시면 'exit'을 입력해주세요. *그렇지 않다면 enter를 입력해주세요: ");
      String answer = scanner.nextLine();

      if (answer.equals("exit")) {
        return;
      }
    }
  }

  private static void doAsk() {
    try {
      System.out.print("장르를 입력해주세요 (ex. Action) *만약 없다면 바로 enter를 입력해주세요: ");
      String genre = scanner.nextLine();
      System.out.print("사용자의 직업을 입력해주세요 (ex. doctor) *만약 없다면 바로 enter를 입력해주세요: ");
      String occupation = scanner.nextLine();
      System.out.print("평균 평점의 최솟값을 입력해주세요 (ex. 4.0) *만약 없다면 바로 enter를 입력해주세요: ");
      String min = scanner.nextLine();
      System.out.print("평균 평점의 최댓값을 입력해주세요 (ex. 4.0) *만약 없다면 바로 enter를 입력해주세요: ");
      String max = scanner.nextLine();
      System.out.print("정렬 옵션을 입력해주세요 (종류. 제목, 평점, VOTE) *만약 없다면 바로 enter를 입력해주세요: ");
      String sortingOption = scanner.nextLine();

      SearchOption searchOption = new SearchOption(
          genre.equals("") ? null : genre,
          occupation.equals("") ? null : occupation,
          min.equals("") ? -1 : Double.parseDouble(min),
          max.equals("") ? -1 : Double.parseDouble(max),
          sortingOption.equals("") ? null : SortOptions.fromName(sortingOption)
      );
      List<ResultData> resultDataList = searchService.search(searchOption);
      System.out.println();
      System.out.println("영화 ID    제목                                                                          장르                                                  평점    Vote수");
      System.out.println("==========================================================================================================================================================");
      for (ResultData resultData : resultDataList) {
        System.out.println(
            String.format("%-5d\t\t\t%-75s\t\t%-50s\t\t%.2f\t\t%d", resultData.getMovieId(), resultData.getMovieTitle(),
                resultData.getGenreNames(), resultData.getRating(), resultData.getVoteCount()));
      }
    } catch (Exception e) {
      e.printStackTrace();
      System.out.println("입력값 중 잘못된 것들이 있습니다. 확인해주세요");
    }
  }
}
