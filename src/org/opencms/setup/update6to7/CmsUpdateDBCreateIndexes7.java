/*
 * File   : $Source: /alkacon/cvs/opencms/src/org/opencms/setup/update6to7/Attic/CmsUpdateDBCreateIndexes7.java,v $
 * Date   : $Date: 2007/05/25 08:14:37 $
 * Version: $Revision: 1.1.2.2 $
 *
 * This library is part of OpenCms -
 * the Open Source Content Mananagement System
 *
 * Copyright (C) 2005 Alkacon Software GmbH (http://www.alkacon.com)
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
 * For further information about Alkacon Software GmbH, please see the
 * company website: http://www.alkacon.com
 *
 * For further information about OpenCms, please see the
 * project website: http://www.opencms.org
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

package org.opencms.setup.update6to7;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.ExtendedProperties;
import org.opencms.setup.CmsSetupDb;
import org.opencms.util.CmsPropertyUtils;

/**
 * This class creates all the indexes that are used in the database version 7.<p>
 * 
 * @author metzler
 */
public class CmsUpdateDBCreateIndexes7 {

    /** Constant for the SQL query properties.<p> */
    private static final String QUERY_PROPERTY_FILE = "/update/sql/cms_add_new_indexes_queries.properties";

    /** The database connection.<p> */
    private CmsSetupDb m_dbcon;

    /** The sql queries.<p> */
    private ExtendedProperties m_queryProperties;

    /**
     * Constructor with paramaters for the database connection and query properties file.<p>
     * 
     * @param dbcon the database connection
     * @param rfsPath the path to the opencms installation
     * 
     * @throws IOException if the query properties cannot be read
     */
    public CmsUpdateDBCreateIndexes7(CmsSetupDb dbcon, String rfsPath)
    throws IOException {

        System.err.println(getClass().getName());
        m_dbcon = dbcon;
        m_queryProperties = CmsPropertyUtils.loadProperties(rfsPath + QUERY_PROPERTY_FILE);
    }

    /**
     * Gets the database connection.<p> 
     * 
     * @return the dbcon
     */
    public CmsSetupDb getDbcon() {

        return m_dbcon;
    }

    /**
     * Sets the database connection.<p>
     * 
     * @param dbcon the dbcon to set
     */
    public void setDbcon(CmsSetupDb dbcon) {

        m_dbcon = dbcon;
    }

    /**
     * Gets the query properties.<p>
     * 
     * @return the queryProperties
     */
    public ExtendedProperties getQueryProperties() {

        return m_queryProperties;
    }

    /**
     * Sets the database properties.<p>
     * 
     * @param queryProperties the queryProperties to set
     */
    public void setQueryProperties(ExtendedProperties queryProperties) {

        m_queryProperties = queryProperties;
    }

    /**
     * Creates the new indexes for the tables of the database.<p> 
     * 
     * @throws SQLException if something goes wrong 
     */
    public void createNewIndexes() throws SQLException {

        System.out.println(new Exception().getStackTrace()[0].toString());
        Set elements = m_queryProperties.entrySet();
        // iterate the queries
        for (Iterator it = elements.iterator(); it.hasNext();) {
            Map.Entry entry = (Map.Entry)it.next();
            m_dbcon.updateSqlStatement((String)entry.getValue(), null, null);
        }
    }
}
