/*
 * File   : $Source: /alkacon/cvs/opencms/src-modules/org/opencms/gwt/client/ui/Attic/CmsListItem.java,v $
 * Date   : $Date: 2010/06/07 14:27:01 $
 * Version: $Revision: 1.20 $
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

import org.opencms.gwt.client.ui.css.I_CmsLayoutBundle;
import org.opencms.gwt.client.ui.css.I_CmsLayoutBundle.I_CmsListItemCss;
import org.opencms.gwt.client.ui.input.CmsCheckBox;

import java.util.LinkedList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

/**
 * List item which uses a float panel for layout.<p>
 * 
 * @author Georg Westenberger
 * 
 * @version $Revision: 1.20 $
 *  
 * @since 8.0.0 
 */
public class CmsListItem extends Composite implements I_CmsListItem {

    /**
     * @see com.google.gwt.uibinder.client.UiBinder
     */
    protected interface I_CmsSimpleListItemUiBinder extends UiBinder<CmsFlowPanel, CmsListItem> {
        // GWT interface, nothing to do here
    }

    /** The width of a checkbox. */
    private static final int CHECKBOX_WIDTH = 20;

    /** The CSS bundle used for this widget. */
    private static final I_CmsListItemCss CSS = I_CmsLayoutBundle.INSTANCE.listItemCss();

    /** The ui-binder instance for this class. */
    private static I_CmsSimpleListItemUiBinder uiBinder = GWT.create(I_CmsSimpleListItemUiBinder.class);

    /** The checkbox of this list item, or null if there is no checkbox. */
    protected CmsCheckBox m_checkbox;

    /** The panel which contains both the decorations (checkbox, etc.) and the main widget. */
    protected CmsSimpleDecoratedPanel m_decoratedPanel;

    /** A list of decoration widgets which is used to initialize {@link CmsListItem#m_decoratedPanel}. */
    protected LinkedList<Widget> m_decorationWidgets = new LinkedList<Widget>();

    /** The decoration width which should be used to initialize {@link CmsListItem#m_decoratedPanel}. */
    protected int m_decorationWidth;

    /** The logical id, it is not the HTML id. */
    protected String m_id;

    /** The main widget of the list item. */
    protected Widget m_mainWidget;

    /** This widgets panel. */
    protected CmsFlowPanel m_panel;

    /** The list item widget, if this widget has one. */
    private CmsListItemWidget m_listItemWidget;

    /** 
     * Default constructor.<p>
     */
    public CmsListItem() {

        m_panel = uiBinder.createAndBindUi(this);
        initWidget(m_panel);
    }

    /** 
     * Wrapping constructor.<p>
     * 
     * @param element the element to wrap 
     */
    public CmsListItem(Element element) {

        m_panel = new CmsFlowPanel(element);
        initWidget(m_panel);
    }

    /**
     * @see org.opencms.gwt.client.ui.I_CmsListItem#add(com.google.gwt.user.client.ui.Widget)
     */
    public void add(Widget w) {

        throw new UnsupportedOperationException();
    }

    /**
     * Gets the checkbox of this list item.<p>
     * 
     * This method will return a checkbox if this list item has one, or null if it doesn't.
     * 
     * @return a check box or null
     */
    public CmsCheckBox getCheckBox() {

        return m_checkbox;
    }

    /**
     * @see org.opencms.gwt.client.ui.I_CmsListItem#getId()
     */
    public String getId() {

        return m_id;
    }

    /**
     * Returns the list item widget of this list item, or null if this item doesn't have a list item widget.<p>
     * 
     * @return a list item widget or null
     */
    public CmsListItemWidget getListItemWidget() {

        if ((m_mainWidget == null) || !(m_mainWidget instanceof CmsListItemWidget)) {
            return null;
        }
        return (CmsListItemWidget)m_mainWidget;
    }

    /**
     * @see org.opencms.gwt.client.ui.I_CmsListItem#setId(java.lang.String)
     */
    public void setId(String id) {

        m_id = id;
    }

    /**
     * @see org.opencms.gwt.client.ui.I_CmsTruncable#truncate(java.lang.String, int)
     */
    public void truncate(String textMetricsPrefix, int widgetWidth) {

        for (Widget widget : m_panel) {
            if (!(widget instanceof I_CmsTruncable)) {
                continue;
            }
            int width = widgetWidth - 4; // just to be on the safe side
            if (widget instanceof CmsList<?>) {
                width -= 25; // 25px left margin
            }
            ((I_CmsTruncable)widget).truncate(textMetricsPrefix, width);
        }
    }

    /**
     * Adds a check box to this list item.<p>
     * 
     * @param checkbox the check box 
     */
    protected void addCheckBox(CmsCheckBox checkbox) {

        assert m_checkbox == null;
        m_checkbox = checkbox;
        addDecoration(m_checkbox, CHECKBOX_WIDTH, false);
        m_checkbox.addStyleName(CSS.listItemCheckbox());

    }

    /**
     * Helper method for adding a decoration widget and updating the decoration width accordingly.<p>
     * 
     * @param widget the decoration widget to add 
     * @param width the intended width of the decoration widget
     * @param first if true, inserts the widget at the front of the decorations, else at the end.
     */
    protected void addDecoration(Widget widget, int width, boolean first) {

        m_decorationWidgets.add(widget);
        m_decorationWidth += width;
    }

    /**
     * Adds the main widget to the list item.<p>
     * 
     * In most cases, the widget will be a list item widget. If this is the case, then further calls to {@link CmsListItem#getListItemWidget()} will 
     * return the widget which was passed as a parameter to this method. Otherwise, the method will return null.<p>
     * 
     * @param widget
     */
    protected void addMainWidget(Widget widget) {

        assert m_mainWidget == null;
        assert m_listItemWidget == null;
        if (widget instanceof CmsListItemWidget) {
            m_listItemWidget = (CmsListItemWidget)widget;
            // TODO: add style for list item widget here 
        }
        m_mainWidget = widget;
    }

    /**
     * This internal helper method creates the actual contents of the widget by combining the decorators and the main widget.<p>
     */
    protected void initContent() {

        if (m_decoratedPanel != null) {
            m_decoratedPanel.removeFromParent();
        }
        m_decoratedPanel = new CmsSimpleDecoratedPanel(m_decorationWidth, m_mainWidget, m_decorationWidgets);
        m_panel.insert(m_decoratedPanel, 0);
    }

    /**
     * This method is a convenience method which sets the checkbox and main widget of this widget, and then calls {@link CmsListItem#initContent()}.<p>
     *  
     * @param checkbox the checkbox to add
     * @param mainWidget the mainWidget to add
     */
    protected void initContent(CmsCheckBox checkbox, Widget mainWidget) {

        addCheckBox(checkbox);
        addMainWidget(mainWidget);
        initContent();
    }

    /**
     * This method is a convenience method which sets the main widget of this widget, and then calls {@link CmsListItem#initContent()}.<p>
     * 
     * @param mainWidget the main widget to add 
     */
    protected void initContent(Widget mainWidget) {

        addMainWidget(mainWidget);
        initContent();
    }

}
