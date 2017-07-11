package cordova.plugin.mosambeepos;

import com.mosambee.lib.MosCallback;
import com.mosambee.lib.ResultData;
import com.mosambee.lib.TransactionCallback;
import org.apache.cordova.*;
import org.apache.cordova.engine.SystemWebView;
import com.abm.mainetTaxCollection.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;


public class Mosambee extends Activity implements OnClickListener{

	static TextView textView;
	TransactionCallback tc;
	OpenNative op;
	MosCallback mosCallback;
	private FrameLayout container;
	Button btnPayment, btnMetadata;
	EditText txtOrderID,textUserId;
	private static Handler handler;
	static Button buttonABORT;
	private static Context context;
	String phone = "";
	String propno = "";
	String propid = "";
	String totalamt = "";
	String payableamt = "";
	String suserMobile ="";
	final static JARClass jarClass = new JARClass();
	//Mosambee() { }
	public String LoadPreferences(String key){
		System.out.println("get key "+key);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        System.out.println("sharedPreferences.getString: "+sharedPreferences.getString(key, ""));
        return sharedPreferences.getString(key, "");
	}
	public String ClearPreferences(String key){
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("transactiondata", "").commit();
        return sharedPreferences.getString(key, "");
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//super.init();
		try{
			
		setContentView(R.layout.activity_main);
		 getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#36979e")));
		 getActionBar().setTitle("Mainet App");
		container = (FrameLayout) findViewById(R.id.frameContainer);
		textView = (TextView) findViewById(R.id.textView1);
		btnPayment = (Button) findViewById(R.id.button1);
		txtOrderID=(EditText) findViewById(R.id.textOrderId);
		textUserId=(EditText) findViewById(R.id.textUserId);
		buttonABORT=(Button) findViewById(R.id.buttonABORT);
		
		context = getApplicationContext();
		
		 phone = getIntent().getStringExtra("phone");
		 propno = getIntent().getStringExtra("propno");
		 propid = getIntent().getStringExtra("propid");
		 totalamt = getIntent().getStringExtra("totalamt");
		 payableamt = getIntent().getStringExtra("payableamt");
		 suserMobile = getIntent().getStringExtra("suserMobile");
		 textUserId.setText(suserMobile);
		 textUserId.setFocusable(false);
		 
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("Error while opening"+e);
			Toast.makeText(getApplicationContext(), "Error while opening"+e, Toast.LENGTH_LONG).show();
		}
		
		jarClass.setContext(context);
		
		handler = new Handler();
		btnPayment.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
			//	Toast.makeText(context, "Process starts here......", Toast.LENGTH_LONG).show();
				
				if(txtOrderID.getText().toString().equals(null) || txtOrderID.getText().toString().equals("") || textUserId.getText().toString().equals(null) || textUserId.getText().toString().equals("")){
					textView.setText("Please Enter Both Username and Password");
				}
				else {
					textView.setText("Process is going on......");
					jarClass.startProcess(container,txtOrderID.getText().toString(),textUserId.getText().toString(),phone,propno,propid,totalamt,payableamt);

				}
			}
		});
				
		buttonABORT.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				jarClass.stopProcess();
				/*ScriptEngineManager manager = new ScriptEngineManager();
		        ScriptEngine engine = manager.getEngineByName("JavaScript");
		     // JavaScript code in a String
		        String script = "function callLoadPrefs() { alert('hi'); }";
		        // evaluate script
		        engine.eval(script);
		 
		        // javax.script.Invocable is an optional interface.
		        // Check whether your script engine implements or not!
		        // Note that the JavaScript engine implements Invocable interface.
		        Invocable inv = (Invocable) engine;
		 
		        // invoke the global function named "hello"
		        //inv.invokeFunction("callLoadPrefs", "" );
*/				finish();
				/*try{
				SystemWebView webView;
				webView  = (SystemWebView) appView.getEngine().getView();
				
				webView.loadUrl("javascript:loadPrefs()");
				
				}catch(Exception e){
					e.printStackTrace();
				}*/
				
			}
		});
	}

	
	public static void setData(final ResultData result) {
		if (result == null) {
			textView.setText("");
		} else {
			textView.setText("");
			System.out.println("result.getTransactionData()-------"+result.getTransactionData());
			final CallbackContext callbackContext = null;
			//Mosambee.echo(result, callbackContext);
		
			
			Runnable runnable = new Runnable() {
				@Override
				public void run() {
					handler.post(new Runnable() { // This thread runs in the UI
						private Context Activity;

						@Override
						public void run() {
							System.out.println("Transaction data : "+result.getTransactionData());
							/*textView.setText("Result: " + result.getResult()
							+ "\nReason code: " + result.getReasonCode() + "\nReason: "
							+ result.getReason() + "\nTranaction Id: "
							+ result.getTransactionId() + "\nTransactin amount: "
							+ result.getAmount() + "\nTransactin data: "
							+ result.getTransactionData());*/
							
							/*AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
						      alertDialogBuilder.setMessage("Hello der");
						      AlertDialog alertDialog = alertDialogBuilder.create();
						      alertDialog.show();*/
							try{
								System.out.println("result.getReason()"+result.getReason());
								String transData = result.getTransactionData();
								if(transData.equals("null") || transData.equals("")){
									textView.setText(Html.fromHtml("<strong>Result<p style=' '>Reason: </strong>"+result.getReason()+"</p><strong><p style=' '>Tranaction Id: </strong>"+result.getTransactionId()+"</p>"));
								}
								else {
									//textView.setText("Please Enter Valid Credentials");
									String[] reason = result.getReason().split(": ");
									textView.setText(Html.fromHtml("<strong>Result<p style=' '>Reason: </strong>"+reason[1]+"</p><strong><p style=' '>Tranaction Id: </strong>"+result.getTransactionId()+"</p>"));
								}
							}catch(Exception e){
								e.printStackTrace();
							}
							
							String transactiondata = String.valueOf(result.getResult());
							System.out.println("getting transactiondata"+transactiondata);
					//String transactiondata =  result.getAmount();
					 SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
				        SharedPreferences.Editor editor = sharedPreferences.edit();
				        editor.putString("transactiondata", transactiondata);
				        editor.commit();
				    SharedPreferences sharedPreferencesLoad = PreferenceManager.getDefaultSharedPreferences(context);
				    if(transactiondata == "true"){
				    	stopprocess();
				    }
				   // textView.setText("sharedPreferencesLoad : " +sharedPreferencesLoad.getString("transactiondata", ""));
				    	
							
						}
					});
				}
				
			};
			new Thread(runnable).start();
			
		  }
		}
public static void stopprocess(){
	jarClass.stopProcess();
	buttonABORT.performClick();
}
	 /*public static void echo( ResultData result, CallbackContext callbackContext) {
		 System.out.println("result.getTransactionData() in echo -------"+result.getTransactionData());
             callbackContext.success(result.getTransactionData());
             
     }*/
	public static  void setCommand(final String command) {
		System.out.println("command is in setCommand()-------------------" + command);
		//textView.setText(command);

	
			Runnable runnable = new Runnable() {
				@Override
				public void run() {
					handler.post(new Runnable() { // This thread runs in the UI
						@Override
						public void run() {
							System.out
									.println("-textView.setText(command)---------------------"
											+ command);
							textView.setText(command);
						}
					});
				}
			};
			new Thread(runnable).start();
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
}
