/*
 * File   : $Source: /alkacon/cvs/opencms/src-modules/org/opencms/gwt/client/ui/tree/Attic/CmsTree.java,v $
 * Date   : $Date: 2010/06/07 14:27:01 $
 * Version: $Revision: 1.5 $
 *
 * This library is part of OpenCms -
 * the Open Source Content Management System
 *
 * Copyright (C) 2002 - 2009 Alkacon Software (http://www.alkacon.com)
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * For further information about Alkacon Software, please see the
 * company website: http://www.alkacon.com
 *
 * For further information about OpenCms, please see the
 * project website: http://www.opencms.org
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

package org.opencms.gwt.client.ui.tree;

import org.opencms.gwt.client.ui.CmsList;
import org.opencms.util.CmsStringUtil;

import com.google.gwt.event.logical.shared.HasOpenHandlers;
import com.google.gwt.event.logical.shared.OpenEvent;
import com.google.gwt.event.logical.shared.OpenHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.HasAnimation;
import com.google.gwt.user.client.ui.Widget;

/**
 * A tree of list items.<p>
 * 
 * @param <I> the specific tree item implementation 
 * 
 * @author Georg Westenberger
 * 
 * @version $Revision: 1.5 $
 * 
 * @since 8.0.0
 */
public class CmsTree<I extends CmsTreeItem> extends CmsList<I> implements HasOpenHandlers<I>, HasAnimation {

    /** The event handlers for the tree. */
    protected HandlerManager m_handlers;

    /** Flag to indicate is animations are enabled or not. */
    private boolean m_animate;

    /**
     * Constructor.<p>
     */
    public CmsTree() {

        m_animate = false;
        m_handlers = new HandlerManager(this);
    }

    /**
     * @see org.opencms.gwt.client.ui.CmsList#add(com.google.gwt.user.client.ui.Widget)
     */
    @SuppressWarnings("unchecked")
    @Override
    public void add(Widget item) {

        super.add(item);
        if (item instanceof CmsTreeItem) {
            ((CmsTreeItem)item).setTree((CmsTree<CmsTreeItem>)this);
        }
    }

    /**
     * @see com.google.gwt.event.logical.shared.HasOpenHandlers#addOpenHandler(com.google.gwt.event.logical.shared.OpenHandler)
     */
    public HandlerRegistration addOpenHandler(final OpenHandler<I> handler) {

        m_handlers.addHandler(OpenEvent.getType(), handler);
        return new HandlerRegistration() {

            /**
             * @see com.google.gwt.event.shared.HandlerRegistration#removeHandler()
             */
            public void removeHandler() {

                m_handlers.removeHandler(OpenEvent.getType(), handler);
            }
        };
    }

    /**
     * @see com.google.gwt.user.client.ui.Widget#fireEvent(com.google.gwt.event.shared.GwtEvent)
     */
    @Override
    public void fireEvent(GwtEvent<?> event) {

        m_handlers.fireEvent(event);
    }

    /**
     * Fires an open event for a tree item.<p>
     *
     * @param item the tree item for which the open event should be fired
     */
    public void fireOpen(I item) {

        OpenEvent.fire(this, item);
    }

    /**
     * Returns the tree entry with the given path.<p>
     * 
     * @param path the path to look for
     * 
     * @return the tree entry with the given path, or <code>null</code> if not found
     */
    @SuppressWarnings("unchecked")
    public I getTreeItem(String path) {

        String[] names = CmsStringUtil.splitAsArray(path, "/");
        I result = null;
        for (String name : names) {
            if (CmsStringUtil.isEmptyOrWhitespaceOnly(name)) {
                // in case of leading slash
                continue;
            }
            if (result != null) {
                result = (I)result.getChild(name);
            } else {
                // match the root node
                result = getItem(name);
            }
            if (result == null) {
                // not found
                break;
            }
        }
        return result;
    }

    /**
     * @see com.google.gwt.user.client.ui.HasAnimation#isAnimationEnabled()
     */
    public boolean isAnimationEnabled() {

        return m_animate;
    }

    /**
     * @see com.google.gwt.user.client.ui.HasAnimation#setAnimationEnabled(boolean)
     */
    public void setAnimationEnabled(boolean enable) {

        m_animate = enable;
    }
}
