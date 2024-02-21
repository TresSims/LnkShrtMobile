package net.lnkshrt.lnkshrtmobile.ManageLinks;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

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
        holder.mShareLink.setOnClickListener((v) -> {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, String.format("https://lnkshrt.net/api/%s", mValues.get(position).id));
            sendIntent.setType("text/plain");
            v.getContext().startActivity(sendIntent);
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView mIdView;
        public final TextView mContentView;
        public ManageLinkItem mItem;
        public ImageButton mShareLink;

        public ViewHolder(FragmentLinkBinding binding) {
            super(binding.getRoot());
            mIdView = binding.linkTarget;
            mContentView = binding.shortLink;
            mShareLink = binding.shareLink;
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}