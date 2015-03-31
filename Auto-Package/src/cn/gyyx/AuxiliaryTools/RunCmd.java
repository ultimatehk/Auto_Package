package cn.gyyx.AuxiliaryTools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class RunCmd {
	/**
	 * 运行cmd命令并输出结果
	 * 
	 * @param cmd
	 */
	public static void runCmd(String cmd) {

		BufferedReader br = null;

		try {

			Process p = Runtime.getRuntime().exec(cmd);

			br = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String msg = null;
			while ((msg = br.readLine()) != null) {
				System.out.println(msg);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null) {
					br.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
