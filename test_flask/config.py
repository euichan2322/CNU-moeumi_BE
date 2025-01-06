<<<<<<< HEAD
#import os
=======
import os
>>>>>>> 0d38752f4caaf84309c25cb52a5a7d621d5f5a52

class Config:
    #SQLALCHEMY_DATABASE_URI = f"mysql+pymysql://{os.getenv('MYSQL_USER')}:{os.getenv('MYSQL_PASSWORD')}@{os.getenv('MYSQL_HOST')}:{os.getenv('MYSQL_PORT')}/{os.getenv('MYSQL_DB')}"
    SQLALCHEMY_DATABASE_URI = "mysql+pymysql://hello:password@152.67.222.171:3306/bibimping"
    SQLALCHEMY_TRACK_MODIFICATIONS = False
    SECRET_KEY = os.getenv('SECRET_KEY')
