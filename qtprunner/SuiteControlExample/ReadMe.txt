This posting describes a technique for using a QTP test (in this example named: suiteControl) to control execution of X number of other tests. This posting should interest anyone who would prefer not to use the heavyweight TestDirector/QC solution simply for the purpose of implementing a suite of tests.

Attached is a ZIP file with all of the components and tests described below. You should be able to unzip these tests and libs in any directory and run them (they are suppose to all contain path relative references, but in you have a problems try running them in c:\qtp\scripts\suiteExample root directory).

Note that the attached example was done using QTP 9.2, so those who have QTP 8.2 will not be able to run the code, but hopefully will be able to view the QTP files using a standard text editor.

The Components

1. <rootdir>\testArray.vbs is the heart of this implementation and contains an array of data structures, where the array is actually a Dictionary object and the structures are simple custom objects¡ªone for each test called by the suite controller test.

2. <rootdir>\suiteControl is the suite controller QTP test that calls X number of tests in the suite.

3. <rootdir>\test101 an example test case to be called.

4. <rootdir>\test520 an example test case to be called.

How The Components Interact

As you can see by reviewing the suiteControl test case, its job is to simply iterate through the testArray dictionary (defined in testArray.vbs) and call each test defined in each testStruct object found in the dictionary. Furthermore it logs pass/fail status for each test as they return.

Because the testArray dictionary and its objects are shared by the suite controller test and all of the called tests (they all include ..\testArray.vbs on their Test Settings Resource tab) each called test can communicate (i.e. pass data to and from) with the suite controller using its test-unique testStruct object in the dictionary.

Review either of the called test cases, test101 or 520. Note that they get a copy of their private testStruct from the testArray dictionary using the method call:
Code:


Dim testNum : testNum=101
Dim   ts    : Set  ts=testArray.getStruct(testNum, "test enter")


Thereafter a test can determine if they have been invoked ¡°standalone¡± or invoked via the suite controller test by evaluating the ts.bStandalone setting (if False they have been invoked by the suite controller). This allows for standalone execution and debugging stuff to be placed in the script that does not need to be commented out when invoked by the suite controller. (Review this test code and experiment¡­)

Setup Related Remarks

At this point you should probably have enough understanding to experiment and extend this design implementation to meet your unique needs¡ªas I believe the code is simple and complete. But a few setup related issues and gothcas are worth mentioning:

1. testArray.vbs¡¯s testArrayInit() function builds a dataStruct object for every test action being called. Of this initialization the one who¡¯s syntax is most demainding is the one defining the action to call in a test, for example:

testStruct.sActionToCall = "main [Test101]"

The string must contain the exact syntax that QTP inserts in a call to the external action. (Note that I like to standardize on ¡°main¡± as the entry Action to all of my tests, but this is not a requirement to using this implementation).

2. It would be a wonderful world in QTP land if you could place a ¡°call to action blah¡± in your code, and then call it. If that was the case then the definition in step #1 above would be all we would have to do for setup. But alas, this is not the case with QTP. You must also, using the QTP IDE, perform an Insert:Call To Existing Action¡­ interactive step in the suite controller test case (suiteControl in our example). This insertion should always be done using one of the path relative techniques described in the following posting:

http://www.sqaforums.com/showflat.php?Cat=0&Number=378894&an=0&page=0#Post378894

3. Each called test returns its status via its private testStruct¡¯s bPassed variable. By default a test is always invoked with this value set to False (i.e. test ¡°failed¡± status), so a test which determines it has ¡°passed¡± needs to set this testStruct value to True prior to returning the the suite controller. This should be done no matter how the test is invoked (to be on the safe side).

Beyond this simple example

This example code has been kept intentionally simple, so that the concepts and communication mechanisms are easy to see and understand. But the testStruct object can be enhanced and added to in any number of ways (say, adding X number of input fields and Y number of output fields) to meet more demanding test interface needs. This implementation also solves the problem, by using a user extensible testStruct object, to passing complex data to/from calling and called Actions.

-Hope this helps, Terry Horwath 