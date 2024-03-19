import java.io.FileNotFoundException;

/**
 * @author Guan
 * @date 2022/1/18 11:22
 * @Description :
 */
public class StringUtilsTest
{

    public static void main(String[] args) throws FileNotFoundException
    {

        // String s = "D:\\DIP\\GitProject\\desktop\\desktop\\src\\main\\webapp\\";
        //
        // String[] split = s.split("/");
        // StringBuilder sb = new StringBuilder();
        // for (int i = 0; i < split.length - 1; i++) {
        //     sb.append(split[i]).append("\\");
        // }
        //
        // System.out.println(sb.toString());

        System.getProperties().list(System.out);
        // System.out.println(System.getProperties().get("file.separator"));
        // System.out.println(System.getProperties().get("sun.desktop"));
        // System.out.println(System.getProperties());
        // System.getProperties().forEach((k, v) -> {
        //     System.out.println(k + "=" + v);
        // });
    }
}
