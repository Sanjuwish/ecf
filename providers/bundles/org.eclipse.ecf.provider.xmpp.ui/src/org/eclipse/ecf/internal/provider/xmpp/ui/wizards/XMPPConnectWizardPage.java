/****************************************************************************
 * Copyright (c) 2007 Remy Suen, Composent Inc., and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Remy Suen <remy.suen@gmail.com> - initial API and implementation
 *****************************************************************************/
package org.eclipse.ecf.internal.provider.xmpp.ui.wizards;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.ecf.internal.provider.xmpp.ui.Activator;
import org.eclipse.ecf.internal.provider.xmpp.ui.Messages;
import org.eclipse.ecf.ui.SharedImages;
import org.eclipse.ecf.ui.util.PasswordCacheHelper;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class XMPPConnectWizardPage extends WizardPage {

	Combo connectText;

	Text passwordText;

	String usernameAtHost;

	XMPPConnectWizardPage() {
		super(""); //$NON-NLS-1$
		setTitle(Messages.XMPPConnectWizardPage_WIZARD_TITLE);
		setDescription(Messages.XMPPConnectWizardPage_WIZARD_DESCRIPTION);
		setPageComplete(false);
		setImageDescriptor(SharedImages.getImageDescriptor(SharedImages.IMG_CHAT_WIZARD));
	}

	XMPPConnectWizardPage(String usernameAtHost) {
		this();
		this.usernameAtHost = usernameAtHost;
	}

	private void verify() {
		String text = connectText.getText();
		if (text.equals("")) { //$NON-NLS-1$
			updateStatus(Messages.XMPPConnectWizardPage_WIZARD_STATUS);
		} else if (text.indexOf('@') == -1) {
			updateStatus(Messages.XMPPConnectWizardPage_WIZARD_STATUS_INCOMPLETE);
		} else {
			updateStatus(null);
			restorePassword(text);
		}
	}
	
	public void createControl(Composite parent) {
		parent.setLayout(new GridLayout());
		GridData fillData = new GridData(SWT.FILL, SWT.CENTER, true, false);
		GridData endData = new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1);

		Label label = new Label(parent, SWT.LEFT);
		label.setText(Messages.XMPPConnectWizardPage_LABEL_USERID);

		connectText = new Combo(parent, SWT.SINGLE | SWT.BORDER | SWT.DROP_DOWN);
		connectText.setLayoutData(fillData);
		connectText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				verify();
			}
		});
		connectText.addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent e) {
				verify();
			}
			public void widgetSelected(SelectionEvent e) {
				verify();
			}});
		
		label = new Label(parent, SWT.RIGHT);
		label.setText(Messages.XMPPConnectWizardPage_USERID_TEMPLATE);
		label.setLayoutData(endData);

		label = new Label(parent, SWT.LEFT);
		label.setText(Messages.XMPPConnectWizardPage_WIZARD_PASSWORD);
		passwordText = new Text(parent, SWT.SINGLE | SWT.PASSWORD | SWT.BORDER);
		passwordText.setLayoutData(fillData);

		restoreCombo();
		
		if (usernameAtHost != null) {
			connectText.setText(usernameAtHost);
			restorePassword(usernameAtHost);
			passwordText.setFocus();
		}
		setControl(parent);
	}

	protected void restorePassword(String username) {
		PasswordCacheHelper pwStorage = new PasswordCacheHelper(username);
		String pw = pwStorage.retrievePassword();
		if (pw != null) {
			passwordText.setText(pw);
		}
	}
	
	String getConnectID() {
		return connectText.getText();
	}

	String getPassword() {
		return passwordText.getText();
	}

	protected void updateStatus(String message) {
		setErrorMessage(message);
		setPageComplete(message == null);
	}

	private static final String PAGE_SETTINGS = XMPPConnectWizardPage.class
			.getName();
	private static final int MAX_COMBO_VALUES = 40;
	private static final String COMBO_TEXT_KEY = "connectTextValue";
	private static final String COMBO_BOX_ITEMS_KEY = "comboValues";

	protected void saveComboText() {
		IDialogSettings pageSettings = getPageSettings();
		if (pageSettings != null)
			pageSettings.put(COMBO_TEXT_KEY, connectText.getText());
	}

	protected void saveComboItems() {
		IDialogSettings pageSettings = getPageSettings();
		if (pageSettings != null) {
			String connectTextValue = connectText.getText();
			List rawItems = Arrays.asList(connectText.getItems());
			// If existing text item is not in combo box then add it
			List items = new ArrayList();
			if (!rawItems.contains(connectTextValue)) items.add(connectTextValue);
			items.addAll(rawItems);
			int itemsToSaveLength = items.size();
			if (itemsToSaveLength > MAX_COMBO_VALUES)
				itemsToSaveLength = MAX_COMBO_VALUES;
			String[] itemsToSave = new String[itemsToSaveLength];
			System.arraycopy(items.toArray(new String[] {}), 0, itemsToSave, 0,
					itemsToSaveLength);
			pageSettings.put(COMBO_BOX_ITEMS_KEY, itemsToSave);
		}
	}

    public IDialogSettings getDialogSettings() {
        return Activator.getDefault().getDialogSettings();
    }

	private IDialogSettings getPageSettings() {
		IDialogSettings pageSettings = null;
		IDialogSettings dialogSettings = this.getDialogSettings();
		if (dialogSettings != null) {
			pageSettings = dialogSettings.getSection(PAGE_SETTINGS);
			if (pageSettings == null)
				pageSettings = dialogSettings.addNewSection(PAGE_SETTINGS);
			return pageSettings;
		}
		return null;
	}

	protected void restoreCombo() {
		IDialogSettings pageSettings = getPageSettings();
		if (pageSettings != null) {
			String [] items = pageSettings.getArray(COMBO_BOX_ITEMS_KEY);
			if (items != null) connectText.setItems(items);
			String text = pageSettings.get(COMBO_TEXT_KEY);
			if (text != null) connectText.setText(text);
		}
	}

}
