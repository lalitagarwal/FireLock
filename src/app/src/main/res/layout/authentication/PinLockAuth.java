package layout.authentication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Vibrator;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import ca.uwaterloo.crysp.R;
import ca.uwaterloo.crysp.storage.Storage;

public class PinLockAuth extends Activity {
    private View bgview;
    EditText userinput;
    Vibrator vibe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin_lock_auth);

        TextView tv = (TextView) findViewById(R.id.inputActivityInfo);
        tv.setText("Please select a 4-digit PIN");
        bgview = findViewById(R.id.pin_lock);
        bgview.setBackgroundColor(Color.parseColor("#50000000"));

        vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        userinput = (EditText) bgview.findViewById(R.id.userInput);
        userinput.setInputType(InputType.TYPE_NULL);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_pin_lock_auth, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void btnClickHandler(View v) {
        switch (v.getId()) {
            case R.id.numlock_b0:
                addtoarray("0");
                break;
            case R.id.numlock_b1:
                addtoarray("1");
                break;
            case R.id.numlock_b2:
                addtoarray("2");
                break;
            case R.id.numlock_b3:
                addtoarray("3");
                break;
            case R.id.numlock_b4:
                addtoarray("4");
                break;
            case R.id.numlock_b5:
                addtoarray("5");
                break;
            case R.id.numlock_b6:
                addtoarray("6");
                break;
            case R.id.numlock_b7:
                addtoarray("7");
                break;
            case R.id.numlock_b8:
                addtoarray("8");
                break;
            case R.id.numlock_b9:
                addtoarray("9");
                break;
            case R.id.numlock_bAccept:
                check_pin();
                break;
            case R.id.numlock_bDelete:
                vibe.vibrate(28);
                userinput = (EditText) bgview.findViewById(R.id.userInput);
                int slength = userinput.length();
                if (slength > 0) {
                    //get the last character of the input
                    String selection = userinput.getText().toString().substring(slength - 1, slength);
                    String result = userinput.getText().toString().replace(selection, "");
                    userinput.setText(result);
                    userinput.setSelection(userinput.getText().length());
                }
        }
    }

    public void addtoarray(String n) {
        vibe.vibrate(28);
        userinput = (EditText) bgview.findViewById(R.id.userInput);
        int slength = userinput.length();
        if (slength < 4) {
            userinput.append(n);
        }
    }

    public void check_pin() {
        vibe.vibrate(28);
        EditText pin_input = (EditText) bgview.findViewById(R.id.userInput);
        String pin = pin_input.getText().toString();
        if (pin.length() == 4) {
            bgview.setClickable(false);
            bgview.setVisibility(View.GONE);
            pin_input.setText("");
            Storage.Log("Lock", "PIN Selected");

            Intent auth2 = new Intent(this, PinLockAuth2.class);
            auth2.putExtra("PIN", pin);
            startActivity(auth2);
            finish();
        } else {
            Context context = bgview.getContext();
            CharSequence text = "Please enter a PIN consisting of 4 digits";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            pin_input.setText("");
        }
    }
}
