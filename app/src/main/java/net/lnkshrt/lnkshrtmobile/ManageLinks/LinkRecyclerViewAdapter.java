package net.lnkshrt.lnkshrtmobile.ManageLinks;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import net.lnkshrt.lnkshrtmobile.ManageLinks.ManageLinkItem;
import net.lnkshrt.lnkshrtmobile.databinding.FragmentLinkBinding;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link ManageLinkItem}.
 */
public class LinkRecyclerViewAdapter extends RecyclerView.Adapter<LinkRecyclerViewAdapter.ViewHolder> {

    private final List<ManageLinkItem> mValues;

    public LinkRecyclerViewAdapter(List<ManageLinkItem> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(FragmentLinkBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(String.format("https://lnkshrt.net/api/%s", mValues.get(position).id));
        holder.mContentView.setText(mValues.get(position).link);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView mIdView;
        public final TextView mContentView;
        public ManageLinkItem mItem;

        public ViewHolder(FragmentLinkBinding binding) {
            super(binding.getRoot());
            mIdView = binding.linkTarget;
            mContentView = binding.shortLink;
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}