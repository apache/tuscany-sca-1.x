package calculator.android;

import org.apache.tuscany.sca.host.embedded.SCADomain;

import calculator.CalculatorService;
import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class CalculatorClient extends Activity {
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle icicle) {
    	 super.onCreate(icicle);
    	 StringBuffer sb = new StringBuffer();
    	 
    	 SCADomain scaDomain = SCADomain.newInstance(this, "dex://calculator.android/raw/calculator.composite");
    	 
         CalculatorService calculatorService = 
             scaDomain.getService(CalculatorService.class, "CalculatorServiceComponent");
         
                  // Calculate
         sb.append("3 + 2=" + calculatorService.add(3, 2));
         sb.append("3 - 2=" + calculatorService.subtract(3, 2));
         sb.append("3 * 2=" + calculatorService.multiply(3, 2));
         sb.append("3 / 2=" + calculatorService.divide(3, 2));
         
         scaDomain.close();
         
         TextView tv = new TextView(this);
         tv.setText(sb.toString());
         setContentView(tv);
         
    }
}