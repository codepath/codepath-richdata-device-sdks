package codepath.com.contactloaderapp;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

public class ContactsActivity extends AppCompatActivity {


    SimpleCursorAdapter mAdapter;

    public static final int CONTACT_LOADER_ID = 123; // A unique identifier for this loader. Can be whatever you want.


    // Step 3:
    // Defines the asynchronous callback for the contacts data loader
    private LoaderManager.LoaderCallbacks<Cursor> contactsLoader =
            new LoaderManager.LoaderCallbacks<Cursor>() {
                // Create and return the actual cursor loader for the contacts data
                @Override
                public Loader<Cursor> onCreateLoader(int id, Bundle args) {
                    // Define the columns to retrieve
                    String[] projectionFields = new String[] { ContactsContract.Contacts._ID,
                            ContactsContract.Contacts.DISPLAY_NAME,
                            ContactsContract.Contacts.PHOTO_URI };
                    // Construct the loader
                    CursorLoader cursorLoader = new CursorLoader(ContactsActivity.this,
                            ContactsContract.Contacts.CONTENT_URI, // URI (like SQL FROM [table])
                            projectionFields, // projection fields (like SQL SELECT [cols])
                            null, // the selection criteria (like SQL WHERE [conditions])
                            null, // the selection args
                            null // the sort order (like SQL ORDER BY [cols])
                    );
                    // Return the loader for use
                    return cursorLoader;
                }

                // When the system finishes retrieving the Cursor through the CursorLoader,
                // a call to the onLoadFinished() method takes place.
                // Also, the Loader will monitor for changes to the provider's data
                // and call this method automatically
                @Override
                public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
                    // The swapCursor() method assigns the new Cursor to the adapter
                    mAdapter.swapCursor(cursor);
                }

                // This method is triggered when the loader is being reset
                // and the loader data is no longer available.
                @Override
                public void onLoaderReset(Loader<Cursor> loader) {
                    // Clear the Cursor we were using with another call to the swapCursor()
                    mAdapter.swapCursor(null);
                }
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        setupCursorAdapter();

        // Step 2: Connect adapter to list
        ListView lvContacts = (ListView) findViewById(R.id.lvContacts);
        lvContacts.setAdapter(mAdapter);

        // Step 4: Activate the loader
        getSupportLoaderManager().initLoader(CONTACT_LOADER_ID,
                new Bundle(), contactsLoader);
    }

    // Step 1: setup cursor adapter mappings (COLUMN => VIEW)
    // Create simple cursor adapter to connect the cursor dataset we load with a ListView
    private void setupCursorAdapter() {
        // Column data from cursor to bind views from
        String[] uiBindFrom = { ContactsContract.Contacts.DISPLAY_NAME,
                ContactsContract.Contacts.PHOTO_URI };
        // View IDs which will have the respective column data inserted
        int[] uiBindTo = { R.id.tvName, R.id.ivImage};
        // Create the simple cursor adapter to use for our list
        // specifying the template to inflate (item_contact),
        mAdapter = new SimpleCursorAdapter(
                this, R.layout.item_contact,
                null, uiBindFrom, uiBindTo,
                0);
    }


}
