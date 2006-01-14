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
package org.apache.tuscany.samples.bigbank.webclient.tags.sca;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.osoa.sca.CurrentModuleContext;
import org.osoa.sca.ModuleContext;


/**
 * Places an SCA service in the JSP page context, making it available to other
 * tags corresponding to its id value.
 */

public class ServiceTag extends TagSupport {

    // ----------------------------------
    // Constructors
    // ----------------------------------

    public ServiceTag() {
        super();
    }

    // ----------------------------------
    // Methods
    // ----------------------------------

    private String mName;

    /**
     * Returns the name of the SCA service to import into the page context.
     */
    public String getName() {
        return mName;
    }

    /**
     * Sets name of the SCA service to import into the page context.
     */
    public void setName(String pName) {
        mName = pName;
    }

    private String mId;

    /**
     * Returns the id of the service in the page context
     */
    public String getId() {
        return mId;
    }

    /**
     * Sets the id of the service for the page context
     */

    public void setId(String pId) {
        mId = pId;
    }

    public int doStartTag() throws JspException {
        ModuleContext moduleContext = CurrentModuleContext.getContext();
        Object service = moduleContext.locateService(mName);
        if (service == null) {
            throw new JspException("Service [" + mName + "] not found in current module context");
        }
        if (mId == null) {
            // if the Id name was not specified, default to the basic name of the
            // service
            mId = mName;
        }
        pageContext.setAttribute(mId, service);
        return EVAL_BODY_INCLUDE;
    }

    public int doEndTag() throws JspException {
        return EVAL_PAGE;
    }

    public void release() {
        super.release();
    }
}