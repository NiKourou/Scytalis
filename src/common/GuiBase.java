package common;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

/**
 * Class for storing common methods.
 * 
 * @author NK
 * @since 2020-10-20
 */
public class GuiBase {

	private static final String IS_ENCRYPTED_PATTERN = "^([A-Za-z0-9+/]{4})*([A-Za-z0-9+/]{4}|[A-Za-z0-9+/]{3}=|[A-Za-z0-9+/]{2}==)$";

	/**
	 * Creates a MenuItem for MenuBar.
	 * 
	 * @param menu
	 *             {@link Menu}
	 * @param text
	 *             {@link String}
	 */
	protected static void createMenuItem(Menu menu, String text, int style) {
		MenuItem menuItem = new MenuItem(menu, style);
		menuItem.setText(text);
	}

	/**
	 * Creates a ToolItem for the ToolBar.
	 * 
	 * @param toolbar
	 *                  {@link ToolBar}
	 * @param text
	 *                  {@link String}
	 * @param imagePath
	 *                  {@link String}
	 */
	protected ToolItem createToolItem(Shell shell, ToolBar toolbar, String toolTip, String imagePath) {
		ToolItem toolItem = new ToolItem(toolbar, SWT.PUSH);
		toolItem.setToolTipText(toolTip);
		toolItem.setImage(new Image(shell.getDisplay(), imagePath));
		return toolItem;
	}

	/**
	 * Creates a toolItem as a vertical separator line for a toolBar.
	 * 
	 * @param toolbar
	 */
	protected void createSeparatorItem(ToolBar toolbar) {
		new ToolItem(toolbar, SWT.SEPARATOR);
	}

	/**
	 * Checks if a text is encrypted (multiple of 4 and all characters in text
	 * are base64 characters) or not.
	 * 
	 * @param text
	 *             {@link String}
	 * @return {@link Boolean}
	 */
	public static boolean checkIfEncrypted(String text) {
		String encryptedPattern = IS_ENCRYPTED_PATTERN;
		Pattern pattern = Pattern.compile(encryptedPattern);
		Matcher matcher = pattern.matcher(text);
		return matcher.find() ? true : false;
	}

	/**
	 * Creates a label without text, for placing an icon.
	 * 
	 * @param parent
	 *               {@link Composite}
	 * @param style
	 *               int
	 * @param icon
	 *               {@link String}
	 * @return {@link Label}
	 */
	public Label createLabel(Composite parent, int style, String icon) {
		return this.createLabel(parent, style, "", icon);
	}

	/**
	 * Creates a label.
	 * 
	 * @param parent
	 *               {@link Composite}
	 * @param style
	 *               int
	 * @param text
	 *               {@link String}
	 * @param icon
	 *               {@link String}
	 * @return {@link Label}
	 */
	public Label createLabel(Composite parent, int style, String text, String icon) {
		Label label = new Label(parent, style);
		label.setText(text);
		label.setImage(new Image(parent.getDisplay(), icon));
		return label;
	}

	/**
	 * Creates a label.
	 * 
	 * @param parent
	 *               {@link Composite}
	 * @param style
	 *               int
	 * @return {@link Label}
	 */
	public Label createLabel(Composite parent) {
		Label label = new Label(parent, SWT.NONE);
		return label;
	}
}