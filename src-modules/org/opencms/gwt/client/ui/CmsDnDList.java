/*
 * File   : $Source: /alkacon/cvs/opencms/src-modules/org/opencms/gwt/client/ui/Attic/CmsDnDList.java,v $
 * Date   : $Date: 2010/06/07 14:27:01 $
 * Version: $Revision: 1.1 $
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

package org.opencms.gwt.client.ui;

import org.opencms.gwt.client.draganddrop.I_CmsSortableDragTarget;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Widget;

/**
 * List for DnD.<p>
 * 
 * @param <I> the specific list item implementation 
 * 
 * @author Michael Moossen
 * 
 * @version $Revision: 1.1 $
 * 
 * @since 8.0.0
 */
public class CmsDnDList<I extends CmsDnDListItem> extends CmsList<I> implements I_CmsSortableDragTarget {

    /** Flag to indicate if drag'n drop is enabled. */
    protected boolean m_dndEnabled;

    /** The drag'n drop handler. */
    protected CmsDnDListHandler m_handler;

    /** The handler manager. */
    protected HandlerManager m_handlerManager;

    /**
     * Constructor.<p>
     */
    public CmsDnDList() {

        super();
        m_handlerManager = new HandlerManager(this);
    }

    /**
     * Adds a new list drop event handler.<p>
     * 
     * @param handler the handler to add
     * 
     * @return the handler registration
     */
    public HandlerRegistration addListDropHandler(I_CmsDnDListDropHandler handler) {

        return m_handlerManager.addHandler(CmsDnDListDropEvent.getType(), handler);
    }

    /**
     * Returns the drag'n drop handler.<p>
     *
     * @return the handler
     */
    public CmsDnDListHandler getDnDHandler() {

        return m_handler;
    }

    /**
     * Checks if drag'n drop is enabled.<p>
     *
     * @return <code>true</code> if drag'n drop is enabled
     */
    public boolean isDndEnabled() {

        return m_dndEnabled;
    }

    /**
     * Enables/Disables drag'n drop.<p>
     * 
     * @param enabled <code>true</code> to enable drag'n drop 
     */
    @SuppressWarnings("unchecked")
    public void setDnDEnabled(boolean enabled) {

        m_dndEnabled = enabled;
        if (m_handler == null) {
            m_handler = new CmsDnDListHandler();
            m_handler.addDragTarget((CmsDnDList<CmsDnDListItem>)this);
        }
        for (Widget w : this) {
            if (w instanceof CmsDnDListItem) {
                if (enabled) {
                    ((CmsDnDListItem)w).enableDnD(getDnDHandler());
                } else {
                    ((CmsDnDListItem)w).disableDnD();
                }
            }
        }
    }

    /**
     * Sets the drag'n drop handler.<p>
     *
     * @param handler the handler to set
     */
    public void setDnDHandler(CmsDnDListHandler handler) {

        m_handler = handler;
    }

    /**
     * Fires a new drag and drop event.<p>
     * 
     * @param dropEvent the event to fire
     */
    protected void fireDropEvent(CmsDnDListDropEvent dropEvent) {

        m_handlerManager.fireEvent(dropEvent);
    }

    /**
     * @see org.opencms.gwt.client.ui.CmsList#registerItem(org.opencms.gwt.client.ui.I_CmsListItem)
     */
    @Override
    protected void registerItem(I item) {

        super.registerItem(item);
        item.setDragParent(this);
        if (getDnDHandler() != null) {
            if (isDndEnabled()) {
                item.enableDnD(getDnDHandler());
            } else {
                item.disableDnD();
            }
        }
    }
}
