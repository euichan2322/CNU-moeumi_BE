# 해당 폴더의 모든 파이썬 코드를 실행시키는 파이썬 코드. 리눅스 cronjob과 유사.
# 실행시킬때는 백그라운드로 실행시켜야함.
# 리팩토링할것: 로그 찍는 내용 추가.
import os
import time
import subprocess


folder_path = os.getcwd()  # cwd는 현재 경로


exclude_files = ['config.py', 'cron.py'] # 이 파일들은 실행시키지 않음.



def run_python_files():
    for filename in os.listdir(folder_path):
        if filename.endswith('.py') and filename not in exclude_files:
            file_path = os.path.join(folder_path, filename)
            print(f"Running: {file_path}")
            
            subprocess.run(['python3', file_path])


while True:
    run_python_files()
    time.sleep(3600)