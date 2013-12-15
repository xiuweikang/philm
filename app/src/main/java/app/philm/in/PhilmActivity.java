package app.philm.in;

import com.squareup.otto.Bus;

import android.app.Activity;
import android.os.Bundle;

import app.philm.in.controllers.MainController;
import app.philm.in.controllers.MovieController;
import app.philm.in.controllers.UserController;
import app.philm.in.state.ApplicationState;


public class PhilmActivity extends Activity implements MovieController.MovieControllerProvider,
        UserController.UserControllerProvider {

    private MainController mMainController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        final Bus bus = Container.getInstance(this).getEventBus();

        Display display = new Display(this);
        ApplicationState state = new ApplicationState(bus);

        UserController userController = new UserController(display, state);
        MovieController movieController = new MovieController(display, state);

        mMainController = new MainController(userController, movieController);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        mMainController.init();
    }

    @Override
    protected void onPause() {
        mMainController.suspend();
        super.onPause();
    }

    @Override
    public MovieController getMovieController() {
        return mMainController.getMovieController();
    }

    @Override
    public UserController getUserController() {
        return mMainController.getUserController();
    }
}
