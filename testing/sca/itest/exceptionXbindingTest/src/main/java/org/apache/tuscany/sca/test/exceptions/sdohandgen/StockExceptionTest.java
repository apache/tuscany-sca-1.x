

    /**
     * StockExceptionTest.java
     *
     * This file was auto-generated from WSDL
     * by the Apache Axis2 version: #axisVersion# #today#
     */
    package org.apache.tuscany.sca.test.exceptions.sdohandgen;

    import org.osoa.sca.annotations.Remotable;
    import org.apache.tuscany.api.annotation.DataType;
    
    /*
     *  StockExceptionTest java interface
     */

    @Remotable
    @DataType(name="commonj.sdo.DataObject")
    public interface StockExceptionTest {
          
        /**
         * Auto generated method signatures
         * @param param0
         */
         public stockexceptiontestservice.scatesttool.StockOffer stockQuoteOffer(
         stockexceptiontestservice.scatesttool.StockOffer param0) throws java.rmi.RemoteException, InvalidSymbolSDOException, MarketClosedSDOException;
        
       }


    