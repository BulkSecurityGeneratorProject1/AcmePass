package com.acme;

import com.acme.GeneratePassword.GeneratePasswordTest;
import junit.framework.Test;
import junit.framework.TestSuite;

public class AcmePassTestSuite {

    public static Test suite() {
        TestSuite suite = new TestSuite();
//        suite.addTestSuite(CreatePassTest.class);
        suite.addTestSuite(GeneratePasswordTest.class);
//        suite.addTestSuite(PaginationTest.class);

//        suite.addTestSuite(EmptyLogin.class);
//        suite.addTestSuite(EmptyPass.class);
//        suite.addTestSuite(EmptySite.class);
//        suite.addTestSuite(NewLongLogin.class);
//        suite.addTestSuite(NewLongPass.class);
//        suite.addTestSuite(NewLongSite.class);
//        suite.addTestSuite(NoChange.class);
//        suite.addTestSuite(ShortSite.class);
//        suite.addTestSuite(ValidField.class);

        return suite;
    }

    public static void main(String[] args) {
        junit.textui.TestRunner.run(suite());
    }
}
