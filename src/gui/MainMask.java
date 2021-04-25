package gui;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.LineStyleEvent;
import org.eclipse.swt.custom.LineStyleListener;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

import common.GuiBase;
import common.IGuiImages;
import common.IGuiTexts;
import tools.EnDecryption;
import tools.IOFile;

/**
 * Class representing the main mask of Scytalis.
 * 
 * @author NK
 * @since 2020-10-11
 */
public class MainMask extends GuiBase implements IGuiTexts, IGuiImages {

	private Display display;
	private Shell shell;
	private Composite composite;
	private Composite toolbarComposite;
	private Composite topComposite;
	private Composite searchComposite;
	private Composite textComposite;
	private Composite infoComposite;

	private ToolItem encryptTool;
	private ToolItem chooseFileTool;
	private ToolItem openHRTextTool;

	private Label infoArea;

	private Text searchText;
	private String selectedFilePath;
	private StyledText styledText;

	private static final String KEY = "PBKDF2WithHmacSH";

	/**
	 * Constructor.
	 */
	public MainMask() {
		display = Display.getDefault();
		shell = new Shell(display);

		createShell();
		create();

		shell.open();
		readAndDispatch();
	}

	/**
	 * Create the Shell.
	 */
	private void createShell() {
		shell.setImage(new Image(display, ICON_LOGO));
		shell.setText(PROGRAM_NAME);
		shell.setMinimumSize(500, 600);
		shell.setLocation(600, 200);
		shell.setLayout(new GridLayout());
		shell.pack();
	}

	/**
	 * Create elements of mask.
	 */
	private void create() {
//		createMenu();
		createComposites();
	}

	/**
	 * Create all composites.
	 */
	private void createComposites() {
		createComposite();
		topComposite();
		createTextComposite();
		createInfoAreaComposite();
	}

	/**W
	 * Create the menu bar.
	 */
	@SuppressWarnings("unused")
	private void createMenu() {
		Menu menu = new Menu(shell, SWT.BAR);

		MenuItem fileMenuHeader = new MenuItem(menu, SWT.CASCADE);
		fileMenuHeader.setText("Configurations");

		Menu fileMenu = new Menu(shell, SWT.DROP_DOWN);
		fileMenuHeader.setMenu(fileMenu);

		createMenuItem(fileMenu, "Option 1", SWT.PUSH);
		createMenuItem(fileMenu, "Option 2", SWT.PUSH);

		MenuItem helpMenuHeader = new MenuItem(menu, SWT.CASCADE);
		helpMenuHeader.setText("Help");

		Menu helpMenu = new Menu(shell, SWT.DROP_DOWN);
		helpMenuHeader.setMenu(helpMenu);

		createMenuItem(helpMenu, "Get Help", SWT.PUSH);

		shell.setMenuBar(menu);
	}

	/**
	 * Create the main composite.
	 */
	private void createComposite() {
		composite = new Composite(shell, SWT.NONE);

		composite.setLayout(new GridLayout(1, false));
		GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
		composite.setLayoutData(data);
	}

	/**
	 * This composite includes the Info-Area and the search field.
	 */
	private void topComposite() {
		topComposite = new Composite(composite, SWT.NONE);
		GridLayout layout = new GridLayout(2, false);
		layout.marginBottom = -10;
		layout.marginTop = -10;
		layout.marginLeft = -5;

		topComposite.setLayout(layout);

		topComposite.setLayoutData(new GridData(SWT.FILL, SWT.NONE, true, false));

		createToolbarComposite();
		createSearchComposite();
	}

	/**
	 * Create the toolbar's composite.
	 */
	private void createToolbarComposite() {
		toolbarComposite = new Composite(topComposite, SWT.NONE);

		// Layout & data
		createToolbarLayout();
		createToolbarData();

		createToolbar();
	}

	/**
	 * Create grid layout for the toolBar.
	 */
	private void createToolbarLayout() {
		GridLayout layout = new GridLayout(5, true);
		toolbarComposite.setLayout(layout);
	}

	/**
	 * Create grid data for the toolBar.
	 */
	private void createToolbarData() {
		GridData data = new GridData(SWT.FILL, SWT.NONE, true, false);
		toolbarComposite.setLayoutData(data);
	}

	/**
	 * Create the search's composite.
	 */
	private void createSearchComposite() {
		searchComposite = new Composite(topComposite, SWT.NONE);

		GridLayout layout = new GridLayout(2, false);
		layout.marginRight = 287;
		searchComposite.setLayout(layout);

		GridData data = new GridData(SWT.FILL, SWT.NONE, true, false);
		data.horizontalAlignment = SWT.RIGHT;
		data.verticalAlignment = SWT.CENTER;
		searchComposite.setLayoutData(data);

		createSearchArea();
	}

