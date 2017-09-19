package com.risetek.usbprinter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Vector;

/**
 * Hello world!
 *
 */
public class Tsc {

	private static byte[] createLabel(String command) {

		// 标签打印
		LabelCommand tsc = new LabelCommand();
		tsc.addSize(300, 500); // 设置标签尺寸，按照实际尺寸设置
		tsc.addGap(2); // 设置标签间隙，按照实际尺寸设置，如果为无间隙纸则设置为0
		tsc.addDirection(LabelCommand.DIRECTION.BACKWARD, LabelCommand.MIRROR.NORMAL);// 设置打印方向
		tsc.addReference(0, 0);// 设置原点坐标
		tsc.addTear(LabelUtils.ENABLE.ON); // 撕纸模式开启

		tsc.addCls();// 清除打印缓冲区
		// 绘制简体中文
		tsc.addText(20, 20, LabelCommand.FONTTYPE.SIMPLIFIED_CHINESE, LabelCommand.ROTATION.ROTATION_0,
				LabelCommand.FONTMUL.MUL_1, LabelCommand.FONTMUL.MUL_1, "     天猫超市");
		tsc.addText(20, 50, LabelCommand.FONTTYPE.SIMPLIFIED_CHINESE, LabelCommand.ROTATION.ROTATION_0,
				LabelCommand.FONTMUL.MUL_1, LabelCommand.FONTMUL.MUL_1, "商品：农夫山泉");
		tsc.addText(20, 80, LabelCommand.FONTTYPE.SIMPLIFIED_CHINESE, LabelCommand.ROTATION.ROTATION_0,
				LabelCommand.FONTMUL.MUL_1, LabelCommand.FONTMUL.MUL_1, "规格：箱");
		tsc.addText(20, 110, LabelCommand.FONTTYPE.SIMPLIFIED_CHINESE, LabelCommand.ROTATION.ROTATION_0,
				LabelCommand.FONTMUL.MUL_1, LabelCommand.FONTMUL.MUL_1, "金额：￥16.00");
		tsc.addText(20, 140, LabelCommand.FONTTYPE.SIMPLIFIED_CHINESE, LabelCommand.ROTATION.ROTATION_0,
				LabelCommand.FONTMUL.MUL_1, LabelCommand.FONTMUL.MUL_1, "时间：2017/05/19 15:00");
		tsc.addText(20, 170, LabelCommand.FONTTYPE.SIMPLIFIED_CHINESE, LabelCommand.ROTATION.ROTATION_0,
				LabelCommand.FONTMUL.MUL_1, LabelCommand.FONTMUL.MUL_1, "电话：18818181818");

		tsc.addText(20, 200, LabelCommand.FONTTYPE.SIMPLIFIED_CHINESE, LabelCommand.ROTATION.ROTATION_0,
				LabelCommand.FONTMUL.MUL_1, LabelCommand.FONTMUL.MUL_1, command);
		
		// 绘制图片
		// Bitmap b = BitmapFactory.decodeResource(getResources(),
		// R.drawable.product_000001);
		// tsc.addBitmap(20, 50, LabelCommand.BITMAP_MODE.OVERWRITE, b.getWidth(), b);

		// 绘制二维码
		tsc.addQRCode(20, 250, LabelCommand.EEC.LEVEL_L, 5, LabelCommand.ROTATION.ROTATION_0, " www.risetek.com");

		// 绘制条形码
		tsc.add1DBarcode(160, 250, LabelCommand.BARCODETYPE.CODE128, 100, LabelCommand.READABEL.EANBEL,
				LabelCommand.ROTATION.ROTATION_0, "wangyuchun");

		tsc.addText(0, 0, LabelCommand.FONTTYPE.SIMPLIFIED_CHINESE, LabelCommand.ROTATION.ROTATION_0,
				LabelCommand.FONTMUL.MUL_1, LabelCommand.FONTMUL.MUL_1, "---------------------");

		tsc.addText(0, 500, LabelCommand.FONTTYPE.SIMPLIFIED_CHINESE, LabelCommand.ROTATION.ROTATION_0,
				LabelCommand.FONTMUL.MUL_1, LabelCommand.FONTMUL.MUL_1, "=======================");

		tsc.addPrint(1, 1); // 打印标签
		// tsc.addSound(2, 100); // 打印标签后 蜂鸣器响

		// tsc.addCashdrwer(LabelCommand.FOOT.F5, 255, 255);

		Vector<Byte> datas = tsc.getCommand(); // 发送数据
		byte[] bytes = LabelUtils.ByteTo_byte(datas);
		return bytes;
	}

	public static final String EXIT_COMMAND = "exit";
	
	public static void main(String[] args) throws IOException {
		
		MessageQueue messageQueue = new MessageQueue();
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Enter some text, or '" + EXIT_COMMAND + "' to quit");

		while (true) {

			System.out.print("> ");
			String input = br.readLine();
			System.out.println(input);

			if (input.length() == EXIT_COMMAND.length() && input.toLowerCase().equals(EXIT_COMMAND)) {
				System.out.println("Exiting.");
				return;
			}

			System.out.println("...response goes here...");

			byte[] bytes = createLabel(input);
			for (int loop = 0; loop < bytes.length; loop++) {
				char a = (char) bytes[loop];
				System.out.print(a);
			}
			messageQueue.sendDatas(bytes);
		}
	}
}
