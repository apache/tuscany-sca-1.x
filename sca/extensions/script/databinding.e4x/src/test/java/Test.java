import org.apache.axiom.om.OMNode;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.Script;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.xmlimpl.AxiomNode;
import org.mozilla.javascript.xmlimpl.AxiomNodeAccessor;
import org.mozilla.javascript.xmlimpl.XML;

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

public class Test {
    
    public static void main(String[] args) {
        Context cx = Context.enter();
        try {
            Scriptable scope = cx.initStandardObjects();
            
            String script = "var xml = <a><b>petra</b></a>; java.lang.System.out.println(typeof xml); xml;";
            
            Object result = cx.evaluateString(scope, script, "<cmd>", 1, null);            
            System.out.println(result);
            XML xml = (XML) result;
            OMNode om = xml.getAxiomFromXML();
            String script2 = "function foo(x) { java.lang.System.out.println(typeof x); return x; }";
            Script func = cx.compileString(script2, "<cmd>", 1, null);
            func.exec(cx, scope);
            Function f = (Function)scope.get("foo", scope);

//            AxiomNode x = AxiomNodeAccessor.toAxiomNode(om);
//            Object jsXML = cx.getWrapFactory().wrap(cx, scope, om, OMNode.class);
            Object[] jsArgs = new Object[] { cx.newObject(scope, "XML", new Object[] { om }) };
            
            Object result2 = f.call(cx, scope, scope, jsArgs);            
            System.out.println(result2);
        } finally {
            Context.exit();
        }
    }

}
