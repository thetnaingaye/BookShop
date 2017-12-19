package iss.sa45.bookshop;


import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.content.ClipData;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListingFragment extends ListFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.list, container, false);


        new AsyncTask<String,Void,List<BookItem>>(){

            @Override
            protected List<BookItem> doInBackground(String... params) {

                return BookItem.jread(params[0]);
            }

            @Override
            protected  void onPostExecute(List<BookItem> resultBookList){
                setListAdapter(new MyAdaptor(getActivity(),R.layout.row3,resultBookList ));
            }


        }.execute(BookItem.URI_SERVICE);



        return(v);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        BookItem s = (BookItem) getListAdapter().getItem(position);

        if (getActivity().findViewById(R.id.detailsframe) == null) {
            // single-pane
            Intent intent = new Intent(getActivity(), DetailsActivity.class);
            intent.putExtra("details", s);
            startActivity(intent);
        } else
            // multi-pane
        display(s);
    }

    void display(BookItem details) {
        final String TAG = "DETAILS_FRAG";
        FragmentManager fm = getFragmentManager();
        FragmentTransaction trans = fm.beginTransaction();

        Fragment frag = new DetailsFragment();
        Bundle args = new Bundle();
        args.putSerializable("item", details);
        frag.setArguments(args);
        if (fm.findFragmentByTag(TAG) == null)
            // fragment not found -- to be added
            trans.add(R.id.detailsframe, frag, TAG);
        else
            // fragment found -- to be replaced
            trans.replace(R.id.detailsframe, frag, TAG);
        trans.commit();
    }
}