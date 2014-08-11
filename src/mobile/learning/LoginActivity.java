/*
 * This Activity is used to display the login Page 
 * and check the authenticity of the user by connecting to the server 
 */

package mobile.learning;

import java.io.BufferedReader;
import java.io.InputStreamReader;
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
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity 
{
 public SharedPreferences sp_obj;
 public Editor editor_obj;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginfile);
       
        /*
         *   getting references of Button "Login" 
         */
        Button b=(Button) findViewById(R.id.Button01);
        b.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) 
			{
				/* getting references of edittext and 
				placing the information present in edittext into string
				*/
				 EditText useredit=(EditText)findViewById(R.id.uname);
			       EditText passedit=(EditText)findViewById(R.id.upass);
			       String username=useredit.getEditableText().toString();
			       String password=passedit.getEditableText().toString();
// if username and password fields are empty displaying alert
				 if((username.length()<1) ||(password.length()<1))
				    {
					 String str1="Please enter username and password";
					      displayalert(str1);
				    }
				 else
				 {
		
				   /*
				    * Connecting to the server and will check the authenticity of the user
				    */
		        
		        	  HttpClient client = new DefaultHttpClient();
		        /*
		        *  If the server is in your system then replace the IPAaddress with your system IpAddress
		        */
			    HttpPost post = new HttpPost("http://10.11.32.59:8080/MlearningServerApp/LoginServlet");
		        
				      
				      List<NameValuePair> pairs = new ArrayList<NameValuePair>();
				      pairs.add(new BasicNameValuePair("username", username));
				      pairs.add(new BasicNameValuePair("password", password));
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
				 * If the response from the server is Login Success then
				 */
							if(s.equals("Login Success"))
							{
			             
							sp_obj=getSharedPreferences("mlearninddata",MODE_WORLD_WRITEABLE);
							editor_obj=sp_obj.edit();
							editor_obj.putString("username",username);
							editor_obj.commit();
							Toast.makeText(LoginActivity.this, "valid user: "  , Toast.LENGTH_SHORT).show();
							System.out.append(s);
						
							Intent intent1=new Intent(v.getContext(), Course.class);
							startActivity(intent1);
							
							}
							/*
							 * If the response from the server is Login Fail then
							 */
							else if(s.equals("Login Fail"))
							{
								String str2="Invalid username or password!.......";
								displayalert(str2);
								//Toast.makeText(LoginActivity.this, "Invalid user: "  , Toast.LENGTH_SHORT).show();
							}
		           
		        } 
		    catch (Exception e) 
		        {
		          e.printStackTrace();
		        } 
				 
	      } // else close 
     	}  // method close
		});
        
        /*
         *   getting references of Button "Registration" 
         */
        Button bregister=(Button) findViewById(R.id.bregister);
        bregister.setOnClickListener(new View.OnClickListener() 
        {
			
			@Override
			public void onClick(View v) 
			{
			Intent i=new Intent(LoginActivity.this,RegisterActivity.class);
			startActivity(i);
			}
		});
    
    } // Closing On create Method
    
 // Alert if Login details are empty
    public void displayalert(String displaymessage)
    {
    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
    	builder.setTitle("Alert");
    	builder.setMessage(displaymessage);
   
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
    	           public void onClick(DialogInterface dialog, int id) {
    	             
    	           }
    	       });
    	builder.show();
    }
}// Main Method