/*******************************************************************************
 * Copyright (c) 2004 Peter Nehrer and Composent, Inc.
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Peter Nehrer - initial API and implementation
 *******************************************************************************/
package org.eclipse.ecf.example.sdo.editor;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Status;
import org.eclipse.ecf.core.ISharedObjectContainer;
import org.eclipse.ecf.core.identity.IDFactory;
import org.eclipse.ecf.core.util.ECFException;
import org.eclipse.ecf.example.collab.Client;
import org.eclipse.ecf.sdo.ISharedDataGraph;
import org.eclipse.ecf.sdo.ISubscriptionCallback;
import org.eclipse.ecf.sdo.IUpdateConsumer;
import org.eclipse.ecf.sdo.SDOPlugin;
import org.eclipse.ecf.sdo.emf.EMFUpdateProvider;
import org.eclipse.ecf.test.EventSpy;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import commonj.sdo.DataGraph;

/**
 * The main plugin class to be used in the desktop.
 * 
 * @author pnehrer
 */
public class EditorPlugin extends AbstractUIPlugin {
    // The shared instance.
    private static EditorPlugin plugin;

    // Resource bundle.
    private ResourceBundle resourceBundle;

    private ISharedObjectContainer container;

    private PublishedGraphTracker tracker;

    /**
     * The constructor.
     */
    public EditorPlugin() {
        super();
        plugin = this;
        try {
            resourceBundle = ResourceBundle
                    .getBundle("org.eclipse.ecf.example.sdo.editor.EditorPluginResources");
        } catch (MissingResourceException x) {
            resourceBundle = null;
        }
    }

    /**
     * This method is called upon plug-in activation
     */
    public void start(BundleContext context) throws Exception {
        super.start(context);
    }

    /**
     * This method is called when the plug-in is stopped
     */
    public void stop(BundleContext context) throws Exception {
        super.stop(context);
    }

    /**
     * Returns the shared instance.
     */
    public static EditorPlugin getDefault() {
        return plugin;
    }

    /**
     * Returns the string from the plugin's resource bundle, or 'key' if not
     * found.
     */
    public static String getResourceString(String key) {
        ResourceBundle bundle = EditorPlugin.getDefault().getResourceBundle();
        try {
            return (bundle != null) ? bundle.getString(key) : key;
        } catch (MissingResourceException e) {
            return key;
        }
    }

    /**
     * Returns the plugin's resource bundle,
     */
    public ResourceBundle getResourceBundle() {
        return resourceBundle;
    }

    public void log(Throwable t) {
        if (t instanceof CoreException)
            getLog().log(((CoreException) t).getStatus());
        else
            getLog().log(
                    new Status(Status.ERROR, getBundle().getSymbolicName(), 0,
                            "An unexpected error occurred.", t));
    }

    public synchronized ISharedDataGraph subscribe(String path,
            ISubscriptionCallback callback, IUpdateConsumer consumer)
            throws ECFException {
        initialize();
        return SDOPlugin.getDefault().getDataGraphSharing(container).subscribe(
                IDFactory.makeStringID(path), callback,
                new EMFUpdateProvider(), consumer);
    }

    public synchronized ISharedDataGraph publish(String path,
            DataGraph dataGraph, IUpdateConsumer consumer) throws ECFException {
        initialize();
        SDOPlugin.getDefault().setDebug(true);
        return SDOPlugin.getDefault().getDataGraphSharing(container).publish(
                dataGraph, IDFactory.makeStringID(path),
                new EMFUpdateProvider(), consumer);
    }

    public synchronized boolean isPublished(String path) throws ECFException {
        initialize();
        return tracker.isPublished(path);
    }

    public synchronized void checkConnected() throws ECFException {
        initialize();
    }

    private void initialize() throws ECFException {
        if (tracker == null) {
            Client client;
            try {
                client = new Client();
            } catch (Exception e) {
                throw new ECFException(e);
            }

            container = client.getContainer();
            if (container == null)
                throw new ECFException("Not connected.");

            PublishedGraphTracker tracker = new PublishedGraphTracker();
            container.getSharedObjectManager().addSharedObject(
                    IDFactory.makeStringID(PublishedGraphTracker.class
                            .getName()), tracker, null, null);
            this.tracker = tracker;
            container.getSharedObjectManager()
                    .addSharedObject(IDFactory.makeStringID("debug"),
                            new EventSpy(), null, null);
        }
    }
}