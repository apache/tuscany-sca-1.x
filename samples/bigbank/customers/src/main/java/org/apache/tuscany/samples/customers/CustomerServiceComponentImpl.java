/**
 *
 *  Copyright 2005 The Apache Software Foundation or its licensors, as applicable.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.apache.tuscany.samples.customers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;

import commonj.sdo.DataObject;
import org.osoa.sca.annotations.Service;

import org.apache.tuscany.das.rdb.Command;

/**
 * 
 */
@Service(CustomerServiceComponent.class)
public class CustomerServiceComponentImpl implements CustomerServiceComponent {

    private static final String driverName = "org.apache.derby.jdbc.EmbeddedDriver";

    private static final String databaseURL = "jdbc:derby:../webapps/tuscany-samples-customerWEB/customerdb";

    /*
      * (non-Javadoc)
      *
      * @see org.apache.tuscany.samples.helloworldweb.HelloWorldServiceComponent#getGreetings()
      */
    public DataObject getCustomer(String id) {
        // Create and initialize command to read customers
        Command readCustomers = Command.FACTORY
                .createCommand("select * from CUSTOMER where ID = :ID");
        readCustomers.setConnection(getConnection());
        readCustomers.setParameterValue("ID", new Integer(id));

        // Read
        DataObject root = readCustomers.executeQuery();

        readCustomers.close();

        List customers = root.getList("CUSTOMER");
        if (customers.size() > 0)
            return (DataObject) customers.get(0);
        else
            return null;

    }

    private Connection getConnection() {
        try {

            Class.forName(driverName).newInstance();
            Connection connection = DriverManager.getConnection(databaseURL);
            connection.setAutoCommit(false);
            return connection;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

    }

}
