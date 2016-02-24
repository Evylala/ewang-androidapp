package android.fil.emn.ewang;

import java.util.List;
import retrofit.Call;
import retrofit.http.GET;
/**
 * Created by evyil on 22/01/2016.
 */
public interface HenriPotierService {

    @GET("books")
    Call<List<Book>> listBooks();

}

