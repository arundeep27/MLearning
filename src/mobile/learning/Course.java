/*
  This Activity is used for Displaying the Course List and when clicked on any session in the list 
  will navigate to Video Class with the Session URL as Request
 */

package mobile.learning;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;


public class Course extends Activity implements OnItemClickListener
{	
	 public SharedPreferences sp_obj;
	 public Editor editor_obj;
	 
	String listinfo[]={"Android Session","Flex Session","Java Session","Dotnet Session"};
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
	   super.onCreate(savedInstanceState);
	   setContentView(R.layout.coursefile);
	   /*
		 * Getting the path of Shared Preference XML File
		 */
	    sp_obj=getSharedPreferences("mlearninddata",MODE_WORLD_WRITEABLE);
		editor_obj=sp_obj.edit();
	   /*
	    * Getting reference for listView
	    */
	   
	   ListView courseList=(ListView)findViewById(R.id.courseView);
	   courseList.setAdapter(new ArrayAdapter(Course.this,android.R.layout.simple_list_item_1,listinfo));
	   /*
	    *  Setting Item Click Listener on ListView
	    */
       courseList.setOnItemClickListener(this);	
       
	}// Closing onCreate Method
	
	
	/*
	 * This method is invoked when any item in the list is selected
	and it identifies the selected session with the help of Position
	 */
	
	@Override
	public void onItemClick(AdapterView<?> arg0, View v, int position, long arg3) 
	{
		   String session_name=((TextView) v).getText().toString();
		   editor_obj.putString("sessionname",session_name);
		   editor_obj.commit();
		switch (position)
		{
		case 0:
			 /*
			  * Storing SessionUrl in Shared Preference XML File
			  */
			   String session_Url1="http://10.11.32.59/Topics/Java-Hello.mp4";
			   editor_obj.putString("sessionUrl",session_Url1);
			   editor_obj.commit();
			   Intent intent_obj1=new Intent(Course.this,VideoActivity.class);
			   startActivity(intent_obj1);
			break;
			
		case 1:
			/*
			  * Storing SessionUrl in Shared Preference XML File
			  */
			String session_Url2="http://10.11.32.59/Topics/Java-If-Statement.mp4";
			   editor_obj.putString("sessionUrl",session_Url2);
			   editor_obj.commit();
			   Intent intent_obj2=new Intent(Course.this,VideoActivity.class);
			   startActivity(intent_obj2);
			break;
			
		case 2:
			/*
			  * Storing SessionUrl in Shared Preference XML File
			  */
			String session_Url3="http://10.11.32.59/Topics/Running-a-Java-Program.mp4";
			   editor_obj.putString("sessionUrl",session_Url3);
			   editor_obj.commit();
			   Intent intent_obj3=new Intent(Course.this,VideoActivity.class);
			   startActivity(intent_obj3);
			break;
			
		case 3:
			/*
			  * Storing SessionUrl in Shared Preference XML File
			  */
			String session_Url4="http://10.11.32.59/Topics/Java-Constructors.mp4";
			   editor_obj.putString("sessionUrl",session_Url4);
			   editor_obj.commit();
			   Intent intent_obj4=new Intent(Course.this,VideoActivity.class);
			   startActivity(intent_obj4);
			break;
			
	    default:
		    break;
		} // Closing Switch Case
		
	} // Closing ItemClick
            
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
	              Intent intent_obj1=new Intent(Course.this,LoginActivity.class);
			      startActivity(intent_obj1);
			break;
		 default:
		      break;
		}
		return true;
	}
}// Closing Class
