/*******************************************************************************
 * Copyright (c) 2011 Subgraph.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Subgraph - initial API and implementation
 ******************************************************************************/
package com.subgraph.vega.internal.ui.http;

import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

public class ProxyPerspectiveFactory implements IPerspectiveFactory {
	private final static String PROXY_FOLDER = "proxy";
	private final static String HTTP_VIEW = "com.subgraph.vega.views.http";
	private final static String INTERCEPT_VIEW = "com.subgraph.vega.views.intercept";
	private final static String INTERCEPT_QUEUE_VIEW = "com.subgraph.vega.views.intercept.queue";
	private final static String WEBSITE_VIEW = "com.subgraph.vega.views.website";

	@Override
	public void createInitialLayout(IPageLayout layout) {
		IFolderLayout proxyFolder = layout.createFolder(PROXY_FOLDER, IPageLayout.TOP, 0f, layout.getEditorArea());
		proxyFolder.addView(HTTP_VIEW);
		proxyFolder.addView(INTERCEPT_VIEW);
		proxyFolder.addView(INTERCEPT_QUEUE_VIEW);
		layout.addView(WEBSITE_VIEW, IPageLayout.LEFT, 0.25f, PROXY_FOLDER);
		layout.setEditorAreaVisible(false);
		layout.getViewLayout(HTTP_VIEW).setCloseable(false);
		layout.getViewLayout(INTERCEPT_VIEW).setCloseable(false);
		layout.getViewLayout(INTERCEPT_QUEUE_VIEW).setCloseable(false);
		layout.getViewLayout(WEBSITE_VIEW).setCloseable(false);
	}

}
