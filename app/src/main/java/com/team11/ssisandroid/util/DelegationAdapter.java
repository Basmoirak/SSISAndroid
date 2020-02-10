package com.team11.ssisandroid.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

    public DelegationAdapter(Context mContext, Delegation[] delegations){
        this.mContext = mContext;
        this.delegations = delegations;
    }

    // Create new view
    @NonNull
    @Override
    public DelegationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.cardview_delegation_fragment, parent, false);
        final DelegationViewHolder viewHolder = new DelegationViewHolder(view);

        viewHolder.delegation_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "Clicked" + delegations[viewHolder.getAdapterPosition()].getDepartmentName(), Toast.LENGTH_SHORT).show();
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull DelegationViewHolder holder, int position) {

        holder.mUserName.setText(delegations[position].getEmail());
        holder.mDepartmentName.setText(delegations[position].getDepartmentName());
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
