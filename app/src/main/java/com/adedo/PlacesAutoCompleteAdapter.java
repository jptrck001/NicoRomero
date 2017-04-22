package com.adedo;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;

import java.util.ArrayList;

/**
 * Created by Rulo-PC on 15/4/2016.
 */
class PlacesAutoCompleteAdapter extends ArrayAdapter<String> implements Filterable {

    ArrayList<String> resultList;

    Context mContext;
    int mResource;

    PlaceAPI mPlaceAPI = new PlaceAPI();

    public PlacesAutoCompleteAdapter(Context context, int resource) {
        super(context, resource);

        mContext = context;
        mResource = resource;
    }

    @Override
    public int getCount() {
        // Last item will be the footer
        return resultList.size();
    }

    @Override
    public String getItem(int position) {
        return resultList.get(position);
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                ArrayList<String> FilteredResultList;
                if (constraint != null) {
                    resultList = mPlaceAPI.autocomplete(constraint.toString());

                    FilteredResultList = new ArrayList<String>();
                    for(int i = 0; i< resultList.size(); i++){
                        //if(resultList.get(i).toString().contains("Argentina")) {
                            if(FilteredResultList.size() <= 0)
                                FilteredResultList.add(0, resultList.get(i).toString());
                            else
                                FilteredResultList.add(FilteredResultList.size(), resultList.get(i).toString());
                        //}
                    }
                    // Footer
                    //resultList.add("footer");
                    resultList = FilteredResultList;
                    filterResults.values = resultList;
                    filterResults.count = resultList.size();
                }

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if (results != null && results.count > 0) {
                    notifyDataSetChanged();
                }
                else {
                    notifyDataSetInvalidated();
                }
            }
        };

        return filter;
    }
}