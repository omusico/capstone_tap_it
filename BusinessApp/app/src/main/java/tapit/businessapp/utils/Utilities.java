package tapit.businessapp.utils;

import android.content.Context;
import android.content.res.Configuration;

/**
 * Created by frank on 5/2/15.
 */
public class Utilities {
    /**
     * if the current device is tablet.
     * @return true if it is a tablet.
     */
    public static boolean isTablet(Context context){
        boolean xlarge = ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_XLARGE);
        boolean large = ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE);
        return (xlarge || large);
    }
}
