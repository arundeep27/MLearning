/*
 *  This Activity is used to connect to server and retrieve the reviews about particular session
 */

package mobile.learning;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

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


public class ViewReview extends Activity
{
	
	public SharedPreferences sp_obj;
    public void onCreate(Bundle savedInstanceState)
    {
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.viewreviewfile);
    	/*
		 * Getting the path of Shared Preference XML File
		 */
    	sp_obj=getSharedPreferences("mlearninddata",MODE_WORLD_WRITEABLE);
    	
    	// Fetching session name from XML file
    	String Session_name=sp_obj.getString("sessionname", "No Session Name");
    	
    	 /*
		    * Connecting to the server  and retrieve reviews
		    */
    	
    	HttpClient client = new DefaultHttpClient();
    	    /*
	        *  If the server is in your system then replace the IPAaddress with your system IpAddress
	        */
	       HttpPost post = new HttpPost("http://10.11.32.59:8080/MlearningServerApp/SendReview");
    	
	          
	      List<NameValuePair> pairs = new ArrayList<NameValuePair>();
	      pairs.add(new BasicNameValuePair("Sessionname", Session_name));
	     
	      try {
				post.setEntity(new UrlEncodedFormEntity(pairs));							
				HttpResponse response = client.execute(post);
				BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
				String line;
				StringBuilder sb =  new StringBuilder();
				String check=rd.readLine().trim();
				if(!check.equals("NoReviewAvailable"))
				{
				String receivinguname =rd.readLine();
				String receivingcomment=rd.readLine();
				String receivingrating=rd.readLine();
				String[] reviewuname_array=Split(receivinguname,"#@*%");
				String[] reviewcomment_array=Split(receivingcomment,"#@*%");
				String[] reviewrating_array=Split(receivingrating,"#@*%");
		/*
		 *  Sending the REceived review arrays to "MultiColumnListView" Activity for displaying them in a ListView
		 */
				
				 Intent intent1=new Intent(ViewReview.this, MultiColumnListView.class);
				 intent1.putExtra("username_array", reviewuname_array);
				 intent1.putExtra("comment_array", reviewcomment_array);
				 intent1.putExtra("rating_array", reviewrating_array);
	             startActivity(intent1);
				}
				else
				{
					displayalert();
					
					//Toast.makeText(ViewReview.this, "There are no Reviews given to this Session" , Toast.LENGTH_SHORT).show();
				}
		} // closing try block
catch (Exception e) 
 {
   e.printStackTrace();
 } 	


    }  // Closing onCreate Method
    
    // For Displaying Alert
    public void displayalert()
    {
    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
    	builder.setTitle("Alert");
    	builder.setMessage("There are no Reviews given to this Session");
   
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
    	           public void onClick(DialogInterface dialog, int id) 
    	           {
    	        	   Intent back_intent=new Intent(ViewReview.this,VideoActivity.class);
   					startActivity(back_intent);
    	           }
    	       });
    	builder.show();
    }
    
    /*
     * The Split function for splitting the string into array
     */
    
    public static String[] Split(String splitStr, String delimiter)
    {
     StringBuffer token = new StringBuffer();
     Vector tokens = new Vector();
     // split
     char[] chars = splitStr.toCharArray();
     for (int i=0; i < chars.length; i++) {
         if (delimiter.indexOf(chars[i]) != -1) {
             // we bumbed into a delimiter
             if (token.length() > 0)
             {
                tokens.addElement(token.toString());
                 //System.out.println("tokenks are"+token.toString());
                 token.setLength(0);
             }
         } else {
             token.append(chars[i]);
         }
     }

     String[] splitArray = new String[tokens.size()];
     for (int i=0; i < splitArray.length; i++) {
         splitArray[i] = tokens.elementAt(i).toString();
     }
     return splitArray;
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
	              Intent intent_obj1=new Intent(ViewReview.this,LoginActivity.class);
			      startActivity(intent_obj1);
			break;
		 default:
		      break;
		}
		return true;
	}
    
   
}  // Closing Class
