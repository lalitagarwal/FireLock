package layout.authentication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import ca.uwaterloo.crysp.R;

public class MainActivity_Pin extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

    public void btnClickHandler_imm_trans(View view){
        Intent transparent_scr= new Intent(this, Webview_Pin.class);
        transparent_scr.putExtra("category", "imm_trans");
        startActivity(transparent_scr);
    }

    public void btnClickHandler_imm_dark(View view){
        Intent transparent_scr= new Intent(this, Webview_Pin.class);
        transparent_scr.putExtra("category", "imm_dark");
        startActivity(transparent_scr);
    }

    public void btnClickHandler_del_dark_pin_start(View view){
        Intent transparent_scr= new Intent(this, Webview_Pin.class);
        transparent_scr.putExtra("category", "del_dark_pin_start");
        startActivity(transparent_scr);
    }

    public void btnClickHandler_del_dark_pin_end(View view){
        Intent transparent_scr= new Intent(this, Webview_Pin.class);
        transparent_scr.putExtra("category", "del_dark_pin_end");
        startActivity(transparent_scr);
    }

    public void btnClickHandler_del_dark_pin_middle(View view){
        Intent transparent_scr= new Intent(this, Webview_Pin.class);
        transparent_scr.putExtra("category", "del_dark_pin_middle");
        startActivity(transparent_scr);
    }
}
