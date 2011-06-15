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
package com.subgraph.vega.application;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;
import org.osgi.util.tracker.ServiceTracker;

import com.subgraph.vega.api.console.IConsole;
import com.subgraph.vega.api.model.IModel;
import com.subgraph.vega.api.paths.IPathFinder;
import com.subgraph.vega.application.preferences.IVegaSocksPreferenceConstants;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "com.subgraph.vega.application"; //$NON-NLS-1$

	// The shared instance
	private static Activator plugin;
	
	private ServiceTracker modelTracker;
	private ServiceTracker consoleTracker;
	private ServiceTracker pathFinderTracker;
	
	/**
	 * The constructor
	 */
	public Activator() {
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
		
		modelTracker = new ServiceTracker(context, IModel.class.getName(), null);
		modelTracker.open();
		
		consoleTracker = new ServiceTracker(context, IConsole.class.getName(), null);
		consoleTracker.open();
		
		pathFinderTracker = new ServiceTracker(context, IPathFinder.class.getName(), null);
		pathFinderTracker.open();
		
		getPreferenceStore().addPropertyChangeListener(new IPropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent event) {
				configureSocks();
			}
		});
		configureSocks();
	}

	private void configureSocks() {
		final IPreferenceStore store = getPreferenceStore();
		if(!store.getBoolean(IVegaSocksPreferenceConstants.P_SOCKS_ENABLED)) {
			System.getProperties().remove("socksProxyHost");
			System.getProperties().remove("socksProxyPort");
			System.getProperties().remove("socksEnabled");
			return;
		}
		
		System.setProperty("socksProxyHost", store.getString(IVegaSocksPreferenceConstants.P_SOCKS_ADDRESS));
		System.setProperty("socksProxyPort", store.getString(IVegaSocksPreferenceConstants.P_SOCKS_PORT));
		System.setProperty("socksEnabled", "true");
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static Activator getDefault() {
		return plugin;
	}

	/**
	 * Returns an image descriptor for the image file at the given
	 * plug-in relative path
	 *
	 * @param path the path
	 * @return the image descriptor
	 */
	public static ImageDescriptor getImageDescriptor(String path) {
		return imageDescriptorFromPlugin(PLUGIN_ID, path);
	}
	
	public IModel getModel() {
		return (IModel) modelTracker.getService();
	}
	
	public IConsole getConsole() {
		return (IConsole) consoleTracker.getService();
	}
	
	public IPathFinder getPathFinder() {
		return (IPathFinder) pathFinderTracker.getService();
	}
}
