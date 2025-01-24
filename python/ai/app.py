from sqlalchemy import create_engine, text
from sentence_transformers import SentenceTransformer, util
import pandas as pd
from datetime import datetime, timedelta
from flask import Flask, jsonify
import os


app = Flask(__name__)

DATABASE_URI = "mysql+pymysql://hello:password@152.67.222.171:3306/bibimping2"
engine = create_engine(DATABASE_URI)

# 모델 로드
model = SentenceTransformer('jhgan/ko-sroberta-multitask')

# 게시물 그룹 정보 매핑
group_mapping = {
    "1": {"business_group_name": "sojoong", "business_group_id": "1"},
    "2": {"business_group_name": "inhyuck", "business_group_id": "2"},
    "3": {"business_group_name": "potal", "business_group_id": "3"},
    "4": {"business_group_name": "haksa", "business_group_id": "4"},
    "5": {"business_group_name": "janghack", "business_group_id": "5"},
    "6": {"business_group_name": "chjin", "business_group_id": "6"},    
    "7": {"business_group_name": "chahyuck", "business_group_id": "7"},
    "8": {"business_group_name": "EAI", "business_group_id": "8"}
}
# 현재 시점에서 3개월 전 날짜 계산
three_months_ago = datetime.now() - timedelta(days=90)

# 날짜를 문자열로 변환
three_months_ago_str = three_months_ago.strftime('%Y-%m-%d %H:%M:%S')

# SQLAlchemy에서 사용할 쿼리 작성
query = text("""
SELECT title, link, alarm_at, business_group_id
FROM alarms
WHERE alarm_at >= :three_months_ago
""")

# 데이터 가져오기 (바로 DataFrame으로 변환)
with engine.connect() as connection:
    alarm_df = pd.read_sql(query, connection, params={"three_months_ago": three_months_ago})

# `title_with_group` 생성
alarm_df['title_with_group'] = alarm_df['title'] + ', ' + alarm_df['business_group_id'].astype(str)

# 선택한 컬럼으로 데이터프레임 필터링
df = alarm_df[['title_with_group', 'title', 'link', 'alarm_at', 'business_group_id']]

# 유사도 계산 함수
def get_similar_titles(query, selected_tags=None):
    # 쿼리 임베딩 생성
    query_embedding = model.encode(query, convert_to_tensor=True)

    # `title_with_group` 임베딩
    title_embeddings = model.encode(df['title_with_group'].tolist(), convert_to_tensor=True)

    if selected_tags:
        # 태그 임베딩 생성
        tag_embeddings = model.encode(selected_tags, convert_to_tensor=True)
        similarity_scores = []
        for tag_embedding in tag_embeddings:
            similarities = util.cos_sim(tag_embedding, title_embeddings)[0]
            similarity_scores.append(similarities)
        # 태그의 평균 유사도 계산
        average_similarity = sum(similarity_scores) / len(similarity_scores)
        # 쿼리 임베딩과 태그 임베딩을 결합한 유사도 계산
        total_similarity = 0.5 * util.cos_sim(query_embedding, title_embeddings)[0] + 0.5 * average_similarity
    else:
        # 쿼리 임베딩과 title_with_group 임베딩 간 유사도 계산
        total_similarity = util.cos_sim(query_embedding, title_embeddings)[0]

    # 유사도 결과 정렬
    cosine_scores = total_similarity.tolist()
    indexed_scores = list(enumerate(cosine_scores))
    top_results = sorted(indexed_scores, key=lambda x: x[1], reverse=True)[:5]

    # 상위 5개 결과 반환
    return [(df.iloc[idx]['title'], df.iloc[idx]['link'], df.iloc[idx]['alarm_at'], df.iloc[idx]['business_group_id'], score)
            for idx, score in top_results]

# Flask 엔드포인트
@app.route('/ai-recommendation', methods=['GET', 'POST'])
def search():
    # 쿼리 리스트
    queries = [
        ('2017 특용작물학 (식물분자생리학)분야 대학원생 모집, Graduate student recruiting', 1),
        ('광주광역시 동구 청년네트워크 위원 공개모집 안내', 2),
        ('재단법인 동아시아연구원(EAI) 장학생 선발 안내', 3)
    ]

    # 선택된 태그
    selected_tags = ["공학", "보건"]

    # 결과 저장
    all_recommendations = []

    # 모든 쿼리 처리
    for query, category in queries:
        # 쿼리에 카테고리를 추가
        query_with_category = f"{query} [Category: {category}]"

        # 유사한 타이틀 추천
        recommendations = get_similar_titles(query_with_category, selected_tags)

        # 추천된 게시글을 처리
        for title, link, alarm_at, business_group_id, score in recommendations:
            # business_group_id를 문자열로 변환하여 매핑
            group_info = group_mapping.get(str(business_group_id), {})

            # 게시글 데이터를 묶어서 추가
            all_recommendations.append({
                "business_group_name": group_info.get("business_group_name", "unknown"),
                "business_group_id": group_info.get("business_group_id", "unknown"),
                "alarm": {
                    "title": title,
                    "url": link,
                    "timestamp": alarm_at.strftime('%Y-%m-%d %H:%M:%S')
                }
            })

    top_5_recommendations = all_recommendations[:5]

    # 최종 JSON 응답 구성
    response = {
        "response": top_5_recommendations
    }

    # JSON 응답 반환
    return jsonify(response)



if __name__ == '__main__':
    app.run(port=80, debug=True)