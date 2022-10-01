import java.time.LocalTime;

/**
 * MPL 2.0 라이센스
 * SAXON
 */

public class Main {

    public static void main(String args[]) throws Exception{

        System.out.println(LocalTime.now());
        new JobManager().run(args);
        System.out.println(LocalTime.now());

    }
}
