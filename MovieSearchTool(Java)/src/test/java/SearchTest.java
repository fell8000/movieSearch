import com.jinseong.soft.movie.ResultData;
import com.jinseong.soft.movie.SearchOption;
import com.jinseong.soft.movie.SearchService;
import com.jinseong.soft.movie.SortOptions;
import java.sql.SQLException;
import java.util.List;
import org.junit.Test;

public class SearchTest {

  @Test
  public void test() throws SQLException {
    SearchService searchService = new SearchService("root", "4112665aa!");
    SearchOption searchOption = new SearchOption(
        null,
        null,
        4.5,
        -1,
        SortOptions.RATING
    );
    List<ResultData> list = searchService.search(searchOption);
    System.out.println("영화 ID    제목                                                                          장르                                                  평점    Vote수");
    System.out.println("==========================================================================================================================================================");
    for (ResultData resultData : list) {
      System.out.println(
          String.format("%-5d\t\t\t%-75s\t\t%-50s\t\t%.2f\t\t%d", resultData.getMovieId(), resultData.getMovieTitle(),
              resultData.getGenreNames(), resultData.getRating(), resultData.getVoteCount()));
    }
  }
}
