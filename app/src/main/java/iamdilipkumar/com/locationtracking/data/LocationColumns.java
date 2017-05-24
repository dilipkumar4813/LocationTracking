package iamdilipkumar.com.locationtracking.data;

import net.simonvt.schematic.annotation.AutoIncrement;
import net.simonvt.schematic.annotation.DataType;
import net.simonvt.schematic.annotation.NotNull;
import net.simonvt.schematic.annotation.PrimaryKey;

/**
 * Created on 24/05/17.
 *
 * @author dilipkumar4813
 * @version 1.0
 */

public interface LocationColumns {

    @DataType(DataType.Type.INTEGER)
    @PrimaryKey
    @AutoIncrement
    String _ID = "id";

    @DataType(DataType.Type.INTEGER)
    @NotNull
    String UID = "uniqueIdentifier";

    @DataType(DataType.Type.TEXT)
    @NotNull
    String LATITUDE = "latitude";

    @DataType(DataType.Type.TEXT)
    @NotNull
    String LONGITUDE = "longitude";

    @DataType(DataType.Type.TEXT)
    @NotNull
    String TIME_STAMP = "timestamp";
}
