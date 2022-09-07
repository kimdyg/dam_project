<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<c:set value="${pageContext.request.contextPath}" var="rootPath" />   
<%@ taglib uri="http://www.springframework.org/security/tags"  prefix="sec"%> 
<%@ taglib uri="http://www.springframework.org/tags/form"  prefix="form"%>
   
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<meta name="_csrf_header" th:content="${_csrf.headerName}">
<meta name="_csrf" th:content="${_csrf.token}">

<title>Insert title here</title>
<style>

	#chart {
		display: flex;
		justify-content: center;
		margin: 2%;
	
	}
	#chart1 {
		display: flex;
		justify-content: center;
	
	}
</style>
<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
    <script type="text/javascript">

      // Load the Visualization API and the corechart package.
      google.charts.load('current', {'packages':['corechart']});

      // Set a callback to run when the Google Visualization API is loaded.
      google.charts.setOnLoadCallback(drawChart);
      google.charts.setOnLoadCallback(drawAnthonyChart);

      // Callback that creates and populates a data table,
      // instantiates the pie chart, passes in the data and
      // draws it.
      function drawChart() {

        // Create the data table.
        var data = new google.visualization.DataTable();
        data.addColumn('string', 'Topping');
        data.addColumn('number', 'Slices');
        data.addRows([
          ['광산구', 3],
          ['북구', 1],
          ['동구', 1],
          ['남구', 1],
          ['서구', 2]
        ]);

        // Set chart options
        var options = {'title':'광주광역시 쓰레기 처리 현황',
                       'width':400,
                       'height':300};

        // Instantiate and draw our chart, passing in some options.
        var chart = new google.visualization.PieChart(document.getElementById('chart_div'));
        chart.draw(data, options);
      }
      function drawAnthonyChart() {

          // Create the data table for Anthony's pizza.
          var data = new google.visualization.DataTable();
          data.addColumn('string', 'Topping');
          data.addColumn('number', 'Slices');
          data.addRows([
            ['광산구', 2],
            ['북구', 2],
            ['동구', 2],
            ['남구', 1],
            ['서구', 3]
          ]);

          // Set options for Anthony's pie chart.
          var options = {title:'광주광역시 쓰레기 제보 현황',
                         width:400,
                         height:300};

          // Instantiate and draw the chart for Anthony's pizza.
          var chart = new google.visualization.PieChart(document.getElementById('Anthony_chart_div'));
          chart.draw(data, options);
        }
      google.charts.load('current', {packages: ['corechart', 'bar']});
      google.charts.setOnLoadCallback(drawBasic);
      google.charts.setOnLoadCallback(drawBasic1);
      function drawBasic() {

          var data = google.visualization.arrayToDataTable([
              ['Element', 'Density', { role: 'style' }, { role: 'annotation' } ],
              ['영대', 8.94, '#b87333', '4등' ],
              ['김대영', 10.49, 'silver', '3등' ],
              ['김영대', 19.30, 'gold', '2등' ],
              ['대영김', 21.45, 'color: #e5e4e2', '1등' ]
           ]);

          var options = {
            title: '개인별 쓰레기 처리 현황 TOP5',
            hAxis: {
              title: 'Time of Day',
              format: 'h:mm a',
              viewWindow: {
                min: [7, 30, 0],
                max: [12, 30, 0]
              }
            },
            vAxis: {
              title: 'Rating (scale of 1-10)'
            }
          };

          var chart = new google.visualization.ColumnChart(
            document.getElementById('bargraph1'));

          chart.draw(data, options);
        }
      function drawBasic1() {

          var data = google.visualization.arrayToDataTable([
              ['Element', 'Density', { role: 'style' }, { role: 'annotation' } ],
              ['영대', 19.30, '#b87333', '3등' ],
              ['김대영', 21.45, 'silver', '1등' ],
              ['김영대', 9.30, 'gold', '4등' ],
              ['대영김', 20.45, 'color: #e5e4e2', '2등' ]
           ]);

          var options = {
            title: '개인별 쓰레기 제보 현황 TOP5',
            hAxis: {
              title: 'Time of Day',
              format: 'h:mm a',
              viewWindow: {
                min: [7, 30, 0],
                max: [12, 30, 0]
              }
            },
            vAxis: {
              title: 'Rating (scale of 1-10)'
            }
          };

          var chart = new google.visualization.ColumnChart(
            document.getElementById('bargraph2'));

          chart.draw(data, options);
        }
    </script>

</head>
<body>
	<div id="chart">
	    <div id="chart_div"></div>
	    <div id="Anthony_chart_div"></div>
	</div>
		<div id="chart1">
	    <div id="bargraph1"></div>
	    <div id="bargraph2"></div>
	    </div>
</body>
</html>