package com.example.chris.collapsingtoolbar;

/**
 * Created by Chris on 04/12/2017.
 */

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.ViewConfiguration;

import com.example.collapsingtoolbar.R;

import java.lang.reflect.Field;

//import static com.miguelcatalan.materialsearchview.R.styleable.MaterialSearchView;

public class SearchResultsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //always show the overflow menu. Some devices don't show it by default
        //This function is in the GlobalFunctions class
        makeActionOverflowMenuShown(SearchResultsActivity.this);

        handleIntent(getIntent());

}

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            //use the query to search your data somehow
        }
    }


    // Menu icons are inflated just as they were with actionbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        SearchView searchView;
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        MenuItem searchMenuItem = menu.findItem(R.id.search);
        if (searchMenuItem == null) {
            return true;
        }

        searchView = (SearchView) searchMenuItem.getActionView();
        MenuItemCompat.setOnActionExpandListener(searchMenuItem, new MenuItemCompat.OnActionExpandListener() {

            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                // Set styles for expanded state here
                if (getSupportActionBar() != null) {
                    getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.RED));
                }
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                // Set styles for collapsed state here
                if (getSupportActionBar() != null) {
                    getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.BLUE));
                }
                return true;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.new_contact:
                //start the NewContact class
                Intent intent = new Intent(SearchResultsActivity.this, NewContact.class);

                //pass allPhonesofContacts Array to NewContact, so we can display in ListView
                //intent.putExtra("allPhonesofContacts", allPhonesofContacts);

                //pass allNamesofContacts Array to NewContact, so we can display in ListView
                //intent.putExtra("allNamesofContacts", allNamesofContacts);

                //also take the names and numbers of contacts, so they'll be displayed in the list view
                //intent.putExtra("jsonArrayAllPhonesandNamesofContacts", jsonArrayAllPhonesandNamesofContacts);

                //also take the matching contacts, so they'll be displayed in the list view
                //intent.putExtra("JsonArrayMatchingContacts", JsonArrayMatchingContacts);
                startActivity(intent);
                return true;
        }
        return false;
    }

    //always show the overflow menu, some models of phone don't show it by default
    public static void makeActionOverflowMenuShown(Context context) {
        //devices with hardware menu button (e.g. Samsung Note) don't show action overflow menu
        try {
            ViewConfiguration config = ViewConfiguration.get(context);
            Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
            if (menuKeyField != null) {
                menuKeyField.setAccessible(true);
                menuKeyField.setBoolean(config, false);
            }
        } catch (Exception e) {
            //Log.d(TAG, e.getLocalizedMessage());
        }

    }




}