' configure test setting properties and options
' you only need to run this once when you launch QTP for the very first time
' since the option settings are different from user to user, each user needs to run the script once.

' created by: Sahoko Hama

Dim App 'As Application
dim Addin, i, rc, addin_count
dim arr_addins

Set App = CreateObject("QuickTest.Application")

' active all the add-ins
addin_count = App.Addins.Count
redim arr_addins(addin_count-1)

for i=1 to addin_count

	set Addin = App.Addins.Item(i)
	arr_addins(i-1) = Addin.Name

next

rc = App.SetActiveAddins(arr_addins)
if not rc then
	msgbox "add-ins couldn't be loaded successfully"
end if

App.Launch
App.Visible = True

' configure run settings
App.Test.Settings.Launchers("Web").Active = False
App.Test.Settings.Launchers("Web").Browser = 1
App.Test.Settings.Launchers("Web").Address = ""
App.Test.Settings.Launchers("Web").CloseOnExit = False
App.Test.Settings.Launchers("Windows Applications").Active = False
App.Test.Settings.Launchers("Windows Applications").Applications.RemoveAll
App.Test.Settings.Run.IterationMode = "rngIterations"
App.Test.Settings.Run.StartIteration = 1
App.Test.Settings.Run.EndIteration = 1
App.Test.Settings.Run.ObjectSyncTimeOut = 20000
App.Test.Settings.Run.DisableSmartIdentification = False
App.Test.Settings.Run.OnError = "NextStep"
App.Test.Settings.Resources.DataTablePath = "<Default>"
App.Test.Settings.Resources.ObjectRepositoryPath = "C:\QTP_Demo\Data\main.tsr"
App.Test.Settings.Resources.Libraries.RemoveAll
App.Test.Settings.Resources.Libraries.Add("C:\QTP_Demo\Lib\EMOS_GPL\startup.vbs")
App.Test.Settings.Web.BrowserNavigationTimeout = 60000
App.Test.Settings.Web.NextPageIfObjNotFound = False
App.Test.Settings.Web.ActiveScreenAccess.UserName = ""
App.Test.Settings.Web.ActiveScreenAccess.Password = ""

' configure options
App.Options.DisableVORecognition = False
App.Options.AutoGenerateWith = False
App.Options.WithGenerationLevel = 2
App.Options.TimeToActivateWinAfterPoint = 500
App.Options.SaveLoadAndMonitorData = True
App.Options.Run.RunMode = "Normal"
App.Options.Run.ViewResults = False
App.Options.Run.CaptureForTestResults = "Always"
App.Options.Web.AddToPageLoadTime = 10
App.Options.Web.CheckBrokenLinks = False
App.Options.Web.OptimizeFrameCreation = True
App.Options.Web.OptimizeUsingUserData = "Get Post"
App.Options.Web.OptimizeUsingNonUserData = ""
App.Options.Web.OptimizeUsingAdditionalPageInfo = True
App.Options.Web.RecordCoordinates = False
App.Options.Web.RecordMouseDownAndUpAsClick = False
App.Options.Web.RecordAllNavigations = False
App.Options.Web.RecordByWinMouseEvents = ""
App.Options.Web.BrowserCleanup = False
App.Options.Web.RunOnlyClick = False
App.Options.Web.RunMouseByEvents = True
App.Options.Web.RunUsingSourceIndex = True
App.Options.Web.EnableBrowserResize = True
App.Options.WindowsApps.AttachedTextRadius = 35
App.Options.WindowsApps.AttachedTextArea = "TopLeft"
App.Options.WindowsApps.ExpandMenuToRetrieveProperties = True
App.Options.WindowsApps.NonUniqueListItemRecordMode = "ByName"
App.Options.WindowsApps.RecordOwnerDrawnButtonAs = "PushButtons"
App.Folders.RemoveAll

App.Quit

msgbox "done"