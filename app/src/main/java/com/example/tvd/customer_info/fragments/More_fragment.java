package com.example.tvd.customer_info.fragments;



import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.tvd.customer_info.Location;
import com.example.tvd.customer_info.R;

public class More_fragment extends Fragment {

    RelativeLayout language,location;
    AlertDialog alertDialog1;
    CharSequence[] values = {"English","Kannada"};
    public More_fragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_more_fragment, container, false);
        language = (RelativeLayout) view.findViewById(R.id.relative_language);
        location = (RelativeLayout) view.findViewById(R.id.relative_location);

        language.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateAlertDialogWithRadioButtonGroup() ;
            }
        });
        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Location.class);
                startActivity(intent);
            }
        });
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_more,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
        public void CreateAlertDialogWithRadioButtonGroup(){


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("Select Your Choice");

        builder.setSingleChoiceItems(values, -1, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int item) {

                switch(item)
                {
                    case 0:
                        Toast.makeText(getActivity(), "English Clicked", Toast.LENGTH_LONG).show();
                        break;
                    case 1:
                        Toast.makeText(getActivity(), "Kannada Clicked", Toast.LENGTH_LONG).show();
                        break;
                }
                alertDialog1.dismiss();
            }
        });
        alertDialog1 = builder.create();
        alertDialog1.show();
    }
}
