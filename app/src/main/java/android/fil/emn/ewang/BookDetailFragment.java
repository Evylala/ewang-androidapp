package android.fil.emn.ewang;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

/**
 * Created by evyil on 24/02/2016.
 */
public class BookDetailFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.test, container, false);

        Book book = getArguments().getParcelable("BOOK");
        TextView titleView = (TextView) view.findViewById(R.id.nameTextDetailView);
        titleView.setText(book.getTitle());

        ImageView imgView = (ImageView) view.findViewById(R.id.coverImageDetailView);
        Glide.with(getContext())
                .load(book.getCover())
                .centerCrop()
                .into(imgView);

        TextView priceView = (TextView) view.findViewById(R.id.priceTextDetailView);
        priceView.setText("Prix : "+ book.getPrice() + "€");

        return view;
    }
}
