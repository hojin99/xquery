import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class JobManager {

    /**
     * 작업관리자에 작업 지시
     *
     * @param args 1개일 경우 작업내역파일, 2개 이상일 경우 파일명..., Xquery파일명, 결과파일명
     * @throws Exception
     */
    public void run(String[] args) throws Exception {
        // 1. 작업 목록 선언
        ArrayList<HashMap<String,Object>> jobList = new ArrayList<>();

        // 2. 작업 목록 작성
        if (args.length == 1) {
            // 멀티 작업(csv파일의 작업목록 읽기)
            File csv = new File(args[0]);
            BufferedReader br = null;
            String line;

            try {
                br = new BufferedReader(new FileReader(csv));
                while ((line = br.readLine()) != null) {
                    String[] lineArr = line.split(",");
                    addJob(jobList, lineArr);
                }
            } finally {
                try {
                    if(br != null) br.close();
                } catch(IOException ex) {
                    ex.printStackTrace();
                }
            }
        } else if (args.length > 2) {
            // 단일 작업(인자값)
            addJob(jobList, args);
        } else {
            throw new Exception("Invalid arguments" +
                    "Usage: " +
                    "단일작업 - xml파일..., xquery파일, 결과 파일" +
                    "멀티작업 - csv파일(단일 작업 인자값을 여러 줄로 표현)");
        }

        // 3. 작업 실행
        SaxonRunner parser = new SaxonRunner();
        int cnt = 0;

        for(HashMap<String,Object> job : jobList) {
            System.out.println("job cnt : " + ++cnt);
            System.out.println("result fle : " + job.get("result"));

            try {
                // 단일 파일이 에러가 나더라도 진행 되도록 함
                parser.runApp((String[]) job.get("xml"), (String) job.get("xquery"), (String) job.get("result"));
                System.out.println(job.get("result") + " completed!!");
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 작업목록에 작업 추가
     *
     * @param jobList 작업목록
     * @param args 파일명..., Xquery파일명, 결과파일명
     */
    private void addJob(ArrayList<HashMap<String,Object>> jobList, String[] args) {

        HashMap<String, Object> job = new HashMap<>();

        String[] files = new String[args.length - 2];
        System.arraycopy(args, 0, files, 0, args.length - 2);

        job.put("xquery", args[args.length - 2]);
        job.put("result", args[args.length - 1]);
        job.put("xml", files);

        jobList.add(job);
    }

}
