package tapit.businessapp;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;


/**
 * Created by ichenwu on 3/10/15.
 */
public class TapEatApp extends Application {

    private static TapEatApp instance;
//    private TopicsRepo repository;


    public TapEatApp() {
//        if (instance == null) {
//            instance = this;
//        } else {
//            Log.e(TAG, "Created new QuizApp");
//            throw new RuntimeException("Multiple app exception");
//        }
    }

    public static TapEatApp getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {


        Parse.initialize(this, "30csrJ92WSyK4vLawClS1L72JYpHIPoOAGeDw4FX", "gK2cEy7QAUwlD0n3CTVHDcgewknkToHqZogdeag0");

        ParseObject testObject = new ParseObject("TestObject");
        testObject.put("foo", "bar");
        testObject.saveInBackground();
//
//        ParsePush.subscribeInBackground("", new SaveCallback() {
//            @Override
//            public void done(ParseException e) {
//                if (e == null) {
//                    Log.d("com.parse.push", "successfully subscribed to the broadcast channel.");
//                } else {
//                    Log.e("com.parse.push", "failed to subscribe for push", e);
//                }
//            }
//        });

//        super.onCreate();
//        this.repository = new TopicsRepo();
//        Log.i(TAG, "Holy crap it works!");
    }

//    public TopicsRepo getRepository() {
//        return repository;
//    }


}