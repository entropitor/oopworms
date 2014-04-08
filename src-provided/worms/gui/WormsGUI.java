package worms.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;

import worms.gui.menu.MainMenuScreen;
import worms.model.IFacade;

public class WormsGUI {

	private JFrame window;
	private JPanel screenPanel;

	private Screen currentScreen = null;

	private final GUIOptions options;
	private final IFacade facade;

	public WormsGUI(IFacade facade, GUIOptions options) {
		this.facade = facade;
		this.options = options;
	}

	public void switchToScreen(Screen newScreen) {
		if (currentScreen != null) {
			screenPanel.remove(currentScreen.getPanel());
		}
		screenPanel.add(newScreen.getPanel(), BorderLayout.CENTER);
		screenPanel.validate();
		currentScreen = newScreen;
		newScreen.startScreen();
	}

	public void start() {
		try {
			initializeGUI();
			gotoMainMenu();
		} catch (Exception e) {
			e.printStackTrace();
			showError(e.getMessage());
		}
	}

	private void gotoMainMenu() {
		MainMenuScreen menuScreen = new MainMenuScreen(this);
		switchToScreen(menuScreen);
	}

	public void exit() {
		window.dispose();
		System.exit(0);
	}

	private void initializeGUI() {
		GraphicsEnvironment env = GraphicsEnvironment
				.getLocalGraphicsEnvironment();
		if (env.isHeadlessInstance()) {
			System.out.println("Graphics not supported");
			System.exit(0);
		}

		this.window = new JFrame("Worms");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				exit();
			};
		});
		window.setFocusTraversalKeysEnabled(false);

		this.screenPanel = new JPanel();
		screenPanel.setLayout(new BorderLayout());
		screenPanel.setBackground(Color.WHITE);
		window.getContentPane().add(screenPanel);

		window.setFocusable(false);

		GraphicsDevice device = env.getDefaultScreenDevice();
		if (device.isFullScreenSupported() && !options.disableFullScreen) {
			window.setUndecorated(true);
			window.pack();
			device.setFullScreenWindow(window);
		} else {
			window.setUndecorated(false);
			screenPanel.setPreferredSize(new Dimension(
					GUIConstants.DEFAULT_WINDOW_WIDTH,
					GUIConstants.DEFAULT_WINDOW_HEIGHT));
			window.pack();
		}

		window.setVisible(true);
	}

	public void showError(String message) {
		if (message == null) {
			message = "(Unknown error)";
		}
		ErrorScreen errorScreen = new ErrorScreen(this, message);
		switchToScreen(errorScreen);
	}

	public IFacade getFacade() {
		return facade;
	}

	public GUIOptions getOptions() {
		return options;
	}

	public int getWidth() {
		return currentScreen.getScreenWidth();
	}
	
	public int getHeight() {
		return currentScreen.getScreenHeight();
	}

}
