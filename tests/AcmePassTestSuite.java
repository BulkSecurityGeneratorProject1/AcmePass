package com.acme;

import com.acme.EditPass.*;
import com.acme.GeneratePassword.GeneratePasswordTest;
import junit.framework.Test;
import junit.framework.TestSuite;

public class AcmePassTestSuite {

    public static Test suite() {
        TestSuite suite = new TestSuite();
        suite.addTestSuite(CreatePassTest.class);
        suite.addTestSuite(GeneratePasswordTest.class);

        suite.addTestSuite(PaginationTest.class);
        suite.addTestSuite(PassViss.class);

        suite.addTestSuite(EmptyLogin.class);
        suite.addTestSuite(EmptyPass.class);
        suite.addTestSuite(EmptySite.class);
        suite.addTestSuite(NewLongLogin.class);
        suite.addTestSuite(NewLongPass.class);
        suite.addTestSuite(NewLongSite.class);
        suite.addTestSuite(NoChange.class);
        suite.addTestSuite(ShortSite.class);
        suite.addTestSuite(ValidField.class);

        suite.addTestSuite(UnverifiedAccessCase.class);
        suite.addTestSuite(DeletingButCancellingCase.class);
        suite.addTestSuite(FullDeletionCase.class);
        suite.addTestSuite(SortingCase.class);
        suite.addTestSuite(SortingCaseIDNumber.class);

        return suite;
    }

    public static void main(String[] args) {
        junit.textui.TestRunner.run(suite());
    }
}
