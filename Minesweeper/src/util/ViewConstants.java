package util;

/**
 * Interface containing constants concerning the view.
 */
public interface ViewConstants {

	/*
	 * As the application height can't be reliably predetermined, and is thus
	 * dynamically calculated, only the width is fixed.
	 */
	public static final int APPLICATION_WIDTH = 500;
	public static final int ACTION_BAR_HEIGHT = 70;

	/* Path constants for image assets. */
	public static final String REFRESH_BUTTON_IMAGE_PATH = "resources/ic_refresh.png";
	public static final String EXPLODED_BOMB_PATH = "resources/bomb-exploded-16.png";
	public static final String BOMB_PATH = "resources/bomb-16.png";
	public static final String FLAG_PATH = "resources/flag-16.png";
	public static final String WRONGLY_FLAGGED_PATH = "resources/wrongly-flagged-16.png";

	public static final int ACTION_BAR_FONT_SIZE = 40;
	public static final String ACTION_BAR_FONT = "Impact";
}
