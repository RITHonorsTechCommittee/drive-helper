# Helper Classes

## Entities
Entities store the state of the Drive.  I (Reggie) have a strong preference that entities be immutable.

:File
Represents a single file in the Drive.

:Folder
Subclass of File.  Adds functionality associated with directories

:User
Represents a person who has access to a file.  The User class provides a way to access email address and permissions of each person who has access to a file.

## Lookup Classes
Lookup classes access the Google Drive API and return Entities.

:FileHelper
Finds files in the drive.  E.g. findChildren(), getTree(), etc.

:UserHelper
Finds users of files and permissions of users.
