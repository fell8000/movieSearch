import pymysql
import cryptography
from datetime import datetime

#mysql connector를 이용하여 mysql server에 연결
conn = pymysql.connect(host='127.0.0.1', user='root', password='ht9562',
                           db='db_201711299', charset='utf8')
#쿼리를 수행하기 위한 cursor 요청
curs = conn.cursor()

#user 테이블 업데이트 함수
def updateUser(filename) :
    with open(filename) as f:
        content = f.readlines()
        for line in content:
            userId = int(line.split("|")[0])
            age = int(line.split("|")[1])
            gender = line.split("|")[2]
            occupation = line.split("|")[3]
            zipcode = line.split("|")[4].replace("\n", "")
            sql = """insert into user(user_id, age, gender, occupation, zipcode) values (%s, %s, %s, %s, %s)"""
            curs.execute(sql, (userId, age, gender, occupation, zipcode))
            conn.commit()

#genre 테이블 업데이트 함수
def updateGenres(filename):
    with open(filename) as f:
        content = f.readlines()
        for line in content:
            genreName = line.split("|")[0]
            genreCode = int(line.split("|")[1].replace("\n", ""))
            sql = """insert into genres(genres_code, genres_name)
                                 values (%s, %s)"""
            curs.execute(sql, (genreCode, genreName))
            conn.commit()

#movie 테이블 업데이트 함수
def updateMovie(filename):
    with open(filename, 'rt', encoding='UTF8') as f:
        content = f.readlines()
        for line in content:
            movieId = int(line.split("|")[0])
            movieName = line.split("|")[1]
            releseDate = line.split("|")[2]
            if releseDate == '':
                releseDateString = None
            else:
                releseDateString = datetime.strptime(releseDate, '%d-%b-%Y').strftime("%Y-%m-%d")



            url = line.split("|")[4]
            list = []
            for i in range(5, 24):
                if (int(line.split("|")[i].replace("\n", "")) == 1):
                    list.append(i - 5)
            genreNames = getGengeNamesFromlist(list)
            sql = """insert into movie(movie_id, movie_title, release_date, videorelease_date, IMDB_URL, genres_names)
                                             values (%s, %s, %s, %s, %s, %s)"""
            curs.execute(sql, (movieId, movieName, releseDateString, None, url, genreNames))
            conn.commit()

#data 테이블 업데이트 함수
def updateData(filename):
    with open(filename) as f:
        content = f.readlines()
        for line in content:
            userId = int(line.split("\t")[0])
            movieId = int(line.split("\t")[1])
            rating = int(line.split("\t")[2])
            timestamp = line.split("\t")[3].replace("\n", "")
            sql = """insert into data(user_id,movie_id,  rating, time_stamp)
                                             values (%s, %s, %s, %s)"""
            curs.execute(sql, (userId, movieId, rating, timestamp))            conn.commit()


#genre code list를 통해서 genre names를 반환하는 함수
def getGengeNamesFromlist(list):
    string = ""
    for num in list:
        sql = "select genres_name from genres where genres_code = " + str(num)
        curs.execute(sql)
        rows = curs.fetchall()
        string += rows[0][0] + ","
    string = string[:-1]
    return string




if __name__ == "__main__":
    print("Started to table(user) update")
    updateUser("data/u.user.tsv")
    print("Completed to table(user) update")
    print("Started to table(genres) update")
    updateGenres("data/u.genre.tsv")
    print("Completed to table(genres) update")
    print("Started to table(data) update")
    updateData("data/u.data.tsv")
    print("Completed to table(data) update")
    print("Started to table(movie) update")
    updateMovie("data/u.item.tsv")
    print("Completed to table(movie) update")
