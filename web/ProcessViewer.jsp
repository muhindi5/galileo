<%-- 
    Document   : processList
    Created on : 24-Aug-2015, 20:43:08
    Author     : Kelli
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>pTrack</title>
        <link href="style/style.css" rel="stylesheet" type="text/css"/>
        <link href="style/tab-style.css" rel="stylesheet" type="text/css"/> 
        <script src="scripts/jquery-2.1.3.min.js" type="text/javascript"></script>
        <!-- Load the Google Ajax API -->
        <script type="text/javascript" src="https://www.google.com/jsapi"></script>
        <!-- Call Javascript code to fetch usage data, create google chart and display -->
    </head>
    <body>
        <div id ="banner">
            <!--<img src="images/test-banner.jpg" width="800" height="100" alt="banner here"/>-->
        </div>
        <hr>
        <div id="hosts-panel">

            <c:forEach items="${hosts}" var="current">
                <div id="host-block">
                    <a href="http://localhost:8080/galileo/p_viewer?id=${current.csName}">
                        <span>${current.csName}</span></a>
                </div>
            </c:forEach>
        </div>
        <div class="tabs">
            <ul class="tab-links">
                <li class="active"><a href="#tab1">User Activity</a></li>
                <li><a href="#tab2">Usage Metrics</a></li>
                <li><a href="#tab3">Snap Shots</a></li>
            </ul>
        <div class="tab-content">
            <div id="tab1" class="tab active">
                <table>
                    <thead>
                    <td>Document Opened</td>
                    <td>Application</td>
                    <td>Started at</td>
                    </thead>
                    <tbody>
                        <c:forEach items="${pList}" var="current">
                            <tr>
                                <td><a href="">${current.winTitle}</a></td>
                                <td><a href="">${current.description}</a></td>
                                <td><a href="">${current.startTime}</a></td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
            <!-- Application Usage chart section -->
            <script type="text/javascript">
                var preproc = '${chart_data}'
            </script>
        <script type="text/javascript" src="scripts/chart_gen.js"></script>
            <div id="tab2" class="tab">
                <div id="chart_div">
                </div>
            </div>
            <!-- Host screen captures section -->
            <div id="tab3" class="tab">
                <p>Snapshots</p>
            </div>
        </div>
        </div>
        <script>
            jQuery(document).ready(function () {
                jQuery('.tabs .tab-links a').on('click', function (e) {
                    var currentAttrValue = jQuery(this).attr('href');
                    console.log("clicked: " + currentAttrValue.toString());
                    // Show/Hide Tabs
                    jQuery('.tabs ' + currentAttrValue).show().siblings().hide();

                    // Change/remove current tab to active
                    jQuery(this).parent('li').addClass('active').siblings().removeClass('active');

                    e.preventDefault();
                });
            });
        </script>
        

    </body>
</html>
