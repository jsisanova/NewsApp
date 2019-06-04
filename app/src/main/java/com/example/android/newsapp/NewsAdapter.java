package com.example.android.newsapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

// NewsAdapter is a custom adapter that takes as it's input a list of News objects
public class NewsAdapter extends ArrayAdapter<News> {

    /**
     * @param context  The current context. Used to inflate the layout file.
     * @param newsList    A List of News objects to display in a list
     */
    public NewsAdapter(Context context, ArrayList<News> newsList) {
        super(context, 0, newsList);
    }

    /**
     * Provide a view for an AdapterView (ListView)
     *
     * @param position The position in the list of data that should be displayed in the
     *                 list item view.
     * @param convertView The recycled view to populate.
     * @param parent The parent ViewGroup that is used for inflation (in this case ListView)
     * @return The View for the position in the AdapterView.
     */
    @Override
    // Get a list item view that we can use (and return it to ListView)
    public View getView(int position, View convertView, ViewGroup parent) {
        // EITHER by reusing
        View listItemView = convertView;
        // OR inflate (= create) new list item view from news_list_item.xml
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.news_list_item, parent, false);
        }

        //Get the {@link News} object located at this position in the list
        News currentNews = getItem(position);

        // Find the TextViews in the news_list_item.xml file
        TextView articleTitle = listItemView.findViewById(R.id.article_title);
        TextView articleSection = listItemView.findViewById(R.id.article_section);
        TextView articleAuthor = listItemView.findViewById(R.id.article_author);
        TextView articleDate = listItemView.findViewById(R.id.date_textview);

        // Populate the data into the template view using the data object (get and set the associated String values)
        articleTitle.setText(currentNews .getArticleTitle());
        articleSection.setText(currentNews .getArticleSection());
        articleAuthor.setText(currentNews .getArticleAuthor());
        articleDate.setText(currentNews .getArticleDate());

        // Return the whole list item layout so that it can be shown in the ListView on screen
        return listItemView;
    }
}