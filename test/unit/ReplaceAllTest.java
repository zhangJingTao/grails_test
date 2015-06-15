/**
 * Created by SoulSleep on 2015/6/15.
 */
public class ReplaceAllTest {
    public static void main(String[] args) {
        String s = "http://pic2.zhimg.com/4b491d59b69213d1c03da5224d6dfd5d_b.jpg\n" +
                "http://pic1.zhimg.com/4b491d59b69213d1c03da5224d6dfd5d_b.jpg\n" +
                "http://pic4.zhimg.com/4b491d59b69213d1c03da5224d6dfd5d_b.jpg\n" +
                "http://pic5.zhimg.com/4b491d59b69213d1c03da5224d6dfd5d_b.jpg\n" +
                "http://pic.zhimg.com/4b491d59b69213d1c03da5224d6dfd5d_b.jpg\n";
        System.out.print(s.replaceAll("http://pic[0-9].zhimg.com","/zhihu/pic?url="));
    }
}
