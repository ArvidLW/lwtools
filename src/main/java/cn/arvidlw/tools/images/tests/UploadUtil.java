package cn.arvidlw.tools.images.tests;

import java.awt.Image;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Date;
import javax.imageio.ImageIO;

public class UploadUtil {
	private static String gifdir;
	private static String targetPath;

	/**
	 * 构建目录
	 * 
	 * @param pathdir
	 *            目录的全路径
	 * @throws Exception
	 */
	public static void createDir(String pathdir) throws Exception {
		try {
			File dir = new File(pathdir);
			if (!dir.exists()) {
				dir.mkdirs();
			}
		} catch (Exception e) {
			System.err.println(new Date() + ":" + e.getLocalizedMessage());
		}
	}

	/**
	 * 上传文件*(一般上传)
	 * @param uploadfile  上传文件流
	 * @param targetpath 上传后的文件全路径名
	 * @throws Exception
	 */
	public static void upload(File uploadfile, String targetpath)
			throws Exception {
		try {
			FileInputStream fin = new FileInputStream(uploadfile);
			FileOutputStream fout = new FileOutputStream(targetpath);
			byte[] buf = new byte[20480];
			int bufsize = 0;
			while ((bufsize = fin.read(buf, 0, buf.length)) != -1) {
				fout.write(buf, 0, bufsize);
			}
			fin.close();
			fout.close();

		} catch (Exception e) {
			System.err.println(new Date() + ":" + e.getLocalizedMessage());
		}
	}

	/**
	 * 
	 * @param tempdir
	 * @param fileName
	 * @param in
	 * @return
	 * @throws Exception
	 */
	public static File upload(String tempdir, String fileName, InputStream in)
			throws Exception {
		File file = new File(tempdir + "/" + fileName);
		try {
			FileOutputStream fout = new FileOutputStream(file);
			byte[] buf = new byte[20480];
			int bufsize = 0;
			while ((bufsize = in.read(buf, 0, buf.length)) != -1) {
				fout.write(buf, 0, bufsize);
			}
			in.close();
			fout.close();

		} catch (Exception e) {
			System.err.println(new Date() + ":" + e.getLocalizedMessage());
		}
		return file;
	}

	/**
	 * 获得文件后缀
	 * 
	 * @param filename
	 * @return
	 */
	public static String getExt(String filename) {
		return filename.substring(filename.lastIndexOf('.') + 1).toLowerCase();
	}

	/**
	 * 压缩处理上传图片
	 * 
	 * @param inputDir       输入图路径
	 * @param outputDir      输出图路径
	 * @param inputFileName  输入图文件名
	 * @param outputFileName 输出图文件名
	 * @param width          设置图片长宽
	 * @param height
	 * @param gp             是否是等比缩放 标记
	 */
	public static boolean compress(String inputDir, String outputDir,
			String inputFileName, String outputFileName, int width, int height,
			boolean gp) {
		if ("gif".equalsIgnoreCase(UploadUtil.getExt(inputFileName))) {
			String pic[] = ImageSpek.splitGif(inputDir + "/" + inputFileName,
					gifdir);
			for (int i = 0; i < pic.length; i++) {
				ImageSpek.compressPic(gifdir, gifdir, i + ".jpg", +i + ".jpg",
						width, height, true);
				pic[i] = gifdir + "\\" + i + ".jpg";
			}
			FileOutputStream ou = null;

			ImageSpek.jpgToGif(pic, targetPath + "/" + outputFileName);
			ImageSpek.delAllFile(gifdir);
		} else {
			ImageSpek.compressPic(inputDir, outputDir, inputFileName,
					outputFileName, width, height, gp);
		}

		return true;
	}

	/**
	 * 图片上传（等比例压缩）
	 * 
	 * @param tempFile
	 *            上传的图片路径
	 * @param dir
	 *            应用的根目录
	 * @param fileName
	 *            图片保存的文件名
	 * @param targetDir
	 *            保存目标目录
	 * @param newWidth
	 * @param newHeight
	 * @return
	 */
	public static boolean uploadZip(String tempFile, String dir,
			String fileName, String targetDir, int newWidth, int newHeight) {
		boolean issuccee = false;
		File file = new File(tempFile);
		if (!file.exists()) {// 文件是否上传成功
			return issuccee;
		}
		targetPath = targetDir;
		// 上传的文件目标全路径名
		String filepath = targetDir + "/" + fileName;
		String xiao = targetDir + "/xiao_" + fileName;
		gifdir = dir + "upload/images/temp/gifPhoto";
		try {
			// 构建目录结构
			UploadUtil.createDir(targetDir);
			UploadUtil.createDir(gifdir);
			Image img = ImageIO.read(file);
			int width = img.getWidth(null);
			int height = img.getHeight(null);
			if (width > 510) {
				issuccee = UploadUtil.compress(file.getParent(), targetDir,
						file.getName(), fileName, 510, 768, true);
			} else {
				UploadUtil.upload(file, filepath);
			}
			if (width <= newWidth && height <= newHeight) {
				UploadUtil.upload(file, xiao);
			} else {
				issuccee = UploadUtil.compress(file.getParent(), targetDir,
						file.getName(), "xiao_" + fileName, newWidth,
						newHeight, true);
			}
			file.delete();
			issuccee = true;
		} catch (Exception e) {
			e.printStackTrace();
			return issuccee;
		}
		return issuccee;
	}
}
