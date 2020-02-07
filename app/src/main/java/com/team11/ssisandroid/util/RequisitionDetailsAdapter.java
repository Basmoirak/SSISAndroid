package com.team11.ssisandroid.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.team11.ssisandroid.R;
import com.team11.ssisandroid.models.RequisitionDetail;

public class RequisitionDetailsAdapter extends RecyclerView.Adapter<RequisitionDetailsAdapter.RequisitionDetailsViewHolder> {
    private Context mContext;
    private RequisitionDetail[] mRequisitionDetailsArr;

    public RequisitionDetailsAdapter(Context mContext, RequisitionDetail[] mRequisitionDetailsArr){
        this.mContext = mContext;
        this.mRequisitionDetailsArr = mRequisitionDetailsArr;
    }

    // Create new view (invoked by layout manager)
    @NonNull
    @Override
    public RequisitionDetailsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.cardview_requisition_fragment_details, parent, false);
        final RequisitionDetailsViewHolder viewHolder = new RequisitionDetailsViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RequisitionDetailsViewHolder holder, int position) {

        holder.mTextViewItemCode.setText("ItemCode: " + mRequisitionDetailsArr[position].getItemCode());
        holder.mTextViewDescription.setText("Description: " + mRequisitionDetailsArr[position].getDescription());
        holder.mTextViewQuantity.setText("Quantity: " + mRequisitionDetailsArr[position].getQuantity());
    }

    @Override
    public int getItemCount() {
        if(mRequisitionDetailsArr.length > 0){
            return mRequisitionDetailsArr.length;
        }else{
            return 0;
        }
    }

    // Inner class to hold reference to each item of Recycler View
    public class RequisitionDetailsViewHolder extends RecyclerView.ViewHolder{
        private TextView mTextViewItemCode;
        private TextView mTextViewDescription;
        private TextView mTextViewQuantity;

        public RequisitionDetailsViewHolder(View itemView) {
            super(itemView);

            mTextViewItemCode = itemView.findViewById(R.id.requisitionDetailItemCode);
            mTextViewDescription = itemView.findViewById(R.id.requisitionDetailDescription);
            mTextViewQuantity = itemView.findViewById(R.id.requisitionDetailQuantity);
        }

    }
}
