#리팩토링 할것: 객체지향으로 클래스 이용해서 모든 파일 하나로 통합했으면 좋겠음. + for문 escape
import requests, json
from bs4 import BeautifulSoup

from sqlalchemy import create_engine, Column, Integer, String, DateTime
from sqlalchemy.ext.declarative import declarative_base
from sqlalchemy.orm import sessionmaker
from config import Config


#크롤링 부분. 이거 함수화, 클래스화 해서 리팩토링 하기...
url = 'https://jnu.nccoss.kr/www/'

response = requests.get(url)

if response.status_code == 200:
    html = response.text
    soup = BeautifulSoup(html, 'html.parser')
    ul = soup.select_one('#content > article.mainContent-wrap.mainContent-board > div > section.mainLibrary-wrap > div.mainLibrary-list.grayBg-wrap')
    titles = ul.select('ul > li > a > span > strong')
    links = ul.select('li')

    links = [a.get('href') for a in ul.find_all('a')]
    titles = [title.get_text() for title in titles]
    #titles = [title.get_text() for title in titles]
    # 출력하여 확인
    #for link in links:
    #    print(link)
    #for titile in text_titles:
    #    print(titile)


    #for title in titles:
    #    print(title.get_text())

    '''
    for link in links:
        print(link.get_text())
    '''
    
    timestamp = []
    for i in range(len(titles)):
        url2 = url + links[i].replace('/www/','')
        respones2 = requests.get(url2)
        if respones2.status_code == 200:
            html2 = respones2.text
            soup2 = BeautifulSoup(html2, 'html.parser')
            timestamp.append(soup2.select_one('#content > div > article.viewContainer-wrap > section > div.viewContainer-info > ul > li').text)
        else :
            print(respones2.status_code)
    mapped_list = [{"title": titles[i], "link": links[i], "timestamp" : timestamp[i]} for i in range(len(titles))]

    for item in mapped_list:
        item['business_group_id'] = 3
    print(mapped_list)


else:
    print(response.status_code)




# # 여기부터 데이터베이스
DATABASE_URL = Config.SQLALCHEMY_DATABASE_URI
Base = declarative_base()


class Notice(Base):
    __tablename__ = 'alarms'

    id = Column(Integer, primary_key=True, autoincrement=True)
    business_group_id = Column(Integer, nullable = False)
    title = Column(String(255), nullable=False, unique=True)
    link = Column(String(255), nullable=False)
    alarm_at = Column(DateTime, nullable=False)

    def __repr__(self):
      return f"<Notice(title={self.title}, link={self.link})>"


engine = create_engine(DATABASE_URL, echo=True)
Session = sessionmaker(bind=engine)
session = Session()

Base.metadata.create_all(engine)

# 데이터 insert 함수
def insert_data(title, link, business_group_id, alarm_at):
    # 중복 체크: 이미 존재하는 제목이 있으면 종료
    existing_notice = session.query(Notice).filter_by(title=title).first()

    if existing_notice:
      print(f"'{title}'가 이미 존재합니다. 종료합니다.")
      return

    # 중복이 없으면 데이터 삽입
    new_notice = Notice(title=title, link=link, business_group_id=business_group_id, alarm_at=alarm_at)
    session.add(new_notice)
    session.commit()
    print(f"'{title}'가 성공적으로 삽입되었습니다.")

# 데이터 삽입
# 이것도 리팩토링 하기. 함수가 여러번 실행되는 for문인데, 중복이 있으면 for문까지 escape했으면 좋겠음.
for item in mapped_list:
    insert_data(item['title'], item['link'], item['business_group_id'], item['timestamp'])


session.close()


