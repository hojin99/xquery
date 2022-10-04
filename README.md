# xquery
xml을 xquery 문법으로 파싱 하기 위한 CLI 도구  
결과는 csv로 가졍하고 구현했지만, 원하는 대로 출력하면 됨  

## 실행 방법
### single_run.sh, single_run_bat
- 작업 목록 기반 배치 실행  
`java -jar jar파일 joblist.csv`  
- 인자값 기반 단일 실행  
`java -jar jar파일 xml파일1..., xquery파일, 결과파일`
- single_run_err.sh, single_run_err.bat는 중간에 에러가 날 경우 처리 테스트를 위한 파일  

### multi_run.sh, multi_run_bat
- 인자값 기반 단일 실행을 별도 프로세스로 실행 시 성능 비교를 위한 파일  
- 프로세스 실행 오버헤드로 성능 저하  
