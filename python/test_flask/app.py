from flask import Flask, request, jsonify
from flask_sqlalchemy import SQLAlchemy
from config import Config

app = Flask(__name__)
app.config.from_object(Config)

db = SQLAlchemy(app)

class User(db.Model):
    __tablename__ = 'user'
    id = db.Column(db.Integer, primary_key=True)
    account_id = db.Column(db.String(80), unique=True, nullable=False)
    password = db.Column(db.String(128), nullable=False)
    email = db.Column(db.String(120), unique=True, nullable=False)

    def __repr__(self):
        return f'<User {self.account_id}>'




@app.route('/api/users/register', methods=['POST'])
def register_user():
    data = request.get_json()

    if not data:
        return jsonify({'message': 'No input data provided'}), 400

    account_id = data.get('accountId')
    password = data.get('password')
    email = data.get('email')

    if not account_id or not password or not email:
        return jsonify({'message': 'accountId, password, and email are required'}), 400

    if User.query.filter_by(account_id=account_id).first():
        return jsonify({'message': 'Account ID already exists'}), 409

    if User.query.filter_by(email=email).first():
        return jsonify({'message': 'Email already registered'}), 409

    new_user = User(account_id=account_id, password=password, email=email)

    try:
        db.session.add(new_user)
        db.session.commit()
        return jsonify({'message': '회원가입 성공'}), 201
    except Exception as e:
        db.session.rollback()
        return jsonify({'message': '회원가입 실패', 'error': str(e)}), 500



if __name__ == '__main__':
    app.run(debug=True)
