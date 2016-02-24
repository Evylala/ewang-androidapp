package android.fil.emn.ewang;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by evyil on 22/01/2016.
 */
public class LibraryActivity extends AppCompatActivity implements BooksListFragment.OnBookDetailListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library);

        //boolean landscape = getResources().getBoolean(R.bool.landscape);

        getSupportFragmentManager().beginTransaction()
               .replace(R.id.fragments, new BooksListFragment())
                .commit();
    }

    @Override
    public void onNext() {

        getSupportFragmentManager().beginTransaction();
    }
}
