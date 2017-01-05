import java.util.Arrays;

/**
 * 微信特殊字符
 * @date 2016年12月30日下午12:54:15
 * @author jq.yin@i-vpoints.com
 */
public class Test {
	
	public static void main(String[] args) {
//		test1();
//		test2();
		test3();
	}
	
	public static void test1() {
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
	
	public static void test2(){
		String source = "射ีึีึีึีึีึีึีึีึีึีึ๊๋ีึ๊๋ีึีึีึีึีึ๊๋ีึีึ้๊ีึีึีึ็้ีึีึีึ็้ีึ็้ีึีึีึีึ้๊ีึีึีึ้๊ีึีึีึ๊๋ีึีึ็้ีึ้๊ีึีึีึีึีึ๊๋ีึ้๊ีึ้๊ีึีึีึีึ๊๋ีึีึีึีึีึีึ้๊ีึีึีึีึีึีึีึีึีึีึีึีึีึีึีึีึ้๊ีึ็้ีึ็้ีึ้๊ีึ้๊ีึีึีึีึ็้ีึ๊๋ีึีึีึีึีึีึ็้ีึีึีึ็้ีึีึีึ็้ีึ๊๋ีึีึีึ้๊ีึีึีึ้๊ีึีึีึ้๊ีึีึีึ็้ีึีึ๊๋ีึีึีึีึีึีึีึ็้ีึีึ๊๋ีึีึ้๊ีึ็ุุ้ีึีึีึีึีึีึีึีึีึีึ";
		String str = "呵呵呵";
		byte[] sourceByte = source.getBytes();
		sourceByte = Arrays.copyOfRange(sourceByte, 3, sourceByte.length);
		int addLength = sourceByte.length;
		byte[] result = new byte[]{};
		for(int i=0;i<str.length();i++){
			byte[] sc = str.substring(i, i+1).getBytes();
			sc = Arrays.copyOf(sc, addLength+sc.length);
			// 添加byte[]
			System.arraycopy(sourceByte, 0, sc, sc.length-addLength, addLength);
			// 将数组扩容
			result = Arrays.copyOf(result, result.length+sc.length);
			// 添加至结果数组
			System.arraycopy(sc, 0, result, result.length-sc.length, sc.length);
		}
		System.out.println(new String(result));
		
	}
	
	public static void test3(){
		String a = "射ีึีึีึีึีึีึีึีึีึีึ๊๋ีึ๊๋ีึีึีึีึีึ๊๋ีึีึ้๊ีึีึีึ็้ีึีึีึ็้ีึ็้ีึีึีึีึ้๊ีึีึีึ้๊ีึีึีึ๊๋ีึีึ็้ีึ้๊ีึีึีึีึีึ๊๋ีึ้๊ีึ้๊ีึีึีึีึ๊๋ีึีึีึีึีึีึ้๊ีึีึีึีึีึีึีึีึีึีึีึีึีึีึีึีึ้๊ีึ็้ีึ็้ีึ้๊ีึ้๊ีึีึีึีึ็้ีึ๊๋ีึีึีึีึีึีึ็้ีึีึีึ็้ีึีึีึ็้ีึ๊๋ีึีึีึ้๊ีึีึีึ้๊ีึีึีึ้๊ีึีึีึ็้ีึีึ๊๋ีึีึีึีึีึีึีึ็้ีึีึ๊๋ีึีึ้๊ีึ็ุุ้ีึีึีึีึีึีึีึีึีึีึ";
		String b = "呵";
		String result = "";
		for(int i=0;i<b.length();i++){
			result += a.replace("射", b.substring(i, i+1));
		}
		System.out.println(result);
	}
}
