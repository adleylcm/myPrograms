package com.triangle23.calculate.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class CalculateValueOfCD {
	private double[] numbers1 = null;
	private double[] numbers2 = null;

	private void readData(String inputPath, int N) {

		BufferedReader readIn = null;
		try {
			readIn = new BufferedReader(new FileReader(new File(inputPath)));
			numbers1 = new double[N];
			numbers2 = new double[N];
			for (int i = 0; i < N; ++i) {
				String[] twoNums = readIn.readLine().split(",");
				numbers1[i] = Double.parseDouble(twoNums[0]);
				numbers2[i] = Double.parseDouble(twoNums[1]);
			}
			// try {
			// readIn = new BufferedReader(new FileReader(new File(inputPath)));
			// numbers1 = new double[N];
			// numbers2 = new double[N];
			// String[] lineOneData = readIn.readLine().split(",");
			// String[] lineTwoData = readIn.readLine().split(",");
			// for (int i = 0; i < N; i++) {
			// numbers1[i] = Double.parseDouble(lineOneData[i]);
			// numbers2[i] = Double.parseDouble(lineTwoData[i]);
			// }

		} catch (FileNotFoundException e) {
			System.out
					.println("The file path does not exit, please input the right one!");
		} catch (NumberFormatException e) {
			System.out
					.println("The file's content isn't right, please check your data format!");
		} catch (IOException e) {
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
		String path = null;
		int N = 0;

		if (2 != args.length) {
			System.out
					.println("Please input the source data path or data count");
			System.exit(0);
		} else {
			path = args[0];
			N = Integer.parseInt(args[1]);
		}

		CalculateValueOfCD so = new CalculateValueOfCD();
		so.readData(path, N);

		double result = 0.0;
		double tempResult = 0.0;

		for (int i = 0; i < N; i++) {
			tempResult = Math.pow((so.numbers1[i] - so.numbers2[i])
					/ (so.numbers1[i] + so.numbers2[i]), 2);
			// System.out.println((i + 1) + ": " + tempResult);
			result += tempResult;
		}
		result = Math.sqrt(result / (N / 1.0));
		System.out.println("CD: " + result);
	}
}