	/**
	 * Create the area for searching through text.
	 */
	private void createSearchArea() {
		searchText = new Text(searchComposite, SWT.BORDER);
		GridData data = new GridData();
		data.widthHint = 150;
		searchText.setLayoutData(data);

		// Creates the search icon
		createLabel(searchComposite, SWT.NONE, ICON_SEARCH);

		addEnterKeyListener();
	}

	/**
	 * Adds a key listener: If user clicks return/enter to search for a keyword
	 * in text.
	 */
	private void addEnterKeyListener() {
		searchText.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				enterKeyFunctionality(e);
			}
		});
	}

	/**
	 * Enter key functionality.
	 * 
	 * @param e
	 *          {@link KeyEvent}
	 */
	private void enterKeyFunctionality(KeyEvent e) {
		// If user clicks return/enter (from keypad also)
		if (e.keyCode == SWT.CR || e.keyCode == SWT.KEYPAD_CR) {

			// Reset text highlights if new keyword search
			styledText.setStyleRanges(new StyleRange[0]);

			// Core search functionality
			searchFunctionality();
		}
	}

	/**
	 * Core search functionality.
	 */
	private void searchFunctionality() {
		addLineStyleListener(searchText.getText());
		styledText.redraw();
	}

	/**
	 * Adds a line style listener, for highlighting keywords in text.
	 * 
	 * @param keyword
	 *                {@link String}
	 */
	private void addLineStyleListener(String keyword) {
		styledText.addLineStyleListener(new LineStyleListener() {
			@Override
			public void lineGetStyle(LineStyleEvent e) {
				lineGetStyleFunctionality(e, keyword);
			}
		});
	}

	/**
	 * The core functionality of line style listener.
	 * 
	 * @param e
	 *                {@link LineStyleEvent}
	 * @param keyword
	 *                {@link String}
	 */
	private void lineGetStyleFunctionality(LineStyleEvent e, String keyword) {
		// No style, if no keyword
		if (keyword == null || keyword.length() == 0) {
			e.styles = new StyleRange[0];
			return;
		}
		// Each line separate
		String line = e.lineText;
		int cursor = -1;
		LinkedList<StyleRange> list = new LinkedList<>();
		while ((cursor = line.indexOf(keyword, cursor + 1)) >= 0) {
				// Adds in list, the style range of the highlighted keyword
				list.add(getHighlightStyle(e.lineOffset + cursor, keyword.length()));				
		}
		e.styles = list.toArray(new StyleRange[list.size()]);
	}

	/**
	 * Returns the style of highlighting a word in text if search was
	 * successful.
	 * 
	 * @param startOffset
	 *                    int
	 * @param length
	 *                    int
	 * @return {@link StyleRange}
	 */
	private StyleRange getHighlightStyle(int startOffset, int length) {
		StyleRange styleRange = new StyleRange();
		styleRange.start = startOffset;
		styleRange.length = length;
		styleRange.background = new Color(display, 150, 230, 141);
		return styleRange;
	}

	/**
	 * Create the text's composite.
	 */
	private void createTextComposite() {
		textComposite = new Composite(composite, SWT.NONE);

		GridLayout layout = new GridLayout(1, false);
		textComposite.setLayout(layout);

		GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
		data.heightHint = 400;
		textComposite.setLayoutData(data);

		createTextArea();
	}

	/**
	 * Create the information area's composite.
	 */
	private void createInfoAreaComposite() {
		infoComposite = new Composite(composite, SWT.NONE);
		infoComposite.setLayout(new GridLayout());

		GridData data = new GridData(SWT.FILL, SWT.CENTER, true, false);
		infoComposite.setLayoutData(data);

		createInfoArea();
	}

	/**
	 * Create the area where informations for user will appear.
	 */
	private void createInfoArea() {
		infoArea = createLabel(infoComposite);
	}

	/**
	 * Create the ToolBar.
	 */
	private void createToolbar() {
		ToolBar toolbar = new ToolBar(toolbarComposite, SWT.FLAT | SWT.WRAP | SWT.LEFT);

		// ToolItem: Open a human readable text
		openHRTextTool = createToolItem(shell, toolbar, TEXT_HUMAN_READABLE_TEXT, ICON_PLAIN_TEXT);
		addListenerOpenHRText();

		// ToolItem: Choose file
		chooseFileTool = createToolItem(shell, toolbar, TEXT_OPEN_ENCRYPTED_FILE, ICON_OPEN_FILE);
		addListenerChooseFile();

		createSeparatorItem(toolbar);

		// ToolItem: Encrypt & Save text
		encryptTool = createToolItem(shell, toolbar, TEXT_ENCRYPT, ICON_ENCRYPT);
		addListenerEncryptAndSave();
	}

	/**
	 * Add listener for opening a human readable text (plain).
	 */
	private void addListenerOpenHRText() {
		openHRTextTool.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				openDialogHumanReadable();
			}
		});
	}

	/**
	 * Add listener for encrypting a text.
	 */
	private void addListenerEncryptAndSave() {
		encryptTool.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				saveToFile();
			}
		});
	}

	/**
	 * Save the text (as encrypted) in a file.
	 */
	private void saveToFile() {
		EnDecryption enDecryption = new EnDecryption();
		IOFile file = new IOFile();
		String textEncryptedToSave = null;
		String textHR = styledText.getText();
		try {
			if(textHR != null && textHR.length() > 0) {
				textEncryptedToSave = enDecryption.encrypt(textHR, KEY);	
			}
			else {				
				updateInfoArea(TEXT_NO_TEXT_TO_ENCRYPT);
			}
		} catch (Exception e) {
			updateInfoArea("Error while encrypting the text.");
		}

		try {
			// Path to file
			if (textEncryptedToSave != null && textEncryptedToSave.length() != 0) {
				byte[] outputBytes = textEncryptedToSave.getBytes();
				file.write(outputBytes, selectedFilePath);
				styledText.setText("");
				updateInfoArea("Saved successfully.");
			}
		} catch (IOException e) {
			updateInfoArea("Error while encrypting the text.");
		}
	}

	/**
	 * Add listener for choosing a file.
	 */
	private void addListenerChooseFile() {
		chooseFileTool.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				openDialogDecrypt();
			}
		});
	}

	/**
	 * Opens the dialog window to choose a file with <b>human readable text</b>
	 * and open it in the main mask as a plain text.
	 */
	private void openDialogHumanReadable() {
		selectedFilePath = createDialog(DIALOG_OPEN_HR);

		IOFile iofile = new IOFile();
		if (selectedFilePath != null && selectedFilePath.length() != 0) {
			File file = new File(selectedFilePath);
			byte[] bytes = iofile.read(file);
			String textHR = new String(bytes);
			if (!checkIfEncrypted(textHR)) {
				styledText.setText(textHR);
				updateInfoArea(INFO_FILE_LOADED);
			} else {
				updateInfoArea(INFO_UNABLE_LOAD_FILE);
			}
		}
	}

	/**
	 * Opens the dialog window to choose an <b>encrypted file</b> and open it in
	 * the main mask as a plain text.
	 */
	private void openDialogDecrypt() {
		selectedFilePath = createDialog(DIALOG_OPEN_ENCRYPTED);

		try {
			EnDecryption nd = new EnDecryption();
			IOFile iofile = new IOFile();
			if (selectedFilePath != null && selectedFilePath.length() != 0) {
				File file = new File(selectedFilePath);
				byte[] bytes = iofile.read(file);
				String bytesToDecrypt = new String(bytes);
				String textHR = nd.decrypt(bytesToDecrypt, KEY);
				styledText.setText(textHR);
				updateInfoArea(INFO_DECRYPTED);
			}
		} catch (Exception e) {
			updateInfoArea("Cannot perform decryption. This file contains human readable text.");
			return;
		}
	}

	/**
	 * Create a dialog to choose a file, inclusive all of its settings.
	 * 
	 * @param title
	 *              {@link String}
	 * @return {@link String}
	 */
	private String createDialog(String title) {
		FileDialog dialog = new FileDialog(shell, SWT.OPEN);
		dialog.setText(title);
		dialog.setFilterPath("C:/");
		String[] filterExt = { "*.txt", "*.doc", ".rtf", ".odt"/* , "*.*" */ };
		dialog.setFilterExtensions(filterExt);
		return dialog.open();
	}

	/**
	 * Create the text area where the text appears.
	 */
	private void createTextArea() {
		styledText = new StyledText(textComposite, SWT.BORDER | SWT.V_SCROLL | SWT.WRAP);
		GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
		styledText.setLayoutData(data);
	}

	/**
	 * @return {@link Label}
	 */
	public Label getInfoArea() {
		return infoArea;
	}

	/**
	 * Updates the info area with a message for the user.
	 * 
	 * @param text
	 *             {@link String}
	 */
	private void updateInfoArea(String text) {
		getInfoArea().setText(text);
		getInfoArea().getParent().layout();
	}

	@SuppressWarnings("unused")
	private void dispose() {
		// dispose the elements
	}

	/**
	 * Waiting for actions.
	 */
	private void readAndDispatch() {
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
//		icon.dispose();
		display.dispose();
	}
}