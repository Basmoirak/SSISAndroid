package com.team11.ssisandroid.util;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.team11.ssisandroid.R;
import com.team11.ssisandroid.models.Delegation;

public class DelegationAdapter extends RecyclerView.Adapter<DelegationAdapter.DelegationViewHolder>{

    private Context mContext;
    private Delegation[] delegations;
    private Dialog myDialog;

    TextView dialogUserName;
    EditText dialogStartDate;
    EditText dialogEndDate;
    Button dialogConfirmBtn;
    Button dialogCancelBtn;

    public DelegationAdapter(Context mContext, Delegation[] delegations){
        this.mContext = mContext;
        this.delegations = delegations;
    }

    // Create new view
    @NonNull
    @Override
    public DelegationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        // ini Dialog
        myDialog = new Dialog(mContext);
        myDialog.setContentView(R.layout.dialog_delegation);
        dialogUserName = myDialog.findViewById(R.id.dialog_item_delegation_user);
        dialogStartDate = myDialog.findViewById(R.id.dialog_delegation_start_date);
        dialogEndDate = myDialog.findViewById(R.id.dialog_delegation_end_date);
        dialogConfirmBtn = myDialog.findViewById(R.id.dialog_delegation_confirm_button);
        dialogCancelBtn = myDialog.findViewById(R.id.dialog_delegation_cancel_button);

        View view = LayoutInflater.from(mContext).inflate(R.layout.cardview_delegation_fragment, parent, false);
        final DelegationViewHolder viewHolder = new DelegationViewHolder(view);



        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull DelegationViewHolder holder, final int position) {

        holder.mUserName.setText(delegations[position].getEmail());
        holder.mDepartmentName.setText(delegations[position].getDepartmentName());

        holder.delegation_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogStartDate.setText("");
                dialogEndDate.setText("");
                myDialog.show();

                dialogConfirmBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(mContext, dialogStartDate.getText(), Toast.LENGTH_SHORT).show();
                        myDialog.dismiss();
                    }
                });

                dialogCancelBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        myDialog.dismiss();
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        if(delegations.length > 0){
            return delegations.length;
        } else {
            return 0;
            }
    }

    // Inner class to hold reference to each item of Recycler View
    public class DelegationViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout delegation_item;
        private TextView mUserName;
        private TextView mDepartmentName;

        public DelegationViewHolder(@NonNull View itemView) {
            super(itemView);
            // Get entire cardview_delegation_fragment layout
            delegation_item = itemView.findViewById(R.id.delegation_user_item);

            mUserName = itemView.findViewById(R.id.delegationViewEmployee);
            mDepartmentName = itemView.findViewById(R.id.delegationViewDepartment);

        }
    }
}
