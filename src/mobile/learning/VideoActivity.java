/*
 *  This Activity is used to fetch SessionUrl from Shared Preference XML File
 *  and gives that URL to Video View Session
 */

package mobile.learning;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

public class VideoActivity extends Activity 
{
	public SharedPreferences sp_obj;
	private VideoView mvideoview;
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.videodisplay);
		/*
		 * Getting the path of Shared Preference XML File
		 */
		sp_obj=getSharedPreferences("mlearninddata",MODE_WORLD_WRITEABLE);
		TextView vtext=(TextView)findViewById(R.id.videoActivityText);
		/*
		 *  Retrieving the SessionName from Shared Preference XML File
		 */
		String sname="You are listening to the "+sp_obj.getString("sessionname","no url");
		vtext.setText(sname);
		vtext.setTextSize(25);
		/*
		 *  Retrieving the SessionURL from Shared Preference XML File
		 */
		String Sessionpath=sp_obj.getString("sessionUrl","no url");
     mvideoview=(VideoView)findViewById(R.id.surface_view);
  /*
   * If the URL Path is NULL then
   */
     if(Sessionpath=="")
	    {
	      Toast.makeText(VideoActivity.this,
	         "There is no path given",Toast.LENGTH_LONG).show();
	    }
     /*
      *  Plays Video
      */
    else
	    {
	      Toast.makeText(VideoActivity.this,
	         "Buffering the video to Play",Toast.LENGTH_LONG).show();
	       mvideoview.setVideoURI(Uri.parse(Sessionpath));
	       mvideoview.setMediaController(new MediaController(this));
	       mvideoview.requestFocus();
	    }
		
	}// Closing onCreate Method
	
  /*
   *  Creating Options Menu For Adding " Review,Rating" and
   *  For Viewing Given "Review,Rating "
   *  This Method is used for Displaying Menu options 
   *  when clicked on Menu key in android Device
   */
	
	public boolean onCreateOptionsMenu(Menu menu_obj)
	{
		menu_obj.add(0, 1,0,"Give Review");
		menu_obj.add(0, 2,0,"View Review");
		menu_obj.add(0,3,0,"Go Back");
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
			 
	              Intent intent_obj1=new Intent(VideoActivity.this,WriteReview.class);
			      startActivity(intent_obj1);
			break;
		case 2:
			      Intent intent_obj2=new Intent(VideoActivity.this,ViewReview.class);
		          startActivity(intent_obj2);
		    break;
		case 3:
			Intent intent_obj3=new Intent(VideoActivity.this,Course.class);
	          startActivity(intent_obj3);
			
	    default:
		      break;
		}
		return true;
	
	}
	
	
} // Closing Class
