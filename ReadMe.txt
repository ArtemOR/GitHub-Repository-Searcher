Description of application:

When the application opens,  the user can see parameters to fill:
Qualifiers - it is mandatory field. The user should fill this parameter to continue use application. For details how to correctly fill this parameter, please see GitHub documentation https://developer.github.com/v3/search/#search-repositories
Sort - optional list field. User can choose one of values “stars”, “forks”, “updated”, or leave field empty
Order - available only if “Sort” attribute is filled. User can choose one of values “asc” or “desc”. Default value is “desc”.

Button “Search”:
When user will fill all the attributes, he clicks the “Search” button.
System checks parameter “Qualifiers” and if it empty shows Exception id1;
Then system checks internet connection, and if device is not connected to the Internet,  system shows Exception id2;
If connection is fine, system search repositories. If repositories was not found system shows Exception id3;

If repositories was found, a page with founded repositories is opens.
System can show maximum 30 repositories. 
Repository view has next format: first line content name of Repository enclosed in quotation marks, textlabel “by” and login of repository’s author(ex: “repository_name” by author_login), second line content it’s description(if description is empty user can see "No description" text). All repositories separated by empty line.
When user clicks by repository name or description system opens user’s default browser or gives to choose one of device’s browsers, and navigate to chosen repository web page.
Page with repositories content button “Return”. When user click to this button system navigate to first page, and user can modify filled keywords.

Specific Exceptions:
id1 - Please fill mandatory field “Qualifiers”
id2 - Please check your internet connection.
id3 - Search complete, found 0 repositories. Please specify another Qualifiers.
