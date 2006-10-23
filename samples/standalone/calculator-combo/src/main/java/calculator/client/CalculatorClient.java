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
package calculator.client;

import org.osoa.sca.CompositeContext;
import org.osoa.sca.CurrentCompositeContext;

import calculator.CalculatorService;
import calculator.sci.SciCalculatorService;

/**
 * Calculator client
 */
public class CalculatorClient {

    public CalculatorClient() {
    }

    public static void main(String args[]) throws Exception {
        try {
            CalculatorClient calcClient = new CalculatorClient();
            calcClient.testCalcCombo(args);
            System.exit(0);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    private void testCalcCombo(String args[]) {
        System.out.println("\n\n***************************************");
        System.out.println("Starting the Calculator Combo sample!!!");
        System.out.println("***************************************");
        CompositeContext context = CurrentCompositeContext.getContext();
        CalculatorService calculatorService =
            (CalculatorService)context.locateService(CalculatorService.class, "CalculatorServiceComponent");
        System.out.println("\nInvoking Java Implementation ... ");
        System.out.println((new StringBuilder()).append("3 + 2 = ").append(calculatorService.add(3D, 2D))
            .toString());
        System.out.println("\nInvoking Ruby Implementation ... ");
        System.out.println((new StringBuilder()).append("3 - 2 = ")
            .append(calculatorService.subtract(3D, 2D)).toString());
        System.out.println("\nInvoking over RMI Reference... ");
        System.out.println((new StringBuilder()).append("3 * 2 = ")
            .append(calculatorService.multiply(3D, 2D)).toString());
        System.out.println("\nInvoking WebService Implementation ... ");
        System.out.println((new StringBuilder()).append("3 / 2 = ").append(calculatorService.divide(3D, 2D))
            .toString());
        System.out.println("\nInvoking Scientific Calculator Composite Implementation ... ");
        SciCalculatorService sciCalculator =
            (SciCalculatorService)context.locateService(SciCalculatorService.class, "sciCalculatorService");
        double values[] = {1.0D, 2D, 3D, 4D, 5D, 6D, 7D, 8D, 9D};
        System.out.println("\tInvoking Java Implementation  ... ");
        System.out.println((new StringBuilder()).append("\tAverage of 1,2,3,4,5,6,7,8,9 = ")
            .append(sciCalculator.average(values)).toString());
        System.out.println("\n\tInvoking JavaScript Implementation  ... ");
        System.out.println((new StringBuilder()).append("\tSquare Root of 81 = ").append(sciCalculator
            .sqrt(81D)).toString());
        System.out.println("\tInvoking Java Implementation configured for Property  ... ");
        System.out.println((new StringBuilder()).append("\tSine 90 Degrees = ")
            .append(sciCalculator.sin(90D)).toString());
        System.out.println((new StringBuilder()).append("\tCos 90 Degrees = ").append(sciCalculator.cos(90D))
            .toString());
        System.out.println((new StringBuilder()).append("\tTan 90 Degrees = ").append(sciCalculator.tan(90D))
            .toString());
        System.out.println("\nExiting...");
    }

}
