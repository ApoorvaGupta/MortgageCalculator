package com.example.apoorva.mortgagecalculator;

import android.support.v4.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
/**
 * Created by apoorva on 9/21/15.
 */
public class FragmentTwo extends  Fragment {

   // @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View myInflatedView = inflater.inflate(
                R.layout.fragment_two, container, false);

        // Set the Text to try this out
        TextView t = (TextView) myInflatedView.findViewById(R.id.total_tax_paid);
        String totPropTax=getArguments().getString("Total Property Tax");
        t.setText(totPropTax);

        TextView t1 = (TextView) myInflatedView.findViewById(R.id.monthly_payment);
        String monthlyPayment=getArguments().getString("Monthly Payment");
        t1.setText(monthlyPayment);

        TextView t2 = (TextView) myInflatedView.findViewById(R.id.total_interest_paid);
        String totInterestPaid=getArguments().getString("TOTAL interest paid");
        t2.setText(totInterestPaid);

        TextView t3 = (TextView) myInflatedView.findViewById(R.id.pay_off_date);
        String date = getArguments().getString("Payoff date");
        t3.setText(date);

       // return myInflatedView;
        // Inflate the layout for this fragment
//        String strtext=getArguments().getString("name");
//        TextView tv = (TextView) getView().findViewById(R.id.total_tax_paid);
//        tv.setText(strtext);
       // FragmentTwo.tv
       // TextView tv = (TextView) findViewById(R.id.total_tax_paid);
      //  tv.showT(strtext);
       // return inflater.inflate(R.layout.fragment, container, false);
        return myInflatedView;
    }
}
