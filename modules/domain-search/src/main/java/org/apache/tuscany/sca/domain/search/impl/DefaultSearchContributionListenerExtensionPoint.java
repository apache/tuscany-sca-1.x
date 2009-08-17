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
package org.apache.tuscany.sca.domain.search.impl;

import org.apache.tuscany.sca.contribution.Contribution;
import org.apache.tuscany.sca.contribution.service.ContributionRepository;
import org.apache.tuscany.sca.domain.search.SearchContributionListenerExtensionPoint;

/**
 * 
 * @version $Rev$ $Date$
 */
public class DefaultSearchContributionListenerExtensionPoint implements SearchContributionListenerExtensionPoint {

    public void contributionAdded(ContributionRepository repository, Contribution contribution) {
        System.out.println("contributionAdded:");
        System.out.println(repository);
        System.out.println(contribution);

    }

    public void contributionRemoved(ContributionRepository repository, Contribution contribution) {
        System.out.println("contributionRemoved:");
        System.out.println(repository);
        System.out.println(contribution);

    }

    public void contributionUpdated(ContributionRepository repository,
                                    Contribution oldContribution,
                                    Contribution contribution) {

        System.out.println("contributionUpdated:");
        System.out.println(repository);
        System.out.println(oldContribution);
        System.out.println(contribution);

    }

}
