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
package bigbank.webclient.services.profile;

import java.rmi.RemoteException;

import org.osoa.sca.annotations.Reference;
import org.osoa.sca.annotations.Service;

import com.bigbank.account.AccountService;
import com.bigbank.account.CustomerProfileData;

@Service(LoginService.class)
public class AccountLoginServiceImpl implements LoginService {
    @Reference 
    public AccountService accountService;
    @Reference
    public ProfileService profileService;

    public int login(String userName, String password) throws RemoteException {

        CustomerProfileData profileData = accountService.getCustomerProfile(userName);
 
        if (!password.equals(profileData.getPassword())) {
            return INVALID_PASSWORD;
        }

        profileService.setLoggedIn(true);
        profileService.setFirstName(profileData.getFirstName());
        profileService.setLastName(profileData.getLastName());
        profileService.setId(profileData.getId());

        return SUCCESS;
    }
}
