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
package calculator.sci;

import org.osoa.sca.annotations.Property;
import org.osoa.sca.annotations.Reference;

public class SciCalculatorServiceImpl implements SciCalculatorService {

    public static final String RADIANS = "RADIANS";
    public static final String DEGREES = "DEGREES";

    @Reference
    protected SqrtService sqrtService;

    private String trig_metric;

    public SciCalculatorServiceImpl() {
    }

    public double average(double values[]) {
        double sum = 0.0D;
        int count = 0;
        double arr$[] = values;
        int len$ = arr$.length;
        for (int i$ = 0; i$ < len$; i$++) {
            double aValue = arr$[i$];
            sum += aValue;
            count++;
        }

        return sum / (double)count;
    }

    public double sqrt(double n) {
        return sqrtService.sqrt(n);
    }

    public double sin(double angle) {
        if (trig_metric.equalsIgnoreCase("DEGREES"))
            angle = Math.toRadians(angle);
        return Math.sin(angle);
    }

    public double cos(double angle) {
        if (trig_metric.equalsIgnoreCase("DEGREES"))
            angle = Math.toRadians(angle);
        return Math.cos(angle);
    }

    public double tan(double angle) {
        if (trig_metric.equalsIgnoreCase("DEGREES"))
            angle = Math.toRadians(angle);
        return Math.tan(angle);
    }

    public String getTrig_metric() {
        return trig_metric;
    }

    @Property
    public void setTrig_metric(String trig_metric) {
        this.trig_metric = trig_metric;
    }

}
