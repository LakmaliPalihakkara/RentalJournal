package com.example.tenantjournal;


import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;


/**
 * A simple {@link Fragment} subclass.
 */
public class TenantsInformationFragment extends Fragment {

    private ListView lvSearch;
    private EditText etSearch;
    private ArrayAdapter<String> searchAdapter;

    String data[]={"Anne", "Mark", "Sara"};

    public TenantsInformationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.fragment_tenants_information, container, false);

        lvSearch = (ListView) rootView.findViewById(R.id.lv_search);
        etSearch = (EditText) rootView.findViewById(R.id.et_search);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            searchAdapter = new ArrayAdapter<String>(getContext(), R.layout.search_list_item, R.id.textview,data);
        }
        lvSearch.setAdapter(searchAdapter);

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                TenantsInformationFragment.this.searchAdapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        return rootView;
    }

}
