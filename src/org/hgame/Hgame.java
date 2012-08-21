package org.hgame;

import com.htris.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Hgame extends Activity {
	public final static String EXTRA_MESSAGE = "ru.dvatris.MESSAGE";
    
	private Button mBtnStart;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	//TODO  Why wrong output for all TableRow android:layout_weight="1"
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBtnStart = (Button) findViewById(R.id.buttom_start);
    }
    
    public void onLeft(View view) {
    	
    }
    
    public void onStart(View view) {
    	mBtnStart.setVisibility(View.GONE);
    }

}
