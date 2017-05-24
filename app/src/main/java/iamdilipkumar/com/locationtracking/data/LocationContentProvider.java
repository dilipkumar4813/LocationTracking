package iamdilipkumar.com.locationtracking.data;

import android.net.Uri;

import net.simonvt.schematic.annotation.ContentProvider;
import net.simonvt.schematic.annotation.ContentUri;
import net.simonvt.schematic.annotation.InexactContentUri;
import net.simonvt.schematic.annotation.TableEndpoint;

/**
 * Created on 24/05/17.
 *
 * @author dilipkumar4813
 * @version 1.0
 */

@ContentProvider(authority = LocationContentProvider.AUTHORITY, database = LocationDatabase.class)
public class LocationContentProvider {

    static final String AUTHORITY = "iamdilipkumar.com.locationtracking.data.provider";

    @TableEndpoint(table = LocationDatabase.LOCATIONS)
    public static class ContentLocations {

        @ContentUri(path = "locations", type = "vnd.android.cursor.dir/locations", defaultSort = LocationColumns._ID + " DESC")
        public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/locations");

        @InexactContentUri(
                path = "locations/#",
                name = "LIST_ID",
                type = "vnd.android.cursor.item/locations",
                whereColumn = LocationColumns._ID,
                pathSegment = 1)
        public static Uri withId(long id) {
            return Uri.parse("content://" + AUTHORITY + "/locations/" + id);
        }
    }
}
