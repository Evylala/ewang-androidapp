package android.fil.emn.ewang;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
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
    private RecyclerView bookList;
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

        this.bookList = (RecyclerView) view.findViewById(R.id.my_recycler_view);
        this.bookList.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        this.bookList.setLayoutManager(mLayoutManager);

        this.bookList.setAdapter(this.myRecycledAdapter);

        final GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
            @Override public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }
        });

        bookList.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

                View child = bookList.findChildViewUnder(e.getX(), e.getY());
                if (child != null && gestureDetector.onTouchEvent(e)) {
                    Book book = books.get(bookList.getChildAdapterPosition(child));
                    listener.onNext(book);
                    return true;
                }
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {
            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //textView.setText(step0);
    }

    public interface OnBookDetailListener {

        public void onNext(Book book);

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
                    books.add(book);
                }
                myRecycledAdapter.notifyDataSetChanged();
            }
            @Override
            public void onFailure(Throwable t) {
            }
        });
    }
}
