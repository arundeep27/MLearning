/*
 *  This Activity is used for Displaying The Reviews ina ListView
 */

package mobile.learning;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;


public class MultiColumnListView extends Activity 
{
    /** Called when the activity is first created. */

	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Bundle extradata =getIntent().getExtras();
        String[] Review_username=extradata.getStringArray("username_array");
        String[] Review_comment=extradata.getStringArray("comment_array");
        String[] Review_rating=extradata.getStringArray("rating_array");
        
        ListView listView = (ListView)findViewById(R.id.list);
        
       
        listView.setAdapter(new myListAdapter(Review_username,Review_comment,Review_rating,this));
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE); 
        
        // back button reference
        
        Button backvideo =(Button)findViewById(R.id.reviewback);
        backvideo.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) 
			{
	Intent back_intent=new Intent(MultiColumnListView.this,VideoActivity.class);
	startActivity(back_intent);
			}
		});
        
        // GoHome Button reference
        
        Button homebtn=(Button)findViewById(R.id.reviewhome);
        homebtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) 
			{
				Intent home_intent=new Intent(MultiColumnListView.this,Course.class);
				startActivity(home_intent);	
			}
		});
    }
    
	

	
    private class myListAdapter extends BaseAdapter{
    	
    	private String[] review_uname;
    	private String[] review_comment;
    	private String[] review_rating;
    	private LayoutInflater mInflater;
    	
    	
		//public myListAdapter(ArrayList<HashMap<String, Object>> books, Context context){
    	public myListAdapter(String[] unames,String[] comments,String[] ratings, Context context){
			
			
			review_uname = unames;
			review_comment=comments;
			review_rating=ratings;
			mInflater = LayoutInflater.from(context);
		}
    	
    	
    	@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return review_uname.length;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			//return Books.get(position);
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) 
		{
			// TODO Auto-generated method stub
			// A ViewHolder keeps references to children views to avoid unneccessary calls
            // to findViewById() on each row.
			ViewHolder holder;
			// When convertView is not null, we can reuse it directly, there is no need
            // to reinflate it. We only inflate a new View when the convertView supplied
            // by ListView is null
			 if (convertView == null) {
	             convertView = mInflater.inflate(R.layout.listbox, null);
	             // Creates a ViewHolder and store references to the two children views
	             // we want to bind data to.
	             holder = new ViewHolder();
	             holder.v = (TextView) convertView.findViewById(R.id.text1);
	             holder.v1 = (TextView) convertView.findViewById(R.id.text2);
	             holder.rating = (RatingBar)convertView.findViewById(R.id.star);
	             convertView.setTag(holder);
	                
			 }else {
				 // Get the ViewHolder back to get fast access to the TextView
	             // and the ImageView.
				 holder = (ViewHolder) convertView.getTag(); 
			 }
			 	// Bind the data with the holder.
			 
			
			 holder.v.setText(review_uname[position]);
			 
			 holder.v1.setText(review_comment[position]);
			 Float rating_value = Float.valueOf(review_rating[position].trim()).floatValue();	
				
			 holder.rating.setRating(rating_value);
				
				return convertView;
		}
		
		
    	
    }
    static class ViewHolder {
		TextView v;
    	TextView v1;
    	RatingBar rating;
    }
	
}