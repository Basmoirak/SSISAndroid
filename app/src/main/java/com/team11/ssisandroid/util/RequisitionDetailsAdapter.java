package com.team11.ssisandroid.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.team11.ssisandroid.R;
import com.team11.ssisandroid.fragments.RequisitionFragment;
import com.team11.ssisandroid.interfaces.RequisitionApi;
import com.team11.ssisandroid.models.Requisition;
import com.team11.ssisandroid.models.RequisitionDetail;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RequisitionDetailsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_BUTTON = 0;
    private static final int TYPE_ITEM = 1;

    private Context mContext;
    private RequisitionDetail[] mRequisitionDetailsArr;
    private String requisitionId;
    private String token;
    private String role;
    private String departmentId;

    public RequisitionDetailsAdapter(Context mContext, RequisitionDetail[] mRequisitionDetailsArr, String role, String requisitionId, String departmentId, String token){
        this.mContext = mContext;
        this.mRequisitionDetailsArr = mRequisitionDetailsArr;
        this.role = role;
        this.requisitionId = requisitionId;
        this.departmentId = departmentId;
        this.token = token;
    }

    // Create new view (invoked by layout manager)
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType == TYPE_ITEM) {
            // Here Inflating your recyclerview item layout
            View itemView = LayoutInflater.from(mContext).inflate(R.layout.cardview_requisition_fragment_details, parent, false);
            return new RequisitionDetailsViewHolder(itemView);
        }

        if(role.contains("DepartmentHead")){
            if(viewType == TYPE_BUTTON){
                //Here Inflating your button view
                View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_requisition_button_fragment, parent, false);
                return new RequisitionButtonViewHolder(itemView);
            }
        }

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if(holder instanceof RequisitionDetailsViewHolder){
            RequisitionDetailsViewHolder itemViewHolder = (RequisitionDetailsViewHolder) holder;

          if(role.contains("Employee")){
              itemViewHolder.mTextViewItemCode.setText("ItemCode: " + mRequisitionDetailsArr[position].getItemCode());
              itemViewHolder.mTextViewDescription.setText("Description: " + mRequisitionDetailsArr[position].getDescription());
              itemViewHolder.mTextViewQuantity.setText("Quantity: " + mRequisitionDetailsArr[position].getQuantity());
          }
          else if(role.contains("DepartmentHead")){
              if(position != mRequisitionDetailsArr.length + 1){
                  itemViewHolder.mTextViewItemCode.setText("ItemCode: " + mRequisitionDetailsArr[position].getItemCode());
                  itemViewHolder.mTextViewDescription.setText("Description: " + mRequisitionDetailsArr[position].getDescription());
                  itemViewHolder.mTextViewQuantity.setText("Quantity: " + mRequisitionDetailsArr[position].getQuantity());
              }
          }
        }
        else if(holder instanceof RequisitionButtonViewHolder){
            RequisitionButtonViewHolder buttonViewHolder = (RequisitionButtonViewHolder) holder;

            //Approval Button
            buttonViewHolder.approval_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    approveRequisition(new RequisitionListener() {
                        @Override
                        public void onResponse() {
                            // Create fragment that you want to go to
                            RequisitionFragment requisitionFragment = new RequisitionFragment();

                            // Go to new fragment on button click
                            AppCompatActivity activity  =(AppCompatActivity) mContext;
                            activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, requisitionFragment).addToBackStack(null).commit();

                            // Approval message
                            Toast.makeText(mContext, "Requisitions Approved", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });

            //Reject Button
            buttonViewHolder.reject_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    rejectRequisition(new RequisitionListener() {
                        @Override
                        public void onResponse() {
                            // Create fragment that you want to go to
                            RequisitionFragment requisitionFragment = new RequisitionFragment();

                            // Go to new fragment on button click
                            AppCompatActivity activity  =(AppCompatActivity) mContext;
                            activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, requisitionFragment).addToBackStack(null).commit();

                            // Rejection message
                            Toast.makeText(mContext, "Rejected Approval", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {

        //If role contains Department Employee
        if(role.contains("Employee")){
            return TYPE_ITEM;
        }
        // If role contains DepartmentHead
        else if (role.contains("DepartmentHead")){
            if(position == mRequisitionDetailsArr.length){
                return TYPE_BUTTON;
            } else {
                return TYPE_ITEM;
            }
        }
        return TYPE_ITEM;
    }

    @Override
    public int getItemCount() {
        if(mRequisitionDetailsArr != null){
            if(role.contains("Employee")){
                return mRequisitionDetailsArr.length;
            }
            else if(role.contains("DepartmentHead")){
                return mRequisitionDetailsArr.length + 1;
            }
        }
        return 0;
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

    //Inner class to hold reference to confirmation button of Recycler View
    public class RequisitionButtonViewHolder extends RecyclerView.ViewHolder {
        private Button approval_button;
        private Button reject_button;

        public RequisitionButtonViewHolder(View itemView) {
            super(itemView);
            approval_button = itemView.findViewById(R.id.requisition_approve_button);
            reject_button = itemView.findViewById(R.id.requisition_reject_button);
        }
    }

    public interface RequisitionListener {
        void onResponse();
    }

    private void approveRequisition(final RequisitionListener listener){
        Retrofit.Builder builder = new Retrofit.Builder().baseUrl("https://team11ssis.azurewebsites.net/").addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        RequisitionApi requisitionApi = retrofit.create(RequisitionApi.class);

        //Set up requisition
        Requisition requisition = new Requisition(requisitionId, departmentId,null, null, null);
        Call<ResponseBody> call = requisitionApi.approveRequisition(token, requisition);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful())
                    listener.onResponse();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    private void rejectRequisition(final RequisitionListener listener){
        Retrofit.Builder builder = new Retrofit.Builder().baseUrl("https://team11ssis.azurewebsites.net/").addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        RequisitionApi requisitionApi = retrofit.create(RequisitionApi.class);

        //Set up requisition
        Requisition requisition = new Requisition(requisitionId, departmentId, null, null, null);
        Call<ResponseBody> call = requisitionApi.rejectRequisition(token, requisition);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful())
                    listener.onResponse();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
}
