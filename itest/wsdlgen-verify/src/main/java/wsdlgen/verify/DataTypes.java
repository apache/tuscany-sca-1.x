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

package wsdlgen.verify;

import java.util.List;
import javax.jws.WebParam;
import javax.jws.soap.SOAPBinding;
import commonj.sdo.DataObject;
import org.osoa.sca.annotations.Remotable;

@Remotable
public interface DataTypes {

    void testSimpleInt(int simple);

    void testSimpleArrayInt(int[] simple);

    void testSimpleMultiArrayInt(int[][] simple);

    void testSimpleMulti3ArrayInt(int[][][] simple);

    void testList(List any);

    void testSimpleListString(List<String> simple);

    List<String> testReturnSimpleListString();

    void testListByteArray(List<byte[]> byteArrayList);

    void testListWildcard(List<?> wild);

    void testComplex(ComplexNumber complex);

    void testByteArray(byte[] byteArray);

    void testException() throws Exception;

    DataObject testDynamicSDO();

/*
    @SOAPBinding(parameterStyle=SOAPBinding.ParameterStyle.BARE)
    void testWebParam(@WebParam(name="simpleInt") int simple);

    @SOAPBinding(parameterStyle=SOAPBinding.ParameterStyle.BARE)
    void testWebParamArray(@WebParam(name="arrayInt") int[] array);
*/
}
