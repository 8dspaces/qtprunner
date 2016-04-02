Dim App 'As Application
Set App = CreateObject("QuickTest.Application")
App.Launch
App.Visible = True
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

set App = nothing
msgbox "done"