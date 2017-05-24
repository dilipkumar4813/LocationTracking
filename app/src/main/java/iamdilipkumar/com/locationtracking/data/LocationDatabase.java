package iamdilipkumar.com.locationtracking.data;

import net.simonvt.schematic.annotation.Database;
import net.simonvt.schematic.annotation.Table;

/**
 * Created on 24/05/17.
 *
 * @author dilipkumar4813
 * @version 1.0
 */

@Database(version = LocationDatabase.VERSION)
public class LocationDatabase {
    public static final int VERSION = 1;

    @Table(LocationColumns.class) public static final String LOCATIONS = "locations";
}
