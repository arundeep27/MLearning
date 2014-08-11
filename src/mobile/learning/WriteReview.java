/*
 *  This activity is used to send the reviews to server and store in database
 */

package mobile.learning;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;
import android.widget.RatingBar.OnRatingBarChangeListener;

public class WriteReview extends Activity implements OnRatingBarChangeListener
{
	 public SharedPreferences sp_obj;
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.writingreviewfile);
		
		final RatingBar rating=(RatingBar)findViewById(R.id.ratingBar1);
		rating.setEnabled(true);
		rating.setOnRatingBarChangeListener(this);
		
		/*
         *   getting references of Button "Submit Details" 
         */
		
		Button submit=(Button)findViewById(R.id.submit_btn);
		submit.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) 
			{
				sp_obj=getSharedPreferences("mlearninddata",MODE_WORLD_WRITEABLE);
			RatingBar rating=(RatingBar)findViewById(R.id.ratingBar1);
			String rating_val=""+rating.getRating();
			EditText review=(EditText)findViewById(R.id.reviewedit);
			String user_review=review.getEditableText().toString();
		    String username1=sp_obj.getString("username", "no username");
		    String Session_name=sp_obj.getString("sessionname", "No Session Name");
		/*
		 *  Checking whether the user has given rating and review or not    
		 */
		    if((user_review.equals("")) || (rating_val.equals("")))
		    {
		    	String str1=" Give Rating and Review To submit the details ";
				Displayalert(str1);
		    }
		    else
		    {
		    /*
			    * Connecting to the server  and sends reviews
			    */	
		
				HttpClient client = new DefaultHttpClient();
				
				    /*
			        *  If the server is in your system then replace the IPAaddress with your system IpAddress
			        */
	 HttpPost post = new HttpPost("http://10.11.32.59:8080/MlearningServerApp/ReceiveReview");
			
		    
			      List<NameValuePair> pairs = new ArrayList<NameValuePair>();
			      pairs.add(new BasicNameValuePair("username",username1));
			      pairs.add(new BasicNameValuePair("rating", rating_val));
			      pairs.add(new BasicNameValuePair("review", user_review));
			      pairs.add(new BasicNameValuePair("Sessionname", Session_name));
			     
			      try {
						post.setEntity(new UrlEncodedFormEntity(pairs));							
						HttpResponse response = client.execute(post);
						BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
						String line;
						StringBuilder sb =  new StringBuilder();
						while ((line = rd.readLine()) != null) 
						{
								sb.append(line);
						}
						rd.close();
						String s = sb.toString();
				/*
				 *  if the response is "Details Stored" then
				 */
						if(s.equals("Details Stored"))
						{
					//	Toast.makeText(WriteReview.this, "Details Stored Successfully"  , Toast.LENGTH_SHORT).show();
				String str2=" Details Stored Successfully ";
							Displayalert(str2);
							
					// clearing data for storing them on server
						clearReviewData();
						}
				/*
				 *  if the response is "Details Not Stored" then
				 */
						else if(s.equals("Details Not Stored"))
						{
							String str3=" Unsuccessfully to store details ";
							Displayalert(str3);
						//	Toast.makeText(WriteReview.this, "Unsuccessfully to store details "  , Toast.LENGTH_SHORT).show();
						}
	           
	        } 
	    catch (Exception e) 
	        {
	          e.printStackTrace();
	        } 	
  } // closing else block	
			}
		});
		
		/*
         *   getting references of Button "Go Home" 
         */
		
    Button home=(Button)findViewById(R.id.home_btn);
    home.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View v) 
		{
	Intent homeintent=new Intent(WriteReview.this,Course.class);
		startActivity(homeintent);
			
		}
	});
		
  
	} // Closing onCreate Method
	 
	/*
     *  Clearing data
     */
	public void clearReviewData()
	{
		RatingBar rating=(RatingBar)findViewById(R.id.ratingBar1);
	    rating.setRating(0);
		EditText review=(EditText)findViewById(R.id.reviewedit);
		review.setText("");
	}
	/*
	 *  Options Menu For Logout
	 */

	public boolean onCreateOptionsMenu(Menu menu_obj)
	{
		menu_obj.add(0, 1,0," Logout ");
		
		return true;
	}

	/*
	 * This Method is Invoked whenever a Menu Item is selected
	 * This Method identifies Selected Menu Item 
	 * with the help of ID of that Menu Item
	 */

	public boolean onOptionsItemSelected(MenuItem menuitem_obj)
	{
		switch(menuitem_obj.getItemId())
		{
		case 1:
			    //  Navigating to Login Activity when clicked on Logout
	              Intent intent_obj1=new Intent(WriteReview.this,LoginActivity.class);
			      startActivity(intent_obj1);
			break;
		 default:
		      break;
		}
		return true;
	}
	
	/*
	 *  Displaying Response as Alert
	 */
	public void Displayalert(String message)
    {
    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
    	builder.setTitle("Alert");
    	builder.setMessage(message);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
    	           public void onClick(DialogInterface dialog, int id) {
    	             
    	           }
    	       });
    	builder.show();
    }
	
	
	@Override
	public void onRatingChanged(RatingBar ratingbar, float rating, boolean fromuser) 
	{
     Toast.makeText(getBaseContext(),"The Rating Selected is:  "+rating, 
    		 Toast.LENGTH_LONG).show();
     
	}// closing onRatingChanged Method

}// Closing Class
