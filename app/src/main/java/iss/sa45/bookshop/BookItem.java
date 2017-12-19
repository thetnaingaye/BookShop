package iss.sa45.bookshop;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by mmu1t on 18/12/2017.
 */

public class BookItem  extends HashMap<String,String>{

    public static final String URI_SERVICE = "http://192.168.1.15/BookShop/Service.svc/Books";
    public static final String URI_BOOKIMAGE = "http://192.168.1.15/BookShop/images/";

    public BookItem(String author,String bookId, String catId, String isbn,String price,String stock,String title)
    {
        put("author",author);
        put("bookId", bookId);
        put("catId", catId);
        put("isbn",isbn);
        put("price",price);
        put("stock",stock);
        put("title",title);

    }

    public static List<BookItem> jread(String url) {
        List<BookItem> list = new ArrayList<BookItem>();
        JSONArray a = JSONParser.getJSONArrayFromUrl(url);
        try {
            for (int i =0; i<a.length(); i++) {
                JSONObject b = a.getJSONObject(i);
                list.add(new BookItem(b.getString("Author"), b.getString("BookID"),
                        b.getString("CategoryID"),b.getString("ISBN"),b.getString("Price"),b.getString("Stock"),b.getString("Title")));
            }
        } catch (Exception e) {
            Log.e("BookItem", "JSONArray error");
        }
        return(list);
    }

}
