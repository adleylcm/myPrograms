package lzl.play.change.files.name;

import java.io.File;
import java.io.FileFilter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChangeFilesName {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ChangeFilesName cfn = new ChangeFilesName();
		String namePrefix = "We Get Married-";
		String patterns = ".+E[0-9]{1,2}.+";

		// String namePrefix = "士兵突击-";
//		 String namePrefix = "Special Arms-";
		// String patterns = ".*[0-9]{1,2}.*";
		int count = cfn.changeOneFolderFilesName(
				"/Users/liuzeli/Movies/We Get Married/", namePrefix, patterns);
//		int count = cfn.changeOneFolderFilesName(
//				"/Users/liuzeli/Movies/Special Arms/", namePrefix, patterns);
		System.out.println(count);
	}

	class KeyWordsFileFilter implements FileFilter {
		private String keyWords;

		public KeyWordsFileFilter(String keyWords) {
			super();
			this.keyWords = keyWords;
		}

		@Override
		public boolean accept(File fileName) {
			Pattern p = Pattern.compile(keyWords);
			Matcher m = p.matcher(fileName.getName());

			if (fileName.isDirectory())
				return false;
			else
				return m.matches();
		}

	}

	public int changeOneFolderFilesName(String folderPath, String namePrefix,
			String patterns) {
		int count = 0;
		File path = new File(folderPath);
		Pattern p = Pattern.compile("E[0-9]{1,2}");
		// Pattern p = Pattern.compile("[0-9]{1,2}");
		File[] list = path.listFiles(new KeyWordsFileFilter(patterns));
		for (int i = 0; i < list.length; i++) {
			Matcher m = p.matcher(list[i].getName());
			if (m.find()) {
				StringBuilder sb = new StringBuilder();
				int pathEndIndex = list[i].getAbsolutePath().lastIndexOf("/");
				int fileExtension = list[i].getAbsolutePath().lastIndexOf(".");
				sb.append(list[i].getPath().substring(0, pathEndIndex + 1));
				sb.append(namePrefix);
				System.out.println(namePrefix);
				sb.append(m.group().substring(1));
				// sb.append(m.group());
				// System.out.println(m.group());
				sb.append(list[i].getPath().substring(fileExtension));
				System.out.println(sb.toString());

				list[i].renameTo(new File(sb.toString()));
				count++;
			}
		}
		return count;
	}
}
