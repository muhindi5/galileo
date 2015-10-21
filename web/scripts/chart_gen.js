   
                // Load the Visualization API and the piechart package.
      google.load('visualization', '1.0', {'packages':['corechart']});

      // Set a callback to run when the Google Visualization API is loaded.
      google.setOnLoadCallback(drawChart);

      // Callback that creates and populates a data table,
      // instantiates the pie chart, passes in the data and
      // draws it.
      function drawChart() {

        // Create the data table.
       
        var data = new google.visualization.arrayToDataTable(preproc);
        console.log(preproc);
//        data.addColumn('string', 'Application');
//        data.addColumn('number', 'Usage Time');
//        data.addRows([
//          ['VLC Media Player', 3],
//          ['Mozilla Firefox', 6],
//          ['Microsoft Word', 1],
//          ['Foxit PDF Viewer', 2]
//        ]);

        // Set chart options
        var options = {'title':'Application Usage on Lunar',
                       'width':700,
                       'height':300};

        // Instantiate and draw our chart, passing in some options.
        var chart = new google.visualization.BarChart(document.getElementById('chart_div'));
        chart.draw(data, options);
      }
      
      function getUsageMetrics(jsonStr){
          
      }