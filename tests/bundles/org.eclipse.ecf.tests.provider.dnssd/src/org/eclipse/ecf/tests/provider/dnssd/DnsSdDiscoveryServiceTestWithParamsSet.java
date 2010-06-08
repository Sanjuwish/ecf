/*******************************************************************************
 * Copyright (c) 2010 Markus Alexander Kuppe.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Markus Alexander Kuppe (ecf-dev_eclipse.org <at> lemmster <dot> de) - initial API and implementation
 ******************************************************************************/
package org.eclipse.ecf.tests.provider.dnssd;

import org.eclipse.ecf.discovery.IDiscoveryLocator;
import org.eclipse.ecf.provider.dnssd.DnsSdDisocoveryLocator;

public class DnsSdDiscoveryServiceTestWithParamsSet extends
		DnsSdDiscoveryServiceTest {

	public static final String RESOLVER = "ns1.ecf-project.org";

	public DnsSdDiscoveryServiceTestWithParamsSet() {
		super();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ecf.tests.discovery.DiscoveryServiceTest#getDiscoveryLocator()
	 */
	protected IDiscoveryLocator getDiscoveryLocator() {
		DnsSdDisocoveryLocator locator = (DnsSdDisocoveryLocator) super.getDiscoveryLocator();
		locator.setSearchPath(new String[]{DOMAIN});
		locator.setResolver(RESOLVER);
		return locator;
	}

}
