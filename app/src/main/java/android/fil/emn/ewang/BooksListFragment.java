package android.fil.emn.ewang;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by evyil on 24/02/2016.
 */

public class BooksListFragment extends Fragment {
    private static final String step0 = "This is step 0";
    private List<Book> books;
    private RecycledBookAdapter myRecycledAdapter;
    private OnBookDetailListener listener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (OnBookDetailListener) context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_view_book, container, false);

        this.books = new ArrayList<Book>();
        this.getBooks();

        this.myRecycledAdapter = new RecycledBookAdapter(books);

        RecyclerView bookList = (RecyclerView) view.findViewById(R.id.my_recycler_view);
        bookList.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        bookList.setLayoutManager(mLayoutManager);

        bookList.setAdapter(this.myRecycledAdapter);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //textView.setText(step0);
    }

    public interface OnBookDetailListener {

        public void onNext();

    }

    private void getBooks() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://henri-potier.xebia.fr/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        HenriPotierService service = retrofit.create(HenriPotierService.class);
        Call<List<Book>> call = service.listBooks();
        call.enqueue(new Callback<List<Book>>() {
            @Override
            public void onResponse(Response<List<Book>> response, Retrofit retrofit) {
                for (Book book: response.body()) {
                    books.add(new Book(book.getTitle(), book.getPrice(), book.getCover()));
                }
                myRecycledAdapter.notifyDataSetChanged();
            }
            @Override
            public void onFailure(Throwable t) {
            }
        });
    }
}
