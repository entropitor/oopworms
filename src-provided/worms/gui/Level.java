package worms.gui;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Arrays;

import javax.imageio.ImageIO;

public class Level {

	private static final String LEVEL_FILE_EXTENSION = ".lvl";
	private static final String LEVELS_DIRECTORY = "levels";

	private static class LoadException extends RuntimeException {

		private static final long serialVersionUID = 1L;

		public LoadException(String message, Exception cause) {
			super(message, cause);
		}

	}

	public static Level[] getAvailableLevels() {
		File[] files = getLevelFiles();
		Level[] levels = new Level[files.length];
		for (int i = 0; i < files.length; i++) {
			levels[i] = new Level(files[i]);
		}
		return levels;
	}

	private static File[] getLevelFiles() {
		File levelsDir = new File(LEVELS_DIRECTORY);
		if (!levelsDir.exists() || !levelsDir.isDirectory()) {
			throw new RuntimeException("levels directory not found");
		}
		File[] levelFiles = levelsDir.listFiles(new FilenameFilter() {

			@Override
			public boolean accept(File dir, String name) {
				return name.endsWith(LEVEL_FILE_EXTENSION);
			}
		});
		Arrays.sort(levelFiles);
		return levelFiles;
	}

	private final File file;
	private BufferedImage mapImage;

	private double scale;

	public Level(File file) {
		this.file = file;
	}

	public String getName() {
		return file.getName().substring(0, file.getName().length() - 4);
	}

	public void load() {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			readFile(reader);
			reader.close();
		} catch (Exception e) {
			throw new LoadException("Could not load world from file "
					+ file.getAbsolutePath(), e);
		}
	}

	protected void readFile(BufferedReader reader) throws IOException {
		this.mapImage = ImageIO.read(new File(file.getParentFile(),
				readAsKeyVal(reader, "map")));
		try {
			double height = Double.parseDouble(readAsKeyVal(reader, "height"));
			this.scale = height / mapImage.getHeight();
		} catch (IllegalArgumentException e) {
			double width = Double.parseDouble(readAsKeyVal(reader, "width"));
			this.scale = width / mapImage.getWidth();
		}
	}

	protected String readAsKeyVal(BufferedReader reader, String expectedKey)
			throws IOException {
		String line = reader.readLine();
		while (line.isEmpty() || line.indexOf("#") == 0) {
			line = reader.readLine();
		}
		if (line.indexOf("#") > 0) {
			line = line.substring(0, line.indexOf("#")).trim();
		}
		int split = line.indexOf(":");
		String key = line.substring(0, split);
		String value = line.substring(split + 1);
		if (!expectedKey.equals(key)) {
			throw new IllegalArgumentException("Expected key " + expectedKey
					+ ", got " + key);
		}
		return value;
	}

	public BufferedImage getMapImage() {
		return mapImage;
	}

	public int getMapHeight() {
		return mapImage.getHeight();
	}

	public int getMapWidth() {
		return mapImage.getWidth();
	}

	/**
	 * Scale of the world (in worm-meter per map pixel)
	 * 
	 * @return
	 */
	public double getScale() {
		return scale;
	}

	public double getWorldWidth() {
		return scale * mapImage.getWidth();
	}

	public double getWorldHeight() {
		return scale * mapImage.getHeight();
	}

	public boolean[][] getPassableMap() {
		final boolean[][] result = new boolean[getMapHeight()][getMapWidth()];
		final byte[] bytes = ((DataBufferByte) mapImage.getRaster().getDataBuffer())
				.getData();
		final int w = getMapWidth();
		final int h = getMapHeight();
		for (int row = 0; row < h; row++) {
			final int offset = w * row;
			for (int col = 0; col < w; col++) {
				final byte alpha = bytes[4 * (offset + col)];
				// alpha < 128 ((alpha & 0xf) == 0) => passable
				// alpha >= 128 ((alpha & 0xf) != 0) => impassable
				if (((int) alpha & 0xf0) == 0) {
					result[row][col] = true;
				}
			}
		}
		return result;
	}

	/**
	 * map width / map height
	 */
	public double getMapAspectRatio() {
		return (double) getMapWidth() / getMapHeight();
	}

	/**
	 * world width / world height
	 */
	public double getWorldAspectRatio() {
		return getWorldWidth() / getWorldHeight();
	}
}
