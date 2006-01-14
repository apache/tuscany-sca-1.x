<%--
  Copyright (c) 2005 The Apache Software Foundation or its licensors, as applicable.

  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
 --%>
<%@ taglib uri="/WEB-INF/bigbank-tags.tld" prefix="sca" %>
<sca:login profile="ProfileServiceComponent" url="login.html">
    <sca:service id="profile" name="ProfileServiceComponent"/>

    <html>
    <title>BigBank Account Summary</title>

    <body>

    Account Information for
    <jsp:getProperty name='profile' property='firstName'/>
    <jsp:getProperty name='profile' property='lastName'/>
    <br>

    <table>
        <sca:accountStatus accountService="AccountServiceComponent" profileService="ProfileServiceComponent" id="account">
        <tr>
            <td><strong>Account</strong></td>
            <td><strong>Balance</strong></td>
        </tr>
        <tr>
            <td>
                <jsp:getProperty name="account" property="accountNumber"/>
            </td>
            <td>
                <jsp:getProperty name="account" property="balance"/>
            </td>
        </tr>
        </sca:accountStatus>
        <table>
    </body>
    </html>
</sca:login>
