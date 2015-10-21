<%-- 
    Document   : tabs
    Created on : 22-Sep-2015, 20:31:07
    Author     : Kelli
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link href="style/tab-style.css" rel="stylesheet" type="text/css"/> 
        <script src="scripts/jquery-2.1.3.min.js" type="text/javascript"></script>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title></title>
        <script>
        jQuery(document).ready(function() {
    jQuery('.tabs .tab-links a').on('click', function(e)  {
        var currentAttrValue = jQuery(this).attr('href');
        console.log("clicked: "+currentAttrValue.toString());
        // Show/Hide Tabs
        jQuery('.tabs ' + currentAttrValue).show().siblings().hide();
 
        // Change/remove current tab to active
        jQuery(this).parent('li').addClass('active').siblings().removeClass('active');
 
        e.preventDefault();
    });
});
        </script>
    </head>
    <body>
        <div class="tabs">
            <ul class="tab-links">
                <li class="active"><a href="#tab1">User Activity</a></li>
                <li><a href="#tab2">Usage Metrics</a></li>
                <li><a href="#tab3">Snap Shots</a></li>
            </ul>
        </div>
            <div class="tab-content">
                <div id="tab1" class="tab active">
                    <p>Tab 1 content here</p>
                </div>
                <div id="tab2" class="tab">
                    <p>Tab 2 content here</p>
                </div>
                <div id="tab3" class="tab">
                    <p>Tab 3 content here</p>
                </div>
            </div>
    </body>
</html>
