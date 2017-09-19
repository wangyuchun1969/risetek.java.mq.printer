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

		// ��ǩ��ӡ
		LabelCommand tsc = new LabelCommand();
		tsc.addSize(300, 500); // ���ñ�ǩ�ߴ磬����ʵ�ʳߴ�����
		tsc.addGap(2); // ���ñ�ǩ��϶������ʵ�ʳߴ����ã����Ϊ�޼�϶ֽ������Ϊ0
		tsc.addDirection(LabelCommand.DIRECTION.BACKWARD, LabelCommand.MIRROR.NORMAL);// ���ô�ӡ����
		tsc.addReference(0, 0);// ����ԭ������
		tsc.addTear(LabelUtils.ENABLE.ON); // ˺ֽģʽ����

		tsc.addCls();// �����ӡ������
		// ���Ƽ�������
		tsc.addText(20, 20, LabelCommand.FONTTYPE.SIMPLIFIED_CHINESE, LabelCommand.ROTATION.ROTATION_0,
				LabelCommand.FONTMUL.MUL_1, LabelCommand.FONTMUL.MUL_1, "     ��è����");
		tsc.addText(20, 50, LabelCommand.FONTTYPE.SIMPLIFIED_CHINESE, LabelCommand.ROTATION.ROTATION_0,
				LabelCommand.FONTMUL.MUL_1, LabelCommand.FONTMUL.MUL_1, "��Ʒ��ũ��ɽȪ");
		tsc.addText(20, 80, LabelCommand.FONTTYPE.SIMPLIFIED_CHINESE, LabelCommand.ROTATION.ROTATION_0,
				LabelCommand.FONTMUL.MUL_1, LabelCommand.FONTMUL.MUL_1, "�����");
		tsc.addText(20, 110, LabelCommand.FONTTYPE.SIMPLIFIED_CHINESE, LabelCommand.ROTATION.ROTATION_0,
				LabelCommand.FONTMUL.MUL_1, LabelCommand.FONTMUL.MUL_1, "����16.00");
		tsc.addText(20, 140, LabelCommand.FONTTYPE.SIMPLIFIED_CHINESE, LabelCommand.ROTATION.ROTATION_0,
				LabelCommand.FONTMUL.MUL_1, LabelCommand.FONTMUL.MUL_1, "ʱ�䣺2017/05/19 15:00");
		tsc.addText(20, 170, LabelCommand.FONTTYPE.SIMPLIFIED_CHINESE, LabelCommand.ROTATION.ROTATION_0,
				LabelCommand.FONTMUL.MUL_1, LabelCommand.FONTMUL.MUL_1, "�绰��18818181818");

		tsc.addText(20, 200, LabelCommand.FONTTYPE.SIMPLIFIED_CHINESE, LabelCommand.ROTATION.ROTATION_0,
				LabelCommand.FONTMUL.MUL_1, LabelCommand.FONTMUL.MUL_1, command);
		
		// ����ͼƬ
		// Bitmap b = BitmapFactory.decodeResource(getResources(),
		// R.drawable.product_000001);
		// tsc.addBitmap(20, 50, LabelCommand.BITMAP_MODE.OVERWRITE, b.getWidth(), b);

		// ���ƶ�ά��
		tsc.addQRCode(20, 250, LabelCommand.EEC.LEVEL_L, 5, LabelCommand.ROTATION.ROTATION_0, " www.risetek.com");

		// ����������
		tsc.add1DBarcode(160, 250, LabelCommand.BARCODETYPE.CODE128, 100, LabelCommand.READABEL.EANBEL,
				LabelCommand.ROTATION.ROTATION_0, "wangyuchun");

		tsc.addText(0, 0, LabelCommand.FONTTYPE.SIMPLIFIED_CHINESE, LabelCommand.ROTATION.ROTATION_0,
				LabelCommand.FONTMUL.MUL_1, LabelCommand.FONTMUL.MUL_1, "---------------------");

		tsc.addText(0, 500, LabelCommand.FONTTYPE.SIMPLIFIED_CHINESE, LabelCommand.ROTATION.ROTATION_0,
				LabelCommand.FONTMUL.MUL_1, LabelCommand.FONTMUL.MUL_1, "=======================");

		tsc.addPrint(1, 1); // ��ӡ��ǩ
		// tsc.addSound(2, 100); // ��ӡ��ǩ�� ��������

		// tsc.addCashdrwer(LabelCommand.FOOT.F5, 255, 255);

		Vector<Byte> datas = tsc.getCommand(); // ��������
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
