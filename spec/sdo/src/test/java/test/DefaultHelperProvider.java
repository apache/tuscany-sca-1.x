/* =====================================================================
 *
 * Copyright (c) 2005 Jeremy Boynes.  All rights reserved.
 *
 * =====================================================================
 */
package test;

import commonj.sdo.impl.HelperProvider;
import commonj.sdo.impl.ExternalizableDelegator;
import commonj.sdo.helper.CopyHelper;
import commonj.sdo.helper.DataFactory;
import commonj.sdo.helper.DataHelper;
import commonj.sdo.helper.EqualityHelper;
import commonj.sdo.helper.TypeHelper;
import commonj.sdo.helper.XMLHelper;
import commonj.sdo.helper.XSDHelper;

/**
 * @version $Revision$ $Date$
 */
public class DefaultHelperProvider extends HelperProvider {
    public CopyHelper copyHelper() {
        throw new UnsupportedOperationException();
    }

    public DataFactory dataFactory() {
        throw new UnsupportedOperationException();
    }

    public DataHelper dataHelper() {
        throw new UnsupportedOperationException();
    }

    public EqualityHelper equalityHelper() {
        throw new UnsupportedOperationException();
    }

    public TypeHelper typeHelper() {
        throw new UnsupportedOperationException();
    }

    public XMLHelper xmlHelper() {
        throw new UnsupportedOperationException();
    }

    public XSDHelper xsdHelper() {
        throw new UnsupportedOperationException();
    }

    public ExternalizableDelegator.Resolvable resolvable() {
        throw new UnsupportedOperationException();
    }

    public ExternalizableDelegator.Resolvable resolvable(Object target) {
        throw new UnsupportedOperationException();
    }
}
