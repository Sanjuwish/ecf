/*******************************************************************************
* Copyright (c) 2009 Composent, Inc. and others. All rights reserved. This
* program and the accompanying materials are made available under the terms of
* the Eclipse Public License v1.0 which accompanies this distribution, and is
* available at http://www.eclipse.org/legal/epl-v10.html
*
* Contributors:
*   Composent, Inc. - initial API and implementation
******************************************************************************/
package org.eclipse.ecf.remoteservice.rest;

public class RestParameter implements IRestParameter {

	private String name;
	private String value;

	public RestParameter(String name, String value) {
		this.name = name;
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public String getValue() {
		return value;
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("RestParameter[name="); //$NON-NLS-1$
		buffer.append(name);
		buffer.append(", value="); //$NON-NLS-1$
		buffer.append(value);
		buffer.append("]"); //$NON-NLS-1$
		return buffer.toString();
	}

}