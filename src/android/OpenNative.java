package cordova.plugin.mosambeepos;
import java.io.IOException;

import org.apache.cordova.*;
import org.json.JSONArray;
import org.json.JSONException;
import android.content.Context;
import com.mosambee.lib.ResultData;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;

public class OpenNative extends CordovaPlugin {

	JARClass jr;
	Mosambee mos;
	private static Context context;
    /**
     * Executes the request and returns a boolean.
     * 
     * @param action
     *            The action to execute.
     * @param args
     *            JSONArry of arguments for the plugin.
     * @param callbackContext
     *            The callback context used when calling back into JavaScript.
     * @return boolean.
     */ 
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
                if (action.equals("open")) {
                        try {
                        	System.out.println("callback"+callbackContext);
                        	String phone = args.getString(0);
                        	String propno = args.getString(1);
                        	String propid = args.getString(2);
                        	String totalamt = args.getString(3);
                        	String payableamt = args.getString(4);
                        	String suserMobile = args.getString(5);
                            openN(phone,propno,propid,totalamt,payableamt,suserMobile);
                            callbackContext.success("done");
                            
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                    else {
                    }
                    return true;
            }
            private void openN(String phone,String propno,String propid,String totalamt,String payableamt ,String suserMobile) throws IOException {

            }
            /*private void echo(String message, CallbackContext callbackContext) {
                if (message != null && message.length() > 0) {
                    callbackContext.success(message);
                } else {
                    callbackContext.error("Expected one non-empty string argument.");
                }
            }
            */
           
            
            
        }
