# Helper Classes

## Entities
Entities store the state of the Drive.  I (Reggie) have a strong preference that entities be immutable.

:File
Represents a single file in the Drive.  This should in some way reference the provided File class by the Google Drive SDK (https://developers.google.com/resources/api-libraries/documentation/drive/v2/java/latest/com/google/api/services/drive/model/class-use/File.html)

:Folder
Subclass of File.  Adds functionality associated with directories (eg. an iterator for all children)

:User
Represents a person who has access to a file.  The User class provides a way to access email address and permissions of each person who has access to a file.

## Lookup Classes
Lookup classes access the Google Drive API and return Entities.

:FileHelper
Finds files in the drive.  E.g. findChildren(), getTree(), etc.

:UserHelper
Finds users of files and permissions of users.
