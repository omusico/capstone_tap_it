package tapit.clientapp.hamburger_menu;

/**
 * Created by stephenlee on 5/14/15.
 */
public class NavItem {
    String mTitle;

    public NavItem(String title) {
        mTitle = title;
    }

    public void setTitle(String title){
        mTitle = title;
    }

    public String getTitle(){
        return mTitle;
    }
}