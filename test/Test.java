import java.util.Arrays;


/**
 * @date 2016年12月30日下午12:54:15
 * @author jq.yin@i-vpoints.com
 */
public class Test {
	public static void main(String[] args) {
		String a = "q‮e‭";
		byte[] c = a.getBytes();
		System.out.println(Arrays.toString(c));
		byte[] b1 = {c[1],c[2],c[3]};
		byte[] b2 = {c[5],c[6],c[7]};
		System.out.println(new String(c));
		System.out.println(new String(b1));
		System.out.println(new String(b2));
		System.out.println(Arrays.toString(a.toCharArray()));
	}
}
