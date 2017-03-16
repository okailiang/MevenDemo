/**
 * 常用的本地测试
 *
 * @author oukailiang
 * @create 2016-09-23 下午3:24
 */

public class CommonTest {
    public enum MsgType {
        even, text;

        public static MsgType getMsgType(String msgTYpe) {
            return valueOf(msgTYpe.toLowerCase());
        }
    }

    public static void testHashcode(String str) {
        switch (MsgType.getMsgType(str)) {
            case even:
                System.out.println(str);
                break;
            case text:
                System.out.println(str);
                break;
            default:
                ;
        }
        System.out.println("Aa".hashCode());
        System.out.println("aA".hashCode());
    }

    public static void main(String[] args) {
        testHashcode("even");

    }
}
