package com.example.apoorva.mortgagecalculator;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import android.app.AlertDialog ;
import android.content.DialogInterface;
import android.content.Context;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputMethodManager;
import android.view.inputmethod.EditorInfo;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.LinearLayout;
import android.view.View;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.TextView;


public class MainCalculator extends FragmentActivity {
    EditText loanamount;
    EditText homevalue ;
    EditText dwnpmnt ;
    EditText interest ;
    EditText propertytax ;
    Spinner staticSpinner ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_calculator);
        loanamount  = (EditText) findViewById(R.id.loan_amount);
        homevalue   = (EditText) findViewById(R.id.home_value);
        dwnpmnt = (EditText) findViewById(R.id.down_payment);
        interest = (EditText) findViewById(R.id.interest_rate);
        propertytax = (EditText) findViewById(R.id.property_tax);
        staticSpinner = (Spinner) findViewById(R.id.static_spinner);


        loanamount.setImeOptions(EditorInfo.IME_ACTION_DONE|EditorInfo.IME_FLAG_NO_EXTRACT_UI);
        homevalue.setImeOptions(EditorInfo.IME_ACTION_DONE|EditorInfo.IME_FLAG_NO_EXTRACT_UI);
        dwnpmnt.setImeOptions(EditorInfo.IME_ACTION_DONE|EditorInfo.IME_FLAG_NO_EXTRACT_UI);
        interest.setImeOptions(EditorInfo.IME_ACTION_DONE|EditorInfo.IME_FLAG_NO_EXTRACT_UI);
        propertytax.setImeOptions(EditorInfo.IME_ACTION_DONE|EditorInfo.IME_FLAG_NO_EXTRACT_UI);
        // Create an ArrayAdapter using the string array and a default spinner
        ArrayAdapter<CharSequence> staticAdapter = ArrayAdapter
                .createFromResource(this, R.array.term_array,
                        android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        staticAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        staticSpinner.setAdapter(staticAdapter);


    }

    public void selectFrag(View view) {
        Fragment fr;

        if(view == findViewById(R.id.button1)) {
          //  EditText homevalue = (EditText) findViewById(R.id.home_value);
            String homeval = homevalue.getText().toString();

          //  EditText loanamount = (EditText) findViewById(R.id.loan_amount);
            String loan = loanamount.getText().toString();

          //  EditText dwnpmnt = (EditText) findViewById(R.id.down_payment);
            String dwnpt = dwnpmnt.getText().toString();

         //   EditText interest = (EditText) findViewById(R.id.interest_rate);
            String rate = interest.getText().toString();

         //   EditText propertytax = (EditText) findViewById(R.id.property_tax);
            String propertyrate = propertytax.getText().toString();

            Spinner term = (Spinner) findViewById(R.id.static_spinner);
            String years = term.getSelectedItem().toString();


            if(homeval.isEmpty() || loan.isEmpty() || dwnpt.isEmpty() || rate.isEmpty() || propertyrate.isEmpty())
            {
                Context context = this;
                AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
                builder1.setMessage("The values are missing! Make sure you have entered all the values!");
                builder1.setCancelable(true);
                builder1.setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();
                Log.v("x:","empty home value");
            }
               else {
                Log.v("x", homeval);
                int hv = Integer.parseInt(homeval);


                int principal = Integer.parseInt(loan);
                Log.v("item", loan);


                int dp = Integer.parseInt(dwnpt);
                Log.v("item", dwnpt);


                float taxRate = Float.parseFloat(rate);
                float apr = taxRate / 100;
                float monthlyapr = apr / 12;
                Log.v("item", rate);


                float propertyTaxRate = Float.parseFloat(propertyrate);
                Log.v("item", propertyrate);


                int yr = Integer.parseInt(years);
                Log.v("item", years);

                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.YEAR, yr);
                //  Log.v("year", "" + cal.get(Calendar.YEAR));

                cal.add(Calendar.MONTH, -1);
                // Log.v("month",""+cal.get(Calendar.MONTH));

                SimpleDateFormat month_date = new SimpleDateFormat("MMMM");
                String month_name = month_date.format(cal.getTime());
                //  Log.v("MONTH:", "" + month_name);

                String payoff = String.format("" + month_name + " " + cal.get(Calendar.YEAR));
                Log.v("final date", payoff);

                //OUTPUT 3
                float totalPropertyTax = hv * propertyTaxRate * yr / 100;
                Log.d("TotalTax", "Total Property Tax: " + totalPropertyTax);

                float monthlyPropertyTax = totalPropertyTax / (yr * 12);
                Log.d("TotalTax", "Monthly Property Tax: " + monthlyPropertyTax);

                double val = Math.pow((double) 1 + monthlyapr, (double) yr * 12);
                double val1 = Math.pow((double) 1 + monthlyapr, (double) yr * 12);

                double monthlyMortgagePayment = principal * monthlyapr * val / (val1 - 1);
                Log.d("TotalTax", "Monthly Mortgage Payment: " + monthlyMortgagePayment);

                //OUTPUT 1
                double monthlyPayment = monthlyMortgagePayment + monthlyPropertyTax;
                Log.d("TotalTax", "Monthly Payment: " + monthlyPayment);


                //OUTPUT 2
                double totalInterestPaid = (monthlyPayment * yr * 12) - principal - totalPropertyTax;
                Log.d("TotalTax", "TOTAL interest paid: " + totalInterestPaid);


                Bundle bundle = new Bundle();
                bundle.putString("Total Property Tax", String.format("%.2f", totalPropertyTax));
                bundle.putString("Monthly Payment", String.format("%.2f", monthlyPayment));
                bundle.putString("TOTAL interest paid", String.format("%.2f", totalInterestPaid));
                bundle.putString("Payoff date", payoff);

                //set Fragmentclass Arguments
                fr = new FragmentTwo();
                fr.setArguments(bundle);
                android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
                android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_place, fr);
                transaction.addToBackStack(null);
                transaction.commit();
                //   TextView tv = (TextView) findViewById(R.id.total_tax_paid);
                //   tv.setText(homeval);

            }
        }else {
           // EditText homevalue = (EditText) findViewById(R.id.home_value);
            homevalue.setText("");

           // EditText loanamount = (EditText) findViewById(R.id.loan_amount);
            loanamount.setText("");

          //  EditText dwnpmnt = (EditText) findViewById(R.id.down_payment);
            dwnpmnt.setText("");

          //  EditText interest = (EditText) findViewById(R.id.interest_rate);
            interest.setText("");

           // EditText propertytax = (EditText) findViewById(R.id.property_tax);
            propertytax.setText("");

            Spinner term = (Spinner) findViewById(R.id.static_spinner);
            term.setSelection(0);
            fr = new FragmentOne();
            android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
            android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_place, fr);
            transaction.addToBackStack(null);
            transaction.commit();
        }




    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_calculator, menu);
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

    public void calculateMortgage (View view) {
       // Intent intent = new Intent(this, DisplayMessageActivity.class);
        EditText homevalue = (EditText) findViewById(R.id.home_value);
        String homeval = homevalue.getText().toString();
        int hv = Integer.parseInt(homeval);
       // Log.v("item", homeval);
      //  Log.d("val", "val " + hv);

        EditText loanamount = (EditText) findViewById(R.id.loan_amount);
        String loan = loanamount.getText().toString();
        int principal = Integer.parseInt(loan);
        Log.v("item", loan);

        EditText dwnpmnt = (EditText) findViewById(R.id.down_payment);
        String dwnpt = dwnpmnt.getText().toString();
        int dp = Integer.parseInt(dwnpt);
        Log.v("item", dwnpt);

        EditText interest = (EditText) findViewById(R.id.interest_rate);
        String rate = interest.getText().toString();
        float taxRate = Float.parseFloat(rate);
        float apr = taxRate/100;
        float monthlyapr = apr/12;
        Log.v("item", rate);

        EditText propertytax = (EditText) findViewById(R.id.property_tax);
        String propertyrate = propertytax.getText().toString();
        float propertyTaxRate = Float.parseFloat(propertyrate);
        Log.v("item", propertyrate);

        Spinner term = (Spinner) findViewById(R.id.static_spinner);
        String years = term.getSelectedItem().toString();
        int yr = Integer.parseInt(years);
        Log.v("item", years);

        //OUTPUT 3
        float totalPropertyTax = hv*propertyTaxRate*yr/100;
        Log.d("TotalTax", "Total Property Tax: " + totalPropertyTax);
       // DecimalFormat df = new DecimalFormat("#.##"); String twoDigitNum = df.format(myNum);
       // totalPropertyTax = String.format("%.2f", totalPropertyTax);

        float monthlyPropertyTax = totalPropertyTax/(yr*12);
        Log.d("TotalTax", "Monthly Property Tax: "+monthlyPropertyTax);

        double val = Math.pow((double)1+monthlyapr,(double)yr*12);
        double val1 = Math.pow((double)1+monthlyapr,(double)yr*12);

        double monthlyMortgagePayment = principal*monthlyapr*val/(val1-1);
        Log.d("TotalTax", "Monthly Mortgage Payment: "+monthlyMortgagePayment);

        //OUTPUT 1
        double monthlyPayment = monthlyMortgagePayment+monthlyPropertyTax;
        Log.d("TotalTax", "Monthly Payment: " + monthlyPayment);
       String s = String.format("%.2f", monthlyPayment);

        //OUTPUT 2
        double totalInterestPaid = (monthlyPayment*yr*12) - principal - totalPropertyTax;
        Log.d("TotalTax", "TOTAL interest paid: "+totalInterestPaid);



    }
}
