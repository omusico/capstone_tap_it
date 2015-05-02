package tapit.clientapp.fragments.sign_up_wizard;

import org.codepond.wizardroid.WizardFlow;
import org.codepond.wizardroid.layouts.BasicWizardLayout;

/**
 * Created by stephenlee on 5/2/15.
 */
public class sign_up_wizard extends BasicWizardLayout {

    /**
     * Must have an empty constructor
     */
    public sign_up_wizard() {
        super();
    }

    //You must override this method and create a wizard flow by
    //using WizardFlow.Builder as shown in this example
    @Override
    public WizardFlow onSetup() {
        /* Optionally, you can set different labels for the control buttons
        setNextButtonLabel("Advance");
        setBackButtonLabel("Return");
        setFinishButtonLabel("Finalize"); */

        return new WizardFlow.Builder()
                .addStep(nameStep.class)
                .addStep(phoneNumberStep.class)
                .addStep(preferenceStep.class)
                .create();                              //to create the wizard flow.
    }
}


