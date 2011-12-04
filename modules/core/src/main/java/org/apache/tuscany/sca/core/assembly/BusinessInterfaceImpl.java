/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.    
 */

package org.apache.tuscany.sca.core.assembly;

import org.apache.tuscany.sca.runtime.BusinessInterface;

/**
 * @version $Rev: 574648 $ $Date: 2007-09-11 18:45:36 +0100 (Tue, 11 Sep 2007) $
 */
public class BusinessInterfaceImpl implements BusinessInterface {
    private String interfaze;
    
    /**
     * @return the interface class name
     */
    public String getInterface() {
        return interfaze;
    }

    /**
     * @param interfaze the interface class name
     */
    public void setInterface(String interfaze) {
        this.interfaze = interfaze;
    }

    /**
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((interfaze == null) ? 0 : interfaze.hashCode());
        return result;
    }

    /**
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof BusinessInterfaceImpl))
            return false;
        final BusinessInterfaceImpl other = (BusinessInterfaceImpl)obj;
        if (interfaze == null) {
            if (other.interfaze != null)
                return false;
        } else if (!interfaze.equals(other.interfaze))
            return false;
        return true;
    }
}
