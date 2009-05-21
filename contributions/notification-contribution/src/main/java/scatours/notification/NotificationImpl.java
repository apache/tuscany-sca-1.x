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

package scatours.notification;

import org.osoa.sca.annotations.Reference;
import org.osoa.sca.annotations.Service;

import scatours.smsgateway.SMSGateway;

@Service(Notification.class)
public class NotificationImpl implements Notification {

  private static final String SCA_TOURS_SMS ="12345";
  
  @Reference
  protected SMSGateway smsGateway;

  public boolean notify(String accountID,
      String subject, String message) {
    
    boolean result = true;
   
    String sms = getSMSAddress(accountID);
    if (sms != null) {
      System.out.println("Sending SMS to " + sms);
      result &= smsGateway.sendSMS(SCA_TOURS_SMS, sms, subject + message);
    }

    return result;
  }

  private String getSMSAddress(String accountID) {
    return "23874";
  }
}
