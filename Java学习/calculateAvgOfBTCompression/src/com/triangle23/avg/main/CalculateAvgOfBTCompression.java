package com.triangle23.avg.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class CalculateAvgOfBTCompression {
	// private String results[] = null;
	private String oneLineResult = null;
	private String oneLine = "";

	private void calculateAvg(String inputPath) {
		BufferedReader readIn = null;
		try {
			readIn = new BufferedReader(new FileReader(new File(inputPath)));
			oneLine = readIn.readLine();
			while (null != oneLine) {
				oneLineResult = "";
				String oneLineTemp[] = null;
				oneLineTemp = oneLine.split("=");

				String oneLineScales[] = null;
				oneLineScales = oneLineTemp[1].trim().split("-");
				int scale = Integer.parseInt(oneLineScales[0]);
				// oneLineResult += scale + ":\t";
				// System.out.print(scale + "\t");
				// System.out.println(scale + "\t");

				String[] oneLineData = readIn.readLine().split(" ");
				for (int i = 0; i < oneLineData.length - 3; ++i) {
					oneLineResult += oneLineData[i] + "\t";
				}
				// System.out.println(oneLineResult);
				double sum = 0.0;
				for (int i = oneLineData.length - 3; i < oneLineData.length; ++i) {
					sum += Double.parseDouble(oneLineData[i]);
				}
				oneLineResult += (sum / 3.0) + "\t";
				// System.out.println(oneLineResult);
				System.out.println(sum / 3.0);
				oneLine = readIn.readLine();
			}

		} catch (FileNotFoundException e) {
			System.out
					.println("The file path does not exit, please input the right one!");
		} catch (NumberFormatException e) {
			System.out
					.println("The file's content isn't right, please check your data format!");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (null != readIn) {
				try {
					readIn.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		CalculateAvgOfBTCompression caBT = new CalculateAvgOfBTCompression();
		// caBT.calculateAvg("/Users/liuzeli/Documents/TestData/result_BTcompressed.txt");
		caBT.calculateAvg("/Users/liuzeli/Desktop/result_BTcompressed2.txt");

		System.out.println();
		System.out.println();

		// caBT.calculateAvg("/Users/liuzeli/Documents/TestData/result_BTorigin.txt");
		caBT.calculateAvg("/Users/liuzeli/Desktop/result_BTorigin2.txt");
	}

}
