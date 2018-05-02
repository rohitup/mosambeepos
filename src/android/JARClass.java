package cordova.plugin.mosambeepos;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import android.content.Context;
import android.content.Intent;
import android.widget.FrameLayout;

import com.mosambee.lib.MosCallback;
import com.mosambee.lib.ResultData;
import com.mosambee.lib.TransactionCallback;
import com.mosambee.lib.TransactionResult;

public class JARClass implements TransactionResult {

	private Context context;
	MosCallback moscCallback;
	TransactionCallback tc;

	void startProcess(FrameLayout container, String orderId, String userId,String phone,String propno,String propid,String totalamt,String payableamt) {
		moscCallback = new MosCallback(context);
		tc = new MosCallback(context);
		System.out.println("User id "+userId+"Password : "+orderId);
		
		if (orderId.equals(null) || orderId.equals("")) {
			ResultData res = new ResultData();
			res.setAmount(null);
			res.setReason("Enter both the user id and order id properly");
			res.setReasonCode("MD031");
			res.setResult(false);
			res.setTransactionData(null);
			res.setTransactionId(null);
			onResult(res);
		} else {
			SimpleDateFormat dateFormatGmt = new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss");
			dateFormatGmt.setTimeZone(TimeZone.getTimeZone("GMT"));

			//Local time zone   
			SimpleDateFormat dateFormatLocal = new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss");

			//Time in GMT
			 try {
				dateFormatLocal.parse( dateFormatGmt.format(new Date()) );
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String getDate = dateFormatGmt.toString();
			double paidamt = Double.parseDouble(payableamt);
			double totalAmt = Double.parseDouble(totalamt);
			   tc.initialise(userId, orderId, this);
			// initializeSignatureView(FrameLayout, primaryColor, secondaryColor);
			   tc.initializeSignatureView(container, "#55004A", "#750F5A");
			// Communication mode will be by default Bluetooth
			// Communication mode will be USB if it is mentioned as USb.
			  /* moscCallback.initialiseFields("sale", "9689799845",
					"cGjhE$@fdhj4675riesae", false, "faiz.saifi@mosambee.in",
					"merchantRef1", "", "09082013101105", "120");*/
			  
			   moscCallback.setSleepTime(0);
			  // moscCallback.doChequeCash(PostType.CASH,  Double.parseDouble("1.00"), "Myname", "4554545454545",orderId) ;
			   //tc.processTransaction(orderId, Double.parseDouble("1.00"),Double.parseDouble("675466"), "879209");
			   tc.processTransaction("1234", totalAmt,paidamt, propid);
			// moscCallback.sendSMS((long) 36112,"1111212111");
			// moscCallback.doVoid("32696");
			// moscCallback.addTipAmount(Double.parseDouble("00.25"), (long)32693);
			// moscCallback.getTransactionHistory("5");
			// tc.getMetaData(orderId);
			//moscCallback.getReceipt("32428");
			// moscCallback.doSettlement();
		}

		// moscCallback.getTransactionHistory("1");
		// moscCallback.addTipAmount(Double.parseDouble("00.25"), (long) 13061);
		// moscCallback.getReceipt("13061");
		// moscCallback.doVoid("32666");
		// moscCallback.doSettlement();
		// tc.getMetaData("1223"); //call to invoice

	}

	public void getReceipt(String invoiceNo) {
		moscCallback = new MosCallback(context);
		tc = new MosCallback(context);
		tc.initialise("", "1234", this);
		tc.getMetaData(invoiceNo);
	}

	public void setContext(Context context) {
		this.context = context;
	}

	@Override
	public void onCommand(String command) {
		System.out.println("command is--------------------" + command);
		Mosambee.setCommand(command);
	}

	@Override
	public void onResult(ResultData result) {
		System.out.println("-----" + result.getTransactionData()+"\n-----------"+result.getReason());
		Mosambee.setData(result);
		
	}

	public void stopProcess() {
		moscCallback = new MosCallback(context);
		System.out.println("-------------called posReset()");
		moscCallback.posReset();
	}
}
