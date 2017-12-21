package iss.sa45.bookshop;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.util.HashMap;

/**
 * Created by mmu1t on 19/12/2017.
 */

public class DetailsFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.details, container, false);
        Bundle arg = getArguments();
        if (arg != null) {
            final HashMap<String,String> item = (HashMap<String,String>) arg.getSerializable("item");
            if (item != null) {
                TextView textAuthor = (TextView) v.findViewById(R.id.textAuthor);
                textAuthor.setText("Author :"+ item.get("author"));

                TextView textBookID = (TextView) v.findViewById(R.id.textBookID);
                textBookID.setText("BookID :"+ item.get("bookId"));

                Resources res = getResources();
                String[] catArray = res.getStringArray(R.array.category);
                int catIndex = (Integer.parseInt(item.get("catId")) );


                TextView textCatID = (TextView) v.findViewById(R.id.textCatID);
                textCatID.setText("Category :"+ catArray[catIndex]);


                TextView textISBN = (TextView) v.findViewById(R.id.textISBN);
                textISBN.setText("ISBN :"+ item.get("isbn"));

                TextView textPrice = (TextView) v.findViewById(R.id.textPrice);
                textPrice.setText("Price :"+ item.get("price"));


                TextView textStock = (TextView) v.findViewById(R.id.textStock);
                textStock.setText("Stock :"+ item.get("stock"));

                TextView textTitle = (TextView) v.findViewById(R.id.textTitle);
                textTitle.setText("Title :"+ item.get("title"));

                //ImageView img = (ImageView) d.findViewById(R.id.imgView);
                String uri = BookItem.URI_BOOKIMAGE+item.get("isbn")+".jpg";

                new AsyncTask<String, Void, Bitmap>() {
                    @Override
                    protected Bitmap doInBackground(String... params) {

                        return getImage(params[0]);
                    }

                    @Override
                    protected void onPostExecute(Bitmap resultImg){
                        ImageView img = (ImageView) v.findViewById(R.id.imgView);
                        img.setImageBitmap(resize(resultImg,2.5));
                    }

                    protected Bitmap getImage(String url) {

                        Bitmap bitmap = null;
                        try {
                            InputStream in = new java.net.URL(url).openStream();
                            bitmap = BitmapFactory.decodeStream(in);
                        } catch (Exception e) {
                            Log.e("MyApp", e.getMessage());
                        }
                        return bitmap;
                    }

                    protected Bitmap resize(Bitmap bitmap, double size) {

                            Bitmap b = Bitmap.createScaledBitmap(bitmap,
                                    (int) (bitmap.getWidth() * size),
                                    (int) (bitmap.getHeight() * size), false);
                            bitmap = b;
                            return bitmap;
                    }

                }.execute(uri);

                //changsiang and jane start writing here

                Button btnUpdate = (Button) v.findViewById(R.id.btnUpdate);
                btnUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final String TAG = "DETAILS_FRAG";
                        FragmentManager fm = getFragmentManager();
                        FragmentTransaction trans = fm.beginTransaction();
                        Bundle args = new Bundle();
                        args.putSerializable("item", item);
                        Fragment f = new UpdateFragment();
                        f.setArguments(args);
                        if (fm.findFragmentByTag(TAG) == null)
                            // fragment not found -- to be added //portriat
                            trans.replace(R.id.detailsframe2, f, TAG);
                        else
                            // fragment found -- to be replaced
                            trans.replace(R.id.detailsframe, f, TAG);
                        trans.commit();
                    }
                });
                //changsiang and jane end writing here


            }
        }
        return(v);
    }
}