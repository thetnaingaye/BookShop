package iss.sa45.bookshop;

import android.app.Dialog;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.LAX);
        List<BookItem> books = BookItem.jread();

        SimpleAdapter adapter = new SimpleAdapter(this, books, R.layout.row2, new String[]{"title", "isbn"}, new int[]{R.id.text1, R.id.text2});
        setListAdapter(adapter);

    }


    @Override
    protected void onListItemClick(ListView l, View v,
                                   int position, long id) {
        BookItem item = (BookItem) getListAdapter().getItem(position);

        final Dialog d = new Dialog(this);
        d.setTitle(getString(R.string.customdialogtitle));
        d.setContentView(R.layout.customedialog);
        d.setCancelable(true);
        TextView textAuthor = (TextView) d.findViewById(R.id.textAuthor);
        textAuthor.setText("Author :"+ item.get("author"));

        TextView textBookID = (TextView) d.findViewById(R.id.textBookID);
        textBookID.setText("BookID :"+ item.get("bookId"));

        TextView textCatID = (TextView) d.findViewById(R.id.textCatID);
        textCatID.setText("CategoryID :"+ item.get("catId"));


        TextView textISBN = (TextView) d.findViewById(R.id.textISBN);
        textISBN.setText("ISBN :"+ item.get("isbn"));

        TextView textPrice = (TextView) d.findViewById(R.id.textPrice);
        textPrice.setText("Price :"+ item.get("price"));


        TextView textStock = (TextView) d.findViewById(R.id.textStock);
        textStock.setText("Stock :"+ item.get("stock"));

        TextView textTitle = (TextView) d.findViewById(R.id.textTitle);
        textTitle.setText("Title :"+ item.get("title"));


        d.show();

    }
}
