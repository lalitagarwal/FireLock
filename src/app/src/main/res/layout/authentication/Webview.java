package layout.authentication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import ca.uwaterloo.crysp.R;
import haibison.android.lockpattern.LockPatternActivity;

public class Webview extends Activity {
	
	private WebView webView;
	private String category;
	private static final int REQ_ENTER_PATTERN = 2;
	public static Webview webActivity;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_webview);
		webActivity=this;
		Bundle extras= getIntent().getExtras();
        category= extras.getString("category");
		webView = (WebView) findViewById(R.id.webView1);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("https://en.wikipedia.org/wiki/Canada");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		return super.dispatchTouchEvent(ev);
	}

	public void btnNextHandler(View view){
		Intent intent = new Intent(PatternLock.ACTION_COMPARE_PATTERN, null, this, PatternLock.class);
		intent.putExtra(LockPatternActivity.EXTRA_PATTERN, MainActivity.savedPattern);
		intent.putExtra("category", category);
		startActivityForResult(intent, REQ_ENTER_PATTERN);

		//Intent pin_lock= new Intent(this, PinLock.class);
		//pin_lock.putExtra("category", category);
		//startActivity(pin_lock);
	}

	public void btnHomeHandler(View view){
		Intent home= new Intent(this, MainActivity.class);
		startActivity(home);
	}
}
