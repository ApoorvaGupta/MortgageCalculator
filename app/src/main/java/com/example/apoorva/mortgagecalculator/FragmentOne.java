package com.example.apoorva.mortgagecalculator;
import android.support.v4.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
/**
 * Created by apoorva on 9/21/15.
 */
public class FragmentOne extends Fragment  {


        @Override
        public View onCreateView(LayoutInflater inflater,
                                 ViewGroup container, Bundle savedInstanceState) {

            //Inflate the layout for this fragment

            return inflater.inflate(
                    R.layout.fragment_one, container, false);
        }

}
