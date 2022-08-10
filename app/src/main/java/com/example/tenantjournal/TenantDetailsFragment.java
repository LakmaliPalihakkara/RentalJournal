package com.example.tenantjournal;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

import android.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.tenantjournal.Model.Tenant;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class TenantDetailsFragment extends Fragment {

    TextView tvTenantIdNumberValue, tvTenantFullNameValue, tvOccupationValue, tvPhoneNumberValue, tvMoveInValue,
            tvMoveOutValue, tvTotalRentalFeePaidValue;

    int position;
    String name;
    private ArrayList<Tenant> tenantArrayList = new ArrayList<Tenant>();
    Button btPersonalInfoEdit, btClose;

    public TenantDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_tenant_details, container, false);

        tvTenantIdNumberValue = (TextView) view.findViewById(R.id.tv_tenant_id_number_value);
        tvTenantFullNameValue = (TextView) view.findViewById(R.id.tv_tenant_full_name_value);
        tvOccupationValue = (TextView) view.findViewById(R.id.tv_occupation_value);
        tvPhoneNumberValue = (TextView) view.findViewById(R.id.tv_phone_number_value);
        tvMoveInValue = (TextView) view.findViewById(R.id.tv_move_in_value);
        tvMoveOutValue = (TextView) view.findViewById(R.id.tv_move_out_value);
        tvTotalRentalFeePaidValue = (TextView) view.findViewById(R.id.tv_total_rental_fee_paid_value);
//        tvDepositIncludedValue = (TextView) view.findViewById(R.id.tv_deposit_included_value);
//        tvRemainingRentalFeeValue = (TextView) view.findViewById(R.id.tv_remaining_rental_fee_value);
//        tvRemainingDamagePaymentValue = (TextView) view.findViewById(R.id.tv_remaining_damage_payment_value);
//        tvOutstandingBalanceValue = (TextView) view.findViewById(R.id.tv_outstanding_balance);

        btPersonalInfoEdit = (Button) view.findViewById(R.id.bt_personal_info_edit);
        btClose = (Button) view.findViewById(R.id.bt_close);

        final Bundle bundle = getArguments();
        if (bundle != null) {
            position = bundle.getInt("position");
            tenantArrayList = bundle.getParcelableArrayList("tenant");
            name = bundle.getString("name");



            for (int i = 0; i < tenantArrayList.size(); i++) {
                if (i == position) {
                    tvTenantIdNumberValue.setText(tenantArrayList.get(i).getPassport());
                    tvTenantFullNameValue.setText(tenantArrayList.get(i).getFullName());
                    tvOccupationValue.setText(tenantArrayList.get(i).getProfession());
                    tvPhoneNumberValue.setText(tenantArrayList.get(i).getPhoneNumber());
                    tvMoveInValue.setText(tenantArrayList.get(i).getCheckInDate());
                    tvMoveOutValue.setText(tenantArrayList.get(i).getCheckOutDate());
                    tvTotalRentalFeePaidValue.setText(tenantArrayList.get(i).getDepositPaid());
                //    tvDepositIncludedValue.setText(tenantArrayList.get(i).getDepositPaid());
                    // tvRemainingRentalFeeValue.setText(tenantArrayList.get(i).getFullName());
                    // tvRemainingDamagePaymentValue.setText(tenantArrayList.get(i).getFullName());
                    //tvOutstandingBalanceValue.setText(tenantArrayList.get(i).getFullName());
                }
            }
        }

        btPersonalInfoEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putInt("position",position);
                bundle.putParcelableArrayList("tenant",tenantArrayList);
                bundle.putBoolean("edit",true);

                System.out.println("position"+position);

                Fragment fr = new AddNewTenantFragment();
                FragmentManager fm = getFragmentManager();
                fr.setArguments(bundle);
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.container, fr);
                fragmentTransaction.commit();
            }
        });

        btClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("name", name);

                Fragment fr = new TenantsInformationFragment();
                FragmentManager fm = getFragmentManager();
                fr.setArguments(bundle);
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.container, fr);
                fragmentTransaction.commit();
            }
        });

        return view;
    }

}
