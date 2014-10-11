package com.example.auvict.tipcalculator;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.NumberFormat;


public class Main extends Activity {

    private static final String TAG = Main.class.getSimpleName();

    //currency and percent formatters
    private static final NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
    private static final NumberFormat percentFormat = NumberFormat.getPercentInstance();

    private double billAmount = 0.0;  //bill amount entered by the user
    private double customPercent = 0.18;  //intial custom tip percentage
    private TextView amountDisplayTextView;   //shows formatted bill amount
    private TextView percentCustomTextView; //shows custom tip percetnage
    private TextView tip15TextView; //shows 15% tip
    private TextView total15TextView; //shows total with 15% tip
    private TextView tipCustomTextView; //shows custom tip amount
    private TextView totalCustomTextView; //shows total with custom tip


    //can't be more than 5 seconds or it freaks out
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);

        Log.d(TAG, "starting");

        //get references to the textViews
        //that main interacts wiht programatically

        amountDisplayTextView = (TextView) findViewById(R.id.amountDisplayTextView);
        percentCustomTextView = (TextView) findViewById(R.id.percentCustomTextView);
        tip15TextView = (TextView) findViewById(R.id.tip15TextView);
        total15TextView = (TextView) findViewById(R.id.total15TextView);
        tipCustomTextView = (TextView) findViewById(R.id.tipCustomTextView);
        totalCustomTextView = (TextView) findViewById(R.id.totalCustomTextView);

        //update GUI baed on billAmount and customPercent
        amountDisplayTextView.setText(currencyFormat.format(billAmount));
        updateStandard();
        updateCustom();

        EditText amountEditText = (EditText) findViewById(R.id.amountEditText);
        amountEditText.addTextChangedListener(amountEditTextWatcher);

        SeekBar customTipSeekBar = (SeekBar) findViewById(R.id.customTipSeekBar);
        customTipSeekBar.setOnSeekBarChangeListener(customSeekBarListener);
    }

    private void updateStandard()
    {
        double fifteenPercentTip = billAmount *0.15;
        double fifteenPercentTotal = billAmount + fifteenPercentTip;

        tip15TextView.setText(currencyFormat.format(fifteenPercentTip));
        totalCustomTextView.setText(currencyFormat.format(fifteenPercentTotal));
    }

    private void updateCustom()
    {
        percentCustomTextView.setText(percentFormat.format(customPercent));

        double customTip = billAmount * customPercent;
        double customTotal = billAmount * customTip;

        tipCustomTextView.setText(currencyFormat.format(customTip));
        totalCustomTextView.setText(currencyFormat.format(customTotal));
    }

    private TextWatcher amountEditTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            try{
                Log.d(TAG, "something");
                billAmount = Double.parseDouble(s.toString());
            }
            catch(NumberFormatException e){
                billAmount = 0.0;
            }

            amountDisplayTextView.setText(currencyFormat.format(billAmount));
            updateStandard();
            updateCustom();
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    private SeekBar.OnSeekBarChangeListener customSeekBarListener = new SeekBar.OnSeekBarChangeListener(){

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            customPercent = progress/100.0;
            updateCustom();
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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
}
