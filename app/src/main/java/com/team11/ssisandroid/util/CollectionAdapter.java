package com.team11.ssisandroid.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.team11.ssisandroid.R;
import com.team11.ssisandroid.fragments.CollectionFragment;
import com.team11.ssisandroid.interfaces.CollectionApi;
import com.team11.ssisandroid.models.DepartmentCollection;
import com.team11.ssisandroid.models.UserRole;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CollectionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    private static final int TYPE_BUTTON = 2;

    private String role;
    private String token;
    private String departmentId;
    private Context mContext;
    private DepartmentCollection departmentCollection;

    public CollectionAdapter(Context mContext, DepartmentCollection departmentCollection, String role, String departmentId, String token) {
        this.mContext = mContext;
        this.departmentCollection = departmentCollection;
        this.role = role;
        this.departmentId = departmentId;
        this.token = token;
    }

    // Create new view
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType == TYPE_ITEM) {
            // Here Inflating your recyclerview item layout
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_collection_fragment, parent, false);
            return new CollectionViewHolder(itemView);
        } else if (viewType == TYPE_HEADER) {
            // Here Inflating your header view
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_collection_header_fragment, parent, false);
            return new CollectionHeaderViewHolder(itemView);
        }

        if(role.contains("Employee")){
            if(viewType == TYPE_BUTTON){
                //Here Inflating your button view
                View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_collection_button_fragment, parent, false);
                return new CollectionButtonViewHolder(itemView);
            }
        }

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof CollectionHeaderViewHolder){
            CollectionHeaderViewHolder headerViewHolder = (CollectionHeaderViewHolder) holder;

            // You have to set your header items values with the help of model class and you can modify as per your needs
            headerViewHolder.mTextViewCollectionPoint.setText(departmentCollection.getCollectionPoint());
            headerViewHolder.mTextViewDepartmentName.setText(departmentCollection.getDepartmentName());

        }
        else if (holder instanceof CollectionViewHolder){

            final CollectionViewHolder itemViewHolder = (CollectionViewHolder) holder;

            // You have to set your listview items values with the help of model class and you can modify as per your needs

            if(role.contains("StoreClerk")){
                itemViewHolder.mTextViewDescription.setText(departmentCollection.getItemDisbursements()[position - 1].getItemDescription());
                itemViewHolder.mTextViewItemCode.setText(departmentCollection.getItemDisbursements()[position - 1].getItemCode());
                itemViewHolder.mTextViewQuantity.setText("Quantity: " + departmentCollection.getItemDisbursements()[position - 1].getAvailableQuantity());
            }
            else if(role.contains("Employee")){
                if(position != departmentCollection.getItemDisbursements().length + 1){
                    itemViewHolder.mTextViewDescription.setText(departmentCollection.getItemDisbursements()[position - 1].getItemDescription());
                    itemViewHolder.mTextViewItemCode.setText(departmentCollection.getItemDisbursements()[position - 1].getItemCode());
                    itemViewHolder.mTextViewQuantity.setText("Quantity: " + departmentCollection.getItemDisbursements()[position - 1].getAvailableQuantity());
                }
            }
        }
        else if (holder instanceof CollectionButtonViewHolder){

            final CollectionButtonViewHolder buttonViewHolder = (CollectionButtonViewHolder) holder;

            buttonViewHolder.collection_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(departmentCollection.getItemDisbursements().length < 1){
                        Toast.makeText(view.getContext(), "You have no collections to confirm", Toast.LENGTH_SHORT).show();
                    } else {
                        // Send api call to server to update database
                        confirmCollection(new ConfirmCollectionListener() {
                            @Override
                            public void onResponse() {
                                Toast.makeText(mContext, "Collections confirmed", Toast.LENGTH_SHORT).show();
                                // Create fragment that you want to go to
                                CollectionFragment collectionFragment = new CollectionFragment();

                                // Go to new fragment on button click
                                AppCompatActivity activity  =(AppCompatActivity) mContext;
                                activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, collectionFragment).addToBackStack(null).commit();
                            }
                        });
                    }
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position){

        //If role contains Employee, itemViewType can be header/item/confirmationButton
        if(role.contains("Employee")){
            if(position == 0){
                return TYPE_HEADER;
            } else if(position == departmentCollection.getItemDisbursements().length + 1){
                return TYPE_BUTTON;
            } else {
                return TYPE_ITEM;
            }
        }
        //If role contains StoreClerk, should only have header and items
        else if (role.contains("StoreClerk")){
            if(position == 0){
                return TYPE_HEADER;
            } else {
                return TYPE_ITEM;
            }
        } else {
            return TYPE_ITEM;
        }
    }

    // getItemCount increase the position depending on role.
    @Override
    public int getItemCount() {
        if(departmentCollection.getItemDisbursements() != null){
            // If role contains StoreClerk, should show Header + Items
            if(role.contains("StoreClerk")){
                if (departmentCollection.getItemDisbursements().length > 0)
                    return departmentCollection.getItemDisbursements().length + 1;
            }
            // If role contains Employee, should show Header + Items + Approval button
            else if(role.contains("Employee")){
                if(departmentCollection.getItemDisbursements().length > 0)
                    return departmentCollection.getItemDisbursements().length + 2;
            } else {
                return 0;
            }
        } else {
            // Return Header
            return 1;
        }

        return 0;
    }


    // Inner class to hold reference to header of Recycler View
    public class CollectionHeaderViewHolder extends RecyclerView.ViewHolder{
        private TextView mTextViewCollectionPoint;
        private TextView mTextViewDepartmentName;

        public CollectionHeaderViewHolder(View view) {
            super(view);

            mTextViewCollectionPoint = itemView.findViewById(R.id.collectionCollectionPoint);
            mTextViewDepartmentName = itemView.findViewById(R.id.collectionDepartmentName);
        }
    }

    //Inner class to hold reference to confirmation button of Recycler View
    public class CollectionButtonViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout collection_button;

        public CollectionButtonViewHolder(View itemView) {
            super(itemView);
            collection_button = itemView.findViewById(R.id.collection_button);
        }
    }

    // Inner class to hold reference to header of Recycler View
    public class CollectionViewHolder extends RecyclerView.ViewHolder{
        private TextView mTextViewDescription;
        private TextView mTextViewItemCode;
        private TextView mTextViewQuantity;

        public CollectionViewHolder(View itemView) {
            super(itemView);

            mTextViewDescription = itemView.findViewById(R.id.collectionDescription);
            mTextViewItemCode = itemView.findViewById(R.id.collectionItemCode);
            mTextViewQuantity = itemView.findViewById(R.id.collectionQuantity);
        }
    }

    public interface ConfirmCollectionListener {
        void onResponse();
    }

    private void confirmCollection(final ConfirmCollectionListener listener){
        Retrofit.Builder builder = new Retrofit.Builder().baseUrl("https://team11ssis.azurewebsites.net/").addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        CollectionApi collectionApi = retrofit.create(CollectionApi.class);

        // Set up UserRole
        UserRole userRole = new UserRole(null, null, departmentId);
        Call<ResponseBody> call = collectionApi.confirmDepartmentCollection(token, userRole);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    listener.onResponse();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

    }
}
