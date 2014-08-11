/*
 *  This Activity is used to send the registration Details to server 
 *  and stores the registration details in database
 */

package mobile.learning;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.util.List;



public class RegisterActivity extends Activity {
	BufferedReader regdata;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.registerfile);
		
		/*
         *   getting references of Button "Register" 
         */
		Button bregister=(Button) findViewById(R.id.bregister);
		bregister.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) 
			{
				/* getting references of edittext and 
				placing the information present in edittext into string
				*/
				
				EditText uname=(EditText) findViewById(R.id.etusername1);
		        EditText pwd=(EditText) findViewById(R.id.etpwd);
		        EditText email=(EditText) findViewById(R.id.etemail);
				String uname1=uname.getText().toString();
		        String pwd1=pwd.getText().toString();
		        String email1=email.getText().toString();
		       
		                if((uname1.length()<1)||(pwd1.length()<1)||(email1.length()<1))
		                {
		                 String str1="Please enter username and password";      	
		                	Registeralert(str1);
		                }
		                else
		                {
		                	
		                	 /*
		 				    * Connecting to the server and will send the user registration details to server
		 				    */
		                		HttpClient client = new DefaultHttpClient();
		 			  
		                		 /*
		             	        *  If the server is in your system then replace the IPAaddress with your system IpAddress
		             	        */
		        HttpPost post = new HttpPost("http://10.11.32.59:8080/MlearningServerApp/RegistrationServlet");
		               
		 			      
		 			      List<NameValuePair> pairs = new ArrayList<NameValuePair>();
		 			      pairs.add(new BasicNameValuePair("username", uname1));
		 			     pairs.add(new BasicNameValuePair("password", pwd1));
		 			    pairs.add(new BasicNameValuePair("email", email1));
		 			      try {
		 						post.setEntity(new UrlEncodedFormEntity(pairs));
		 						HttpResponse response = client.execute(post);
		 					/*
		 					 *  Receiving the response from Servlet
		 					 */
		 						BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()), 4096);
		 						String line;
		 						StringBuilder sb =  new StringBuilder();
		 						while ((line = rd.readLine()) != null) {
		 								sb.append(line);
		 						}
		 						rd.close();
		 						String s = sb.toString();
		 						/*
		 						 * If the response from the servlet is "Registered" then
		 						 */					
		 						if(s.equals("Registered"))
		 						{
		 						Toast.makeText(RegisterActivity.this, "Registration success: "  , Toast.LENGTH_SHORT).show();
		 						System.out.append(s);
		 						}
		 						/*
		 						 * If the response from the servlet is "UserId Exists" then
		 						 */	
		 						else if(s.equals("UserId Exists"))
		 						{
		 					Toast.makeText(RegisterActivity.this, "Username Already Exists ",
		 									Toast.LENGTH_SHORT).show();
		 					String str2="Username Already Exists!.... try using another name";
		 					Registeralert(str2);
		 						}
		 				}
		 			      catch(Exception e)
		 			      {
		 			    	  e.printStackTrace();
		 			      }
					}
			}
					});
		// Reference For Cancel Button
		Button bcancel=(Button) findViewById(R.id.bcancel);
		bcancel.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v)
			{
				Intent i=new Intent(RegisterActivity.this,LoginActivity.class);
				startActivity(i);
			}
		});
	}  // Closing on Create Method
	
	// Alert if registration details are empty
	public void Registeralert(String message)
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
}
